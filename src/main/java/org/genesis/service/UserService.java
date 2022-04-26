package org.genesis.service;

import org.genesis.UserRepository;
import org.genesis.domain.BuildingObject;
import org.genesis.domain.ElevatorObject;
import org.genesis.domain.UserRequest;
import org.genesis.entity.Building;
import org.genesis.entity.Elevator;
import org.genesis.entity.User;
import org.genesis.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(UserRequest request) {
        User user = parseRequest(request);
        this.repository.save(user);
    }

    public List<BuildingObject> fetchUserBuildings(Long userId) {
        Optional<User> user = this.repository.findById(userId);
        List<BuildingObject> buildingRequestList = new ArrayList<>();
        if(user.isPresent()) {
            buildingRequestList = getBuildingRequests(user.get().getBuildings());
        } else {
            throw new NotFoundException("User not found.");
        }
        if(null == buildingRequestList) {
            throw new NotFoundException("Building not found.");
        }
        return buildingRequestList;
    }

    public List<BuildingObject> fetchUserBuildings(Long userId, Long buildingId) {
        Optional<User> user = this.repository.findById(userId);
        List<BuildingObject> buildingRequestList = null;
        if(user.isPresent()) {
            buildingRequestList = getBuildingRequests(user.get().getBuildings().stream().
                    filter(building -> buildingId.equals(building.getIdentifier())).
                    collect(Collectors.toList()));
        } else {
            throw new NotFoundException("User not found.");
        }
        if(null == buildingRequestList) {
            throw new NotFoundException("Building not found.");
        }
        return buildingRequestList;
    }

    public ElevatorObject summonElevator(Long userId, Long buildingId, Long userFloorId) {
        Optional<User> user = this.repository.findById(userId);
        ElevatorObject elevatorObj = null;
        Long nearestFloorDiff = Long.MAX_VALUE;
        if(user.isPresent()) {
            List<BuildingObject> buildingRequestList = getBuildingRequests(user.get().getBuildings().stream().
                    filter(building -> buildingId.equals(building.getIdentifier())).
                    collect(Collectors.toList()));
            for(ElevatorObject elevatorObject : buildingRequestList.get(0).getElevators()) {
                if(Math.abs(userFloorId - elevatorObject.getCurrentFloor()) < nearestFloorDiff
                    && !ElevatorObject.StatusEnum.OUTOFSERVICE.equals(elevatorObject.getStatus())) {
                    elevatorObj = elevatorObject;
                    nearestFloorDiff = Math.abs(userFloorId - elevatorObject.getCurrentFloor());
                }
            }
        } else {
            throw new NotFoundException("User not found.");
        }
        if(null == elevatorObj) {
            throw new NotFoundException("Requested resource not found.");
        }
        if(userFloorId < elevatorObj.getCurrentFloor()) {
            elevatorObj.setStatus(ElevatorObject.StatusEnum.DOWN);
        } else if(userFloorId > elevatorObj.getCurrentFloor()) {
            elevatorObj.setStatus(ElevatorObject.StatusEnum.UP);
        }if(userFloorId.equals(elevatorObj.getCurrentFloor())) {
            elevatorObj.setStatus(ElevatorObject.StatusEnum.STOPPED);
        }

        return elevatorObj;
    }

    private List<BuildingObject> getBuildingRequests(List<Building> buildings) {
        List<BuildingObject> buildingRequestList = new ArrayList<>();
        List<ElevatorObject> elevatorRequestList;
        BuildingObject buildingObject;
        ElevatorObject elevatorObject;
        for(Building building : buildings) {
            elevatorRequestList = new ArrayList<>();
            buildingObject = new BuildingObject();
            buildingObject.setIdentifier(building.getIdentifier());
            buildingObject.setLocation(building.getLocation());
            buildingObject.setName(building.getName());
            for(Elevator elevator : building.getElevators()) {
                elevatorObject = new ElevatorObject();
                elevatorObject.setIdentifier(elevator.getIdentifier());
                elevatorObject.setName(elevator.getName());
                elevatorObject.setFloors(elevator.getFloors());
                elevatorObject.setCurrentFloor(elevator.getCurrentFloor());
                elevatorObject.setStatus(ElevatorObject.StatusEnum.fromValue(elevator.getStatus()));
                elevatorRequestList.add(elevatorObject);
            }
            buildingObject.setElevators(elevatorRequestList);
            buildingRequestList.add(buildingObject);
        }
        return buildingRequestList;
    }

    private User parseRequest(UserRequest userRequest) {
        User user = new User();
        user.setIdentifier(userRequest.getIdentifier());
        user.setName(userRequest.getName());
        Building building;
        Elevator elevator;
        for(BuildingObject buildingObject : userRequest.getBuildings()) {
            building = new Building();
            building.setName(buildingObject.getName());
            building.setLocation(buildingObject.getLocation());
            for(ElevatorObject elevatorRequest : buildingObject.getElevators()) {
                elevator = new Elevator(null, elevatorRequest.getName(),
                        elevatorRequest.getFloors(), elevatorRequest.getCurrentFloor(), elevatorRequest.getStatus().getValue());
                building.addElevators(elevator);
            }
            user.addBuilding(building);
        }
        return user;
    }
}
