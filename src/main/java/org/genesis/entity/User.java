package org.genesis.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDENTIFIER", updatable = false, nullable = false)
    private Long identifier;

    @Column(name = "NAME")
    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="USER_ID", nullable=false)
    private List<Building> buildings;

    public User() {   }

    public User(Long identifier, String name, List<Building> buildings) {
        this.identifier = identifier;
        this.name = name;
        this.buildings = buildings;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public void addBuilding(Building building) {
        if(null == this.buildings) {
            this.buildings = new ArrayList<>();
        }
        this.buildings.add(building);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", buildings=" + buildings +
                '}';
    }
}
