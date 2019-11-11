package de.metas.handlingunits.pporder.api.impl;

import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

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
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.impl.HUTransactionCandidate;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.pporder.api.CreateReceiptCandidateRequest;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/* package */abstract class AbstractPPOrderReceiptHUProducer implements IPPOrderReceiptHUProducer
{
	// Services
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IPPOrderProductAttributeBL ppOrderProductAttributeBL = Services.get(IPPOrderProductAttributeBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	// Parameters
	private final PPOrderId ppOrderId;
	private transient I_M_HU_LUTU_Configuration _lutuConfiguration;
	private ZonedDateTime _movementDate;
	@Deprecated
	private boolean skipCreatingReceiptCandidates;
	private boolean processReceiptCandidates;
	private boolean receiveOneVHU;
	private PickingCandidateId pickingCandidateId;

	//
	// Abstract methods
	// @formatter:off
	protected abstract ProductId getProductId();
	protected abstract Object getAllocationRequestReferencedModel();
	protected abstract IAllocationSource createAllocationSource();
	protected abstract IDocumentLUTUConfigurationManager createReceiptLUTUConfigurationManager();
	protected abstract void setAssignedHUs(final Collection<I_M_HU> hus);
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
	public List<I_M_HU> createPlanningHUs(@NonNull final Quantity qtyToReceive)
	{
		skipCreatingReceiptCandidates = true;
		return trxManager.callInNewTrx(() -> createReceiptCandidatesAndPlanningHUs_InTrx(qtyToReceive));
	}

	@Override
	public I_M_HU receiveVHU(@NonNull final Quantity qtyToReceive)
	{
		this.processReceiptCandidates = true;
		this.receiveOneVHU = true;

		final List<I_M_HU> vhus = trxManager.callInNewTrx(() -> createReceiptCandidatesAndPlanningHUs_InTrx(qtyToReceive));

		if (vhus.isEmpty())
		{
			throw new AdempiereException("No VHU was created for " + qtyToReceive);
		}
		else if (vhus.size() == 1)
		{
			throw new AdempiereException("More than one VHU was created for " + qtyToReceive + ": " + vhus);
		}
		else
		{
			return vhus.get(0);
		}
	}

	private final List<I_M_HU> createReceiptCandidatesAndPlanningHUs_InTrx(@NonNull final Quantity qtyToReceive)
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
		updateReceivedHUs(planningHUs);

		//
		// Create receipt candidates
		createAndProcessReceiptCandidatesIfRequested(ppOrderReceiptCandidateCollector.getRequests());

		//
		// Return created HUs
		return planningHUs;
	}

	private void createAndProcessReceiptCandidatesIfRequested(final ImmutableList<CreateReceiptCandidateRequest> requests)
	{
		if (skipCreatingReceiptCandidates)
		{
			return;
		}

		if (requests.isEmpty())
		{
			return;
		}

		final List<I_PP_Order_Qty> receiptCandidates = huPPOrderQtyDAO.saveAll(requests);

		if (processReceiptCandidates && !receiptCandidates.isEmpty())
		{
			// Process the receipt candidates we just created
			// => HU will be activated, a receipt cost collector will be generated,
			HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
					.setCandidatesToProcess(receiptCandidates)
					.process();
		}
	}

	protected abstract ReceiptCandidateRequestProducer newReceiptCandidateRequestProducer();

	@Override
	public final void createReceiptCandidatesFromPlanningHU(@NonNull final I_M_HU planningHU)
	{
		processReceiptCandidates = true;

		if (!X_M_HU.HUSTATUS_Planning.equals(planningHU.getHUStatus()))
		{
			throw new HUException("HU " + planningHU + " shall have status Planning but it has " + planningHU.getHUStatus());
		}

		huTrxBL.process(huContext -> {
			InterfaceWrapperHelper.setThreadInheritedTrxName(planningHU); // just to be sure

			//
			// Delete previously created candidates
			// Assume there are no processed one, and even if it would be it would fail on DAO level
			huPPOrderQtyDAO.streamOrderQtys(getPpOrderId())
					.filter(candidate -> candidate.getM_HU_ID() == planningHU.getM_HU_ID())
					.forEach(huPPOrderQtyDAO::delete);

			// Extract it if not top level
			huTrxBL.setParentHU(huContext,
					null,
					planningHU,
					true // destroyOldParentIfEmptyStorage
			);

			final HuId topLevelHUId = HuId.ofRepoId(planningHU.getM_HU_ID());
			final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(planningHU.getM_Locator_ID());

			// Stream all product storages
			// ... and create planning receipt candidates
			{
				final ImmutableList<CreateReceiptCandidateRequest> requests = huContext.getHUStorageFactory()
						.getStorage(planningHU)
						.getProductStorages()
						.stream()
						// FIXME: validate if the storage product is accepted
						.map(productStorage -> toCreateReceiptCandidateRequest(productStorage, topLevelHUId, locatorId))
						.collect(ImmutableList.toImmutableList());

				createAndProcessReceiptCandidatesIfRequested(requests);
			}

			//
			updateReceivedHUs(ImmutableSet.of(planningHU));
		});
	}

	private static CreateReceiptCandidateRequest toCreateReceiptCandidateRequest(final IHUProductStorage productStorage, final HuId topLevelHUId, final LocatorId locatorId)
	{
		return CreateReceiptCandidateRequest.builder()
				.locatorId(locatorId)
				.topLevelHUId(topLevelHUId)
				.productId(productStorage.getProductId())
				.build()
				.addQtyToReceive(productStorage.getQty());
	}

	private void updateReceivedHUs(final Collection<I_M_HU> hus)
	{
		//
		// Modify the HU Attributes based on the attributes already existing from issuing (task 08177)
		ppOrderProductAttributeBL.updateHUAttributes(hus, getPpOrderId());

		//
		// Assign HUs to PP_Order/PP_Order_BOMLine
		setAssignedHUs(hus);
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

	private final IHUProducerAllocationDestination createAllocationDestination()
	{
		if (receiveOneVHU)
		{
			return HUProducerDestination.ofVirtualPI();
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

	private final I_M_HU_LUTU_Configuration getCreateLUTUConfiguration()
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
		private final PPOrderBOMLineId orderBOMLineId;
		private final OrgId orgId;
		private final ZonedDateTime date;
		private final PickingCandidateId pickingCandidateId;

		private final Map<AggregationKey, CreateReceiptCandidateRequest> requests = new HashMap<>();

		@Builder
		private ReceiptCandidateRequestProducer(
				@NonNull final PPOrderId orderId,
				@NonNull final PPOrderBOMLineId orderBOMLineId,
				@NonNull final OrgId orgId,
				@NonNull final ZonedDateTime date,
				@Nullable final PickingCandidateId pickingCandidateId)
		{
			this.orderId = orderId;
			this.orderBOMLineId = orderBOMLineId;
			this.orgId = orgId;
			this.date = date;
			this.pickingCandidateId = pickingCandidateId;
		}

		public ImmutableList<CreateReceiptCandidateRequest> getRequests()
		{
			return ImmutableList.copyOf(requests.values());
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
			final AggregationKey key = AggregationKey.builder()
					.locatorId(huTransaction.getLocatorId())
					.topLevelHUId(HuId.ofRepoId(topLevelHU.getM_HU_ID()))
					.productId(huTransaction.getProductId())
					.build();

			final CreateReceiptCandidateRequest request = requests.computeIfAbsent(key, this::createReceiptCandidateRequest);
			request.addQtyToReceive(quantity);
		}

		private CreateReceiptCandidateRequest createReceiptCandidateRequest(final AggregationKey key)
		{
			return CreateReceiptCandidateRequest.builder()
					.orderId(orderId)
					.orderBOMLineId(orderBOMLineId)
					.orgId(orgId)
					.date(date)
					.locatorId(key.getLocatorId())
					.topLevelHUId(key.getTopLevelHUId())
					.productId(key.getProductId())
					.pickingCandidateId(pickingCandidateId)
					.build();
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
