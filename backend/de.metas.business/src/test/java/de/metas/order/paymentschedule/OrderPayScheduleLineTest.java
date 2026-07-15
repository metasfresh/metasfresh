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

package de.metas.order.paymentschedule;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPayScheduleId;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleLineContext;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pure-POJO unit test for {@link OrderPayScheduleLine#applyAndProcess(OrderPayScheduleLineContext)}.
 * <p>
 * Focus: derivation of the iter-3 columns {@code referenceDate} and {@code isPaid} from
 * {@code (status, dueDate, offsetDays)} inside the sole-writer method. These two fields are
 * indexed aliases of Status — they cannot diverge because they are computed at the same instant
 * Status is written.
 */
class OrderPayScheduleLineTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(123);
	private static final OrderPayScheduleId LINE_ID = OrderPayScheduleId.ofRepoId(456);
	private static final PaymentTermBreakId BREAK_ID = PaymentTermBreakId.ofRepoId(789, 100);
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final int OFFSET_DAYS = 30;
	private static final LocalDate DUE_DATE = LocalDate.of(2026, 4, 15);
	private static final LocalDate REFERENCE_DATE = LocalDate.of(2026, 3, 16);  // DUE_DATE - OFFSET_DAYS
	private static final LocalDate INFINITE_FUTURE = LocalDate.of(9999, 12, 1);  // pending() sentinel

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
				.dueDate(INFINITE_FUTURE)
				.dueAmount(Money.of(BigDecimal.valueOf(1000), EUR))
				.build();
	}

	private static OrderPayScheduleLineContext awaitingPayment()
	{
		return OrderPayScheduleLineContext.awaitingPayment()
				.dueDate(DUE_DATE)
				.build();
	}

	@Test
	void pending_setsReferenceDateNullAndIsPaidFalse()
	{
		final OrderPayScheduleLine line = newPendingLine();
		// Force a Pending → Pending no-op write through applyAndProcess by transitioning
		// Pending → Awaiting_Pay → Pending. The Pending→Pending direct call returns early.
		line.applyAndProcess(awaitingPayment());
		line.applyAndProcess(OrderPayScheduleLineContext.pending());

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		assertThat(line.getReferenceDate()).isNull();
		assertThat(line.isPaid()).isFalse();
	}

	@Test
	void awaitingPayment_setsReferenceDateAsDueDateMinusOffsetAndIsPaidFalse()
	{
		final OrderPayScheduleLine line = newPendingLine();

		line.applyAndProcess(awaitingPayment());

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(line.getReferenceDate()).isEqualTo(REFERENCE_DATE);
		assertThat(line.isPaid()).isFalse();
	}

	@Test
	void paid_keepsReferenceDateAndFlipsIsPaidTrue()
	{
		final OrderPayScheduleLine line = newPendingLine();

		line.applyAndProcess(awaitingPayment());
		line.applyAndProcess(OrderPayScheduleLineContext.paid().dueDate(DUE_DATE).build());

		assertThat(line.getStatus()).isEqualTo(OrderPayScheduleStatus.Paid);
		assertThat(line.getReferenceDate()).isEqualTo(REFERENCE_DATE);
		assertThat(line.isPaid()).isTrue();
	}
}
