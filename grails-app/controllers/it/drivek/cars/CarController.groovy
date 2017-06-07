package it.drivek.cars

import grails.converters.JSON
import org.springframework.http.HttpStatus

/**
 * This class exposes REST method to perform query operations against {@link Car}
 */
class CarController {

    def carService

    /**
     * This method get all cars stored in database filtered using the optional submitted parameters
     *
     * @return a List of {@link Car}
     */
    def getAll(){

        String make = params.containsKey('make')? params.make : null
        String year =  params.containsKey('year')? params.year : null
        String model =  params.containsKey('model')? params.model : null
        render carService.findByMakeAndModelAndYear(make, model, year) as JSON
    }

    /**
     *  This method return a list of car similar to the submitted one.
     *  This means that returns a configurable number of car with the same segment of the provided one.
     *
     * @return a List of {@link Car}
     */
    def suggest(){
        if(params.containsKey("carId")){
           def car = new Car(carId: params.get("carId"))
           render carService.getSuggestion(car) as JSON
        }else{
            render(status: HttpStatus.BAD_REQUEST.value(), text: "missing mandatory parameter 'carId'")
        }
    }
}
