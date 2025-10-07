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

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderPaySchedule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderPayScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

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
	public List<OrderPaySchedule> getByOrderId(@NonNull OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderPaySchedule.class).addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId).orderBy(I_C_OrderPaySchedule.COLUMNNAME_Line).create().stream().map(this::fromRecord).collect(Collectors.toList());

	}

	public void deleteByOrderId(@NonNull OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_OrderPaySchedule.class).addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId).create().delete();

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
		record.setDueAmt(schedule.getDueAmount().getAsBigDecimal());
		record.setDueDate(schedule.getDueDate());
		record.setLine(schedule.getSeqNo());
		record.setReferenceDateType(schedule.getReferenceDateType().getCode());

		return record;
	}

	private OrderPaySchedule fromRecord(@NonNull final I_C_OrderPaySchedule record)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(record.getC_Order_ID()));
		final PaymentTermId paymentTermId = orderBL.getPaymentTermId(order);
		final CurrencyCode currencyCode = orderBL.getCurrencyCode(order);

		return OrderPaySchedule.builder().id(OrderPayScheduleId.ofRepoId(record.getC_OrderPaySchedule_ID())).orderId(OrderId.ofRepoId(record.getC_Order_ID())).paymentTermBreakId(PaymentTermBreakId.ofRepoId(paymentTermId, record.getC_PaymentTerm_Break_ID())).dueAmount(Amount.of(record.getDueAmt(), currencyCode)) // Get currency from order
				.dueDate(record.getDueDate()).seqNo(record.getLine()).referenceDateType(ReferenceDateType.ofCode(record.getReferenceDateType())).build();
	}

}
