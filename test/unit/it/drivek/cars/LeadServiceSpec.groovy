package it.drivek.cars

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import it.drive.cars.costants.LeadStatus
import org.apache.commons.logging.LogFactory
import spock.lang.Specification

/**
 * Created by stefano on 05/06/17.
 */
@TestFor(LeadService)
@Mock([Person,Lead,Car])
class LeadServiceSpec extends Specification {

    def log = LogFactory.getLog(getClass())

    def leadToBeUpdated = new Lead(
            id: 1,
            status: LeadStatus.CREATED,
            person: new Person(name:" NAME", surname: "SURNAME", phone: "MYPHONE"),
            date: new Date(),
            car: new Car(carId: "1")
    )

    void setup(){
        def carService = mockFor(CarService)
        carService.demand.findByCarId { String q -> new Car(carId: "1")}
        service.carService = carService.createMock()

        Lead.metaClass.static.get = {
            Long id -> leadToBeUpdated
        }

    }

    def "test create of a Lead" (){
        given: "a lead"
            def c = new Car(carId: "1")
            def p = new Person(name:" NAME", surname: "SURNAME", phone: "MYPHONE")
            def lead = new Lead(
                    "car": c,
                    "person": p,
            )
        when: "create lead request"
            def storedLead = service.create(lead)
        then: "check created lead status"
            assert storedLead.getStatus() == LeadStatus.CREATED
    }

    def "test update of a Lead" (){
        given: "a lead"
            def c = new Car(carId: "1")
            def lead = new Lead(
                    car: c,
                    status: LeadStatus.VALID
            )
            lead.setId(1)
        when: "update lead request"

            def storedLead = service.update(lead)
        then: "check created lead status"
            assert storedLead.getStatus() == LeadStatus.VALID
    }

    def "test update of a VALID Lead" (){
        given: "a lead"
            def c = new Car(carId: "1")
            def lead = new Lead(
                    car: c,
                    purchaseDate: new Date(),
                    status: LeadStatus.SOLD
            )
            lead.setId(1)
            leadToBeUpdated.setStatus(LeadStatus.VALID)
        when: "update lead request"
            def storedLead = service.update(lead)
        then: "check created lead status"
            assert storedLead.getStatus() == LeadStatus.SOLD
            assert storedLead.getPurchaseDate() != null
    }

}
