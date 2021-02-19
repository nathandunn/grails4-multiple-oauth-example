//tag::staticRules[]
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]
//end::staticRules[]
//tag::grailsPluginSpringSecurityRest[]


grails {
	plugin {
		springsecurity {
			rest {
				token {
					validation {
						useBearerToken = false // <1>
						enableAnonymousAccess = true // <2>
					}
					storage {
						jwt {
							secret = 'foobar123'*4 //<3>
						}
					}
				}
				oauth {
					frontendCallbackUrl = { String tokenValue -> "http://localhost:8080/auth/success?token=${tokenValue}" } //<4>
					google {
						client = org.pac4j.oauth.client.Google2Client //<5>
						key = "${System.getenv().get('GOOGLE_KEY')}" //<6>
						secret = "${System.getenv().get('GOOGLE_SECRET')}" //<7>
						scope = org.pac4j.oauth.client.Google2Client.Google2Scope.EMAIL_AND_PROFILE //<8>
						defaultRoles = [] //<9>
//						defaultRoles = ['ROLE_USER', 'ROLE_GOOGLE']
					}
					github {
						client = org.pac4j.oauth.client.GitHubClient//<5>
						key = "${System.getenv().get('GITHUB_KEY')}" //<6>
						secret = "${System.getenv().get('GITHUB_SECRET')}" //<7>
						scope = org.pac4j.oauth.client.GitHubClient.DEFAULT_SCOPE //<8>
						defaultRoles = [] //<9>
//						defaultRoles = ['ROLE_USER', 'ROLE_GOOGLE']
					}
//					orcid {
//						client = org.pac4j.oauth.client.OrcidClient //<5>
//						key = "${System.getenv().get('ORCID_KEY')}" //<6>
//						secret = "${System.getenv().get('ORCID_SECRET')}" //<7>
//						// scope is set by default
////						scope = org.pac4j.oauth.client.OrcidClient //<8>
//						defaultRoles = [] //<9>
//					}
				}
			}
			providerNames = ['anonymousAuthenticationProvider'] // <10>
		}
	}
}
//end::grailsPluginSpringSecurityRest[]

//tag::filterChain[]
String ANONYMOUS_FILTERS = 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor' // <1>
grails.plugin.springsecurity.filterChain.chainMap = [
		[pattern: '/dbconsole/**',      filters: 'none'],
		[pattern: '/assets/**',      filters: 'none'],
		[pattern: '/**/js/**',       filters: 'none'],
		[pattern: '/**/css/**',      filters: 'none'],
		[pattern: '/**/images/**',   filters: 'none'],
		[pattern: '/**/favicon.ico', filters: 'none'],
		[pattern: '/', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/book/show/*', filters: ANONYMOUS_FILTERS],  // <1>
		[pattern: '/bookFavourite/index', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/auth/success', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/oauth/authenticate/google', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/oauth/authenticate/github', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/oauth/callback/google', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/oauth/callback/github', filters: ANONYMOUS_FILTERS], // <1>
		[pattern: '/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],  // <1>
]
//end::filterChain[]

//tag::logoutHandlers[]
grails.plugin.springsecurity.logout.handlerNames = ['rememberMeServices', 'securityContextLogoutHandler', 'cookieClearingLogoutHandler']
//end::logoutHandlers[]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'demo.Person'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'demo.PersonAuthority'
grails.plugin.springsecurity.authority.className = 'demo.Authority'
grails.plugin.springsecurity.requestMap.className = 'demo.RequestMap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

