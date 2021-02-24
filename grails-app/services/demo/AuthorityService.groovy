package demo

import grails.gorm.services.Service

@Service(Authority)
interface  AuthorityService {

    Authority save(String authority)

    Authority findByAuthority(String authority)
}
