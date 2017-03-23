/**
 * 
 */
package org.adempiere.ad.migration.logger;

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


import java.util.Set;

import org.adempiere.ad.session.MFSession;
import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/**
 * @author tsa
 * 
 */
public interface IMigrationLogger extends ISingletonService
{
	boolean isLogTableName(String tableName);

	/**
	 * Create migration step using the current {@link IMigrationLoggerContext} for the specified {@link PO}
	 * 
	 * @param migrationCtx
	 * @param po
	 * @param info
	 * @param event
	 */
	void logMigration(IMigrationLoggerContext migrationCtx, PO po, POInfo info, String event);

	/**
	 * Create migration step using the current session for the specified {@link PO}
	 * 
	 * @param session
	 * @param po
	 * @param info
	 * @param event
	 */
	void logMigration(MFSession session, PO po, POInfo info, String event);

	/**
	 * Create a raw SQL migration step for the specified {@link PO}
	 * 
	 * @param session
	 * @param po
	 * @param info
	 * @param event
	 */
	void logMigrationSQL(PO contextPO, String sql);

	/**
	 * Add table to ignore list (ignore specified table when logging migration steps).
	 * 
	 * @param tableName
	 */
	void addTableToIgnoreList(String tableName);

	/**
	 * Remove table from ignore list (do not ignore specified table when logging migration steps).
	 * 
	 * @param tableName
	 */
	void removeTableFromIgnoreList(String tableName);

	/**
	 * Gets a list of table names that shall be ignored when creating migration scripts.
	 * 
	 * NOTE:
	 * <ul>
	 * <li>all table names are uppercase
	 * <li>based on current login #AD_Client_ID, the list could be different
	 * </ul>
	 * 
	 * @return list of table names
	 */
	Set<String> getTablesToIgnoreUC();
}
