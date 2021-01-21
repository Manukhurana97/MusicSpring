package com.example.demo.Jwt;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.Dao.UserDao;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ Component
public class Util {

	private static final String key = "411C24D29D9EE41AFCF272F39";
	private static final String key1 = "411aLphA1";
	private static final String Issuer = "ADMIN";
	private static final String subject = "SESS_SUB";
	private static final String SESS_EMAIL = "SESS_USERNAME";
	private static final boolean Enabled = false;
	private static final boolean AccountNonExpired = false;
	private static final boolean credentialsNonExpired = false;
	private static final boolean AccountNonLocked = false;
	private static final String GrantedAuthorities = null;
	
	@Autowired
	private UserDao dao;
	
	
	public String create_Token(Authentication auth) 
	{
		String token=null;
		Map<String, Object> map = new HashMap();
		map.put(SESS_EMAIL, auth.getName());

		SignatureAlgorithm algo =  SignatureAlgorithm.HS256;

			if (Arrays.stream(auth.getAuthorities().toArray()).findFirst().get().toString().equals("ROLE_ADMIN"))
			{
				token = Jwts.builder().setIssuer(Issuer).setClaims(map).setSubject(subject)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() +1000*24*3600*7)).signWith(algo, key).compact();
			}
			
			else if (Arrays.stream(auth.getAuthorities().toArray()).findFirst().get().toString().equals("ROLE_USER"))
			{
				token = Jwts.builder().setIssuer(Issuer).setClaims(map).setSubject(subject)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() +1000*24*3600)).signWith(algo, key).compact();	
			}
		
		return token;
	}


	public Claims checkToken(String token)
	{
		Claims claims = null;
		try{

			claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		}
		catch (Exception e)
		{
			throw new BadCredentialsException("Invalid token");
		}
		return claims;
	}


	public boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = checkToken(token);
		return claimsResolver.apply(claims);
	}
	
	
}
