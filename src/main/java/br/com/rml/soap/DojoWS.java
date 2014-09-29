package br.com.rml.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import br.com.rml.model.Dojo;
import br.com.rml.utils.PersistenceUtils;

@WebService
@SOAPBinding(style = Style.RPC)
public class DojoWS {

	/*
	 * private String dateTime; private String moderator; private String
	 * dojoLink; private String costCenter;
	 */
	@WebMethod
	public Dojo insertDojo(@WebParam(name = "dateTime") String dateTime,
			@WebParam(name = "moderator") String moderator, @WebParam(name = "dojoLink") String dojoLink,
			@WebParam(name = "costCenter") String costCenter) {
		Dojo dojo = new Dojo(dateTime, moderator, dojoLink, costCenter);
		PersistenceUtils.persist(dojo);
		return dojo;
	}

}
