package com.threatsense.consumer.controller;

import com.threatsense.consumer.db.AlertEntity;
import com.threatsense.consumer.db.AlertRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertRepository AlertRepository;

    public AlertController(AlertRepository AlertRepository) {
        this.AlertRepository = AlertRepository;
    }

    @GetMapping
    public Flux<AlertEntity> getAllAlerts() {
        return AlertRepository.findAll();
    }
}
