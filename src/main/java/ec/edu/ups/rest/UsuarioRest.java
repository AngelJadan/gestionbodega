package ec.edu.ups.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.annotations.Parameter;

import ec.edu.ups.controlador.UsuarioBean;
import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.entidad.Usuario;

@Path("/usuario")
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Stateless
public class UsuarioRest {

	@Inject
	private UsuarioFacade ejbUsuarioFacade;
	
	private Mensaje sms;
	
	public UsuarioRest() {
		// TODO Auto-generated constructor stub
		sms = new Mensaje();
	}
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Mensaje login(@FormParam("usuario") String usuario, @FormParam("password") String password) throws IOException {
		/*
		 * http://localhost:8080/gestionbodega/rest/usuario/login user:"" pass:""
		 */		
		System.out.println("REST--> USER:" + usuario + ". pass:" + password);
		if (usuario != null) {
			for (Usuario usu : ejbUsuarioFacade.findAll()) {
				System.out.println("user actual: "+usu.toString());
				if (usu.getNombre().equals(usuario) && usu.getPassword().equals(password)) {
					System.out.println("Usuario encontrado: " + usu.toString());
					sms.setCodigo("200");
					sms.setMensaje("Usuario logueado");
				}
			}
		} else {
			sms.setCodigo("405");
			sms.setMensaje("Se requiere un usuario y una contraseña");
		}
		return sms;
	}
}
