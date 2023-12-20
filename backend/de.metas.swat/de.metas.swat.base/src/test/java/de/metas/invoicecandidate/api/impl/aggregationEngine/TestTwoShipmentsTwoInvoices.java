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

import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * => Expectation: one invoice, but two lines, because on the sales side, only iols that belong to the same inOut can be aggregated into one invoice line.
 */
public class TestTwoShipmentsTwoInvoices extends AbstractTwoInOutsTests
{
	@Override
	public void init()
	{
		super.init();
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	@Override
	protected final boolean config_IsSOTrx()
	{
		return true;
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
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		assertThat(invoices).hasSize(1);

		//
		// Assume that the invoices are OK
		{
			// final IInvoiceHeader invoice1 = removeInvoiceHeaderForInOutId(invoices, inOut1.getM_InOut_ID());
			final IInvoiceHeader invoice1 = invoices.remove(0);
			assertThat(invoice1.isSOTrx()).isEqualTo(config_IsSOTrx());
			validateInvoiceHeader("Invoice", invoice1, ic);

			final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
			assertThat(invoiceLines1).as("We are expecting two invoice lines: " + invoiceLines1).hasSize(2);

			final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines1, iol11);
			assertThat(invoiceLine1).isNotNull();
			assertThat(invoiceLine1.getC_InvoiceCandidate_InOutLine_IDs()).hasSize(1);
			assertThat(invoiceLine1.getPriceActual().toBigDecimal().intValueExact()).as("Invalid PriceActual").isEqualTo(1);
			assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualByComparingTo(partialQty1_32);
			assertThat(invoiceLine1.getNetLineAmt().toBigDecimal()).isEqualByComparingTo(partialQty1_32.multiply(TEN)); // price=1 and uomQty is stockQty x 10

			// validate the IC<->IL qty allocation

			validateIcIlAllocationQty(ic, invoice1, invoiceLine1, partialQty1_32);

			final IInvoiceLineRW invoiceLine2 = getSingleForInOutLine(invoiceLines1, iol21);
			assertThat(getSingleForInOutLine(invoiceLines1, iol22)).as("iol21 and iol22 have the same IInvoiceLineRW").isEqualTo(invoiceLine2);
			assertThat(invoiceLine2).isNotNull();
			assertThat(invoiceLine2.getC_InvoiceCandidate_InOutLine_IDs()).hasSize(2);
			assertThat(invoiceLine2.getPriceActual().toBigDecimal().intValueExact()).as("Invalid PriceActual").isEqualTo(1);
			assertThat(invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualByComparingTo(partialQty2_8.add(partialQty3_4));
			assertThat(invoiceLine2.getNetLineAmt().toBigDecimal()).isEqualByComparingTo(partialQty2_8.add(partialQty3_4).multiply(TEN)); // price=1 and uomQty is stockQty x 10

			validateIcIlAllocationQty(ic, invoice1, invoiceLine2, partialQty2_8.add(partialQty3_4));
		}

		//
		// Make sure all invoices were evaluated
		assertThat(invoices).as("All generated invoices should be evaluated").isEmpty();
	}
}
