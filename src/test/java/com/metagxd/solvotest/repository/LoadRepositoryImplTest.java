package com.metagxd.solvotest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoadRepositoryImplTest {

    private static LoadRepository loadRepository = new LoadRepositoryImpl();

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
    }

    @Test
    void createMany() {
        loadRepository.createMany(4, "Cell_7");
    }
}