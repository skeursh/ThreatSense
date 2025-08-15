package com.threatsense.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.threatsense.consumer.db.AlertEntity;
import com.threatsense.consumer.db.AlertRepository;
import com.threatsense.consumer.db.LogEntity;
import com.threatsense.consumer.db.LogRepository;
import com.threatsense.model.NetworkLog;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class LogConsumerService {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final LogRepository logRepo;
    private final AlertRepository alertRepo;

    public LogConsumerService(LogRepository logRepo, AlertRepository alertRepo) {
        this.logRepo = logRepo;
        this.alertRepo = alertRepo;
    }

    @KafkaListener(topics = "network-logs", groupId = "threatsense-consumer")
    public void consume(String json) throws Exception {
        NetworkLog log = mapper.readValue(json, NetworkLog.class);

        // Save raw log
        logRepo.save(toEntity(log)).subscribe();

        // Rule check
        String reason = ruleReason(log);
        if (reason != null) {
            alertRepo.save(toAlert(log, reason)).subscribe();
            System.out.println("[ALERT] " + json);
        } else {
            System.out.println("[OK]    " + json);
        }
    }

    /* Utilities */

    private static final Set<Integer> SUSPICIOUS_PORTS = Set.of(3389, 23, 445, 135);

    private String ruleReason(NetworkLog log) {
        if (SUSPICIOUS_PORTS.contains(log.port)) return "SUSPICIOUS_PORT_" + log.port;
        if (log.bytes > 500_000) return "LARGE_TRANSFER";
        return null;
    }

    private LogEntity toEntity(NetworkLog log) {
        LogEntity e = new LogEntity();
        e.srcIP = log.srcIP;
        e.destIP = log.destIP;
        e.port = log.port;
        e.protocol = log.protocol;
        e.bytes = log.bytes;
        e.timestamp = log.timestamp != null ? log.timestamp : Instant.now();
        return e;
    }

    private AlertEntity toAlert(NetworkLog log, String reason) {
        AlertEntity a = new AlertEntity();
        a.srcIP = log.srcIP;
        a.destIP = log.destIP;
        a.port = log.port;
        a.reason = reason;
        a.createdAt = Instant.now();
        return a;
    }
}
