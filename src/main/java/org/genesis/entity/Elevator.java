package org.genesis.entity;

import javax.persistence.*;

@Entity
public class Elevator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDENTIFIER", updatable = false, nullable = false)
    private Long identifier;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FLOORS")
    private Integer floors;

    @Column(name = "CURRENTFLOOR")
    private Integer currentFloor;

    @Column(name = "STATUS")
    private String status;

    public Elevator() {   }

    public Elevator(Long identifier, String name, Integer floors, Integer currentFloor, String status) {
        this.identifier = identifier;
        this.name = name;
        this.floors  = floors;
        this.currentFloor = currentFloor;
        this.status = status;
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

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", floors=" + floors +
                ", currentFloor=" + currentFloor +
                ", status='" + status + '\'' +
                '}';
    }
}
