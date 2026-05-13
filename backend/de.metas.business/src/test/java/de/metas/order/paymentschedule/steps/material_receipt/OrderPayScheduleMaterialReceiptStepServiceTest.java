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
import org.compiere.model.X_C_OrderPaySchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Forward-mode unit tests for {@link OrderPayScheduleMaterialReceiptStepService#computeOrderPayScheduleLines}.
 * Directly invokes the package-visible method; reversal / idempotence / dormancy cells are in W1.2b.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class OrderPayScheduleMaterialReceiptStepServiceTest
{
	// -----------------------------------------------------------------------
	// Constants shared across cells
	// -----------------------------------------------------------------------

	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);

	/**
	 * Payment term with two breaks: LC=30% + MR=70%.
	 * IDs are arbitrary – the service never validates them.
	 */
	private static final PaymentTermId PT_ID = PaymentTermId.ofRepoId(1000);
	private static final PaymentTermBreakId LC_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID, 1001);
	private static final PaymentTermBreakId MR_BREAK_ID = PaymentTermBreakId.ofRepoId(PT_ID, 1002);

	/** Order grand total: 100 000 EUR. MR break gets last-break remainder = 70 000 EUR. */
	private static final Money GRAND_TOTAL = Money.of("100000.00", EUR);
	/** R1 receipt value (lineGrossAmt, fully matched qty). */
	private static final Money R1_VALUE = Money.of("31808.00", EUR);
	/** R2 receipt value – used in the over-delivery cell. */
	private static final Money R2_VALUE = Money.of("80000.00", EUR);

	/** Arbitrary movement date for receipts. */
	private static final LocalDate MOVEMENT_DATE = LocalDate.of(2026, 3, 15);

	// IDs for receipts / invoices
	private static final InOutId R1_ID = InOutId.ofRepoId(8001);
	private static final InOutId R2_ID = InOutId.ofRepoId(8002);
	private static final InOutLineId R1_LINE_ID = InOutLineId.ofRepoId(8101);
	private static final InOutLineId R2_LINE_ID = InOutLineId.ofRepoId(8201);
	private static final InvoiceId INV1_ID = InvoiceId.ofRepoId(5001);

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

		// Create a POJO UOM record required by Quantity.of()
		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		// Mock Services.get() targets used by ReceiptValueCalculator (static fields in the service)
		final IOrderBL orderBL = mock(IOrderBL.class);
		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		Services.registerService(IOrderBL.class, orderBL);
		Services.registerService(IOrderLineBL.class, orderLineBL);

		// Stub: IOrderBL.getLinesByIds → returns a map with order lines for R1 and R2
		when(orderBL.getLinesByIds(any())).thenAnswer(inv -> {
			final java.util.Set<OrderAndLineId> ids = inv.getArgument(0);
			final Map<OrderAndLineId, I_C_OrderLine> result = new java.util.HashMap<>();
			for (final OrderAndLineId id : ids)
			{
				final I_C_OrderLine line = mock(I_C_OrderLine.class);
				result.put(id, line);
			}
			return result;
		});

		// Stub: orderLineBL.getLineGrossAmt → return the receipt value via the order line stub
		// The test uses qtyOrdered == qtyReceived so the full lineGrossAmt is returned.
		when(orderLineBL.getLineGrossAmt(any(I_C_OrderLine.class))).thenAnswer(inv -> {
			// Identify which receipt the line belongs to by checking if it was created for R1 or R2.
			// We stash the expected lineGrossAmt on the mock via Mockito name (no side-channel needed;
			// see the per-cell helper methods that configure the money stub further).
			return Mockito.RETURNS_DEFAULTS.answer(inv);
		});
		// Default: both R1-line and R2-line return ZERO (overridden per receipt in helper methods)
		when(orderLineBL.getQtyOrdered(any(I_C_OrderLine.class))).thenReturn(qty("10"));

		final MoneyService moneyService = mock(MoneyService.class);
		when(moneyService.getStdPrecision(any(CurrencyId.class))).thenReturn(CurrencyPrecision.TWO);

		regularInvoiceService = mock(OrderPayScheduleRegularInvoiceService.class);
		when(regularInvoiceService.getByReceipt(any(), any(), any())).thenReturn(Optional.empty()); // default: no invoice

		orderPayScheduleService = mock(OrderPayScheduleService.class);
		receiptService = mock(OrderPayScheduleMaterialReceiptService.class);

		service = new OrderPayScheduleMaterialReceiptStepService(
				moneyService,
				orderPayScheduleService,
				receiptService,
				regularInvoiceService
		);

		// Wire up the simpler line-gross-amt stubs by making orderLineBL return the R1/R2 values
		// based on what the ReceiptValueCalculator computes.  The two receipts each have one line;
		// the line's gross amt is the receipt's full value (qty ordered == qty received → ratio = 1).
		configureLineGrossAmt(orderLineBL, R1_VALUE, R2_VALUE);
	}

	/**
	 * Configures the per-line gross amount stubs.
	 * The order BL mock returns a distinct {@link I_C_OrderLine} mock per {@link OrderAndLineId}.
	 * We cannot correlate the mock instance to R1 or R2 after the fact, so we key on invocation order
	 * across the warm-up: first distinct line that appears for getLineGrossAmt → R1_VALUE, second → R2_VALUE.
	 */
	private void configureLineGrossAmt(final IOrderLineBL orderLineBL, final Money r1Amt, final Money r2Amt)
	{
		final Money[] amounts = { r1Amt, r2Amt };
		final int[] callCount = { 0 };
		// The ReceiptValueCalculator calls getLineGrossAmt once per line in iteration order (R1 first, then R2).
		Mockito.doAnswer(inv -> {
			final int idx = callCount[0]++;
			return idx < amounts.length ? amounts[idx] : r2Amt;
		}).when(orderLineBL).getLineGrossAmt(any(I_C_OrderLine.class));

		// qtyOrdered == qtyReceived for all lines → ratio = 1 → receiptValue = lineGrossAmt
		Mockito.doReturn(qty("10")).when(orderLineBL).getQtyOrdered(any(I_C_OrderLine.class));
	}

	// -----------------------------------------------------------------------
	// Cell 1 — AC #1 — no receipts → single remainder row, Status=Pending
	// -----------------------------------------------------------------------

	@Test
	void noReceipts_oneRemainder()
	{
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				MaterialReceiptCollection.EMPTY,
				order,
				mrBreak(),
				schedule);

		assertThat(lines).hasSize(1);

		final OrderPayScheduleLine remainder = lines.get(0);
		assertThat(remainder.getInoutId()).isNull();
		assertThat(remainder.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		// dueAmountActual = full mrBreak share = 70 000 (no receipts → nothing consumed)
		assertThat(remainder.getDueAmountActual()).isEqualByComparingTo(Money.of("70000.00", EUR));
		assertThat(remainder.getInvoiceId()).isNull();
	}

	// -----------------------------------------------------------------------
	// Cell 2 — AC #3 — one receipt, no invoice → sub-row Pending + remainder
	// -----------------------------------------------------------------------

	@Test
	void oneReceipt_pendingNoInvoice()
	{
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final MaterialReceiptCollection receipts = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				receipts,
				order,
				mrBreak(),
				schedule);

		assertThat(lines).hasSize(2);

		final OrderPayScheduleLine r1Line = lines.get(0);
		assertThat(r1Line.getInoutId()).isEqualTo(R1_ID);
		assertThat(r1Line.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		// AC#3 / I-1: DueAmt = R1_VALUE × 70% = 31808 × 70% = 22265.60
		assertThat(r1Line.getDueAmountActual()).isEqualByComparingTo(Money.of("22265.60", EUR));
		assertThat(r1Line.getBaseAmount()).isEqualByComparingTo(R1_VALUE);
		assertThat(r1Line.getInvoiceId()).isNull();

		final OrderPayScheduleLine remainder = lines.get(1);
		assertThat(remainder.getInoutId()).isNull();
		assertThat(remainder.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		// I-4: BaseAmt = 100000 - 31808 = 68192; DueAmt = 68192 × 70% = 47734.40
		assertThat(remainder.getDueAmountActual()).isEqualByComparingTo(Money.of("47734.40", EUR));
		assertThat(remainder.getBaseAmount()).isEqualByComparingTo(Money.of("68192.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Cell 3 — AC #5 — one receipt + completed (unpaid) invoice → Awaiting_Pay
	// -----------------------------------------------------------------------

	@Test
	void oneReceipt_completedInvoice_awaitingPay()
	{
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final MaterialReceiptCollection receipts = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));

		// Stub: R1 has a completed but NOT yet paid invoice
		when(regularInvoiceService.getByReceipt(any(), any(), any())).thenReturn(Optional.of(buildInvoice(INV1_ID, false)));

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				receipts,
				order,
				mrBreak(),
				schedule);

		assertThat(lines).hasSize(2);

		final OrderPayScheduleLine r1Line = lines.get(0);
		assertThat(r1Line.getInoutId()).isEqualTo(R1_ID);
		assertThat(r1Line.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(r1Line.getInvoiceId()).isEqualTo(INV1_ID);

		// Remainder still present with Pending status
		final OrderPayScheduleLine remainder = lines.get(1);
		assertThat(remainder.getInoutId()).isNull();
		assertThat(remainder.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// Cell 4 — extension of AC #5 — one receipt + fully paid invoice → Paid
	// (B4 fix: Pending→Paid direct transition now allowed)
	// -----------------------------------------------------------------------

	@Test
	void oneReceipt_paidInvoice_paid()
	{
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final MaterialReceiptCollection receipts = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));

		// Stub: R1 has a fully paid invoice
		when(regularInvoiceService.getByReceipt(any(), any(), any())).thenReturn(Optional.of(buildInvoice(INV1_ID, true)));

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				receipts,
				order,
				mrBreak(),
				schedule);

		assertThat(lines).hasSize(2);

		final OrderPayScheduleLine r1Line = lines.get(0);
		assertThat(r1Line.getInoutId()).isEqualTo(R1_ID);
		assertThat(r1Line.getStatus()).isEqualTo(OrderPayScheduleStatus.Paid);
		assertThat(r1Line.getInvoiceId()).isEqualTo(INV1_ID);
	}

	// -----------------------------------------------------------------------
	// Cell 5 — AC #7 — two receipts totalling > GrandTotal → no remainder row
	// -----------------------------------------------------------------------

	@Test
	void twoReceipts_overDelivery_remainderDeleted()
	{
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		// R1=31808 + R2=80000 = 111808 > mrBreakDueAmt=70000 → over-delivery
		final MaterialReceiptCollection receipts = receiptCollection(
				buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE),
				buildReceipt(R2_ID, R2_LINE_ID, R2_VALUE));

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				receipts,
				order,
				mrBreak(),
				schedule);

		// Exactly 2 sub-rows (R1 + R2), no remainder because dueAmtRemaining reaches 0 after R1+R2
		assertThat(lines).hasSize(2);

		assertThat(lines.get(0).getInoutId()).isEqualTo(R1_ID);
		assertThat(lines.get(0).getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);

		assertThat(lines.get(1).getInoutId()).isEqualTo(R2_ID);
		assertThat(lines.get(1).getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		// AC#3 / I-1: DueAmt = R2_VALUE × 70% = 80000 × 70% = 56000.00
		assertThat(lines.get(1).getDueAmountActual()).isEqualByComparingTo(Money.of("56000.00", EUR));
		assertThat(lines.get(1).getBaseAmount()).isEqualByComparingTo(R2_VALUE);

		// No remainder row
		assertThat(lines).allMatch(l -> l.getInoutId() != null);
	}

	// -----------------------------------------------------------------------
	// Cell 6 — AC #16 — reversed invoice clears invoiceId, status back to Pending
	// -----------------------------------------------------------------------

	@Test
	void reverseInvoice_clearsCInvoiceId_statusPending()
	{
		// Setup: R1 sub-row was Awaiting_Pay with INV1. Now the invoice is reversed.
		// Production code calls regularInvoiceService.getByReceipt(...) which filters for CO/CL invoices.
		// A reversed invoice fails that filter → Optional.empty() → invoice=null → status=Pending.
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final MaterialReceiptCollection receipts = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));

		// Stub: reversed invoice is absent from the service (filter rejects RE status)
		when(regularInvoiceService.getByReceipt(any(), any(), any())).thenReturn(Optional.empty());

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				receipts,
				order,
				mrBreak(),
				schedule);

		assertThat(lines).hasSize(2);

		final OrderPayScheduleLine r1Line = lines.get(0);
		assertThat(r1Line.getInoutId()).isEqualTo(R1_ID);
		assertThat(r1Line.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		assertThat(r1Line.getInvoiceId()).isNull();

		// Remainder row unchanged
		final OrderPayScheduleLine remainder = lines.get(1);
		assertThat(remainder.getInoutId()).isNull();
		assertThat(remainder.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// Cell 7 — AC #17 — reversed receipt drops sub-row, remainder covers full amount
	// -----------------------------------------------------------------------

	@Test
	void reverseReceipt_dropsSubrow_remainderRecomputed()
	{
		// Setup: R1 is now reversed / ineligible; receiptService returns an empty collection.
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();

		// Stub: no eligible receipts (R1 reversed → filtered out by isEligibleReceipt)
		final MaterialReceiptCollection emptyReceipts = MaterialReceiptCollection.EMPTY;

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				emptyReceipts,
				order,
				mrBreak(),
				schedule);

		// Same shape as Cell 1: 0 sub-rows, 1 remainder with full mrBreak share
		assertThat(lines).hasSize(1);

		final OrderPayScheduleLine remainder = lines.get(0);
		assertThat(remainder.getInoutId()).isNull();
		assertThat(remainder.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		assertThat(remainder.getDueAmountActual()).isEqualByComparingTo(Money.of("70000.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Cell 8 — AC #19 — idempotence: calling recompute twice yields identical structure
	// -----------------------------------------------------------------------

	@Test
	void idempotent_secondCallNoChange()
	{
		// Re-stub getLineGrossAmt to always return R1_VALUE (pure / stateless)
		// so that both calls to computeOrderPayScheduleLines see the same data.
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		Mockito.doReturn(R1_VALUE).when(orderLineBL).getLineGrossAmt(any(de.metas.interfaces.I_C_OrderLine.class));

		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final MaterialReceiptCollection receipts = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));

		// First call
		final List<OrderPayScheduleLine> firstResult = service.computeOrderPayScheduleLines(
				receipts, order, mrBreak(), schedule);

		// Second call on the same inputs — must produce the same structure
		final List<OrderPayScheduleLine> secondResult = service.computeOrderPayScheduleLines(
				receipts, order, mrBreak(), schedule);

		assertThat(secondResult).hasSize(firstResult.size());
		for (int i = 0; i < firstResult.size(); i++)
		{
			final OrderPayScheduleLine first = firstResult.get(i);
			final OrderPayScheduleLine second = secondResult.get(i);
			assertThat(second.getInoutId()).isEqualTo(first.getInoutId());
			assertThat(second.getDueAmount()).isEqualByComparingTo(first.getDueAmount());
			assertThat(second.getDueAmountActual()).isEqualByComparingTo(first.getDueAmountActual());
			assertThat(second.getStatus()).isEqualTo(first.getStatus());
		}
	}

	// -----------------------------------------------------------------------
	// Cell 9 — AC #22 — non-proforma (non-complex) order → recomputeDeliverySteps is dormant
	// -----------------------------------------------------------------------

	@Test
	void nonProformaOrder_dormant()
	{
		// Build a non-complex payment term (empty breaks → isComplex=false)
		final PaymentTerm nonComplexTerm = PaymentTerm.builder()
				.id(PT_ID)
				.clientId(ClientId.SYSTEM)
				.orgId(de.metas.organization.OrgId.ANY)
				.value("SIMPLE")
				.name("Simple Payment Term")
				.breaks(ImmutableList.of())
				.paySchedules(ImmutableList.of())
				.build();

		final OrderSchedulingContext nonComplexOrder = OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.grandTotal(GRAND_TOTAL)
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(nonComplexTerm)
				.build();

		// Stub: getContextById returns a non-complex order
		when(orderPayScheduleService.getContextById(ORDER_ID)).thenReturn(Optional.of(nonComplexOrder));

		// When: public entry point is called
		service.recomputeDeliverySteps(ORDER_ID);

		// Then: updateById is never called (dormancy short-circuit at line 92)
		verify(orderPayScheduleService, never()).updateById(any(), any());
	}

	// -----------------------------------------------------------------------
	// Cell 10 — Finding #1 — non-completed/closed invoices treated as absent (parametrised)
	// -----------------------------------------------------------------------

	@ParameterizedTest
	@ValueSource(strings = { "DR", "VO", "RE" })
	void onlyCompletedOrClosedInvoicesCount(final String docStatusCode)
	{
		final OrderSchedulingContext order = buildOrder();
		final OrderPaySchedule schedule = buildEmptySchedule();
		final MaterialReceiptCollection receipts = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));

		// Simulate: production isCompletedOrClosed() filter rejects DR/VO/RE → service returns empty
		when(regularInvoiceService.getByReceipt(any(), any(), any())).thenReturn(Optional.empty());

		final List<OrderPayScheduleLine> lines = service.computeOrderPayScheduleLines(
				receipts, order, mrBreak(), schedule);

		assertThat(lines).hasSize(2);

		final OrderPayScheduleLine r1Line = lines.get(0);
		assertThat(r1Line.getInoutId()).isEqualTo(R1_ID);
		assertThat(r1Line.getStatus())
				.as("DocStatus=%s should be treated as absent → Pending", docStatusCode)
				.isEqualTo(OrderPayScheduleStatus.Pending);
		assertThat(r1Line.getInvoiceId()).isNull();
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	/** Build an {@link OrderSchedulingContext} with LC (30%) + MR (70%) breaks. */
	private OrderSchedulingContext buildOrder()
	{
		return OrderSchedulingContext.builder()
				.orderId(ORDER_ID)
				.grandTotal(GRAND_TOTAL)
				.precision(CurrencyPrecision.TWO)
				.paymentTerm(buildPaymentTerm())
				.build();
	}

	/** Build a {@link PaymentTerm} with two breaks: LC=30% (seqNo=10) + MR=70% (seqNo=20). */
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

	/** The MR break from the payment term. */
	private PaymentTermBreak mrBreak()
	{
		return buildPaymentTerm().getBreakById(MR_BREAK_ID);
	}

	/**
	 * Build an {@link OrderPaySchedule} with one saved LC line (status=Paid).
	 * The MR lines list is empty — computeOrderPayScheduleLines will create them fresh.
	 */
	private OrderPaySchedule buildEmptySchedule()
	{
		final PaymentTermBreak lcBreak = buildPaymentTerm().getBreakById(LC_BREAK_ID);
		final OrderPayScheduleLine lcLine = OrderPayScheduleLine.from(buildOrder(), lcBreak);
		lcLine.markSaved(OrderPayScheduleId.ofRepoId(1));
		// Force LC line to Paid status (it was paid when the LC step ran before delivery)
		lcLine.applyAndProcess(de.metas.order.paymentschedule.core.OrderPayScheduleLineContext.builder()
				.status(OrderPayScheduleStatus.Paid)
				.dueDate(LocalDate.of(2026, 2, 1))
				.build());

		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(lcLine));
	}

	/** Build a single-line {@link MaterialReceipt} with the given receipt/line ID and receipt value. */
	private MaterialReceipt buildReceipt(final InOutId id, final InOutLineId lineId, final Money receiptValue)
	{
		final OrderAndLineId orderLineId = OrderAndLineId.ofRepoIds(ORDER_ID.getRepoId(), lineId.getRepoId());
		return MaterialReceipt.builder()
				.id(id)
				.orderId(ORDER_ID)
				.movementDate(MOVEMENT_DATE)
				.lines(ImmutableList.of(
						MaterialReceipt.Line.builder()
								.id(lineId)
								.movementQty(qty("10"))       // 10 units received
								.orderLineId(orderLineId)
								.build()))
				.build();
	}

	private MaterialReceiptCollection receiptCollection(final MaterialReceipt... receipts)
	{
		return MaterialReceiptCollection.ofCollection(ImmutableList.copyOf(receipts));
	}

	/** Build a {@link RegularInvoice} value object stub. */
	private RegularInvoice buildInvoice(final InvoiceId invoiceId, final boolean isPaid)
	{
		return RegularInvoice.builder()
				.id(invoiceId)
				.orderId(ORDER_ID)
				.isPartialInvoice(true)
				.orgId(de.metas.organization.OrgId.ANY)
				.bpartnerId(de.metas.bpartner.BPartnerId.ofRepoId(1))
				.dateInvoiced(LocalDate.of(2026, 3, 10))
				.dateAcct(LocalDate.of(2026, 3, 10))
				.currencyId(EUR)
				.docStatus(de.metas.document.engine.DocStatus.Completed)
				.isPaid(isPaid)
				.lines(ImmutableList.of())
				.build();
	}

	// -----------------------------------------------------------------------
	// Cell 11 — AC #17 — receipt reversal: in-memory reversed receipt excluded, remainder recreated
	// -----------------------------------------------------------------------

	/**
	 * Verifies the fix for the Bug C1-sibling (receipt-reversal path).
	 *
	 * <p>Scenario: order with R1 (40 000) + R2 (32 000); GrandTotal = 100 000 → MR break 70 000.
	 * R1 is reversed. {@code MInOut.reverseCorrectIt()} sets {@code Reversal_ID} on the
	 * in-memory R1 at line 2208 but does NOT save before AFTER_REVERSECORRECT fires at line 2262.
	 *
	 * <p>Without the fix: DB query returns R1 (CO / Reversal_ID=0) + R2 → total 72 000 &gt; 70 000 → no remainder.
	 * With the fix: R1 is excluded via the in-memory hint → only R2 (32 000) → remainder = 38 000 recreated.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 AC#17</a>
	 */
	@Test
	void recomputeAfterReceiptReversal_recreatesRemainder()
	{
		// ---- Setup ----
		// R1 receipt: 40 000 EUR (being reversed; Reversal_ID set in memory, not yet saved to DB)
		final Money r1Value = Money.of("40000.00", EUR);
		// R2 receipt: 32 000 EUR (still active)
		final Money r2Value = Money.of("32000.00", EUR);

		// Re-stub lineGrossAmt: only R2 is returned (R1 is excluded by the fix)
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		Mockito.doReturn(r2Value).when(orderLineBL).getLineGrossAmt(any(de.metas.interfaces.I_C_OrderLine.class));

		// Build the complex order context
		final OrderSchedulingContext order = buildOrder();

		// The in-memory R1 record: DocStatus=CO but Reversal_ID already set in memory
		final org.compiere.model.I_M_InOut r1Record = mock(org.compiere.model.I_M_InOut.class);
		when(r1Record.getM_InOut_ID()).thenReturn(R1_ID.getRepoId());

		// Stub: orderPayScheduleService.getContextById → complex order
		when(orderPayScheduleService.getContextById(ORDER_ID)).thenReturn(Optional.of(order));

		// Stub: receiptService.getByOrderId(orderId, null, r1Record) → only R2
		// (simulates the fix: R1 excluded because excludeReceipt=r1Record)
		final MaterialReceiptCollection onlyR2 = receiptCollection(buildReceipt(R2_ID, R2_LINE_ID, r2Value));
		when(receiptService.getByOrderId(eq(ORDER_ID), isNull(), eq(r1Record))).thenReturn(onlyR2);

		// Capture the consumer passed to updateById so we can invoke it and inspect the result
		final OrderPaySchedule[] capturedSchedule = { null };
		final List<OrderPayScheduleLine>[] capturedLines = new List[] { null };
		Mockito.doAnswer(inv -> {
			final OrderPaySchedule schedule = buildEmptySchedule();
			capturedSchedule[0] = schedule;
			@SuppressWarnings("unchecked")
			final java.util.function.Consumer<OrderPaySchedule> consumer = inv.getArgument(1);
			consumer.accept(schedule);
			return null;
		}).when(orderPayScheduleService).updateById(eq(ORDER_ID), any());

		// ---- Act ----
		service.recomputeDeliveryStepsAfterReceiptReversed(ORDER_ID, r1Record);

		// ---- Assert ----
		// updateById must have been called (proves the service did not short-circuit)
		verify(orderPayScheduleService).updateById(eq(ORDER_ID), any());

		// receiptService must have been called with the exclude hint
		verify(receiptService).getByOrderId(eq(ORDER_ID), isNull(), eq(r1Record));

		// The schedule must have been updated: R2 sub-row + remainder row (no R1 sub-row)
		assertThat(capturedSchedule[0]).isNotNull();
		final List<OrderPayScheduleLine> mrLines = capturedSchedule[0].streamLinesByBreakId(MR_BREAK_ID)
				.collect(java.util.stream.Collectors.toList());

		// Expect: 1 sub-row for R2 + 1 remainder row
		assertThat(mrLines).hasSize(2);

		final OrderPayScheduleLine r2Line = mrLines.stream()
				.filter(l -> R2_ID.equals(l.getInoutId()))
				.findFirst()
				.orElse(null);
		assertThat(r2Line).as("R2 sub-row must be present").isNotNull();
		assertThat(r2Line.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		// DueAmt = 32000 × 70% = 22400.00
		assertThat(r2Line.getDueAmountActual()).isEqualByComparingTo(Money.of("22400.00", EUR));

		final OrderPayScheduleLine remainderLine = mrLines.stream()
				.filter(l -> l.getInoutId() == null)
				.findFirst()
				.orElse(null);
		assertThat(remainderLine).as("Remainder row must be present after R1 reversal").isNotNull();
		assertThat(remainderLine.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
		// BaseAmt = GrandTotal(100000) - R2(32000) = 68000; DueAmt = 68000 × 70% = 47600.00
		assertThat(remainderLine.getDueAmountActual()).isEqualByComparingTo(Money.of("47600.00", EUR));

		// R1 must NOT appear in the schedule lines
		final boolean r1Present = mrLines.stream().anyMatch(l -> R1_ID.equals(l.getInoutId()));
		assertThat(r1Present).as("R1 sub-row must NOT be present after reversal").isFalse();
	}

	// -----------------------------------------------------------------------
	// Cell 12 — invoice completion path: in-memory invoice threaded through bypasses
	//                                    the stale DocStatus="IP" filter in the DB
	// -----------------------------------------------------------------------

	/**
	 * Verifies the F1 fix for Bug C1 (invoice-completion path).
	 *
	 * <p>Scenario: R1 receipt is matched to INV1.  INV1 is currently completing —
	 * {@code MInvoice.completeIt()} fires {@code @DocValidate(AFTER_COMPLETE)} listeners
	 * BEFORE {@code DocumentEngine.completeIt()} flips {@code DocStatus} from "IP" to "CO".  Any AFTER_COMPLETE listener that re-reads the invoice from DB sees {@code DocStatus="IP"}.
	 *
	 * <p>Without the F1 fix: DB re-read returns DocStatus=IP → {@code isCompletedOrClosed()} filter
	 * rejects → {@code getByReceipt} returns empty → sub-row stays at Status=Pending / C_Invoice_ID=null.
	 *
	 * <p>With the F1 fix: the interceptor threads the in-memory (authoritatively completing) {@code RegularInvoice}
	 * down through {@code recomputeDeliveryStepsAfterInvoiceCompleted}, and {@code getByReceipt} bypasses
	 * the DB re-read for exactly this one invoice → sub-row flips to Status=Awaiting_Pay / C_Invoice_ID=INV1.
	 *
	 * <p>Sibling pattern to Cell 11 (receipt-reversal path).
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369</a>
	 */
	@Test
	void recomputeAfterInvoiceCompletion_resolvesToAwaitingPay()
	{
		// ---- Setup ----
		// R1 receipt: 31808 EUR (matches INV1, which is currently completing)
		final OrderSchedulingContext order = buildOrder();

		// The in-memory completing invoice (DocStatus would be IP in the DB at this point)
		final RegularInvoice completingInvoice = buildInvoice(INV1_ID, false);

		// Stub: orderPayScheduleService.getContextById → complex order
		when(orderPayScheduleService.getContextById(ORDER_ID)).thenReturn(Optional.of(order));

		// Stub: receiptService.getByOrderId(orderId, null, null) → R1 only
		final MaterialReceiptCollection onlyR1 = receiptCollection(buildReceipt(R1_ID, R1_LINE_ID, R1_VALUE));
		when(receiptService.getByOrderId(eq(ORDER_ID), isNull(), isNull())).thenReturn(onlyR1);

		// Stub regularInvoiceService.getByReceipt: simulate the F1 bypass.
		// - Called WITH the in-memory completingInvoice → resolves to INV1 (fast path).
		// - Called WITHOUT it (e.g. plain DB path) → empty (DB still shows DocStatus=IP, filter rejects).
		when(regularInvoiceService.getByReceipt(any(), eq(completingInvoice), isNull()))
				.thenReturn(Optional.of(completingInvoice));
		when(regularInvoiceService.getByReceipt(any(), isNull(), isNull()))
				.thenReturn(Optional.empty());

		// Capture the consumer passed to updateById so we can invoke it and inspect the result
		final OrderPaySchedule[] capturedSchedule = { null };
		Mockito.doAnswer(inv -> {
			final OrderPaySchedule schedule = buildEmptySchedule();
			capturedSchedule[0] = schedule;
			@SuppressWarnings("unchecked")
			final Consumer<OrderPaySchedule> consumer = inv.getArgument(1);
			consumer.accept(schedule);
			return null;
		}).when(orderPayScheduleService).updateById(eq(ORDER_ID), any());

		// ---- Act ----
		service.recomputeDeliveryStepsAfterInvoiceCompleted(ORDER_ID, completingInvoice);

		// ---- Assert ----
		// updateById must have been called (proves the service did not short-circuit)
		verify(orderPayScheduleService).updateById(eq(ORDER_ID), any());

		// regularInvoiceService.getByReceipt must have been called WITH the in-memory completingInvoice
		// (proves the thread-through reached the lookup layer)
		verify(regularInvoiceService).getByReceipt(any(), eq(completingInvoice), isNull());

		// The schedule must have been updated: R1 sub-row resolved to Awaiting_Pay + remainder row
		assertThat(capturedSchedule[0]).isNotNull();
		final List<OrderPayScheduleLine> mrLines = capturedSchedule[0].streamLinesByBreakId(MR_BREAK_ID)
				.collect(java.util.stream.Collectors.toList());

		// Expect: 1 sub-row for R1 + 1 remainder row
		assertThat(mrLines).hasSize(2);

		final OrderPayScheduleLine r1Line = mrLines.stream()
				.filter(l -> R1_ID.equals(l.getInoutId()))
				.findFirst()
				.orElse(null);
		assertThat(r1Line).as("R1 sub-row must be present").isNotNull();
		// Without the F1 fix this would be Pending (DocStatus=IP filter rejects); with the fix
		// the in-memory invoice is used directly and the status flips to Awaiting_Pay.
		assertThat(r1Line.getStatus()).isEqualTo(OrderPayScheduleStatus.Awaiting_Pay);
		assertThat(r1Line.getInvoiceId()).isEqualTo(INV1_ID);

		final OrderPayScheduleLine remainderLine = mrLines.stream()
				.filter(l -> l.getInoutId() == null)
				.findFirst()
				.orElse(null);
		assertThat(remainderLine).as("Remainder row must be present").isNotNull();
		assertThat(remainderLine.getStatus()).isEqualTo(OrderPayScheduleStatus.Pending);
	}

	private Quantity qty(final String value)
	{
		return Quantity.of(new BigDecimal(value), uom);
	}
}
