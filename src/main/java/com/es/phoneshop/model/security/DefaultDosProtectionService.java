package com.es.phoneshop.model.security;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static final long THRESHOLD = 20;
    private Timer countMapCleaner;

    private Map<String, Long> countMap;

    private DefaultDosProtectionService() {
        countMap = new ConcurrentHashMap<>();
        countMapCleaner = new Timer(60 * 1000, event -> countMap.clear());
        countMapCleaner.start();
    }

    private static class SingletonHelper {
        private static final DefaultDosProtectionService INSTANCE = new DefaultDosProtectionService();
    }

    public static DefaultDosProtectionService getInstance() {
        return DefaultDosProtectionService.SingletonHelper.INSTANCE;
    }

    @Override
    public boolean isAllowed(String ip) {
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1L;
        } else if (count > THRESHOLD) {
            return false;
        } else {
            count++;
        }
        countMap.put(ip, count);
        return true;
    }
}
