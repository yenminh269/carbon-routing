package com.minhho.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService uds){
        this.jwtService = jwtService;
        this.userDetailsService = uds;
    }

    @Override
    protected boolean shouldNotFilter( HttpServletRequest request){
        return request.getServletPath().startsWith("/auth/");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
        throws ServletException, IOException {
            String path = request.getServletPath();
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")
            || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
                    || path.startsWith("/swagger-resources")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = authHeader.substring(7);
            String username = jwtService.extractUserName(token);

            if(username != null && SecurityContextHolder.getContext()
                    .getAuthentication() == null){
                UserDetails user = userDetailsService.loadUserByUsername(username);

                if(jwtService.isTokenValid(token, user)){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        filterChain.doFilter(request,response);
        }
}
