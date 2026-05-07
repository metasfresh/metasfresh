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

package de.metas.order.paymentschedule.steps.material_receipt;

import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleId;
import de.metas.order.paymentschedule.core.OrderPayScheduleLineContext;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.OrderSchedulingContext;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceipt;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceiptCollection;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.OrderPayScheduleRegularInvoiceService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.RegularInvoice;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * JU1 — property test for {@link OrderPayScheduleMaterialReceiptStepService#computeOrderPayScheduleLines}.
 * <p>
 * Enumerates the (receipts × invoices × DocStatus × IsPartialInvoice) truth table from
 * REQUIREMENTS.md §5 JU1 (~36 cells). Each parametrised invocation:
 * <ol>
 * <li>Builds {@link OrderSchedulingContext} + {@link MaterialReceiptCollection} + invoice stubs from the {@link Cell}.</li>
 * <li>Calls {@code computeOrderPayScheduleLines} twice.</li>
 * <li>Asserts (a) all expected fields per {@link Cell}, (b) second invocation produces identical state (idempotence — AC #19).</li>
 * </ol>
 *
 * <p><b>Arithmetic note</b>:
 * <p>For each receipt row: {@code dueAmountActual = receipt.GrandTotal (with-tax) × break%}.
 * The remainder row (BaseAmt = max(0, order.GrandTotal − Σ receipt.with_tax)) is omitted if BaseAmt ≤ 0 (over-delivery).
 * <ul>
 * <li>R1 (31,808.00): dueAmountActual = 31808.00 × 51.04% ≈ 16,224.03.</li>
 * <li>R2_OVER (37,092.00): dueAmountActual = 37092.00 × 51.04% ≈ 18,923.85; remainder BaseAmt = 0 (over-delivery) → remainder omitted.</li>
 * <li>R2_UNDER (10,000.00): dueAmountActual = 10000.00 × 51.04% ≈ 5,104.00; remainder BaseAmt = 8,858.00 → remainder row created.</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class OrderPayScheduleDeliveryServiceTest
{
	// -----------------------------------------------------------------------
	// Shared constants
	// -----------------------------------------------------------------------

	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);

	/** Payment term: LC=30% (break 1001) + MR=70% (break 1002). */
	private static final PaymentTermId PT_ID = PaymentTermId.ofRepoId(1000);
	private static final PaymentTermBreakId LC_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID, 1001);
	private static final PaymentTermBreakId MR_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID, 1002);

	/**
	 * PO GrandTotal from the customer spreadsheet: 68,654.40 EUR.
	 * MR break (last break) = GrandTotal − LC = 68654.40 − 20596.32 = 48,058.08 EUR.
	 */
	private static final Money GRAND_TOTAL = Money.of("68654.40", EUR);

	/** dueAmtRemaining starts at MR_BREAK_TOTAL = 48,058.08. */
	private static final Money MR_BREAK_TOTAL = Money.of("48058.08", EUR);

	/** R1 receipt with-tax value (customer canonical). */
	private static final Money R1_VALUE = Money.of("31808.00", EUR);

	/**
	 * R2 receipt value for over-delivery: R2 = 37,092.00.
	 * R1 + R2 = 68,900 > GrandTotal 68,654.40. MR remaining after R1 = 16,250.08;
	 * R2 dueAmountActual = min(37092, 16250.08) = 16,250.08; remaining after = 0 (no remainder).
	 */
	private static final Money R2_OVER_VALUE = Money.of("37092.00", EUR);

	/**
	 * R2 receipt value for under-delivery: R2 = 10,000.00.
	 * R1 + R2 = 41,808 < MR_BREAK_TOTAL 48,058.08.
	 * R2 dueAmountActual = min(10000, 16250.08) = 10,000.00; remaining = 6,250.08 (remainder row).
	 */
	private static final Money R2_UNDER_VALUE = Money.of("10000.00", EUR);

	// dueAmountActual values — §3.5 AC #3 + invariant I-1: DueAmt = BaseAmt × break% (break%=70%).

	/** R1 sub-row: BaseAmt=31808.00; DueAmt = 31808.00 × 70% = 22,265.60. */
	private static final Money R1_DUE_ACTUAL = Money.of("22265.60", EUR);

	/** R2_OVER sub-row: BaseAmt=37092.00; DueAmt = 37092.00 × 70% = 25,964.40. */
	private static final Money R2_OVER_DUE_ACTUAL = Money.of("25964.40", EUR);

	/** R2_UNDER sub-row: BaseAmt=10000.00; DueAmt = 10000.00 × 70% = 7,000.00. */
	private static final Money R2_UNDER_DUE_ACTUAL = Money.of("7000.00", EUR);

	/**
	 * Remainder after R1 only: BaseAmt = GRAND_TOTAL − R1_VALUE = 68654.40 − 31808.00 = 36,846.40.
	 * DueAmt = 36846.40 × 70% = 25,792.48.
	 */
	private static final Money REMAINDER_AFTER_R1_DUE = Money.of("25792.48", EUR);

	/**
	 * Remainder after R1+R2_UNDER: BaseAmt = 68654.40 − 31808.00 − 10000.00 = 26,846.40.
	 * DueAmt = 26846.40 × 70% = 18,792.48.
	 */
	private static final Money REMAINDER_AFTER_R1_R2_UNDER_DUE = Money.of("18792.48", EUR);

	private static final InOutId R1_ID = InOutId.ofRepoId(8001);
	private static final InOutId R2_ID = InOutId.ofRepoId(8002);
	private static final InOutLineId R1_LINE_ID = InOutLineId.ofRepoId(8101);
	private static final InOutLineId R2_LINE_ID = InOutLineId.ofRepoId(8201);
	private static final InvoiceId INV1_ID = InvoiceId.ofRepoId(5001);
	private static final InvoiceId INV2_ID = InvoiceId.ofRepoId(5002);

	private static final LocalDate MOVEMENT_DATE = LocalDate.of(2026, 3, 15);

	// -----------------------------------------------------------------------
	// Cell — one truth-table row (Java-8-compatible; no record syntax)
	// -----------------------------------------------------------------------

	/**
	 * One truth-table row.
	 *
	 * @param label              Human-readable label for the surefire report.
	 * @param hasR1              R1 receipt is in the collection.
	 * @param hasR2              R2 receipt is in the collection.
	 * @param useOverDeliveryR2  True = use R2_OVER_VALUE; false = use R2_UNDER_VALUE.
	 * @param inv1ForR1          Invoice for R1 (null = no invoice). DR/RE stubs simulate absent invoice (service filter).
	 * @param inv2ForR2          Invoice for R2 (null = no invoice).
	 * @param expectedRowCount   Total output rows.
	 * @param expectedRemainderDue Expected dueAmountActual for the remainder row; null = no remainder expected.
	 * @param expectedR1Status   Expected R1 sub-row status; null when R1 absent.
	 * @param expectedR2Status   Expected R2 sub-row status; null when R2 absent.
	 */
	static final class Cell
	{
		final String label;
		final boolean hasR1;
		final boolean hasR2;
		final boolean useOverDeliveryR2;
		@Nullable final RegularInvoice inv1ForR1;
		@Nullable final RegularInvoice inv2ForR2;
		final int expectedRowCount;
		@Nullable final Money expectedRemainderDue;
		@Nullable final OrderPayScheduleStatus expectedR1Status;
		@Nullable final OrderPayScheduleStatus expectedR2Status;

		Cell(
				final String label,
				final boolean hasR1,
				final boolean hasR2,
				final boolean useOverDeliveryR2,
				@Nullable final RegularInvoice inv1ForR1,
				@Nullable final RegularInvoice inv2ForR2,
				final int expectedRowCount,
				@Nullable final Money expectedRemainderDue,
				@Nullable final OrderPayScheduleStatus expectedR1Status,
				@Nullable final OrderPayScheduleStatus expectedR2Status)
		{
			this.label = label;
			this.hasR1 = hasR1;
			this.hasR2 = hasR2;
			this.useOverDeliveryR2 = useOverDeliveryR2;
			this.inv1ForR1 = inv1ForR1;
			this.inv2ForR2 = inv2ForR2;
			this.expectedRowCount = expectedRowCount;
			this.expectedRemainderDue = expectedRemainderDue;
			this.expectedR1Status = expectedR1Status;
			this.expectedR2Status = expectedR2Status;
		}

		@Override
		public String toString() {return label;}
	}

	// -----------------------------------------------------------------------
	// Invoice stub factory helpers
	// -----------------------------------------------------------------------

	private static RegularInvoice invoice(
			final InvoiceId id,
			final DocStatus docStatus,
			final boolean isPaid,
			final boolean isPartialInvoice)
	{
		return RegularInvoice.builder()
				.id(id)
				.orderId(ORDER_ID)
				.isPartialInvoice(isPartialInvoice)
				.orgId(de.metas.organization.OrgId.ANY)
				.bpartnerId(de.metas.bpartner.BPartnerId.ofRepoId(1))
				.dateInvoiced(LocalDate.of(2026, 3, 20))
				.dateAcct(LocalDate.of(2026, 3, 20))
				.currencyId(EUR)
				.docStatus(docStatus)
				.isPaid(isPaid)
				.lines(ImmutableList.of())
				.build();
	}

	/** CO, unpaid, Partial. */
	private static RegularInvoice inv1CO_Y()      {return invoice(INV1_ID, DocStatus.Completed, false, true);}

	/** CO, unpaid, Final. */
	private static RegularInvoice inv1CO_N()      {return invoice(INV1_ID, DocStatus.Completed, false, false);}

	/** CO, fully paid, Partial. */
	private static RegularInvoice inv1CO_Y_paid() {return invoice(INV1_ID, DocStatus.Completed, true, true);}

	/** CO, fully paid, Final. */
	private static RegularInvoice inv1CO_N_paid() {return invoice(INV1_ID, DocStatus.Completed, true, false);}

	/** DR, Partial — treated as absent by the invoice-service filter. */
	private static RegularInvoice inv1DR_Y()      {return invoice(INV1_ID, DocStatus.Drafted, false, true);}

	/** DR, Final. */
	private static RegularInvoice inv1DR_N()      {return invoice(INV1_ID, DocStatus.Drafted, false, false);}

	/** RE, Partial — treated as absent by the invoice-service filter. */
	private static RegularInvoice inv1RE_Y()      {return invoice(INV1_ID, DocStatus.Reversed, false, true);}

	/** RE, Final. */
	private static RegularInvoice inv1RE_N()      {return invoice(INV1_ID, DocStatus.Reversed, false, false);}

	private static RegularInvoice inv2CO_Y()      {return invoice(INV2_ID, DocStatus.Completed, false, true);}

	private static RegularInvoice inv2CO_N()      {return invoice(INV2_ID, DocStatus.Completed, false, false);}

	private static RegularInvoice inv2CO_Y_paid() {return invoice(INV2_ID, DocStatus.Completed, true, true);}

	private static RegularInvoice inv2CO_N_paid() {return invoice(INV2_ID, DocStatus.Completed, true, false);}

	private static RegularInvoice inv2DR_Y()      {return invoice(INV2_ID, DocStatus.Drafted, false, true);}

	private static RegularInvoice inv2RE_Y()      {return invoice(INV2_ID, DocStatus.Reversed, false, true);}

	private static RegularInvoice inv2RE_N()      {return invoice(INV2_ID, DocStatus.Reversed, false, false);}

	// -----------------------------------------------------------------------
	// Truth-table cells (~36)
	// -----------------------------------------------------------------------

	static Stream<Cell> cells()
	{
		return Stream.of(
				// ── 0 receipts (1 cell) ──────────────────────────────────────────────────────────────────────────
				// No receipts → 1 remainder row with dueAmountActual = MR_BREAK_TOTAL.
				new Cell("0R-0I: no receipts → 1 remainder Pending MR_BREAK_TOTAL",
						false, false, false, null, null,
						1, MR_BREAK_TOTAL, null, null),

				// ── 1 receipt (R1), no invoice ───────────────────────────────────────────────────────────────────
				new Cell("1R-0I: R1 present no invoice → R1=Pending dueAct=R1_VALUE + remainder",
						true, false, false, null, null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Pending, null),

				// ── 1 receipt (R1), invoice DR (treated as absent → Pending) ─────────────────────────────────────
				new Cell("1R-INV1-DR-Y: R1 + DR/Partial → R1=Pending",
						true, false, false, inv1DR_Y(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Pending, null),
				new Cell("1R-INV1-DR-N: R1 + DR/Final → R1=Pending",
						true, false, false, inv1DR_N(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Pending, null),

				// ── 1 receipt (R1), invoice CO unpaid ────────────────────────────────────────────────────────────
				new Cell("1R-INV1-CO-Y-unpaid: R1 + CO/Partial/unpaid → R1=AwaitingPay",
						true, false, false, inv1CO_Y(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Awaiting_Pay, null),
				new Cell("1R-INV1-CO-N-unpaid: R1 + CO/Final/unpaid → R1=AwaitingPay",
						true, false, false, inv1CO_N(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Awaiting_Pay, null),

				// ── 1 receipt (R1), invoice CO paid ──────────────────────────────────────────────────────────────
				new Cell("1R-INV1-CO-Y-paid: R1 + CO/Partial/paid → R1=Paid",
						true, false, false, inv1CO_Y_paid(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Paid, null),
				new Cell("1R-INV1-CO-N-paid: R1 + CO/Final/paid → R1=Paid",
						true, false, false, inv1CO_N_paid(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Paid, null),

				// ── 1 receipt (R1), invoice RE (treated as absent → Pending) ─────────────────────────────────────
				new Cell("1R-INV1-RE-Y: R1 + RE/Partial → R1=Pending",
						true, false, false, inv1RE_Y(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Pending, null),
				new Cell("1R-INV1-RE-N: R1 + RE/Final → R1=Pending",
						true, false, false, inv1RE_N(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Pending, null),

				// ── 2 receipts (over-delivery), no invoices ──────────────────────────────────────────────────────
				// R2_OVER dueAmountActual = min(37092, 16250.08) = 16250.08 (capped).
				// dueAmtRemaining → 0 → no remainder row.
				new Cell("2R-over-0I: over-delivery no invoices → 2 sub-rows Pending no remainder",
						true, true, true, null, null,
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (under-delivery), no invoices ─────────────────────────────────────────────────────
				// R2_UNDER (10000) < remaining (16250.08) → remainder dueAmountActual = 6250.08.
				new Cell("2R-under-0I: under-delivery no invoices → 2 sub-rows Pending + remainder",
						true, true, false, null, null,
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (over-delivery), INV1 for R1 only ─────────────────────────────────────────────────
				new Cell("2R-over-INV1-CO-Y: INV1 CO/Partial → R1=AwaitingPay R2=Pending",
						true, true, true, inv1CO_Y(), null,
						2, null, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Pending),
				new Cell("2R-over-INV1-CO-N: INV1 CO/Final → R1=AwaitingPay R2=Pending",
						true, true, true, inv1CO_N(), null,
						2, null, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Pending),
				new Cell("2R-over-INV1-RE-Y: INV1 RE/Partial → R1=Pending R2=Pending",
						true, true, true, inv1RE_Y(), null,
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),
				new Cell("2R-over-INV1-DR-Y: INV1 DR/Partial → R1=Pending R2=Pending",
						true, true, true, inv1DR_Y(), null,
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (over-delivery), INV2 for R2 only ─────────────────────────────────────────────────
				new Cell("2R-over-INV2-CO-Y: R1=Pending, INV2 CO/Partial → R2=AwaitingPay",
						true, true, true, null, inv2CO_Y(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Awaiting_Pay),
				new Cell("2R-over-INV2-CO-N: R1=Pending, INV2 CO/Final → R2=AwaitingPay",
						true, true, true, null, inv2CO_N(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Awaiting_Pay),
				new Cell("2R-over-INV2-RE-Y: R1=Pending, INV2 RE → R2=Pending",
						true, true, true, null, inv2RE_Y(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),
				new Cell("2R-over-INV2-DR-Y: R1=Pending, INV2 DR → R2=Pending",
						true, true, true, null, inv2DR_Y(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (over-delivery), INV1+INV2 both CO unpaid ─────────────────────────────────────────
				new Cell("2R-over-INV1-CO-Y-INV2-CO-Y: both CO Partial unpaid → both AwaitingPay",
						true, true, true, inv1CO_Y(), inv2CO_Y(),
						2, null, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Awaiting_Pay),
				new Cell("2R-over-INV1-CO-Y-INV2-CO-N: INV1 Partial / INV2 Final unpaid → both AwaitingPay",
						true, true, true, inv1CO_Y(), inv2CO_N(),
						2, null, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Awaiting_Pay),

				// ── 2 receipts (over-delivery), INV1+INV2 mixed paid states ──────────────────────────────────────
				new Cell("2R-over-INV1-CO-Y-paid-INV2-CO-N: INV1 paid + INV2 unpaid → Paid+AwaitingPay",
						true, true, true, inv1CO_Y_paid(), inv2CO_N(),
						2, null, OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Awaiting_Pay),
				new Cell("2R-over-INV1-CO-Y-paid-INV2-CO-N-paid: both paid → Paid+Paid",
						true, true, true, inv1CO_Y_paid(), inv2CO_N_paid(),
						2, null, OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Paid),
				new Cell("2R-over-canonical-INV1-CO-Y-INV2-CO-N-paid: INV1 unpaid + INV2 Final paid → AwaitingPay+Paid",
						true, true, true, inv1CO_Y(), inv2CO_N_paid(),
						2, null, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Paid),

				// ── 2 receipts (over-delivery), INV1+INV2 reversal cells ────────────────────────────────────────
				new Cell("2R-over-INV1-RE-Y-INV2-CO-Y: INV1 reversed, INV2 CO → R1=Pending R2=AwaitingPay",
						true, true, true, inv1RE_Y(), inv2CO_Y(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Awaiting_Pay),
				new Cell("2R-over-INV1-CO-Y-INV2-RE-Y: INV1 CO, INV2 reversed → R1=AwaitingPay R2=Pending",
						true, true, true, inv1CO_Y(), inv2RE_Y(),
						2, null, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Pending),
				new Cell("2R-over-INV1-RE-N-INV2-RE-N: both reversed Final → both Pending",
						true, true, true, inv1RE_N(), inv2RE_N(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),
				new Cell("2R-over-INV1-DR-Y-INV2-RE-N: INV1 DR, INV2 RE → both Pending",
						true, true, true, inv1DR_Y(), inv2RE_N(),
						2, null, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (under-delivery), INV1+INV2 both CO ──────────────────────────────────────────────
				// R2_UNDER = 10000, remainder = 6250.08 → 3 rows
				new Cell("2R-under-INV1-CO-Y-INV2-CO-N: both CO, remainder persists → AwaitingPay+AwaitingPay+remainder",
						true, true, false, inv1CO_Y(), inv2CO_N(),
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Awaiting_Pay),
				new Cell("2R-under-INV1-CO-Y-paid-INV2-CO-N-paid: both paid, remainder persists → Paid+Paid+remainder",
						true, true, false, inv1CO_Y_paid(), inv2CO_N_paid(),
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Paid),

				// ── 2 receipts (under-delivery), INV1 only ──────────────────────────────────────────────────────
				new Cell("2R-under-INV1-CO-Y-no-INV2: INV1 CO, no INV2 → AwaitingPay+Pending+remainder",
						true, true, false, inv1CO_Y(), null,
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Awaiting_Pay, OrderPayScheduleStatus.Pending),
				new Cell("2R-under-INV1-CO-Y-paid-no-INV2: INV1 paid, no INV2 → Paid+Pending+remainder",
						true, true, false, inv1CO_Y_paid(), null,
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (under-delivery), both invoices reversed ──────────────────────────────────────────
				new Cell("2R-under-INV1-RE-INV2-RE: both reversed → all Pending + remainder",
						true, true, false, inv1RE_Y(), inv2RE_Y(),
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Pending),

				// ── 2 receipts (under-delivery), INV2 only ──────────────────────────────────────────────────────
				new Cell("2R-under-no-INV1-INV2-CO-N: no INV1, INV2 Final CO → Pending+AwaitingPay+remainder",
						true, true, false, null, inv2CO_N(),
						3, REMAINDER_AFTER_R1_R2_UNDER_DUE, OrderPayScheduleStatus.Pending, OrderPayScheduleStatus.Awaiting_Pay),

				// ── 2 receipts (over-delivery), both CO Y_paid ──────────────────────────────────────────────────
				new Cell("2R-over-INV1-CO-Y-paid-INV2-CO-Y-paid: both Partial paid → Paid+Paid no remainder",
						true, true, true, inv1CO_Y_paid(), inv2CO_Y_paid(),
						2, null, OrderPayScheduleStatus.Paid, OrderPayScheduleStatus.Paid),

				// ── Edge: Pending→Paid shortcut (B4 fix) ────────────────────────────────────────────────────────
				// A sub-row can go directly Pending → Paid when its invoice is already fully paid at creation time.
				new Cell("1R-INV1-CO-Y-paid-edge: Pending→Paid shortcut preserved",
						true, false, false, inv1CO_Y_paid(), null,
						2, REMAINDER_AFTER_R1_DUE, OrderPayScheduleStatus.Paid, null)
		);
	}

	// -----------------------------------------------------------------------
	// Test infrastructure
	// -----------------------------------------------------------------------

	private OrderPayScheduleMaterialReceiptStepService service;
	private OrderPayScheduleRegularInvoiceService regularInvoiceService;
	private OrderPayScheduleService orderPayScheduleService;
	private OrderPayScheduleMaterialReceiptService receiptService;
	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		final IOrderBL orderBL = mock(IOrderBL.class);
		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IOrderLineBL.class, orderLineBL);

		// getLinesByIds → return a distinct mock I_C_OrderLine per OrderAndLineId
		when(orderBL.getLinesByIds(any())).thenAnswer(inv -> {
			final java.util.Set<OrderAndLineId> ids = inv.getArgument(0);
			final Map<OrderAndLineId, I_C_OrderLine> result = new HashMap<>();
			for (final OrderAndLineId id : ids)
			{
				result.put(id, mock(I_C_OrderLine.class));
			}
			return result;
		});

		// qty ordered = 10, qty received = 10 → ratio = 1 → receiptValue = lineGrossAmt
		when(orderLineBL.getQtyOrdered(any(I_C_OrderLine.class))).thenReturn(qty("10"));

		final MoneyService moneyService = mock(MoneyService.class);
		when(moneyService.getStdPrecision(any(CurrencyId.class))).thenReturn(CurrencyPrecision.TWO);

		regularInvoiceService = mock(OrderPayScheduleRegularInvoiceService.class);
		orderPayScheduleService = mock(OrderPayScheduleService.class);
		receiptService = mock(OrderPayScheduleMaterialReceiptService.class);

		service = new OrderPayScheduleMaterialReceiptStepService(
				moneyService,
				orderPayScheduleService,
				receiptService,
				regularInvoiceService
		);
	}

	// -----------------------------------------------------------------------
	// JU1 — parametrised property + idempotence test
	// -----------------------------------------------------------------------

	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource("cells")
	void deliveryStepsMatchTruthTable_andAreIdempotent(final Cell cell)
	{
		// Build receipts and configure stubs for this cell
		final MaterialReceiptCollection receipts = buildReceipts(cell);
		configureInvoiceStubs(cell);
		configureLineGrossAmtForCell(cell);

		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final PaymentTermBreak mrBreak = mrBreak();

		// ── First invocation ─────────────────────────────────────────────────────────────────────────
		final List<OrderPayScheduleLine> firstResult = service.computeOrderPayScheduleLines(
				receipts, order, mrBreak, schedule);

		assertRowCount(cell, firstResult);
		assertRowFields(cell, firstResult);

		// ── Idempotence: second invocation must produce identical structure ────────────────────────────
		configureInvoiceStubs(cell);
		configureLineGrossAmtForCell(cell);

		final OrderPaySchedule schedule2 = buildEmptySchedule();
		final List<OrderPayScheduleLine> secondResult = service.computeOrderPayScheduleLines(
				receipts, order, mrBreak, schedule2);

		assertThat(secondResult)
				.as("idempotence [%s]: same row count", cell.label)
				.hasSize(firstResult.size());

		for (int i = 0; i < firstResult.size(); i++)
		{
			final OrderPayScheduleLine first = firstResult.get(i);
			final OrderPayScheduleLine second = secondResult.get(i);
			assertThat(second.getInoutId())
					.as("idempotence row[%d] inoutId [%s]", i, cell.label)
					.isEqualTo(first.getInoutId());
			assertThat(second.getBaseAmount())
					.as("idempotence row[%d] baseAmount [%s]", i, cell.label)
					.isEqualTo(first.getBaseAmount());
			assertThat(second.getDueAmount())
					.as("idempotence row[%d] dueAmount [%s]", i, cell.label)
					.isEqualByComparingTo(first.getDueAmount());
			assertThat(second.getDueAmountActual())
					.as("idempotence row[%d] dueAmountActual [%s]", i, cell.label)
					.isEqualByComparingTo(first.getDueAmountActual());
			assertThat(second.getStatus())
					.as("idempotence row[%d] status [%s]", i, cell.label)
					.isEqualTo(first.getStatus());
			assertThat(second.getInvoiceId())
					.as("idempotence row[%d] invoiceId [%s]", i, cell.label)
					.isEqualTo(first.getInvoiceId());
		}
	}

	// -----------------------------------------------------------------------
	// Assertion helpers
	// -----------------------------------------------------------------------

	private static void assertRowCount(final Cell cell, final List<OrderPayScheduleLine> lines)
	{
		assertThat(lines)
				.as("cell '%s': expected %d rows", cell.label, cell.expectedRowCount)
				.hasSize(cell.expectedRowCount);
	}

	private void assertRowFields(final Cell cell, final List<OrderPayScheduleLine> lines)
	{
		// Locate row indices by inoutId
		int r1Idx = -1, r2Idx = -1, remainderIdx = -1;
		for (int i = 0; i < lines.size(); i++)
		{
			final InOutId inoutId = lines.get(i).getInoutId();
			if (InOutId.equals(inoutId, R1_ID))
			{
				r1Idx = i;
			}
			else if (InOutId.equals(inoutId, R2_ID))
			{
				r2Idx = i;
			}
			else if (inoutId == null)
			{
				remainderIdx = i;
			}
		}

		// ── R1 sub-row (I-1, AC#3) ─────────────────────────────────────────────────────────────────
		if (cell.hasR1)
		{
			assertThat(r1Idx).as("[%s] R1 sub-row must exist", cell.label).isGreaterThanOrEqualTo(0);
			final OrderPayScheduleLine r1Line = lines.get(r1Idx);
			assertThat(r1Line.getInoutId()).as("[%s] R1 inoutId", cell.label).isEqualTo(R1_ID);
			// AC#3 / I-1: BaseAmt = receipt GrandTotal; DueAmt = BaseAmt × break%
			assertThat(r1Line.getBaseAmount())
					.as("[%s] R1 baseAmount = R1_VALUE (AC#3)", cell.label)
					.isNotNull()
					.satisfies(ba -> assertThat(ba).isEqualByComparingTo(R1_VALUE));
			assertThat(r1Line.getDueAmount())
					.as("[%s] R1 dueAmount = R1_VALUE × 70% (I-1)", cell.label)
					.isEqualByComparingTo(R1_DUE_ACTUAL);
			assertThat(r1Line.getDueAmountActual())
					.as("[%s] R1 dueAmountActual = R1_VALUE × 70% (I-1)", cell.label)
					.isEqualByComparingTo(R1_DUE_ACTUAL);
			assertThat(r1Line.getStatus())
					.as("[%s] R1 status", cell.label)
					.isEqualTo(cell.expectedR1Status);
			// Invoice link only for CO/CL invoices (DR/RE are filtered to absent)
			if (cell.inv1ForR1 != null && cell.inv1ForR1.isCompletedOrClosed())
			{
				assertThat(r1Line.getInvoiceId()).as("[%s] R1 invoiceId = INV1_ID", cell.label).isEqualTo(INV1_ID);
			}
			else
			{
				assertThat(r1Line.getInvoiceId()).as("[%s] R1 invoiceId null (no CO/CL inv)", cell.label).isNull();
			}
		}

		// ── R2 sub-row (I-1, AC#3) ─────────────────────────────────────────────────────────────────
		if (cell.hasR2)
		{
			assertThat(r2Idx).as("[%s] R2 sub-row must exist", cell.label).isGreaterThanOrEqualTo(0);
			final OrderPayScheduleLine r2Line = lines.get(r2Idx);
			assertThat(r2Line.getInoutId()).as("[%s] R2 inoutId", cell.label).isEqualTo(R2_ID);
			// AC#3 / I-1: BaseAmt = receipt GrandTotal; DueAmt = BaseAmt × break%
			final Money expectedR2BaseAmt = cell.useOverDeliveryR2 ? R2_OVER_VALUE : R2_UNDER_VALUE;
			final Money expectedR2Due = cell.useOverDeliveryR2 ? R2_OVER_DUE_ACTUAL : R2_UNDER_DUE_ACTUAL;
			assertThat(r2Line.getBaseAmount())
					.as("[%s] R2 baseAmount = receipt GrandTotal (AC#3)", cell.label)
					.isNotNull()
					.satisfies(ba -> assertThat(ba).isEqualByComparingTo(expectedR2BaseAmt));
			assertThat(r2Line.getDueAmount())
					.as("[%s] R2 dueAmount = BaseAmt × 70% (I-1)", cell.label)
					.isEqualByComparingTo(expectedR2Due);
			assertThat(r2Line.getDueAmountActual())
					.as("[%s] R2 dueAmountActual = BaseAmt × 70% (I-1)", cell.label)
					.isEqualByComparingTo(expectedR2Due);
			assertThat(r2Line.getStatus())
					.as("[%s] R2 status", cell.label)
					.isEqualTo(cell.expectedR2Status);
			if (cell.inv2ForR2 != null && cell.inv2ForR2.isCompletedOrClosed())
			{
				assertThat(r2Line.getInvoiceId()).as("[%s] R2 invoiceId = INV2_ID", cell.label).isEqualTo(INV2_ID);
			}
			else
			{
				assertThat(r2Line.getInvoiceId()).as("[%s] R2 invoiceId null", cell.label).isNull();
			}
		}

		// ── Remainder row (I-4, AC#3) ───────────────────────────────────────────────────────────────
		if (cell.expectedRemainderDue != null)
		{
			assertThat(remainderIdx).as("[%s] remainder row must exist", cell.label).isGreaterThanOrEqualTo(0);
			final OrderPayScheduleLine remainder = lines.get(remainderIdx);
			assertThat(remainder.getInoutId()).as("[%s] remainder inoutId null", cell.label).isNull();
			assertThat(remainder.getStatus()).as("[%s] remainder status=Pending", cell.label).isEqualTo(OrderPayScheduleStatus.Pending);
			// I-4 / AC#3: DueAmt = BaseAmt × break%; dueAmountActual = DueAmt
			assertThat(remainder.getDueAmount())
					.as("[%s] remainder dueAmount (I-4)", cell.label)
					.isEqualByComparingTo(cell.expectedRemainderDue);
			assertThat(remainder.getDueAmountActual())
					.as("[%s] remainder dueAmountActual (I-4)", cell.label)
					.isEqualByComparingTo(cell.expectedRemainderDue);
		}
		else
		{
			assertThat(remainderIdx).as("[%s] no remainder row expected (over-delivery or exact)", cell.label).isEqualTo(-1);
		}
	}

	// -----------------------------------------------------------------------
	// Per-cell stub configuration
	// -----------------------------------------------------------------------

	private void configureInvoiceStubs(final Cell cell)
	{
		// Default: no invoice for any receipt
		when(regularInvoiceService.getByReceipt(any(), any(), any())).thenReturn(Optional.empty());

		// R1 → inv1 (filtered to CO/CL only, mimicking the production service)
		when(regularInvoiceService.getByReceipt(Mockito.argThat(r -> r != null && R1_ID.equals(r.getId())), any(), any()))
				.thenReturn(Optional.ofNullable(completedOrClosedOnly(cell.inv1ForR1)));

		// R2 → inv2
		when(regularInvoiceService.getByReceipt(Mockito.argThat(r -> r != null && R2_ID.equals(r.getId())), any(), any()))
				.thenReturn(Optional.ofNullable(completedOrClosedOnly(cell.inv2ForR2)));
	}

	/**
	 * Mimics the production filter in {@link OrderPayScheduleRegularInvoiceService}:
	 * only CO/CL invoices are returned; DR and RE are filtered to empty.
	 */
	@Nullable
	private static RegularInvoice completedOrClosedOnly(@Nullable final RegularInvoice invoice)
	{
		if (invoice == null)
		{
			return null;
		}
		return invoice.isCompletedOrClosed() ? invoice : null;
	}

	/**
	 * Configures {@link IOrderLineBL#getLineGrossAmt} to return the correct value per call order.
	 * {@link ReceiptValueCalculator} iterates receipts in order: R1 first, then R2 (if present).
	 */
	private void configureLineGrossAmtForCell(final Cell cell)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final Money r2Value = cell.useOverDeliveryR2 ? R2_OVER_VALUE : R2_UNDER_VALUE;

		if (!cell.hasR2)
		{
			// Single receipt — always R1_VALUE
			Mockito.doReturn(R1_VALUE).when(orderLineBL).getLineGrossAmt(any(I_C_OrderLine.class));
		}
		else
		{
			// Two receipts: 1st call → R1_VALUE, subsequent calls → r2Value
			final int[] callCount = { 0 };
			Mockito.doAnswer(inv -> {
				final int idx = callCount[0]++;
				return idx == 0 ? R1_VALUE : r2Value;
			}).when(orderLineBL).getLineGrossAmt(any(I_C_OrderLine.class));
		}
	}

	// -----------------------------------------------------------------------
	// Domain builders (pattern from OrderPayScheduleMaterialReceiptStepServiceTest)
	// -----------------------------------------------------------------------

	private MaterialReceiptCollection buildReceipts(final Cell cell)
	{
		final ImmutableList.Builder<MaterialReceipt> builder = ImmutableList.builder();
		if (cell.hasR1)
		{
			builder.add(buildReceipt(R1_ID, R1_LINE_ID));
		}
		if (cell.hasR2)
		{
			builder.add(buildReceipt(R2_ID, R2_LINE_ID));
		}
		return MaterialReceiptCollection.ofCollection(builder.build());
	}

	private OrderSchedulingContext buildOrder()
	{
		return OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.grandTotal(GRAND_TOTAL)
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(buildPaymentTerm())
				.build();
	}

	private PaymentTerm buildPaymentTerm()
	{
		final PaymentTermBreak lcBreak = PaymentTermBreak.builder()
				.id(LC_BREAK_ID)
				.referenceDateType(ReferenceDateType.LetterOfCreditDate)
				.percent(Percent.of("30"))
				.seqNo(SeqNo.ofInt(10))
				.offsetDays(0)
				.build();

		final PaymentTermBreak mrBreak = PaymentTermBreak.builder()
				.id(MR_BREAK_ID)
				.referenceDateType(ReferenceDateType.BillOfLadingDate)
				.percent(Percent.of("70"))
				.seqNo(SeqNo.ofInt(20))
				.offsetDays(0)
				.build();

		return PaymentTerm.builder()
				.id(PT_ID)
				.clientId(ClientId.SYSTEM)
				.orgId(de.metas.organization.OrgId.ANY)
				.value("TEST")
				.name("Test Payment Term")
				.breaks(ImmutableList.of(lcBreak, mrBreak))
				.paySchedules(ImmutableList.of())
				.build();
	}

	private PaymentTermBreak mrBreak()
	{
		return buildPaymentTerm().getBreakById(MR_BREAK_ID);
	}

	/** Builds an {@link OrderPaySchedule} with one saved LC line (Paid). MR lines empty. */
	private OrderPaySchedule buildEmptySchedule()
	{
		final PaymentTermBreak lcBreak = buildPaymentTerm().getBreakById(LC_BREAK_ID);
		final OrderPayScheduleLine lcLine = OrderPayScheduleLine.from(buildOrder(), lcBreak);
		lcLine.markSaved(OrderPayScheduleId.ofRepoId(1));
		lcLine.applyAndProcess(OrderPayScheduleLineContext.builder()
				.status(OrderPayScheduleStatus.Paid)
				.dueDate(LocalDate.of(2026, 2, 1))
				.build());
		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(lcLine));
	}

	private MaterialReceipt buildReceipt(final InOutId id, final InOutLineId lineId)
	{
		final OrderAndLineId orderLineId = OrderAndLineId.ofRepoIds(ORDER_ID.getRepoId(), lineId.getRepoId());
		return MaterialReceipt.builder()
				.id(id)
				.orderId(ORDER_ID)
				.movementDate(MOVEMENT_DATE)
				.lines(ImmutableList.of(
						MaterialReceipt.Line.builder()
								.id(lineId)
								.movementQty(qty("10"))
								.orderLineId(orderLineId)
								.build()))
				.build();
	}

	private Quantity qty(final String value)
	{
		return Quantity.of(new BigDecimal(value), uom);
	}
}
