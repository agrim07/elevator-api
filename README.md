# Machine health processor Service

This is a service that is responsible for handling a fictional building's Elevator
operations. Think of it kinda like Uber for Elevators. User of the service should be able to
summon an elevator for a particular building they are in. And then interact with that elevator
through API requests. Once in the elevator, they should be able to select a floor for the elevator
to take them to. Its using SQLLite in-memory database used to store User, Building and Elevator 
details in the DB tables.

This API implemented by using API-First approach and the contract of this api is 
at location src\main\resources\openapi\api.yaml.

###This application having two end-points:

1) /v1/user - This end-point takes input user, its building and elevator details and insert/update 
   records in the database tables. It insert new records and updates existing one.
   Request Format: 

   {
   "name": "Amaresh Kumar",
   "buildings": [{"name": "A1", "location": "Delhi", "elevators": [{"name": "Z1", "floors": "20", "currentFloor": "16", "status": "STOPPED"}]}]
   } 
2) /user/{userId}/buildings - This end-point takes user id as input and return this user all building details.
   Example: /v1/user/2/buildings
3) /user/{userId}/buildings/{buildingId} - This end-point return details of a particular building and takes user is and building id as input parameter.
4) /user/{userId}/buildings/{buildingId}/summons-elevator/{userFloorId} - This end-point returns an elevator of the building which is nearest to user floor.
   Response Format:
   {
   "code": 200,
   "type": "OK",
   "message": "Use this Elevator.",
   "elevator": {
   "identifier": 3,
   "name": "Z1",
   "floors": 20,
   "currentFloor": 16,
   "status": "DOWN"
   }
   }