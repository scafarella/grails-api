package it.drive.cars.load

import grails.converters.JSON
import it.drive.cars.costants.CarCategories
import it.drivek.cars.Car
import it.drivek.cars.Category
import org.codehaus.groovy.grails.web.json.JSONElement

class CarImporter {
    private final File sourceFile

    CarImporter(String sourcePath) {
        this.sourceFile = new File(sourcePath)
    }

    def getAll()  {

        String content = sourceFile.getText('UTF-8')
        JSONElement jsonContent = JSON.parse(content)
        jsonContent.each {carJSON ->
            JSONElement attrsJSON = carJSON.get("attrs")
            def car = new Car(
                    carId: attrsJSON.get("carId"),
                    make: attrsJSON.get("make"),
                    model: attrsJSON.get("model"),
                    submodel: attrsJSON.get("submodel"),
                    year: attrsJSON.get("year"),
                    img: attrsJSON.get("img")
            )
            JSONElement tagsJSON = carJSON.get("tags")
            def internalSpaceCategory = Category.findByNameAndValue( CarCategories.INTERNAL_SPACE, tagsJSON.get(CarCategories.INTERNAL_SPACE))
            if(internalSpaceCategory == null){
                internalSpaceCategory = new Category(name: CarCategories.INTERNAL_SPACE, value: tagsJSON.get(CarCategories.INTERNAL_SPACE))
                internalSpaceCategory.save(failOnError:true, flush:true)
            }

            def segmentCategory = Category.findByNameAndValue(CarCategories.SEGMENT, tagsJSON.get(CarCategories.SEGMENT))
            if(segmentCategory == null){
                segmentCategory = new Category(name: CarCategories.SEGMENT, value:tagsJSON.get(CarCategories.SEGMENT))
                segmentCategory.save(failOnError:true, flush:true)
            }
            def fuelTypeCategory = Category.findByNameAndValue(CarCategories.FUEL_TYPE, tagsJSON.get(CarCategories.FUEL_TYPE))
            if(fuelTypeCategory == null){
                fuelTypeCategory = new Category(name: CarCategories.FUEL_TYPE, value: tagsJSON.get(CarCategories.FUEL_TYPE))
                fuelTypeCategory.save(failOnError:true, flush:true)

            }
            def lookCategory = Category.findByNameAndValue(CarCategories.LOOK, tagsJSON.get(CarCategories.LOOK))
            if(lookCategory == null){
                lookCategory = new Category(name: CarCategories.LOOK, value: tagsJSON.get(CarCategories.LOOK))
                lookCategory.save(failOnError:true, flush:true)
            }
            def priceCategory = Category.findByNameAndValue(CarCategories.PRICE, tagsJSON.get(CarCategories.PRICE))
            if(priceCategory == null){
                priceCategory = new Category(name: CarCategories.PRICE, value: tagsJSON.get(CarCategories.PRICE))
                priceCategory.save(failOnError:true, flush:true)
            }
            car.addToAttributes(internalSpaceCategory)
            car.addToAttributes(segmentCategory)
            car.addToAttributes(fuelTypeCategory)
            car.addToAttributes(lookCategory)
            car.addToAttributes(priceCategory)
            car.save(failOnError:true)
        }
    }
}