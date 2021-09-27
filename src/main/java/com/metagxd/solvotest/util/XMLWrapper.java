package com.metagxd.solvotest.util;

import com.metagxd.solvotest.model.Location;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "dbinfo")
public class XMLWrapper {
    List<Location> locationList;

    public XMLWrapper() {
    }

    public XMLWrapper(List<Location> locationList) {
        this.locationList = locationList;
    }

    @XmlElement(name = "location")
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }
}
