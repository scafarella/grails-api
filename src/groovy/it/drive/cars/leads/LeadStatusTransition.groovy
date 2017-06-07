package it.drive.cars.leads

import it.drive.cars.costants.LeadStatus

/**
 * Created by stefano on 05/06/17.
 */
class LeadStatusTransition {

    def map = [:]
    static statusList = [
            LeadStatus.CREATED,
            LeadStatus.VALID,
            LeadStatus.CANCELED,
            LeadStatus.SOLD,
            LeadStatus.CLOSED,
            LeadStatus.INVALID
    ]

    LeadStatusTransition (){
        map[LeadStatus.CREATED] = [LeadStatus.VALID, LeadStatus.CANCELED, LeadStatus.INVALID]
        map[LeadStatus.VALID] = [LeadStatus.SOLD, LeadStatus.CLOSED]

    }

    def statusExists(status){
        return statusList.findIndexOf {it == status} >= 0
    }

    def validateTransition(from, to){
        return map.get(from).findIndexOf {it == to} >= 0
    }

}
