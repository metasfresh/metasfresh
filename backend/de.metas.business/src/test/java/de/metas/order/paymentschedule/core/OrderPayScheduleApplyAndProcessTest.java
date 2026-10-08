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

package de.metas.order.paymentschedule.core;

import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * State-machine transition tests for {@link OrderPayScheduleLine#applyAndProcess(OrderPayScheduleLineContext)}.
 * <p>
 * Covers: same-status field-update (B3 fix), forward transitions, reversal transitions,
 * disallowed transitions, and referenceDate/invoiceId clearing on reversal.
 */
class OrderPayScheduleApplyAndProcessTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(1001);
	private static final OrderPayScheduleId LINE_ID = OrderPayScheduleId.ofRepoId(2001);
	private static final PaymentTermBreakId BREAK_ID = PaymentTermBreakId.ofRepoId(3001, 100);
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final int OFFSET_DAYS = 30;
	private static final LocalDate DUE_DATE = LocalDate.of(2026, 4, 30);
	private static final LocalDate REFERENCE_DATE = DUE_DATE.minusDays(OFFSET_DAYS); // 2026-03-31
	private static final InvoiceId INV1 = InvoiceId.ofRepoId(9001);
	private static final InOutId REC1 = InOutId.ofRepoId(8001);

	private OrderPayScheduleLine newPendingLine()
	{
		return OrderPayScheduleLine.builder()
				.id(LINE_ID)
				.orderId(ORDER_ID)
				.paymentTermBreakId(BREAK_ID)
				.referenceDateType(ReferenceDateType.OrderDate)
				.percent(Percent.of(100))
				.offsetDays(OFFSET_DAYS)
				.status(OrderPayScheduleStatus.Pending)
				.dueDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
				.dueAmount(Money.of(BigDecimal.valueOf(1000), EUR))
				.build();
	}

	private OrderPayScheduleLine newAwaitingPayLine()
	{
		final OrderPayScheduleLine line = newPendingLine();
		line.applyAndProcess(
				OrderPayScheduleLineContext.awaitingPayment()
						.dueDate(DUE_DATE)
						.invoiceId(INV1)
						.build());
		return line;
	}

	// -----------------------------------------------------------------------
	// Test 1: pendingToPending_noChange
	// Locks in the B3 fix: same-status calls no longer early-return, so
	// non-status fields (inoutId) are still applied even when status=Pending→Pending.
	// -----------------------------------------------------------------------
	@Test
	void pendingToPending_noChange()
	{
		final OrderPayScheduleLine line = newPendingLine();

		final OrderPayScheduleLineContext ctx = OrderPayScheduleLineContext.builder()
				.status(OrderPayScheduleStatus.Pending)
				.dueDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
				.inoutId(REC1)
				.build();
		line.applyAndProcess(ctx);

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		assertThat(line.getInoutId()).isEqualTo(REC1);    // field applied despite same-status
		assertThat(line.isPaid()).isFalse();
		assertThat(line.getReferenceDate()).isNull();      // Pending always nulls referenceDate
	}

	// -----------------------------------------------------------------------
	// Test 2: pendingToAwaitingPay_setsCInvoiceIdAndReferenceDate
	// -----------------------------------------------------------------------
	@Test
	void pendingToAwaitingPay_setsCInvoiceIdAndReferenceDate()
	{
		final OrderPayScheduleLine line = newPendingLine();

		line.applyAndProcess(
				OrderPayScheduleLineContext.awaitingPayment()
						.dueDate(DUE_DATE)
						.invoiceId(INV1)
						.build());

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(line.getInvoiceId()).isEqualTo(INV1);
		// referenceDate derived from dueDate - offsetDays because referenceDateSet=false
		assertThat(line.getReferenceDate()).isEqualTo(REFERENCE_DATE);
		assertThat(line.isPaid()).isFalse();
	}

	// -----------------------------------------------------------------------
	// Test 3: awaitingPayToPaid_setsIsPaidYRefDateUnchanged
	// referenceDate is recomputed as dueDate - offsetDays when isReferenceDateSet=false.
	// -----------------------------------------------------------------------
	@Test
	void awaitingPayToPaid_setsIsPaidAndRecomputesReferenceDate()
	{
		final OrderPayScheduleLine line = newAwaitingPayLine(); // referenceDate=REFERENCE_DATE from Awaiting_Pay
		assertThat(line.getReferenceDate()).isEqualTo(REFERENCE_DATE);

		// Transition to Paid; do NOT provide an explicit referenceDate
		line.applyAndProcess(
				OrderPayScheduleLineContext.paid()
						.dueDate(DUE_DATE)
						.build());

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Paid);
		assertThat(line.isPaid()).isTrue();
		// Not-Pending, no referenceDate set in context → recomputed as dueDate - offsetDays
		assertThat(line.getReferenceDate()).isEqualTo(DUE_DATE.minusDays(OFFSET_DAYS));
	}

	// -----------------------------------------------------------------------
	// Test 4: paidToPending_disallowed_throws
	// Paid→Pending is NOT in allowedTransitions; must throw AdempiereException.
	// -----------------------------------------------------------------------
	@Test
	void paidToPending_disallowed_throws()
	{
		final OrderPayScheduleLine line = newAwaitingPayLine();
		line.applyAndProcess(OrderPayScheduleLineContext.paid().dueDate(DUE_DATE).build());
		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Paid); // sanity

		assertThatThrownBy(() -> line.applyAndProcess(OrderPayScheduleLineContext.pending()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Cannot change status from Paid to Pending");
	}

	// -----------------------------------------------------------------------
	// Test 5: awaitingPayToPending_clearsCInvoiceId_setsRefDateNull
	// Partial-invoice reversal: invoiceId cleared, referenceDate set to null.
	// -----------------------------------------------------------------------
	@Test
	void awaitingPayToPending_clearsCInvoiceId_setsRefDateNull()
	{
		final OrderPayScheduleLine line = newAwaitingPayLine();
		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(line.getInvoiceId()).isEqualTo(INV1);
		assertThat(line.getReferenceDate()).isEqualTo(REFERENCE_DATE);

		// Reversal: status→Pending, invoiceId explicitly cleared (invoiceIdSet=true, value=null),
		// referenceDateSet=false → production sets referenceDate=null when nextStatus is Pending.
		line.applyAndProcess(
				OrderPayScheduleLineContext.builder()
						.status(OrderPayScheduleStatus.Pending)
						.dueDate(OrderPayScheduleLineContext.INFINITE_FUTURE_DATE)
						.invoiceId(null)   // sets invoiceIdSet=true with null value
						.build());

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		assertThat(line.getInvoiceId()).isNull();       // cleared
		assertThat(line.getReferenceDate()).isNull();   // Pending always nulls referenceDate
		assertThat(line.isPaid()).isFalse();
	}
}
