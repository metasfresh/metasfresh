package org.adempiere.ad.service;

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


import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_InfoWindow_From;

import de.metas.util.ISingletonService;

public interface IADInfoWindowDAO extends ISingletonService
{

	List<I_AD_InfoWindow_From> retrieveFroms(I_AD_InfoWindow infoWindow);

	I_AD_InfoWindow retrieveInfoWindowByTableId(Properties ctx, int table_id);

	/**
	 * 
	 * @param ctx
	 * @param tableName
	 * @return the first match (preferring one with <code>IsDefault='Y'</code>) or <code>null</code>.
	 */
	I_AD_InfoWindow retrieveInfoWindowByTableName(Properties ctx, String tableName);

	List<I_AD_InfoColumn> retrieveInfoColumns(I_AD_InfoWindow infoWindow);

	/**
	 * Retrieves the info column with the given <code>infoWindow</code> and whose <code>AD_Element</code>'s <code>ColumnName</code> is equal to the given <code>columnName</code>.
	 * <p>
	 * <b>Important:</b> the info columns's own <code>ColumnName</code> does not play any role in the retrieval. Note that this is a more or less arbitrary decision. I wanted it to be simple (no
	 * unnecessary fallbacks etc) and AD_Element_ID seemed to be the more stable candidate to do retrievals by.
	 * 
	 * @param infoWindow
	 * @param columnName
	 * @return the retrieved record or <code>null</code>.
	 * @throws {@link DBException} if there is more than one match.
	 */
	I_AD_InfoColumn retrieveInfoColumnByColumnName(I_AD_InfoWindow infoWindow, String columnName);

	I_AD_InfoColumn retrieveTreeInfoColumn(I_AD_InfoWindow infoWindow);

	/**
	 * Get query info columns for a specific Info Window
	 * 
	 * @param infoWindow
	 * @return
	 */
	List<I_AD_InfoColumn> retrieveQueryColumns(I_AD_InfoWindow infoWindow);

	/**
	 * Get displayed info columns for a specific Info Window
	 * 
	 * @param infoWindow
	 * @return
	 */
	List<I_AD_InfoColumn> retrieveDisplayedColumns(I_AD_InfoWindow infoWindow);

	/**
	 * Gets Info Windows that need to be added in main menu.
	 * <p>
	 * <b>IMPORTANT:</b> method will never return "Info Product" and "Info BPartner", because their appearing is already controlled from AD_Role.
	 * 
	 * @param ctx
	 * @return
	 */
	List<I_AD_InfoWindow> retrieveInfoWindowsInMenu(Properties ctx);
}
