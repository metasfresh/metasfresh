package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookupById;
import de.metas.ui.web.window.descriptor.sql.SqlSelectDisplayValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.user.UserId;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.table.api.ColumnNameFQ;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link SqlViewSelectData#selectFieldValues(ViewEvaluationCtx, String, String, int)}.
 * <p>
 * gh#28680: Verifies the performance optimization that computes DISTINCT first,
 * then fetches display values only for the distinct results.
 */
class SqlViewSelectDataTest
{
	private static final String TABLE_NAME = "C_Order_M_InOut_C_Invoice_Overview_V";
	private static final String TABLE_ALIAS = "v";
	private static final String KEY_COLUMN = "Overview_ID";

	private ViewEvaluationCtx viewEvalCtx;

	@BeforeEach
	void beforeEach()
	{
		viewEvalCtx = ViewEvaluationCtx._builder()
				.loggedUserId(Optional.of(UserId.METASFRESH))
				.orgId(OrgId.ofRepoId(1000000))
				.adLanguage("en_US")
				.timeZone(ZoneId.of("Europe/Berlin"))
				.permissionsKey(UserRolePermissionsKey.of(
						RoleId.SYSTEM,
						UserId.METASFRESH,
						ClientId.SYSTEM,
						LocalDate.of(2026, 3, 22)))
				.build();
	}

	@Nested
	class selectFieldValues
	{
		@Test
		void withoutDisplayValue()
		{
			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createSimpleField("DateOrdered", "DateOrdered", DocumentFieldWidgetType.LocalDate));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "DateOrdered", 10);

			assertThat(result.getSql()).isEqualTo(
					"SELECT DISTINCT DateOrdered"
							+ "\n FROM ("
							+ "\n SELECT "
							+ "\n " + TABLE_NAME + ".DateOrdered AS DateOrdered"
							+ "\n FROM T_WEBUI_ViewSelection sel"
							+ "\n INNER JOIN " + TABLE_NAME + " ON (" + TABLE_NAME + ".Overview_ID=sel.IntKey1)"
							+ "\n WHERE sel.UUID=?"
							+ "\n ORDER BY sel.Line"
							+ "\n) t"
							+ "\n LIMIT ?");

			assertThat(result.getSqlParams()).containsExactly("test-uuid", 10);
		}

		@Test
		void withDisplayValue()
		{
			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createFieldWithDisplay("CreatedBy", "CreatedBy", DocumentFieldWidgetType.Integer,
							createAdUserDisplayValue("CreatedBy")));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "CreatedBy", 10);

			assertThat(result.getSql()).isEqualTo(
					"SELECT t.CreatedBy"
							+ "\n, (SELECT "
							+ "\n ARRAY[AD_User.AD_User_ID::text, AD_User.Name, NULL,NULL, NULL] "
							+ "\n FROM AD_User"
							+ "\n WHERE AD_User.AD_User_ID=t.CreatedBy) AS CreatedBy$Display"
							+ "\n FROM ("
							+ "\n SELECT DISTINCT "
							+ "\n " + TABLE_NAME + ".CreatedBy AS CreatedBy"
							+ "\n FROM T_WEBUI_ViewSelection sel"
							+ "\n INNER JOIN " + TABLE_NAME + " ON (" + TABLE_NAME + ".Overview_ID=sel.IntKey1)"
							+ "\n WHERE sel.UUID=?"
							+ "\n) t"
							+ "\n LIMIT ?");

			assertThat(result.getSqlParams()).containsExactly("test-uuid", 10);
		}

		@Test
		void withDisplayValue_innerQueryDoesNotContainDisplaySubquery()
		{
			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createFieldWithDisplay("M_Product_ID", "M_Product_ID", DocumentFieldWidgetType.Integer,
							createProductDisplayValue()));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "M_Product_ID", 10);
			final String sql = result.getSql();

			// Split at inner subquery boundary
			final int fromSubqueryStart = sql.indexOf("FROM (");
			final int subqueryEnd = sql.indexOf(") t");
			final String outerSelect = sql.substring(0, fromSubqueryStart);
			final String innerSubquery = sql.substring(fromSubqueryStart, subqueryEnd);

			// Display lookup must be in OUTER select only (this was the 89s perf bug)
			assertThat(outerSelect).contains("M_Product_ID$Display");
			assertThat(innerSubquery).doesNotContain("M_Product_ID$Display");
			assertThat(innerSubquery).doesNotContain("M_Product.Name");
		}

		@Test
		void fieldNotFound_throwsException()
		{
			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createSimpleField("DateOrdered", "DateOrdered", DocumentFieldWidgetType.LocalDate));

			assertThatThrownBy(() -> selectData.selectFieldValues(viewEvalCtx, "test-uuid", "NonExistentField", 10))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class)
					.hasMessageContaining("NonExistentField")
					.hasMessageContaining("not found");
		}

		@Test
		void withDisplayValue_refListLookup()
		{
			final SqlSelectDisplayValue displayValue = SqlSelectDisplayValue.builder()
					.joinOnColumnName("DocStatus")
					.sqlExpression(SqlForFetchingLookupById.builder()
							.keyColumnNameFQ(ColumnNameFQ.ofTableAndColumnName("AD_Ref_List", "Value"))
							.numericKey(false)
							.displayColumn(ConstantStringExpression.of("AD_Ref_List.Name"))
							.sqlFrom(ConstantStringExpression.of("AD_Ref_List"))
							.additionalWhereClause("AD_Ref_List.AD_Reference_ID=131")
							.build())
					.columnNameAlias("DocStatus$Display")
					.build();

			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createFieldWithDisplayAndClass("DocStatus", "DocStatus", DocumentFieldWidgetType.List, String.class, displayValue));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "DocStatus", 10);

			assertThat(result.getSql()).isEqualTo(
					"SELECT t.DocStatus"
							+ "\n, (SELECT "
							+ "\n ARRAY[AD_Ref_List.Value::text, AD_Ref_List.Name, NULL,NULL, NULL] "
							+ "\n FROM AD_Ref_List"
							+ "\n WHERE AD_Ref_List.Value=t.DocStatus AND AD_Ref_List.AD_Reference_ID=131) AS DocStatus$Display"
							+ "\n FROM ("
							+ "\n SELECT DISTINCT "
							+ "\n " + TABLE_NAME + ".DocStatus AS DocStatus"
							+ "\n FROM T_WEBUI_ViewSelection sel"
							+ "\n INNER JOIN " + TABLE_NAME + " ON (" + TABLE_NAME + ".Overview_ID=sel.IntKey1)"
							+ "\n WHERE sel.UUID=?"
							+ "\n) t"
							+ "\n LIMIT ?");

			assertThat(result.getSqlParams()).containsExactly("test-uuid", 10);
		}

		@Test
		void withDisplayValue_displayNotInDisplayFieldNames_treatedAsNoDisplay()
		{
			// Field has SqlSelectDisplayValue but is NOT in displayFieldNames =>
			// should behave like a field without display value (DISTINCT in outer query)
			final SqlViewRowFieldBinding keyField = createKeyField();
			final SqlViewRowFieldBinding createdByField = SqlViewRowFieldBinding.builder()
					.fieldName("CreatedBy")
					.columnName("CreatedBy")
					.widgetType(DocumentFieldWidgetType.Integer)
					.sqlValueClass(Integer.class)
					.sqlSelectValue(SqlSelectValue.builder()
							.columnName("CreatedBy")
							.columnNameAlias("CreatedBy")
							.build())
					.sqlSelectDisplayValue(createAdUserDisplayValue("CreatedBy"))
					.fieldLoader((rs, adLanguage) -> null)
					.build();

			final SqlViewSelectData selectData = SqlViewSelectData.builder()
					.sqlTableName(TABLE_NAME)
					.sqlTableAlias(TABLE_ALIAS)
					.keyColumnNamesMap(SqlViewKeyColumnNamesMap.ofIntKeyField(KEY_COLUMN))
					.displayFieldNames(ImmutableSet.of()) // empty — CreatedBy NOT listed
					.allFields(ImmutableList.of(keyField, createdByField))
					.build();

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "CreatedBy", 10);

			assertThat(result.getSql()).startsWith("SELECT DISTINCT CreatedBy");
			assertThat(result.getSql()).doesNotContain("$Display");
		}
	}

	// --- Reusable display value builders ---

	private static SqlSelectDisplayValue createAdUserDisplayValue(final String joinOnColumnName)
	{
		return SqlSelectDisplayValue.builder()
				.joinOnColumnName(joinOnColumnName)
				.sqlExpression(SqlForFetchingLookupById.builder()
						.keyColumnNameFQ(ColumnNameFQ.ofTableAndColumnName("AD_User", "AD_User_ID"))
						.numericKey(true)
						.displayColumn(ConstantStringExpression.of("AD_User.Name"))
						.sqlFrom(ConstantStringExpression.of("AD_User"))
						.build())
				.columnNameAlias(joinOnColumnName + "$Display")
				.build();
	}

	private static SqlSelectDisplayValue createProductDisplayValue()
	{
		return SqlSelectDisplayValue.builder()
				.joinOnColumnName("M_Product_ID")
				.sqlExpression(SqlForFetchingLookupById.builder()
						.keyColumnNameFQ(ColumnNameFQ.ofTableAndColumnName("M_Product", "M_Product_ID"))
						.numericKey(true)
						.displayColumn(ConstantStringExpression.of("M_Product.Name"))
						.sqlFrom(ConstantStringExpression.of("M_Product"))
						.build())
				.columnNameAlias("M_Product_ID$Display")
				.build();
	}

	// --- Field builder helpers ---

	private SqlViewRowFieldBinding createKeyField()
	{
		return SqlViewRowFieldBinding.builder()
				.fieldName(KEY_COLUMN)
				.columnName(KEY_COLUMN)
				.keyColumn(true)
				.widgetType(DocumentFieldWidgetType.Integer)
				.sqlValueClass(Integer.class)
				.sqlSelectValue(SqlSelectValue.builder()
						.columnName(KEY_COLUMN)
						.columnNameAlias(KEY_COLUMN)
						.build())
				.fieldLoader((rs, adLanguage) -> null)
				.build();
	}

	private SqlViewRowFieldBinding createSimpleField(
			final String fieldName,
			final String columnName,
			final DocumentFieldWidgetType widgetType)
	{
		return SqlViewRowFieldBinding.builder()
				.fieldName(fieldName)
				.columnName(columnName)
				.widgetType(widgetType)
				.sqlValueClass(widgetType.getValueClass())
				.sqlSelectValue(SqlSelectValue.builder()
						.columnName(columnName)
						.columnNameAlias(columnName)
						.build())
				.fieldLoader((rs, adLanguage) -> null)
				.build();
	}

	private SqlViewRowFieldBinding createFieldWithDisplay(
			final String fieldName,
			final String columnName,
			final DocumentFieldWidgetType widgetType,
			final SqlSelectDisplayValue displayValue)
	{
		return createFieldWithDisplayAndClass(fieldName, columnName, widgetType, widgetType.getValueClass(), displayValue);
	}

	private SqlViewRowFieldBinding createFieldWithDisplayAndClass(
			final String fieldName,
			final String columnName,
			final DocumentFieldWidgetType widgetType,
			final Class<?> valueClass,
			final SqlSelectDisplayValue displayValue)
	{
		return SqlViewRowFieldBinding.builder()
				.fieldName(fieldName)
				.columnName(columnName)
				.widgetType(widgetType)
				.sqlValueClass(valueClass)
				.sqlSelectValue(SqlSelectValue.builder()
						.columnName(columnName)
						.columnNameAlias(columnName)
						.build())
				.sqlSelectDisplayValue(displayValue)
				.fieldLoader((rs, adLanguage) -> null)
				.build();
	}

	private SqlViewSelectData createSelectData(SqlViewRowFieldBinding keyField, SqlViewRowFieldBinding... otherFields)
	{
		final ImmutableList.Builder<SqlViewRowFieldBinding> allFields = ImmutableList.builder();
		allFields.add(keyField);
		allFields.add(otherFields);

		final ImmutableSet.Builder<String> displayFieldNames = ImmutableSet.builder();
		for (final SqlViewRowFieldBinding field : otherFields)
		{
			if (field.getSqlSelectDisplayValue() != null)
			{
				displayFieldNames.add(field.getFieldName());
			}
		}

		return SqlViewSelectData.builder()
				.sqlTableName(TABLE_NAME)
				.sqlTableAlias(TABLE_ALIAS)
				.keyColumnNamesMap(SqlViewKeyColumnNamesMap.ofIntKeyField(KEY_COLUMN))
				.displayFieldNames(displayFieldNames.build())
				.allFields(allFields.build())
				.build();
	}
}
