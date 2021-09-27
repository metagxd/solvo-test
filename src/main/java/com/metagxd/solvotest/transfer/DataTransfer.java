package com.metagxd.solvotest.transfer;

import java.util.List;

public interface DataTransfer<T> {
    void saveToFile(String fileName, List<T> objectsList);
}
