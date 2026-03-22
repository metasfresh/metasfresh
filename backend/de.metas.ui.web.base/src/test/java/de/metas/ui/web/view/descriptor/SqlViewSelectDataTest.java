package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.PlainSqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookupById;
import de.metas.ui.web.window.descriptor.sql.SqlSelectDisplayValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.user.UserId;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.table.api.ColumnNameFQ;
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
				.loggedUserId(Optional.of(UserId.ofRepoId(100)))
				.orgId(OrgId.ofRepoId(1000000))
				.adLanguage("en_US")
				.timeZone(ZoneId.of("Europe/Berlin"))
				.permissionsKey(UserRolePermissionsKey.of(
						de.metas.security.RoleId.ofRepoId(0),
						UserId.ofRepoId(100),
						org.adempiere.service.ClientId.ofRepoId(0),
						LocalDate.of(2026, 3, 22)))
				.build();
	}

	@Nested
	class selectFieldValues
	{
		@Test
		void withoutDisplayValue_usesDistinctInOuterSelect()
		{
			// A simple field without a display value (e.g., a plain date or amount column)
			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createSimpleField("DateOrdered", "DateOrdered", DocumentFieldWidgetType.LocalDate));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "DateOrdered", 10);

			final String sql = result.getSql();

			// Without display value: SELECT DISTINCT in outer, no display subquery
			assertThat(sql).startsWith("SELECT DISTINCT DateOrdered");
			assertThat(sql).contains("FROM (");
			assertThat(sql).contains(TABLE_NAME);
			assertThat(sql).contains("T_WEBUI_ViewSelection sel");
			assertThat(sql).contains("sel.UUID=?");
			assertThat(sql).contains("LIMIT ?");

			// Should NOT have any display column
			assertThat(sql).doesNotContain("$Display");

			// Params: selectionId, limit
			assertThat(result.getSqlParams()).containsExactly("test-uuid", 10);
		}

		@Test
		void withDisplayValue_computesDistinctBeforeDisplay()
		{
			// A field with a display value (e.g., CreatedBy with AD_User lookup)
			final SqlSelectDisplayValue displayValue = SqlSelectDisplayValue.builder()
					.joinOnColumnName("CreatedBy")
					.sqlExpression(SqlForFetchingLookupById.builder()
							.keyColumnNameFQ(ColumnNameFQ.ofTableAndColumnName("AD_User", "AD_User_ID"))
							.numericKey(true)
							.displayColumn(ConstantStringExpression.of("AD_User.Name"))
							.sqlFrom(ConstantStringExpression.of("AD_User"))
							.build())
					.columnNameAlias("CreatedBy$Display")
					.build();

			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createFieldWithDisplay("CreatedBy", "CreatedBy", DocumentFieldWidgetType.Integer, displayValue));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "CreatedBy", 10);

			final String sql = result.getSql();

			// gh#28680: The critical performance fix:
			// Outer SELECT must NOT have DISTINCT — it selects from already-distinct inner results
			assertThat(sql).startsWith("SELECT t.CreatedBy");
			assertThat(sql).doesNotMatch("(?i)^SELECT DISTINCT.*");

			// Display value must be computed in the OUTER query (referencing t.CreatedBy)
			assertThat(sql).contains("CreatedBy$Display");
			// The display subquery should reference t.CreatedBy (outer table alias)
			assertThat(sql).contains("AD_User.AD_User_ID=t.CreatedBy");

			// DISTINCT must be in the INNER subquery (on the value column only)
			assertThat(sql).contains("SELECT DISTINCT");
			// The inner DISTINCT should be followed by the value column with the view table
			assertThat(sql).matches("(?s).*SELECT DISTINCT\\s+\\n\\s+" + TABLE_NAME + "\\.CreatedBy AS CreatedBy.*");

			// Inner query must join selection table
			assertThat(sql).contains("T_WEBUI_ViewSelection sel");
			assertThat(sql).contains("sel.UUID=?");
			assertThat(sql).contains("LIMIT ?");

			// Params
			assertThat(result.getSqlParams()).containsExactly("test-uuid", 10);
		}

		@Test
		void withDisplayValue_innerQueryDoesNotContainDisplaySubquery()
		{
			// Verify the display subquery is NOT in the inner query
			// (this was the root cause of the 89-second query)
			final SqlSelectDisplayValue displayValue = SqlSelectDisplayValue.builder()
					.joinOnColumnName("M_Product_ID")
					.sqlExpression(SqlForFetchingLookupById.builder()
							.keyColumnNameFQ(ColumnNameFQ.ofTableAndColumnName("M_Product", "M_Product_ID"))
							.numericKey(true)
							.displayColumn(ConstantStringExpression.of("M_Product.Name"))
							.sqlFrom(ConstantStringExpression.of("M_Product"))
							.build())
					.columnNameAlias("M_Product_ID$Display")
					.build();

			final SqlViewSelectData selectData = createSelectData(
					createKeyField(),
					createFieldWithDisplay("M_Product_ID", "M_Product_ID", DocumentFieldWidgetType.Integer, displayValue));

			final SqlAndParams result = selectData.selectFieldValues(viewEvalCtx, "test-uuid", "M_Product_ID", 10);

			final String sql = result.getSql();

			// Split SQL at the inner subquery boundary to check what's inside vs outside
			final int fromSubqueryStart = sql.indexOf("FROM (");
			final int subqueryEnd = sql.indexOf(") t");
			assertThat(fromSubqueryStart).isGreaterThan(0);
			assertThat(subqueryEnd).isGreaterThan(fromSubqueryStart);

			final String outerSelect = sql.substring(0, fromSubqueryStart);
			final String innerSubquery = sql.substring(fromSubqueryStart, subqueryEnd);

			// Display lookup must be in the OUTER select, not in the inner subquery
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
			// Test with AD_Ref_List lookup (string key, has additionalWhereClause)
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

			final String sql = result.getSql();

			// Outer query references t.DocStatus for the lookup
			assertThat(sql).contains("AD_Ref_List.Value=t.DocStatus");
			// Additional where clause must be preserved
			assertThat(sql).contains("AD_Ref_List.AD_Reference_ID=131");
			// DISTINCT in inner query only
			assertThat(sql).startsWith("SELECT t.DocStatus");
			assertThat(sql).contains("SELECT DISTINCT");
		}

		@Test
		void withDisplayValue_displayNotInDisplayFieldNames_treatedAsNoDisplay()
		{
			// If the field has a SqlSelectDisplayValue but is NOT in displayFieldNames,
			// it should behave like a field without display value
			final SqlSelectDisplayValue displayValue = SqlSelectDisplayValue.builder()
					.joinOnColumnName("CreatedBy")
					.sqlExpression(SqlForFetchingLookupById.builder()
							.keyColumnNameFQ(ColumnNameFQ.ofTableAndColumnName("AD_User", "AD_User_ID"))
							.numericKey(true)
							.displayColumn(ConstantStringExpression.of("AD_User.Name"))
							.sqlFrom(ConstantStringExpression.of("AD_User"))
							.build())
					.columnNameAlias("CreatedBy$Display")
					.build();

			// Create field WITH display value but don't include it in displayFieldNames
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
					.sqlSelectDisplayValue(displayValue)
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

			final String sql = result.getSql();

			// Should use the simpler DISTINCT path since display is not active
			assertThat(sql).startsWith("SELECT DISTINCT CreatedBy");
			assertThat(sql).doesNotContain("$Display");
		}
	}

	// --- Helper methods ---

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

		// Collect field names that have display values to include in displayFieldNames
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
