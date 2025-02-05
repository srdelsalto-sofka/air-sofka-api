package ec.com.airsofka;

import ec.com.airsofka.user.queries.query.GetByElementQuery;
import ec.com.airsofka.user.queries.usecases.FrequentUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication{
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

/*
    @Autowired
    private FrequentUserUseCase frequentUserUseCase;

    @Override
    public void run(String... args) throws Exception {
        frequentUserUseCase.accept(new GetByElementQuery("2e09a545-b954-4236-9753-d70f2b24d829"));
    }*/
}