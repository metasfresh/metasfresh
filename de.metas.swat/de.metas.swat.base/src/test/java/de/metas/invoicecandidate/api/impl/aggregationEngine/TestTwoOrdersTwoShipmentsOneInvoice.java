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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TestTwoOrdersTwoShipmentsOneInvoice extends AbstractTwoOrdersTwoInOutsOneInvoiceTests
{
	@Override
	protected boolean config_IsSOTrx()
	{
		return true;
	}

	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines, final List<IInvoiceHeader> invoices)
	{
		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice1 = invoices.remove(0);

		assertThat(invoice1.getPOReference(), is(IC_PO_REFERENCE));
		assertThat(invoice1.getDateAcct(), is(IC_DATE_ACCT)); // task 08437

		assertThat(invoice1.isSOTrx(), is(config_IsSOTrx()));
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertEquals("We are expecting three invoice lines: " + invoiceLines1, 3, invoiceLines1.size());

		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol111);
		assertNotNull("Missing IInvoiceLineRW for iol11=" + iol111, il1);
		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(TEN));

		final IInvoiceLineRW il2 = getSingleForInOutLine(invoiceLines1, iol121);
		assertNotNull("Missing IInvoiceLineRW for iol21=" + iol121, il2);
		assertThat(il2.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(TWENTY));

		final IInvoiceLineRW il3 = getSingleForInOutLine(invoiceLines1, iol211);
		assertNotNull("Missing IInvoiceLineRW for iol21=" + iol121, il3);
		assertThat(il3.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(FIFTY));
	}
}
