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
import java.util.function.Consumer;

@Builder
public class OrderPayScheduleLoaderAndSaver
{

	@NonNull private final IQueryBL queryBL;

	private final HashMap<OrderPayScheduleId, I_C_OrderPaySchedule> schedRecordsById = new HashMap<>();

	private final HashMap<OrderId, ImmutableList<I_C_OrderPaySchedule>> orderCache = new HashMap<>();


	OrderPayScheduleLine loadFromRecord(@NonNull final I_C_OrderPaySchedule orderPayScheduleRecord)
	{
		addToCacheAndWarmUp(ImmutableList.of(orderPayScheduleRecord));
		return fromRecord(orderPayScheduleRecord);
	}

	private void addToCacheAndWarmUp(@NonNull Collection<I_C_OrderPaySchedule> orderPayScheduleRecords)
	{
		if (orderPayScheduleRecords.isEmpty())
		{
			return;
		}

		orderPayScheduleRecords.forEach(this::addToCache);

	}

	private void addToCache(@NonNull final I_C_OrderPaySchedule orderPayScheduleRecord)
	{
		schedRecordsById.put(extractOrderPayScheduleId(orderPayScheduleRecord), orderPayScheduleRecord);
	}

	static OrderPayScheduleId extractOrderPayScheduleId(@NonNull final I_C_OrderPaySchedule orderPayScheduleRecord)
	{
		return OrderPayScheduleId.ofRepoId(orderPayScheduleRecord.getC_OrderPaySchedule_ID());
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
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.orderPayScheduleStatus(OrderPayScheduleStatus.ofCode(record.getStatus()))
				.build();
	}

	void save(@NonNull final OrderPayScheduleLine orderPaySchedLine)
	{
		final I_C_OrderPaySchedule orderPayScheduleRecord = getOrderPaysSchedRecordById(orderPaySchedLine.getId());
		updateRecord(orderPayScheduleRecord, orderPaySchedLine);
		InterfaceWrapperHelper.save(orderPayScheduleRecord);
		addToCache(orderPayScheduleRecord);
		orderCache.remove(orderPaySchedLine.getOrderId());
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
		record.setReferenceDateType(from.getReferenceDateType().getCode());
		record.setSeqNo(from.getSeqNo().toInt());
		record.setStatus(OrderPayScheduleStatus.toCodeOrNull(from.getOrderPayScheduleStatus()));
	}

	OrderPayScheduleLine updateById(@NonNull final OrderPayScheduleId id, @NonNull final Consumer<OrderPayScheduleLine> updater)
	{
		final I_C_OrderPaySchedule orderPaySchedRecord = getOrderPaysSchedRecordById(id);
		final OrderPayScheduleLine orderPaySchedLine = fromRecord(orderPaySchedRecord);
		updater.accept(orderPaySchedLine);
		save(orderPaySchedLine);
		return orderPaySchedLine;
	}

	private I_C_OrderPaySchedule getOrderPaysSchedRecordById(@NonNull final OrderPayScheduleId schedId)
	{
		I_C_OrderPaySchedule orderPaySchedRecord = schedRecordsById.get(schedId);
		if (orderPaySchedRecord == null)
		{
			orderPaySchedRecord = retrieveOrderPaySchedRecordById(schedId);
			addToCache(orderPaySchedRecord);
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

	OrderPayScheduleLine getById(final @NonNull OrderPayScheduleId id)
	{
		return fromRecord(getOrderPaysSchedRecordById(id));
	}

	public ImmutableList<I_C_OrderPaySchedule> getRecordsByOrderId(@NonNull final OrderId orderId)
	{
		if (orderCache.containsKey(orderId))
		{
			return orderCache.get(orderId);
		}

		final ImmutableList<I_C_OrderPaySchedule> records = queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_OrderPaySchedule.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());

		for (final I_C_OrderPaySchedule record : records)
		{
			schedRecordsById.put(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID()), record);
		}

		orderCache.put(orderId, records);
		return records;
	}

}
