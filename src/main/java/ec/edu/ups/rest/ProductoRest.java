package ec.edu.ups.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Producto;

@Path("/productos")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class ProductoRest {
	
	@Inject
	private ProductoFacade ejbProducto;
	
	
	public ProductoRest() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Metodo para listar por categoria de producto.
	 * @param category
	 * @return
	 */
	@GET
	@Path("/productslist")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> productsList(@QueryParam("category")String category){
		List<Producto> list = new ArrayList<Producto>();
		for (Producto producto : ejbProducto.findAll()) {
			if (producto.getCategoria().equals(category)) {
				list.add(producto);
			}
		}
		return list;
	}

}
