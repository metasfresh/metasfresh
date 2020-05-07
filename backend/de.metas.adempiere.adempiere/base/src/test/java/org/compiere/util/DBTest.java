package org.compiere.util;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.junit.Assert;
import org.junit.Test;

public class DBTest
{
	@Test(expected = AdempiereException.class)
	public void test_buildSqlList_null_paramsOut()
	{
		Check.setDefaultExClass(AdempiereException.class); // setting the exception that a failing assumption shall throw
		DB.buildSqlList(new ArrayList<>(), null);
	}

	@Test
	public void test_buildSqlList_empty_paramsIn()
	{
		{
			final List<Object> paramsOut = new ArrayList<>();
			final String sql = DB.buildSqlList(null, paramsOut);
			Assert.assertTrue("paramsOut shall be empty: " + paramsOut, paramsOut.isEmpty());
			Assert.assertEquals("sql shall be empty list", DB.SQL_EmptyList, sql);
		}

		{
			final List<Object> paramsOut = new ArrayList<>();
			final String sql = DB.buildSqlList(Collections.emptyList(), paramsOut);
			Assert.assertTrue("paramsOut shall be empty: " + paramsOut, paramsOut.isEmpty());
			Assert.assertEquals("sql shall be empty list", DB.SQL_EmptyList, sql);
		}

	}

	@Test
	public void test_buildSqlList_with_paramIn_and_paramsOut()
	{
		final List<Object> paramsIn = new ArrayList<>();
		paramsIn.add("value1");
		paramsIn.add("value2");

		final List<Object> paramsInExpected = new ArrayList<>(paramsIn);

		final List<Object> paramsOut = new ArrayList<>();
		paramsOut.add("existing-value1");
		paramsOut.add(new Date());
		paramsOut.add(true);

		final String sqlExpected = "(?,?)";
		final List<Object> paramsOutExpected = new ArrayList<>(paramsOut);
		paramsOutExpected.addAll(paramsIn);

		final String sqlActual = DB.buildSqlList(paramsIn, paramsOut);

		Assert.assertEquals("Invalid produced sql", sqlExpected, sqlActual);
		Assert.assertEquals("ParamsIn shall not be modified", paramsInExpected, paramsIn);
		Assert.assertEquals("Invalid paramsOut", paramsOutExpected, paramsOut);
	}

	@Test
	public void toBoolean()
	{
		assertThat(DB.TO_BOOLEAN(true)).isEqualTo("'Y'");
		assertThat(DB.TO_BOOLEAN(false)).isEqualTo("'N'");
	}
}
