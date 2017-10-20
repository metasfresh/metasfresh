package de.metas.material.model.interceptor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.ModelValidator;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.TransactionEvent;
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
	static final M_Transaction INSTANCE = new M_Transaction();

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
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE /* beforeDelete because we still need the M_TransAction_ID */ })
	public void fireTransactionEvent(
			@NonNull final I_M_Transaction transaction,
			@NonNull final ModelChangeType type)
	{
		if (transaction.getPP_Cost_Collector_ID() > 0 || transaction.getM_MovementLine_ID() > 0)
		{
			return;
		}

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		final String trxName = InterfaceWrapperHelper.getTrxName(transaction);

		final Collection<TransactionEvent> events = createTransactionEvents(transaction, type);
		events.forEach(event -> materialEventService.fireEventAfterNextCommit(event, trxName));
	}

	@VisibleForTesting
	static List<TransactionEvent> createTransactionEvents(
			@NonNull final I_M_Transaction transaction,
			@NonNull final ModelChangeType type)
	{
		final Builder<TransactionEvent> result = ImmutableList.builder();

		final Map<Integer, BigDecimal> shipmentScheduleId2qty = retrieveShipmentScheduleId(transaction);

		for (final Entry<Integer, BigDecimal> entries : shipmentScheduleId2qty.entrySet())
		{
			final BigDecimal quantity = type.isDelete() ? BigDecimal.ZERO : entries.getValue();

			final TransactionEvent event = TransactionEvent.builder()
					.eventDescr(EventDescr.createNew(transaction))
					.transactionId(transaction.getM_Transaction_ID())
					.materialDescr(createMateriallDescriptor(transaction, quantity))
					.shipmentScheduleId(entries.getKey())
					.build();
			result.add(event);
		}
		return result.build();
	}

	private static Map<Integer, BigDecimal> retrieveShipmentScheduleId(@NonNull final I_M_Transaction transaction)
	{
		final Map<Integer, BigDecimal> result = new TreeMap<>();

		BigDecimal qtyLeftToDistribute = transaction.getMovementQty();

		if (transaction.getM_InOutLine_ID() <= 0)
		{
			result.put(0, qtyLeftToDistribute);
			return result;
		}

		final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtysPicked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMN_M_InOutLine_ID, transaction.getM_InOutLine_ID())
				.create()
				.list();
		for (final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked : shipmentScheduleQtysPicked)
		{
			result.merge(
					shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID(),
					shipmentScheduleQtyPicked.getQtyPicked(),
					BigDecimal::add);
			qtyLeftToDistribute = qtyLeftToDistribute.subtract(shipmentScheduleQtyPicked.getQtyPicked());
		}

		if (qtyLeftToDistribute.signum() > 0)
		{
			result.put(0, qtyLeftToDistribute);
		}
		return result;
	}

	private static MaterialDescriptor createMateriallDescriptor(
			@NonNull final I_M_Transaction transaction,
			@NonNull final BigDecimal quantity)
	{
		final BigDecimal effectiveQty = MTransactionUtil.isInboundTransaction(transaction)
				? quantity
				: quantity.negate();

		return MaterialDescriptor.builder()
				.warehouseId(transaction.getM_Locator().getM_Warehouse_ID())
				.date(transaction.getMovementDate())
				.productId(transaction.getM_Product_ID())
				.quantity(effectiveQty)
				.build();
	}
}
