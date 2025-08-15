package com.threatsense.consumer.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "logs")
public class LogEntity {
    @Id
    public String id;

    public String srcIP;
    public String destIP;
    public int port;
    public String protocol;
    public long bytes;
    public Instant timestamp;
}
