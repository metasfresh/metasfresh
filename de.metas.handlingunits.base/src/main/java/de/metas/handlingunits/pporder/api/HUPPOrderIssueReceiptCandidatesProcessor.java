package de.metas.handlingunits.pporder.api;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.api.impl.ReceiptCostCollectorCandidate;
import org.eevolution.model.X_PP_Order_BOMLine;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.materialtracking.IHUPPOrderMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.impl.PPOrderBOMLineProductStorage;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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
 * <li>in case of material receipt activate the HU, create CC
 * <li>in case of material issue: consume/destroy the HU, create CC
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
					final I_PP_Cost_Collector costCollector = processCandidate(candidate);
					if (costCollector != null)
					{
						result.add(costCollector);
					}
				})
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.process(getCandidatesToProcess());

		return result;
	}

	private I_PP_Cost_Collector processCandidate(final I_PP_Order_Qty candidate)
	{
		if (candidate.isProcessed())
		{
			logger.debug("Skip processing {} because it was already processed", candidate);
			return null;
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
		final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine = candidate.getPP_Order_BOMLine();
		return ppOrderBOMLine == null || PPOrderUtil.isReceipt(ppOrderBOMLine.getComponentType());
	}

	private final void markProcessedAndSave(@NonNull final I_PP_Order_Qty candidate, @NonNull final I_PP_Cost_Collector cc)
	{
		Preconditions.checkArgument(!candidate.isProcessed(), "candidate was already processed: %s", candidate);
		candidate.setPP_Cost_Collector(cc);
		candidate.setProcessed(true);
		huPPOrderQtyDAO.save(candidate);
	}

	private I_PP_Cost_Collector processReceiptCandidate(final I_PP_Order_Qty candidate)
	{
		// NOTE: we assume the candidate was not processed yet

		//
		// Validate the HU
		final I_M_HU hu = candidate.getM_HU();
		Preconditions.checkNotNull(hu, "No HU for %s", candidate);
		if (!X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()))
		{
			throw new HUException("Only planning HUs can be received")
					.setParameter("HU", hu)
					.setParameter("HUStatus", hu.getHUStatus())
					.setParameter("candidate", candidate);
		}

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

		markProcessedAndSave(candidate, cc);

		return cc;
	}

	private I_PP_Cost_Collector processIssueCandidate(final I_PP_Order_Qty candidate)
	{
		// NOTE: we assume the candidate was not processed yet

		//
		// Validate the HU
		final I_M_HU hu = candidate.getM_HU();
		if (!ImmutableSet.of(
				X_M_HU.HUSTATUS_Active,
				X_M_HU.HUSTATUS_Issued)
				.contains(hu.getHUStatus()))
		{
			// if operated by the swing-ui, this code has to deal with active HUs because the swingUI skips that part of the workflow
			throw new HUException("Only HUs with status 'issued' and 'active' can be finalized with their PP_Cost_Collector and destroyed")
					.setParameter("HU", hu)
					.setParameter("HUStatus", hu.getHUStatus())
					.setParameter("candidate", candidate);
		}

		//
		final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(candidate.getPP_Order_BOMLine(), I_PP_Order_BOMLine.class);
		final Timestamp movementDate = candidate.getMovementDate();

		//
		// Calculate the quantity to issue.
		final Quantity qtyToIssue = calculateQtyToIssue(ppOrderBOMLine, candidate);
		//
		// Update candidate's qty to issue
		// NOTE: in case of "IssueOnlyReceived" issue method the qty to issue is calculated just in time. We assume it's saved by caller
		candidate.setQty(qtyToIssue.getQty());
		//
		if (qtyToIssue.isZero())
		{
			logger.debug("Skipping candidate ZERO quantity candidate: {}, bomLine={}", candidate, ppOrderBOMLine);
			return null;
		}

		//
		// Fully unload the HU
		final IssueCandidatesBuilder issueCostCollectorsBuilder = new IssueCandidatesBuilder()
				.setMovementDate(movementDate);
		final String snapshotId;
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
			final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());
			huContext.getTrxListeners().callAfterLoad(issueCostCollectorsBuilder::addAllocationResults);

			//
			// Allocation request
			final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext //
					, candidate.getM_Product() // product
					, qtyToIssue // the quantity to issue
					, SystemTime.asDayTimestamp() // transaction date
					, null // referenced model: IMPORTANT to be null, else our build won't detect correctly which is the HU transaction and which is the BOMLine-side transaction
					, true // forceQtyAllocation: yes, we want to transfer exactly how much we specified in the candidate
			);

			//
			// Unload from HU and load to BOM line
			HULoader.of(husSource, orderBOMLinesDestination)
					.setAllowPartialLoads(false)
					.setAllowPartialUnloads(false)
					.load(allocationRequest);

			snapshotId = husSource.getSnapshotId();
		}

		// Create cost collectors and mark the candidate as processed
		{
			final I_PP_Cost_Collector cc = issueCostCollectorsBuilder.createSingleCostCollector(snapshotId);
			markProcessedAndSave(candidate, cc);

			return cc;
		}
	}

	private Quantity calculateQtyToIssue(final I_PP_Order_BOMLine targetBOMLine, final I_PP_Order_Qty candidate)
	{
		//
		// Case: if this is an Issue BOM Line, IssueMethod is Backflush and we did not over-issue on it yet
		// => enforce the capacity to Projected Qty Required (i.e. standard Qty that needs to be issued on this line).
		// initial concept: http://dewiki908/mediawiki/index.php/07433_Folie_Zuteilung_Produktion_Fertigstellung_POS_%28102170996938%29
		// additional (use of projected qty required): http://dewiki908/mediawiki/index.php/07601_Calculation_of_Folie_in_Action_Receipt_%28102017845369%29
		final String issueMethod = targetBOMLine.getIssueMethod();
		if (X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived.equals(issueMethod))
		{
			return ppOrderBOMBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(targetBOMLine, candidate.getC_UOM());
		}
		else
		{
			return Quantity.of(candidate.getQty(), candidate.getC_UOM());
		}
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

	public HUPPOrderIssueReceiptCandidatesProcessor setCandidatesToProcessByPPOrderId(final int ppOrderId)
	{
		setCandidatesToProcess(() -> huPPOrderQtyDAO.retrieveOrderQtys(ppOrderId)
				.stream()
				.filter(candidate -> !candidate.isProcessed()) // not already processed
				.collect(GuavaCollectors.toImmutableList()));
		return this;
	}

	private List<I_PP_Order_Qty> getCandidatesToProcess()
	{
		return candidatesToProcessSupplier.get();
	}

	/**
	 * Aggregates {@link IHUTransactionCandidate}s and creates {@link I_PP_Cost_Collector}s for issuing materials to manufacturing order
	 *
	 * @author tsa
	 *
	 */
	private static final class IssueCandidatesBuilder
	{
		// Services
		private final transient IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		private final transient IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
		//
		private final transient IHUPPOrderMaterialTrackingBL huPPOrderMaterialTrackingBL = Services.get(IHUPPOrderMaterialTrackingBL.class);
		private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);

		//
		// Parameters
		private Date movementDate = null;

		// Status
		private final Map<Integer, IssueCandidate> candidatesByOrderBOMLineId = new HashMap<>();

		public IssueCandidatesBuilder setMovementDate(Date movementDate)
		{
			this.movementDate = movementDate;
			return this;
		}

		private Date getMovementDate()
		{
			Preconditions.checkNotNull(movementDate, "movementDate");
			return movementDate;
		}

		public void addAllocationResults(final IHUContext huContext, final List<IAllocationResult> results)
		{
			results.stream()
					.flatMap(loadResult -> loadResult.getTransactions().stream())
					.forEach(huTransaction -> addHUTransaction(huContext, huTransaction));
		}

		private void addHUTransaction(final IHUContext huContext, final IHUTransactionCandidate huTransaction)
		{
			//
			// Get the BOM line on which it was issued
			final I_PP_Order_BOMLine ppOrderBOMLine = getOrderBOMLineToIssueOrNull(huTransaction);
			if (ppOrderBOMLine == null)
			{
				// does not apply
				return;
			}

			//
			// Get/Create Issue Candidate
			final int ppOrderBOMLineId = ppOrderBOMLine.getPP_Order_BOMLine_ID();
			final IssueCandidate issueCandidate = candidatesByOrderBOMLineId.computeIfAbsent(ppOrderBOMLineId, k -> new IssueCandidate(ppOrderBOMLine));

			//
			// Add Qty To Issue
			final I_M_Product product = huTransaction.getProduct();
			final Quantity qtyToIssue = huTransaction.getQuantity();

			// Get HU from counterpart transaction
			// (because in this transaction we have the Order BOM line)
			final IHUTransactionCandidate huTransactionCounterpart = huTransaction.getCounterpart();
			final I_M_HU hu = huTransactionCounterpart.getM_HU();
			Check.assumeNotNull(hu, "HU not found in counterpart transaction. \n trx: {} \n counterpart: {}", huTransaction, huTransactionCounterpart);

			//
			// Get the Top Level HU of this transaction.
			// That's the HU that we will need to assign to generated cost collector.
			// NOTE: even if those HUs were already destroyed, we have to assign them, for tracking
			final I_M_HU huTopLevel = handlingUnitsBL.getTopLevelParent(hu);

			//
			// Add Qty To Issue
			issueCandidate.addQtyToIssue(product, qtyToIssue, huTopLevel);

			//
			// Collect the material tracking if any
			issueCandidate.addMaterialTracking(huPPOrderMaterialTrackingBL.extractMaterialTrackingIfAny(huContext, hu));
		}

		private I_PP_Order_BOMLine getOrderBOMLineToIssueOrNull(final IHUTransactionCandidate huTransaction)
		{
			final Object referencedModel = huTransaction.getReferencedModel();
			if (referencedModel == null)
			{
				return null;
			}

			if (!InterfaceWrapperHelper.isInstanceOf(referencedModel, I_PP_Order_BOMLine.class))
			{
				return null;
			}

			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(referencedModel, I_PP_Order_BOMLine.class);

			//
			// Make sure it's a issue line (shall not happen)
			if (!PPOrderUtil.isIssue(ppOrderBOMLine.getComponentType()))
			{
				throw new HUException("BOM line does not allow issuing materials."
						+ "\n @PP_Order_BOMLine_ID@: " + ppOrderBOMLine);
			}

			return ppOrderBOMLine;
		}

		/**
		 * Creates single cost collector
		 *
		 * @return cost collector; never returns null
		 */
		public I_PP_Cost_Collector createSingleCostCollector(final String snapshotId)
		{
			final List<IssueCandidate> candidates = getCandidatesToProcess();
			if (candidates.isEmpty())
			{
				throw new HUException("Got no candidates when only one was expected");
			}
			else if (candidates.size() > 1)
			{
				throw new HUException("Got more then one candidates when only one was expected: " + candidates);
			}

			if (Check.isEmpty(snapshotId, true))
			{
				throw new HUException("No snapshotId provided");
			}

			// Actually process the candidate and generate the CC
			final IssueCandidate candidate = candidates.get(0);
			final I_PP_Cost_Collector cc = createCostCollector(candidate, snapshotId);
			return cc;
		}

		private List<IssueCandidate> getCandidatesToProcess()
		{
			return candidatesByOrderBOMLineId.values()
					.stream()
					.filter(candidate -> {
						if (candidate.isZeroQty())
						{
							logger.warn("Skipping ZERO quantity candidate: {}", candidate);
							return false;
						}
						return true;
					}) // only those eligible
					.collect(ImmutableList.toImmutableList());
		}

		/**
		 *
		 * @param candidate
		 * @return created issue cost collector; never returns ZERO
		 */
		private I_PP_Cost_Collector createCostCollector(final IssueCandidate candidate, final String snapshotId)
		{
			if (candidate.isZeroQty())
			{
				throw new HUException("Cannot create issue cost collector for zero quantity candidate: " + candidate);
			}

			final BigDecimal qtyToIssue = candidate.getQtyToIssue();
			final I_C_UOM qtyToIssueUOM = candidate.getUom();
			final Date movementDate = getMovementDate();
			final I_PP_Order_BOMLine ppOrderBOMLine = candidate.getOrderBOMLine();
			final int locatorId = ppOrderBOMLine.getM_Locator_ID();

			//
			// Link this manufacturing order to material tracking, if any
			I_M_Material_Tracking materialTracking = candidate.getMaterialTracking();
			if (materialTracking != null)
			{
				huPPOrderMaterialTrackingBL.linkPPOrderToMaterialTracking(ppOrderBOMLine, materialTracking);
			}

			//
			// Create the cost collector & process it.
			final I_PP_Cost_Collector cc = InterfaceWrapperHelper.create(ppCostCollectorBL.createIssue(
					PlainContextAware.newWithThreadInheritedTrx(), // context
					ppOrderBOMLine,
					locatorId, // locator
					0, // attributeSetInstanceId: N/A
					movementDate,
					qtyToIssue,
					BigDecimal.ZERO, // qtyScrap,
					BigDecimal.ZERO, // qtyReject
					qtyToIssueUOM // UOM
			), I_PP_Cost_Collector.class);

			//
			// Assign the HUs to cost collector.
			// NOTE: even if those HUs were already destroyed, we have to assign them, for tracking
			huPPCostCollectorBL.assignHUs(cc, candidate.getHusToAssign());

			//
			// Set the Snapshot_UUID for later recall, in case of an reversal.
			cc.setSnapshot_UUID(snapshotId);
			InterfaceWrapperHelper.save(cc);

			// Add issued attributes to manufacturing order (task 08177)
			ppOrderProductAttributeBL.addPPOrderProductAttributes(cc);

			//
			// Return it
			return cc;
		}
	}

	@Data
	@ToString(exclude = "uomConversionBL")
	private static final class IssueCandidate
	{
		// Services
		private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		//
		private final I_PP_Order_BOMLine orderBOMLine;
		private final I_C_UOM uom;

		//
		@Setter(AccessLevel.NONE)
		private BigDecimal qtyToIssue = BigDecimal.ZERO;
		private final Set<I_M_HU> husToAssign = new TreeSet<>(HUByIdComparator.instance);
		@Setter(AccessLevel.NONE)
		private I_M_Material_Tracking materialTracking;

		public IssueCandidate(final I_PP_Order_BOMLine ppOrderBOMLine)
		{
			super();

			Check.assumeNotNull(ppOrderBOMLine, "ppOrderBOMLine not null");
			this.orderBOMLine = ppOrderBOMLine;

			uom = ppOrderBOMLine.getC_UOM();
		}

		/**
		 * @param product
		 * @param qtyToIssueToAdd
		 * @param huToAssign HU to be assigned to generated cost collector
		 */
		public void addQtyToIssue(@NonNull final I_M_Product product, @NonNull final Quantity qtyToIssueToAdd, @NonNull final I_M_HU huToAssign)
		{
			// Validate
			if (product.getM_Product_ID() != orderBOMLine.getM_Product_ID())
			{
				throw new HUException("Invalid product to issue."
						+ "\nExpected: " + orderBOMLine.getM_Product()
						+ "\nGot: " + product
						+ "\n@PP_Order_BOMLine_ID@: " + orderBOMLine);
			}

			final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(product);
			final Quantity qtyToIssueToAddConv = uomConversionBL.convertQuantityTo(qtyToIssueToAdd, uomConversionCtx, uom);

			qtyToIssue = qtyToIssue.add(qtyToIssueToAddConv.getQty());
			husToAssign.add(huToAssign);
		}

		public void addMaterialTracking(I_M_Material_Tracking materialTracking)
		{
			if (materialTracking == null)
			{
				return;
			}

			if (this.materialTracking != null && this.materialTracking.getM_Material_Tracking_ID() != materialTracking.getM_Material_Tracking_ID())
			{
				throw new HUException("An material issue cannot have more then one material tracking"
						+ "\nPrevious: " + this.materialTracking
						+ "\nRequested to add: " + materialTracking);
			}

			this.materialTracking = materialTracking;
		}

		public boolean isZeroQty()
		{
			final BigDecimal qtyToIssue = getQtyToIssue();
			return qtyToIssue.signum() == 0;
		}
	}
}
