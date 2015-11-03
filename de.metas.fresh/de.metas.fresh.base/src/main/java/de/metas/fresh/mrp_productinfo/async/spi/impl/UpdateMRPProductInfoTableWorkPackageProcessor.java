package de.metas.fresh.mrp_productinfo.async.spi.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.DB;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.async.spi.WorkpackagesOnCommitSchedulerTemplate;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelector;
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

	private static final WorkpackagesOnCommitSchedulerTemplate<IMRPProductInfoSelector> SCHEDULER = new WorkpackagesOnCommitSchedulerTemplate<IMRPProductInfoSelector>(
			UpdateMRPProductInfoTableWorkPackageProcessor.class)
	{
		@Override
		protected Properties extractCtxFromItem(final IMRPProductInfoSelector item)
		{
			return InterfaceWrapperHelper.getCtx(item.getModel());
		}

		@Override
		protected String extractTrxNameFromItem(final IMRPProductInfoSelector item)
		{
			return InterfaceWrapperHelper.getTrxName(item.getModel());
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final IMRPProductInfoSelector item)
		{
			return item.getModel();
		}
	};

	public static void schedule(final Object item)
	{
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		final IMRPProductInfoSelector itemToEnqueue = mrpProductInfoSelectorFactory.createOrNull(item);
		if (itemToEnqueue == null)
		{
			return; // nothing to do
		}
		SCHEDULER.schedule(itemToEnqueue);
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage,
			final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
		final IMRPProductInfoSelectorFactory mrpProductInfoSelectorFactory = Services.get(IMRPProductInfoSelectorFactory.class);

		final List<Object> items = queueDAO.retrieveItemsSkipMissing(workpackage, Object.class, localTrxName);

		// we need a fixed ordering to make sure that the items are always updated in the same order, in case we go multi-threaded.
		final SortedSet<IMRPProductInfoSelector> orderedSelectors = new TreeSet<IMRPProductInfoSelector>();

		for (final Object item : items)
		{
			final IMRPProductInfoSelector selector = mrpProductInfoSelectorFactory.createOrNull(item);
			if (selector == null)
			{
				continue;
			}
			orderedSelectors.add(selector);
		}

		for (final IMRPProductInfoSelector selector : orderedSelectors)
		{
			final StringBuilder logMsg = new StringBuilder();
			try
			{
				logMsg.append(selector.toStringForRegularLogging());

				// always update the X_MRP_ProductInfo_Detail_MV rows of the date, product and ASI
				{
					final String functionCall = "\"de.metas.fresh\".X_MRP_ProductInfo_Detail_MV_Refresh";
					logMsg.append("\nCalling " + functionCall);

					doDBFunctionCall(functionCall, selector);
				}

				// If the selector originates from a C_OrderLine, the also update the poor man's MRP records of the BOM-products which
				// the given order line's product is made of.
				// This needs to happen after having refreshed X_MRP_ProductInfo_Detail_MV, because it uses QtyOrdered_Sale_OnDate
				if (InterfaceWrapperHelper.isInstanceOf(selector.getModel(), I_C_OrderLine.class))
				{
					final String functionCall = "\"de.metas.fresh\".x_mrp_productinfo_detail_update_poor_mans_mrp";
					logMsg.append("\nCalling " + functionCall);

					doDBFunctionCall(functionCall, selector);
				}
			}
			finally
			{
				// note: just make sure we log what we got. Assume that any error/exception logging is done by the framework
				ILoggable.THREADLOCAL.getLoggable().addLog(logMsg.toString());
			}
		}

		return Result.SUCCESS;
	}

	private void doDBFunctionCall(final String functionCall,
			final IMRPProductInfoSelector selector)
	{
		final CallableStatement callableStmt = DB.prepareCall("select " + functionCall + "(?,?,?)");
		try
		{
			// we could also identify the parameter by their names, but the names might be "beautified" by a dev.
			callableStmt.setTimestamp(1, selector.getDate());
			callableStmt.setInt(2, selector.getM_Product_ID());
			callableStmt.setInt(3, selector.getM_AttributeSetInstance_ID());

			callableStmt.execute();
		}
		catch (SQLException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
