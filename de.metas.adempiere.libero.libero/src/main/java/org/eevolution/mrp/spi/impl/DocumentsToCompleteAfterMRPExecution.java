package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPExecutorJobs;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

import de.metas.document.engine.IDocActionBL;

public class DocumentsToCompleteAfterMRPExecution implements Runnable
{
	private static transient String JOBNAME_DocumentsToCompleteAfterMRPExecution = DocumentsToCompleteAfterMRPExecution.class.getName();

	public static final DocumentsToCompleteAfterMRPExecution getCreate(final IMRPExecutor mrpExecutor)
	{
		final IMRPExecutorJobs jobs = mrpExecutor.getAfterAllSegmentsRunJobs();
		final DocumentsToCompleteAfterMRPExecution job = jobs.getCreateJob(JOBNAME_DocumentsToCompleteAfterMRPExecution
				, new IReference<DocumentsToCompleteAfterMRPExecution>()
				{
					@Override
					public DocumentsToCompleteAfterMRPExecution getValue()
					{
						return new DocumentsToCompleteAfterMRPExecution();
					}
				});

		return job;
	}

	// services
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Set<Integer> ppOrderIdsToComplete = new LinkedHashSet<>();
	private final Set<Integer> ddOrderMRPDemandIdsToComplete = new LinkedHashSet<>();

	private DocumentsToCompleteAfterMRPExecution()
	{
		super();
	}

	private IContextAware getContext()
	{
		final PlainContextAware context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);
		return context;
	}

	public void enqueueDDOrderForMRPDemand(final I_PP_MRP mrpDemand)
	{
		final int mrpDemandId = mrpDemand.getPP_MRP_ID();
		ddOrderMRPDemandIdsToComplete.add(mrpDemandId);
	}

	public void enqueuePPOrder(final I_PP_Order ppOrder)
	{
		ppOrderIdsToComplete.add(ppOrder.getPP_Order_ID());
	}

	@Override
	public void run()
	{
		//
		// Complete PP_Orders first, because DD_Orders depends on that
		completePPOrders();

		//
		// Complete DD_Orders
		completeDDOrders();
	}

	private final void completePPOrders()
	{
		final List<I_PP_Order> ppOrdersToComplete = retrievePPOrdersToCompleteAndClear();
		for (final I_PP_Order ppOrder : ppOrdersToComplete)
		{
			InterfaceWrapperHelper.refresh(ppOrder); // make sure it was not completed/updated in meantime
			completePPOrderIfNeeded(ppOrder);
		}
	}

	private final void completePPOrderIfNeeded(final I_PP_Order ppOrder)
	{
		if (!docActionBL.isStatusDraftedOrInProgress(ppOrder))
		{
			return;
		}

		try
		{
			docActionBL.processEx(ppOrder, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
		}
		catch (final Exception e)
		{
			logger.warn("Failed to complete PP_Order {}. Skipped.", ppOrder);
		}
	}

	private final void completeDDOrders()
	{
		final List<I_DD_Order> ddOrdersToComplete = retrieveDDOrdersToCompleteAndClear();
		for (final I_DD_Order ddOrder : ddOrdersToComplete)
		{
			InterfaceWrapperHelper.refresh(ddOrder); // make sure it was not completed/updated in meantime
			completeDDOrderIfNeeded(ddOrder);
		}
	}

	private void completeDDOrderIfNeeded(final I_DD_Order ddOrder)
	{
		try
		{
			ddOrderBL.completeDDOrderIfNeeded(ddOrder);
		}
		catch (final Exception e)
		{
			logger.warn("Failed to complete DD_Order {}. Skipped.", ddOrder);
		}
	}

	private final List<I_PP_Order> retrievePPOrdersToCompleteAndClear()
	{
		if (ppOrderIdsToComplete.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_PP_Order> ppOrdersToComplete = queryBL
				.createQueryBuilder(I_PP_Order.class, getContext())
				.addInArrayOrAllFilter(I_PP_Order.COLUMN_PP_Order_ID, ppOrderIdsToComplete)
				//
				// Not already processed
				.addInArrayOrAllFilter(I_PP_Order.COLUMNNAME_DocStatus, DocAction.STATUS_Drafted, DocAction.STATUS_InProgress)
				.addEqualsFilter(I_PP_Order.COLUMN_Processed, false)
				//
				// Retrieve them
				.create()
				.list();

		ppOrderIdsToComplete.clear();

		return ppOrdersToComplete;
	}

	private List<I_DD_Order> retrieveDDOrdersToCompleteAndClear()
	{
		if (ddOrderMRPDemandIdsToComplete.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Retrieve MRP Demands
		final List<I_PP_MRP> mrpDemands = queryBL
				.createQueryBuilder(I_PP_MRP.class, getContext())
				.addInArrayOrAllFilter(I_PP_MRP.COLUMN_PP_MRP_ID, ddOrderMRPDemandIdsToComplete)
				.create()
				.list();

		//
		// Clear PP_MRP_IDs list to make sure we will not do it again
		ddOrderMRPDemandIdsToComplete.clear();

		//
		// Collect DD_Order_IDs from MRP Demands by navigating forward
		final Set<Integer> ddOrderIdsToComplete = new HashSet<>();
		for (final I_PP_MRP mrpDemand : mrpDemands)
		{
			//
			// Restrictions
			final int ppPlantId = mrpDemand.getS_Resource_ID();
			if (ppPlantId <= 0)
			{
				// shall not happen
				continue;
			}

			final DDOrderLineMRPForwardNavigator forwardNavigator = new DDOrderLineMRPForwardNavigator()
					.setContext(getContext())
					.setPP_Plant_ID(ppPlantId);

			forwardNavigator.navigateForward(mrpDemand);
			final Set<Integer> collectedDD_Order_IDs = forwardNavigator.getCollectedDD_Order_IDs();
			ddOrderIdsToComplete.addAll(collectedDD_Order_IDs);
		}
		if (ddOrderIdsToComplete.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Retrieve DD_Orders
		final List<I_DD_Order> ddOrdersToComplete = queryBL
				.createQueryBuilder(I_DD_Order.class, getContext())
				.addInArrayOrAllFilter(I_DD_Order.COLUMN_DD_Order_ID, ddOrderIdsToComplete)
				//
				// Not already processed
				.addInArrayOrAllFilter(I_DD_Order.COLUMNNAME_DocStatus, DocAction.STATUS_Drafted, DocAction.STATUS_InProgress)
				.addEqualsFilter(I_DD_Order.COLUMN_Processed, false)
				//
				// Retrieve them
				.create()
				.list();
		return ddOrdersToComplete;
	}
}
