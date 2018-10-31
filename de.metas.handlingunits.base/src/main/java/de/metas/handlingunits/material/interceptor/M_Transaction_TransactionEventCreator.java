package de.metas.handlingunits.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_PP_Cost_Collector;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.materialtransaction.MTransactionUtil;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class M_Transaction_TransactionEventCreator
{
	public static final M_Transaction_TransactionEventCreator INSTANCE = new M_Transaction_TransactionEventCreator();

	private M_Transaction_TransactionEventCreator()
	{
	}

	public final List<MaterialEvent> createEventsForTransaction(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final Builder<MaterialEvent> result = ImmutableList.builder();

		if (transaction.getInoutLineId() > 0)
		{
			final List<MaterialEvent> //
			eventsForInOutLine = M_Transaction_InOutLineEventCreator.createEventsForInOutLine(transaction, deleted);
			result.addAll(eventsForInOutLine);
		}
		else if (transaction.getCostCollectorId() > 0)
		{
			result.addAll(createEventForCostCollector(transaction, deleted));
		}
		else if (transaction.getMovementLineId() > 0)
		{
			result.addAll(createEventForMovementLine(transaction, deleted));
		}
		else if (transaction.getInventoryLineId() > 0)
		{
			result.addAll(createEventForInventoryLine(transaction, deleted));
		}
		return result.build();
	}

	private List<MaterialEvent> createEventForCostCollector(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final I_PP_Cost_Collector costCollector = load(transaction.getCostCollectorId(), I_PP_Cost_Collector.class);

		final List<HUDescriptor> huDescriptors = //
				M_Transaction_HuDescriptor.INSTANCE.createHuDescriptorsForCostCollector(costCollector, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
		materialDescriptors = M_Transaction_HuDescriptor.INSTANCE.createMaterialDescriptors(
				transaction,
				0, // don't provide the ppOrder's bPartnerId unless we clarified that it's the customer for which the produced goods are reserved
				huDescriptors);

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
				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.ppOrderId(costCollector.getPP_Order_ID())
						.ppOrderLineId(costCollector.getPP_Order_BOMLine_ID())
						.build();
			}
			events.add(event);
		}
		return events.build();
	}

	private static void assertSignumsOfQuantitiesMatch(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final TransactionDescriptor transaction)
	{
		final BigDecimal qtyPicked = shipmentScheduleQtyPicked.getQtyPicked();
		final BigDecimal movementQty = transaction.getMovementQty();

		if (qtyPicked.signum() == 0 || movementQty.signum() == 0)
		{
			return; // at least one of them is zero
		}
		if (qtyPicked.signum() != movementQty.signum())
		{
			return;
		}

		throw new AdempiereException(
				"For the given shipmentScheduleQtyPicked and transaction, one needs to be positive and one needs to be negative")
						.appendParametersToMessage()
						.setParameter("qtyPicked", qtyPicked)
						.setParameter("movementQty", movementQty)
						.setParameter("shipmentScheduleQtyPicked", shipmentScheduleQtyPicked)
						.setParameter("transaction", transaction);
	}

	@VisibleForTesting
	static Map<Integer, BigDecimal> retrieveShipmentScheduleId2Qty(
			@NonNull final TransactionDescriptor transaction)
	{
		final Map<Integer, BigDecimal> shipmentScheduleId2quantity = new TreeMap<>();

		BigDecimal qtyLeftToDistribute = transaction.getMovementQty();

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtysPicked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID, transaction.getInoutLineId())
				.create()
				.list();

		for (final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked : shipmentScheduleQtysPicked)
		{
			assertSignumsOfQuantitiesMatch(shipmentScheduleQtyPicked, transaction);

			final BigDecimal qtyPicked = shipmentScheduleQtyPicked.getQtyPicked();
			final BigDecimal quantityForMaterialDescriptor = MTransactionUtil.isInboundMovementType(transaction.getMovementType())
					? qtyPicked
					: qtyPicked.negate();

			shipmentScheduleId2quantity.merge(
					shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID(),
					quantityForMaterialDescriptor,
					BigDecimal::add);

			qtyLeftToDistribute = qtyLeftToDistribute.subtract(quantityForMaterialDescriptor);
		}
		return shipmentScheduleId2quantity;
	}

	private List<MaterialEvent> createEventForMovementLine(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final boolean directMovementWarehouse = isDirectMovementWarehouse(transaction.getWarehouseId());

		final I_M_MovementLine movementLine = load(transaction.getMovementLineId(), I_M_MovementLine.class);

		final List<HUDescriptor> huDescriptors = //
				M_Transaction_HuDescriptor.INSTANCE.createHuDescriptorsForMovementLine(movementLine, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
		materialDescriptors = M_Transaction_HuDescriptor.INSTANCE.createMaterialDescriptors(
				transaction,
				0, // the movement's bpartner (if set at all) is not the customer, but probably a shipper
				huDescriptors);

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
				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.ddOrderId(ddOrderId)
						.ddOrderLineId(movementLine.getDD_OrderLine_ID())
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

		final I_M_InventoryLine inventoryLine = load(transaction.getInventoryLineId(), I_M_InventoryLine.class);

		final List<HUDescriptor> huDescriptors = //
				M_Transaction_HuDescriptor.INSTANCE.createHuDescriptorsForInventoryLine(inventoryLine, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
		materialDescriptors = M_Transaction_HuDescriptor.INSTANCE.createMaterialDescriptors(
				transaction,
				0, // customerId
				huDescriptors);

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
						.build();
			}
			else
			{
				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.huOnHandQtyChangeDescriptors(materialDescriptor.getValue())
						.directMovementWarehouse(directMovementWarehouse)
						.build();
			}
			events.add(event);
		}
		return events.build();
	}

	private static boolean isDirectMovementWarehouse(final int warehouseId)
	{
		final int intValue = Services.get(ISysConfigBL.class).getIntValue(IHUMovementBL.SYSCONFIG_DirectMove_Warehouse_ID, -1);
		return intValue == warehouseId;
	}
}
