package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Location;

import java.util.List;

public interface LocationRepository {
    Location createIfNotExist(String cellName);

    List<Location> getAll();

    int countLoads(String cellName);

    List<Location> getAllWithLoads();
}
