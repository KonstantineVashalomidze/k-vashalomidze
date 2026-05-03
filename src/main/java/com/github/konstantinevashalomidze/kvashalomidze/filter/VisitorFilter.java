package com.github.konstantinevashalomidze.kvashalomidze.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class VisitorFilter extends OncePerRequestFilter {

    public static final String VISITOR_ATTR = "visitor_id";
    private static final String COOKIE_NAME = "visitor_id";
    private static final int ONE_YEAR = 60 * 60 * 24 * 365;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String visitorId = null;

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (COOKIE_NAME.equals(c.getName())) {
                    visitorId = c.getValue();
                    break;
                }
            }
        }

        if (visitorId == null) {
            visitorId = UUID.randomUUID().toString() + Long.toString(System.currentTimeMillis());
            Cookie cookie = new Cookie(COOKIE_NAME, visitorId);
            cookie.setMaxAge(ONE_YEAR);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        request.setAttribute(VISITOR_ATTR, visitorId);
        filterChain.doFilter(request, response);

    }
}
