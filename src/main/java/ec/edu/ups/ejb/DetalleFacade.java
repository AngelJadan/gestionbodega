package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidad.Detalle;

@Stateless
public class DetalleFacade extends AbstractFacade<Detalle>{

	@PersistenceContext(unitName = "Ejemplo.EJB.JSF.JPA")
	private EntityManager em;
	
	public DetalleFacade() {
		super(Detalle.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	

}
