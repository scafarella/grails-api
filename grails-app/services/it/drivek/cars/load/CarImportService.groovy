package it.drivek.cars.load

import it.drive.cars.load.CarImporter
import it.drivek.cars.Car
import it.drivek.quote.Quote

/**
 * Created by stefano on 01/06/17.
 */
class CarImportService {

    def grailsApplication

    void initialize() throws Exception {
        log.info("CarImportService: Starting import...");
        def sourcePath = grailsApplication.config.cars.path
        CarImporter importer = new CarImporter(sourcePath)

        importer.getAll()

    }
}
