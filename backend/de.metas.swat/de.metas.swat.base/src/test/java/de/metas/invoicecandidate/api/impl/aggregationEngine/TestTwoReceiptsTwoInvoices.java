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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;

/**
 * => Expectation: one invoice, one line; on the purchase side we want to aggregate inoutLines over different InOuts, as long as they don't differ in their "invoice-relevant" ASI-values.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TestTwoReceiptsTwoInvoices extends AbstractTwoInOutsTests
{

	@Override
	protected final boolean config_IsSOTrx()
	{
		return false;
	}

	@Override
	protected BigDecimal config_GetPriceEntered_Override()
	{
		return null;
	}

	@Override
	protected void step_validate_after_aggregation(
			final List<I_C_Invoice_Candidate> invoiceCandidates,
			final List<I_M_InOutLine> inOutLines,
			final List<IInvoiceHeader> invoices)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.getFirst();

		assertThat(invoices.size(), is(1));

		//
		// Assume that the invoices are OK
		{
			//final IInvoiceHeader invoice1 = removeInvoiceHeaderForInOutId(invoices, inOut1.getM_InOut_ID());
			final IInvoiceHeader invoice1 = invoices.removeFirst();
			assertThat(invoice1.isSOTrx(), is(config_IsSOTrx()));
			validateInvoiceHeader("Invoice", invoice1, ic);

			final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
			assertEquals("We are expecting one invoice line: " + invoiceLines1, 1, invoiceLines1.size());

			final BigDecimal fullQty = partialQty1_32.add(partialQty2_8).add(partialQty3_4);

			final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines1, iol11);
			assertThat(invoiceLine1.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(3));
			assertEquals("Invalid PriceActual", 1, invoiceLine1.getPriceActual().toBigDecimal().intValueExact());
			assertThat("Invalid QtyToInvoice", invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(fullQty));
			assertThat("Invalid NetLineAmt", invoiceLine1.getNetLineAmt().toBigDecimal(), comparesEqualTo(fullQty.multiply(TEN)) /* because price=1 and uomQty is stockQty x 10 */);

			// validate the IC<->IL qty allocation
			validateIcIlAllocationQty(ic, invoice1, invoiceLine1, fullQty);
		}

		//
		// Make sure all invoices were evaluated
		assertEquals("All generated invoices should be evaluated", Collections.emptyList(), invoices);
	}
}
