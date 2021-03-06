package com.example.demo.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.model.Artist;
import com.example.demo.model.Followers;

@Service
public class ArtistQueryImpl implements ArtistQuery {

	@PersistenceContext()
	private final EntityManager entityManager;

	public ArtistQueryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Artist> findAllArtistsWithCriteriaQuery(List<Artist> lst) {
		List<Artist> ret_art = new ArrayList<>();
		Session session = entityManager.unwrap(Session.class);
		for(Artist artist:lst) {
//			if (!sessionFactory.getCache().containsEntity(Artist.class, artist)) {}
			Artist data = session.get(Artist.class, artist.getArtistid());
			ret_art.add(data);
		}
		session.close();
		entityManager.close();
		return ret_art;
	}

	@Override
	@Transactional
	public void deleteByFollower(Followers follow) {
		Session session = entityManager.unwrap(Session.class);
		String sql = "delete from followers where id="+follow.getId();
		session.createSQLQuery(sql).executeUpdate();
		session.flush();
		entityManager.close();
		
	}
}