package de.metas.migration.impl;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.junit.Assert;
import org.junit.Test;

public class SQLDatabaseTest
{
	@Test
	public void test_ctor_URL()
	{
		final String dbUrl = "jdbc:postgresql://localhost/databaseName";
		final SQLDatabase database = new SQLDatabase(dbUrl, "testUser", "testPassword");

		Assert.assertEquals("Invalid DbHostname", "localhost", database.getDbHostname());
		Assert.assertEquals("Invalid DbHostname", null, database.getDbPort());
		Assert.assertEquals("Invalid DbHostname", "databaseName", database.getDbName());
		Assert.assertEquals("Invalid DbHostname", "postgresql", database.getDbType());
		Assert.assertEquals("Invalid DbHostname", "testUser", database.getDbUser());
		Assert.assertEquals("Invalid DbHostname", "testPassword", database.getDbPassword());
	}
}
