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

//    def index() { }
}
