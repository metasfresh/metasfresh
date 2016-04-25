package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CompareQueryFilterTest
{
	private Properties ctx;

	@Before
	public void init()
	{
		ctx = new Properties();
	}

	@Test
	public void test_getSql_NotEquals_NotNullValue()
	{
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>("MyColumn", Operator.NOT_EQUAL, 1);

		assertSql(filter,
				"(MyColumn <> ? OR MyColumn IS NULL)", // expected SQL
				Arrays.<Object> asList(1) // expected SQL params
		);
	}

	@Test
	public void test_getSql_NotEquals_NullValue()
	{
		final CompareQueryFilter<Object> filter = new CompareQueryFilter<>("MyColumn", Operator.NOT_EQUAL, null);

		assertSql(filter,
				"MyColumn IS NOT NULL", // expected SQL
				Arrays.<Object> asList() // expected SQL params
		);
	}

	private void assertSql(final CompareQueryFilter<Object> filter, final String expectedSql, final List<Object> expectedSqlParams)
	{
		final String sql = filter.getSql();
		final List<Object> sqlParams = filter.getSqlParams(ctx);
		Assert.assertEquals("Invalid SQL for \"" + filter + "\"",
				expectedSql, // expected
				sql // actual
		);
		Assert.assertEquals("Invalid SQL params for \"" + filter + "\"",
				expectedSqlParams, // expected
				sqlParams // actual
		);
	}
}
