package it.drivek.cars

import grails.converters.JSON

/**
 * This class exposes REST method to perform CRUD operations against {@link Lead}
 *
 */
class LeadController {

    def leadService

    /**
     * Method that create a new Lead from the submitted json
     *
     * @return the new created Lead object or the validation errors if it fails
     */
    def create(){
        def jsonObject = request.JSON
        def lead = new Lead(jsonObject)
        leadService.validateCreate(lead)
        if(lead.hasErrors()){
            render lead.errors.allErrors as JSON
        }else {
            render leadService.create(lead) as JSON
        }
    }

    /**
     * Method that update the status of Lead. If the new status is {@link it.drive.cars.costants.LeadStatus#SOLD} it also store the
     * {@link Lead#purchaseDate} with the format 'YYYY-MM-DD HH:mm:ss.S' from the purchaseDate of the request
     *
     * @return the uopdated Lead object or the validation errors if it fails
     */
    def update(){
        def jsonObject = request.JSON
        def lead = new Lead(jsonObject)
        lead.setId(params.long("leadId"))
        leadService.validateUpdate(lead)
        if(lead.hasErrors()){
            render lead.errors.allErrors as JSON
        }else {
            render leadService.update(lead) as JSON
        }
    }
}
