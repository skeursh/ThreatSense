package com.threatsense.consumer.controller;

import com.threatsense.consumer.db.LogEntity;
import com.threatsense.consumer.db.LogRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping
    public Flux<LogEntity> getAllLogs() {
        return logRepository.findAll();
    }
}
