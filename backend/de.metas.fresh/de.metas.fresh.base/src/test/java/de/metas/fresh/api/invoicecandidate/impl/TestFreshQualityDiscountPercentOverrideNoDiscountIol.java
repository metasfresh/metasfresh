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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.fresh.invoicecandidate.spi.impl.FreshQuantityDiscountAggregator;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.impl.aggregationEngine.TestQualityDiscountPercentOverrideNoDiscountIol;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import lombok.NonNull;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Checks the {@link FreshQuantityDiscountAggregator} when using {@link I_C_Invoice_Candidate#setQualityDiscountPercent_Override(BigDecimal)}.
 * <p>
 * Note that there is also no in-dispute iol in this case.
 */
public class TestFreshQualityDiscountPercentOverrideNoDiscountIol extends TestQualityDiscountPercentOverrideNoDiscountIol
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

		// Make sure it's using our aggregator
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);
		assertThat(ic.getC_Invoice_Candidate_Agg(), is(freshAgg));
	}

	@Override
	protected void step_validate_after_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> inOutLines, final @NonNull List<IInvoiceHeader> invoices)
	{
		Assertions.assertThat(invoices).as("We are expecting one invoice: " + invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.remove(0);

		assertThat(invoice.getPOReference(), is(IC_PO_REFERENCE));
		assertThat(invoice.getDateAcct(), is(IC_DATE_ACCT));

		assertThat(invoice.isSOTrx(), is(false));

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		Assertions.assertThat(invoiceLines).as("We are expecting two invoice lines: " + invoiceLines).hasSize(2);

		final IInvoiceLineRW invoiceLine1 = invoiceLines.get(0);
		assertThat("Invalid invoice line 1 - QtyToInvoice", invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(new BigDecimal("100")));

		final InvoiceCandidateInOutLineToUpdate icIolToUpdate11 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol11);
		assertThat(icIolToUpdate11.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(new BigDecimal("900")));

		final IInvoiceLineRW invoiceLine2 = invoiceLines.get(1);
		assertThat("Invalid invoice line 2 - QtyToInvoice", invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(new BigDecimal("-10")));
	}
}
