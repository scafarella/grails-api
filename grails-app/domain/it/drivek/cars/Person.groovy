package it.drivek.cars

/**
 * Created by stefano on 04/06/17.
 */
class Person {

    static constrains = {
        phone blank: false, size: 6..20
        name blank: false, size: 2..20
        surname blank: false, size: 2..20
    }

    String name
    String surname
    String phone
}
