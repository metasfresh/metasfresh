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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link CompositeQueryFilter}
 *
 * @author tsa
 *
 */
public class CompositeQueryFilterTest
{
	public static interface I_ModelClass
	{
		String Table_Name = "ModelClass";

		String COLUMNNAME_Column1 = "Column1";
		String COLUMNNAME_Column2 = "Column2";
	}

	public static final class DummyNonSqlQueryFilter<T> implements IQueryFilter<T>
	{
		private static int nextId = 1;
		private final int id;

		public DummyNonSqlQueryFilter()
		{
			super();
			this.id = nextId;
			nextId++;
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "-" + id;
		}

		@Override
		public boolean accept(T model)
		{
			Assert.fail("accept method shall not be called for " + this);
			return true;
		}
	}

	public static final class DummySqlQueryFilter<T> implements ISqlQueryFilter, IQueryFilter<T>
	{
		private static int nextId = 1;
		private final int id;
		private final String sql;
		private final List<Object> sqlParams;

		public DummySqlQueryFilter()
		{
			super();

			this.id = nextId;
			nextId++;

			this.sql = "/*" + DummySqlQueryFilter.class.getSimpleName() + "-ID=" + id + "*/ 1=1";
			this.sqlParams = Arrays.<Object> asList("SqlQuery" + id + "-param1");
		}

		@Override
		public String toString()
		{
			return getClass().getSimpleName() + "-" + id;
		}

		@Override
		public String getSql()
		{
			return sql;
		}

		@Override
		public List<Object> getSqlParams(Properties ctx)
		{
			return sqlParams;
		}

		@Override
		public boolean accept(T model)
		{
			Assert.fail("accept method shall not be called for " + this);
			return true;
		}
	}

	private Properties ctx;

	@Before
	public void init()
	{
		ctx = new Properties();
	}

	@Test
	public void Defaults()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		assertDefaults(filter);

	}

	@Test
	public void DefaultAccept_True()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		filter.setDefaultAccept(true);

		assertSqlQueryFilter("Invalid filter", filter, CompositeQueryFilter.DEFAULT_SQL_TRUE, Collections.emptyList());
	}

	@Test
	public void DefaultAccept_False()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		filter.setDefaultAccept(false);

		assertSqlQueryFilter("Invalid filter", filter, CompositeQueryFilter.DEFAULT_SQL_FALSE, Collections.emptyList());
	}

	@Test
	public void addFilter_Duplicate()
	{
		final CompositeQueryFilter<I_ModelClass> filters = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummyNonSqlQueryFilter<I_ModelClass> filterToAdd = new DummyNonSqlQueryFilter<I_ModelClass>();

		filters.addFilter(filterToAdd);
		Assert.assertEquals("Invalid getFilters(): " + filters, Collections.singletonList(filterToAdd), filters.getFilters());

		// Add it again and check to not add duplicates
		filters.addFilter(filterToAdd);
		Assert.assertEquals("Invalid getFilters(): " + filters, Collections.singletonList(filterToAdd), filters.getFilters());
	}

	@Test(expected = RuntimeException.class)
	public void addFilter_NULL()
	{
		final CompositeQueryFilter<I_ModelClass> filters = new CompositeQueryFilter<>(I_ModelClass.class);

		final IQueryFilter<I_ModelClass> filterToAdd = null;
		filters.addFilter(filterToAdd); // expect exception here
	}

	@Test
	public void addFilters_NullList()
	{
		final CompositeQueryFilter<I_ModelClass> filters = new CompositeQueryFilter<>(I_ModelClass.class);

		final List<IQueryFilter<I_ModelClass>> filtersToAdd = null;
		filters.addFilters(filtersToAdd); // shall do nothing

		assertEmpty(filters);
	}

	@Test
	public void addFilters_EmptyList()
	{
		final CompositeQueryFilter<I_ModelClass> filters = new CompositeQueryFilter<>(I_ModelClass.class);

		final List<IQueryFilter<I_ModelClass>> filtersToAdd = new ArrayList<IQueryFilter<I_ModelClass>>();
		filters.addFilters(filtersToAdd); // shall do nothing

		assertEmpty(filters);
	}

	@Test(expected = RuntimeException.class)
	public void addFilters_ListWithOneNullValue()
	{
		final CompositeQueryFilter<I_ModelClass> filters = new CompositeQueryFilter<>(I_ModelClass.class);

		final List<IQueryFilter<I_ModelClass>> filtersToAdd = new ArrayList<IQueryFilter<I_ModelClass>>();
		filtersToAdd.add(new DummyNonSqlQueryFilter<I_ModelClass>());
		filtersToAdd.add(null);
		filtersToAdd.add(new DummyNonSqlQueryFilter<I_ModelClass>());

		filters.addFilters(filtersToAdd); // expect exception here
	}

	@Test
	public void compile_empty()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);

		assertDefaults(filter);
		assertPureSql(filter);
		assertPartialSql(filter, CompositeQueryFilter.DEFAULT_SQL_TRUE, Collections.emptyList());
	}

	@Test
	public void empty_IsPureSQL()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		filter.setJoinAnd();
		assertEmpty(filter);
		assertPureSql(filter);
	}

	@Test
	public void compile_PureSQL()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);

		filter.addEqualsFilter(I_ModelClass.COLUMNNAME_Column1, 1);
		filter.addEqualsFilter(I_ModelClass.COLUMNNAME_Column2, 2);

		assertDefaults(filter);
		assertPureSql(filter, "(Column1 = ?) AND (Column2 = ?)", Arrays.<Object> asList(1, 2));
	}

	@Test
	public void compile_PureNonSQL()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);

		final DummyNonSqlQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		filter.addFilter(nonSqlFilter);

		assertDefaults(filter);
		assertPureNonSql(filter);
		Assert.assertEquals("Invalid NonSqlFilters: " + filter, Collections.singletonList(nonSqlFilter), filter.getNonSqlFilters());
	}

	// see http://stackoverflow.com/questions/1445233/is-it-possible-to-solve-the-a-generic-array-of-t-is-created-for-a-varargs-param
	@Test
	public void compile_MixedFiltersWithJoinAND_AddSqlFilter_AddNonSqlFilter()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);

		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();

		filter.setJoinAnd(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);

		Assert.assertEquals("Invalid SqlFilter: " + filter, Arrays.asList(sqlFilter), filter.getSqlFilters());
		Assert.assertEquals("Invalid NonSqlFilters: " + filter, Arrays.asList(nonSqlFilter), filter.getNonSqlFilters());
		assertPartialSql(filter, "(" + sqlFilter.getSql() + ")", sqlFilter.getSqlParams(ctx));
	}

	@Test
	public void compile_MixedFiltersWithJoinAND_AddNonSqlFilter_AddSqlFilter()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);

		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();

		filter.setJoinAnd(); // just to be sure
		filter.addFilter(nonSqlFilter);
		filter.addFilter(sqlFilter);

		Assert.assertEquals("Invalid SqlFilter: " + filter, Arrays.asList(sqlFilter), filter.getSqlFilters());
		Assert.assertEquals("Invalid NonSqlFilters: " + filter, Arrays.asList(nonSqlFilter), filter.getNonSqlFilters());
		assertPartialSql(filter, "(" + sqlFilter.getSql() + ")", sqlFilter.getSqlParams(ctx));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void compile_MixedFiltersWithJoinAND_Including_MixedFiltersWithJoinAND()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		includedFilter.setJoinAnd(); // just to be sure
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		filter.setJoinAnd(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		Assert.assertEquals("Invalid SqlFilter: " + filter, Arrays.asList(sqlFilter, includedSqlFilter), filter.getSqlFilters());
		Assert.assertEquals("Invalid NonSqlFilters: " + filter, Arrays.asList(nonSqlFilter, includedNonSqlFilter), filter.getNonSqlFilters());
		assertPartialSql(filter,
				"(" + sqlFilter.getSql() + ")" + CompositeQueryFilter.SQL_AND + "(" + includedSqlFilter.getSql() + ")", // sql
				join(sqlFilter.getSqlParams(ctx), includedSqlFilter.getSqlParams(ctx)) // sql params
		);
	}

	@Test
	public void compile_MixedFiltersWithJoinAND_Including_MixedFiltersWithJoinOR()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		includedFilter.setJoinOr();
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		filter.setJoinAnd(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		Assert.assertEquals("Invalid IsPureSQL: " + filter, false, filter.isPureSql());
		Assert.assertEquals("Invalid SqlFilter: " + filter, Arrays.asList(sqlFilter), filter.getSqlFilters());
		Assert.assertEquals("Invalid NonSqlFilters: " + filter, Arrays.asList(nonSqlFilter, includedFilter), filter.getNonSqlFilters());
		assertPartialSql(filter,
				"(" + sqlFilter.getSql() + ")",
				sqlFilter.getSqlParams(ctx));
	}

	@Test
	public void compile_MixedFiltersWithJoinOR()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);

		final IQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();

		filter.setJoinOr(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);

		// Because when JOIN mode is "OR" we cannot create partial SQLs, all filters will be considered as nonSQL
		assertPureNonSql(filter);
	}

	@Test
	public void compile_MixedFiltersWithJoinOR_Including_MixedFiltersWithJoinAND()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		includedFilter.setJoinAnd();
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		filter.setJoinOr();
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		assertPureNonSql(filter);
		Assert.assertEquals("Invalid NonSqlFilters: " + filter, Arrays.asList(sqlFilter, nonSqlFilter, includedFilter), filter.getNonSqlFilters());
	}

	@Test
	public void compile_PureSqlWithJoinOR_Including_PureSqlWithJoinOr()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		includedFilter.setJoinAnd();
		includedFilter.addFilter(includedSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		filter.setJoinOr();
		filter.addFilter(sqlFilter);
		filter.addFilter(includedFilter);

		assertPureSql(filter);
		Assert.assertEquals("Invalid SqlFilters: " + filter, Arrays.asList(sqlFilter, includedFilter), filter.getSqlFilters());
	}

	@Test
	public void compile_MixedFiltersWithJoinOR_Including_MixedFiltersWithJoinOR()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		includedFilter.setJoinOr();
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<I_ModelClass>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<I_ModelClass>();
		filter.setJoinOr();
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		assertPureNonSql(filter);
		Assert.assertEquals("Invalid NonSqlFilter: " + filter, Arrays.asList(sqlFilter, nonSqlFilter, includedFilter), filter.getNonSqlFilters());
	}

	@Test(expected = RuntimeException.class)
	public void filter_mayNotIncludeItself()
	{
		final CompositeQueryFilter<I_ModelClass> filter = new CompositeQueryFilter<>(I_ModelClass.class);
		filter.addFilter(filter);
	}

	//
	// Helper methods ------------------------------------------------------------
	//

	private final static <T> List<T> join(final List<T>... lists)
	{
		if (lists == null || lists.length == 0)
		{
			return Collections.emptyList();
		}

		final List<T> result = new ArrayList<T>();
		for (final List<T> list : lists)
		{
			result.addAll(list);
		}

		return result;
	}

	private <T> void assertDefaults(final ICompositeQueryFilter<T> filter)
	{
		Assert.assertEquals("Invalid Default - DefaultAccept", true, filter.isDefaultAccept());
		Assert.assertEquals("Invalid Default - Join AND", true, filter.isJoinAnd());
		Assert.assertEquals("Invalid Default - Join OR", false, filter.isJoinOr());
	}

	private <T> void assertEmpty(final ICompositeQueryFilter<T> filter)
	{
		Assert.assertEquals("Invalid isEmpty: " + filter, true, filter.isEmpty());

		// NOTE: an empty composite filter shall be a PureSQL filter
		assertPureSql(filter);
	}

	private <T> void assertPureSql(final ICompositeQueryFilter<T> filter)
	{
		Assert.assertEquals("Invalid PureSQL - IsPureSql: " + filter, true, filter.isPureSql());
		Assert.assertEquals("Invalid PureSQL - IsPureNonSql: " + filter, false, filter.isPureNonSql());
		Assert.assertEquals("Invalid PureSQL - Partial NonSqlFilter: " + filter, null, filter.asPartialNonSqlFilterOrNull());
	}

	private <T> void assertPureNonSql(final ICompositeQueryFilter<T> filter)
	{
		Assert.assertEquals("Invalid Pure nonSQL - IsPureSql: " + filter, false, filter.isPureSql());
		Assert.assertEquals("Invalid Pure nonSQL - IsPureNonSql: " + filter, true, filter.isPureNonSql());
		Assert.assertEquals("Invalid Pure nonSQL - SqlFilters: " + filter, Collections.emptyList(), filter.getSqlFilters());
		Assert.assertNotNull("Invalid Pure nonSQL - Partial NonSqlFilter: " + filter, filter.asPartialNonSqlFilterOrNull());

		Assert.assertEquals("Invalid Pure nonSQL - NonSqlFilters shall be the same as all filters: " + filter,
				filter.getFilters(),
				filter.getNonSqlFilters());

		// Make sure build SQL is empty
		assertPartialSql(filter, "", Collections.emptyList());

		// Because it's not a pure SQL, filter.asSqlQueryFilter() shall throw an exception
		assertAsSqlQueryFilterFails(filter);
	}

	private <T> void assertPartialSql(final ICompositeQueryFilter<T> filter,
			final String expectedSqlFiltersWhereClause,
			final List<Object> expectedSqlFiltersParam)
	{
		Assert.assertEquals("Invalid Partial SQL - SQL: " + filter, expectedSqlFiltersWhereClause, filter.getSqlFiltersWhereClause());
		Assert.assertEquals("Invalid Partial SQL - SQL Params: " + filter, expectedSqlFiltersParam, filter.getSqlFiltersParams(ctx));

		//
		// Also validate sql query filter returned by "asPartialSqlQueryFilter" with same expectations
		final ISqlQueryFilter partialSqlQueryFilter = filter.asPartialSqlQueryFilter();
		assertSqlQueryFilter("Invalid PartialSqlQueryFilter", partialSqlQueryFilter, expectedSqlFiltersWhereClause, expectedSqlFiltersParam);
	}

	private void assertSqlQueryFilter(final String messagePrefix,
			final ISqlQueryFilter filter,
			final String expectedSql,
			final List<Object> expectedSqlParam)
	{
		Assert.assertNotNull(messagePrefix + " - NOT NULL: " + filter, filter);
		Assert.assertEquals(messagePrefix + " - SQL: " + filter, expectedSql, filter.getSql());
		Assert.assertEquals(messagePrefix + " - SQL Params: " + filter, expectedSqlParam, filter.getSqlParams(ctx));
	}

	private <T> void assertPureSql(final ICompositeQueryFilter<T> filter,
			final String expectedSqlFiltersWhereClause,
			final List<Object> expectedSqlFiltersParam)
	{
		assertPureSql(filter);
		assertPartialSql(filter, expectedSqlFiltersWhereClause, expectedSqlFiltersParam);
	}

	private <T> void assertAsSqlQueryFilterFails(final ICompositeQueryFilter<T> filter)
	{
		IllegalStateException exception = null;
		try
		{
			filter.asSqlQueryFilter();
		}
		catch (IllegalStateException e)
		{
			exception = e;
		}

		Assert.assertNotNull("filter.asSqlQueryFilter() expected to throw exception: " + filter, exception);
	}

}
