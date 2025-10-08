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

import de.metas.order.OrderId;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPayScheduleService
{
	private final OrderPayScheduleRepository orderPayScheduleRepo;

	public OrderPayScheduleService(@NonNull final OrderPayScheduleRepository orderPayScheduleRepo)
	{
		this.orderPayScheduleRepo = orderPayScheduleRepo;
	}

	public void createSchedule(@NonNull final OrderPayScheduleRequest orderPayScheduleRequest)
	{
		final OrderPaySchedule schedule = OrderPaySchedule.builder()
				.id(null) // New record, no ID yet
				.orderId(orderPayScheduleRequest.getOrderId())
				.paymentTermBreakId(orderPayScheduleRequest.getPaymentTermBreakId())
				.referenceDateType(orderPayScheduleRequest.getReferenceDateType())
				.dueAmount(orderPayScheduleRequest.getDueAmount())
				.dueDate(TimeUtil.asInstant(orderPayScheduleRequest.getDueDate()))
				.percent(orderPayScheduleRequest.getPercent())
				.seqNo(orderPayScheduleRequest.getSeqNo())
				.orderPayScheduleStatus(orderPayScheduleRequest.getOrderPayScheduleStatus())
				.build();
		orderPayScheduleRepo.save(schedule);
	}

	@NonNull
	public List<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId)
	{
		return orderPayScheduleRepo.getByOrderId(orderId);
	}

	public void deleteByOrderId(@NonNull final OrderId orderId)
	{
		orderPayScheduleRepo.deleteByOrderId(orderId);
	}

	public SeqNo getNextSeqNo(@NonNull final OrderId orderId)
	{
		return orderPayScheduleRepo.getNextSeqNo(orderId);
	}
}
