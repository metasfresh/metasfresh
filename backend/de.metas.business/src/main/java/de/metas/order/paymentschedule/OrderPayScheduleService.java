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

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleService
{

	public static OrderPayScheduleService newInstanceForUnitTesting()
	{
		final OrderPayScheduleLoaderAndSaver orderPayScheduleLoaderAndSaver = new OrderPayScheduleLoaderAndSaver(new PaymentTermService(), new OrderPayScheduleRepository());
		return new OrderPayScheduleService(orderPayScheduleLoaderAndSaver);
	}

	@NonNull private final OrderPayScheduleLoaderAndSaver loaderAndSaver;

	public void createOrderPaySchedules(final I_C_Order order) {loaderAndSaver.saveSchedulesForOrder(order);}

	public void create(@NonNull final OrderPayScheduleCreateRequest request) {loaderAndSaver.save(request);}

	@NonNull
	public Optional<OrderPaySchedule> getByOrderId(@NonNull final OrderId orderId) {return loaderAndSaver.loadByOrderId(orderId);}

	public void deleteByOrderId(@NonNull final OrderId orderId) {loaderAndSaver.deleteByOrderId(orderId);}
}
