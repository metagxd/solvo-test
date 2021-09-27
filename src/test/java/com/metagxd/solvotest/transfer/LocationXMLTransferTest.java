package com.metagxd.solvotest.transfer;

import com.metagxd.solvotest.model.Location;
import com.metagxd.solvotest.repository.LocationRepository;
import com.metagxd.solvotest.repository.LocationRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

class LocationXMLTransferTest {

    private final DataTransfer<Location> dataTransfer = new LocationXMLTransfer();
    LocationRepository locationRepository = new LocationRepositoryImpl();

    @Test
    void test() {
        List<Location> locationList = locationRepository.getAllWithLoads();
        dataTransfer.saveToFile("",locationList);
    }
}