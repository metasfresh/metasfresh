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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * More that what was delivered shall be invoiced. See {@link #config_GetQtyToInvoice_Override()}.
 *
 * @see AbstractDoubleReceiptQtyOverride
 * @author ts
 *
 */
public class TestDoubleReceiptInvoiceMoreThanReceived extends AbstractDoubleReceiptQtyOverride
{

	@Override
	protected void step_validate_after_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines, List<IInvoiceHeader> invoices)
	{
		// guard
		assertThat(config_IsSOTrx(), is(false));

		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice1 = invoices.remove(0);

		assertThat(invoice1.isSOTrx(), is(config_IsSOTrx()));

		assertThat(invoice1.getPOReference(), is(IC_PO_REFERENCE));
		assertThat(invoice1.getDateAcct(), is(IC_DATE_ACCT));

		assertThat(invoice1.isSOTrx(), is(config_IsSOTrx()));
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertEquals("We are expecting one invoice line: " + invoiceLines1, 1, invoiceLines1.size());

		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol111);
		assertNotNull("Missing IInvoiceLineRW for iol111=" + iol111, il1);
		assertThat(il1.getQtyToInvoice(), comparesEqualTo(config_GetQtyToInvoice_Override()));
		assertThat(il1.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(2));

		final IInvoiceCandidateInOutLineToUpdate icIolToUpdate111 = retrieveIcIolToUpdateIfExists(il1,iol111);
		assertThat(icIolToUpdate111.getQtyInvoiced(), is(FIFTY));

		final IInvoiceCandidateInOutLineToUpdate icIolToUpdate121 =retrieveIcIolToUpdateIfExists(il1, iol121);
		assertThat(icIolToUpdate121.getQtyInvoiced(), is(FIFTY.add(TWENTY)));
	}

	/**
	 * Returns 120.
	 */
	@Override
	BigDecimal config_GetQtyToInvoice_Override()
	{
		return FIFTY.add(FIFTY).add(TWENTY);
	}

}
