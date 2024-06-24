package de.metas.ui.web.window.descriptor.sql;

import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SqlOrderByValueTest
{
	@Test
	void sqlSelectDisplayValue()
	{
		final SqlOrderByValue sqlOrderByValue = SqlOrderByValue.builder()
				.sqlSelectDisplayValue(SqlSelectDisplayValue.builder()
						.joinOnTableNameOrAlias("joinOnTableNameOrAlias")
						.joinOnColumnName("joinOnColumnName")
						.sqlExpression(SqlForFetchingLookupById.builder()
								.keyColumnNameFQ("keyColumnNameFQ")
								.numericKey(true)
								.displayColumn(ConstantStringExpression.of("displayColumn"))
								.descriptionColumn(ConstantStringExpression.of("descriptionColumn"))
								.activeColumn("IsActive")
								.validationMsgColumn(ConstantStringExpression.of("validationMsgColumn"))
								.sqlFrom(ConstantStringExpression.of("sqlFrom"))
								.additionalWhereClause("additionalWhereClause")
								.build())
						.columnNameAlias("columnName$Display")
						.build())
				.joinOnTableNameOrAlias("joinOnTableNameOrAlias2")
				.build();

		assertThat(sqlOrderByValue.isNull()).isFalse();
		assertThat(sqlOrderByValue.toSqlUsingColumnNameAlias())
				.isEqualTo("joinOnTableNameOrAlias2.columnName$Display[2]");
		assertThat(sqlOrderByValue.toSourceSqlExpression().evaluate(Evaluatees.empty(), IExpressionEvaluator.OnVariableNotFound.Fail))
				.isEqualTo("SELECT "
						+ "\n displayColumn"
						+ "\n FROM sqlFrom"
						+ "\n WHERE keyColumnNameFQ=joinOnTableNameOrAlias2.joinOnColumnName AND additionalWhereClause");
	}

	@Nested
	class sqlSelectValue
	{
		@Test
		void normalColumn()
		{
			final SqlOrderByValue sqlOrderByValue = SqlOrderByValue.builder()
					.sqlSelectValue(SqlSelectValue.builder()
							.tableNameOrAlias("tableNameOrAlias")
							.columnName("columnName")
							.virtualColumnSql(null)
							.columnNameAlias("columnNameAlias")
							.build())
					.joinOnTableNameOrAlias("joinOnTableNameOrAlias2")
					.build();

			assertThat(sqlOrderByValue.isNull()).isFalse();
			assertThat(sqlOrderByValue.toSqlUsingColumnNameAlias())
					.isEqualTo("joinOnTableNameOrAlias2.columnNameAlias");
			assertThat(sqlOrderByValue.toSourceSqlExpression().evaluate(Evaluatees.empty(), IExpressionEvaluator.OnVariableNotFound.Fail))
					.isEqualTo("joinOnTableNameOrAlias2.columnName");
		}

		@Test
		void virtualColumn()
		{
			final SqlOrderByValue sqlOrderByValue = SqlOrderByValue.builder()
					.sqlSelectValue(SqlSelectValue.builder()
							.virtualColumnSql(ColumnSql.ofSql("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=C_OrderLine.M_Product_ID", "C_OrderLine"))
							.columnNameAlias("ProductName")
							.build())
					.joinOnTableNameOrAlias("master")
					.build();

			assertThat(sqlOrderByValue.isNull()).isFalse();
			assertThat(sqlOrderByValue.toSqlUsingColumnNameAlias())
					.isEqualTo("master.ProductName");
			assertThat(sqlOrderByValue.toSourceSqlExpression().evaluate(Evaluatees.empty(), IExpressionEvaluator.OnVariableNotFound.Fail))
					.isEqualTo("(SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=master.M_Product_ID)");
		}

		@Test
		void virtualColumn_with_JoinTableNameOrAliasIncludingDot_in_subquery()
		{
			final SqlOrderByValue sqlOrderByValue = SqlOrderByValue.builder()
					.sqlSelectValue(SqlSelectValue.builder()
							.virtualColumnSql(ColumnSql.ofSql("SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=@JoinTableNameOrAliasIncludingDot@Parent_Product_ID", "C_OrderLine"))
							.columnNameAlias("ParentProductName")
							.build())
					.joinOnTableNameOrAlias("master")
					.build();

			assertThat(sqlOrderByValue.isNull()).isFalse();
			assertThat(sqlOrderByValue.toSqlUsingColumnNameAlias())
					.isEqualTo("master.ParentProductName");
			assertThat(sqlOrderByValue.toSourceSqlExpression().evaluate(Evaluatees.empty(), IExpressionEvaluator.OnVariableNotFound.Fail))
					.isEqualTo("(SELECT Name FROM M_Product WHERE M_Product.M_Product_ID=master.Parent_Product_ID)");
		}
	}

	@Test
	void columnName()
	{
		final SqlOrderByValue sqlOrderByValue = SqlOrderByValue.builder()
				.columnName("columnName")
				.joinOnTableNameOrAlias("joinOnTableNameOrAlias2")
				.build();

		assertThat(sqlOrderByValue.isNull()).isFalse();
		assertThat(sqlOrderByValue.toSqlUsingColumnNameAlias())
				.isEqualTo("joinOnTableNameOrAlias2.columnName");
		assertThat(sqlOrderByValue.toSourceSqlExpression().evaluate(Evaluatees.empty(), IExpressionEvaluator.OnVariableNotFound.Fail))
				.isEqualTo("joinOnTableNameOrAlias2.columnName");
	}

	@Test
	void allNulls()
	{
		final SqlOrderByValue sqlOrderByValue = SqlOrderByValue.builder()
				.joinOnTableNameOrAlias("joinOnTableNameOrAlias2")
				.build();

		assertThat(sqlOrderByValue.isNull()).isTrue();
		assertThat(sqlOrderByValue.toSqlUsingColumnNameAlias())
				.isNull();
		assertThat(sqlOrderByValue.toSourceSqlExpression())
				.isSameAs(IStringExpression.NULL);
	}

}