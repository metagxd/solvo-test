package com.metagxd.solvotest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(value = XmlAccessType.PROPERTY)
public class Location extends BaseEntity {

    public Location() {
        super();
    }

    private String name;

    private List<Load> loadList = new ArrayList<>();

    public Location(String name) {
        super(0);
        this.name = name;
    }

    @XmlElement(name = "load")
    public List<Load> getLoadList() {
        return loadList;
    }

    public void setLoadList(List<Load> loadList) {
        this.loadList = loadList;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public Location(int id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Location location = (Location) o;
        return name.equals(location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", loadList=" + loadList +
                "} " + super.toString();
    }
}
