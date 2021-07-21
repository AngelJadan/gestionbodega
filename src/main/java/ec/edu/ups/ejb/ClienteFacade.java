package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidad.Cliente;

@Stateless
public class ClienteFacade extends AbstractFacade<Cliente>{

	@PersistenceContext(unitName = "gestionbodegaPersistenceUnit")
//	@Inject
	private EntityManager em;
	
	public ClienteFacade() {
		super(Cliente.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public Cliente searchClienteCedula(String cedula) {
		Cliente cli = new Cliente();
		try {
			String sql ="SELECT cli FROM Cliente cli WHERE cli.cedula=:ced";
			cli = (Cliente) em.createQuery(sql,Cliente.class).setParameter("ced", cedula).getSingleResult();
		} catch (Exception e) {
			System.out.println("Error al consultar el cliente por numero de cedula "+e.getLocalizedMessage());
		}
		return cli;
	}

}
