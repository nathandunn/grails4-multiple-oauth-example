package demo

import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.reader.TokenReader
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import java.nio.charset.StandardCharsets

@Slf4j
@CompileStatic
class JwtCookieTokenReader implements TokenReader {

    final static String DEFAULT_COOKIE_NAME = 'JWT'

    String cookieName = DEFAULT_COOKIE_NAME
    private static final String HMAC_SHA512 = "HmacSHA512";


    @Override
    AccessToken findToken(HttpServletRequest request) {

        println "Looking for jwt token in a cookie named {}: " + cookieName
        String tokenValue = null
        Cookie cookie = request.getCookies()?.find { Cookie cookie -> cookie.name.equalsIgnoreCase(cookieName) }

        def headerNames = request.getHeaderNames()
        println "header names"
        println headerNames.each{ println it}

        String bearer = request.getHeader("Authorization")
        println "bearer:"
        println bearer
        if(bearer && bearer.startsWith("Bearer jwt=")){
//            println "has authorization"
//            Mac sha512Hmac;
//            String key = 'foobar123'*4
//            String key = System.getenv().get('GOOGLE_SECRET')
//            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
//            sha512Hmac = Mac.getInstance(HMAC_SHA512);
//            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
//            sha512Hmac.init(keySpec);
//            byte[] macData = sha512Hmac.doFinal(bearer.substring(4).getBytes(StandardCharsets.UTF_8));
//
//
//            // Can either base64 encode or put it right into hex
//            String result = Base64.getEncoder().encodeToString(macData);

            tokenValue = bearer.substring("Bearer jwt=".size())
        }
        println "A token"
        println tokenValue

        if ( cookie ) {
            println "has cookie"
            tokenValue = cookie.value
        }
        println "B token"
        println tokenValue

        println "Token: ${tokenValue}"
        return tokenValue ? new AccessToken(tokenValue) : null

    }
}
