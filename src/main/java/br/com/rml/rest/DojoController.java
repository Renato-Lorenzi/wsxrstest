package br.com.rml.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rml.model.Dojo;
import br.com.rml.utils.PersistenceUtils;

@Path("/dojo")
public class DojoController {

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Dojo insertDojo(@FormParam("dateTime") String dateTime, @FormParam("moderator") String moderator,
			@FormParam("dojoLink") String dojoLink, @FormParam("constCenter") String costCenter) {
		Dojo dojo = new Dojo(dateTime, moderator, dojoLink, costCenter);
		PersistenceUtils.persist(dojo);
		return dojo;
	}
}