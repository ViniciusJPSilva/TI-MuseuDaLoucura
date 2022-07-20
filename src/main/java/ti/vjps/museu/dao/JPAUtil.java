package ti.vjps.museu.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("museuloucura");

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
} // class JPAUtil
