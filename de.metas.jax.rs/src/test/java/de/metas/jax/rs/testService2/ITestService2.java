package de.metas.jax.rs.testService2;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import de.metas.jax.rs.TestPojo;
import de.metas.jax.rs.testService.ITestService;

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
 * See {@link ITestService} for documentation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Path("/testService2/")
public interface ITestService2
{
	@POST
	@Path("addTestJojo")
	Response addTestPojo(TestPojo testPojo);

	@POST
	@Path("getTestJojo/{id}")
	TestPojo getTestPojo(@PathParam("id") int id);
}