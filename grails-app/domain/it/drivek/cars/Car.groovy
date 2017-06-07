package it.drivek.cars

class Car {

    static constraints = {
        carId unique:true
    }

    String carId
    String make
    String model
    String submodel
    String year
    String img

    static hasMany = [attributes: Category]
    static belongsTo = Category
}
