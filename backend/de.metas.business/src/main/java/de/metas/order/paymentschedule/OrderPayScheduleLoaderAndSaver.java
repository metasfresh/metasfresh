/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Builder
public class OrderPayScheduleLoaderAndSaver
{

	@NonNull private final IQueryBL queryBL;

	private final HashMap<OrderPayScheduleId, I_C_OrderPaySchedule> schedRecordsById = new HashMap<>();

	private final HashMap<OrderId, OrderPaySchedule> schedsByOrderId = new HashMap<>();

	OrderPaySchedule loadByOrderId(@NonNull final OrderId orderId)
	{
		final OrderPaySchedule cachedSchedule = schedsByOrderId.get(orderId);
		if (cachedSchedule != null)
		{
			return cachedSchedule;
		}

		final ImmutableList<I_C_OrderPaySchedule> records = retrieveOrderPaySchedRecordsByOrderId(ImmutableSet.of(orderId)).get(orderId);
		if (records == null || records.isEmpty())
		{
			// Return an empty schedule if none found, ensuring the consumer gets a non-null object
			final OrderPaySchedule emptySchedule = OrderPaySchedule.ofList(orderId, ImmutableList.of());
			schedsByOrderId.put(orderId, emptySchedule);
			return emptySchedule;
		}

		// Cache records individually and build the aggregate schedule
		records.forEach(this::addToLineCache);

		final OrderPaySchedule schedule = createOrderPaySchedule(orderId, records);
		schedsByOrderId.put(orderId, schedule);
		return schedule;
	}

	private Map<OrderId, ImmutableList<I_C_OrderPaySchedule>> retrieveOrderPaySchedRecordsByOrderId(@NonNull final Set<OrderId> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<OrderId, ImmutableList<I_C_OrderPaySchedule>> recordsByOrderId = queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addInArrayFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderIds)
				.orderBy(I_C_OrderPaySchedule.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						OrderPayScheduleLoaderAndSaver::extractOrderId,
						ImmutableList.toImmutableList()
				));

		final HashMap<OrderId, ImmutableList<I_C_OrderPaySchedule>> result = new HashMap<>();
		for (final OrderId orderId : orderIds)
		{
			final ImmutableList<I_C_OrderPaySchedule> records = recordsByOrderId.get(orderId);
			result.put(orderId, records != null ? records : ImmutableList.of());
		}

		return result;
	}

	private OrderPaySchedule createOrderPaySchedule(
			@NonNull final OrderId orderId,
			@NonNull final Collection<I_C_OrderPaySchedule> records)
	{
		final ImmutableList<OrderPayScheduleLine> lines = records.stream()
				.map(OrderPayScheduleLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return OrderPaySchedule.ofList(orderId, lines);
	}

	private void addToLineCache(@NonNull final I_C_OrderPaySchedule orderPayScheduleRecord)
	{
		schedRecordsById.put(extractOrderPayScheduleId(orderPayScheduleRecord), orderPayScheduleRecord);
	}

	private I_C_OrderPaySchedule getOrderPaysSchedRecordById(@NonNull final OrderPayScheduleId schedId)
	{
		I_C_OrderPaySchedule orderPaySchedRecord = schedRecordsById.get(schedId);
		if (orderPaySchedRecord == null)
		{
			orderPaySchedRecord = retrieveOrderPaySchedRecordById(schedId);
			addToLineCache(orderPaySchedRecord);
		}
		return orderPaySchedRecord;
	}

	private I_C_OrderPaySchedule retrieveOrderPaySchedRecordById(@NonNull final OrderPayScheduleId schedId)
	{
		final I_C_OrderPaySchedule orderPaySchedRecord = InterfaceWrapperHelper.load(schedId, I_C_OrderPaySchedule.class);
		if (orderPaySchedRecord == null)
		{
			throw new AdempiereException("No order pay sched found for " + schedId);
		}
		return orderPaySchedRecord;
	}

	static OrderPayScheduleId extractOrderPayScheduleId(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID());
	}

	static OrderId extractOrderId(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderId.ofRepoId(record.getC_Order_ID());
	}

	@NonNull
	static OrderPayScheduleLine fromRecord(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.paymentTermBreakId(PaymentTermBreakId.ofRepoId(record.getC_PaymentTerm_ID(), record.getC_PaymentTerm_Break_ID()))
				.dueAmount(Money.of(record.getDueAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.dueDate(record.getDueDate().toInstant())
				.percent(Percent.of(record.getPercent()))
				// .offsetDays(record.getOffsetDays()) // TODO
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.orderPayScheduleStatus(OrderPayScheduleStatus.ofCode(record.getStatus()))
				.build();
	}

	/* --- Saving and Updating --- */

	void saveLine(@NonNull final OrderPayScheduleLine orderPaySchedLine)
	{
		final I_C_OrderPaySchedule orderPayScheduleRecord = getOrderPaysSchedRecordById(orderPaySchedLine.getId());
		updateRecord(orderPayScheduleRecord, orderPaySchedLine);
		InterfaceWrapperHelper.save(orderPayScheduleRecord);

		// Invalidate the schedule cache for this order since a line changed
		schedsByOrderId.remove(orderPaySchedLine.getOrderId());
		addToLineCache(orderPayScheduleRecord);
	}

	private static void updateRecord(final I_C_OrderPaySchedule record, final @NotNull OrderPayScheduleLine from)
	{
		record.setC_Order_ID(from.getOrderId().getRepoId());
		record.setC_PaymentTerm_ID(from.getPaymentTermBreakId().getPaymentTermId().getRepoId());
		record.setC_PaymentTerm_Break_ID(from.getPaymentTermBreakId().getRepoId());
		record.setDueAmt(from.getDueAmount().toBigDecimal());
		record.setC_Currency_ID(from.getDueAmount().getCurrencyId().getRepoId());
		record.setDueDate(TimeUtil.asTimestamp(from.getDueDate()));
		record.setPercent(from.getPercent().toInt());
		// record.setOffsetDays(from.getOffsetDays()); // Corrected field inclusion
		record.setReferenceDateType(from.getReferenceDateType().getCode());
		record.setSeqNo(from.getSeqNo().toInt());
		record.setStatus(OrderPayScheduleStatus.toCodeOrNull(from.getOrderPayScheduleStatus()));
	}

	OrderPayScheduleLine updateById(@NonNull final OrderPayScheduleId id, @NonNull final Consumer<OrderPayScheduleLine> updater)
	{
		final I_C_OrderPaySchedule orderPaySchedRecord = getOrderPaysSchedRecordById(id);
		final OrderPayScheduleLine orderPaySchedLine = fromRecord(orderPaySchedRecord);

		updater.accept(orderPaySchedLine);

		saveLine(orderPaySchedLine);

		return orderPaySchedLine;
	}

	void save(@NonNull final OrderPaySchedule orderPaySchedule)
	{
		orderPaySchedule.getLines().forEach(this::saveLine);
	}

}