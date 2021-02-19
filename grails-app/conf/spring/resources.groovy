import demo.PersonPasswordEncoderListener
//tag::tokenReaderImport[]
import demo.JwtCookieTokenReader
//end::tokenReaderImport[]
//tag::cookieClearingImport[]
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler
//end::cookieClearingImport[]
beans = {
    personPasswordEncoderListener(PersonPasswordEncoderListener)
    //tag::tokenReader[]
    tokenReader(JwtCookieTokenReader) {
        cookieName = 'jwt'
    }
    //end::tokenReader[]
    //tag::cookieClearing[]
    cookieClearingLogoutHandler(CookieClearingLogoutHandler, ['jwt'])
    //end::cookieClearing[]
}
