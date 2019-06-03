package sk.garwan.animal.shop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sk.garwan.animal.shop.exception.JWTException;
import sk.garwan.animal.shop.model.ApplicationRole;

@Slf4j
@Component
public class JwtTokenProvider {

  @Value("${jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 3600000; // 1h

  private final DefaultUserDetail userDetailService;

  @Autowired
  public JwtTokenProvider(DefaultUserDetail userDetailService) {
    this.userDetailService = userDetailService;
  }

  @PostConstruct
  protected void encodeSecret() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<ApplicationRole> roles) {

    Claims claims = prepareClaims(username, roles);

    Instant expiration = Instant.now().plusMillis(validityInMilliseconds);

    String token = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(expiration))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
    Authentication auth = getAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(auth);
    return token;
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailService.loadUserByUsername(extractUsernameFromToken(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  String extractUsernameFromToken(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new JWTException("Expired or invalid JWT token");
    }
  }

  private Claims prepareClaims(String username, List<ApplicationRole> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
        .collect(Collectors.toList()));

    return claims;
  }

}
