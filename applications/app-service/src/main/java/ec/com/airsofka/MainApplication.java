package ec.com.airsofka;

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