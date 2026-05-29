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

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import org.assertj.core.api.Assertions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestTwoOrdersTwoReceiptsOneInvoice extends AbstractTwoOrdersTwoInOutsOneInvoiceTests
{
	@Override
	protected boolean config_IsSOTrx()
	{
		return false;
	}

	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines, final List<IInvoiceHeader> invoices)
	{
		assertThat(invoices).as("We are expecting one invoice: %s", invoices).hasSize(1);

		final IInvoiceHeader invoice1 = invoices.remove(0);

		assertThat(invoice1.getPOReference()).isEqualTo(IC_PO_REFERENCE);
		assertThat(invoice1.getDateAcct()).isEqualTo(IC_DATE_ACCT); // task 08437

		assertThat(invoice1.isSOTrx()).isEqualTo(config_IsSOTrx());
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertThat(invoiceLines1).as("We are expecting two invoice lines: %s", invoiceLines1).hasSize(2);

		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol111);
		assertThat(il1).as("Missing IInvoiceLineRW for iol11=%s", iol111).isNotNull();

		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualTo(TEN.add(TWENTY));

		assertThat(getSingleForInOutLine(invoiceLines1, iol121)).isEqualTo(il1);

		final IInvoiceLineRW il2 = getSingleForInOutLine(invoiceLines1, iol211);
		Assertions.assertThat(il2).as("Missing IInvoiceLineRW for iol21=%s", iol211).isNotNull();
		assertThat(il2.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualByComparingTo(FIFTY);
	}
}
