package com.threatsense.consumer.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "alerts")
public class AlertEntity {
    @Id
    public String id;

    public String srcIP;
    public String destIP;
    public int port;
    public String reason;
    public Instant createdAt;
}
