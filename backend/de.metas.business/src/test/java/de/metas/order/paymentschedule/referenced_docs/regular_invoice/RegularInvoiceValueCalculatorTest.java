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
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceiptCollection;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.ITaxBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.stream.Stream;

import static de.metas.organization.ClientAndOrgId.SYSTEM;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link RegularInvoiceValueCalculator#computeValueMatchedByReceipts(RegularInvoice)}.
 *
 * <p>Covers:
 * <ul>
 *   <li>{@link #invoiceLineGrossSummed} — 2 invoice lines, fully matched; verifies invoice-line-gross semantic.</li>
 *   <li>{@link #mixedTaxOrder_invoiceLineGrossMatchesSpec} — different tax rates per invoice line + partial match;
 *       verifies the calculator reads invoice-line gross (NOT order-line × tax).</li>
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
class RegularInvoiceValueCalculatorTest
{
	private static final CurrencyId EUR = CurrencyId.ofRepoId(318);
	private static final OrderId ORDER_ID = OrderId.ofRepoId(9001);
	private static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(5001);
	private static final InvoiceLineId LINE_A_ID = InvoiceLineId.ofRepoId(6001);
	private static final InvoiceLineId LINE_B_ID = InvoiceLineId.ofRepoId(6002);
	private static final InOutId INOUT_ID = InOutId.ofRepoId(8001);
	private static final InOutAndLineId INOUT_LINE_A = InOutAndLineId.ofRepoId(8001, 8101);
	private static final InOutAndLineId INOUT_LINE_B = InOutAndLineId.ofRepoId(8001, 8102);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1);
	private static final LocalDate INVOICE_DATE = LocalDate.of(2026, 3, 1);

	private I_C_UOM uom;
	private MatchInvoiceRepository matchInvoiceRepository;
	private OrderPayScheduleMaterialReceiptService materialReceiptService;
	private MoneyService moneyService;
	private RegularInvoiceValueCalculator calculator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		Services.registerService(ITaxBL.class, mock(ITaxBL.class));

		matchInvoiceRepository = mock(MatchInvoiceRepository.class);
		materialReceiptService = mock(OrderPayScheduleMaterialReceiptService.class);
		moneyService = mock(MoneyService.class);
		when(moneyService.getStdPrecision(any(CurrencyId.class))).thenReturn(CurrencyPrecision.TWO);

		calculator = RegularInvoiceValueCalculator.builder()
				.taxBL(Services.get(ITaxBL.class))
				.moneyService(moneyService)
				.matchInvoiceRepository(matchInvoiceRepository)
				.materialReceiptService(materialReceiptService)
				.build();
	}

	// -----------------------------------------------------------------------
	// Test 1 — 2 invoice lines, fully matched; invoice-line-gross summed
	// -----------------------------------------------------------------------

	/**
	 * Invoice with 2 lines, each fully matched (qtyMatched == qtyInvoiced):
	 * <ul>
	 *   <li>Line A: lineNetAmt=1000, taxAmt=190 → gross=1190, qtyInvoiced=10, matchedQty=10</li>
	 *   <li>Line B: lineNetAmt=500,  taxAmt=100 → gross=600,  qtyInvoiced=20, matchedQty=20</li>
	 * </ul>
	 * Expected: 1190 × (10/10) + 600 × (20/20) = 1190 + 600 = 1790.00.
	 */
	@Test
	void invoiceLineGrossSummed()
	{
		// Arrange: 2 MatchInv records, one per invoice line, each with qty == qtyInvoiced
		final Quantity qtyA = qty("10");
		final Quantity qtyB = qty("20");

		final MatchInv matchInvA = buildMatchInv(MatchInvId.ofRepoId(1), INVOICE_ID, LINE_A_ID, INOUT_LINE_A, qtyA);
		final MatchInv matchInvB = buildMatchInv(MatchInvId.ofRepoId(2), INVOICE_ID, LINE_B_ID, INOUT_LINE_B, qtyB);

		stubMatchInvsAndReceipts(matchInvA, matchInvB);

		final RegularInvoice invoice = buildInvoice(
				ImmutableList.of(
						buildLine(LINE_A_ID, qtyA, Money.of("1190.00", EUR)),
						buildLine(LINE_B_ID, qtyB, Money.of("600.00", EUR))));

		// Act
		final Money result = calculator.computeValueMatchedByReceipts(invoice);

		// Assert: 1190.00 × (10/10) + 600.00 × (20/20) = 1790.00
		assertThat(result).isEqualByComparingTo(Money.of("1790.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Test 2 — different tax rates per line + partial match; invoice-line-gross semantic
	// -----------------------------------------------------------------------

	/**
	 * Invoice with 2 lines, different tax rates, partial match on line A:
	 * <ul>
	 *   <li>Line A: gross=1100.00 (net=1000, tax=10%), qtyInvoiced=10, matchedQty=5 (partial)</li>
	 *   <li>Line B: gross=1200.00 (net=1000, tax=20%), qtyInvoiced=20, matchedQty=20 (full)</li>
	 * </ul>
	 * Expected: 1100 × (5/10) + 1200 × (20/20) = 550.00 + 1200.00 = 1750.00.
	 * Verifies the invoice-line-gross semantic: reads invoice-line LineNetAmt + TaxAmtInfo,
	 * NOT order-line × tax.
	 */
	@Test
	void mixedTaxOrder_invoiceLineGrossMatchesSpec()
	{
		// Arrange
		final Quantity qtyInvoicedA = qty("10");
		final Quantity qtyMatchedA = qty("5");   // partial
		final Quantity qtyInvoicedB = qty("20");
		final Quantity qtyMatchedB = qty("20");  // full

		final MatchInv matchInvA = buildMatchInv(MatchInvId.ofRepoId(1), INVOICE_ID, LINE_A_ID, INOUT_LINE_A, qtyMatchedA);
		final MatchInv matchInvB = buildMatchInv(MatchInvId.ofRepoId(2), INVOICE_ID, LINE_B_ID, INOUT_LINE_B, qtyMatchedB);

		stubMatchInvsAndReceipts(matchInvA, matchInvB);

		final RegularInvoice invoice = buildInvoice(
				ImmutableList.of(
						buildLine(LINE_A_ID, qtyInvoicedA, Money.of("1100.00", EUR)),
						buildLine(LINE_B_ID, qtyInvoicedB, Money.of("1200.00", EUR))));

		// Act
		final Money result = calculator.computeValueMatchedByReceipts(invoice);

		// Assert: 1100.00 × (5/10) + 1200.00 × (20/20) = 550.00 + 1200.00 = 1750.00
		assertThat(result).isEqualByComparingTo(Money.of("1750.00", EUR));
	}

	// -----------------------------------------------------------------------
	// Fixture helpers
	// -----------------------------------------------------------------------

	/**
	 * Stubs {@code matchInvoiceRepository.stream()} and {@code materialReceiptService.getByOrderId()}
	 * so that both provided matchInvs pass the containsInOutLineId filter.
	 */
	private void stubMatchInvsAndReceipts(final MatchInv... matchInvs)
	{
		// thenAnswer provides a fresh Stream per invocation (thenReturn exhausts the stream on first use)
		when(matchInvoiceRepository.stream(any())).thenAnswer(inv -> Stream.of(matchInvs));

		// MaterialReceiptCollection mock: both INOUT_LINE_A and INOUT_LINE_B pass the filter
		final MaterialReceiptCollection receiptsMock = mock(MaterialReceiptCollection.class);
		when(receiptsMock.containsInOutLineId(INOUT_LINE_A)).thenReturn(true);
		when(receiptsMock.containsInOutLineId(INOUT_LINE_B)).thenReturn(true);
		when(materialReceiptService.getByOrderId(any(OrderId.class))).thenReturn(receiptsMock);
	}

	private MatchInv buildMatchInv(
			final MatchInvId matchInvId,
			final InvoiceId invoiceId,
			final InvoiceLineId invoiceLineId,
			final InOutAndLineId inoutAndLineId,
			final Quantity qty)
	{
		return MatchInv.builder()
				.id(matchInvId)
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(invoiceId.getRepoId(), invoiceLineId.getRepoId()))
				.inoutLineId(inoutAndLineId)
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

	private RegularInvoice buildInvoice(final ImmutableList<RegularInvoice.Line> lines)
	{
		return RegularInvoice.builder()
				.id(INVOICE_ID)
				.orderId(ORDER_ID)
				.isPartialInvoice(false)
				.orgId(OrgId.ANY)
				.bpartnerId(BPartnerId.ofRepoId(1))
				.dateInvoiced(INVOICE_DATE)
				.dateAcct(INVOICE_DATE)
				.currencyId(EUR)
				.docStatus(DocStatus.Completed)
				.isPaid(false)
				.lines(lines)
				.build();
	}

	private RegularInvoice.Line buildLine(
			final InvoiceLineId id,
			final Quantity qtyInvoiced,
			final Money lineGrossAmt)
	{
		return RegularInvoice.Line.builder()
				.id(id)
				.qtyInvoiced(qtyInvoiced)
				.lineGrossAmt(lineGrossAmt)
				.build();
	}

	private Quantity qty(final String value)
	{
		return Quantity.of(new BigDecimal(value), uom);
	}
}
