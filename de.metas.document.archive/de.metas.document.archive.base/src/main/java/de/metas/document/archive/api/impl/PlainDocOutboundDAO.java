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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Archive;

import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;

public class PlainDocOutboundDAO extends AbstractDocOutboundDAO
{

	@Override
	public List<I_C_Doc_Outbound_Config> retrieveAllConfigs()
	{
		return POJOLookupMap.get().getRecords(I_C_Doc_Outbound_Config.class);
	}

	@Override
	public I_C_Doc_Outbound_Log retrieveLog(final I_AD_Archive archive)
	{
		Check.assume(archive != null, "archive not null");

		final int adTableId = archive.getAD_Table_ID();
		final int recordId = archive.getRecord_ID();

		return POJOLookupMap.get().getFirstOnly(I_C_Doc_Outbound_Log.class, new IQueryFilter<I_C_Doc_Outbound_Log>()
		{
			@Override
			public boolean accept(final I_C_Doc_Outbound_Log pojo)
			{
				if (pojo.getAD_Table_ID() != adTableId)
				{
					return false;
				}
				if (pojo.getRecord_ID() != recordId)
				{
					return false;
				}
				return true;
			}
		});
	}

}
