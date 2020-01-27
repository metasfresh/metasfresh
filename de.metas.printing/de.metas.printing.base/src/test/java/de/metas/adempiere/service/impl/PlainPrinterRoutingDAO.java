package de.metas.adempiere.service.impl;

/*
 * #%L
 * de.metas.printing.base
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.junit.Ignore;

import de.metas.adempiere.model.I_AD_Printer;
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.util.Check;

@Ignore
public class PlainPrinterRoutingDAO implements IPrinterRoutingDAO
{
	@Override
	public <T> List<T> fetchPrinterRoutings(
			@CacheCtx final Properties ctx,
			final int AD_Client_ID, final int AD_Org_ID,
			final int AD_Role_ID, final int AD_User_ID,
			final int C_DocType_ID, final int AD_Process_ID,
			final int AD_Table_ID,
			final String printerType,
			final Class<T> clazz)
	{
		final List<I_AD_PrinterRouting> result = POJOLookupMap.get().getRecords(I_AD_PrinterRouting.class, new IQueryFilter<I_AD_PrinterRouting>()
		{
			@Override
			public boolean accept(final I_AD_PrinterRouting pojo)
			{
				if (!(pojo.getAD_Client_ID() <= 0 || pojo.getAD_Client_ID() == AD_Client_ID))
				{
					return false;
				}
				if (!(pojo.getAD_Org_ID() <= 0 || pojo.getAD_Org_ID() == AD_Org_ID))
				{
					return false;
				}
				if (!(pojo.getAD_Role_ID() <= 0 || pojo.getAD_Role_ID() == AD_Role_ID))
				{
					return false;
				}
				if (!(pojo.getAD_User_ID() <= 0 || pojo.getAD_User_ID() == AD_User_ID))
				{
					return false;
				}
				if (!(pojo.getC_DocType_ID() <= 0 || pojo.getC_DocType_ID() == C_DocType_ID))
				{
					return false;
				}
				if (!(pojo.getAD_Process_ID() <= 0 || pojo.getAD_Process_ID() == AD_Process_ID))
				{
					return false;
				}

				if (!(pojo.getAD_Table_ID() <= 0 || pojo.getAD_Table_ID() == AD_Table_ID))
				{
					return false;
				}
				if (printerType != null && pojo.getAD_Printer() != null && !printerType.equals(pojo.getAD_Printer().getPrinterType()))
				{
					return false;
				}

				return true;
			}
		});

		Collections.sort(result, new Comparator<I_AD_PrinterRouting>()
		{
			@Override
			public int compare(final I_AD_PrinterRouting o1, final I_AD_PrinterRouting o2)
			{
				if (o1.getAD_Client_ID() != o2.getAD_Client_ID())
				{
					return o2.getAD_Client_ID() - o1.getAD_Client_ID(); // DESC
				}
				if (o1.getAD_Org_ID() != o2.getAD_Org_ID())
				{
					return o2.getAD_Org_ID() - o1.getAD_Org_ID(); // DESC
				}
				if (o1.getC_DocType_ID() != o2.getC_DocType_ID())
				{
					return o2.getC_DocType_ID() - o1.getC_DocType_ID(); // DESC
				}
				if (o1.getAD_Role_ID() != o2.getAD_Role_ID())
				{
					return o2.getAD_Role_ID() - o1.getAD_Role_ID();// DESC
				}
				if (o1.getAD_User_ID() != o2.getAD_User_ID())
				{
					return o2.getAD_User_ID() - o1.getAD_User_ID();// DESC
				}

				return o1.getAD_PrinterRouting_ID() - o2.getAD_PrinterRouting_ID(); // ASC
			}
		});

		final List<T> resultConv = new ArrayList<>(result.size());
		for (final I_AD_PrinterRouting r : result)
		{
			resultConv.add(POJOWrapper.create(r, clazz));
		}

		return resultConv;
	}

	@Override
	public I_AD_Printer findPrinterByName(final Properties ctx, final String printerName)
	{
		return POJOLookupMap.get().getFirstOnly(I_AD_Printer.class, new IQueryFilter<I_AD_Printer>()
		{
			@Override
			public boolean accept(final I_AD_Printer pojo)
			{
				return Check.equals(pojo.getPrinterName(), printerName);
			}
		});
	}
}
