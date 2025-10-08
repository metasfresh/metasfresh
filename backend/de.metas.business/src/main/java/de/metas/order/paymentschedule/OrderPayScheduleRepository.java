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

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class OrderPayScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void create(@NonNull final OrderPayScheduleCreateRequest request)
	{
		final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);

		request.getLines()
				.forEach(line -> create(line, request.getOrderId(), seqNoProvider.getAndIncrement()));
	}

	private void create(@NonNull final OrderPayScheduleCreateRequest.Line request, @NonNull OrderId orderId, @NonNull SeqNo seqNo)
	{
		final I_C_OrderPaySchedule record = newInstance(I_C_OrderPaySchedule.class);
		record.setC_Order_ID(orderId.getRepoId());
		record.setC_PaymentTerm_ID(request.getPaymentTermBreakId().getPaymentTermId().getRepoId());
		record.setC_PaymentTerm_Break_ID(request.getPaymentTermBreakId().getRepoId());
		record.setDueAmt(request.getDueAmount().toBigDecimal());
		record.setC_Currency_ID(request.getDueAmount().getCurrencyId().getRepoId());
		record.setDueDate(TimeUtil.asTimestamp(request.getDueDate()));
		record.setPercent(request.getPercent().toInt());
		record.setReferenceDateType(request.getReferenceDateType().getCode());
		record.setSeqNo(seqNo.toInt());
		record.setStatus(OrderPayScheduleStatus.toCodeOrNull(request.getOrderPayScheduleStatus()));
		saveRecord(record);
	}

	@NonNull
	private I_C_OrderPaySchedule save(@NonNull final OrderPayScheduleLine schedule)
	{
		final I_C_OrderPaySchedule record = InterfaceWrapperHelper.load(schedule.getId(), I_C_OrderPaySchedule.class);
		updateRecord(record, schedule);
		saveRecord(record);
		return record;
	}

	@NonNull
	public Optional<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_OrderPaySchedule.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.map(OrderPayScheduleRepository::fromRecord)
				.collect(OrderPaySchedule.collect());
	}

	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.delete();
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

	@NonNull
	private static OrderPayScheduleLine fromRecord(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.paymentTermBreakId(PaymentTermBreakId.ofRepoId(record.getC_PaymentTerm_ID(), record.getC_PaymentTerm_Break_ID()))
				.dueAmount(Money.of(record.getDueAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.dueDate(TimeUtil.asInstant(record.getDueDate())) // FIXME nullable
				.percent(Percent.of(record.getPercent()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.orderPayScheduleStatus(OrderPayScheduleStatus.ofCode(record.getStatus())) // FIXME nullable
				.build();
	}
}
