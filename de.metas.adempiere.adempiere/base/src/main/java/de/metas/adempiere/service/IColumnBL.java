package de.metas.adempiere.service;

import java.util.Optional;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IColumnBL extends ISingletonService
{

	/**
	 * Get the context AD Table ID for the table that is in pair with the Record_ID column.
	 * Note that both table and record column names must have the same prefix.
	 * Example: PREFIX_Record_ID and either PREFIX_AD_Table_ID or PREFIX_Table_ID
	 *
	 * @param m_ctx
	 * @param m_curWindowNo
	 * @param columnName
	 * @return the context as ID if found, 0 otherwise
	 */
	int getContextADTableID(Properties m_ctx, int m_curWindowNo, String columnName);

	/**
	 * Verify if the given columnName is "Record_ID" or has the form "Prefix_Record_ID"
	 *
	 * @param columnName
	 * @return true if record_ID form, false otherwise
	 */
	boolean isRecordColumnName(String columnName);

	/**
	 * Get the primary key column for the given <code>tableName</code>.
	 * The difference to {@link InterfaceWrapperHelper#getKeyColumnName(String)} is that
	 * <li>this method shall return the "real" column name from {@link org.compiere.model.POInfo} and
	 * <li>that it shall throw an exception if there are more or less than one key column and finally
	 * <li>that the key column name might not be made up of <code>tablename + "_ID"</code>, but whatever is declared to be the key or single parent column in the application dictionary.
	 *
	 * @param tableName
	 * @return
	 * @throws org.adempiere.ad.table.exception.NoSingleKeyColumnException if the given table does not have exactly one key column.
	 */
	String getSingleKeyColumn(String tableName);

	/**
	 * For the given <code>tableName</code> and <code>recordColumnName</code>, return the name of the column that contains the respective <code>AD_Table_ID</code>.
	 * Do not fail if this column can't be found.
	 *
	 * @param tableName
	 * @param recordColumnName
	 * @return
	 */
	Optional<String> getTableColumnName(String tableName, String recordColumnName);

}
