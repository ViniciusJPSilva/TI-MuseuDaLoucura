package ti.vjps.museu.dao;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ti.vjps.museu.modelo.Agendamento;
import ti.vjps.museu.modelo.Pessoa;

public class AgendamentoDAO {

	public Agendamento preencheListaAgendamento(Agendamento agendamento) {
		EntityManager em = new JPAUtil().getEntityManager();

		em.getTransaction().begin();

		Query query = em.createNativeQuery("SELECT pessoa_id FROM agendamento_pessoa WHERE agendamento_id = ?");
		query.setParameter(1, agendamento.getId());

		for(Object idObj : query.getResultList()) {
			Long id = ((BigInteger) idObj).longValue();
			
			DAO<Pessoa> dao = new DAO<Pessoa>(Pessoa.class);
			agendamento.getGrupoPessoas().add(dao.buscaPorld(id));
		}

		em.getTransaction().commit();
		em.close();

		return agendamento;
	}

}
