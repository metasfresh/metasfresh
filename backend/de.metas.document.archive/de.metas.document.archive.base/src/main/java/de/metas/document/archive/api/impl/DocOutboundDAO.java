package de.metas.document.archive.api.impl;

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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;

public class DocOutboundDAO extends AbstractDocOutboundDAO
{
	@Override
	public I_C_Doc_Outbound_Log retrieveLog(final I_AD_Archive archive)
	{
		Check.assume(archive != null, "archive not null");

		final int adTableId = archive.getAD_Table_ID();
		final int recordId = archive.getRecord_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);

		final String whereClause = I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID + "=?"
				+ " AND " + I_C_Doc_Outbound_Log.COLUMNNAME_Record_ID + "=?";

		final I_C_Doc_Outbound_Log docExchange = new Query(ctx, I_C_Doc_Outbound_Log.Table_Name, whereClause, trxName)
				.setParameters(adTableId, recordId)
				.firstOnly(I_C_Doc_Outbound_Log.class);

		return docExchange;
	}
	

	@Override
	@Cached(cacheName = I_C_Doc_Outbound_Config.Table_Name + "#All")
	public List<I_C_Doc_Outbound_Config> retrieveAllConfigs()
	{
		final Properties ctx = Env.getCtx();
		return new Query(ctx, I_C_Doc_Outbound_Config.Table_Name, null, ITrx.TRXNAME_None)
				.setOnlyActiveRecords(true)
				.list(I_C_Doc_Outbound_Config.class);
	}
	
	
}
