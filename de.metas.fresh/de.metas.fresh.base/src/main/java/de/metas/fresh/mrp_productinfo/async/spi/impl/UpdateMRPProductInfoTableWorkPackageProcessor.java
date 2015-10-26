package de.metas.fresh.mrp_productinfo.async.spi.impl;

import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.util.DB;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.fresh.mrp_productinfo.IMRPProdcutInfoSelector;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelectorFactory;

/*
 * #%L
 * de.metas.fresh.base
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

public class UpdateMRPProductInfoTableWorkPackageProcessor extends WorkpackageProcessorAdapter
{

	private static final WorkpackagesOnCommitSchedulerTemplate<IMRPProdcutInfoSelector> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<IMRPProdcutInfoSelector>(
			UpdateMRPProductInfoTableWorkPackageProcessor.class)
	{
		@Override
		protected Properties extractCtxFromItem(IMRPProdcutInfoSelector item)
		{
			return InterfaceWrapperHelper.getCtx(item.getModel());
		}

		@Override
		protected String extractTrxNameFromItem(IMRPProdcutInfoSelector item)
		{
			return InterfaceWrapperHelper.getTrxName(item.getModel());
		}

		@Override
		protected Object extractModelToEnqueueFromItem(IMRPProdcutInfoSelector item)
		{
			return item.getModel();
		}
	};

	public static void schedule(Object item)
	{
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		// will be discarded by SCHEDULER if null
		final IMRPProdcutInfoSelector itemToEnqueue = mrpProductInfoSelectorFactory.createOrNull(item);
		SCHEDULER.schedule(itemToEnqueue);
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage,
			final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		final List<Object> items = queueDAO.retrieveItemsSkipMissing(workpackage, Object.class, localTrxName);

		// we need a fixed ordering to make sure that the items are always updates in the same order, in case we go multi-threaded.
		final SortedSet<IMRPProdcutInfoSelector> selectors = new TreeSet<IMRPProdcutInfoSelector>();

		for (final Object item : items)
		{
			final IMRPProdcutInfoSelector selector = mrpProductInfoSelectorFactory.createOrNull(item);
			if (selector == null)
			{
				continue;
			}
			selectors.add(selector);
		}

		for (final IMRPProdcutInfoSelector selector : selectors)
		{
			ILoggable.THREADLOCAL.getLoggable().addLog(
					"Calling X_MRP_ProductInfo_Detail_MV_Refresh with "
							+ "[Date=" + selector.getDate() + ",M_Product_ID=" + selector.getM_Product_ID() + ",M_AttributeSetInstance_ID=" + selector.getM_AttributeSetInstance_ID() + "]");

			// this is a hack: we want to call the X_MRP_ProductInfo_Detail_MV_Refresh, but that will return one "void" record. So we do a trivial update instead.
			// Note that due to the where-clause nothing will actually be updated
			final int no = DB.executeUpdateEx("UPDATE AD_Client SET AD_Client_ID=AD_Client_ID WHERE (select X_MRP_ProductInfo_Detail_MV_Refresh(?,?,?) IS NOT NULL)",
					new Object[] { selector.getDate(), selector.getM_Product_ID(), selector.getM_AttributeSetInstance_ID() },
					ITrx.TRXNAME_None);
			Check.assume(no == 0, "This is just a dummy-update to encapsulate the select; nothing shall be updated.");
		}

		return Result.SUCCESS;
	}
}
