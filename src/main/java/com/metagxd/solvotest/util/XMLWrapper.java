package com.metagxd.solvotest.util;

import com.metagxd.solvotest.model.Location;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class-wrapper that used by {@link com.metagxd.solvotest.transfer.LocationXMLTransfer}
 * for wrapping all xml-data with custom tag "dbinfo"
 */

@XmlRootElement(name = "dbinfo")
public class XMLWrapper {
    List<Location> locations;

    public XMLWrapper() {
    }

    public XMLWrapper(List<Location> locations) {
        this.locations = locations;
    }

    @XmlElement(name = "location")
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
