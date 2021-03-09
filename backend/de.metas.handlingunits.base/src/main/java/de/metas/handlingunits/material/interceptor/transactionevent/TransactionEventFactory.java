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

package de.metas.handlingunits.material.interceptor.transactionevent;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.materialtransaction.IMTransactionDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.X_M_Transaction;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component // not calling it service, because right now it's intended to be used only from the M_Transaction model interceptor
public final class TransactionEventFactory
{
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IMovementDAO movementsRepo = Services.get(IMovementDAO.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

	private final TransactionEventFactoryForInOutLine inOutLineEventCreator;
	private final HUDescriptorsFromHUAssignmentService huDescriptorsViaHUAssignmentsService;
	private final HUDescriptorFromInventoryLineService huDescriptorsViaInventoryLineService;
	private final ReplenishInfoRepository replenishInfoRepository;

	public TransactionEventFactory(
			@NonNull final HUDescriptorsFromHUAssignmentService huDescriptorsViaHUAssignmentsService,
			@NonNull final ReplenishInfoRepository replenishInfoRepository,
			@NonNull final InventoryRepository inventoryRepository,
			@NonNull final HUDescriptorService huDescriptorService)
	{
		this.huDescriptorsViaHUAssignmentsService = huDescriptorsViaHUAssignmentsService;
		this.huDescriptorsViaInventoryLineService = new HUDescriptorFromInventoryLineService(inventoryRepository, huDescriptorService);
		this.replenishInfoRepository = replenishInfoRepository;
		this.inOutLineEventCreator = new TransactionEventFactoryForInOutLine(huDescriptorsViaHUAssignmentsService, replenishInfoRepository);
	}

	public List<MaterialEvent> createEventsForTransaction(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final ImmutableList.Builder<MaterialEvent> result = ImmutableList.builder();

		if (transaction.getInoutLineId() != null)
		{
			result.addAll(inOutLineEventCreator.createEventsForInOutLine(transaction, deleted));
		}
		else if (transaction.getCostCollectorId() != null)
		{
			result.addAll(createEventForCostCollector(transaction, deleted));
		}
		else if (transaction.getMovementLineId() != null)
		{
			result.addAll(createEventForMovementLine(transaction, deleted));
		}
		else if (transaction.getInventoryLineId() != null)
		{
			result.addAll(createEventForInventoryLine(transaction, deleted));
		}
		return result.build();
	}

	private List<MaterialEvent> createEventForCostCollector(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final I_PP_Cost_Collector costCollector = ppCostCollectorBL.getById(transaction.getCostCollectorId());

		final List<HUDescriptor> huDescriptors = huDescriptorsViaHUAssignmentsService.createHuDescriptorsForCostCollector(costCollector, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
				materialDescriptors = huDescriptorsViaHUAssignmentsService.newMaterialDescriptors()
				.transaction(transaction)
				.huDescriptors(huDescriptors)
				// don't provide the ppOrder's bPartnerId, unless we clarified that it's the customer for which the produced goods are reserved
				.customerId(null)
				.vendorId(null)
				.build();

		final boolean directMovementWarehouse = isDirectMovementWarehouse(transaction.getWarehouseId());

		final ImmutableList.Builder<MaterialEvent> events = ImmutableList.builder();
		for (final Entry<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptor : materialDescriptors.entrySet())
		{
			final AbstractTransactionEvent event;
			if (deleted)
			{
				event = TransactionDeletedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.ppOrderId(costCollector.getPP_Order_ID())
						.ppOrderLineId(costCollector.getPP_Order_BOMLine_ID())
						.build();
			}
			else
			{
				final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository.getBy(materialDescriptor.getKey()).toMinMaxDescriptor();

				final MaterialDescriptor materialDescriptorEff;
				final Collection<HUDescriptor> huDescriptorsEff;
				if (
						X_M_Transaction.MOVEMENTTYPE_WorkOrderMinus.equals(transaction.getMovementType())
								|| X_M_Transaction.MOVEMENTTYPE_ProductionMinus.equals(transaction.getMovementType())
				)
				{ // the HUs were destroyed, so we can't rely on their Qty
					materialDescriptorEff = materialDescriptor.getKey().withQuantity(transaction.getMovementQty());
					huDescriptorsEff = ImmutableList.of();
				}
				else
				{
					materialDescriptorEff = materialDescriptor.getKey();
					huDescriptorsEff = materialDescriptor.getValue();
				}

				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptorEff)
						.huOnHandQtyChangeDescriptors(huDescriptorsEff)
						.directMovementWarehouse(directMovementWarehouse)
						.ppOrderId(costCollector.getPP_Order_ID())
						.ppOrderLineId(costCollector.getPP_Order_BOMLine_ID())
						.minMaxDescriptor(minMaxDescriptor)
						.build();
			}
			events.add(event);
		}
		return events.build();
	}

	private List<MaterialEvent> createEventForMovementLine(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final boolean directMovementWarehouse = isDirectMovementWarehouse(transaction.getWarehouseId());

		final I_M_MovementLine movementLine = movementsRepo.getLineById(transaction.getMovementLineId());

		final List<HUDescriptor> huDescriptors = huDescriptorsViaHUAssignmentsService.createHuDescriptorsForMovementLine(movementLine, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
				materialDescriptors = huDescriptorsViaHUAssignmentsService.newMaterialDescriptors()
				.transaction(transaction)
				.huDescriptors(huDescriptors)
				// the movement's bpartner (if set at all) is not the customer nor a vendor, but probably a shipper
				.customerId(null)
				.vendorId(null)
				.build();

		final int ddOrderId = movementLine.getDD_OrderLine_ID() > 0
				? movementLine.getDD_OrderLine().getDD_Order_ID()
				: 0;

		final ImmutableList.Builder<MaterialEvent> events = ImmutableList.builder();
		for (final Entry<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptor : materialDescriptors.entrySet())
		{
			final AbstractTransactionEvent event;
			if (deleted)
			{
				event = TransactionDeletedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.ddOrderId(ddOrderId)
						.ddOrderLineId(movementLine.getDD_OrderLine_ID())
						.build();
			}
			else
			{
				final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository.getBy(materialDescriptor.getKey()).toMinMaxDescriptor();

				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.ddOrderId(ddOrderId)
						.ddOrderLineId(movementLine.getDD_OrderLine_ID())
						.minMaxDescriptor(minMaxDescriptor)
						.build();
			}
			events.add(event);
		}
		return events.build();
	}

	private List<MaterialEvent> createEventForInventoryLine(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final boolean directMovementWarehouse = isDirectMovementWarehouse(transaction.getWarehouseId());

		final I_M_InventoryLine inventoryLineRecord = inventoryDAO.getLineById(transaction.getInventoryLineId());

		final List<HUDescriptor> huDescriptors = huDescriptorsViaInventoryLineService.createHuDescriptorsForInventoryLine(inventoryLineRecord, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
				materialDescriptors = huDescriptorsViaHUAssignmentsService.newMaterialDescriptors()
				.transaction(transaction)
				.huDescriptors(huDescriptors)
				.build();

		final ImmutableList.Builder<MaterialEvent> events = ImmutableList.builder();
		for (final Entry<MaterialDescriptor, Collection<HUDescriptor>> materialDescriptor : materialDescriptors.entrySet())
		{
			final AbstractTransactionEvent event;
			if (deleted)
			{
				event = TransactionDeletedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.inventoryId(inventoryLineRecord.getM_Inventory_ID())
						.inventoryLineId(transaction.getInventoryLineId().getRepoId())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.build();
			}
			else
			{
				final MinMaxDescriptor minMaxDescriptor = replenishInfoRepository.getBy(materialDescriptor.getKey()).toMinMaxDescriptor();

				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.inventoryId(inventoryLineRecord.getM_Inventory_ID())
						.inventoryLineId(transaction.getInventoryLineId().getRepoId())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.minMaxDescriptor(minMaxDescriptor)
						.build();
			}
			events.add(event);
		}
		return events.build();
	}

	private boolean isDirectMovementWarehouse(final WarehouseId warehouseId)
	{
		if (warehouseId == null)
		{
			return false;
		}

		final int intValue = sysConfigBL.getIntValue(IHUMovementBL.SYSCONFIG_DirectMove_Warehouse_ID, -1);
		return intValue == warehouseId.getRepoId();
	}
}
