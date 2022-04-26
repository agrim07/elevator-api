package org.genesis.delegator;

import io.swagger.annotations.ApiParam;
import org.genesis.api.UserApiDelegate;
import org.genesis.domain.*;
import org.genesis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserApiDelegateImpl implements UserApiDelegate {

    public static final Logger log = LoggerFactory.getLogger(UserApiDelegateImpl.class);

    private UserService userService;

    public UserApiDelegateImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ModelApiResponse> createUser(UserRequest request) {
        log.info("Request createUser---------------->{}", request);
        this.userService.createUser(request);
        return successfulResponse();
    }

    @Override
    public ResponseEntity<BuildingResponse> buildings(Long userId) {
        log.info("Request Buildings for User ---------------->{}", userId);
        List<BuildingObject> buildingRequestList = this.userService.fetchUserBuildings(userId);
        BuildingResponse buildingResponse = new BuildingResponse();
        buildingResponse.setCode(HttpStatus.OK.value());
        buildingResponse.setType(HttpStatus.OK.name());
        buildingResponse.setBuildings(buildingRequestList);
        buildingResponse.setMessage("User linked buildings returned successfully.");
        return new ResponseEntity<>(buildingResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BuildingResponse> building(Long userId,
                                                     Long buildingId) {
        log.info("Request Building for User {} and Building {}", userId, buildingId);
        List<BuildingObject> buildingRequestList = this.userService.fetchUserBuildings(userId, buildingId);
        BuildingResponse buildingResponse = new BuildingResponse();
        buildingResponse.setCode(HttpStatus.OK.value());
        buildingResponse.setType(HttpStatus.OK.name());
        buildingResponse.setBuildings(buildingRequestList);
        buildingResponse.setMessage("User linked and ordered building returned successfully.");
        return new ResponseEntity<>(buildingResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ElevatorResponse> summonsElevator(Long userId,
                                                            Long buildingId,
                                                            Long userFloorId){
        log.info("Request Elevator for User {} and Building {} from Floor {}", userId, buildingId, userFloorId);
        ElevatorObject elevatorObject = this.userService.summonElevator(userId, buildingId, userFloorId);
        log.info("elevatorObject--->"+elevatorObject);
        ElevatorResponse elevatorResponse = new ElevatorResponse();
        elevatorResponse.setCode(HttpStatus.OK.value());
        elevatorResponse.setType(HttpStatus.OK.name());
        elevatorResponse.setElevator(elevatorObject);
        elevatorResponse.setMessage("Use this Elevator.");
        return new ResponseEntity<>(elevatorResponse, HttpStatus.OK);
    }

    private ResponseEntity<ModelApiResponse> successfulResponse() {
        ModelApiResponse response = new ModelApiResponse();
        response.setCode(HttpStatus.OK.value());
        response.setType(HttpStatus.OK.name());
        response.setMessage("User created successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
