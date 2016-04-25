package de.metas.jax.rs.testService;

import java.util.HashMap;
import java.util.Map;

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

public class TestService implements ITestService
{
	private static final Map<Integer, TestPojo> map = new HashMap<Integer, TestPojo>();

	@Override
	public Response addTestPojo(final TestPojo testPojo)
	{
		testPojo.setProcessedByService(ITestService.class.getSimpleName());
		map.put(testPojo.getId(), testPojo);
		return Response.ok().build();
	}

	@Override
	public TestPojo getTestPojo(final int id)
	{
		final TestPojo testPojo = map.get(id);
		return testPojo;
	}
}
