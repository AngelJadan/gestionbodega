package ec.edu.ups.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entidad.Pedido;
import ec.edu.ups.entidad.Producto;

@Stateless
public class PedidoFacade extends AbstractFacade<Pedido>{

	@PersistenceContext(unitName = "gestionbodegaPersistenceUnit")
	private EntityManager em;
	
	public PedidoFacade() {
		super(Pedido.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	public Pedido getPedido(int id) {
		Pedido pedido = new Pedido();
		pedido = em.find(Pedido.class, id);
		return pedido;
	}
	public List<Pedido> listPedidoCliente(String cedula){
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "SELECT ped FROM Pedido ped, Cliente cli "
				+ "WHERE cli.cedula=:cedula AND ped.cliente=cli.id";
		System.out.println("SQL "+sql);
		lista = em.createQuery(sql, Pedido.class).setParameter("cedula", cedula).getResultList(); 
		return lista;
	}

}
