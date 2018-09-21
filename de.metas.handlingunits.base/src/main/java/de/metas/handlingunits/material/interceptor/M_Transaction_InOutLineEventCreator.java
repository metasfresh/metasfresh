package de.metas.handlingunits.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_M_Transaction;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
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

public class M_Transaction_InOutLineEventCreator
{
	public static final M_Transaction_InOutLineEventCreator INSTANCE = new M_Transaction_InOutLineEventCreator();

	private M_Transaction_InOutLineEventCreator()
	{
	}

	public static List<MaterialEvent> createEventsForInOutLine(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final boolean shipment = X_M_Transaction.MOVEMENTTYPE_CustomerReturns.equals(transaction.getMovementType())
				|| X_M_Transaction.MOVEMENTTYPE_CustomerShipment.equals(transaction.getMovementType());

		if (shipment)
		{
			return createEventsForShipment(transaction, deleted);
		}
		return createEventsForReceipt(transaction, deleted);
	}

	private static List<MaterialEvent> createEventsForShipment(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys = retrieveShipmentScheduleId2Qty(transaction);

		final boolean directMovementWarehouse = isDirectMovementWarehouse(transaction.getWarehouseId());

		final I_M_InOutLine inOutLine = load(transaction.getInoutLineId(), I_M_InOutLine.class);

		final List<HUDescriptor> huDescriptors = //
				M_Transaction_HuDescriptor.INSTANCE.createHuDescriptorsForInOutLine(inOutLine, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
		materialDescriptors = M_Transaction_HuDescriptor.INSTANCE.createMaterialDescriptors(
				transaction,
				inOutLine.getM_InOut().getC_BPartner_ID(), // customerId
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
						.shipmentScheduleIds2Qtys(shipmentScheduleIds2Qtys)
						.inOutId(inOutLine.getM_InOut_ID())
						.inOutLineId(inOutLine.getM_InOutLine_ID())
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
						.shipmentScheduleIds2Qtys(shipmentScheduleIds2Qtys)
						.inOutId(inOutLine.getM_InOut_ID())
						.inOutLineId(inOutLine.getM_InOutLine_ID())
						.directMovementWarehouse(directMovementWarehouse)
						.build();
			}
			events.add(event);
		}
		return events.build();
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

	private static List<MaterialEvent> createEventsForReceipt(
			@NonNull final TransactionDescriptor transaction,
			final boolean deleted)
	{
		final boolean directMovementWarehouse = isDirectMovementWarehouse(transaction.getWarehouseId());
		final I_M_InOutLine inOutLine = load(transaction.getInoutLineId(), I_M_InOutLine.class);

		final Map<Integer, BigDecimal> receiptScheduleIds2Qtys = Services
				.get(IReceiptScheduleDAO.class)
				.retrieveRsaForInOutLine(inOutLine)
				.stream()
				.collect(Collectors.groupingBy(
						I_M_ReceiptSchedule_Alloc::getM_ReceiptSchedule_ID,
						Collectors.reducing(
								BigDecimal.ZERO,
								I_M_ReceiptSchedule_Alloc::getQtyAllocated,
								BigDecimal::add)));

		final List<HUDescriptor> huDescriptors = //
				M_Transaction_HuDescriptor.INSTANCE.createHuDescriptorsForInOutLine(inOutLine, deleted);

		final Map<MaterialDescriptor, Collection<HUDescriptor>> //
		materialDescriptors = M_Transaction_HuDescriptor.INSTANCE.createMaterialDescriptors(
				transaction,
				0, // receipts's bpartner is not a customer
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
						.receiptScheduleIdsQtys(receiptScheduleIds2Qtys)
						.inOutId(inOutLine.getM_InOut_ID())
						.inOutLineId(inOutLine.getM_InOutLine_ID())
						.directMovementWarehouse(directMovementWarehouse)
						.build();
			}
			else
			{
				event = TransactionCreatedEvent.builder()
						.eventDescriptor(transaction.getEventDescriptor())
						.transactionId(transaction.getTransactionId())
						.materialDescriptor(materialDescriptor.getKey())
						.receiptScheduleIdsQtys(receiptScheduleIds2Qtys)
						.inOutId(inOutLine.getM_InOut_ID())
						.inOutLineId(inOutLine.getM_InOutLine_ID())
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
