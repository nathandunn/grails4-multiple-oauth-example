package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured('permitAll')
class PersonController {

    PersonService personService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured('isAuthenticated()')
//    @Secured(["ROLE_ADMIN"])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def personCount = personService.count()
        println "persons: ${personCount}"
        def personList = Person.all
        println "person list: ${personList}"
//        respond personService.listPerson(params), model:[personCount: personService.count()]
        respond personList, model:[personCount: personService.count()]
    }

//    @Secured(["ROLE_ADMIN","ROLE_USER"])
    def show(Long id) {
        respond personService.get(id)
    }

    def create() {
        respond new Person(params)
    }

    def save(Person person) {
        if (person == null) {
            notFound()
            return
        }

        try {
            personService.save(person)
        } catch (ValidationException e) {
            respond person.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), person.id])
                redirect person
            }
            '*' { respond person, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond personService.get(id)
    }

    def update(Person person) {
        if (person == null) {
            notFound()
            return
        }

        try {
            personService.save(person)
        } catch (ValidationException e) {
            respond person.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), person.id])
                redirect person
            }
            '*'{ respond person, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        personService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
