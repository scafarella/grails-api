package it.drivek.cars


/**
 * Created by stefano on 04/06/17.
 */
class Category {

    static constraints = {
        name(unique: ['value'])
    }

    String name
    String value

    static hasMany = [cars: Car]
}

