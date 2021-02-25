package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["ROLE_USER"])
//@Secured('permitAll')
class ApiController extends RestfulController<Book>{

    static responseFormats = ['json', 'xml']
//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",index:"GET"]
    ApiController() {
        super(Book)
    }

    def index() {
        println "request: ${request}"
        println "request JSON: ${request.JSON}"
        println "params : ${params}"
        response.setHeader("Access-Control-Allow-Headers",'Origin, X-Requested-With, Content-Type, Accept, Authorization,  X-PINGOTHER\'')
        response.setHeader("Access-Control-Allow-Credentials","true")
        respond Book.all
    }
}
