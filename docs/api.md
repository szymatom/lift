### GET outside

##### URI Query Params
Param | Description
------|------------
**``floor``** | (Optional) Number of floor at which button was pressed. Default value is 0.

##### Examples
* Sample request: `http://localhost:8080/outside?floor=5`
* Sample response
    * Status: 200, Success
    * Body:`Call button pressed: floor 5`

* Sample request: `http://localhost:8080/outside?floor=-1`
* Sample response
    * Status: 400, Bad Request

### GET inside

##### URI Query Params
Param | Description
------|------------
**``floor``** | (Optional) Number of floor where lift should go. Default value is 0.

##### Examples
* Sample request: `http://localhost:8080/inside?floor=6`
* Sample response
    * Status: 200, Success
    * Body: `Elevator car floor button pressed: 6`

* Sample request: `http://localhost:8080/inside?floor=35`
* Sample response
    * Status: 400, Bad Request    