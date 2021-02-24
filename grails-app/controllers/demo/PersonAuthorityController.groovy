package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured('permitAll')
class PersonAuthorityController {

    PersonAuthorityService personAuthorityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def personAuthorityList = PersonAuthority.all
        println "personAuthority list A"
        println personAuthorityList
        println "count: ${personAuthorityList.size()}"
//        respond personAuthorityService.list(params), model:[personAuthorityCount: personAuthorityService.count()]
        respond personAuthorityList, model:[personAuthorityCount: personAuthorityService.count()]
    }

    def show(Long id) {
        respond personAuthorityService.get(id)
    }

    def create() {
        respond new PersonAuthority(params)
    }

    def save(PersonAuthority personAuthority) {
        if (personAuthority == null) {
            notFound()
            return
        }

        try {
            personAuthorityService.save(personAuthority)
        } catch (ValidationException e) {
            respond personAuthority.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'personAuthority.label', default: 'PersonAuthority'), personAuthority.id])
                redirect personAuthority
            }
            '*' { respond personAuthority, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond personAuthorityService.get(id)
    }

    def update(PersonAuthority personAuthority) {
        if (personAuthority == null) {
            notFound()
            return
        }

        try {
            personAuthorityService.save(personAuthority)
        } catch (ValidationException e) {
            respond personAuthority.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'personAuthority.label', default: 'PersonAuthority'), personAuthority.id])
                redirect personAuthority
            }
            '*'{ respond personAuthority, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        personAuthorityService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'personAuthority.label', default: 'PersonAuthority'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'personAuthority.label', default: 'PersonAuthority'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
