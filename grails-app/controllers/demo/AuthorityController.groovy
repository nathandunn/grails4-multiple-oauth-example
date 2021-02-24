package demo

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AuthorityController {

    AuthorityService authorityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond authorityService.list(params), model:[authorityCount: authorityService.count()]
    }

    def show(Long id) {
        respond authorityService.get(id)
    }

    def create() {
        respond new Authority(params)
    }

    def save(Authority authority) {
        if (authority == null) {
            notFound()
            return
        }

        try {
            authorityService.save(authority)
        } catch (ValidationException e) {
            respond authority.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'authority.label', default: 'Authority'), authority.id])
                redirect authority
            }
            '*' { respond authority, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond authorityService.get(id)
    }

    def update(Authority authority) {
        if (authority == null) {
            notFound()
            return
        }

        try {
            authorityService.save(authority)
        } catch (ValidationException e) {
            respond authority.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'authority.label', default: 'Authority'), authority.id])
                redirect authority
            }
            '*'{ respond authority, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        authorityService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'authority.label', default: 'Authority'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'authority.label', default: 'Authority'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
