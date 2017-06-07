package it.drivek.cars

import it.drive.cars.leads.LeadStatusTransition

/**
 * Created by stefano on 04/06/17.
 */
class Lead {

    String status
    Date date
    Date purchaseDate
    Car car
    Person person

    static constraints = {
        status inList: LeadStatusTransition.statusList
        purchaseDate nullable:true
    }
}
