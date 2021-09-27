package com.metagxd.solvotest.repository;

import com.metagxd.solvotest.model.Load;

public interface LoadRepository {
    Load create();

    int createMany(int quantity, String cellName);
}
