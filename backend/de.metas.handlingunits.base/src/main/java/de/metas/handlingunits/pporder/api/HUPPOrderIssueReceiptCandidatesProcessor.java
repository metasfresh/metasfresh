package de.metas.handlingunits.pporder.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUStatusBL;
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
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ReceiptCostCollectorCandidate;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

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
 * <p>
 * By default, {@link #process()} will process each candidate in it's own transaction/savepoint.
 * <p>
 * Processing one {@link I_PP_Order_Qty} means:
 * <ul>
 * <li>generate cost collector
 * <li>link the HU to cost collector
 * <li>in case of material receipt activate the HU, create CC
 * <li>in case of material issue: consume/destroy the HU, create CC
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class HUPPOrderIssueReceiptCandidatesProcessor
{
	public static HUPPOrderIssueReceiptCandidatesProcessor newInstance()
	{
		return new HUPPOrderIssueReceiptCandidatesProcessor();
	}

	// services
	private static final Logger logger = LogManager.getLogger(HUPPOrderIssueReceiptCandidatesProcessor.class);
	private final transient ITrxItemProcessorExecutorService trxItemProcessorService = Services.get(ITrxItemProcessorExecutorService.class);
	//
	private final transient IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final AdMessageKey MSG_ONLY_CLEARED_HUs_CAN_BE_ISSUED = AdMessageKey.of("OnlyClearedHUsCanBeIssued");

	//
	// Parameters
	private static final Supplier<List<I_PP_Order_Qty>> CandidatesToProcessSupplier_NONE = ImmutableList::of;
	private Supplier<List<I_PP_Order_Qty>> candidatesToProcessSupplier = CandidatesToProcessSupplier_NONE;

	private HUPPOrderIssueReceiptCandidatesProcessor()
	{
	}

	public List<I_PP_Cost_Collector> process()
	{
		final List<I_PP_Cost_Collector> result = new ArrayList<>();

		trxItemProcessorService.<I_PP_Order_Qty, Void>createExecutor()
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

	@Nullable
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
		return ppOrderBOMLine == null // finished goods receipt
				|| BOMComponentType.ofCode(ppOrderBOMLine.getComponentType()).isReceipt(); // by/co product receipt
	}

	private void markProcessedAndSave(@NonNull final I_PP_Order_Qty candidate, @NonNull final I_PP_Cost_Collector cc)
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
		Check.assumeNotNull(hu, "Parameter hu is not null");
		if (!X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()))
		{
			throw new HUException("Only planning HUs can be received")
					.setParameter("HU", hu)
					.setParameter("HUStatus", hu.getHUStatus())
					.setParameter("candidate", candidate);
		}

		final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(candidate.getM_Locator_ID());

		//
		// Create material receipt and activate the HU
		final I_C_UOM uom = IHUPPOrderQtyBL.extractUOM(candidate);
		final ReceiptCostCollectorCandidate costCollectorCandidate = ReceiptCostCollectorCandidate.builder()
				.order(candidate.getPP_Order())
				.orderBOMLine(candidate.getPP_Order_BOMLine())
				.movementDate(TimeUtil.asZonedDateTime(candidate.getMovementDate()))
				.qtyToReceive(Quantity.of(candidate.getQty(), uom))
				.productId(ProductId.ofRepoId(candidate.getM_Product_ID()))
				.locatorId(locatorId)
				.pickingCandidateId(candidate.getM_Picking_Candidate_ID())
				.build();
		final I_PP_Cost_Collector cc = huPPCostCollectorBL.createReceipt(costCollectorCandidate, hu);

		markProcessedAndSave(candidate, cc);

		return cc;
	}

	@Nullable
	private I_PP_Cost_Collector processIssueCandidate(final I_PP_Order_Qty candidate)
	{
		// NOTE: we assume the candidate was not processed yet
		final I_M_HU hu = handlingUnitsBL.getById(HuId.ofRepoId(candidate.getM_HU_ID()));

		validateIssueCandidate(hu, PPOrderQtyId.ofRepoId(candidate.getPP_Order_Qty_ID()));

		//
		final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(candidate.getPP_Order_BOMLine(), I_PP_Order_BOMLine.class);
		final ZonedDateTime movementDate = TimeUtil.asZonedDateTime(candidate.getMovementDate());
		final int pickingCandidateId = candidate.getM_Picking_Candidate_ID();

		//
		// Calculate the quantity to issue.
		final I_C_UOM qtyToIssueUOM = IHUPPOrderQtyBL.extractUOM(candidate);
		final Quantity qtyToIssue = Quantity.of(candidate.getQty(), qtyToIssueUOM);

		//
		// Update candidate's qty to issue
		// NOTE: in case of "IssueOnlyReceived" issue method the qty to issue is calculated just in time. We assume it's saved by caller
		candidate.setQty(qtyToIssue.toBigDecimal());
		//
		if (qtyToIssue.isZero())
		{
			logger.debug("Skipping candidate ZERO quantity candidate: {}, bomLine={}", candidate, ppOrderBOMLine);
			return null;
		}

		//
		// Fully unload the HU
		final IssueCandidatesBuilder issueCostCollectorsBuilder = new IssueCandidatesBuilder()
				.movementDate(movementDate)
				.pickingCandidateId(pickingCandidateId);
		final String snapshotId;
		{
			//
			// Allocation Source: our HUs
			final HUListAllocationSourceDestination husSource = HUListAllocationSourceDestination.of(hu)
					.setCreateHUSnapshots(true) // Ask to create snapshots of HUs because in case we want to revert the Cost Collector, to be able to recover the HUs (08731).
					.setDestroyEmptyHUs(true); // get rid of those HUs which got empty

			//
			// Allocation Destination: our BOM Lines
			final ProductId productId = ProductId.ofRepoId(candidate.getM_Product_ID());
			final IAllocationDestination orderBOMLinesDestination;
			{
				final PPOrderBOMLineProductStorage productStorage = new PPOrderBOMLineProductStorage(ppOrderBOMLine, productId);
				orderBOMLinesDestination = new GenericAllocationSourceDestination(productStorage, ppOrderBOMLine);
			}

			//
			// Create and setup context
			final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(PlainContextAware.newWithThreadInheritedTrx());
			huContext.getTrxListeners().callAfterLoad(issueCostCollectorsBuilder::addAllocationResults);

			//
			// Allocation request
			final IAllocationRequest allocationRequest = AllocationUtils.createQtyRequest(huContext //
					, productId // product
					, qtyToIssue // the quantity to issue
					, SystemTime.asZonedDateTime() // transaction date
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

	private void setCandidatesToProcess(final Supplier<List<I_PP_Order_Qty>> candidatesToProcessSupplier)
	{
		this.candidatesToProcessSupplier = candidatesToProcessSupplier;
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

	public HUPPOrderIssueReceiptCandidatesProcessor setCandidatesToProcessByPPOrderId(final PPOrderId ppOrderId)
	{
		setCandidatesToProcess(() -> huPPOrderQtyDAO.retrieveOrderQtys(ppOrderId)
				.stream()
				.filter(candidate -> !candidate.isProcessed()) // not already processed
				.collect(ImmutableList.toImmutableList()));
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
	 */
	private static final class IssueCandidatesBuilder
	{
		// Services
		private final transient IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		private final transient IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);

		private final transient IHUPPOrderMaterialTrackingBL huPPOrderMaterialTrackingBL = Services.get(IHUPPOrderMaterialTrackingBL.class);
		private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);

		// Parameters
		private ZonedDateTime movementDate = null;
		private int pickingCandidateId = -1;

		// Status
		private final Map<PPOrderBOMLineId, IssueCandidate> candidatesByOrderBOMLineId = new HashMap<>();

		public IssueCandidatesBuilder movementDate(final ZonedDateTime movementDate)
		{
			this.movementDate = movementDate;
			return this;
		}

		public IssueCandidatesBuilder pickingCandidateId(final int pickingCandidateId)
		{
			this.pickingCandidateId = pickingCandidateId;
			return this;
		}

		private ZonedDateTime getMovementDate()
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
			final ProductId productId = huTransaction.getProductId();
			final PPOrderBOMLineId ppOrderBOMLineId = PPOrderBOMLineId.ofRepoId(ppOrderBOMLine.getPP_Order_BOMLine_ID());
			final IssueCandidate issueCandidate = candidatesByOrderBOMLineId.computeIfAbsent(ppOrderBOMLineId, k -> new IssueCandidate(ppOrderBOMLine, productId));

			//
			// Add Qty To Issue
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
			issueCandidate.addQtyToIssue(productId, qtyToIssue, huTopLevel);

			// Collect the material tracking if any
			final I_M_Material_Tracking materialTrackingOrNull = huPPOrderMaterialTrackingBL.extractMaterialTrackingIfAny(huContext, hu);
			if (materialTrackingOrNull != null)
			{
				issueCandidate.addMaterialTracking(materialTrackingOrNull, qtyToIssue);
			}
		}

		@Nullable
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
			if (!BOMComponentType.ofCode(ppOrderBOMLine.getComponentType()).isIssue())
			{
				throw new HUException("BOM line does not allow issuing materials."
						+ "\n @PP_Order_BOMLine_ID@: " + ppOrderBOMLine);
			}

			return ppOrderBOMLine;
		}

		/**
		 * @return never returns null
		 */
		private I_PP_Cost_Collector createSingleCostCollector(final String snapshotId)
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
			return createCostCollector(candidate, snapshotId);
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
		 * @return created issue cost collector; never returns ZERO
		 */
		private I_PP_Cost_Collector createCostCollector(@NonNull final IssueCandidate candidate, final String snapshotId)
		{
			if (candidate.isZeroQty())
			{
				throw new HUException("Cannot create issue cost collector for zero quantity candidate: " + candidate);
			}

			final Quantity qtyToIssue = candidate.getQtyToIssue();
			final ZonedDateTime movementDate = getMovementDate();
			final I_PP_Order_BOMLine ppOrderBOMLine = candidate.getOrderBOMLine();
			final LocatorId locatorId = LocatorId.ofRepoId(ppOrderBOMLine.getM_Warehouse_ID(), ppOrderBOMLine.getM_Locator_ID());

			//
			// Create the cost collector & process it.
			final I_PP_Cost_Collector cc = InterfaceWrapperHelper.create(
					ppCostCollectorBL.createIssue(ComponentIssueCreateRequest.builder()
							.orderBOMLine(ppOrderBOMLine)
							.productId(candidate.getProductId())
							.locatorId(locatorId)
							.attributeSetInstanceId(AttributeSetInstanceId.NONE) // N/A
							.movementDate(movementDate)
							.qtyIssue(qtyToIssue)
							.pickingCandidateId(pickingCandidateId)
							.build()),
					I_PP_Cost_Collector.class);

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
			// Link this cost collector and its manufacturing order to the material tracking, if any
			final ImmutableSet<MaterialTrackingWithQuantity> materialTrackings = candidate.getMaterialTrackings();
			for (final MaterialTrackingWithQuantity materialTracking : materialTrackings)
			{
				huPPOrderMaterialTrackingBL.linkPPOrderToMaterialTracking(cc, materialTracking);
			}

			// Return it
			return cc;
		}
	}

	private void validateIssueCandidate(@NonNull final I_M_HU hu, @NonNull final PPOrderQtyId ppOrderQtyId)
	{
		// Validate the HU
		if (!huStatusBL.isStatusActiveOrIssued(hu))
		{
			// if operated by the swing-ui, this code has to deal with active HUs because the swingUI skips that part of the workflow
			throw new HUException("Only HUs with status 'issued' and 'active' can be finalized with their PP_Cost_Collector and destroyed")
					.setParameter("HU", hu)
					.setParameter("HUStatus", hu.getHUStatus())
					.setParameter("PPOrderQtyId", ppOrderQtyId);
		}

		if (!handlingUnitsBL.isHUHierarchyCleared(HuId.ofRepoId(hu.getM_HU_ID())))
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_ONLY_CLEARED_HUs_CAN_BE_ISSUED));
		}
	}

	@Data
	@ToString(exclude = "uomConversionBL")
	private static final class IssueCandidate
	{
		// Services
		private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		//
		@NonNull private final I_PP_Order_BOMLine orderBOMLine;
		@NonNull private final ProductId productId;

		//
		@NonNull @Setter(AccessLevel.NONE) private Quantity qtyToIssue;
		private final Set<I_M_HU> husToAssign = new TreeSet<>(HUByIdComparator.instance);

		@Setter(AccessLevel.NONE)
		@Getter(AccessLevel.NONE)
		private final Map<Integer, MaterialTrackingWithQuantity> id2materialTracking = new HashMap<>();

		private IssueCandidate(
				@NonNull final I_PP_Order_BOMLine ppOrderBOMLine,
				@NonNull final ProductId productId)
		{
			this.orderBOMLine = ppOrderBOMLine;
			this.productId = productId;

			final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
			final I_C_UOM uom = orderBOMBL.getBOMLineUOM(ppOrderBOMLine);
			qtyToIssue = Quantity.zero(uom);
		}

		public ImmutableSet<MaterialTrackingWithQuantity> getMaterialTrackings()
		{
			return ImmutableSet.copyOf(id2materialTracking.values());
		}

		/**
		 * @param huToAssign HU to be assigned to generated cost collector
		 */
		public void addQtyToIssue(@NonNull final ProductId productId, @NonNull final Quantity qtyToIssueToAdd, @NonNull final I_M_HU huToAssign)
		{
			// Validate
			if (!ProductId.equals(productId, this.productId))
			{
				throw new HUException("Invalid product to issue."
						+ "\nExpected: " + this.productId
						+ "\nGot: " + productId
						+ "\n@PP_Order_BOMLine_ID@: " + orderBOMLine);
			}

			final UOMConversionContext uomConversionCtx = UOMConversionContext.of(productId);
			final Quantity qtyToIssueToAddConv = uomConversionBL.convertQuantityTo(qtyToIssueToAdd, uomConversionCtx, qtyToIssue.getUOM());

			qtyToIssue = qtyToIssue.add(qtyToIssueToAddConv);
			husToAssign.add(huToAssign);
		}

		private void addMaterialTracking(
				@NonNull final I_M_Material_Tracking materialTracking,
				@NonNull final Quantity quantity)
		{
			MaterialTrackingWithQuantity materialTrackingWithQuantity = this.id2materialTracking.get(materialTracking.getM_Material_Tracking_ID());
			if (materialTrackingWithQuantity == null)
			{
				materialTrackingWithQuantity = new MaterialTrackingWithQuantity(materialTracking);
				this.id2materialTracking.put(materialTracking.getM_Material_Tracking_ID(), materialTrackingWithQuantity);
			}
			materialTrackingWithQuantity.addQuantity(quantity);
		}

		public boolean isZeroQty()
		{
			return getQtyToIssue().isZero();
		}
	}
}
