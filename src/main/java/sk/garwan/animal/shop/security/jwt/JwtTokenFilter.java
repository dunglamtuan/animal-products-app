package sk.garwan.animal.shop.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.garwan.animal.shop.exception.JWTException;

@AllArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    String token = jwtTokenProvider.resolveToken(httpServletRequest);
    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (JWTException ex) {
      SecurityContextHolder.clearContext();
      httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
      return;
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
