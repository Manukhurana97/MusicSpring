package com.example.demo.Jwt;

import java.util.Date;

import java.util.function.Function;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.example.demo.Dao.UserDao;

import io.jsonwebtoken.Jwts;

@Component
public class Util {

	protected static final String key = "411C24D29D9EE41AFCF272F39";
	protected static final String key1 = "411aLphA1";
	
	@Autowired
	private UserDao dao;

	
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
