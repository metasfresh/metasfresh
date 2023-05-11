package de.metas.ui.web.document.filter.sql;

import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.sql.SqlOptions;
import org.adempiere.ad.column.ColumnSql;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class SqlDefaultDocumentFilterConverterTest
{
	@Nested
	public class replaceTableNameWithTableAliasIfNeeded
	{
		@Test
		public void usingTableAlias()
		{
			final SqlEntityBinding entityBinding = Mockito.mock(SqlEntityBinding.class);
			Mockito.doReturn("MasterTableName").when(entityBinding).getTableName();

			final SqlDefaultDocumentFilterConverter converter = SqlDefaultDocumentFilterConverter.newInstance(entityBinding);
			final SqlSelectValue sqlSelectValue = SqlSelectValue.builder()
					.columnNameAlias("columnAlias")
					.virtualColumnSql(ColumnSql.ofSql("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla", "MasterTableName"))
					.build();

			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("master");

			assertThat(converter.replaceTableNameWithTableAliasIfNeeded(sqlSelectValue, sqlOpts).toSqlString())
					.isEqualTo("(SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=master.bla)");
		}

		@Test
		public void usingFullTableName()
		{
			final SqlEntityBinding entityBinding = Mockito.mock(SqlEntityBinding.class);
			Mockito.doReturn("MasterTableName").when(entityBinding).getTableName();

			final SqlDefaultDocumentFilterConverter converter = SqlDefaultDocumentFilterConverter.newInstance(entityBinding);
			final SqlSelectValue columnSql = SqlSelectValue.builder()
					.columnNameAlias("columnAlias")
					.virtualColumnSql(ColumnSql.ofSql("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla", "MasterTableName"))
					.build();

			final SqlOptions sqlOpts = SqlOptions.usingTableName("should_be_MasterTableName_but_DoesNotMatter");

			assertThat(converter.replaceTableNameWithTableAliasIfNeeded(columnSql, sqlOpts).toSqlString())
					.isEqualTo("(SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla)");
		}

		@Test
		public void usingFullTableName_with_JoinTableNameOrAliasIncludingDot_in_subquery()
		{
			final SqlEntityBinding entityBinding = Mockito.mock(SqlEntityBinding.class);
			Mockito.doReturn("MasterTableName").when(entityBinding).getTableName();

			final SqlDefaultDocumentFilterConverter converter = SqlDefaultDocumentFilterConverter.newInstance(entityBinding);
			final SqlSelectValue columnSql = SqlSelectValue.builder()
					.columnNameAlias("columnAlias")
					.virtualColumnSql(ColumnSql.ofSql("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=@JoinTableNameOrAliasIncludingDot@bla", "MasterTableName"))
					.build();

			final SqlOptions sqlOpts = SqlOptions.usingTableName("should_be_MasterTableName_but_DoesNotMatter");

			assertThat(converter.replaceTableNameWithTableAliasIfNeeded(columnSql, sqlOpts).toSqlString())
					.isEqualTo("(SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla)");
		}
	}

	@Nested
	public class buildSqlWhereClause_IsNull
	{
		@Test
		public void any()
		{
			assertThat(SqlDefaultDocumentFilterConverter.buildSqlWhereClause_IsNull("MyColumn", NullOperator.ANY))
					.isEqualTo(SqlAndParams.EMPTY);
		}

		@Test
		public void isNull()
		{
			assertThat(SqlDefaultDocumentFilterConverter.buildSqlWhereClause_IsNull("MyColumn", NullOperator.IS_NULL))
					.isEqualTo(SqlAndParams.of("MyColumn IS NULL"));
		}

		@Test
		public void isNotNull()
		{
			assertThat(SqlDefaultDocumentFilterConverter.buildSqlWhereClause_IsNull("MyColumn", NullOperator.IS_NOT_NULL))
					.isEqualTo(SqlAndParams.of("MyColumn IS NOT NULL"));
		}
	}

	@Nested
	public class buildSqlWhereClause_Equals
	{
		@Test
		public void nullValue()
		{
			final SqlAndParams result = SqlDefaultDocumentFilterConverter.buildSqlWhereClause_Equals(
					"MyColumn",
					null,
					false);
			assertThat(result).isEqualTo(SqlAndParams.of("MyColumn IS NULL"));
		}

		@Test
		public void nullValue_negate()
		{
			final SqlAndParams result = SqlDefaultDocumentFilterConverter.buildSqlWhereClause_Equals(
					"MyColumn",
					null,
					true);
			assertThat(result).isEqualTo(SqlAndParams.of("MyColumn IS NOT NULL"));
		}
	}

	@Nested
	public class buildSqlWhereClause_Like
	{
		@Test
		public void nullValue()
		{
			final SqlAndParams result = SqlDefaultDocumentFilterConverter.buildSqlWhereClause_Like(
					"MyColumn",
					false,
					false,
					null);
			assertThat(result).isEqualTo(SqlAndParams.of("MyColumn IS NULL"));
		}

		@Test
		public void nullValue_negate()
		{
			final SqlAndParams result = SqlDefaultDocumentFilterConverter.buildSqlWhereClause_Like(
					"MyColumn",
					true,
					false,
					null);
			assertThat(result).isEqualTo(SqlAndParams.of("MyColumn IS NOT NULL"));
		}
	}
}
