package demo

import grails.gorm.services.Service

@Service(Person)
interface PersonService {

    Person save(String username)

    Person findByUsername(String username)

    Number count()
}
