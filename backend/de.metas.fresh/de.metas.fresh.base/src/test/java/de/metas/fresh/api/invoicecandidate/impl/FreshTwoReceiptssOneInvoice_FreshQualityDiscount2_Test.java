package de.metas.fresh.api.invoicecandidate.impl;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.fresh.invoicecandidate.spi.impl.FreshQuantityDiscountAggregator;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.aggregationEngine.TwoReceiptsOneInvoice_QualityDiscount2_Tests;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.money.MoneyService;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Similar to its super class {@link TwoReceiptsOneInvoice_QualityDiscount2_Tests}, but uses the {@link FreshQuantityDiscountAggregator} instead of the default aggregator.<br>
 * The setup is the same, but the expectations are different:
 * <ul>
 * <li>Return three invoice lines, the second one is dedicated to the in-dispute-iol
 * <li>The first invoice line aggregates both the "normal" iol11 and the in-dispute-iol12.
 * <li>The second line then has a qty of ONE and the negative net amount that goes back to the in-dispute-iol12.
 * <li>And the third invoice line is about the "normal" iol21.
 * <ul>
 * Note that it's three lines as opposed to two because the iols of inOut1 have a different ASI than those of inOut2.
 *
 * task 08507
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, /* ShutdownListener.class,*/ MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class FreshTwoReceiptssOneInvoice_FreshQualityDiscount2_Test extends TwoReceiptsOneInvoice_QualityDiscount2_Tests
{
	private I_C_Invoice_Candidate_Agg freshAgg;

	@Override
	protected final void config_InvoiceCand_LineAggregation(final Properties ctx, final String trxName)
	{
		freshAgg = FreshAggregationTestHelper.configFreshAggregator(ctx, trxName);
	}

	@Override
	protected void step_validate_before_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> ignored)
	{
		super.step_validate_before_aggregation(invoiceCandidates, ignored);

		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		assertThat(ic.getC_Invoice_Candidate_Agg(), is(freshAgg));
	}

	@Override
	protected void step_validate_after_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines, List<IInvoiceHeader> invoices)
	{
		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());
		final IInvoiceHeader invoice = invoices.remove(0);
		final I_C_Invoice_Candidate ic = CollectionUtils.singleElement(invoiceCandidates);

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		assertEquals("We are expecting three invoice lines: " + invoiceLines, 3, invoiceLines.size());

		//
		// invoiceLine1
		// checking that the first il has the full quantity which includes the qtys that are in dispute
		final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines, iol11_three);
		assertNotNull("Missing IInvoiceLineRW for iol11=" + iol11_three, invoiceLine1);

		assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(THREE.add(FIVE)));
		assertThat(invoiceLine1.getNetLineAmt().toBigDecimal(), comparesEqualTo(THREE.add(FIVE).multiply(TEN)));
		validateIcIlAllocationQty(ic, invoice, invoiceLine1, THREE.add(FIVE));
		ic_inout1_attributeExpectations.assertExpected("invoiceLine1 attributes", invoiceLine1.getInvoiceLineAttributes());

		// final I_C_InvoiceCandidate_InOutLine icIol11 = invoiceCandidateInOutLine(ic, iol11_three);
		// assertThat(icIol11.getQtyInvoiced(), comparesEqualTo(THREE)); // as of task 08529 we got rid of I_C_InvoiceCandidate_InOutLine.QtyInvoiced
		// final I_C_InvoiceCandidate_InOutLine icIol12 = invoiceCandidateInOutLine(ic, iol12_five_disp);
		// assertThat("from the iol in dispute, no Qty shall be invoiced", icIol12.getQtyInvoiced(), comparesEqualTo(BigDecimal.ZERO)); // as of task 08529 we got rid of
		// I_C_InvoiceCandidate_InOutLine.QtyInvoiced

		final List<IInvoiceLineRW> forIol12 = getForInOutLine(invoiceLines, iol12_five_disp);
		assertThat(forIol12, hasItems(invoiceLine1));

		forIol12.remove(invoiceLine1); // remove the one we already validated

		//
		// invoiceLine2
		// the InDispute line => this one shall containt the quality discount
		{
			final IInvoiceLineRW invoiceLine2 = CollectionUtils.singleElement(forIol12);

			assertThat(invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(FIVE.negate()));
			assertThat(invoiceLine2.getPriceActual().toBigDecimal(), comparesEqualTo(BigDecimal.ONE));
			assertThat(invoiceLine2.getNetLineAmt().toBigDecimal(), comparesEqualTo(FIVE.negate().multiply(TEN)));
			validateIcIlAllocationQty(ic, invoice, invoiceLine2, FIVE.negate());
			// shall have the same attributes as the invoice line which is not about in dispute quantity (08642)
			ic_inout1_attributeExpectations.assertExpected("invoiceLine2 attributes", invoiceLine2.getInvoiceLineAttributes());
		}

		//
		// invoiceLine3
		{
			final IInvoiceLineRW invoiceLine3 = getSingleForInOutLine(invoiceLines, iol21_ten);
			assertNotNull("Missing IInvoiceLineRW for iol21=" + iol21_ten, invoiceLine3);

			assertThat(invoiceLine3.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(TEN));
			assertThat(invoiceLine3.getNetLineAmt().toBigDecimal(), comparesEqualTo(TEN.multiply(TEN)));
			validateIcIlAllocationQty(ic, invoice, invoiceLine3, TEN);
			ic_inout2_attributeExpectations.assertExpected("invoiceLine3 attributes", invoiceLine3.getInvoiceLineAttributes());

			// final I_C_InvoiceCandidate_InOutLine icIol21 = invoiceCandidateInOutLine(ic, iol21_ten);
			// assertThat(icIol21.getQtyInvoiced(), comparesEqualTo(TEN)); // as of task 08529 we got rid of I_C_InvoiceCandidate_InOutLine.QtyInvoiced
		}
	}
}
