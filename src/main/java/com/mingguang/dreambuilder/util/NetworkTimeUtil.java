package com.mingguang.dreambuilder.util;

import java.sql.Timestamp;
import java.time.Instant;

public class NetworkTimeUtil {
    public static Timestamp getNetworkTime() {
        return Timestamp.from(Instant.now());
    }
}