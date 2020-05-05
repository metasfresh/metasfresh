package de.metas.jax.rs.oneway;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.Oneway;

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
	@POST
	@Path("addTestJojo")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addTestPojo(TestPojo testPojo);

	@POST
	@Path("addTestPojoReturnsVoid")
	@Consumes(MediaType.APPLICATION_JSON)
	void addTestPojoReturnsVoid(TestPojo testPojo);

	@POST
	@Path("addTestJojoNoReply")
	@Oneway // note that with oneway and "void" we get an NPE in the client..both with cxf-3.1.4 and cxf.3.1.5, but fixed in 3.1.6
	@Consumes(MediaType.APPLICATION_JSON)
	Response addTestPojoOneWay(TestPojo testPojo);

	@POST
	@Path("addTestPojoOneWayReturnVoid")
	@Oneway
	@Consumes(MediaType.APPLICATION_JSON)
	void addTestPojoOneWayReturnsVoid(TestPojo testPojo);

}