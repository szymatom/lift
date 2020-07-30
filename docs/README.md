[![Build Status](https://travis-ci.org/szymatom/lift.svg?branch=master)](https://travis-ci.org/szymatom/lift)
## Lift model
This is simple lift model using Spring and Tomcat where REST requests are used to simulate call/lift button presses.
All the actions performed by elevator are presented in log (console and file). 
Default values of parameters:
```
speed = 4000 //time in milliseconds to get one floor up or down
numberOfFloors = 10
port = 8080
```

### Usage:
`java -jar lift-0.0.1-SNAPSHOT.jar`

### API
REST Method | Rest Endpoint | Description
------------|---------------|------------|
GET | [``/outside``](api.md#get-outside) | [Endpoint to emulate presses of call elevator buttons](api.md#get-outside)
GET | [``/inside``](api.md#get-inside) | [Endpoint to emulate presses of floor buttons inside the cabin](api.md#get-inside)

