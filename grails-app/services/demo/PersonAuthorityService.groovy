package demo

import grails.gorm.services.Service

@Service(PersonAuthority)
interface  PersonAuthorityService {

    PersonAuthority save(Person person, Authority authority)

    Number count()
}
