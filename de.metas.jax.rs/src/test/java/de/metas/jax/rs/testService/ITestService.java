package de.metas.jax.rs.testService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.metas.jax.rs.TestPojo;

/*
 * #%L
 * de.metas.jax.rs
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * Service interface.<br>
 * Notes:
 * <ul>
 * <li>When using the {@link org.apache.cxf.jaxrs.client.JAXRSClientFactory} to get our client, we need to pass it an <b>interface</b> class that is also annotated with <code>javax.ws.rs</code> annotations.<br>
 * It's also possible to create the proxy from a class, if cglib-nodeps is available (sais this documentation: http://cxf.apache.org/docs/jax-rs-client-api.html#JAX-RSClientAPI-Proxy-basedAPI ).
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Path("/testService/")
public interface ITestService
{

	/**
	 * The method-path annotation is a bit redundant with the method names, but required to avoid this warning:
	 *
	 * <pre>
	 * WARNING: Both de.metas.jax.rs.server.TestService#getTestPojo and de.metas.jax.rs.server.TestService#addTestJojo are equal candidates for handling the current request which can lead to unpredictable results
	 * </pre>
	 *
	 * @param testPojo
	 * @return
	 */
	@POST
	@Path("addTestJojo")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addTestPojo(TestPojo testPojo);

	/**
	 * About the {@link Path} annotation:
	 * <ul>
	 * <li>
	 * in addition to what i wrote in see {@link #addTestPojo(TestPojo)}, also note the the <code>/{id}</code> part is mandatory for the framework to correctly set the <code>id</code> value when
	 * invoking the method implementation.
	 * <li>also mandatory is the {@link PathParam} annotation. Without it, there will be an error saying <code>Unresolved variables; only 0 value(s) given for 1 unique variable(s)</code>.
	 * </ul>
	 *
	 * @param id
	 * @return
	 */
	@POST
	@Path("getTestJojo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	TestPojo getTestPojo(@PathParam("id") int id);

}