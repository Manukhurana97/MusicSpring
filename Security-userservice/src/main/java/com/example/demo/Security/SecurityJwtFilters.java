package com.example.demo.Security;

import com.example.demo.Dao.UserDao;
import com.example.demo.Jwt.Util;
import com.example.demo.model.Users;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityJwtFilters extends OncePerRequestFilter {

    @Autowired
    private Util util;

    @Autowired
    private UserDao dao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String jwttoken = request.getHeader("Authentication");
        try{

            if(util.checkToken(jwttoken)!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                Claims claims = util.checkToken(jwttoken);

                Users users = dao.findByUsername(claims.get("SESS_USERNAME").toString());

                if(!util.isTokenExpired(jwttoken))
                {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                   
                    authorities.add(new SimpleGrantedAuthority(users.getAuthorities().getAuthority()));
                    
                    UserDetails userDetails = new User(users.getUsername(), users.getPassword(), authorities);
                    
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
