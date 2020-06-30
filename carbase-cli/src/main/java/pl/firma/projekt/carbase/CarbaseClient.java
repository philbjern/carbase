package pl.firma.projekt.carbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarbaseClient implements CommandLineRunner {

    @Autowired
    ApplicationMenu applicationMenu;

    private static Logger LOG = LoggerFactory.getLogger(CarbaseClient.class);

    public static void main(String[] args) {
        SpringApplication.run(CarbaseClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Starting Carbase Client application");
        applicationMenu.run();
    }

}