package com.threatsense.producer;
import com.threatsense.producer.service.LogGeneratorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@SpringBootApplication
public class ProducerApplication {
    private final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    
	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Bean
    ScheduledExecutorService scheduler(LogGeneratorService svc) {
        exec.scheduleAtFixedRate(svc::sendRandom, 0, 1000, MILLISECONDS);
        return exec;
    }

}
