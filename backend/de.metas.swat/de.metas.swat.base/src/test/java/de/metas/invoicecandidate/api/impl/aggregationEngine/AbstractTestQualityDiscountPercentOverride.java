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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public abstract class AbstractTestQualityDiscountPercentOverride extends AbstractNewAggregationEngineTests
{
	protected I_M_InOut inOut1;
	protected I_M_InOutLine iol11;

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		return createInvoiceCandidate()
				.setInstanceName("ic1")
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(100)
				.setSOTrx(false)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				// Required because QualityDiscountPercent_Override will be applied to the QtyDelivered, and if we have invoiceRule "immediate", then qtyDelivered make a difference at all.
				.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery)
				.setQualityDiscountPercent_Override(config_getQualityDiscount_Override()) // manually set Quality Discount Percent
				.setPOReference(IC_PO_REFERENCE)
				.setDateAcct(IC_DATE_ACCT)
				.buildAsSigletonList();
	}

	/**
	 * Important: make up your mind if you want to return zero or <code>null</code>!
	 */
	public abstract BigDecimal config_getQualityDiscount_Override();

	/**
	 * Checks the stuff that shall be the same among all subclasses/tests.
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	protected void step_validate_before_aggregation(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates, 
			@NonNull final List<I_M_InOutLine> inOutLines)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		if (config_getQualityDiscount_Override() == null)
		{
			assertThat(InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override), is(true));
		}
		else
		{
			assertThat(InterfaceWrapperHelper.isNull(ic, I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override), is(false));
			assertThat(ic.getQualityDiscountPercent_Override(), comparesEqualTo(config_getQualityDiscount_Override()));
		}
		// Required because QualityDiscountPercent_Override will be applied to the QtyDelivered, and if we have invoiceRule "immediate", then qtyDelivered make a difference at all.
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(ic, I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule), is(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery));
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	protected void step_validate_after_aggregation(
			@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates,
			@NonNull final List<I_M_InOutLine> inOutLines,
			@NonNull final List<IInvoiceHeader> invoices)
	{
		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice1 = invoices.get(0); // don't remove, because the subclass implementation might also want to get it.

		assertThat(invoice1.getPOReference(), is(IC_PO_REFERENCE));
		assertThat(invoice1.getDateAcct(), is(IC_DATE_ACCT));

		assertThat(invoice1.isSOTrx(), is(false));

		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertEquals("We are expecting one invoice line: " + invoiceLines1, 1, invoiceLines1.size());

		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol11);
		assertNotNull("Missing IInvoiceLineRW for iol111=" + iol11, il1);
		assertThat(il1.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(1));
	}
}
