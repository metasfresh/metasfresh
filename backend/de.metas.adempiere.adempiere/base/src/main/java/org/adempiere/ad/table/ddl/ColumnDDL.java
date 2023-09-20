package org.adempiere.ad.table.ddl;

import de.metas.reflist.ReferenceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
class ColumnDDL
{
	@NonNull String tableName;
	@NonNull String columnName;
	@NonNull ReferenceId referenceId;
	@Nullable String defaultValue;
	boolean mandatory;
	int fieldLength;
	boolean isPrimaryKey; // AD_Column.IsKey
	boolean isParentLink; // AD_Column.IsParent

	boolean noForeignKey; // AD_Column.DDL_NoForeignKey

	/**
	 * Create and return the SQL add column DDL sstatement.
	 *
	 * @return sql
	 */
	public List<String> getSQLAdd()
	{
		final List<String> sqlStatements = new ArrayList<>();

		sqlStatements.add("ALTER TABLE "
				+ "public." // if the table is already DLM'ed then there is a view with the same name in the dlm schema.
				+ tableName
				+ " ADD COLUMN " // not just "ADD" but "ADD COLUMN" to make it easier to distinguish the sort of this DDL further down the road.
				+ getSQLDDL());

		final String sqlConstraint = getSQLConstraint();
		if (!Check.isBlank(sqlConstraint))
		{
			sqlStatements.add("ALTER TABLE " + tableName + " ADD " + sqlConstraint);
		}

		return sqlStatements;
	}

	/**
	 * Get SQL DDL
	 *
	 * @return columnName datataype ..
	 */
	public String getSQLDDL()
	{
		StringBuilder sql = new StringBuilder(this.columnName).append(" ").append(getSQLDataType());

		// Default
		String defaultValue = this.defaultValue;
		final int displayType = this.referenceId.getRepoId();
		if (defaultValue != null
				&& defaultValue.length() > 0
				&& defaultValue.indexOf('@') == -1        // no variables
				&& (!(DisplayType.isID(displayType) && defaultValue.equals("-1"))))    // not for ID's with default -1
		{
			if (DisplayType.isText(displayType)
					|| displayType == DisplayType.List
					|| displayType == DisplayType.YesNo
					// Two special columns: Defined as Table but DB Type is String
					|| columnName.equals("EntityType") || columnName.equals("AD_Language")
					|| (displayType == DisplayType.Button && !(columnName.endsWith("_ID"))))
			{
				if (!defaultValue.startsWith("'") && !defaultValue.endsWith("'"))
				{
					defaultValue = DB.TO_STRING(defaultValue);
				}
			}
			sql.append(" DEFAULT ").append(defaultValue);
		}
		else
		{
			// avoid the explicit DEFAULT NULL, because apparently it causes an extra cost
			// if (!isMandatory())
			// sql.append(" DEFAULT NULL ");
			defaultValue = null;
		}

		// Inline Constraint
		if (displayType == DisplayType.YesNo)
			sql.append(" CHECK (").append(columnName).append(" IN ('Y','N'))");

		// Null
		if (mandatory)
			sql.append(" NOT NULL");
		return sql.toString();
	}

	/**
	 * Get Table Constraint
	 */
	String getSQLConstraint()
	{
		if (isPrimaryKey)
		{
			final String constraintName = tableName + "_Key";
			return "CONSTRAINT " + constraintName + " PRIMARY KEY (" + columnName + ")";
		}
		else if (DisplayType.isID(this.referenceId.getRepoId()) && !this.noForeignKey)
		{
			// gh #539 Add missing FK constraints
			// create a FK-constraint, using the same view we also used to "manually" create FK-constraints in the past.

			// get an FK-constraint for this table, if any
			// this returns something like
			// "ALTER TABLE A_Asset_Change ADD CONSTRAINT ADepreciationCalcT_AAssetChang FOREIGN KEY (A_Depreciation_Calc_Type) REFERENCES A_Depreciation_Method DEFERRABLE INITIALLY DEFERRED;"
			final String fkConstraintDDL = DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT SqlText FROM db_columns_fk WHERE TableName=? AND ColumnName=?", tableName, columnName);
			if (!Check.isEmpty(fkConstraintDDL, true))
			{
				// remove the "ALTER TABLE ... ADD" and the trailing ";"
				// the (?iu) means the the pattern is created with Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
				// thanks to https://blogs.oracle.com/xuemingshen/entry/case_insensitive_matching_in_java
				return fkConstraintDDL
						.replaceFirst("(?iu)ALTER *TABLE *" + tableName + " *ADD *", "")
						.replaceFirst(";$", "");
			}
		}
		return "";
	}    // getConstraint

	/**
	 * Get SQL Modify command
	 *
	 * @param setNullOption generate null / not null statement
	 * @return sql separated by ;
	 */
	public List<String> getSQLModify(final boolean setNullOption)
	{
		final String sqlDefaultValue = getSQLDefaultValue();

		final StringBuilder sqlBase_ModifyColumn = new StringBuilder("ALTER TABLE ")
				.append(tableName)
				.append(" MODIFY ").append(columnName);

		final List<String> sqlStatements = new ArrayList<>();

		//
		// Modify data type and DEFAULT value
		{
			final StringBuilder sqlDefault = new StringBuilder(sqlBase_ModifyColumn);

			// Datatype
			sqlDefault.append(" ").append(getSQLDataType());

			// Default
			if (sqlDefaultValue != null)
			{
				sqlDefault.append(" DEFAULT ").append(sqlDefaultValue);
			}
			else
			{
				// avoid the explicit DEFAULT NULL, because apparently it causes an extra cost
				// if (!mandatory)
				// sqlDefault.append(" DEFAULT NULL ");
			}

			sqlStatements.add(DB.convertSqlToNative(sqlDefault.toString()));
		}

		//
		// Update NULL values
		if (mandatory && sqlDefaultValue != null && !sqlDefaultValue.isEmpty())
		{
			final String sqlSet = "UPDATE " + tableName + " SET " + columnName + "=" + sqlDefaultValue + " WHERE " + columnName + " IS NULL";
			sqlStatements.add(sqlSet);
		}

		//
		// Set NULL/NOT NULL constraint
		if (setNullOption)
		{
			final StringBuilder sqlNull = new StringBuilder(sqlBase_ModifyColumn);
			if (mandatory)
				sqlNull.append(" NOT NULL");
			else
				sqlNull.append(" NULL");
			sqlStatements.add(DB.convertSqlToNative(sqlNull.toString()));
		}

		//
		return sqlStatements;
	}

	/**
	 * Get SQL Data Type
	 *
	 * @return e.g. NVARCHAR2(60)
	 */
	private String getSQLDataType()
	{
		final String columnName = getColumnName();
		final ReferenceId displayType = getReferenceId();
		final int fieldLength = getFieldLength();
		return DB.getSQLDataType(displayType.getRepoId(), columnName, fieldLength);
	}

	private String getSQLDefaultValue()
	{
		final int displayType = referenceId.getRepoId();

		if (defaultValue != null
				&& !defaultValue.isEmpty()
				&& defaultValue.indexOf('@') == -1        // no variables
				&& (!(DisplayType.isID(displayType) && defaultValue.equals("-1"))))    // not for ID's with default -1
		{
			if (DisplayType.isText(displayType)
					|| displayType == DisplayType.List
					|| displayType == DisplayType.YesNo
					// Two special columns: Defined as Table but DB Type is String
					|| columnName.equals("EntityType") || columnName.equals("AD_Language")
					|| (displayType == DisplayType.Button && !(columnName.endsWith("_ID"))))
			{
				if (!defaultValue.startsWith(DB.QUOTE_STRING) && !defaultValue.endsWith(DB.QUOTE_STRING))
				{
					return DB.TO_STRING(defaultValue);
				}
				else
				{
					return defaultValue;
				}
			}
			else
			{
				return defaultValue;
			}
		}
		else
		{
			return null;
		}
	}

}
