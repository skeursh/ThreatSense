package com.threatsense.model;

import java.time.Instant;

public class NetworkLog {
    public String srcIP;
    public String destIP;
    public int port;
    public String protocol;
    public long bytes;
    public Instant timestamp;
}
