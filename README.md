**AssignmentK**
###How to run
test: grails test-app

run: grails run-app -Dgrails.server.port.http=8090

###Test REST API
Pleas use the postman file provided to try the API exposed (AssignmentK.postman_collection.json)

###Design overview
The project structure follows grails conventions:
* Domain:
    * Car: represent a car with its major fields (carId, make, model, submodel, year, img).
       it's in m:n relation with the entity Category
    * Category: entity that represent all possible values that a particular category can have. it has name and 
        value fields
    * Lead: represent a lead. it holds a 1:1 relation with both car and person    
    * Person: represent customer personal details that is creating a lead
* Service:
    * CarService: it provides logic to extract the requested cars
    * LeadService: it provides logic to create, update and validate a lead
* Controller:
    * MakeController: perform aggregation over make, that is a Car field
    * LeadController: perform create and update of a new Lead
    * CarController: perform search operation on Car entity
    
For any implementation related details please refer the javadoc in single files    