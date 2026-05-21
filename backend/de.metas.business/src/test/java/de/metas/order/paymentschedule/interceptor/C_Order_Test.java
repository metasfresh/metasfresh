/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order.paymentschedule.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleId;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class C_Order_Test
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);

	private OrderPayScheduleService orderPayScheduleService;
	private C_Order guard;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.orderPayScheduleService = Mockito.mock(OrderPayScheduleService.class);
		this.guard = new C_Order(orderPayScheduleService);
	}

	@Test
	void allowReactivate_whenAllSchedulesPending()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending)));

		assertThatCode(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.doesNotThrowAnyException();
	}

	@Test
	void rejectReactivate_whenAnyScheduleAwaitingPay()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Awaiting_Pay)));

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
	}

	@Test
	void rejectReactivate_whenAnyScheduleStatusPaid()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Pending)));

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
	}

	@Test
	void allowReactivate_whenNoSchedules()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

		assertThatCode(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.doesNotThrowAnyException();
	}

	// -------------------------------------------------------------------------
	// Fixture helpers
	// -------------------------------------------------------------------------

	private I_C_Order newOrder()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_Order_ID(ORDER_ID.getRepoId());
		InterfaceWrapperHelper.saveRecord(order);
		return order;
	}

	private OrderPaySchedule scheduleWithStatuses(@org.jetbrains.annotations.NotNull final OrderPayScheduleStatus... statuses)
	{
		final ImmutableList.Builder<OrderPayScheduleLine> lines = ImmutableList.builder();
		for (int i = 0; i < statuses.length; i++)
		{
			lines.add(scheduleLine(i + 1, statuses[i]));
		}
		return OrderPaySchedule.ofList(ORDER_ID, lines.build());
	}

	private OrderPayScheduleLine scheduleLine(final int seq, final OrderPayScheduleStatus status)
	{
		// PaymentTermBreakId must be unique within the schedule (used as a key in some lookups).
		// PaymentTermBreakId.ofRepoId requires both the parent PaymentTermId and the break id.
		final PaymentTermBreakId termBreakId = PaymentTermBreakId.ofRepoId(6000, 7000 + seq);
		final OrderPayScheduleId lineId = OrderPayScheduleId.ofRepoId(8000 + seq);

		return OrderPayScheduleLine.builder()
				.id(lineId)
				.orderId(C_Order_Test.ORDER_ID)
				.paymentTermBreakId(termBreakId)
				.referenceDateType(ReferenceDateType.OrderDate)
				.percent(Percent.of(50))
				.offsetDays(0)
				.status(status)
				.dueDate(LocalDate.of(2026, 1, 1))
				.dueAmount(Money.of(1000, CurrencyId.EUR))
				.build();
	}
}
