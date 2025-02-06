package ec.com.airsofka;

import ec.com.airsofka.user.queries.query.GetByElementQuery;
import ec.com.airsofka.user.queries.usecases.FrequentUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }


    @Autowired
    private FrequentUserUseCase frequentUserUseCase;

    @Override
    public void run(String... args) throws Exception {
        frequentUserUseCase.accept(new GetByElementQuery("94d88013-270e-41ca-963d-4419bc45ca07"));
    }
}