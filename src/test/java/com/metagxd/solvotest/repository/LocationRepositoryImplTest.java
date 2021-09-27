package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Location;
import org.junit.jupiter.api.Test;

import java.util.List;

class LocationRepositoryImplTest {

    private static LocationRepository locationRepository = new LocationRepositoryImpl();

    @Test
    void createIfNotExist() {
        locationRepository.createIfNotExist("Cell_75e");
    }

    @Test
    void getAll() {
        final List<Location> all = locationRepository.getAll();
        System.out.println(all.size());
        System.out.println(all);
    }

    @Test
    void getAllWithLoads() {
        final List<Location> allWithLoads = locationRepository.getAllWithLoads();
        System.out.println(allWithLoads.size());
    }
}