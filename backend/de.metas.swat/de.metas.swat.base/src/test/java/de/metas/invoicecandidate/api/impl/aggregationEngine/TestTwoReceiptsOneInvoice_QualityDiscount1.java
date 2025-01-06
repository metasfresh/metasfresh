package de.metas.invoicecandidate.api.impl.aggregationEngine;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.material.MovementType;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Similar to {@link AbstractTwoInOutsOneInvoicePurchaseTests}, but each InOut has two lines, one of each has <code>IsInDispute=Y</code>.
 * <p>
 * => Expectation: the MovementQty that is in dispute and is not invoiced.
 * <p>
 */
public abstract class TestTwoReceiptsOneInvoice_QualityDiscount1 extends AbstractNewAggregationEngineTests
{
	protected static final BigDecimal THREE = new BigDecimal("3");
	protected static final BigDecimal FIVE = new BigDecimal("5");
	protected static final BigDecimal TEN = new BigDecimal("10");
	protected static final BigDecimal TWENTY = new BigDecimal("20");

	protected I_M_InOut inOut1;
	/**
	 * Receipt 1 - line 1
	 */
	protected I_M_InOutLine iol11_three;
	/**
	 * Receipt 1 - line 2 (IsInDispute)
	 */
	protected I_M_InOutLine iol12_five_disp;

	protected I_M_InOut inOut2;
	/**
	 * Receipt 2 - line 1
	 */
	protected I_M_InOutLine iol21_ten;
	/**
	 * Receipt 2 - line 2 (IsInDispute)
	 */
	protected I_M_InOutLine iol22_twenty_disp;

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		final List<I_C_Invoice_Candidate> ics = test_2StepShipment_CommonSetup_Step01(false, null);// isSOTrx, priceEtnered_Override
		final I_C_Invoice_Candidate ic = ics.get(0);
		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		ic.setInvoiceRule_Override(null);
		InterfaceWrapperHelper.save(ic);

		return ics;
	}

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		{
			final StockQtyAndUOMQty qtysDelivered_3 = StockQtyAndUOMQtys.create(new BigDecimal("3"), productId, new BigDecimal("30"), uomId);
			final String inOutDocumentNo = "1";
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo, MovementType.VendorReceipts);
			iol11_three = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_3, inOutDocumentNo + "_1");

			final StockQtyAndUOMQty qtysDelivered_5 = StockQtyAndUOMQtys.create(new BigDecimal("5"), productId, new BigDecimal("50"), uomId);
			iol12_five_disp = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_5, inOutDocumentNo + "_2");
			iol12_five_disp.setIsInDispute(true);
			InterfaceWrapperHelper.save(iol12_five_disp);

			completeInOut(inOut1);
		}

		{
			final String inOutDocumentNo = "2";
			final StockQtyAndUOMQty qtysDelivered_10 = StockQtyAndUOMQtys.create(new BigDecimal("10"), productId, new BigDecimal("100"), uomId);
			inOut2 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo, MovementType.VendorReceipts);
			iol21_ten = createInvoiceCandidateInOutLine(ic, inOut2, qtysDelivered_10, inOutDocumentNo + "_1");

			final StockQtyAndUOMQty qtysDelivered_20 = StockQtyAndUOMQtys.create(new BigDecimal("20"), productId, new BigDecimal("200"), uomId);
			iol22_twenty_disp = createInvoiceCandidateInOutLine(ic, inOut2, qtysDelivered_20, inOutDocumentNo + "_2");
			iol22_twenty_disp.setIsInDispute(true);
			InterfaceWrapperHelper.save(iol22_twenty_disp);

			completeInOut(inOut2);
		}

		return Arrays.asList(iol11_three, iol12_five_disp, iol21_ten, iol22_twenty_disp);
	}

	@Override
	protected void step_validate_before_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> ignored)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
		InvoiceCandidateExpectation.newExpectation()
				.inDispute(true)
				.qtyDelivered(THREE.add(TEN).add(FIVE).add(TWENTY)) // (=> 38)
				.qtyToInvoice(THREE.add(TEN)) // iol11 and iol21 (=> 13)
				.qtyWithIssues(FIVE.add(TWENTY)) // iol12 and iol22 (=> 25)
				.qualityDiscountPercent(new BigDecimal("65.79")) // 25 divided by 38 times 100, rounded to a scale of 2
				.assertExpected(ic);

	}

	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines, final List<IInvoiceHeader> invoices)
	{
		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice1 = invoices.remove(0);

		//
		// guard
		assertThat(invoice1.isSOTrx(), is(false));

		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertEquals("We are expecting one invoice line: " + invoiceLines1, 1, invoiceLines1.size());

		// expecting one line with the summed qty of both non-in-dispute iols icIol11 and icIol21
		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol11_three);
		assertNotNull("Missing IInvoiceLineRW for iol11=" + iol11_three, il1);
		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(THREE.add(TEN)));

		assertThat("iol21=" + iol21_ten + " is aggregated into il1=" + il1, getSingleForInOutLine(invoiceLines1, iol21_ten), is(il1));

		final InvoiceCandidateInOutLineToUpdate icIol11 = retrieveIcIolToUpdateIfExists(il1, iol11_three);
		assertThat(icIol11.getQtyInvoiced().getStockQty().toBigDecimal(), comparesEqualTo(THREE));
		assertThat(icIol11.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(THIRTY));

		final InvoiceCandidateInOutLineToUpdate icIol21 = retrieveIcIolToUpdateIfExists(il1, iol21_ten);
		assertThat(icIol21.getQtyInvoiced().getStockQty().toBigDecimal(), comparesEqualTo(TEN));
		assertThat(icIol21.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(HUNDRET));

		//
		// checking the in-dispute-iols
		assertNull("Unexpected IInvoiceLineRW for iol12=" + iol12_five_disp, getSingleForInOutLine(invoiceLines1, iol12_five_disp));
		final InvoiceCandidateInOutLineToUpdate icIol12 = retrieveIcIolToUpdateIfExists(il1, iol12_five_disp);
		assertNull(icIol12);

		assertNull("Unexpected IInvoiceLineRW for iol22=" + iol22_twenty_disp, getSingleForInOutLine(invoiceLines1, iol22_twenty_disp));
		final InvoiceCandidateInOutLineToUpdate icIol22 = retrieveIcIolToUpdateIfExists(il1, iol22_twenty_disp);
		assertNull(icIol22);
	}
}
