package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Location;

import java.util.List;

public interface LocationRepository {
    boolean createIfNotExist(String locationName);

    int countLoads(String locationName);

    List<Location> getAllWithLoads();
}
