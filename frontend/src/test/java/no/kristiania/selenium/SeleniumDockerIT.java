package no.kristiania.selenium;


import no.kristiania.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Paths;

// Code copied from class
// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/SeleniumDockerIT.java

@ContextConfiguration(initializers = SeleniumDockerIT.DockerInitializer.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SeleniumDockerIT extends SeleniumTestBase {

    private static String MOVIE_ALIAS = "movie-host";
    private static String PG_ALIAS = "postgresql-host";

    public static Network network = Network.newNetwork();


    public static GenericContainer postgres = new GenericContainer("postgres:10")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_HOST_AUTH_METHOD","trust")
            .withNetwork(network)
            .withNetworkAliases(PG_ALIAS);
            //.withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("POSTGRES")));


    public static class DockerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            //get the host:port on local host to connect to Postgres inside Docker
            String host = postgres.getContainerIpAddress();
            int port = postgres.getMappedPort(5432);

            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:postgresql://" + host + ":" + port + "/postgres",
                    "spring.datasource.username=postgres",
                    "spring.datasource.password"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }


    public static GenericContainer spring = new GenericContainer(
            new ImageFromDockerfile("pg5100_8015")
                    .withFileFromPath("target/pg5100_8015-exec.jar",
                            Paths.get("target/pg5100_8015-exec.jar"))
                    .withFileFromPath("Dockerfile", Paths.get("Dockerfile")))
            .withExposedPorts(8080)
            .withNetwork(network)
            .withNetworkAliases(MOVIE_ALIAS)
            .waitingFor(Wait.forHttp("/"));
            //.withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("QUIZGAME")));

    public static BrowserWebDriverContainer browser = (BrowserWebDriverContainer) new BrowserWebDriverContainer()
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null)
            .withNetwork(network)
            ;



    @BeforeAll
    public static void startDocker(){
        postgres.start();
        spring.start();
        browser.start();
    }

    @AfterAll
    public static void stopDocker(){
        browser.stop();
        spring.stop();
        postgres.stop();
    }


    @Override
    protected WebDriver getDriver() {
        return browser.getWebDriver();
    }

    @Override
    protected String getServerHost() {
        return MOVIE_ALIAS;
    }

    @Override
    protected int getServerPort() {
        return 8080;
    }
}
