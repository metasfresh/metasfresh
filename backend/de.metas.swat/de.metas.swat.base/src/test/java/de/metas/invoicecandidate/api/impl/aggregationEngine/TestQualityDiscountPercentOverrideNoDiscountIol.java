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
import de.metas.material.MovementType;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * There is no inoutLine with inDispute, so the quality discount is actually zero. This test sets an override discount and verifies the aggregation result.
 */
public class TestQualityDiscountPercentOverrideNoDiscountIol extends AbstractTestQualityDiscountPercentOverride
{

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		final StockQtyAndUOMQty qtysDelivered_100 = StockQtyAndUOMQtys.create(HUNDRET, productId, THOUSAND, uomId);
		{
			final String inOutDocumentNo = "1";
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo, MovementType.CustomerShipment); // DocumentNo
			iol11 = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_100, inOutDocumentNo); // inOutLineDescription
			completeInOut(inOut1);
		}

		return Collections.singletonList(iol11);
	}

	@Override
	public BigDecimal config_getQualityDiscount_Override()
	{
		return TEN;
	}

	@Override
	protected void step_validate_before_aggregation(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates,
			@NonNull final List<I_M_InOutLine> inOutLines)
	{
		super.step_validate_before_aggregation(invoiceCandidates, inOutLines);

		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		assertThat(ic.getQtyToInvoiceBeforeDiscount()).isEqualByComparingTo("100");
		assertThat(ic.getQtyToInvoice()).isEqualByComparingTo("90");

		// Check QtyWithIssues (from linked inout lines): shall be zero because there are no inout lines with IsInDispute=Y
		assertThat(ic.getQtyWithIssues()).isEqualByComparingTo("0");
		assertThat(ic.getQtyWithIssues_Effective()).isEqualByComparingTo("10");
	}

	@Override
	protected void step_validate_after_aggregation(@NonNull List<I_C_Invoice_Candidate> invoiceCandidates, @NonNull List<I_M_InOutLine> inOutLines, @NonNull List<IInvoiceHeader> invoices)
	{
		super.step_validate_after_aggregation(invoiceCandidates, inOutLines, invoices);

		final IInvoiceHeader invoice1 = invoices.remove(0);
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol11);

		assertThat(il1).isNotNull();
		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualByComparingTo("90");
	}
}
