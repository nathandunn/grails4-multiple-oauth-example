package demo

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.grails.web.json.JSONObject

import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
//import com.google.api.client.util.Base64


//@Secured(["ROLE_USER"])
@Secured('permitAll')
class ApiController extends RestfulController<Book>{

    String sampleToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImZlZDgwZmVjNTZkYjk5MjMzZDRiNGY2MGZiYWZkYmFlYjkxODZjNzMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNjU0NjI5NTA3NTkyLTlpOHZoMTllc252MmY1aXMxcm9vZmwzYzl2N3NsYTU0LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNjU0NjI5NTA3NTkyLTlpOHZoMTllc252MmY1aXMxcm9vZmwzYzl2N3NsYTU0LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE2MDU0MTQ2NTQ1OTIyMDg1NDg0IiwiZW1haWwiOiJuZHVubm1lQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiQXlYdG5OT25pVXhSSGZldDVYbG8yUSIsIm5hbWUiOiJOYXRoYW4gRHVubiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS0vQU9oMTRHaEd1eERTRUxja203ZHlGaU1hWkRqZVM4a2xnNXo5Z2pHR1RJYTM9czk2LWMiLCJnaXZlbl9uYW1lIjoiTmF0aGFuIiwiZmFtaWx5X25hbWUiOiJEdW5uIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2MTQyOTI5NzksImV4cCI6MTYxNDI5NjU3OSwianRpIjoiZGYxZjA5ZjcwNTcyMjAzMGE4NTlmNzJmOTQ4Y2JkYzhlODE2NzM0YSJ9.G1aH7cdu6jtnT5zTKpPCLEbKySwDIv_cnowj5K1EUul46LwqvD0naY9KFJBY6vGcn4MjAFWjG9eKLPdaGBaMCYupKsUND0fTJ1ubTssa7XOw9zdfN2VtqhcrMfQRjXSbnu90GSbdVwjQmVeBgEGtp8fmgS_U8z2XuhMYUfOCfB7vInXiz90jSXSWw8fTdRbaz31PpjLGsCsIGTCU6HjiFhB35WX0EhjjNP4PePbGY4wlouvu_ldCZDleS2uGuhTvD4qHLnvmNKqmgsDTRWXDuPthxWvkLx-JtsMblL0J3exwE7o1sBvvFs23v-54S2eVCQ8j_oHzm3SA044XknCVIw"

    static responseFormats = ['json', 'xml']
//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",index:"GET"]
    ApiController() {
        super(Book)
    }

    def index() {
        println "request: ${request}"
        println "request JSON: ${request.JSON}"
        println "params : ${params}"
        println "cookies: ${request.cookies}"
        Jws<Claims> jws;
        println "A.1"
//        SignatureAlgorithm algorithm = SignatureAlgorithm.RS256("${System.getenv().get('GITHUB_KEY')}", "${System.getenv().get('GITHUB_SECRET')}");
//        JWTVerifier verifier = JWT.require(algorithm)
//                .withIssuer("auth0")
//                .build(); //Reusable verifier instance
//        DecodedJWT jwt = verifier.verify(sampleToken);

        KeyFactory kf = KeyFactory.getInstance("RSA")
        String PUB_KEY = "${System.getenv().get('GOOGLE_KEY')}"
        if (!PUB_KEY.isEmpty()) {
            PUB_KEY = PUB_KEY.replace(" ", "");
        }
//        println PUB_KEY
        def tokens = sampleToken.split("\\.")
        println tokens.length
        def headers =  new String(java.util.Base64.decoder.decode(tokens[1]))
        println headers
        def jsonObject = new JSONObject(headers)
        println jsonObject.toString(2)

//        X509EncodedKeySpec pubKeySpecX509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64());
//        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(decode);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);
////        PublicKey publicKey = kf.generatePublic(pubKeySpecX509EncodedKeySpec);
//        Optional<RSAPublicKey> optionalPublicKey = Optional.of(pubKey);
//
//        jws = Jwts.parserBuilder()  // (1)
//                .setSigningKey(optionalPublicKey)
//            .build()
//                .parseClaimsJws(sampleToken); // (4)
//        println "B"
//        println jws.header
//        println "C"
//        println jws.body
//        println "D"
//        println jws.signature
//        println "E"



        request.cookies.findAll().each {
            println it
        }
        def ids = request.getHeader("ID")
        println "ids: ${ids}"
        response.setHeader("Access-Control-Allow-Headers",'Origin, X-Requested-With, Content-Type, Accept, Authorization,  X-PINGOTHER\'')
        response.setHeader("Access-Control-Allow-Credentials","true")
        respond Book.all
    }
}
