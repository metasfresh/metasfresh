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
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderPayScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public I_C_OrderPaySchedule save(@NonNull final OrderPaySchedule schedule)
	{
		final I_C_OrderPaySchedule record = toRecord(schedule);
		save(record);
		return record;
	}

	public void save(@NonNull final I_C_OrderPaySchedule record)
	{
		InterfaceWrapperHelper.save(record);
	}

	@NonNull
	public OrderPaySchedule getById(@NonNull final OrderPayScheduleId id)
	{
		final I_C_OrderPaySchedule record = InterfaceWrapperHelper.load(id, I_C_OrderPaySchedule.class);
		return fromRecord(record);
	}

	@NonNull
	public List<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL
				.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_OrderPaySchedule.COLUMNNAME_SeqNo)
				.create()
				.stream()
				.map(OrderPayScheduleRepository::fromRecord)
				.collect(Collectors.toList());
	}

	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		queryBL
				.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.delete();
	}

	private I_C_OrderPaySchedule toRecord(@NonNull final OrderPaySchedule schedule)
	{
		final I_C_OrderPaySchedule record;
		if (schedule.getId() != null)
		{
			record = InterfaceWrapperHelper.load(schedule.getId(), I_C_OrderPaySchedule.class);
		}
		else
		{
			record = InterfaceWrapperHelper.newInstance(I_C_OrderPaySchedule.class);
		}

		record.setC_Order_ID(schedule.getOrderId().getRepoId());
		record.setC_PaymentTerm_Break_ID(schedule.getPaymentTermBreakId().getRepoId());
		record.setDueAmt(schedule.getDueAmount().toBigDecimal());
		record.setC_Currency_ID(schedule.getDueAmount().getCurrencyId().getRepoId());
		record.setDueDate(TimeUtil.asTimestamp(schedule.getDueDate()));
		record.setPercent(schedule.getPercent().toInt());
		record.setReferenceDateType(schedule.getReferenceDateType().getCode());
		record.setSeqNo(schedule.getSeqNo().toInt());
		record.setStatus(OrderPayScheduleStatus.toCodeOrNull(schedule.getOrderPayScheduleStatus()));

		return record;
	}

	@NonNull
	static private OrderPaySchedule fromRecord(@NonNull final I_C_OrderPaySchedule record)
	{
		return OrderPaySchedule
				.builder()
				.id(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.paymentTermBreakId(PaymentTermBreakId.ofRepoId(record.getC_PaymentTerm_ID(), record.getC_PaymentTerm_Break_ID()))
				.dueAmount(Money.of(record.getDueAmt(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.dueDate(TimeUtil.asInstant(record.getDueDate()))
				.percent(Percent.of(record.getPercent()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType()))
				.orderPayScheduleStatus(OrderPayScheduleStatus.ofNullableCode(record.getStatus()))
				.build();
	}

	public SeqNo getNextSeqNo(@NonNull final OrderId orderId)
	{
		final int lastSeqNoInt = queryLinesByOrderId(orderId)
				.create()
				.maxInt(I_C_OrderPaySchedule.COLUMNNAME_SeqNo);

		final SeqNo lastSeqNo = SeqNo.ofInt(Math.max(lastSeqNoInt, 0));
		return lastSeqNo.next();
	}

	private IQueryBuilder<I_C_OrderPaySchedule> queryLinesByOrderId(final @NonNull OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addInArrayFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId);
	}

}
