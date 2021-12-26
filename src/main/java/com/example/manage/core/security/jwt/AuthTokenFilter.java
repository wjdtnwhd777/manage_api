package com.example.manage.core.security.jwt;

import com.example.manage.core.encryption.Crypt;
import com.example.manage.core.security.service.UserDetailsServiceImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = jwtUtils.parseJwt(request);

            if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
                Map<String, Object> claims = jwtUtils.getClaimsFromJwtToken(jwt);
                String claimJsonString = MapUtils.getString(claims, "data");
                JsonElement e = JsonParser.parseString(Crypt.decrypt(claimJsonString));
                JsonObject claimJson = e.getAsJsonObject();

                String email = claimJson.get("email").getAsString();

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getLocalizedMessage());
        }

        filterChain.doFilter(request, response);
    }
}
