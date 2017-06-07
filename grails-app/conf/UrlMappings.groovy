class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/dante/say"(controller: "quote") {
            action = [GET: "random"]
        }

        "/makes"(controller: "make"){
            action = [GET: "getAll"]
        }

        "/cars"(controller: "car"){
            action = [GET: "getAll"]
        }

        "/suggest"(controller: "car"){
            action = [GET: "suggest"]
        }

        "/leads"(controller: "lead"){
            action = [POST: "create"]
        }

        "/leads/$leadId"(controller: "lead"){
            action = [PUT: "update"]
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
