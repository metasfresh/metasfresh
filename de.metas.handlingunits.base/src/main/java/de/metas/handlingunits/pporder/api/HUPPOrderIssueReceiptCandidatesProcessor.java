package de.metas.handlingunits.pporder.api;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.api.impl.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Processes given {@link I_PP_Order_Qty}.
 *
 * By default, {@link #process()} will process each candidate in it's own transaction/savepoint.
 *
 * Processing one {@link I_PP_Order_Qty} means:
 * <ul>
 * <li>generate cost collector
 * <li>link the HU to cost collector
 * <li>in case of material receipt activate the HU
 * <li>in case of material issue: TODO
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUPPOrderIssueReceiptCandidatesProcessor
{
	public static final HUPPOrderIssueReceiptCandidatesProcessor newInstance()
	{
		return new HUPPOrderIssueReceiptCandidatesProcessor();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(HUPPOrderIssueReceiptCandidatesProcessor.class);
	private final transient ITrxItemProcessorExecutorService trxItemProcessorService = Services.get(ITrxItemProcessorExecutorService.class);
	//
	private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

	private static final Supplier<List<I_PP_Order_Qty>> CandidatesToProcessSupplier_NONE = () -> ImmutableList.of();
	private Supplier<List<I_PP_Order_Qty>> candidatesToProcessSupplier = CandidatesToProcessSupplier_NONE;
	
	private HUPPOrderIssueReceiptCandidatesProcessor()
	{
	}

	public void process()
	{
		trxItemProcessorService.<I_PP_Order_Qty, Void> createExecutor()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setProcessor(this::processCandidate)
				.process(getCandidatesToProcess().iterator());
	}

	private void processCandidate(final I_PP_Order_Qty candidate)
	{
		if (candidate.isProcessed())
		{
			logger.debug("Skip processing {} because it was already processed", candidate);
			return;
		}

		InterfaceWrapperHelper.setThreadInheritedTrxName(candidate); // just to be sure

		if (isMaterialReceipt(candidate))
		{
			processReceiptCandidate(candidate);
		}
		else
		{
			processIssueCandidate(candidate);
		}
	}

	private boolean isMaterialReceipt(final I_PP_Order_Qty candidate)
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = candidate.getPP_Order_BOMLine();
		return ppOrderBOMLine == null || ppOrderBOMBL.isReceipt(ppOrderBOMLine);
	}

	private void processReceiptCandidate(final I_PP_Order_Qty candidate)
	{
		final I_M_HU hu = candidate.getM_HU();
		Preconditions.checkNotNull(hu, "No HU for %s", candidate);

		//
		// Create material receipt and activate the HU
		final IReceiptCostCollectorCandidate costCollectorCandidate = ReceiptCostCollectorCandidate.builder()
				.PP_Order(candidate.getPP_Order())
				.PP_Order_BOMLine(candidate.getPP_Order_BOMLine())
				.movementDate(candidate.getMovementDate())
				.qtyToReceive(candidate.getQty())
				.M_Product(candidate.getM_Product())
				.C_UOM(candidate.getC_UOM())
				.M_Locator_ID(candidate.getM_Locator_ID())
				.build();
		final I_PP_Cost_Collector cc = huPPCostCollectorBL.createReceipt(costCollectorCandidate, hu);

		//
		// Update the (planning) qty record
		candidate.setPP_Cost_Collector(cc);
		candidate.setProcessed(true);
		InterfaceWrapperHelper.save(candidate);
	}

	private void processIssueCandidate(final I_PP_Order_Qty candidates)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public HUPPOrderIssueReceiptCandidatesProcessor setCandidatesToProcess(final Supplier<List<I_PP_Order_Qty>> candidatesToProcessSupplier)
	{
		Preconditions.checkNotNull(candidatesToProcessSupplier, "candidatesToProcessSupplier");
		this.candidatesToProcessSupplier = candidatesToProcessSupplier;
		return this;
	}

	public HUPPOrderIssueReceiptCandidatesProcessor setCandidatesToProcess(final List<I_PP_Order_Qty> candidatesToProcess)
	{
		Preconditions.checkNotNull(candidatesToProcess, "candidatesToProcess");
		if (candidatesToProcess.isEmpty())
		{
			setCandidatesToProcess(CandidatesToProcessSupplier_NONE);
		}
		else
		{
			setCandidatesToProcess(() -> candidatesToProcess);
		}
		return this;
	}

	public HUPPOrderIssueReceiptCandidatesProcessor setCandidatesToProcessByPPOrderId(final int ppOrderId, final Collection<Integer> ppOrderQtyIds)
	{
		if (ppOrderQtyIds == null || ppOrderQtyIds.isEmpty())
		{
			setCandidatesToProcess(CandidatesToProcessSupplier_NONE);
		}
		else
		{
			setCandidatesToProcess(() -> huPPOrderQtyDAO.retrieveOrderQtys(ppOrderId)
					.stream()
					.filter(candidate -> !candidate.isProcessed()) // not already processed
					.filter(candidate -> ppOrderQtyIds.contains(candidate.getPP_Order_Qty_ID())) // matching the IDs list
					.collect(GuavaCollectors.toImmutableList()));
		}

		return this;
	}

	private List<I_PP_Order_Qty> getCandidatesToProcess()
	{
		return candidatesToProcessSupplier.get();
	}
}
