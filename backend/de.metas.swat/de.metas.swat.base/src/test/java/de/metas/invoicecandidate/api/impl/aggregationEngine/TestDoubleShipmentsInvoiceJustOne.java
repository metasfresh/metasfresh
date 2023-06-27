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

import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestDoubleShipmentsInvoiceJustOne extends AbstractDoubleReceiptQtyOverride
{
	@Override
	public void init()
	{
		super.init();
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	@Override
	protected void step_validate_after_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines, List<IInvoiceHeader> invoices)
	{
		Assertions.assertThat(invoices).as("We are expecting one invoice: " + invoices).hasSize(1);

		final IInvoiceHeader invoice1 = invoices.remove(0);

		assertThat(invoice1.getPOReference(), is(IC_PO_REFERENCE));
		assertThat(invoice1.getDateAcct(), is(IC_DATE_ACCT));

		assertThat(invoice1.isSOTrx(), is(config_IsSOTrx()));
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		Assertions.assertThat(invoiceLines1).as("We are expecting one invoice line: " + invoiceLines1).hasSize(1);

		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol111);
		Assertions.assertThat(il1).as("Missing IInvoiceLineRW for iol111=" + iol111).isNotNull();
		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(config_GetQtyToInvoice_Override()));

		final IInvoiceLineRW il2 = getSingleForInOutLine(invoiceLines1, iol121);
		Assertions.assertThat(il2).as("Unexpected IInvoiceLineRW for iol121=" + iol121).isNull();
	}

	@Override
	BigDecimal config_GetQtyToInvoice_Override()
	{
		return FIFTY;
	}

}
