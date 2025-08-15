package com.threatsense.producer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threatsense.model.NetworkLog;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.time.Instant;
import java.util.Random;

@Service
public class LogGeneratorService {
    private final KafkaTemplate<String, String> kafka;
private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Random rnd = new Random();
    private final String topic = "network-logs";

    public LogGeneratorService(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    public void sendRandom() {
        try {
            NetworkLog log = new NetworkLog();
            log.srcIP = "10.0.0." + rnd.nextInt(256);
            log.destIP = (8 + rnd.nextInt(200)) + "." + rnd.nextInt(256) + "." + rnd.nextInt(256) + "." + rnd.nextInt(256);
            log.port = new int[]{22,80,443,3389}[rnd.nextInt(4)];
            log.protocol = rnd.nextDouble() < 0.85 ? "TCP" : "UDP";
            log.bytes = 200 + rnd.nextLong(20000);
            log.timestamp = Instant.now();

            String json = mapper.writeValueAsString(log);
        System.out.println("[SENT] " + json);

        kafka.send(topic, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
