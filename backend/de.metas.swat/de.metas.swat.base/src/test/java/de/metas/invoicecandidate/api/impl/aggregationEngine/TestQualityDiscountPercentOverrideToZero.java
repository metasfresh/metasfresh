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
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

/**
 * Two inout lines, one in-dispute. However, <code>QualityDiscount_Override</code> is set to zero.
 * <p>
 * Expectation: the full qty (including the in-dispute-qty) is invoiced in one invoice line.
 */
public abstract class TestQualityDiscountPercentOverrideToZero extends AbstractTestQualityDiscountPercentOverride
{

	private I_M_InOutLine iol12_twenty_disp;

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		final StockQtyAndUOMQty qtysDelivered_90 = StockQtyAndUOMQtys.create(new BigDecimal("90"), productId, new BigDecimal("900"), uomId);
		final StockQtyAndUOMQty qtysDelivered_10 = StockQtyAndUOMQtys.create(TEN, productId, HUNDRET, uomId);
		{
			final String inOutDocumentNo = "1";
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo, MovementType.CustomerShipment); // DocumentNo
			iol11 = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_90, inOutDocumentNo + "_90"); // inOutLineDescription

			iol12_twenty_disp = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_10, inOutDocumentNo + "_10_disp");
			iol12_twenty_disp.setIsInDispute(true);
			InterfaceWrapperHelper.save(iol12_twenty_disp);

			completeInOut(inOut1);
		}

		return Arrays.asList(iol11);
	}

	@Override
	public BigDecimal config_getQualityDiscount_Override()
	{
		return BigDecimal.ZERO;
	}

	/**
	 * Need to set the QualityDiscountPercent_Override value again, because it was unset by the first run of the IC-update.
	 */
	@Override
	protected void step_updateInvoiceCandidates(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates, 
			@Nullable final List<I_M_InOutLine> inOutLines)
	{
		final I_C_Invoice_Candidate ic = CollectionUtils.singleElement(invoiceCandidates);
		ic.setQualityDiscountPercent_Override(config_getQualityDiscount_Override());

		InterfaceWrapperHelper.save(ic);
	}

	@Override
	protected void step_validate_before_aggregation(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates, 
			@NonNull final List<I_M_InOutLine> inOutLines)
	{
		super.step_validate_before_aggregation(invoiceCandidates, inOutLines);

		final I_C_Invoice_Candidate ic = CollectionUtils.singleElement(invoiceCandidates);

		assertThat(ic.getQualityDiscountPercent(), comparesEqualTo(new BigDecimal("10"))); // 10 out of (90 + 10)

		assertThat(ic.getQtyDelivered(), comparesEqualTo(new BigDecimal("100")));
		assertThat(ic.getQtyToInvoiceBeforeDiscount(), comparesEqualTo(new BigDecimal("100")));

		assertThat(ic.getQtyWithIssues(), comparesEqualTo(new BigDecimal("10")));
		assertThat("Invalid QtyWithIssues_Effective", ic.getQtyWithIssues_Effective(), comparesEqualTo(new BigDecimal("0")));

		assertThat(ic.getQtyToInvoice(), comparesEqualTo(new BigDecimal("100")));

	}

	@Override
	protected void step_validate_after_aggregation(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates,
			@NonNull final List<I_M_InOutLine> inOutLines,
			@NonNull final List<IInvoiceHeader> invoices)
	{
		super.step_validate_after_aggregation(invoiceCandidates, inOutLines, invoices);

		final IInvoiceHeader invoice1 = invoices.remove(0);
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol11);

		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(new BigDecimal("100")));
	}

}
