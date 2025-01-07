// package org.rabie.hunters_league.util;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
// import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// public class JwtOAuth2Filter extends OncePerRequestFilter {
//     private final JwtAuthenticationProvider jwtAuthProvider;
//     public JwtOAuth2Filter(JwtDecoder jwtDecoder, JwtConverter jwtConverter) {
//         this.jwtAuthProvider = new JwtAuthenticationProvider(jwtDecoder);
//         this.jwtAuthProvider.setJwtAuthenticationConverter(jwtConverter);
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String authorizationHeader = request.getHeader("Authorization");

//         if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//             String token = authorizationHeader.substring(7);
//             try {
//                 var authentication = jwtAuthProvider.authenticate(new BearerTokenAuthenticationToken(token));
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             } catch (Exception e) {
//                 System.out.println("Error: " + e.getMessage());
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }
