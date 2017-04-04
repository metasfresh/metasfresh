package de.metas.handlingunits.pporder.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.api.impl.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.impl.HUIssueCostCollectorBuilder;
import de.metas.handlingunits.pporder.api.impl.PPOrderBOMLineProductStorage;
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
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
	//
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

	//
	// Parameters
	private static final Supplier<List<I_PP_Order_Qty>> CandidatesToProcessSupplier_NONE = () -> ImmutableList.of();
	private Supplier<List<I_PP_Order_Qty>> candidatesToProcessSupplier = CandidatesToProcessSupplier_NONE;

	private HUPPOrderIssueReceiptCandidatesProcessor()
	{
	}

	public List<I_PP_Cost_Collector> process()
	{
		final List<I_PP_Cost_Collector> result = new ArrayList<>();

		trxItemProcessorService.<I_PP_Order_Qty, Void> createExecutor()
				.setProcessor(candidate -> {
					final List<I_PP_Cost_Collector> costCollectors = processCandidate(candidate);
					result.addAll(costCollectors);
				})
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.process(getCandidatesToProcess());

		return result;
	}

	private List<I_PP_Cost_Collector> processCandidate(final I_PP_Order_Qty candidate)
	{
		if (candidate.isProcessed())
		{
			logger.debug("Skip processing {} because it was already processed", candidate);
			return ImmutableList.of();
		}

		InterfaceWrapperHelper.setThreadInheritedTrxName(candidate); // just to be sure

		if (isMaterialReceipt(candidate))
		{
			return processReceiptCandidate(candidate);
		}
		else
		{
			return processIssueCandidate(candidate);
		}
	}

	private boolean isMaterialReceipt(final I_PP_Order_Qty candidate)
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = candidate.getPP_Order_BOMLine();
		return ppOrderBOMLine == null || ppOrderBOMBL.isReceipt(ppOrderBOMLine);
	}

	private List<I_PP_Cost_Collector> processReceiptCandidate(final I_PP_Order_Qty candidate)
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

		return ImmutableList.of(cc);
	}

	private List<I_PP_Cost_Collector> processIssueCandidate(final I_PP_Order_Qty candidate)
	{
		//
		// Validate the HU
		final I_M_HU hu = candidate.getM_HU();
		if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
		{
			throw new HUException("Only active HUs can be issued")
					.setParameter("HU", hu)
					.setParameter("HUStatus", hu.getHUStatus())
					.setParameter("candidate", candidate);
		}

		final I_PP_Order_BOMLine ppOrderBOMLine = candidate.getPP_Order_BOMLine();
		final Timestamp movementDate = candidate.getMovementDate();

		//
		// Fully unload the HU
		final String snapshotId;
		final List<I_PP_Cost_Collector> costCollectors;
		{
			//
			// Allocation Source: our HUs
			final HUListAllocationSourceDestination husSource = HUListAllocationSourceDestination.of(hu)
					.setCreateHUSnapshots(true) // Ask to create snapshots of HUs because in case we want to revert the Cost Collector, to be able to recover the HUs (08731).
					.setDestroyEmptyHUs(true); // get rid of those HUs which got empty

			//
			// Allocation Destination: our BOM Lines
			final IAllocationDestination orderBOMLinesDestination;
			{
				final PPOrderBOMLineProductStorage productStorage = new PPOrderBOMLineProductStorage(ppOrderBOMLine);
				orderBOMLinesDestination = new GenericAllocationSourceDestination(productStorage, ppOrderBOMLine);
			}

			//
			// Create and setup context
			final IssueCostCollectorProducer issueCostCollectorProducer = new IssueCostCollectorProducer();
			final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());
			huContext.getTrxListeners().addListener(issueCostCollectorProducer);
			huContext.setDate(movementDate);

			//
			// Create and configure Loader
			final HULoader loader = HULoader.of(husSource, orderBOMLinesDestination);
			loader.setAllowPartialLoads(true);

			//
			// Unload everything from source (our HUs) and move it to manufacturing order BOM lines
			// NOTE: this will also produce the corresponding cost collectors (see de.metas.handlingunits.pporder.api.impl.PPOrderBOMLineHUTrxListener)
			loader.unloadAllFromSource(huContext);

			snapshotId = husSource.getSnapshotId();
			costCollectors = issueCostCollectorProducer.getAllCostCollectors();
		}

		//
		// Get created cost collectors and set the Snapshot_UUID for later recall, in case of an reversal.
		for (final I_PP_Cost_Collector costCollector : costCollectors)
		{
			costCollector.setSnapshot_UUID(snapshotId);
			InterfaceWrapperHelper.save(costCollector);

			// Add issued attributes to manufacturing order (task 08177)
			ppOrderProductAttributeBL.addPPOrderProductAttributes(costCollector);
		}

		return costCollectors;
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

	//
	//
	//
	//
	//
	private final class IssueCostCollectorProducer implements IHUTrxListener
	{
		private final List<I_PP_Cost_Collector> allCostCollectors = new ArrayList<>();

		private IssueCostCollectorProducer()
		{
			super();
		}

		public List<I_PP_Cost_Collector> getAllCostCollectors()
		{
			return allCostCollectors;
		}

		@Override
		public void afterLoad(final IHUContext huContext, final List<IAllocationResult> loadResults)
		{
			createIssueCostCollectors(huContext, loadResults);
		}

		private final void createIssueCostCollectors(final IHUContext huContext, final List<IAllocationResult> loadResults)
		{
			final HUIssueCostCollectorBuilder issueCostCollectorsBuilder = new HUIssueCostCollectorBuilder(huContext);

			//
			// Iterate HU Transactions and build up Issue Cost Collector Candidates
			for (final IAllocationResult loadResult : loadResults)
			{
				final List<IHUTransaction> huTransactions = loadResult.getTransactions();
				for (final IHUTransaction huTransaction : huTransactions)
				{
					issueCostCollectorsBuilder.addHUTransaction(huTransaction);
				}
			}

			//
			// Create Issue Cost Collectors from collected candidates
			final List<I_PP_Cost_Collector> costCollectors = issueCostCollectorsBuilder.createCostCollectors();
			allCostCollectors.addAll(costCollectors);
		}
	}

}
