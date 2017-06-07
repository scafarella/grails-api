package it.drivek.cars

/**
 * This class is the main service that let perform search operation against the {@link Car} entity.
 */
class CarService {

    def grailsApplication

    static CATEGORY_NAME = 'Segment'

    /**
     * Method that find all the distinct {@link Car#make} from {@link Car}
     *
     * @return a list of String
     */
    List<String> getAllMake(){
        def criteria = Car.createCriteria()
        def makes = criteria.list {
            projections {
                distinct('make')
            }
        }
        return makes
    }

    /**
     * Method that find all the distinct {@link Car#make} from {@link Car} using the optional filter params.
     *
     * @param make
     * @param model
     * @param year
     * @return a list of {@link Car}
     */
    List<Car> findByMakeAndModelAndYear(make=null, model=null, year=null){
        return Car.createCriteria().list {
            and {
                if(make){
                    eq("make", make)
                }
                if(model){
                    eq("model", model)
                }
                if(year){
                    eq("year", year)
                }
            }
        }
    }

    Car findByCarId(carId){
        return Car.findByCarId(carId)
    }

    /**
     * Extracts cars based on the catergory with {@link Category#name} {@link CarService#CATEGORY_NAME} and the same
     * {@link Category#value} as the input car
     *
     * @param car
     * @return
     */
    List<Car> getSuggestion(final car){
        def storedCar = findByCarId(car.getCarId())
        if(storedCar !=null ){
            return Car.executeQuery(
                    'select c from Car as c ' +
                            ' where c IN ' +
                            '(select c from Car as c join c.attributes as attr where attr in (' +
                            'select attr from Car as c join c.attributes as attr where c.id=:id and attr.name=:name))',
                    [id: storedCar.getId(), name: CATEGORY_NAME],
                    [max: grailsApplication.config.cars.maxSuggestions])
        }else{
            return []
        }

    }

}
