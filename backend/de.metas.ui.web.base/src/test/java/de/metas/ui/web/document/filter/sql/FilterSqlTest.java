/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.document.filter.sql;

import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.model.sql.SqlOptions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class FilterSqlTest
{

	@Nested
	@TestMethodOrder(MethodOrderer.DisplayName.class)
	class toSqlAndParams
	{
		private FilterSql.FullTextSearchResult mockFilterByFTS(
				final SqlOptions sqlOpts,
				final String returnSql,
				final Object... returnSqlParams)
		{
			final FilterSql.FullTextSearchResult filterByFTS = Mockito.mock(FilterSql.FullTextSearchResult.class);
			Mockito.doReturn(SqlAndParams.of(returnSql, returnSqlParams))
					.when(filterByFTS)
					.buildExistsWhereClause(sqlOpts.getTableNameOrAlias());

			return filterByFTS;
		}

		private FilterSql.RecordsToAlwaysIncludeSql mockAlwaysIncludeSql(
				final String returnSql,
				final Object... returnSqlParams)
		{
			final FilterSql.RecordsToAlwaysIncludeSql alwaysIncludeSql = Mockito.mock(FilterSql.RecordsToAlwaysIncludeSql.class);
			Mockito.doReturn(SqlAndParams.of(returnSql, returnSqlParams))
					.when(alwaysIncludeSql)
					.toSqlAndParams();

			return alwaysIncludeSql;
		}

		@Test
		void $warmUpMockito()
		{
			mockAlwaysIncludeSql("something");
		}

		@Test
		void allowAll()
		{
			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("alias");
			assertThat(FilterSql.ALLOW_ALL.toSqlAndParams(sqlOpts))
					.isEmpty();
		}

		@Test
		void allowNone()
		{
			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("alias");
			assertThat(FilterSql.ALLOW_NONE.toSqlAndParams(sqlOpts))
					.contains(SqlAndParams.of("1=0"));
		}

		@Test
		void whereClause()
		{
			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("alias");
			assertThat(FilterSql.builder()
					.whereClause(SqlAndParams.of("some where clause", 1, 2, 3))
					.build()
					.toSqlAndParams(sqlOpts))
					.contains(SqlAndParams.of("some where clause", 1, 2, 3));
		}

		@Test
		void whereClause_filterByFTS()
		{
			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("alias");

			assertThat(FilterSql.builder()
					.whereClause(SqlAndParams.of("some where clause", 1, 2, 3))
					.filterByFTS(mockFilterByFTS(sqlOpts, "fts_filter", "fts_param1"))
					.build()
					.toSqlAndParams(sqlOpts))
					.contains(SqlAndParams.of(
							"(some where clause) AND (fts_filter)",
							1, 2, 3, "fts_param1"));
		}

		@Test
		void whereClause_alwaysIncludeSql()
		{
			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("alias");
			assertThat(FilterSql.builder()
					.whereClause(SqlAndParams.of("some where clause", 1, 2, 3))
					.alwaysIncludeSql(mockAlwaysIncludeSql("alwaysIncludeSql", 10, 11, 12))
					.build()
					.toSqlAndParams(sqlOpts))
					.contains(SqlAndParams.of(
							"(some where clause) OR (alwaysIncludeSql)",
							1, 2, 3, 10, 11, 12));
		}

		@Test
		void whereClause_filterByFTS_alwaysIncludeSql()
		{
			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("alias");

			assertThat(FilterSql.builder()
					.whereClause(SqlAndParams.of("some where clause", 1, 2, 3))
					.filterByFTS(mockFilterByFTS(sqlOpts, "fts_filter", "fts_param1"))
					.alwaysIncludeSql(mockAlwaysIncludeSql("alwaysIncludeSql", 10, 11, 12))
					.build()
					.toSqlAndParams(sqlOpts))
					.contains(SqlAndParams.of(
							"((some where clause) AND (fts_filter)) OR (alwaysIncludeSql)",
							1, 2, 3, "fts_param1", 10, 11, 12));
		}

	}
}