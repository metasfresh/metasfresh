package org.adempiere.ad.service.impl;

import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.service.IDeveloperModeBL;
import de.metas.ad_reference.ADRefTable;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class LookupDisplayInfoRepository
{
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	private final static String COLUMNNAME_Value = "Value";

	public LookupDisplayInfo getLookupDisplayInfo(@NonNull final ADRefTable tableRefInfo)
	{
		//
		// Column filter
		final StringBuilder sqlWhereClauseColumn = new StringBuilder();
		final List<Object> sqlWhereClauseColumnParams = new ArrayList<>();
		final StringBuilder sqlOrderBy = new StringBuilder();
		final List<Object> sqlOrderByParams = new ArrayList<>();
		//
		if (tableRefInfo.isValueDisplayed())
		{
			if (sqlWhereClauseColumn.length() > 0)
			{
				sqlWhereClauseColumn.append(" OR ");
			}
			sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=?");
			sqlWhereClauseColumnParams.add(COLUMNNAME_Value);

			if (sqlOrderBy.length() > 0)
			{
				sqlOrderBy.append(", ");
			}
			sqlOrderBy.append("(CASE WHEN c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=? THEN 0 ELSE 1 END)");
			sqlOrderByParams.add(COLUMNNAME_Value);
		}
		//
		if (developerModeBL.isEnabled())
		{
			if (I_AD_Table.Table_Name.equals(tableRefInfo.getTableName()))
			{
				if (sqlWhereClauseColumn.length() > 0)
				{
					sqlWhereClauseColumn.append(" OR ");
				}
				sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=?");
				sqlWhereClauseColumnParams.add(I_AD_Table.COLUMNNAME_TableName);
			}
		}
		//
		if (Check.isEmpty(tableRefInfo.getDisplayColumn(), true))
		{
			// Fetch IsIdentifier fields
			if (sqlWhereClauseColumn.length() > 0)
			{
				sqlWhereClauseColumn.append(" OR ");
			}
			sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_IsIdentifier).append("=?");
			sqlWhereClauseColumnParams.add(true);

			if (sqlOrderBy.length() > 0)
			{
				sqlOrderBy.append(", ");
			}
			sqlOrderBy.append("c.").append(I_AD_Column.COLUMNNAME_SeqNo);
		}
		else
		{
			if (sqlWhereClauseColumn.length() > 0)
			{
				sqlWhereClauseColumn.append(" OR ");
			}
			sqlWhereClauseColumn.append("c.").append(I_AD_Column.COLUMNNAME_ColumnName).append("=?");
			sqlWhereClauseColumnParams.add(tableRefInfo.getDisplayColumn());
		}

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT "
				+ " c." + I_AD_Column.COLUMNNAME_ColumnName
				+ ",c." + I_AD_Column.COLUMNNAME_IsTranslated
				+ ",c." + I_AD_Column.COLUMNNAME_AD_Reference_ID
				+ ",c." + I_AD_Column.COLUMNNAME_AD_Reference_Value_ID
				+ ",t." + I_AD_Table.COLUMNNAME_AD_Window_ID
				+ ",t." + I_AD_Table.COLUMNNAME_PO_Window_ID
				+ ",c." + I_AD_Column.COLUMNNAME_ColumnSQL  // 7
				+ ",c." + I_AD_Column.COLUMNNAME_FormatPattern  // 8
				+ " FROM " + I_AD_Table.Table_Name + " t"
				+ " INNER JOIN " + I_AD_Column.Table_Name + " c ON (t.AD_Table_ID=c.AD_Table_ID)");

		sql.append(" WHERE ");
		sql.append("t.").append(I_AD_Table.COLUMNNAME_TableName).append("=?");
		sqlParams.add(tableRefInfo.getTableName());

		if (sqlWhereClauseColumn.length() > 0)
		{
			sql.append(" AND (").append(sqlWhereClauseColumn).append(")");
			sqlParams.addAll(sqlWhereClauseColumnParams);
		}

		if (sqlOrderBy.length() > 0)
		{
			sql.append(" ORDER BY ").append(sqlOrderBy);
			sqlParams.addAll(sqlOrderByParams);
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<LookupDisplayColumn> lookupDisplayColumns = new ArrayList<>();
			boolean isTranslated = false;
			AdWindowId ZoomWindow = null;
			AdWindowId ZoomWindowPO = null;

			while (rs.next())
			{
				final LookupDisplayColumn ldc = LookupDisplayColumn.builder()
						.columnName(rs.getString(1))
						.columnSQL(rs.getString(7))
						.isTranslated(DisplayType.toBoolean(rs.getString(2)))
						.ad_Reference_ID(rs.getInt(3))
						.ad_Reference_Value_ID(ReferenceId.ofRepoIdOrNull(rs.getInt(4)))
						.formatPattern(rs.getString(8))
						.build();
				lookupDisplayColumns.add(ldc);
				// s_log.debug("getLookup_TableDir: " + ColumnName + " - " + ldc);
				//
				if (!isTranslated && ldc.isTranslated())
				{
					isTranslated = true;
				}
				ZoomWindow = AdWindowId.ofRepoIdOrNull(rs.getInt(5));
				ZoomWindowPO = AdWindowId.ofRepoIdOrNull(rs.getInt(6));
			}

			// Make sure we have some display columns defined.
			if (lookupDisplayColumns.isEmpty())
			{
				throw new AdempiereException("There are no lookup display columns defined for " + tableRefInfo.getTableName() + " table."
						+ "\n HINT: please go to Table and columns, and set IsIdentifier=Y on columns which you want to be part of record's display string.");
			}

			return LookupDisplayInfo.builder()
					.lookupDisplayColumns(lookupDisplayColumns)
					.zoomWindow(ZoomWindow)
					.zoomWindowPO(ZoomWindowPO)
					.translated(isTranslated)
					.build();

		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.toString(), sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
}
