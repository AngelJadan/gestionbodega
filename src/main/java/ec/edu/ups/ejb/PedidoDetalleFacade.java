package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidades.PedidoDetalle;

@Stateless
public class PedidoDetalleFacade extends AbstractFacade<PedidoDetalle>{
	@PersistenceContext(unitName = "Ejemplo.EJB.JSF.JPA")
    private EntityManager em;

    public PedidoDetalleFacade() {
        super(PedidoDetalle.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
