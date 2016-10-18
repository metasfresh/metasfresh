package de.metas.document.archive.api;

/*
 * #%L
 * de.metas.document.archive.base
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

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Archive;

import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;

public interface IDocOutboundDAO extends ISingletonService
{
	/**
	 * Retrieve all <b>active</b> {@link I_C_Doc_Outbound_Config}s for <b>all</b> clients.
	 *
	 * @return
	 */
	List<I_C_Doc_Outbound_Config> retrieveAllConfigs();

	/**
	 * Retrieve {@link I_C_Doc_Outbound_Config} for given tableId. First current AD_Client_ID will be checked if not found, it will be checked on System level.
	 *
	 * @param ctx
	 * @param tableId
	 * @return config or null
	 */
	I_C_Doc_Outbound_Config retrieveConfig(Properties ctx, int tableId);

	/**
	 * Retrieve {@link I_C_Doc_Outbound_Config} for given <code>model</code>.
	 *
	 * @param model
	 * @return config or null
	 * @see #retrieveConfig(Properties, int)
	 */
	I_C_Doc_Outbound_Config retrieveConfigForModel(Object model);

	/**
	 * Retrieve {@link I_C_Doc_Outbound_Log} for give archive (AD_Table_ID and Record_ID fields will be used for matching)
	 *
	 * @param archive
	 * @return {@link I_C_Doc_Outbound_Log} record or null if not found
	 */
	I_C_Doc_Outbound_Log retrieveLog(I_AD_Archive archive);

	/**
	 * Find among the given <code>log</code>'s {@link I_C_Doc_Outbound_Log_Line}s the latest one with action <code>PDF</code> (i.e highest ID)
	 *
	 * @param log
	 * @return log line
	 */
	I_C_Doc_Outbound_Log_Line retrieveCurrentPDFArchiveLogLineOrNull(I_C_Doc_Outbound_Log log);

	/**
	 * Decorate query builder with PDF archive log line filters
	 *
	 * @param contextProvider
	 * @param queryBuilder
	 */
	void addPDFArchiveLogLineFilters(IQueryBuilder<I_C_Doc_Outbound_Log_Line> queryBuilder);
	
	/**
	 *  Retrieves last created {@link I_C_Doc_Outbound_Log} for given bpartner and table
	 * @param contextProvider
	 * @param bpartnerId
	 * @param AD_Table_ID
	 * @return
	 */
	I_C_Doc_Outbound_Log retrieveLog(final IContextAware contextProvider, int bpartnerId, int AD_Table_ID);
}
