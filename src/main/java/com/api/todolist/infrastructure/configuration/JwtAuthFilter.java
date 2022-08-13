package com.api.todolist.infrastructure.configuration;

import com.api.todolist.common.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;
    private final UserDetailsService customUserDetailService;

    public JwtAuthFilter(@Lazy UserDetailsService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final var token = httpServletRequest.getHeader("Authorization");

        final var jwt = Optional.ofNullable(token)
                .map(t -> t.startsWith("Bearer "))
                .map(t -> token.substring(7))
                .orElse(null);

        final var username = Optional.ofNullable(jwt)
                .map(t -> TokenUtil.getUsernameFromToken(jwt, secret))
                .orElse(null);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            final var userDetails = this.customUserDetailService.loadUserByUsername(username);

            if (TokenUtil.validateToken(jwt, secret, userDetails)) {
                log.info("--- The token is valid ---");

                final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
