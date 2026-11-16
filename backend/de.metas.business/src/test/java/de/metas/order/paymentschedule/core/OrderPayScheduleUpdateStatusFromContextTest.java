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
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.InOutId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link OrderPaySchedule#updateStatusFromContext(OrderSchedulingContext)} — the recompute
 * must refresh an unsettled {@code Awaiting_Pay} material-receipt (BL/ETA) line on a reference-date
 * change, while leaving an {@code Awaiting_Pay} LC line untouched (that line stays on the dedicated
 * LC step service path).
 */
class OrderPayScheduleUpdateStatusFromContextTest
{
	private static final PaymentTermId PT_ID = PaymentTermId.ofRepoId(5002);
	private static final PaymentTermBreakId BL_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID.getRepoId(), 5020);
	private static final PaymentTermBreakId LC_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID.getRepoId(), 5021);
	private static final CurrencyId EUR = CurrencyId.ofRepoId(102);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(7002);

	private static final LocalDate OLD_BL_DATE = LocalDate.of(2026, 3, 1);
	private static final LocalDate NEW_BL_DATE = LocalDate.of(2026, 3, 15); // corrected BL date
	private static final LocalDate LC_DATE = LocalDate.of(2026, 2, 1); // LC line's stored value, must stay untouched
	private static final LocalDate NEW_LC_CONTEXT_DATE = LocalDate.of(2026, 2, 20); // context-only change; must never reach the LC line

	private static final PaymentTermBreakId ETA_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID.getRepoId(), 5022);
	private static final LocalDate OLD_ETA_DATE = LocalDate.of(2026, 4, 1);
	private static final LocalDate NEW_ETA_DATE = LocalDate.of(2026, 4, 15); // corrected ETA date

	private PaymentTerm newPaymentTerm()
	{
		final PaymentTermBreak blBreak = PaymentTermBreak.builder()
				.id(BL_BREAK_ID)
				.referenceDateType(ReferenceDateType.BillOfLadingDate)
				.percent(Percent.of("50"))
				.seqNo(SeqNo.ofInt(10))
				.offsetDays(0)
				.build();

		final PaymentTermBreak lcBreak = PaymentTermBreak.builder()
				.id(LC_BREAK_ID)
				.referenceDateType(ReferenceDateType.LetterOfCreditDate)
				.percent(Percent.of("50"))
				.seqNo(SeqNo.ofInt(20))
				.offsetDays(0)
				.build();

		return PaymentTerm.builder()
				.id(PT_ID)
				.clientId(ClientId.SYSTEM)
				.orgId(OrgId.ANY)
				.value("pt_bl_lc")
				.name("pt_bl_lc (BL 50% + LC 50%)")
				.breaks(ImmutableList.of(blBreak, lcBreak))
				.paySchedules(ImmutableList.of())
				.build();
	}

	private PaymentTerm newPaymentTermWithETA()
	{
		final PaymentTermBreak etaBreak = PaymentTermBreak.builder()
				.id(ETA_BREAK_ID)
				.referenceDateType(ReferenceDateType.ETADate)
				.percent(Percent.of("50"))
				.seqNo(SeqNo.ofInt(10))
				.offsetDays(0)
				.build();

		final PaymentTermBreak lcBreak = PaymentTermBreak.builder()
				.id(LC_BREAK_ID)
				.referenceDateType(ReferenceDateType.LetterOfCreditDate)
				.percent(Percent.of("50"))
				.seqNo(SeqNo.ofInt(20))
				.offsetDays(0)
				.build();

		return PaymentTerm.builder()
				.id(PT_ID)
				.clientId(ClientId.SYSTEM)
				.orgId(OrgId.ANY)
				.value("pt_eta_lc")
				.name("pt_eta_lc (ETA 50% + LC 50%)")
				.breaks(ImmutableList.of(etaBreak, lcBreak))
				.paySchedules(ImmutableList.of())
				.build();
	}

	private OrderPayScheduleLine newAwaitingPayLine(
			final PaymentTermBreakId breakId,
			final ReferenceDateType referenceDateType,
			final LocalDate referenceDate)
	{
		return OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(breakId.getRepoId()))
				.orderId(ORDER_ID)
				.paymentTermBreakId(breakId)
				.referenceDateType(referenceDateType)
				.percent(Percent.of("50"))
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Awaiting_Pay)
				.isPaid(false)
				.referenceDate(referenceDate)
				.dueDate(referenceDate)
				.dueAmount(Money.of(BigDecimal.valueOf(5000), EUR))
				.invoiceId(null)
				.inoutId(null)
				.build();
	}

	/**
	 * After a Transport Order completes, the BL line is already {@code Awaiting_Pay}. A later BL-date
	 * correction must still refresh its due date (unsettled: not paid, no invoice, no receipt linked
	 * yet). The LC line, also {@code Awaiting_Pay}, must stay byte-behavior-identical — it is refreshed
	 * only via the dedicated LC step service, never by this generic recompute.
	 */
	@Test
	void blLineRefreshed_lcLineUntouched_onBLDateCorrection()
	{
		final OrderPayScheduleLine blLine = newAwaitingPayLine(BL_BREAK_ID, ReferenceDateType.BillOfLadingDate, OLD_BL_DATE);
		final OrderPayScheduleLine lcLine = newAwaitingPayLine(LC_BREAK_ID, ReferenceDateType.LetterOfCreditDate, LC_DATE);

		final OrderPaySchedule paySchedule = OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(blLine, lcLine));

		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.billOfLadingDate(NEW_BL_DATE) // corrected
				.letterOfCreditDate(NEW_LC_CONTEXT_DATE) // also changed in the context; must not reach the LC line
				.grandTotal(Money.of(BigDecimal.valueOf(10000), EUR))
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(newPaymentTerm())
				.build();

		paySchedule.updateStatusFromContext(context);

		assertThat(blLine.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(blLine.isPaid()).isFalse();
		assertThat(blLine.getDueDate()).as("BL line dueDate refreshed to corrected BL date").isEqualTo(NEW_BL_DATE);
		assertThat(blLine.getReferenceDate()).isEqualTo(NEW_BL_DATE);

		assertThat(lcLine.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(lcLine.isPaid()).isFalse();
		assertThat(lcLine.getDueDate()).as("LC line dueDate must stay at its OLD value, ignoring the changed context LC date").isEqualTo(LC_DATE);
		assertThat(lcLine.getReferenceDate()).as("LC line referenceDate must stay at its OLD value, ignoring the changed context LC date").isEqualTo(LC_DATE);
	}

	/**
	 * Same recompute path as {@link #blLineRefreshed_lcLineUntouched_onBLDateCorrection()}, but for the
	 * other material-receipt reference type: an unsettled {@code Awaiting_Pay} {@code ETA} line must also
	 * refresh its due date on an ETA reference-date correction, proving the refresh gate
	 * ({@code isMaterialReceiptDate()}) covers ETA, not just BL.
	 */
	@Test
	void etaLineRefreshed_onETADateCorrection()
	{
		final OrderPayScheduleLine etaLine = newAwaitingPayLine(ETA_BREAK_ID, ReferenceDateType.ETADate, OLD_ETA_DATE);
		final OrderPayScheduleLine lcLine = newAwaitingPayLine(LC_BREAK_ID, ReferenceDateType.LetterOfCreditDate, LC_DATE);

		final OrderPaySchedule paySchedule = OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(etaLine, lcLine));

		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.ETADate(NEW_ETA_DATE) // corrected
				.letterOfCreditDate(NEW_LC_CONTEXT_DATE) // also changed in the context; must not reach the LC line
				.grandTotal(Money.of(BigDecimal.valueOf(10000), EUR))
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(newPaymentTermWithETA())
				.build();

		paySchedule.updateStatusFromContext(context);

		assertThat(etaLine.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(etaLine.isPaid()).isFalse();
		assertThat(etaLine.getDueDate()).as("ETA line dueDate refreshed to corrected ETA date").isEqualTo(NEW_ETA_DATE);
		assertThat(etaLine.getReferenceDate()).isEqualTo(NEW_ETA_DATE);

		assertThat(lcLine.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(lcLine.isPaid()).isFalse();
		assertThat(lcLine.getDueDate()).as("LC line dueDate must stay at its OLD value, ignoring the changed context LC date").isEqualTo(LC_DATE);
		assertThat(lcLine.getReferenceDate()).as("LC line referenceDate must stay at its OLD value, ignoring the changed context LC date").isEqualTo(LC_DATE);
	}

	/**
	 * The refresh gate excludes an already-paid line: an {@code Awaiting_Pay} BL line whose {@code isPaid}
	 * has drifted to true (hand-edited/legacy row) must NOT be re-dated by a later BL-date correction —
	 * the {@code !isPaid()} guard keeps it untouched.
	 */
	@Test
	void paidMaterialReceiptLine_notRefreshed_onBLDateCorrection()
	{
		final OrderPayScheduleLine paidBlLine = OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(BL_BREAK_ID.getRepoId()))
				.orderId(ORDER_ID)
				.paymentTermBreakId(BL_BREAK_ID)
				.referenceDateType(ReferenceDateType.BillOfLadingDate)
				.percent(Percent.of("50"))
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Awaiting_Pay)
				.isPaid(true) // drifted: Awaiting_Pay yet already paid
				.referenceDate(OLD_BL_DATE)
				.dueDate(OLD_BL_DATE)
				.dueAmount(Money.of(BigDecimal.valueOf(5000), EUR))
				.build();

		final OrderPaySchedule paySchedule = OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(paidBlLine));

		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.billOfLadingDate(NEW_BL_DATE) // corrected
				.grandTotal(Money.of(BigDecimal.valueOf(10000), EUR))
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(newPaymentTerm())
				.build();

		paySchedule.updateStatusFromContext(context);

		assertThat(paidBlLine.getDueDate()).as("a paid line must NOT be refreshed by a later BL-date correction").isEqualTo(OLD_BL_DATE);
		assertThat(paidBlLine.getReferenceDate()).isEqualTo(OLD_BL_DATE);
	}

	/**
	 * The refresh gate excludes a line already linked to a committed downstream document: an
	 * {@code Awaiting_Pay} BL line whose goods receipt ({@code inoutId}) is set must NOT be re-dated by a
	 * later BL-date correction — the {@code !isLinkedToDownstreamDocument()} guard keeps it untouched.
	 */
	@Test
	void downstreamLinkedMaterialReceiptLine_notRefreshed_onBLDateCorrection()
	{
		final OrderPayScheduleLine linkedBlLine = OrderPayScheduleLine.builder()
				.id(OrderPayScheduleId.ofRepoId(BL_BREAK_ID.getRepoId()))
				.orderId(ORDER_ID)
				.paymentTermBreakId(BL_BREAK_ID)
				.referenceDateType(ReferenceDateType.BillOfLadingDate)
				.percent(Percent.of("50"))
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Awaiting_Pay)
				.isPaid(false)
				.referenceDate(OLD_BL_DATE)
				.dueDate(OLD_BL_DATE)
				.dueAmount(Money.of(BigDecimal.valueOf(5000), EUR))
				.inoutId(InOutId.ofRepoId(900001)) // goods receipt already matched
				.build();

		final OrderPaySchedule paySchedule = OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(linkedBlLine));

		final OrderSchedulingContext context = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.billOfLadingDate(NEW_BL_DATE) // corrected
				.grandTotal(Money.of(BigDecimal.valueOf(10000), EUR))
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(newPaymentTerm())
				.build();

		paySchedule.updateStatusFromContext(context);

		assertThat(linkedBlLine.getDueDate()).as("a downstream-linked line must NOT be refreshed by a later BL-date correction").isEqualTo(OLD_BL_DATE);
		assertThat(linkedBlLine.getReferenceDate()).isEqualTo(OLD_BL_DATE);
	}
}
