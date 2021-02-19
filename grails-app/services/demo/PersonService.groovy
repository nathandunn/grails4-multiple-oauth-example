package demo

import grails.gorm.services.Service

@Service(Person)
interface PersonService {

    Person save(String username, String password)

    Person findByUsername(String username)
}
