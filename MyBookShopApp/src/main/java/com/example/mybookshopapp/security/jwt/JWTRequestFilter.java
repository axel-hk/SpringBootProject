package com.example.mybookshopapp.security.jwt;

import com.example.mybookshopapp.security.BookStoreUserDetails;
import com.example.mybookshopapp.security.BookStoreUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final BookStoreUserDetailsService bookStoreUserDetails;
    private final JWTUtil jwtUtil;

    @Autowired
    public JWTRequestFilter(BookStoreUserDetailsService bookStoreUserDetails, JWTUtil jwtUtil) {
        this.bookStoreUserDetails = bookStoreUserDetails;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = null;
        String username = null;
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    username = jwtUtil.extractUsername(token);
                }

                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    BookStoreUserDetails userDetails =
                            (BookStoreUserDetails) bookStoreUserDetails.loadUserByUsername(username);
                    if(jwtUtil.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request
                        ));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
