package JPA.JPA.security.jwt;


import JPA.JPA.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${juanJPA.app.jwtSecret}")
    private String jwtSecret;

    @Value("${juanJPA.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${juanJPA.app.jwtCookieName}")
    private String jwtCookie;

//    public Cookie getCookieFromRequest(HttpServletRequest request, String cookieName) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(cookieName)) {
//                    return cookie;
//                }
//            }
//        }
//        return null;
//    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        System.out.println(cookie + " the cookie");
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        //path should be set to / if we want all the domains to be able to access it
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}




//@Component
//public class CookieUtils {
//
//
//   // Adds a cookie to the HTTP response.*
//    @param response   // the HTTP servlet response to add the cookie to
//    @param cookieName // the name of the cookie
//    @param cookieValue //the value of the cookie
//    @param rememberMe  // to remember the cookie or treat it as session
//
//    public void addCookie(HttpServletResponse response, String cookieName, String cookieValue, Boolean rememberMe) {
//        Cookie cookie = new Cookie(cookieName, cookieValue);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        if (rememberMe) {
//            cookie.setMaxAge(30 * 24 * 60 * 60);} else {
//            cookie.setMaxAge(-1);}
////        cookie.setDomain(Constants.FRONTEND_URL); // TODO: uncomment when using angular
////        cookie.setSecure(true); // set to true if using HTTPS
//        response.addCookie(cookie);
//    }
//
//    /
//
//    Removes a token cookie from the provided HttpServletResponse.*
//    @param response   the HttpServletResponse to remove the token cookie from
//    @param cookieName the name of the cookie to be removed*/
//    public void removeCookie(HttpServletResponse response, String cookieName) {
//        Cookie cookie = new Cookie(cookieName, null);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(0);
////        cookie.setDomain(Constants.FRONTEND_URL); // TODO: uncomment when using angular
////        cookie.setSecure(true); // set to true if using HTTPS
//        response.addCookie(cookie);
//    }
//
//    public Cookie getCookieFromRequest(HttpServletRequest request, String cookieName) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(cookieName)) {
//                    return cookie;
//                }
//            }
//        }
//        return null;
//    }
//}