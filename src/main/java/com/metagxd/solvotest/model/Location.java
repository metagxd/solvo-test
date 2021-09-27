package com.metagxd.solvotest.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(value = XmlAccessType.PROPERTY)
public class Location extends BaseEntity {

    public Location() {
        super();
    }

/*    @NotBlank
    @Size(max = 32, message = "length of name must be between 1 and 32 symbols!")
    @Pattern(regexp = "[A-Za-z0-9]")*/
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
}
