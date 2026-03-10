package de.metas.ui.web.document.filter.sql;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.PlainSqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;
import org.assertj.core.api.RecursiveComparisonAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

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
	private SqlDefaultDocumentFilterConverter converter;

	@Builder(builderMethodName = "newConverter", builderClassName = "$ConverterBuilder")
	SqlDefaultDocumentFilterConverter createSqlDefaultDocumentFilterConverter(
			@NonNull final String columnName,
			@NonNull final DocumentFieldWidgetType widgetType)
	{
		final SqlEntityBinding entityBinding = Mockito.mock(SqlEntityBinding.class);

		Mockito.doReturn(PlainSqlEntityFieldBinding.builder()
						.columnName(columnName)
						.widgetType(widgetType)
						.sqlSelectValue(SqlSelectValue.builder()
								.columnName(columnName)
								.columnNameAlias(columnName)
								.build())
						.build())
				.when(entityBinding)
				.getFieldByFieldName(columnName);

		return SqlDefaultDocumentFilterConverter.newInstance(entityBinding);
	}

	@SuppressWarnings("SameParameterValue")
	static DocumentFilter filter(@NonNull final String fieldName, @NonNull final Operator operator, @Nullable final Object value)
	{
		return DocumentFilter.builder()
				.filterId("filterId")
				.parameter(DocumentFilterParam.builder()
						.setFieldName(fieldName)
						.setOperator(operator)
						.setValue(value)
						.build())
				.build();
	}

	@SuppressWarnings("SameParameterValue")
	static DocumentFilter betweenFilter(@NonNull final String fieldName, @Nullable final Object valueFrom, @Nullable final Object valueTo)
	{
		return DocumentFilter.builder()
				.filterId("filterId")
				.parameter(DocumentFilterParam.builder()
						.setFieldName(fieldName)
						.setOperator(Operator.BETWEEN)
						.setValue(valueFrom)
						.setValueTo(valueTo)
						.build())
				.build();
	}

	RecursiveComparisonAssert<?> assertSqlWhereClause(@NonNull final DocumentFilter filter)
	{
		final SqlOptions sqlOpts = SqlOptions.usingTableName("MyTable");
		final SqlDocumentFilterConverterContext context = SqlDocumentFilterConverterContext.builder().build();
		final FilterSql sql = converter.getSql(FilterSqlRequest.builder()
				.filter(filter)
				.sqlOpts(sqlOpts)
				.context(context)
				.allFilters(DocumentFilterList.of(filter))
				.build());
		final SqlAndParams sqlWhereClause = sql != null ? sql.getWhereClause() : null;
		return assertThat(sqlWhereClause)
				.usingRecursiveComparison();
	}

	//
	//
	//
	//
	//

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

	//
	//
	//
	//
	//

	@Nested
	public class buildSqlWhereClause
	{
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

	//
	//
	//
	//
	//

	@Nested
	public class getSql
	{
		@Nested
		public class Equals
		{
			@BeforeEach
			void beforeEach()
			{
				converter = newConverter().columnName("MyColumn").widgetType(DocumentFieldWidgetType.Text).build();
			}

			@Test
			public void equals_to_123()
			{
				assertSqlWhereClause(filter("MyColumn", Operator.EQUAL, "123"))
						.isEqualTo(SqlAndParams.of("(MyColumn = ?)", Arrays.asList("123")));
			}
		}

		@Nested
		public class NotEquals
		{
			@BeforeEach
			void beforeEach()
			{
				converter = newConverter().columnName("MyColumn").widgetType(DocumentFieldWidgetType.Text).build();
			}

			@Test
			public void equals_to_123()
			{
				assertSqlWhereClause(filter("MyColumn", Operator.NOT_EQUAL, "123"))
						.isEqualTo(SqlAndParams.of("(MyColumn <> ?)", Arrays.asList("123")));
			}
		}

		@Nested
		public class Between
		{
			@BeforeEach
			void beforeEach()
			{
				converter = newConverter().columnName("Balance").widgetType(DocumentFieldWidgetType.Number).build();
			}

			@Test
			public void between_null_values()
			{
				assertSqlWhereClause(betweenFilter("Balance", null, null))
						.isNull();
			}

			@Test
			public void between_100_and_null()
			{
				assertSqlWhereClause(betweenFilter("Balance", "100", null))
						.isEqualTo(SqlAndParams.of("(Balance>=?)", Arrays.asList(new BigDecimal("100"))));
			}

			@Test
			public void between_null_and_200()
			{
				assertSqlWhereClause(betweenFilter("Balance", null, "200"))
						.isEqualTo(SqlAndParams.of("(Balance<=?)", Arrays.asList(new BigDecimal("200"))));

			}

			@Test
			public void between_100_and_200()
			{
				assertSqlWhereClause(betweenFilter("Balance", "100", "200"))
						.isEqualTo(SqlAndParams.of("(Balance BETWEEN ? AND ?)", Arrays.asList(new BigDecimal("100"), new BigDecimal("200"))));
			}
		}
	}

}
