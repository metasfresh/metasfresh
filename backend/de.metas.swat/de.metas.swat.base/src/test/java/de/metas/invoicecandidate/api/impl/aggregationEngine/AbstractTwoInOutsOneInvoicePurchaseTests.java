/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.ProductPrice;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(config_IsSOTrx()).isFalse();

		final BigDecimal fullqty = partialQty1_32.add(partialQty2_8).add(partialQty3_4);

		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		//
		// Assume that the invoice is OK
		{
			assertThat(invoices).hasSize(1);
			final IInvoiceHeader invoice1 = invoices.remove(0);
			assertThat(invoice1.isSOTrx()).isEqualTo(config_IsSOTrx());
			validateInvoiceHeader("Invoice", invoice1, ic);

			final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
			assertThat(invoiceLines1).as("We are expecting one invoice line: %s", invoiceLines1).hasSize(1);

			final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines1, iol11);
			assertThat(invoiceLine1.getC_InvoiceCandidate_InOutLine_IDs()).hasSize(3);

			final ProductPrice priceActual = invoiceCandBL.getPriceActual(ic);
			final ProductPrice priceEntered = invoiceCandBL.getPriceEnteredEffective(ic);

			assertThat(invoiceLine1.getPriceEntered().toBigDecimal()).as("Invalid PriceEntered").isEqualByComparingTo(priceEntered.toBigDecimal());
			assertThat(invoiceLine1.getPriceActual().toBigDecimal()).as("Invalid PriceActual").isEqualByComparingTo(priceActual.toBigDecimal());

			assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal()).as("Invalid QtysToInvoice").isEqualByComparingTo(fullqty);
			assertThat(invoiceLine1.getNetLineAmt().toBigDecimal()).as("Invalid NetLineAmt").isEqualByComparingTo(fullqty.multiply(priceActual.toBigDecimal()).multiply(TEN));
			

			validateIcIlAllocationQty(ic, invoice1, invoiceLine1, fullqty);

			final InvoiceCandidateInOutLineToUpdate ic_iol11 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol11);
			assertThat(ic_iol11.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal()).isEqualTo(partialQty1_32.multiply(TEN));
			final InvoiceCandidateInOutLineToUpdate ic_iol21 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol21);
			assertThat(ic_iol21.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(partialQty2_8.multiply(TEN));
			final InvoiceCandidateInOutLineToUpdate ic_iol22 = retrieveIcIolToUpdateIfExists(invoiceLine1, iol22);
			assertThat(ic_iol22.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal()).isEqualByComparingTo(partialQty3_4.multiply(TEN));
		}

		//
		// Make sure all invoices were evaluated
		assertThat(invoices).as("All generated invoices should be evaluated").isEmpty();
	}
}
