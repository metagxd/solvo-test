package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Location;

import java.util.List;

public interface LocationRepository {
    boolean createIfNotExist(String cellName);

    int countLoads(String cellName);

    List<Location> getAllWithLoads();
}
