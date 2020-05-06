package de.metas.processor.api.impl;

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


import java.util.List;
import java.util.Properties;

import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.adempiere.model.I_AD_POProcessor;
import de.metas.processor.api.IPOProcessorBL;
import de.metas.processor.spi.IPOProcessor;

public class POProcessorBL implements IPOProcessorBL
{
	@Override
	public IPOProcessor retrieveProcessorForPO(PO po)
	{
		return retrieveProcessorForTable(po.getCtx(), po.get_Table_ID(), po.get_TrxName());
	}

	@Override
	public IPOProcessor retrieveProcessorForTable(final Properties ctx, final int tableId, final String trxName)
	{
		final I_AD_POProcessor processorDef =
				new Query(ctx, I_AD_POProcessor.Table_Name, I_AD_POProcessor.COLUMNNAME_AD_Table_Source_ID + "=?", trxName)
						.setParameters(tableId)
						.setOnlyActiveRecords(true)
						.firstOnly(I_AD_POProcessor.class);

		return Util.getInstance(IPOProcessor.class, processorDef.getClassname());
	}

	@Override
	public List<I_AD_POProcessor> retrieveProcessorDefForOrg(Properties ctx, int AD_Org_ID)
	{
		final List<I_AD_POProcessor> list =
				new Query(Env.getCtx(), I_AD_POProcessor.Table_Name, I_AD_POProcessor.COLUMNNAME_AD_Org_ID + "=?", null)
						.setParameters(AD_Org_ID)
						.setOnlyActiveRecords(true)
						.list(I_AD_POProcessor.class);
		return list;
	}

	@Override
	public String getSourceTableName(I_AD_POProcessor processorDef)
	{
		return MTable.getTableName(Env.getCtx(), processorDef.getAD_Table_Source_ID());
	}

}
