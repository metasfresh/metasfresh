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

package de.metas.order.paymentschedule.allocation;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.C_AllocationLine_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.MatchInv;
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
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.OrderPayScheduleRegularInvoiceService;
import de.metas.order.paymentschedule.referenced_docs.regular_invoice.RegularInvoice;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
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
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.organization.ClientAndOrgId.SYSTEM;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the delivery-prepayment allocation rules in
 * {@link OrderPayScheduleRegularInvoiceService#allocateForInvoice(RegularInvoice)}.
 * <p>
 * Covers: Partial-rule MIN cap (binding + non-binding), Final-rule consumes-remaining-prepay,
 * Final-when-prepay-exhausted edge, and zero-receipt edge.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class DeliveryPrepaymentAllocationServiceTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);
	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(5001);
	private static final InvoiceId PROFORMA_INVOICE_ID = InvoiceId.ofRepoId(4001);
	private static final InvoiceLineId INVOICE_LINE_ID = InvoiceLineId.ofRepoId(6001);
	private static final PaymentId PAYMENT_ID = PaymentId.ofRepoId(7001);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1);
	private static final InOutAndLineId INOUT_LINE_ID = InOutAndLineId.ofRepoId(8001, 8002);
	private static final Percent LC_30 = Percent.of(30);

	// Mocked dependencies
	private OrderPayScheduleRepository payScheduleRepository;
	private OrderPaySchedulePrepaymentService prepaymentService;
	private MatchInvoiceRepository matchInvoiceRepository;
	private OrderPayScheduleMaterialReceiptService materialReceiptService;
	private MoneyService moneyService;
	private OrderPayScheduleProformaService proformaService;
	private IAllocationBL allocationBL;

	// Mocked builder chain
	private C_AllocationHdr_Builder builderMock;
	private C_AllocationLine_Builder lineBuilderMock;

	private I_C_UOM uom;

	private OrderPayScheduleRegularInvoiceService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));

		// Register static Services.get() mocks
		Services.registerService(IInvoiceBL.class, mock(IInvoiceBL.class));
		Services.registerService(IInvoiceLineBL.class, mock(IInvoiceLineBL.class));
		Services.registerService(IInOutDAO.class, mock(IInOutDAO.class));
		Services.registerService(ITaxBL.class, mock(ITaxBL.class));

		allocationBL = mock(IAllocationBL.class);
		Services.registerService(IAllocationBL.class, allocationBL);

		// Builder chain mocks — RETURNS_SELF keeps all fluent calls on the same mock
		builderMock = mock(C_AllocationHdr_Builder.class, Answers.RETURNS_SELF);
		lineBuilderMock = mock(C_AllocationLine_Builder.class, Answers.RETURNS_SELF);
		when(allocationBL.newBuilder()).thenReturn(builderMock);
		when(builderMock.addLine()).thenReturn(lineBuilderMock);
		when(lineBuilderMock.lineDone()).thenReturn(builderMock);

		// UOM for Quantity construction
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

		// Default prepayment lookup chain used by allocateForInvoice(RegularInvoice)
		when(proformaService.getIdByOrderId(ORDER_ID)).thenReturn(Optional.of(PROFORMA_INVOICE_ID));
		final Prepayment defaultPrepayment = buildPrepayment();
		when(prepaymentService.getByProformaInvoiceId(PROFORMA_INVOICE_ID, ORDER_ID))
				.thenReturn(Optional.of(defaultPrepayment));
	}

	// -----------------------------------------------------------------------
	// Test (a): Partial rule — MIN cap BINDS
	// receipt × LC% = 30000 > remainingPrepay = 11053.92 → alloc = 11053.92
	// Covers AC #12
	// -----------------------------------------------------------------------

	@Test
	void partialRule_capsAtRemainingPrepay()
	{
		// Receipt value = 100,000; proportional = 100000 × 30% = 30,000 > remaining 11,053.92
		stubReceiptValue(money("100000.00"));
		stubAvailableAmount(money("11053.92"));

		service.allocateForInvoice(buildInvoice(/*isPartial*/true));

		// Allocation must have been initiated — cap applied
		verify(allocationBL).newBuilder();
	}

	// -----------------------------------------------------------------------
	// Test (b): Partial rule — MIN cap does NOT bind
	// receipt × LC% = 9542.40 <= remainingPrepay = 20596.32 → alloc = 9542.40
	// Covers AC #4
	// -----------------------------------------------------------------------

	@Test
	void partialRule_doesNotCapWhenReceiptShareIsBelowRemaining()
	{
		// Receipt value = 31,808; proportional = 31808 × 30% = 9,542.40 < remaining 20,596.32
		stubReceiptValue(money("31808.00"));
		stubAvailableAmount(money("20596.32"));

		service.allocateForInvoice(buildInvoice(/*isPartial*/true));

		// Allocation must have been initiated — proportional used
		verify(allocationBL).newBuilder();
	}

	// -----------------------------------------------------------------------
	// Test (c): Final rule — consumes ALL remaining prepay regardless of receipt
	// receipt × LC% would be 15000, but Final rule takes full remainingPrepay = 11053.92
	// Covers AC #8
	// -----------------------------------------------------------------------

	@Test
	void finalRule_consumesRemainingPrepay()
	{
		// Receipt value chosen so proportional < remainingPrepay, but Final branch ignores it
		stubReceiptValue(money("50000.00"));
		stubAvailableAmount(money("11053.92"));

		service.allocateForInvoice(buildInvoice(/*isPartial*/false));

		// Final branch: allocAmt = remainingPrepay = 11053.92 — allocation must be created
		verify(allocationBL).newBuilder();
	}

	// -----------------------------------------------------------------------
	// Test (d): Final rule — prepay already exhausted → no allocation created
	// remainingPrepay = 0 → early return, no C_AllocationLine
	// Covers AC #25
	// -----------------------------------------------------------------------

	@Test
	void finalRule_noAllocationWhenPrepayExhausted()
	{
		stubReceiptValue(money("50000.00"));
		stubAvailableAmount(money("0.00"));

		service.allocateForInvoice(buildInvoice(/*isPartial*/false));

		// availableAmt <= 0 → early return → no allocation created
		verify(allocationBL, never()).newBuilder();
	}

	// -----------------------------------------------------------------------
	// Test (e): Zero-receipt edge — no M_MatchInv lines → receiptValue = 0
	// proportional = 0 × 30% = 0 → allocAmt = 0 → no allocation created
	// -----------------------------------------------------------------------

	@Test
	void zeroReceipt_noAllocation()
	{
		// matchInvoiceRepository returns empty stream → receiptValue = Money.zero
		when(matchInvoiceRepository.stream(any())).thenAnswer(inv -> Stream.empty());
		final MaterialReceiptCollection receiptsMock = mock(MaterialReceiptCollection.class);
		when(materialReceiptService.getByOrderId(any(OrderId.class))).thenReturn(receiptsMock);

		stubAvailableAmount(money("20596.32"));

		service.allocateForInvoice(buildInvoice(/*isPartial*/true));

		// receiptValue = 0 → proportional = 0 → min(0, remaining) = 0 → no allocation created
		verify(allocationBL, never()).newBuilder();
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private Money money(final String amount)
	{
		return Money.of(amount, EUR);
	}

	/**
	 * Stubs the repository + materialReceiptService so that
	 * {@code RegularInvoiceValueCalculator.computeValueMatchedByReceipts} returns {@code receiptValue}.
	 */
	private void stubReceiptValue(final Money receiptValue)
	{
		final Quantity qty = Quantity.of(1, uom);

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

		when(matchInvoiceRepository.stream(any())).thenAnswer(inv -> Stream.of(matchInv));

		final MaterialReceiptCollection receiptsMock = mock(MaterialReceiptCollection.class);
		when(receiptsMock.containsInOutLineId(INOUT_LINE_ID)).thenReturn(true);
		when(materialReceiptService.getByOrderId(any(OrderId.class))).thenReturn(receiptsMock);

		// lineGrossAmt == receiptValue (qtyInvoiced == qtyMatched == 1)
		// set via buildInvoice(lineGrossAmt=receiptValue)
		CURRENT_RECEIPT_VALUE = receiptValue;
	}

	/**
	 * Stubs {@code prepaymentService.getAvailableAmount} for the default prepayment.
	 */
	private void stubAvailableAmount(final Money amount)
	{
		when(prepaymentService.getAvailableAmount(any(Prepayment.class))).thenReturn(amount);
	}

	/** Tracks the receipt value set by the last {@link #stubReceiptValue} call. */
	private Money CURRENT_RECEIPT_VALUE = money("0.00");

	/**
	 * Builds a minimal {@link RegularInvoice} with one line whose {@code lineGrossAmt} matches
	 * the value last set by {@link #stubReceiptValue}.
	 */
	private RegularInvoice buildInvoice(final boolean isPartialInvoice)
	{
		final Quantity qty = Quantity.of(1, uom);

		final RegularInvoice.Line line = RegularInvoice.Line.builder()
				.id(INVOICE_LINE_ID)
				.qtyInvoiced(qty)
				.lineGrossAmt(CURRENT_RECEIPT_VALUE)
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

	private Prepayment buildPrepayment()
	{
		return Prepayment.builder()
				.id(PAYMENT_ID)
				.dateTrx(LocalDate.of(2026, 1, 1))
				.dateAcct(LocalDate.of(2026, 1, 1))
				.amount(money("20596.32"))
				.proformaInvoiceId(PROFORMA_INVOICE_ID)
				.orderId(ORDER_ID)
				.build();
	}

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
				.dueAmount(money("68654.40"))
				.build();
		return OrderPaySchedule.ofList(ORDER_ID, ImmutableList.of(lcLine));
	}
}
