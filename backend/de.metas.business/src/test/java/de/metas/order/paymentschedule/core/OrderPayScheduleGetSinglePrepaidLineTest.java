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

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link OrderPaySchedule#getSinglePrepaidLine()} — finding the single prepaid
 * ({@link OrderPayScheduleLine#isPrepaidLine()}: Letter-of-Credit or order-date/advance) break of a
 * payment term, analogous to {@link OrderPaySchedule#getSingleLCLine()}.
 */
class OrderPayScheduleGetSinglePrepaidLineTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(1001);
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final LocalDate DUE_DATE = LocalDate.of(2026, 4, 30);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private OrderPayScheduleLine newLine(final int breakId, @NonNull final ReferenceDateType referenceDateType)
	{
		return OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(2000 + breakId))
				.orderId(ORDER_ID)
				.paymentTermBreakId(PaymentTermBreakId.ofRepoId(3001, breakId))
				.referenceDateType(referenceDateType)
				.percent(Percent.of(100))
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Pending)
				.dueDate(DUE_DATE)
				.dueAmount(Money.of(BigDecimal.valueOf(1000), EUR))
				.build();
	}

	private OrderPaySchedule schedule(final OrderPayScheduleLine... lines)
	{
		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.copyOf(lines));
	}

	@Test
	void getSinglePrepaidLine_singleOrderDateLine_returnsIt()
	{
		final OrderPayScheduleLine prepaidLine = newLine(100, ReferenceDateType.OrderDate);
		final OrderPayScheduleLine materialReceiptLine = newLine(101, ReferenceDateType.BillOfLadingDate);

		final OrderPaySchedule paySchedule = schedule(prepaidLine, materialReceiptLine);

		assertThat(paySchedule.getSinglePrepaidLine()).contains(prepaidLine);
	}

	@Test
	void getSinglePrepaidLine_singleLcLine_returnsIt()
	{
		// An LC line IS a prepaid line: with one LC break + one material-receipt break the LC line is returned.
		final OrderPayScheduleLine lcLine = newLine(100, ReferenceDateType.LetterOfCreditDate);
		final OrderPayScheduleLine materialReceiptLine = newLine(101, ReferenceDateType.BillOfLadingDate);

		final OrderPaySchedule paySchedule = schedule(lcLine, materialReceiptLine);

		assertThat(paySchedule.getSinglePrepaidLine()).contains(lcLine);
	}

	@Test
	void getSinglePrepaidLine_onlyMaterialReceiptLines_returnsEmpty()
	{
		final OrderPayScheduleLine materialReceiptLine1 = newLine(100, ReferenceDateType.BillOfLadingDate);
		final OrderPayScheduleLine materialReceiptLine2 = newLine(101, ReferenceDateType.ETADate);

		final OrderPaySchedule paySchedule = schedule(materialReceiptLine1, materialReceiptLine2);

		assertThat(paySchedule.getSinglePrepaidLine()).isEmpty();
	}

	@Test
	void getSinglePrepaidLine_soleInvoiceDateNoOrderDateNoLc_returnsEmpty()
	{
		// A term whose only non-material-receipt break is an invoice-date break has NO prepaid step —
		// an invoice-date break is a regular post-invoice term — so the proforma settles nothing here.
		final OrderPayScheduleLine materialReceiptLine = newLine(100, ReferenceDateType.BillOfLadingDate);
		final OrderPayScheduleLine invoiceDateLine = newLine(101, ReferenceDateType.InvoiceDate);

		final OrderPaySchedule paySchedule = schedule(materialReceiptLine, invoiceDateLine);

		assertThat(paySchedule.getSinglePrepaidLine()).isEmpty();
	}

	@Test
	void getSinglePrepaidLine_invoiceDateAndOrderDate_ignoresInvoiceDate()
	{
		// An invoice-date break is a regular post-invoice term, not prepaid: with one OD (prepaid) break
		// + one Invoice break the method returns the OD line and does NOT count the Invoice line.
		final OrderPayScheduleLine orderDateLine = newLine(100, ReferenceDateType.OrderDate);
		final OrderPayScheduleLine invoiceDateLine = newLine(101, ReferenceDateType.InvoiceDate);

		final OrderPaySchedule paySchedule = schedule(orderDateLine, invoiceDateLine);

		assertThat(paySchedule.getSinglePrepaidLine()).contains(orderDateLine);
	}

	@Test
	void getSinglePrepaidLine_twoOrderDateLines_throws()
	{
		final OrderPayScheduleLine prepaidLine1 = newLine(100, ReferenceDateType.OrderDate);
		final OrderPayScheduleLine prepaidLine2 = newLine(101, ReferenceDateType.OrderDate);

		final OrderPaySchedule paySchedule = schedule(prepaidLine1, prepaidLine2);

		assertThatThrownBy(paySchedule::getSinglePrepaidLine)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("MultipleAdvanceBreaksUnsupported");
	}

	@Test
	void getSinglePrepaidLine_lcAndOrderDate_throws()
	{
		// Both LC and OD are prepaid. The guarded caller resolves LC first (getSingleLCLine) and only falls
		// through to getSinglePrepaidLine for the no-LC case, so this misuse never happens on that path —
		// but called directly on a term carrying both, it correctly fails loud rather than silently pick one.
		final OrderPayScheduleLine lcLine = newLine(100, ReferenceDateType.LetterOfCreditDate);
		final OrderPayScheduleLine orderDateLine = newLine(101, ReferenceDateType.OrderDate);

		final OrderPaySchedule paySchedule = schedule(lcLine, orderDateLine);

		assertThatThrownBy(paySchedule::getSinglePrepaidLine)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("MultipleAdvanceBreaksUnsupported");
	}
}
