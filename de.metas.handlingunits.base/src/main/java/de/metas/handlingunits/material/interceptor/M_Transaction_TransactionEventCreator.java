package de.metas.handlingunits.material.interceptor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Transaction;
import org.eevolution.model.I_PP_Cost_Collector;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUOnHandQtyChangeDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.materialtransaction.MTransactionUtil;
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

public class M_Transaction_TransactionEventCreator extends M_Transaction_EventCreator
{
	public static final M_Transaction_TransactionEventCreator INSTANCE = new M_Transaction_TransactionEventCreator();

	private M_Transaction_TransactionEventCreator()
	{
	}

	private static boolean isDirectMovementWarehouse(final int warehouseId)
	{
		final int intValue = Services.get(ISysConfigBL.class).getIntValue(IHUMovementBL.SYSCONFIG_DirectMove_Warehouse_ID, -1);
		return intValue == warehouseId;
	}

	@Override
	public List<MaterialEvent> createEventsForCostCollector(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		final EventDescriptor eventDescriptor = EventDescriptor.createNew(transaction);
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(
				transaction,
				transaction.getMovementQty());

		final boolean directMovementWarehouse = isDirectMovementWarehouse(extractTransactionWarehouseId(transaction));

		final AbstractTransactionEvent event;
		final I_PP_Cost_Collector costCollector = transaction.getPP_Cost_Collector();

		final List<HUOnHandQtyChangeDescriptor> huDescriptors = //
				M_Transaction_HuOnHandQtyChangeDescriptor.INSTANCE.createEventsForCostCollector(transaction, deleted);

		if (deleted)
		{
			event = TransactionDeletedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.directMovementWarehouse(directMovementWarehouse)
					.ppOrderId(costCollector.getPP_Order_ID())
					.ppOrderLineId(costCollector.getPP_Order_BOMLine_ID())
					.huOnHandQtyChangeDescriptors(huDescriptors)
					.build();
		}
		else
		{
			event = TransactionCreatedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.directMovementWarehouse(directMovementWarehouse)
					.ppOrderId(costCollector.getPP_Order_ID())
					.ppOrderLineId(costCollector.getPP_Order_BOMLine_ID())
					.huOnHandQtyChangeDescriptors(huDescriptors)
					.build();
		}
		return ImmutableList.of(event);
	}

	private static MaterialDescriptor createMaterialDescriptor(
			@NonNull final I_M_Transaction transaction,
			@NonNull final BigDecimal quantity)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(transaction);

		return MaterialDescriptor.builder()
				.warehouseId(extractTransactionWarehouseId(transaction))
				.date(transaction.getMovementDate())
				.productDescriptor(productDescriptor)
				.bPartnerId(transaction.getC_BPartner_ID())
				.quantity(quantity)
				.build();
	}

	private static int extractTransactionWarehouseId(@NonNull final I_M_Transaction transaction)
	{
		return transaction.getM_Locator().getM_Warehouse_ID();
	}

	@Override
	public List<MaterialEvent> createEventsForInOutLine(
			@NonNull final I_M_Transaction transaction,
			final boolean deleted)
	{
		final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys = retrieveShipmentScheduleId2Qty(transaction);

		final AbstractTransactionEvent event = createEventForShipmentScheduleToQtyMapping(transaction, shipmentScheduleIds2Qtys, deleted);
		return ImmutableList.of(event);
	}

	@VisibleForTesting
	static Map<Integer, BigDecimal> retrieveShipmentScheduleId2Qty(
			@NonNull final I_M_Transaction transaction)
	{
		final Map<Integer, BigDecimal> shipmentScheduleId2quantity = new TreeMap<>();

		BigDecimal qtyLeftToDistribute = transaction.getMovementQty();

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtysPicked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID, transaction.getM_InOutLine_ID())
				.create()
				.list();

		for (final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked : shipmentScheduleQtysPicked)
		{
			assertSignumsOfQuantitiesMatch(shipmentScheduleQtyPicked, transaction);

			final BigDecimal qtyPicked = shipmentScheduleQtyPicked.getQtyPicked();
			final BigDecimal quantityForMaterialDescriptor = MTransactionUtil.isInboundTransaction(transaction)
					? qtyPicked
					: qtyPicked.negate();

			shipmentScheduleId2quantity.merge(
					shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID(),
					quantityForMaterialDescriptor,
					BigDecimal::add);

			qtyLeftToDistribute = qtyLeftToDistribute.subtract(quantityForMaterialDescriptor);
		}

		if (qtyLeftToDistribute.signum() != 0)
		{
			shipmentScheduleId2quantity.put(0, qtyLeftToDistribute);
		}
		return shipmentScheduleId2quantity;
	}

	private static void assertSignumsOfQuantitiesMatch(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final I_M_Transaction transaction)
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

	private static AbstractTransactionEvent createEventForShipmentScheduleToQtyMapping(
			@NonNull final I_M_Transaction transaction,
			@NonNull final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys,
			final boolean deleted)
	{
		final boolean directMovementWarehouse = isDirectMovementWarehouse(extractTransactionWarehouseId(transaction));

		final EventDescriptor eventDescriptor = EventDescriptor.createNew(transaction);
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(
				transaction,
				transaction.getMovementQty());

		final List<HUOnHandQtyChangeDescriptor> huDescriptor = //
				M_Transaction_HuOnHandQtyChangeDescriptor.INSTANCE.createEventsForInOutLine(transaction, deleted);

		final AbstractTransactionEvent event;
		if (deleted)
		{
			event = TransactionDeletedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.shipmentScheduleIds2Qtys(shipmentScheduleIds2Qtys)
					.directMovementWarehouse(directMovementWarehouse)
					.huOnHandQtyChangeDescriptors(huDescriptor)
					.build();
		}
		else
		{
			event = TransactionCreatedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.shipmentScheduleIds2Qtys(shipmentScheduleIds2Qtys)
					.directMovementWarehouse(directMovementWarehouse)
					.huOnHandQtyChangeDescriptors(huDescriptor)
					.build();
		}
		return event;
	}

	@Override
	public List<MaterialEvent> createEventsForMovementLine(I_M_Transaction transaction, boolean deleted)
	{
		final boolean directMovementWarehouse = isDirectMovementWarehouse(extractTransactionWarehouseId(transaction));

		final EventDescriptor eventDescriptor = EventDescriptor.createNew(transaction);
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(
				transaction,
				transaction.getMovementQty());

		final AbstractTransactionEvent event;
		final I_M_MovementLine movementLine = transaction.getM_MovementLine();

		final int ddOrderId = movementLine.getDD_OrderLine_ID() > 0
				? movementLine.getDD_OrderLine().getDD_Order_ID()
				: 0;

		final List<HUOnHandQtyChangeDescriptor> huDescriptors = //
				M_Transaction_HuOnHandQtyChangeDescriptor.INSTANCE.createEventsForMovementLine(transaction, deleted);

		if (deleted)
		{
			event = TransactionDeletedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.directMovementWarehouse(directMovementWarehouse)
					.ddOrderId(ddOrderId)
					.ddOrderLineId(movementLine.getDD_OrderLine_ID())
					.huOnHandQtyChangeDescriptors(huDescriptors)
					.build();
		}
		else
		{
			event = TransactionCreatedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.directMovementWarehouse(directMovementWarehouse)
					.ddOrderId(ddOrderId)
					.ddOrderLineId(movementLine.getDD_OrderLine_ID())
					.huOnHandQtyChangeDescriptors(huDescriptors)
					.build();
		}

		return ImmutableList.of(event);
	}
}
