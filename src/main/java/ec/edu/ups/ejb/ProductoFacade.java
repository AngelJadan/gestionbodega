package ec.edu.ups.ejb;

import java.sql.SQLException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidad.Producto;

@Stateless
public class ProductoFacade extends AbstractFacade<Producto>{

	@PersistenceContext(unitName = "gestionbodegaPersistenceUnit")
	private EntityManager em;
	
	public ProductoFacade() {
		super(Producto.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	public Producto getProductoId(int id) throws SQLException {
		Producto producto = new Producto();
		String sql = "SELECT pro FROM Producto pro WHERE pro.id=:id";
		producto = (Producto) em.createQuery(sql, Producto.class)
				.setParameter("id", id)
				.getSingleResult();
		return producto;
	}
}
