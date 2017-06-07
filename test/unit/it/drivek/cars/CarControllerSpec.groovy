package it.drivek.cars

import grails.test.mixin.TestFor
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * Created by stefano on 07/06/17.
 */
@TestFor(CarController)
class CarControllerSpec extends Specification{

    void setup(){
        def carService = mockFor(CarService)
        carService.demand.getSuggestion { Car c -> getCars()}
        controller.carService = carService.createMock()
    }

    def "test get suggestion error for a car" (){
        given: "a missing carId"

        when: "create lead request"
            controller.suggest()
        then: "i get a 400 response"
        assert response.status == HttpStatus.BAD_REQUEST.value()
    }

    def "test get suggestion for a car" (){
        given: "a carId"
            params.carId = 3
        when: "create lead request"
        controller.suggest()
        then: "i get a 400 response"
        assert response.status == HttpStatus.OK.value()
        assert  response.json.size() == 2
    }

    def getCars(){
        return [
                new Car(carId:1),
                new Car(carId: 2)
        ]
    }
}
