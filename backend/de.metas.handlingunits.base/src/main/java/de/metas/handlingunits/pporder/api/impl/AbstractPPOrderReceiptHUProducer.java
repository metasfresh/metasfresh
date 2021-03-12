/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.pporder.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest.CreateReceiptCandidateRequestBuilder;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.util.Env;
import org.eevolution.api.PPCostCollectorId;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/* package */abstract class AbstractPPOrderReceiptHUProducer implements IPPOrderReceiptHUProducer
{
	// Services
	private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	private final PPOrderId ppOrderId;
	private transient I_M_HU_LUTU_Configuration _lutuConfiguration;
	private ZonedDateTime _movementDate;
	private LocatorId locatorId;
	private PickingCandidateId pickingCandidateId;
	@Nullable
	private String lotNumber;
	@Nullable
	private LocalDate bestBeforeDate;
	//
	private boolean processReceiptCandidates;
	private boolean receiveOneVHU;
	private final LinkedHashSet<PPCostCollectorId> createdCostCollectorIds = new LinkedHashSet<>();

	//
	// Abstract methods
	// @formatter:off
	protected abstract ProductId getProductId();
	protected abstract Object getAllocationRequestReferencedModel();
	protected abstract IAllocationSource createAllocationSource();
	protected abstract IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager();
	protected abstract void addAssignedHUs(final Collection<I_M_HU> hus);
	// @formatter:on

	public AbstractPPOrderReceiptHUProducer(@NonNull final PPOrderId ppOrderId)
	{
		this.ppOrderId = ppOrderId;
	}

	private PPOrderId getPpOrderId()
	{
		return ppOrderId;
	}

	@Override
	public final void createDraftReceiptCandidatesAndPlanningHUs()
	{
		trxManager.callInNewTrx(() -> {
			final I_M_HU_LUTU_Configuration lutuConfig = getCreateLUTUConfiguration();
			final Quantity qtyCUsTotal = lutuConfigurationFactory.calculateQtyCUsTotal(lutuConfig);
			if (qtyCUsTotal.isZero())
			{
				throw new AdempiereException("Zero quantity to receive");
			}
			else if (qtyCUsTotal.isInfinite())
			{
				throw new AdempiereException("Quantity to receive was not determined");
			}

			return createReceiptCandidatesAndPlanningHUs_InTrx(qtyCUsTotal);
		});
	}

	@Override
	public I_M_HU receiveVHU(@NonNull final Quantity qtyToReceive)
	{
		this.processReceiptCandidates = true;
		this.receiveOneVHU = true;

		final List<I_M_HU> vhus = trxManager.callInThreadInheritedTrx(() -> createReceiptCandidatesAndPlanningHUs_InTrx(qtyToReceive));

		if (vhus.isEmpty())
		{
			throw new AdempiereException("No VHU was created for " + qtyToReceive);
		}
		else if (vhus.size() == 1)
		{
			return vhus.get(0);
		}
		else
		{
			throw new AdempiereException("More than one VHU was created for " + qtyToReceive + ": " + vhus);
		}
	}

	private List<I_M_HU> createReceiptCandidatesAndPlanningHUs_InTrx(@NonNull final Quantity qtyToReceive)
	{
		//
		// Create HU Context
		trxManager.assertThreadInheritedTrxExists();
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		//
		final ReceiptCandidateRequestProducer ppOrderReceiptCandidateCollector = newReceiptCandidateRequestProducer();
		huContext.getTrxListeners().callAfterLoad(ppOrderReceiptCandidateCollector::collectAllocationResults);

		//
		// Create Allocation Source
		final IAllocationSource ppOrderAllocationSource = createAllocationSource();

		//
		// Create Allocation Destination
		final IHUProducerAllocationDestination huProducerDestination = createAllocationDestination()
				// Make sure we are generating the HUs in Planning (task 08077)
				.setHUStatus(X_M_HU.HUSTATUS_Planning);

		//
		// Create Allocation Request
		final IAllocationRequest allocationRequest = createAllocationRequest(huContext, qtyToReceive);

		//
		// Execute transfer
		final HULoader loader = HULoader.of(ppOrderAllocationSource, huProducerDestination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false);
		final IAllocationResult allocationResult = loader.load(allocationRequest);
		Check.assume(allocationResult.isCompleted(), "Result shall be completed: {}", allocationResult);

		//
		// Generate the HUs
		final List<I_M_HU> planningHUs = huProducerDestination.getCreatedHUs();

		//
		// Update received HUs
		InterfaceWrapperHelper.setThreadInheritedTrxName(planningHUs); // just to be sure
		updateReceivedHUs(huContext, planningHUs, ppOrderReceiptCandidateCollector.coByProductOrderBOMLineId);

		//
		// Create receipt candidates
		createAndProcessReceiptCandidatesIfRequested(ppOrderReceiptCandidateCollector.getRequests());

		//
		// Return created HUs
		return planningHUs;
	}

	private void createAndProcessReceiptCandidatesIfRequested(final ImmutableList<CreateReceiptCandidateRequest> requests)
	{
		if (requests.isEmpty())
		{
			return;
		}

		final List<I_PP_Order_Qty> receiptCandidates = huPPOrderQtyDAO.saveAll(requests);

		if (processReceiptCandidates && !receiptCandidates.isEmpty())
		{
			// Process the receipt candidates we just created
			// => HU will be activated, a receipt cost collector will be generated,
			final List<I_PP_Cost_Collector> costCollectors = HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcess(receiptCandidates)
					.process();

			costCollectors.stream()
					.map(costCollector -> PPCostCollectorId.ofRepoId(costCollector.getPP_Cost_Collector_ID()))
					.forEach(createdCostCollectorIds::add);
		}
	}

	protected abstract ReceiptCandidateRequestProducer newReceiptCandidateRequestProducer();

	private void updateReceivedHUs(
			final IHUContext huContext,
			final Collection<I_M_HU> hus,
			@Nullable final PPOrderBOMLineId coByProductOrderBOMLineId)
	{
		if (hus.isEmpty())
		{
			return;
		}

		//
		// Modify the HU Attributes based on the attributes already existing from issuing (task 08177)
		ppOrderProductAttributeBL.updateHUAttributes(hus, getPpOrderId(), coByProductOrderBOMLineId);

		final IAttributeStorageFactory huAttributeStorageFactory = huContext.getHUAttributeStorageFactory();

		for (final I_M_HU hu : hus)
		{
			final IAttributeStorage huAttributes = huAttributeStorageFactory.getAttributeStorage(hu);

			if (lotNumber != null
					&& huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber))
			{
				huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumber);
			}

			if (bestBeforeDate != null
					&& huAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
			}

			huAttributes.saveChangesIfNeeded();

			huAttributesBL.updateHUAttributeRecursive(
					HuId.ofRepoId(hu.getM_HU_ID()),
					HUAttributeConstants.ATTR_PP_Order_ID,
					ppOrderId.getRepoId(),
					null);
		}

		//
		// Assign HUs to PP_Order/PP_Order_BOMLine
		addAssignedHUs(hus);
	}

	@Override
	public final IPPOrderReceiptHUProducer movementDate(@NonNull final ZonedDateTime movementDate)
	{
		_movementDate = movementDate;
		return this;
	}

	protected final ZonedDateTime getMovementDate()
	{
		if (_movementDate == null)
		{
			_movementDate = SystemTime.asZonedDateTime();
		}
		return _movementDate;
	}

	@Override
	public final IPPOrderReceiptHUProducer locatorId(@NonNull final LocatorId locatorId)
	{
		this.locatorId = locatorId;
		return this;
	}

	protected final LocatorId getLocatorId()
	{
		return locatorId;
	}

	private IAllocationRequest createAllocationRequest(final IHUContext huContext, final Quantity qtyToReceive)
	{
		final ProductId productId = getProductId();
		final ZonedDateTime date = getMovementDate();
		final Object referencedModel = getAllocationRequestReferencedModel();

		return AllocationUtils.createQtyRequest(huContext,
				productId, // product
				qtyToReceive, // the quantity to receive
				date, // transaction date
				referencedModel, // referenced model
				true // forceQtyAllocation: make sure we will transfer the given qty, no matter what
		);
	}

	private IHUProducerAllocationDestination createAllocationDestination()
	{
		if (receiveOneVHU)
		{
			return HUProducerDestination.ofVirtualPI()
					.setLocatorId(getLocatorId());
		}
		else
		{
			final I_M_HU_LUTU_Configuration lutuConfiguration = getCreateLUTUConfiguration();
			return lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		}
	}

	@Override
	public final IPPOrderReceiptHUProducer packUsingLUTUConfiguration(@NonNull final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		_lutuConfiguration = lutuConfiguration;
		return this;
	}

	private I_M_HU_LUTU_Configuration getCreateLUTUConfiguration()
	{
		if (_lutuConfiguration == null)
		{
			_lutuConfiguration = createReceiptLUTUConfigurationManager()
					.startEditing() // start editing just to make sure the LU/TU configuration is created if does not already exist
					.pushBackToModel()
					.getLUTUConfiguration();
		}
		return _lutuConfiguration;
	}

	@Override
	public IPPOrderReceiptHUProducer pickingCandidateId(final PickingCandidateId pickingCandidateId)
	{
		this.pickingCandidateId = pickingCandidateId;
		return this;
	}

	protected final PickingCandidateId getPickingCandidateId()
	{
		return pickingCandidateId;
	}

	@Override
	public IPPOrderReceiptHUProducer lotNumber(final String lotNumber)
	{
		this.lotNumber = StringUtils.trimBlankToNull(lotNumber);
		return this;
	}

	@Override
	public IPPOrderReceiptHUProducer bestBeforeDate(@Nullable final LocalDate bestBeforeDate)
	{
		this.bestBeforeDate = bestBeforeDate;
		return this;
	}

	@Override
	public ImmutableSet<PPCostCollectorId> getCreatedCostCollectorIds()
	{
		return ImmutableSet.copyOf(createdCostCollectorIds);
	}

	//
	//
	//
	//
	//

	/**
	 * Aggregates {@link HUTransactionCandidate}s and creates manufacturing receipt candidates requests.
	 */
	protected static final class ReceiptCandidateRequestProducer
	{
		private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		private final PPOrderId orderId;
		private final PPOrderBOMLineId coByProductOrderBOMLineId;
		private final OrgId orgId;
		private final ZonedDateTime date;
		private final LocatorId locatorId;
		private final PickingCandidateId pickingCandidateId;

		private final Map<AggregationKey, CreateReceiptCandidateRequestBuilder> requests = new HashMap<>();

		@Builder
		private ReceiptCandidateRequestProducer(
				@NonNull final PPOrderId orderId,
				@Nullable final PPOrderBOMLineId coByProductOrderBOMLineId,
				@NonNull final OrgId orgId,
				@NonNull final ZonedDateTime date,
				@Nullable final LocatorId locatorId,
				@Nullable final PickingCandidateId pickingCandidateId)
		{
			this.orderId = orderId;
			this.coByProductOrderBOMLineId = coByProductOrderBOMLineId;
			this.orgId = orgId;
			this.date = date;
			this.locatorId = locatorId;
			this.pickingCandidateId = pickingCandidateId;
		}

		public ImmutableList<CreateReceiptCandidateRequest> getRequests()
		{
			return requests.values()
					.stream()
					.map(CreateReceiptCandidateRequestBuilder::build)
					.collect(ImmutableList.toImmutableList());
		}

		public void collectAllocationResults(final IHUContext IGNORED, @NonNull final List<IAllocationResult> loadResults)
		{
			loadResults.stream()
					.flatMap(loadResult -> loadResult.getTransactions().stream())
					.forEach(this::collectHUTransactionCandidate);
		}

		private void collectHUTransactionCandidate(final IHUTransactionCandidate huTransaction)
		{
			final I_M_HU hu = huTransaction.getM_HU();
			if (hu == null)
			{
				return;
			}

			final Quantity quantity = huTransaction.getQuantity();
			if (quantity.isZero())
			{
				return;
			}

			final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(hu);

			final LocatorId effectiveLocatorId = CoalesceUtil.coalesceSuppliers(
					() -> locatorId,
					() -> huTransaction.getLocatorId(),
					() -> IHandlingUnitsBL.extractLocatorIdOrNull(topLevelHU));
			if (effectiveLocatorId == null)
			{
				throw new AdempiereException("Cannot figure out on which locator to receive.")
						.setParameter("providedLocatorId", locatorId)
						.setParameter("huTransaction", huTransaction)
						.setParameter("topLevelHU", topLevelHU)
						.appendParametersToMessage();
			}

			final AggregationKey key = AggregationKey.builder()
					.locatorId(effectiveLocatorId)
					.topLevelHUId(HuId.ofRepoId(topLevelHU.getM_HU_ID()))
					.productId(huTransaction.getProductId())
					.build();

			requests.computeIfAbsent(key, this::createReceiptCandidateRequestBuilder)
					.addQtyToReceive(quantity);
		}

		private CreateReceiptCandidateRequestBuilder createReceiptCandidateRequestBuilder(final AggregationKey key)
		{
			return CreateReceiptCandidateRequest.builder()
					.orderId(orderId)
					.orderBOMLineId(coByProductOrderBOMLineId)
					.orgId(orgId)
					.date(date)
					.locatorId(key.getLocatorId())
					.topLevelHUId(key.getTopLevelHUId())
					.productId(key.getProductId())
					.pickingCandidateId(pickingCandidateId);
		}

		@Builder
		@Value
		private static class AggregationKey
		{
			@NonNull
			LocatorId locatorId;
			@NonNull
			HuId topLevelHUId;
			@NonNull
			ProductId productId;
		}
	}

}
