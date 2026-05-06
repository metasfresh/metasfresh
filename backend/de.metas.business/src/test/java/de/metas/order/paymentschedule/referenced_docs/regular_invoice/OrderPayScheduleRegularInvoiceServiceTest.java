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

package de.metas.order.paymentschedule.referenced_docs.regular_invoice;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationBL;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvCollection;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.repository.OrderPayScheduleRepository;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceiptCollection;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.order.paymentschedule.referenced_docs.prepayment.OrderPaySchedulePrepaymentService;
import de.metas.order.paymentschedule.referenced_docs.prepayment.Prepayment;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.OrderPayScheduleProformaService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.ITaxBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.organization.ClientAndOrgId.SYSTEM;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link OrderPayScheduleRegularInvoiceService#computeAmountToAllocate}
 * (directly invokes package-visible method).
 *
 * <p>Covers allocation rules: Partial (proportional vs cap), Final (consumes remaining),
 * AP-sign negation, and cascade live-read from {@link OrderPaySchedulePrepaymentService}.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class OrderPayScheduleRegularInvoiceServiceTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);
	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(5001);
	private static final InvoiceId INV1_ID = InvoiceId.ofRepoId(5010);
	private static final InvoiceId INV2_ID = InvoiceId.ofRepoId(5020);
	private static final InvoiceId INV3_ID = InvoiceId.ofRepoId(5030);
	private static final InvoiceLineId INVOICE_LINE_ID = InvoiceLineId.ofRepoId(6001);
	private static final PaymentId PAYMENT_ID = PaymentId.ofRepoId(7001);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1);
	private static final InOutId INOUT_ID = InOutId.ofRepoId(8001);
	private static final InOutAndLineId INOUT_LINE_ID = InOutAndLineId.ofRepoId(8001, 8002);
	private static final Percent LC_30 = Percent.of(30);

	// Constructor-injected mocks
	private OrderPayScheduleRepository payScheduleRepository;
	private OrderPaySchedulePrepaymentService prepaymentService;
	private MatchInvoiceRepository matchInvoiceRepository;
	private OrderPayScheduleMaterialReceiptService materialReceiptService;
	private MoneyService moneyService;
	private OrderPayScheduleProformaService proformaService;

	// UOM record for Quantity construction (needs AdempiereTestHelper)
	private I_C_UOM uom;

	private OrderPayScheduleRegularInvoiceService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register Services.get() mocks for static service fields in the service under test
		Services.registerService(IInvoiceBL.class, mock(IInvoiceBL.class));
		Services.registerService(IInvoiceLineBL.class, mock(IInvoiceLineBL.class));
		Services.registerService(IInOutDAO.class, mock(IInOutDAO.class));
		Services.registerService(IAllocationBL.class, mock(IAllocationBL.class));
		Services.registerService(ITaxBL.class, mock(ITaxBL.class));

		// Create a POJO UOM record for Quantity construction
		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		// Constructor-injected mocks
		payScheduleRepository = mock(OrderPayScheduleRepository.class);
		prepaymentService = mock(OrderPaySchedulePrepaymentService.class);
		matchInvoiceRepository = mock(MatchInvoiceRepository.class);
		materialReceiptService = mock(OrderPayScheduleMaterialReceiptService.class);
		moneyService = mock(MoneyService.class);
		proformaService = mock(OrderPayScheduleProformaService.class);

		service = new OrderPayScheduleRegularInvoiceService(
				payScheduleRepository,
				proformaService,
				prepaymentService,
				matchInvoiceRepository,
				materialReceiptService,
				moneyService
		);

		// Default stubs valid for all tests
		when(moneyService.getStdPrecision(any(CurrencyId.class))).thenReturn(CurrencyPrecision.TWO);
		when(payScheduleRepository.getByOrderId(any(OrderId.class))).thenReturn(Optional.of(buildLcSchedule(LC_30)));
	}

	// -----------------------------------------------------------------------
	// Cell 1: AC #4 + AC #12 — Partial, proportional > remainingPrepay → cap
	// receipt × LC% = 30000  >  remainingPrepay = 11053.92  → returns 11053.92 (AP neg)
	// -----------------------------------------------------------------------

	@Test
	void partialAllocCappedByRemainingPrepay()
	{
		// receiptValue = 100000.00; proportional = 100000 × 30% = 30000.00
		stubReceiptValue(money("100000.00"));

		final Money remainingPrepay = money("11053.92");
		final Prepayment prepayment = buildPrepayment();
		when(prepaymentService.getAvailableAmount(prepayment)).thenReturn(remainingPrepay);

		final RegularInvoice invoice = buildInvoice(/*isPartial*/true, money("100000.00"));
		final Money result = computeAmountToAllocate(invoice, prepayment);

		// proportional (30000) > remainingPrepay (11053.92) → allocAmt = 11053.92, negated for AP
		assertThat(result.abs()).isEqualByComparingTo(money("11053.92"));
		assertThat(result.signum()).isLessThan(0); // AP sign: negative
	}

	// -----------------------------------------------------------------------
	// Cell 2: AC #4 happy path — Partial, proportional < remainingPrepay → proportional
	// receipt × LC% = 9542.40  <  remainingPrepay = 20596.32  → returns 9542.40
	// -----------------------------------------------------------------------

	@Test
	void partialAllocBelowCap()
	{
		// receiptValue = 31808.00; proportional = 31808 × 30% = 9542.40
		stubReceiptValue(money("31808.00"));

		final Money remainingPrepay = money("20596.32");
		final Prepayment prepayment = buildPrepayment();
		when(prepaymentService.getAvailableAmount(prepayment)).thenReturn(remainingPrepay);

		final RegularInvoice invoice = buildInvoice(/*isPartial*/true, money("31808.00"));
		final Money result = computeAmountToAllocate(invoice, prepayment);

		// proportional (9542.40) < remainingPrepay (20596.32) → allocAmt = 9542.40, negated for AP
		assertThat(result.abs()).isEqualByComparingTo(money("9542.40"));
		assertThat(result.signum()).isLessThan(0); // AP sign: negative
	}

	// -----------------------------------------------------------------------
	// Cell 3: AC #8 + AC #10 — Final invoice consumes all remaining prepay
	// regardless of receipt × LC%; remainingPrepay = 11053.92 → returns 11053.92
	// -----------------------------------------------------------------------

	@Test
	void finalConsumesRemainingPrepay()
	{
		// receipt value doesn't matter for the Final branch; stub with any non-zero value
		stubReceiptValue(money("50000.00"));

		final Money remainingPrepay = money("11053.92");
		final Prepayment prepayment = buildPrepayment();
		when(prepaymentService.getAvailableAmount(prepayment)).thenReturn(remainingPrepay);

		final RegularInvoice invoice = buildInvoice(/*isPartial*/false, money("50000.00"));
		final Money result = computeAmountToAllocate(invoice, prepayment);

		// Final branch: allocAmt = remainingPrepay = 11053.92, negated for AP
		assertThat(result.abs()).isEqualByComparingTo(money("11053.92"));
		assertThat(result.signum()).isLessThan(0); // AP sign: negative
	}

	// -----------------------------------------------------------------------
	// Cell 4: GAP §3 row A — AP sign negation
	// computeAmountToAllocate always negates for AP; result must be negative
	// -----------------------------------------------------------------------

	@Test
	void apSignNegation()
	{
		stubReceiptValue(money("100000.00"));

		final Money remainingPrepay = money("30000.00");
		final Prepayment prepayment = buildPrepayment();
		when(prepaymentService.getAvailableAmount(prepayment)).thenReturn(remainingPrepay);

		// Partial invoice: proportional = 100000 × 30% = 30000 ≤ remainingPrepay → allocAmt = 30000
		final RegularInvoice invoice = buildInvoice(/*isPartial*/true, money("100000.00"));
		final Money result = computeAmountToAllocate(invoice, prepayment);

		// C_AllocationLine.Amount convention: AP = negative
		assertThat(result.signum()).as("AP allocation amount must be negative").isLessThan(0);
	}

	// -----------------------------------------------------------------------
	// Cell 5: AC #24 — cascade reads live remainingPrepay each call
	// stub getAvailableAmount to return decreasing values: 30000 → 18000 → 7500
	// each Partial call should derive its amount from the CURRENT live value
	// -----------------------------------------------------------------------

	@Test
	void cascadeRecomputesFromCurrentRemainingPrepay()
	{
		stubReceiptValue(money("100000.00")); // proportional ceiling = 30000 each call

		final Prepayment prepayment = buildPrepayment();
		// Simulate decreasing available amount across 3 live DAO reads
		when(prepaymentService.getAvailableAmount(prepayment))
				.thenReturn(money("30000.00"))
				.thenReturn(money("18000.00"))
				.thenReturn(money("7500.00"));

		final RegularInvoice invoice = buildInvoice(/*isPartial*/true, money("100000.00"));

		// Call 1: proportional = 30000, remaining = 30000 → allocAmt = min(30000, 30000) = 30000
		final Money result1 = computeAmountToAllocate(invoice, prepayment);
		// Call 2: proportional = 30000, remaining = 18000 → allocAmt = min(30000, 18000) = 18000
		final Money result2 = computeAmountToAllocate(invoice, prepayment);
		// Call 3: proportional = 30000, remaining = 7500 → allocAmt = min(30000, 7500) = 7500
		final Money result3 = computeAmountToAllocate(invoice, prepayment);

		assertThat(result1.abs()).isEqualByComparingTo(money("30000.00"));
		assertThat(result2.abs()).isEqualByComparingTo(money("18000.00"));
		assertThat(result3.abs()).isEqualByComparingTo(money("7500.00"));

		// Verify getAvailableAmount was called fresh 3 times (not a cached/snapshot value)
		verify(prepaymentService, org.mockito.Mockito.times(3)).getAvailableAmount(prepayment);
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private Money money(final String amount)
	{
		return Money.of(amount, EUR);
	}

	/**
	 * Stubs matchInvoiceRepository and materialReceiptService so that
	 * {@code RegularInvoiceValueCalculator.computeValueMatchedByReceipts} returns {@code receiptValue}.
	 * <p>
	 * Strategy: the invoice has exactly one line whose lineGrossAmt == receiptValue and
	 * qtyInvoiced == qtyMatched (both 1 unit in the test UOM), so
	 * {@code computeLineGrossAmtMatched} returns lineGrossAmt.
	 */
	private void stubReceiptValue(final Money receiptValue)
	{
		final Quantity qty = Quantity.of(1, uom);

		// Build a minimal MatchInv whose getQtyMatched(INVOICE_LINE_ID) returns qty
		final MatchInv matchInv = MatchInv.builder()
				.id(MatchInvId.ofRepoId(1))
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(INVOICE_ID.getRepoId(), INVOICE_LINE_ID.getRepoId()))
				.inoutLineId(INOUT_LINE_ID)
				.clientAndOrgId(SYSTEM)
				.soTrx(de.metas.lang.SOTrx.PURCHASE)
				.dateTrx(Instant.EPOCH)
				.dateAcct(Instant.EPOCH)
				.posted(false)
				.updatedByUserId(UserId.SYSTEM)
				.productId(PRODUCT_ID)
				.asiId(AttributeSetInstanceId.NONE)
				.qty(StockQtyAndUOMQty.builder()
						.productId(PRODUCT_ID)
						.stockQty(qty)
						.build())
				.type(MatchInvType.Material)
				.build();

		// thenAnswer provides a fresh Stream per invocation; thenReturn would exhaust the stream on first use.
		when(matchInvoiceRepository.stream(any())).thenAnswer(inv -> Stream.of(matchInv));

		// MaterialReceiptCollection mock: containsInOutLineId returns true for our INOUT_LINE_ID
		final MaterialReceiptCollection receiptsMock = mock(MaterialReceiptCollection.class);
		when(receiptsMock.containsInOutLineId(INOUT_LINE_ID)).thenReturn(true);
		when(materialReceiptService.getByOrderId(any(OrderId.class))).thenReturn(receiptsMock);

		// The invoiceLine uses the same qty so qtyMatched == qtyInvoiced → lineGrossAmt is returned
		// (see RegularInvoice.Line.computeLineGrossAmtMatched)
		// receiptValue is set via buildInvoice(lineGrossAmt=receiptValue)
	}

	/**
	 * Builds a minimal {@link RegularInvoice} with one line.
	 * The line's {@code lineGrossAmt} determines {@code receiptValue} when fully matched.
	 */
	private RegularInvoice buildInvoice(final boolean isPartialInvoice, final Money lineGrossAmt)
	{
		final Quantity qty = Quantity.of(1, uom);

		final RegularInvoice.Line line = RegularInvoice.Line.builder()
				.id(INVOICE_LINE_ID)
				.qtyInvoiced(qty)
				.lineGrossAmt(lineGrossAmt)
				.build();

		return RegularInvoice.builder()
				.id(INVOICE_ID)
				.orderId(ORDER_ID)
				.isPartialInvoice(isPartialInvoice)
				.orgId(OrgId.ANY)
				.bpartnerId(BPartnerId.ofRepoId(1))
				.dateInvoiced(LocalDate.of(2026, 3, 1))
				.dateAcct(LocalDate.of(2026, 3, 1))
				.currencyId(EUR)
				.docStatus(DocStatus.Completed)
				.isPaid(false)
				.lines(ImmutableList.of(line))
				.build();
	}

	/**
	 * Builds a minimal {@link Prepayment} for the test scenarios.
	 */
	private Prepayment buildPrepayment()
	{
		return Prepayment.builder()
				.id(PAYMENT_ID)
				.dateTrx(LocalDate.of(2026, 1, 1))
				.dateAcct(LocalDate.of(2026, 1, 1))
				.amount(money("100000.00"))
				.proformaInvoiceId(InvoiceId.ofRepoId(4001))
				.orderId(ORDER_ID)
				.build();
	}

	/**
	 * Builds an {@link OrderPaySchedule} with one LC line having the given LC%.
	 */
	private OrderPaySchedule buildLcSchedule(final Percent lcPercent)
	{
		final OrderPayScheduleLine lcLine = OrderPayScheduleLine.builder()
				.orderId(ORDER_ID)
				.paymentTermBreakId(PaymentTermBreakId.ofRepoId(1, 1))
				.referenceDateType(ReferenceDateType.LetterOfCreditDate)
				.percent(lcPercent)
				.offsetDays(0)
				.status(OrderPayScheduleStatus.Pending)
				.dueDate(LocalDate.of(2026, 6, 1))
				.dueAmount(money("100000.00"))
				.build();
		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(lcLine));
	}

	/**
	 * Directly invokes the package-visible {@code computeAmountToAllocate(RegularInvoice, Prepayment)} method.
	 */
	private Money computeAmountToAllocate(final RegularInvoice invoice, final Prepayment prepayment)
	{
		return service.computeAmountToAllocate(invoice, prepayment);
	}

	// -----------------------------------------------------------------------
	// Cell 6: GAP §3 row B + §11 dec 1 — getRegularInvoicesByOrderId returns invoices in FIFO order
	// Stubs return invoices with dates scrambled (INV3=2026-01-15, INV1=2026-01-01, INV2=2026-01-08);
	// asserts the result is sorted oldest-first.
	// -----------------------------------------------------------------------

	@Test
	void retroAllocateUnallocatedInvoices_fifoOrdering()
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		// 3 invoice records with scrambled dates: INV3 newest, INV1 oldest, INV2 middle
		final I_C_Invoice rec3 = buildInvoiceRecord(INV3_ID, LocalDate.of(2026, 1, 15));
		final I_C_Invoice rec1 = buildInvoiceRecord(INV1_ID, LocalDate.of(2026, 1, 1));
		final I_C_Invoice rec2 = buildInvoiceRecord(INV2_ID, LocalDate.of(2026, 1, 8));

		when(inOutDAO.retrieveInOutIdsByOrderId(ORDER_ID)).thenReturn(ImmutableList.of(INOUT_ID));
		when(matchInvoiceRepository.list(any())).thenReturn(MatchInvCollection.ofList(ImmutableList.of(
				buildMatchInv(INV3_ID, MatchInvId.ofRepoId(31)),
				buildMatchInv(INV1_ID, MatchInvId.ofRepoId(11)),
				buildMatchInv(INV2_ID, MatchInvId.ofRepoId(21))
		)));
		when(invoiceBL.getByIds(any())).thenReturn(ImmutableList.of(rec3, rec1, rec2));
		when(invoiceBL.getInvoiceDocBaseType(any())).thenReturn(InvoiceDocBaseType.VendorInvoice);
		when(invoiceBL.getLinesByInvoiceIds(any())).thenReturn(ImmutableList.of());

		final List<RegularInvoice> result = service.getRegularInvoicesByOrderId(ORDER_ID);

		assertThat(result).hasSize(3);
		assertThat(result.get(0).getDateInvoiced()).isEqualTo(LocalDate.of(2026, 1, 1));  // INV1 oldest
		assertThat(result.get(1).getDateInvoiced()).isEqualTo(LocalDate.of(2026, 1, 8));  // INV2 middle
		assertThat(result.get(2).getDateInvoiced()).isEqualTo(LocalDate.of(2026, 1, 15)); // INV3 newest
	}

	// -----------------------------------------------------------------------
	// Cell 7: GAP §3 row B + §11 dec 1 — findRegularInvoicesNotAlreadyAllocatedToPrepayment skips allocated
	// One of 3 invoices has hasActiveAllocationBetween=true; asserts only 2 are returned.
	// -----------------------------------------------------------------------

	@Test
	void retroAllocateUnallocatedInvoices_skipsAlreadyAllocated()
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IAllocationBL allocationBL = Services.get(IAllocationBL.class);

		final I_C_Invoice rec1 = buildInvoiceRecord(INV1_ID, LocalDate.of(2026, 1, 1));
		final I_C_Invoice rec2 = buildInvoiceRecord(INV2_ID, LocalDate.of(2026, 1, 8));
		final I_C_Invoice rec3 = buildInvoiceRecord(INV3_ID, LocalDate.of(2026, 1, 15));

		when(inOutDAO.retrieveInOutIdsByOrderId(ORDER_ID)).thenReturn(ImmutableList.of(INOUT_ID));
		when(matchInvoiceRepository.list(any())).thenReturn(MatchInvCollection.ofList(ImmutableList.of(
				buildMatchInv(INV1_ID, MatchInvId.ofRepoId(11)),
				buildMatchInv(INV2_ID, MatchInvId.ofRepoId(21)),
				buildMatchInv(INV3_ID, MatchInvId.ofRepoId(31))
		)));
		when(invoiceBL.getByIds(any())).thenReturn(ImmutableList.of(rec1, rec2, rec3));
		when(invoiceBL.getInvoiceDocBaseType(any())).thenReturn(InvoiceDocBaseType.VendorInvoice);
		when(invoiceBL.getLinesByInvoiceIds(any())).thenReturn(ImmutableList.of());

		// INV2 is already allocated to the prepayment
		when(allocationBL.hasActiveAllocationBetween(INV2_ID, PAYMENT_ID)).thenReturn(true);

		final Prepayment prepayment = buildPrepayment();
		final List<RegularInvoice> result = service.findRegularInvoicesNotAlreadyAllocatedToPrepayment(prepayment);

		assertThat(result).hasSize(2);
		assertThat(result.stream().map(RegularInvoice::getId)).doesNotContain(INV2_ID);
	}

	// -----------------------------------------------------------------------
	// Cell 8: AC #23 — sales invoice (IsSOTrx=Y) is excluded by isRegularInvoice
	// -----------------------------------------------------------------------

	@Test
	void triggerFilter_salesInvoice_excluded()
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(true);
		invoice.setIsFinancial(true);
		invoice.setDocStatus(DocStatus.Completed.getCode());

		assertThat(service.isRegularInvoice(invoice, true)).isFalse();
	}

	// -----------------------------------------------------------------------
	// Cell 9: AC #26 — purchase credit memo (DocBaseType=APC) is excluded by isRegularInvoice
	// -----------------------------------------------------------------------

	@Test
	void triggerFilter_creditMemoAPC_excluded()
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(false);
		invoice.setIsFinancial(true);
		invoice.setDocStatus(DocStatus.Completed.getCode());
		when(invoiceBL.getInvoiceDocBaseType(invoice)).thenReturn(InvoiceDocBaseType.VendorCreditMemo);

		assertThat(service.isRegularInvoice(invoice, true)).isFalse();
	}

	// -----------------------------------------------------------------------
	// Additional helpers for cells 6–9
	// -----------------------------------------------------------------------

	/**
	 * Builds an {@link I_C_Invoice} POJO with the given ID and dateInvoiced,
	 * configured as a regular purchase invoice (IsSOTrx=N, IsFinancial=Y, DocStatus=CO).
	 */
	private I_C_Invoice buildInvoiceRecord(final InvoiceId invoiceId, final LocalDate dateInvoiced)
	{
		final I_C_Invoice record = newInstance(I_C_Invoice.class);
		// Set ID before save so extractInvoiceId(record) returns the expected InvoiceId.
		// In POJO mode, setting the PK column before the first saveRecord fixes the ID.
		record.setC_Invoice_ID(invoiceId.getRepoId());
		record.setIsSOTrx(false);
		record.setIsFinancial(true);
		record.setDocStatus(DocStatus.Completed.getCode());
		record.setDateInvoiced(org.compiere.util.TimeUtil.asTimestamp(dateInvoiced));
		record.setDateAcct(org.compiere.util.TimeUtil.asTimestamp(dateInvoiced));
		record.setC_Currency_ID(EUR.getRepoId());
		record.setC_BPartner_ID(1);
		record.setAD_Org_ID(0);
		return record;
	}

	/**
	 * Builds a minimal {@link MatchInv} that maps the given invoiceId (for invoice-ID lookup via MatchInvCollection).
	 */
	private MatchInv buildMatchInv(final InvoiceId invoiceId, final MatchInvId matchInvId)
	{
		final Quantity qty = Quantity.of(1, uom);
		return MatchInv.builder()
				.id(matchInvId)
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(invoiceId.getRepoId(), INVOICE_LINE_ID.getRepoId()))
				.inoutLineId(INOUT_LINE_ID)
				.clientAndOrgId(SYSTEM)
				.soTrx(de.metas.lang.SOTrx.PURCHASE)
				.dateTrx(Instant.EPOCH)
				.dateAcct(Instant.EPOCH)
				.posted(false)
				.updatedByUserId(UserId.SYSTEM)
				.productId(PRODUCT_ID)
				.asiId(AttributeSetInstanceId.NONE)
				.qty(StockQtyAndUOMQty.builder()
						.productId(PRODUCT_ID)
						.stockQty(qty)
						.build())
				.type(MatchInvType.Material)
				.build();
	}
}
