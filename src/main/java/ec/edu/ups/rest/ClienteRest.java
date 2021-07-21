package ec.edu.ups.rest;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.ExemptionMechanismSpi;
import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.ClienteFacade;
import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.entidad.Cliente;
import ec.edu.ups.entidad.Usuario;

@Path("/cliente")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class ClienteRest {
	
	@Inject
	private UsuarioFacade ejbUsuarioFacade;
	@Inject
	private ClienteFacade ejbClienteFacade;
	
	private List<Cliente> clientes;
	private Cliente cliente;
	private Usuario usuario;
	private Mensaje sms;
	
	public ClienteRest() {
		// TODO Auto-generated constructor stub
		clientes = new ArrayList<Cliente>();
		cliente = new Cliente();
		usuario = new Usuario();
		sms = new Mensaje();
	}
	
	/**
	 * Metodo para crear un cliente sin usuario.
	 * @param cedula
	 * @param name
	 * @param lastname
	 * @param address
	 * @param email
	 * @param status
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/sign_in_customer")
	public Response regCustData(@FormParam("cedula") String cedula, @FormParam("name") String name,
			@FormParam("lastname") String lastname, @FormParam("address") String address, 
			@FormParam("email")String email, @FormParam("status") String status) {
		cliente = new Cliente(name, lastname, cedula, address, email, status);
		System.out.println(cliente.toString());
		if (cedula.length()==10 && name.length()>0 && lastname.length()>0 && address.length()>0
			&&email.length()>10 && status.length()>1) {
			try {
				ejbClienteFacade.create(cliente);
				return Response.ok("Cliente "+name+" cedula "+cedula+" ha sido registrado").build();
			} catch (Exception e) {
				return Response.ok("Error: "+e.getMessage()).build();
			}
		}else {
			return Response.ok("Ingrese los datos completos por favor.").build();
		}		
		
	}
	/**
	 * Metodo para crear un usuario de cliente, en base de los datos antes creados.
	 * @param cedula
	 * @return
	 */
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/sign_in_cust_to_id")
	public Response regCustToCed(@FormParam("cedula") String cedula) {
		System.out.println("cedula: "+cedula);
		for (Cliente cliente : ejbClienteFacade.findAll()) {
			if (cliente.getCedula().equals(cedula)) {
				System.out.println("Cliente encontrado"+cliente.toString());
				usuario = new Usuario(cliente.getCorreo(), "cliente", cliente.getCedula());
				ejbUsuarioFacade.create(usuario);
				return Response.ok("Cliente registrado: \n usuario: "+cliente.getCorreo()
				+"\n clave temporal: "+cliente.getCedula()).build();
			}
		}
		
		return Response.ok("Cliente no encontrado").build();
	}
	/**
	 * Metodo para actualizar los datos del cliente y su usuario.
	 * @param id identificador unico del cliente
	 * @param cedula
	 * @param name
	 * @param lastname
	 * @param address
	 * @param email
	 * @param status
	 * @param usuId identificador unico del usuario
	 * @param user
	 * @param password
	 * @param type
	 * @return
	 */
	
	public Mensaje updateCustomer(
			@FormParam("id") int id,
			@FormParam("cedula") String cedula, @FormParam("name") String name,
			@FormParam("lastname") String lastname, @FormParam("address") String address, 
			@FormParam("email")String email, @FormParam("status") String status,
			@FormParam("usuId")int usuId,
			@FormParam("user") String user, @FormParam("password") String password, @FormParam("type") String type) {
		
		cliente = new Cliente(name,lastname,cedula,address,email,status);
		cliente.setId(id);
		
		usuario = new Usuario(user, type, password);
		usuario.setId(usuId);
		
		for (Cliente temp : ejbClienteFacade.findAll()) {
			if (temp.equals(cliente)) {
				System.out.println("Cliente encontrado");
				for (Usuario usu : ejbUsuarioFacade.findAll()) {
					if (usu.equals(usuario)) {
						System.out.println("Usuario encontrado.");
						sms.setCodigo("200");
						sms.setMensaje("Cliente y usuario actualizado");
					}else {
						sms.setCodigo("215");
						sms.setMensaje("No se ha podido actualziar el usuario.");
					}
				}
			}else {
				sms.setCodigo("400");
				sms.setMensaje("Cliente no encontrado");
			}
		}		
		return sms;
	}
	
	/**
	 * 
	 * @param id
	 * @param user
	 * @param type
	 * @return Mensaje con un codigo y una descripcion.
	 */
	public Mensaje deleteUser(@FormParam("id") int id, @FormParam("user") String user,
			@FormParam("type") String type) {
		for (Usuario usu : ejbUsuarioFacade.findAll()) {
			if (usu.getId()==id && usu.getNombre().equals(user)) {
				if (usu.getTipo().equals("cliente") && type.equals("cliente")) {
					for (Cliente cli : ejbClienteFacade.findAll()) {
						if (cli.getCorreo().equals(user)) {
							Cliente client = cli;
							client.setEstado("inactivo");
							ejbClienteFacade.edit(client);
							sms.setCodigo("200");
							sms.setMensaje("Cliente eliminado.");
						}
					}
				}else {
					sms.setCodigo("200");
					sms.setMensaje("El usuario no pertenece a un cliente.");
				}
			}
		}
		return sms;
	}
}
