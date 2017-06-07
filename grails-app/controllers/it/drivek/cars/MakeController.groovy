package it.drivek.cars

import grails.converters.JSON

/**
 * This class exposes REST method to perform aggregation operations against {@link Car#make} fields
 */
class MakeController {

    def carService

    /**
     * This method extracts all the distinct make from the database
     *
     * @return a list of String
     */
    def getAll(){
        render carService.getAllMake() as JSON
    }
}
