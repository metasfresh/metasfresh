package org.adempiere.ad.dao.impl;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit test for {@link CompositeQueryFilter}
 *
 * @author tsa
 */
public class CompositeQueryFilterTest
{
	public interface I_ModelClass
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
			Assertions.fail("accept method shall not be called for " + this);
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
			this.sqlParams = Collections.singletonList("SqlQuery" + id + "-param1");
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
			Assertions.fail("accept method shall not be called for " + this);
			return true;
		}
	}

	private Properties ctx;

	@BeforeEach
	public void init()
	{
		ctx = new Properties();
	}

	@Test
	public void Defaults()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		assertDefaults(filter);

	}

	@Test
	public void DefaultAccept_True()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		filter.setDefaultAccept(true);

		assertSqlQueryFilter("Invalid filter", filter, CompositeQueryFilter.DEFAULT_SQL_TRUE, Collections.emptyList());
	}

	@Test
	public void DefaultAccept_False()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		filter.setDefaultAccept(false);

		assertSqlQueryFilter("Invalid filter", filter, CompositeQueryFilter.DEFAULT_SQL_FALSE, Collections.emptyList());
	}

	@Test
	public void addFilter_Duplicate()
	{
		final CompositeQueryFilter<I_ModelClass> filters = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummyNonSqlQueryFilter<I_ModelClass> filterToAdd = new DummyNonSqlQueryFilter<>();

		filters.addFilter(filterToAdd);
		Assertions.assertEquals(Collections.singletonList(filterToAdd), filters.getFilters(), "Invalid getFilters(): " + filters);

		// Add it again and check to not add duplicates
		filters.addFilter(filterToAdd);
		Assertions.assertEquals(Collections.singletonList(filterToAdd), filters.getFilters(), "Invalid getFilters(): " + filters);
	}

	@Test
	public void addFilter_NULL()
	{
		final CompositeQueryFilter<I_ModelClass> filters = CompositeQueryFilter.newInstance(I_ModelClass.class);

		assertThatThrownBy(() -> {
			//noinspection DataFlowIssue
			filters.addFilter(null);
		})
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("filter");
	}

	@Test
	public void addFilters_NullList()
	{
		final CompositeQueryFilter<I_ModelClass> filters = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final List<IQueryFilter<I_ModelClass>> filtersToAdd = null;
		filters.addFilters(filtersToAdd); // shall do nothing

		assertEmpty(filters);
	}

	@Test
	public void addFilters_EmptyList()
	{
		final CompositeQueryFilter<I_ModelClass> filters = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final List<IQueryFilter<I_ModelClass>> filtersToAdd = new ArrayList<>();
		filters.addFilters(filtersToAdd); // shall do nothing

		assertEmpty(filters);
	}

	@Test
	public void addFilters_ListWithOneNullValue()
	{
		final CompositeQueryFilter<I_ModelClass> filters = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final List<IQueryFilter<I_ModelClass>> filtersAndNulls = new ArrayList<>();
		filtersAndNulls.add(new DummyNonSqlQueryFilter<>());
		filtersAndNulls.add(null);
		filtersAndNulls.add(new DummyNonSqlQueryFilter<>());

		assertThatThrownBy(() -> filters.addFilters(filtersAndNulls))
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("filter");
	}

	@Test
	public void compile_empty()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		assertDefaults(filter);
		assertPureSql(filter);
		assertPartialSql(filter, CompositeQueryFilter.DEFAULT_SQL_TRUE, Collections.emptyList());
	}

	@Test
	public void empty_IsPureSQL()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		filter.setJoinAnd();
		assertEmpty(filter);
		assertPureSql(filter);
	}

	@Test
	public void compile_PureSQL()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		filter.addEqualsFilter(I_ModelClass.COLUMNNAME_Column1, 1);
		filter.addEqualsFilter(I_ModelClass.COLUMNNAME_Column2, 2);

		assertDefaults(filter);
		assertPureSql(filter, "(Column1 = ?) AND (Column2 = ?)", Arrays.asList(1, 2));
	}

	@Test
	public void compile_PureNonSQL()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final DummyNonSqlQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();
		filter.addFilter(nonSqlFilter);

		assertDefaults(filter);
		assertPureNonSql(filter);
		Assertions.assertEquals(Collections.singletonList(nonSqlFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);
	}

	// see http://stackoverflow.com/questions/1445233/is-it-possible-to-solve-the-a-generic-array-of-t-is-created-for-a-varargs-param
	@Test
	public void compile_MixedFiltersWithJoinAND_AddSqlFilter_AddNonSqlFilter()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();

		filter.setJoinAnd(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);

		Assertions.assertEquals(Collections.singletonList(sqlFilter), filter.getSqlFilters(), "Invalid SqlFilter: " + filter);
		Assertions.assertEquals(Collections.singletonList(nonSqlFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);
		assertPartialSql(filter, "(" + sqlFilter.getSql() + ")", sqlFilter.getSqlParams(ctx));
	}

	@Test
	public void compile_MixedFiltersWithJoinAND_AddNonSqlFilter_AddSqlFilter()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();

		filter.setJoinAnd(); // just to be sure
		filter.addFilter(nonSqlFilter);
		filter.addFilter(sqlFilter);

		Assertions.assertEquals(Collections.singletonList(sqlFilter), filter.getSqlFilters(), "Invalid SqlFilter: " + filter);
		Assertions.assertEquals(Collections.singletonList(nonSqlFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);
		assertPartialSql(filter, "(" + sqlFilter.getSql() + ")", sqlFilter.getSqlParams(ctx));
	}

	@Test
	public void compile_MixedFiltersWithJoinAND_Including_MixedFiltersWithJoinAND()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<>();
		includedFilter.setJoinAnd(); // just to be sure
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();
		filter.setJoinAnd(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		Assertions.assertEquals(Arrays.asList(sqlFilter, includedSqlFilter), filter.getSqlFilters(), "Invalid SqlFilter: " + filter);
		Assertions.assertEquals(Arrays.asList(nonSqlFilter, includedNonSqlFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);
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
		final CompositeQueryFilter<I_ModelClass> includedFilter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<>();
		includedFilter.setJoinOr();
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();
		filter.setJoinAnd(); // just to be sure
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		Assertions.assertFalse(filter.isPureSql(), "Invalid IsPureSQL: " + filter);
		Assertions.assertEquals(Collections.singletonList(sqlFilter), filter.getSqlFilters(), "Invalid SqlFilter: " + filter);
		Assertions.assertEquals(Arrays.asList(nonSqlFilter, includedFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);
		assertPartialSql(filter,
				"(" + sqlFilter.getSql() + ")",
				sqlFilter.getSqlParams(ctx));
	}

	@Test
	public void compile_MixedFiltersWithJoinOR()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		final IQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();

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
		final CompositeQueryFilter<I_ModelClass> includedFilter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<>();
		includedFilter.setJoinAnd();
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();
		filter.setJoinOr();
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		assertPureNonSql(filter);
		Assertions.assertEquals(Arrays.asList(sqlFilter, nonSqlFilter, includedFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);
	}

	@Test
	public void compile_PureSqlWithJoinOR_Including_PureSqlWithJoinOr()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<>();
		includedFilter.setJoinAnd();
		includedFilter.addFilter(includedSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		filter.setJoinOr();
		filter.addFilter(sqlFilter);
		filter.addFilter(includedFilter);

		assertPureSql(filter);
		Assertions.assertEquals(Arrays.asList(sqlFilter, includedFilter), filter.getSqlFilters(), "Invalid SqlFilters: " + filter);
	}

	@Test
	public void compile_MixedFiltersWithJoinOR_Including_MixedFiltersWithJoinOR()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> includedFilter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> includedNonSqlFilter = new DummyNonSqlQueryFilter<>();
		includedFilter.setJoinOr();
		includedFilter.addFilter(includedSqlFilter);
		includedFilter.addFilter(includedNonSqlFilter);

		//
		// Filter
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> sqlFilter = new DummySqlQueryFilter<>();
		final IQueryFilter<I_ModelClass> nonSqlFilter = new DummyNonSqlQueryFilter<>();
		filter.setJoinOr();
		filter.addFilter(sqlFilter);
		filter.addFilter(nonSqlFilter);
		filter.addFilter(includedFilter);

		assertPureNonSql(filter);
		Assertions.assertEquals(Arrays.asList(sqlFilter, nonSqlFilter, includedFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilter: " + filter);
	}

	@Test
	public void filter_mayNotIncludeItself()
	{
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);

		assertThatThrownBy(() -> filter.addFilter(filter))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Attempt to add a filter to itself");
	}

	@Test
	public void compile_disallowSqlFilters()
	{
		//
		// Included Filter
		final CompositeQueryFilter<I_ModelClass> filter = CompositeQueryFilter.newInstance(I_ModelClass.class);
		final DummySqlQueryFilter<I_ModelClass> includedSqlFilter = new DummySqlQueryFilter<>();
		filter.addFilter(includedSqlFilter);

		//
		// Check before disabling the SQL filters
		Assertions.assertEquals(Collections.singletonList(includedSqlFilter), filter.getSqlFilters(), "Invalid SqlFilters: " + filter);
		Assertions.assertEquals(Collections.emptyList(), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);

		filter.allowSqlFilters(false);

		//
		Assertions.assertEquals(Collections.emptyList(), filter.getSqlFilters(), "Invalid SqlFilters: " + filter);
		Assertions.assertEquals(Collections.singletonList(includedSqlFilter), filter.getNonSqlFilters(), "Invalid NonSqlFilters: " + filter);

	}

	//
	// Helper methods ------------------------------------------------------------
	//

	@SafeVarargs
	private static <T> List<T> join(final List<T>... lists)
	{
		if (lists == null || lists.length == 0)
		{
			return Collections.emptyList();
		}

		final List<T> result = new ArrayList<>();
		for (final List<T> list : lists)
		{
			result.addAll(list);
		}

		return result;
	}

	private <T> void assertDefaults(final ICompositeQueryFilter<T> filter)
	{
		Assertions.assertTrue(filter.isDefaultAccept(), "Invalid Default - DefaultAccept");
		Assertions.assertTrue(filter.isJoinAnd(), "Invalid Default - Join AND");
		Assertions.assertFalse(filter.isJoinOr(), "Invalid Default - Join OR");
	}

	private <T> void assertEmpty(final ICompositeQueryFilter<T> filter)
	{
		Assertions.assertTrue(filter.isEmpty(), "Invalid isEmpty: " + filter);

		// NOTE: an empty composite filter shall be a PureSQL filter
		assertPureSql(filter);
	}

	private <T> void assertPureSql(final ICompositeQueryFilter<T> filter)
	{
		Assertions.assertTrue(filter.isPureSql(), "Invalid PureSQL - IsPureSql: " + filter);
		Assertions.assertFalse(filter.isPureNonSql(), "Invalid PureSQL - IsPureNonSql: " + filter);
		Assertions.assertNull(filter.asPartialNonSqlFilterOrNull(), "Invalid PureSQL - Partial NonSqlFilter: " + filter);
	}

	private <T> void assertPureNonSql(final ICompositeQueryFilter<T> filter)
	{
		Assertions.assertFalse(filter.isPureSql(), "Invalid Pure nonSQL - IsPureSql: " + filter);
		Assertions.assertTrue(filter.isPureNonSql(), "Invalid Pure nonSQL - IsPureNonSql: " + filter);
		Assertions.assertEquals(Collections.emptyList(), filter.getSqlFilters(), "Invalid Pure nonSQL - SqlFilters: " + filter);
		Assertions.assertNotNull(filter.asPartialNonSqlFilterOrNull(), "Invalid Pure nonSQL - Partial NonSqlFilter: " + filter);

		Assertions.assertEquals(
				filter.getFilters(),
				filter.getNonSqlFilters(), "Invalid Pure nonSQL - NonSqlFilters shall be the same as all filters: " + filter);

		// Make sure build SQL is empty
		assertPartialSql(filter, "", Collections.emptyList());

		// Because it's not a pure SQL, filter.asSqlQueryFilter() shall throw an exception
		assertAsSqlQueryFilterFails(filter);
	}

	private <T> void assertPartialSql(final ICompositeQueryFilter<T> filter,
									  final String expectedSqlFiltersWhereClause,
									  final List<Object> expectedSqlFiltersParam)
	{
		Assertions.assertEquals(expectedSqlFiltersWhereClause, filter.getSqlFiltersWhereClause(), "Invalid Partial SQL - SQL: " + filter);
		Assertions.assertEquals(expectedSqlFiltersParam, filter.getSqlFiltersParams(ctx), "Invalid Partial SQL - SQL Params: " + filter);

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
		Assertions.assertNotNull(filter, messagePrefix + " - NOT NULL: " + filter);
		Assertions.assertEquals(expectedSql, filter.getSql(), messagePrefix + " - SQL: " + filter);
		Assertions.assertEquals(expectedSqlParam, filter.getSqlParams(ctx), messagePrefix + " - SQL Params: " + filter);
	}

	@SuppressWarnings("SameParameterValue")
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

		Assertions.assertNotNull(exception, "filter.asSqlQueryFilter() expected to throw exception: " + filter);
	}

}
