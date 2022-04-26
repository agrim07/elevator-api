package org.genesis.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDENTIFIER", updatable = false, nullable = false)
    private Long identifier;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOCATION")
    private String location;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="BUILDING_ID", nullable=false)
    private List<Elevator> elevators;

    public Building() {   }

    public Building(Long identifier, String name, String location, List<Elevator> elevators) {
        this.identifier = identifier;
        this.name = name;
        this.location = location;
        this.elevators = elevators;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(List<Elevator> elevators) {
        this.elevators = elevators;
    }

    public void addElevators(Elevator elevator) {
        if(null == this.elevators) {
            this.elevators = new ArrayList<>();
        }
        this.elevators.add(elevator);
    }

    @Override
    public String toString() {
        return "Building{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", elevators=" + elevators +
                '}';
    }
}
