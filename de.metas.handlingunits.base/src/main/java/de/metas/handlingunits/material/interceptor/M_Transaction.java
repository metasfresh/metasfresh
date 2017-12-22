package de.metas.handlingunits.material.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.InterceptorUtil;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.materialtransaction.MTransactionUtil;
import lombok.NonNull;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
@Interceptor(I_M_Transaction.class)
public class M_Transaction
{
	public static final M_Transaction INSTANCE = new M_Transaction();

	private M_Transaction()
	{
	}

	/**
	 * Note: it's important to enqueue the transaction after it was saved and before it is deleted, because we need its ID.
	 *
	 * @param purchaseCandidate
	 * @task https://github.com/metasfresh/metasfresh/issues/710
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE
			// ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_TransAction_ID */
	})
	public void fireTransactionEvent(
			@NonNull final I_M_Transaction transaction,
			@NonNull final ModelChangeType type)
	{
		if (transaction.getPP_Cost_Collector_ID() > 0 || transaction.getM_MovementLine_ID() > 0)
		{
			return; // they are handled in dedicated interceptors
		}

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);

		final Collection<AbstractTransactionEvent> events = createTransactionEvents(transaction, type);
		for (final AbstractTransactionEvent event : events)
		{
			materialEventService.fireEventAfterNextCommit(event, getTrxName(transaction));
		}
	}

	@VisibleForTesting
	static List<AbstractTransactionEvent> createTransactionEvents(
			@NonNull final I_M_Transaction transaction,
			@NonNull final ModelChangeType type)
	{

		final Map<Integer, BigDecimal> shipmentScheduleId2qty = retrieveShipmentScheduleId(transaction);

		final boolean deleted = type.isDelete() || InterceptorUtil.isJustDeactivated(transaction);

		final Builder<AbstractTransactionEvent> result = ImmutableList.builder();
		for (final Entry<Integer, BigDecimal> entry : shipmentScheduleId2qty.entrySet())
		{
			final AbstractTransactionEvent event = createEventForShipmentScheduleToQtyMapping(transaction, entry, deleted);
			result.add(event);
		}
		return result.build();
	}

	private static AbstractTransactionEvent createEventForShipmentScheduleToQtyMapping(
			@NonNull final I_M_Transaction transaction,
			@NonNull final Entry<Integer, BigDecimal> entry,
			final boolean deleted
			)
	{
		final BigDecimal quantity = entry.getValue();
		final EventDescriptor eventDescriptor = EventDescriptor.createNew(transaction);
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(transaction, quantity);

		final boolean directMovementWarehouse = isDirectMovementWarehouse(extractTransactionWarehouseId(transaction));

		final AbstractTransactionEvent event;
		if (deleted)
		{
			event = TransactionDeletedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.shipmentScheduleId(entry.getKey())
					.directMovementWarehouse(directMovementWarehouse)
					.build();
		}
		else
		{
			event = TransactionCreatedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescriptor(materialDescriptor)
					.shipmentScheduleId(entry.getKey())
					.directMovementWarehouse(directMovementWarehouse)
					.build();
		}
		return event;
	}

	private static boolean isDirectMovementWarehouse(final int warehouseId)
	{
		final int intValue = Services.get(ISysConfigBL.class).getIntValue(IHUMovementBL.SYSCONFIG_DirectMove_Warehouse_ID, -1);
		return intValue == warehouseId;
	}

	@VisibleForTesting
	static Map<Integer, BigDecimal> retrieveShipmentScheduleId(@NonNull final I_M_Transaction transaction)
	{
		final Map<Integer, BigDecimal> shipmentScheduleId2quantity = new TreeMap<>();

		BigDecimal qtyLeftToDistribute = transaction.getMovementQty();

		if (transaction.getM_InOutLine_ID() <= 0)
		{
			shipmentScheduleId2quantity.put(0, qtyLeftToDistribute);
			return shipmentScheduleId2quantity;
		}

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtysPicked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, transaction.getM_InOutLine_ID())
				.create()
				.list();
		for (final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked : shipmentScheduleQtysPicked)
		{
			assertSignumsOfQuantitiesMatch(shipmentScheduleQtyPicked, transaction);

			final BigDecimal qtyPicked = shipmentScheduleQtyPicked.getQtyPicked();
			final BigDecimal quantityForMaterialDescriptor = MTransactionUtil.isInboundTransaction(transaction) ? qtyPicked : qtyPicked.negate();

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
}
