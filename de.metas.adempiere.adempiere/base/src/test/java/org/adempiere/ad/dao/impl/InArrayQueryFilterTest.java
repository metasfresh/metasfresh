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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class InArrayQueryFilterTest
{
	private final Properties ctx = null; // Context is not used in InArrayQueryFilter

	@Test(expected = Exception.class)
	public void test_ColumnName_NULL()
	{
		final String columnName = null;

		// Expect exception:
		new InArrayQueryFilter<Object>(columnName, Collections.emptyList());
	}

	@Test
	public void test_StandardCases()
	{
		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1"),
				// Expected output
				"MyColumnName=?",
				Arrays.<Object> asList("Value1"));
		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList((Object)null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1", "Value2"),
				// Expected output
				"MyColumnName IN (?,?)",
				Arrays.<Object> asList("Value1", "Value2"));

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList("Value1", null, "Value2", null),
				// Expected output
				"(MyColumnName IN (?,?) OR MyColumnName IS NULL)",
				Arrays.<Object> asList("Value1", "Value2"));

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList((Object)null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());

		assertFilter(
				// Input
				"MyColumnName",
				Arrays.<Object> asList(null, null, null, null),
				// Expected output
				"MyColumnName IS NULL",
				Collections.emptyList());
	}

	/**
	 * Regression: Make sure DefaultReturnWhenEmpty option is set to "true" by default
	 */
	@Test
	public void test_DefaultReturnWhenEmpty_Is_True()
	{
		final String columnName = "MyColumnName";
		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<Object>(columnName, Collections.emptyList());

		Assert.assertEquals("Invalid DefaultReturnWhenEmpty default", true, filter.isDefaultReturnWhenEmpty());

	}

	@Test
	public void test_NoValues_DefaultReturnWhenEmpty_TRUE()
	{
		assertDefaultReturnWhenEmpty(true, null);
		assertDefaultReturnWhenEmpty(true, Collections.emptyList());
		assertDefaultReturnWhenEmpty(true, new ArrayList<Object>());
	}

	@Test
	public void test_NoValues_DefaultReturnWhenEmpty_FALSE()
	{
		assertDefaultReturnWhenEmpty(false, null);
		assertDefaultReturnWhenEmpty(false, Collections.emptyList());
		assertDefaultReturnWhenEmpty(false, new ArrayList<Object>());
	}
	
	private void assertDefaultReturnWhenEmpty(final boolean defaultReturnWhenEmpty, final List<Object> params)
	{
		final String columnName = "MyColumnName";
		final String sqlExpected = defaultReturnWhenEmpty ? InArrayQueryFilter.SQL_TRUE : InArrayQueryFilter.SQL_FALSE;
		final List<Object> sqlParamsExpected = Collections.emptyList();

		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<Object>(columnName, params);
		filter.setDefaultReturnWhenEmpty(defaultReturnWhenEmpty);

		Assert.assertEquals("Invalid build SQL: " + filter, sqlExpected, filter.getSql());
		Assert.assertEquals("Invalid build SQL Params: " + filter, sqlParamsExpected, filter.getSqlParams(ctx));
	}

	private void assertFilter(final String columnName, final List<Object> values,
			final String sqlExpected,
			final List<Object> sqlParamsExpected)
	{
		final InArrayQueryFilter<Object> filter = new InArrayQueryFilter<Object>(columnName, values);
		assertFilter(filter, sqlExpected, sqlParamsExpected);
	}

	private void assertFilter(final InArrayQueryFilter<Object> filter,
			final String sqlExpected,
			final List<Object> sqlParamsExpected)
	{
		final String sqlActual = filter.getSql();
		final List<Object> sqlParamsActual = filter.getSqlParams(ctx);

		Assert.assertEquals("Invalid build SQL: " + filter, sqlExpected, sqlActual);
		Assert.assertEquals("Invalid build SQL Params: " + filter, sqlParamsExpected, sqlParamsActual);
	}
}
