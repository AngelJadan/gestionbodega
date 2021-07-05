package ec.edu.ups.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.entidad.Usuario;
import ec.edu.ups.entidad.Producto;

@Path("/producto/")
public class ServicesProductos {

	@EJB
	ProductoFacade ejbProducto;
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response post(@FormParam("nombre") String nombre, @FormParam("precio") double precio) throws IOException {
		Producto pro = new Producto();
		pro.setNombre(nombre);
		pro.setCosto(precio);
		ejbProducto.create(pro);
		
		System.out.println("REST/producto:post-->" + pro.toString());
		return Response.ok("Producto creado correctamente!").build();
	}
	
	
	@GET
	@Path("/list/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@PathParam("id") Integer id) {
		
		Jsonb jsonb = JsonbBuilder.create();
		List<Producto> list;
		list = ejbProducto.findAll();
		
		return Response.ok(jsonb.toJson(list)).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();	
		
	}
	
	
	
}
