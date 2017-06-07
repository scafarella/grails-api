class BootStrap {

    def quoteImportService
    def carImportService

    def init = { servletContext ->
        quoteImportService.initialize()
        carImportService.initialize()

    }
    def destroy = {
    }
}
