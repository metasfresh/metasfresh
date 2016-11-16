/**
 * 
 */
package org.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.MIndexTable;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import de.metas.process.SvrProcess;

/**
 * @author teo_sarca
 * 
 */
public class AD_Index_Create extends SvrProcess {
	private int p_AD_Index_Table_ID = -1;

	@Override
	protected void prepare() {
		p_AD_Index_Table_ID = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		final MIndexTable index = new MIndexTable(getCtx(),
				p_AD_Index_Table_ID, get_TrxName());
		createIndex(index);
		createBeforeChangeFunction(index);
		return "Ok";
	}

	public void createIndex(MIndexTable index) throws SQLException {
		log.info(index.toString());

		final String[] indexColNames = index.getColumnNames();
		if (indexColNames.length <= 0) {
			throw new AdempiereException("No Index columns specified");
		}
		// Arrays.sort(indexColNames);

		DBIndex dbIndex = getDBIndex(index.getTableName(), index.getName(), index.get_TrxName());
		// DB Index not found => Create new
		if (dbIndex == null) {
			executeDDL(index.getDDL(),index.get_TrxName());
		}
		// DB Index found but modified => Drop and recreate
		else if ((index.isUnique() != dbIndex.isUnique)
				|| !equalsIgnoreCase(index.getWhereClause(),
						dbIndex.filterCondition)
				|| !equalsIgnoreCase(dbIndex.columnNames, indexColNames)) {
			executeDDL("DROP INDEX IF EXISTS " + dbIndex.name,index.get_TrxName()); // metas-ts:adding "IF EXISTS" - solves some SQL migration script errors
			executeDDL(index.getDDL(),index.get_TrxName());
		}

	}

	public void createBeforeChangeFunction(MIndexTable index)
			throws SQLException {
		final String ddlFunction = index.getBeforeChangeCodeFunctionDDL();
		if (Check.isEmpty(ddlFunction, true))
			return;
		executeDDL(ddlFunction,index.get_TrxName());
		//
		final String ddlTrigger = index.getBeforeChangeCodeTriggerDDL();
		if (Check.isEmpty(ddlTrigger, true))
			return;
		if (existsFunction(index.getDBFunctionName()))
			executeDDL("DROP TRIGGER IF EXISTS " + index.getDBTriggerName()
					+ " ON " + index.getTableName(),index.get_TrxName());
		executeDDL(ddlTrigger,index.get_TrxName());
	}

	private DBIndex getDBIndex(String tableName, String indexName, final String trxName)
			throws SQLException {
		
		final String[] indexColsFromDB = new String[30];
		int indexLengthDB = 0;
		DBIndex dbIndex = null;

		final DatabaseMetaData md = Trx.get(trxName, true)
				.getConnection().getMetaData();
		if (md.storesUpperCaseIdentifiers())
			tableName = tableName.toUpperCase();
		else if (md.storesLowerCaseIdentifiers())
			tableName = tableName.toLowerCase();
		final String catalog = "REFERENCE";
		final String schema = null;
		ResultSet rs = null;
		try {
			rs = md.getIndexInfo(catalog, schema, tableName, false, true);
			while (rs.next()) {
				final String dbIndexName = rs.getString("INDEX_NAME");
				if (dbIndexName != null
						&& indexName.equalsIgnoreCase(dbIndexName)) {
					if (dbIndex == null) {
						dbIndex = new DBIndex();
						dbIndex.name = dbIndexName;
						dbIndex.isUnique = true;
					}
					String columnName = rs.getString("COLUMN_NAME");
					int pos = rs.getShort("ORDINAL_POSITION");
					if (pos > 0) {
						// EDB returns varchar index columns wrapped with double
						// quotes, hence comparing
						// after stripping the quotes
						if (columnName.startsWith("\"")
								&& columnName.endsWith("\"")) {
							columnName = columnName.substring(1, columnName
									.length() - 1);
						}
						indexColsFromDB[pos - 1] = columnName;
						if (pos > indexLengthDB)
							indexLengthDB = pos;
					}
					boolean isNonUnique = rs.getBoolean("NON_UNIQUE");
					if (isNonUnique)
						dbIndex.isUnique = false;
					dbIndex.filterCondition = rs.getString("FILTER_CONDITION");
				}
			}
		} finally {
			DB.close(rs);
			rs = null;
		}
		//
		if (dbIndex == null || indexLengthDB <= 0)
			return null;
		//
		dbIndex.columnNames = new String[indexLengthDB];
		for (int i = 0; i < indexLengthDB; i++) {
			dbIndex.columnNames[i] = indexColsFromDB[i];
		}
		// Arrays.sort(dbIndex.columnNames);
		//
		return dbIndex;
	}

	private boolean existsFunction(String functionName) throws SQLException {
		// FIXME I get following error => returning always false
		// ===========> AD_Index_Create.process:
		// com.mchange.v2.c3p0.impl.NewProxyDatabaseMetaData.getFunctions(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/ResultSet;
		// [20]
		// java.lang.AbstractMethodError:
		// com.mchange.v2.c3p0.impl.NewProxyDatabaseMetaData.getFunctions(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/ResultSet;
		// at
		// org.adempiere.process.AD_Index_Create.existsFunction(AD_Index_Create.java:162)
		return true;
		/*
		 * final DatabaseMetaData md = Trx.get(get_TrxName(),
		 * true).getConnection().getMetaData(); if
		 * (md.storesUpperCaseIdentifiers()) functionName =
		 * functionName.toUpperCase(); else if (md.storesLowerCaseIdentifiers())
		 * functionName = functionName.toLowerCase(); final String catalog =
		 * "REFERENCE"; final String schema = null; ResultSet rs = null; try {
		 * rs = md.getFunctions(catalog, schema, functionName); while(rs.next())
		 * { String name = rs.getString("FUNCTION_NAME"); if
		 * (name.equalsIgnoreCase(functionName)) return true; } } finally {
		 * DB.close(rs); rs = null; } return false;
		 */
	}

	private void executeDDL(String sql, final String trxName) {
		executeDDL(sql, false, trxName);
	}

	private void executeDDL(String sql, boolean ignoreError, final String trxName) {
		if (ignoreError) {
			if (DB.executeUpdate(sql, trxName) != -1) {
				addLog(sql + ";");
			}
		} else {
			DB.executeUpdateEx(sql, trxName);
			addLog(sql + ";");
		}
	}

	private static final boolean equalsIgnoreCase(String s, String s2) {
		if (s == s2)
			return true;
		if (s == null || s2 == null)
			return false;
		return s.trim().equalsIgnoreCase(s2.trim());
	}

	private static final boolean equalsIgnoreCase(String[] a, String[] a2) {
		if (a == a2)
			return true;
		if (a == null || a2 == null)
			return false;

		int length = a.length;
		if (a2.length != length)
			return false;

		for (int i = 0; i < length; i++) {
			String o1 = a[i];
			String o2 = a2[i];
			if (!(o1 == null ? o2 == null : o1.equalsIgnoreCase(o2)))
				return false;
		}
		return true;
	}

	public static class DBIndex {
		public String name;
		public boolean isUnique = true;
		public String[] columnNames;
		public String filterCondition = null;
	}
}
