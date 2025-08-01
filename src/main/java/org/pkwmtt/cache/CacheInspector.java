package org.pkwmtt.cache;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.pkwmtt.timetable.CacheableTimetableService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CacheInspector {

    private final CacheManager cacheManager;
    private final CacheableTimetableService service;


    public Map<Object, Object> getAllEntries(String cacheName) {
        CaffeineCache springCache = (CaffeineCache) cacheManager.getCache(cacheName);

        if (springCache == null)
            throw new IllegalArgumentException("No cache with name " + cacheName);

        Cache<Object, Object> nativeCache = springCache.getNativeCache();

        return nativeCache.asMap();
    }

    public void printAllEntries(String cacheName) {
        service.getListOfHours();
        service.getGeneralGroupSchedule("12K1");
        service.getGeneralGroupsList();

        getAllEntries(cacheName).forEach((key, value) ->
            System.out.println("Cache[" + cacheName + "] " + key + " -> " + value)
        );
    }
}
