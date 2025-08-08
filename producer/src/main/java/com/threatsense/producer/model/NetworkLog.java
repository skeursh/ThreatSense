package com.threatsense.producer.model;

import java.time.Instant;

public class NetworkLog {
    public String srcIP;
    public String destIP;
    public int port;
    public String protocol;
    public int bytes;
    public Instant timestamp;
}
