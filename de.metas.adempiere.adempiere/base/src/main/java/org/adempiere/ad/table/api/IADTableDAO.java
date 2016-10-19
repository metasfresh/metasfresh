package org.adempiere.ad.table.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Table;

public interface IADTableDAO extends ISingletonService
{
	/**
	 * @param tableName
	 * @param columnName
	 * @return {@link I_AD_Column} if column was found
	 *
	 * @throws AdempiereException if table was not found
	 * @throws AdempiereException if column was not found
	 */
	I_AD_Column retrieveColumn(String tableName, String columnName);

	/**
	 *
	 * @param columnName
	 * @return the element with the given <code>columnName</code> or <code>null</code>. Note that {@link I_AD_Element#COLUMNNAME_ColumnName} is unique.
	 */
	I_AD_Element retrieveElement(String columnName);

	/**
	 * @param tableName
	 * @param columnName
	 * @return {@link I_AD_Column} if column was found, or <code>null</code> otherwise
	 *
	 * @throws AdempiereException if table was not found
	 */
	I_AD_Column retrieveColumnOrNull(String tableName, String columnName);

	/**
	 * @param tableName
	 * @param columnName
	 * @return true if table contains selected column, false otherwise
	 *
	 * @throws AdempiereException if table was not found
	 */
	boolean hasColumnName(String tableName, String columnName);

	/**
	 * @param adColumnId
	 * @return ColumnName or null
	 */
	String retrieveColumnName(int adColumnId);

	/**
	 *
	 * @param adTableId
	 * @return the name for the given <code>AD_Table_ID</code> or <code>null</code> if the given ID is less or equal zero
	 */
	String retrieveTableName(int adTableId);

	/**
	 * @param tableName, can be case insensitive
	 * @return AD_Table_ID or -1
	 */
	int retrieveTableId(String tableName);

	List<I_AD_Table> retrieveAllTables(Properties ctx, String trxName);

	/**
	 * @param column
	 * @return true if the column is an SQL-Column (ColumnSQL is not empty)
	 */
	boolean isVirtualColumn(I_AD_Column column);

	/**
	 * Check if given AD_Table_ID and TableName are matching.
	 *
	 * @param tableName
	 * @param adTableId
	 * @return true if they are matching
	 */
	boolean isTableId(String tableName, int adTableId);

	/**
	 * @return true if given table name really exist
	 */
	boolean isExistingTable(String tableName);

	/**
	 * Retrieves the default window name of given table.
	 *
	 * @param ctx
	 * @param tableName
	 * @return default window name, in context language.
	 */
	String retrieveWindowName(Properties ctx, String tableName);

	/**
	 * If the old and new <code>tableName</code> of the given <code>table</code> differ, then rename the table sequence too.
	 *
	 * @param table
	 */
	void onTableNameRename(final I_AD_Table table);

	/**
	 * REtrieves a query builder for the given parameters (case insensitive!) that can be refined further.
	 *
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	IQueryBuilder<I_AD_Column> retrieveColumnQueryBuilder(String tableName, String columnName);

	<T extends I_AD_Table> T retrieveTable(String tableName, Class<T> clazz);
}
