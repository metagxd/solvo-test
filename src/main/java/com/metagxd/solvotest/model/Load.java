package com.metagxd.solvotest.model;

import com.metagxd.solvotest.util.StringGenerator;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

public class Load extends BaseEntity {

    private String name;

    public Load(int id, String name) {
        super(id);
        this.name = name;
    }

    public Load() {
        super(0);
        this.name = StringGenerator.getRandomString(32);
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Load load = (Load) o;
        return Objects.equals(name, load.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
