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

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * <ul>
 * <li>two shipments, the first one with one line, the second one with two lines..each line has the same product etc
 * <li>both iols belong to the same order line and thus are associated to the same invoice candidate
 * </ul>
 * 
 * => Expectation: one invoice, but two lines, because only iols that belong to the same inOut can be aggregated into one invoice line.
 * <p>
 * 
 * @author ts
 *
 */
public abstract class AbstractTwoInOutsOneInvoicePurchaseTests extends AbstractTwoInOutsTests
{
	@Override
	protected final boolean config_IsSOTrx()
	{
		return false;
	}

	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines, final List<IInvoiceHeader> invoices)
	{
		// config-guard
		assertThat(config_IsSOTrx(), is(false));

		final BigDecimal fullqty = partialQty1.add(partialQty2).add(partialQty3);

		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		//
		// Assume that the invoice is OK
		{
			assertThat(invoices.size(), is(1));
			final IInvoiceHeader invoice1 = invoices.remove(0);
			assertThat(invoice1.isSOTrx(), is(config_IsSOTrx()));
			validateInvoiceHeader("Invoice", invoice1, ic);

			final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
			assertEquals("We are expecting one invoice line: " + invoiceLines1, 1, invoiceLines1.size());

			final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines1, iol11);
			assertThat(invoiceLine1.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(3));

			final BigDecimal priceActual = invoiceCandBL.getPriceActual(ic);
			final BigDecimal priceEntered = invoiceCandBL.getPriceEntered(ic);

			assertThat("Invalid PriceEntered", invoiceLine1.getPriceEntered(), comparesEqualTo(priceEntered));
			assertThat("Invalid PriceActual", invoiceLine1.getPriceActual(), comparesEqualTo(priceActual));

			assertThat("Invalid QtyToInvoice", invoiceLine1.getQtyToInvoice(), comparesEqualTo(fullqty));
			assertThat("Invalid NetLineAmt", invoiceLine1.getNetLineAmt(), comparesEqualTo(fullqty.multiply(priceActual)));

			validateIcIlAllocationQty(ic, invoice1, invoiceLine1, fullqty);

			final IInvoiceCandidateInOutLineToUpdate ic_iol11 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol11);
			assertThat(ic_iol11.getQtyInvoiced(), is(partialQty1));
			final IInvoiceCandidateInOutLineToUpdate ic_iol21 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol21);
			assertThat(ic_iol21.getQtyInvoiced(), is(partialQty2));
			final IInvoiceCandidateInOutLineToUpdate ic_iol22 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol22);
			assertThat(ic_iol22.getQtyInvoiced(), is(partialQty3));
		}

		//
		// Make sure all invoices were evaluated
		assertEquals("All generated invoices should be evaluated", Collections.emptyList(), invoices);
	}
}
