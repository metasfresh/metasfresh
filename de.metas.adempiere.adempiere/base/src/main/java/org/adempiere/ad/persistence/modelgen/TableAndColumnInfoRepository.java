package org.adempiere.ad.persistence.modelgen;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import com.google.common.base.Optional;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Repository of {@link TableInfo}s, {@link ListInfo}s, {@link TableReferenceInfo}s.
 * 
 * @author tsa
 *
 */
public class TableAndColumnInfoRepository
{
	private LoadingCache<Integer, TableInfo> tableInfos = CacheBuilder.newBuilder()
			.build(new CacheLoader<Integer, TableInfo>()
			{
				@Override
				public TableInfo load(final Integer adTableId) throws Exception
				{
					return loadTableInfos(adTableId);
				}
			});

	private LoadingCache<Integer, Optional<ListInfo>> listInfos = CacheBuilder.newBuilder()
			.build(new CacheLoader<Integer, Optional<ListInfo>>()
			{
				@Override
				public Optional<ListInfo> load(final Integer adReferenceId) throws Exception
				{
					return loadListInfo(adReferenceId);
				}
			});

	private LoadingCache<Integer, Optional<TableReferenceInfo>> tableReferenceInfos = CacheBuilder.newBuilder()
			.build(new CacheLoader<Integer, Optional<TableReferenceInfo>>()
			{
				@Override
				public Optional<TableReferenceInfo> load(final Integer adReferenceId) throws Exception
				{
					return loadTableReferenceInfo(adReferenceId);
				}
			});

	public TableInfo getTableInfo(final int adTableId)
	{
		try
		{
			return tableInfos.get(adTableId);
		}
		catch (ExecutionException e)
		{
			throw Throwables.propagate(e);
		}
	}

	public Optional<ListInfo> getListInfo(final int adReferenceId)
	{
		try
		{
			return listInfos.get(adReferenceId);
		}
		catch (ExecutionException e)
		{
			throw Throwables.propagate(e);
		}
	}

	public Optional<TableReferenceInfo> getTableReferenceInfo(final int adReferenceId)
	{
		try
		{
			return tableReferenceInfos.get(adReferenceId);
		}
		catch (ExecutionException e)
		{
			throw Throwables.propagate(e);
		}
	}

	private TableInfo loadTableInfos(final int adTableId)
	{
		TableInfo.Builder tableInfoBuilder = null;
		final Map<String, ColumnInfo> columnName2columnInfos = new LinkedHashMap<>();

		final String sql = "SELECT c.ColumnName, c.IsUpdateable, c.IsMandatory," // 1..3
				+ " c.AD_Reference_ID, c.AD_Reference_Value_ID, DefaultValue, SeqNo, " // 4..7
				+ " c.FieldLength, c.ValueMin, c.ValueMax, c.VFormat, c.Callout, " // 8..12
				+ " c.Name, c.Description, c.ColumnSQL, c.IsEncrypted, c.IsKey "   // 13..17
				+ ", t.TableName" // 18
				+ ", c.SeqNo" // 19
				+ ", c.IsIdentifier" // 20
				+ ", t.AccessLevel" // 21
				+ ", c.IsLazyLoading" // 22
				+ " FROM AD_Column c "
				+ " INNER JOIN AD_Table t ON (t.AD_Table_ID=c.AD_Table_ID)"
				+ " WHERE c.AD_Table_ID=?"
				// + " AND c.ColumnName <> 'AD_Client_ID'"
				// + " AND c.ColumnName <> 'AD_Org_ID'"
				// + " AND c.ColumnName <> 'IsActive'"
				// + " AND c.ColumnName NOT LIKE 'Created%'"
				// + " AND c.ColumnName NOT LIKE 'Updated%' "
				+ " AND c.IsActive='Y'"
				+ getColumnsEntityTypeWhereClause(adTableId)
				+ " ORDER BY c.ColumnName";
		final Object[] sqlParams = new Object[] { adTableId };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final String tableName = rs.getString("TableName"); // i.e. 18
				String columnName = rs.getString(1);
				boolean isUpdateable = "Y".equals(rs.getString(2));
				boolean isMandatory = "Y".equals(rs.getString(3));
				int displayType = rs.getInt(4);
				int AD_Reference_Value_ID = rs.getInt(5);
				String defaultValue = rs.getString(6);
				// int seqNo = rs.getInt(7);
				int fieldLength = rs.getInt(8);
				String ValueMin = rs.getString(9);
				String ValueMax = rs.getString(10);
				String VFormat = rs.getString(11);
				String Callout = rs.getString(12);
				String Name = rs.getString(13);
				String Description = rs.getString(14);
				String ColumnSQL = rs.getString(15);
				boolean virtualColumn = ColumnSQL != null
						&& ColumnSQL.length() > 0;
				boolean IsEncrypted = "Y".equals(rs.getString(16));
				boolean IsKey = "Y".equals(rs.getString(17));
				final int seqNo = rs.getInt("SeqNo"); // i.e. 19
				final boolean isIdentifier = "Y".equals(rs.getString("IsIdentifier")); // i.e. 20

				if (tableInfoBuilder == null)
				{
					final TableAccessLevel accessLevel = TableAccessLevel.forAccessLevel(rs.getInt("AccessLevel")); // i.e. 21

					tableInfoBuilder = TableInfo.builder()
							.setTableName(tableName)
							.setAD_Table_ID(adTableId)
							.setAccessLevel(accessLevel);
				}
				
				final boolean isLazyLoading = DisplayType.toBoolean(rs.getString("IsLazyLoading")); // 22

				final ColumnInfo columnInfo = new ColumnInfo(tableName,
						columnName,
						isUpdateable, isMandatory,
						displayType, AD_Reference_Value_ID,
						fieldLength,
						defaultValue, ValueMin, ValueMax,
						VFormat, Callout,
						Name, Description,
						virtualColumn,
						IsEncrypted,
						IsKey,
						seqNo,
						adTableId);
				columnInfo.setIdentifier(isIdentifier);
				columnInfo.setLazyLoading(isLazyLoading);
				columnInfo.setRepository(this);
				columnName2columnInfos.put(columnInfo.getColumnName(), columnInfo);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (tableInfoBuilder == null)
		{
			throw new AdempiereException("No table/column info found for AD_Table_ID=" + adTableId);
		}

		//
		// Handle AD_Table_ID/Record_ID generic model reference
		// TODO: before we activate this functionality we need to make sure POWrapper, GridTabWrapper and POJOWrapper is supporting this.
		// {
		// final ColumnInfo adTableIdColumn = columnName2columnInfos.get("AD_Table_ID");
		// final ColumnInfo recordIdColumn = columnName2columnInfos.get("Record_ID");
		// if (adTableIdColumn != null && recordIdColumn != null)
		// {
		// recordIdColumn.setTableIdColumnName(adTableIdColumn.getColumnName());
		// }
		// }

		return tableInfoBuilder
				.setColumnInfos(columnName2columnInfos.values())
				.build();
	}

	private static String getColumnsEntityTypeWhereClause(final int AD_Table_ID)
	{
		if ("true".equals(System.getProperty("org.adempiere.util.GenerateModel.OnlySystemColumns")))
		{
			String entityType = DB.getSQLValueStringEx(null, "SELECT EntityType FROM AD_Table WHERE AD_Table_ID=?", AD_Table_ID);
			int AD_EntityType_ID = DB.getSQLValueEx(null, "SELECT AD_EntityType_ID FROM AD_EntityType WHERE EntityType=?", entityType);
			boolean isSystemEntity = AD_EntityType_ID < 500000;
			if (!isSystemEntity)
				return "";

			// only columns from system entity type
			return " AND EXISTS (SELECT 1 FROM AD_EntityType et WHERE et.EntityType=c.EntityType AND et.AD_EntityType_ID < 500000) ";
		}
		// Strict column's EntityType - same entity type as it's table
		else
		{
			return " AND c.EntityType IN (SELECT t1.EntityType FROM AD_Table t1 WHERE t1.AD_Table_ID=" + AD_Table_ID + ")";
		}
	}

	private final Optional<ListInfo> loadListInfo(final int adReferenceId)
	{
		ListInfo.Builder listInfoBuilder = null;

		final String sql = "SELECT "
				+ " r.Name as ReferenceName"
				+ ", rl.Value"
				+ ", rl.Name"
				+ ", rl.ValueName" // metas: 02827: added ValueName
				+ ", rl.AD_Ref_List_ID"
				+ " FROM AD_Reference r "
				+ " LEFT OUTER JOIN AD_Ref_List rl ON (rl.AD_Reference_ID=r.AD_Reference_ID) "
				+ " WHERE r.AD_Reference_ID=?"
				+ " ORDER BY rl.AD_Ref_List_ID";
		final Object[] sqlParams = new Object[] { adReferenceId };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if (listInfoBuilder == null)
				{
					final String referenceName = rs.getString("ReferenceName");

					listInfoBuilder = ListInfo.builder()
							.setAD_Reference_ID(adReferenceId)
							.setName(referenceName);
				}

				final int AD_Ref_List_ID = rs.getInt("AD_Ref_List_ID");
				if (AD_Ref_List_ID > 0)
				{
					final String value = rs.getString("Value");
					final String name = rs.getString("Name");
					final String valueName = rs.getString("ValueName");
					listInfoBuilder.addItem(value, name, valueName);
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (listInfoBuilder == null)
		{
			return Optional.absent();
		}

		return Optional.of(listInfoBuilder.build());
	}

	public Optional<TableReferenceInfo> loadTableReferenceInfo(final int adReferenceId)
	{
		final String sql = "SELECT"
				+ " t.TableName, t.EntityType" // 1,2
				+ ", ck.AD_Reference_ID" // 3
				+ ", ck.IsKey" // 4
				+ ", ck.AD_Reference_Value_ID as Key_Reference_Value_ID" // 5
				+ " FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Table t ON (t.AD_Table_ID=rt.AD_Table_ID)"
				+ " INNER JOIN AD_Column ck ON (ck.AD_Table_ID=rt.AD_Table_ID AND ck.AD_Column_ID=rt.AD_Key)"
				+ " WHERE rt.AD_Reference_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, adReferenceId);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				final String refTableName = rs.getString(1);
				final String entityType = rs.getString(2);
				final int refDisplayType = rs.getInt(3);
				final boolean refIsKey = "Y".equals(rs.getString("IsKey"));
				final int keyReferenceValueId = rs.getInt("Key_Reference_Value_ID");

				final TableReferenceInfo tableReferenceInfo = new TableReferenceInfo(refTableName, refDisplayType, entityType, refIsKey, keyReferenceValueId);
				return Optional.of(tableReferenceInfo);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return Optional.absent();
	}
}
