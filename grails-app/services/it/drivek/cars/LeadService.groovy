package it.drivek.cars

import it.drive.cars.costants.LeadStatus
import it.drive.cars.leads.LeadStatusTransition

/**
 * This class is the main service that let perform CRUD operation against the {@link Lead} entity.
 *
 * It also provide validation method for create and update operation
 */
class LeadService {

    def carService

    def leadStatusTransition = new LeadStatusTransition()

    /**
     * Create a new {@link Lead} in the database. it sets the {@link Lead#date} fields to the current Date and set the {@link Lead#status}
     * to {@link LeadStatus#CREATED}
     *
     * @param lead
     * @return
     */
    def create(lead){
        def car = carService.findByCarId(lead.getCar().getCarId())
        lead.setDate(new Date())
        lead.setStatus(LeadStatus.CREATED)
        lead.setCar(car)
        lead.getPerson().save(flush:true)
        return lead.save(failOnError:true)
    }

    /**
     * Update the status of the submitted lead instance. It sets the new {@link Lead#status} and {@link Lead#purchaseDate}.
     *
     * @param lead
     * @return
     */
    def update(lead){
        def storedLead = Lead.get(lead.getId())
        storedLead.status = lead.getStatus()
        storedLead.purchaseDate = lead.getPurchaseDate()
        return storedLead.save(failOnError:true)
    }

    /**
     * Validate the request for a new {@link Lead}. It triggers {@link LeadService#validateCar(java.lang.Object)} and
     * {@link LeadService#validatePerson(java.lang.Object)}
     * @param lead
     * @return
     */
    def validateCreate(lead){
        validateCar(lead)
        validatePerson(lead)
        return lead
    }

    /**
     * Validate the request for a status update of a {@link Lead}. If the status is uncorrect it augments the lead
     * with proper errors fields
     * @param lead
     * @return
     */
    def validateUpdate(lead){
        def storedLead = Lead.findById(lead.getId())
        if(storedLead == null){
            lead.errors.rejectValue("id", "", "LEAD DOES NOT EXISTS")
            return
        }

        if(!leadStatusTransition.statusExists(lead.getStatus())){
            lead.errors.rejectValue("status", "", "STATUS DOES NOT EXISTS")
            return
        }

        if(!leadStatusTransition.validateTransition(storedLead.getStatus(), lead.getStatus())){
            lead.errors.rejectValue("status", "", "STATUS IS NOT VALID")
            return
        }

        if(lead.getStatus() == LeadStatus.SOLD && lead.getPurchaseDate() == null){
            lead.errors.rejectValue("purchaseDate", "", "EXPECTED PURCHASE DATE")
            return
        }

        if(lead.getStatus() != LeadStatus.SOLD && lead.getPurchaseDate() != null){
            lead.errors.rejectValue("purchaseDate", "", "UNEXPECTED PURCHASE DATE")
            return
        }

    }

    /**
     * This method check if the submitted {@link Lead} contains and existing @{link Car}.
     * If not augments the lead with proper errors fields
     *
     * @param lead
     * @return
     */
    def validateCar(lead){
        def car = carService.findByCarId(lead.getCar().getCarId())
        if (car == null) {
            lead.errors.rejectValue("car", "", "CAR DOES NOT EXISTS")
        }
    }

    /**
     * This method check if the submitted {@link Lead} contains and existing @{link Person}.
     * If not augments the lead with proper errors fields
     *
     * @param lead
     * @return
     */
    def validatePerson(lead){
        if(exists(lead)){
            lead.errors.rejectValue("person", "", "THIS LEAD ALREADY EXISTS")
        }
    }

    def exists(lead){
        def results = Lead.executeQuery(
                'from Lead as l where ' +
                        'l.person.name = :name AND l.person.surname = :surname AND ' +
                        'l.car.carId=:carId',
                [name: lead.getPerson().getName(), surname: lead.getPerson().getSurname(), carId: lead.getCar().getCarId()])
        return results.size() >0
    }
}
