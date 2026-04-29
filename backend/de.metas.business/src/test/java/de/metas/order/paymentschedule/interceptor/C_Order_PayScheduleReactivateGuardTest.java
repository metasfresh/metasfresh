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
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * RED TDD test for the {@code C_Order_PayScheduleReactivateGuard} interceptor (TODO-1 of iter-3
 * https://github.com/metasfresh/me03/issues/29369).
 *
 * <p>The guard rejects a {@code C_Order} reactivation when its {@link OrderPaySchedule} contains
 * any line whose {@link OrderPayScheduleStatus} is no longer {@code Pending} (i.e.
 * {@code Awaiting_Pay} or {@code Paid}). Reason: metasfresh's standard re-activate flow drops
 * {@code C_OrderPaySchedule} rows; iter-3 stores meaningful per-receipt state on those rows
 * (Status, BaseAmt, {@code C_Invoice_ID} link, allocations) — re-completing recreates rows from
 * scratch and silently destroys iter-3 state.
 *
 * <p>Reactivation IS allowed when ALL rows are still {@code Pending} (nothing has happened yet —
 * receipts/invoices/payments not landed; safe to drop and rebuild) and when the order has no
 * pay-schedule rows at all (regression guard for non-iter-2 orders, mirrors AC #22 dormancy).
 *
 * <p>Fixture: in-memory POJO models via {@link AdempiereTestHelper}; the {@link OrderPayScheduleService}
 * dependency is mocked. The interceptor is invoked directly (no {@code ModelValidationEngine}
 * round-trip) — this is a focused unit test on the guard's logic, mirroring the pattern in
 * {@code C_Payment_AutoAllocateGuardTest} but adapted to {@code @DocValidate} (the
 * {@link org.adempiere.ad.wrapper.POJOLookupMap} fires {@code @ModelChange} hooks on save but
 * does not dispatch {@code @DocValidate} — direct invocation is the simplest path).
 */
class C_Order_PayScheduleReactivateGuardTest
{
	/** Stand-in {@code C_Order_ID} used by the in-memory order. Any positive int works. */
	private static final int ORDER_ID = 9001;

	/** Stand-in currency id used by the {@link Money} amounts on the schedule lines. */
	private static final CurrencyId CURRENCY_ID = CurrencyId.ofRepoId(102);

	private OrderPayScheduleService orderPayScheduleService;
	private C_Order_PayScheduleReactivateGuard guard;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.orderPayScheduleService = Mockito.mock(OrderPayScheduleService.class);
		this.guard = new C_Order_PayScheduleReactivateGuard(orderPayScheduleService);
	}

	// -------------------------------------------------------------------------
	// TODO-1 — block reactivation when any pay-schedule row is no longer Pending
	// -------------------------------------------------------------------------

	/**
	 * All schedule lines are {@code Pending} — nothing has happened yet on this order; reactivation
	 * is safe (the standard flow drops the schedule and the post-complete re-creation will rebuild
	 * it from scratch with no state loss).
	 */
	@Test
	void allowReactivate_whenAllSchedulesPending()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(OrderId.ofRepoId(ORDER_ID)))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending)));

		assertThatCode(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.doesNotThrowAnyException();
	}

	/**
	 * At least one schedule line has advanced to {@code Awaiting_Pay} — a receipt or proforma
	 * payment cycle has started. Reactivation must be rejected to prevent silent loss of iter-3
	 * state.
	 */
	@Test
	void rejectReactivate_whenAnyScheduleAwaitingPay()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(OrderId.ofRepoId(ORDER_ID)))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Awaiting_Pay)));

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
	}

	/**
	 * At least one schedule line has reached {@code Paid} — the LC step (or a delivery sub-row)
	 * has been settled. Reactivation must be rejected.
	 */
	@Test
	void rejectReactivate_whenAnyScheduleStatusPaid()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(OrderId.ofRepoId(ORDER_ID)))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Pending)));

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
	}

	/**
	 * Order has no {@link OrderPaySchedule} at all (non-iter-2 / non-complex payment-term order) —
	 * the guard must remain dormant; reactivation proceeds unchanged.
	 *
	 * <p>This mirrors the AC #22 dormancy contract from the iter-3 EPIC: any iter-3 component
	 * that touches a non-iter-2 order is a bug.
	 */
	@Test
	void allowReactivate_whenNoSchedules()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(OrderId.ofRepoId(ORDER_ID)))
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
		order.setC_Order_ID(ORDER_ID);
		InterfaceWrapperHelper.saveRecord(order);
		return order;
	}

	private OrderPaySchedule scheduleWithStatuses(@org.jetbrains.annotations.NotNull final OrderPayScheduleStatus... statuses)
	{
		final OrderId orderId = OrderId.ofRepoId(ORDER_ID);
		final ImmutableList.Builder<OrderPayScheduleLine> lines = ImmutableList.builder();
		for (int i = 0; i < statuses.length; i++)
		{
			lines.add(scheduleLine(orderId, i + 1, statuses[i]));
		}
		return OrderPaySchedule.ofList(orderId, lines.build());
	}

	private OrderPayScheduleLine scheduleLine(final OrderId orderId, final int seq, final OrderPayScheduleStatus status)
	{
		// PaymentTermBreakId must be unique within the schedule (used as a key in some lookups).
		// PaymentTermBreakId.ofRepoId requires both the parent PaymentTermId and the break id.
		final PaymentTermBreakId termBreakId = PaymentTermBreakId.ofRepoId(6000, 7000 + seq);
		final OrderPayScheduleId lineId = OrderPayScheduleId.ofRepoId(8000 + seq);

		return OrderPayScheduleLine.builder()
				.id(lineId)
				.orderId(orderId)
				.seqNo(SeqNo.ofInt(seq * 10))
				.paymentTermBreakId(termBreakId)
				.referenceDateType(ReferenceDateType.OrderDate)
				.percent(Percent.of(50))
				.offsetDays(0)
				.status(status)
				.dueDate(LocalDate.of(2026, 1, 1))
				.dueAmount(Money.of(BigDecimal.valueOf(1000), CURRENCY_ID))
				.build();
	}
}
