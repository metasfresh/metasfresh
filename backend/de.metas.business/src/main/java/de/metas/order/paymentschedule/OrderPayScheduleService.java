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
import de.metas.order.OrderId;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleService
{
	private @NonNull final OrderPayScheduleRepository orderPayScheduleRepo;

	public void create(@NonNull final ImmutableList<OrderPaySchedule> schedules)
	{
		schedules.forEach(orderPayScheduleRepo::save);
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
