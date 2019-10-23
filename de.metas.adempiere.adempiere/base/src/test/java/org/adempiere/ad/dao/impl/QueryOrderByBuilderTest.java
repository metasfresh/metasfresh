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


import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryOrderByBuilderTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testWithOneColumn()
	{
		testWithOneColumn("MyColumnName ASC NULLS FIRST", "MyColumnName", Direction.Ascending, Nulls.First);
		testWithOneColumn("MyColumnName ASC NULLS LAST", "MyColumnName", Direction.Ascending, Nulls.Last);
		testWithOneColumn("MyColumnName DESC NULLS FIRST", "MyColumnName", Direction.Descending, Nulls.First);
		testWithOneColumn("MyColumnName DESC NULLS LAST", "MyColumnName", Direction.Descending, Nulls.Last);
	}

	private void testWithOneColumn(final String expectedSql,
			final String columnName, final Direction direction, final Nulls nulls)
	{
		final QueryOrderByBuilder<?> builder = new QueryOrderByBuilder<>();
		builder.addColumn(columnName, direction, nulls);

		final String actualSql = builder.createQueryOrderBy().getSql();

		Assert.assertEquals("Invalid order by SQL", expectedSql, actualSql);
	}

	@Test
	public void testBackwardCompatibility()
	{
		final String actualSql = new QueryOrderByBuilder<Object>()
				.addColumn("MyColumnName")
				.createQueryOrderBy()
				.getSql();

		// NOTE: keeping backward compatibility
		// i.e. postgresql 9.1. specifications:
		// "By default, null values sort as if larger than any non-null value;
		// that is, NULLS FIRST is the default for DESC order, and NULLS LAST otherwise."
		//
		// see http://www.postgresql.org/docs/9.1/static/queries-order.html
		final String expectedSql = "MyColumnName ASC NULLS LAST";
		Assert.assertEquals("Invalid order by SQL", expectedSql, actualSql);
	}

	/**
	 * Test {@link QueryOrderByBuilder#addColumn(String, boolean)}
	 */
	@Test
	public void testBackwardCompatibility_addColumn()
	{
		// NOTE: keeping backward compatibility
		// i.e. postgresql 9.1. specifications:
		// "By default, null values sort as if larger than any non-null value;
		// that is, NULLS FIRST is the default for DESC order, and NULLS LAST otherwise."
		//
		// see http://www.postgresql.org/docs/9.1/static/queries-order.html

		testBackwardCompatibility_addColumn("MyColumnName ASC NULLS LAST", "MyColumnName", true);
		testBackwardCompatibility_addColumn("MyColumnName DESC NULLS FIRST", "MyColumnName", false);
	}

	private void testBackwardCompatibility_addColumn(final String expectedSql,
			String columnName, boolean asc)
	{
		final String actualSql = new QueryOrderByBuilder<Object>()
				.addColumn("MyColumnName", asc)
				.createQueryOrderBy()
				.getSql();

		Assert.assertEquals("Invalid order by SQL", expectedSql, actualSql);
	}

}
