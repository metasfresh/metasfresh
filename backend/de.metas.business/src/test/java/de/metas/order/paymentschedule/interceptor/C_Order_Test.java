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
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleId;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.OrderPayScheduleProformaService;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.ProformaInvoice;
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
	private OrderPayScheduleProformaService proformaService;
	private C_Order guard;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.orderPayScheduleService = Mockito.mock(OrderPayScheduleService.class);
		this.proformaService = Mockito.mock(OrderPayScheduleProformaService.class);
		this.guard = new C_Order(orderPayScheduleService, proformaService);
	}

	@Test
	void allowReactivate_whenAllSchedulesPending()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending)));
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

		assertThatCode(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.doesNotThrowAnyException();
	}

	/**
	 * A line with status Awaiting_Pay but no downstream link (no inoutId, no invoiceId, no proforma)
	 * must NOT block reactivation under the new semantics.
	 */
	@Test
	void allowReactivate_whenNonPendingLineHasNoDownstreamActivity()
	{
		final I_C_Order order = newOrder();
		// schedule has one Awaiting_Pay and one Pending line — but NO downstream links
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Pending)));
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

		assertThatCode(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.doesNotThrowAnyException();
	}

	/**
	 * Status Awaiting_Pay without a downstream link must NOT block reactivation — the block requires
	 * an actual inoutId/invoiceId/proforma. This test drives the block via an inoutId on an
	 * Awaiting_Pay line, demonstrating the guard is status-agnostic (blocks on the link, regardless
	 * of status).
	 */
	@Test
	void rejectReactivate_whenAwaitingPayLineHasInoutLink()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithInoutLink(OrderPayScheduleStatus.Awaiting_Pay)));
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
	}

	/**
	 * A Paid line always implies a matched-invoice link; bare Paid status alone does not block —
	 * the block requires the actual downstream link. This test drives the block via an invoiceId on
	 * a Paid line.
	 */
	@Test
	void rejectReactivate_whenPaidLineHasInvoiceLink()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithInvoiceLink(OrderPayScheduleStatus.Paid)));
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

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
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

		assertThatCode(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.doesNotThrowAnyException();
	}

	/** NEW: proforma allocation alone (no per-line link) must block reactivation. */
	@Test
	void rejectReactivate_whenProformaAllocationExists()
	{
		final I_C_Order order = newOrder();
		// schedule lines carry NO per-line downstream link — the block must come from the proforma alone
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithStatuses(OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending)));
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(stubProformaInvoice()));

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
	}

	/** NEW: even a Pending line carrying an inoutId (goods-receipt link) must block reactivation. */
	@Test
	void rejectReactivate_whenAnyLineHasInoutLink()
	{
		final I_C_Order order = newOrder();
		Mockito.when(orderPayScheduleService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.of(scheduleWithInoutLink(OrderPayScheduleStatus.Pending)));
		Mockito.when(proformaService.getByOrderId(ORDER_ID))
				.thenReturn(Optional.empty());

		assertThatThrownBy(() -> guard.blockReactivateWhenScheduleNotPending(order))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Order_Reactivate_Blocked_By_PaySchedule_Activity");
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

	/** Schedule with one line (given status) carrying a goods-receipt link (inoutId). */
	private OrderPaySchedule scheduleWithInoutLink(final OrderPayScheduleStatus status)
	{
		final OrderPayScheduleLine line = scheduleLineBuilder(1, status)
				.inoutId(InOutId.ofRepoId(500))
				.build();
		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(line));
	}

	/** Schedule with one line (given status) carrying a matched-invoice link (invoiceId). */
	private OrderPaySchedule scheduleWithInvoiceLink(final OrderPayScheduleStatus status)
	{
		final OrderPayScheduleLine line = scheduleLineBuilder(1, status)
				.invoiceId(InvoiceId.ofRepoId(600))
				.build();
		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(line));
	}

	/** Minimal real ProformaInvoice instance (ProformaInvoice is @Value/final — cannot be mocked). */
	private ProformaInvoice stubProformaInvoice()
	{
		return ProformaInvoice.builder()
				.id(InvoiceId.ofRepoId(700))
				.grandTotal(Money.of(1000, CurrencyId.EUR))
				.dateInvoiced(LocalDate.of(2026, 1, 1))
				.dueDate(LocalDate.of(2026, 2, 1))
				.build();
	}

	private OrderPayScheduleLine scheduleLine(final int seq, final OrderPayScheduleStatus status)
	{
		return scheduleLineBuilder(seq, status).build();
	}

	private OrderPayScheduleLine.OrderPayScheduleLineBuilder scheduleLineBuilder(final int seq, final OrderPayScheduleStatus status)
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
				.dueAmount(Money.of(1000, CurrencyId.EUR));
	}
}
