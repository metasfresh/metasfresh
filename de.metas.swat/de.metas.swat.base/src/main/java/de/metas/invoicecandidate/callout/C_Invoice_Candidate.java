package de.metas.invoicecandidate.callout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;

@Callout(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	@CalloutMethod(columnNames = {
			I_C_Invoice_Candidate.COLUMNNAME_Discount_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_PriceActual //
			, I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice //
	})
	public void updatePriceAndAmt(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceScheduleBL = Services.get(IInvoiceCandBL.class);

		if (ic.getC_ILCandHandler_ID() <= 0)
		{
			// Case: this is a new record and C_ILCandHandler_ID was not already set. Skip it for now
			return;
		}

		// first chek price actual override
		invoiceScheduleBL.setPriceActual_Override(ic);

		invoiceScheduleBL.setNetAmtToInvoice(ic);

		invoiceScheduleBL.setPriceActualNet(ic);
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_IsManual)
	public void onIsManual(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandlerDAO handlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		if (ic.isManual())
		{
			final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
			final I_C_ILCandHandler handler = handlerDAO.retrieveForTable(ctx, ManualCandidateHandler.MANUAL).get(0);
			final Timestamp today = invoiceCandBL.getToday();

			ic.setC_ILCandHandler(handler);
			ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_EFFECTIVE_Sofort);
			// ic.setQtyToInvoice(BigDecimal.ONE); setting this qty is don my the update process
			ic.setQtyDelivered(BigDecimal.ONE);
			ic.setQtyOrdered(BigDecimal.ONE);
			ic.setDateToInvoice(today);
			ic.setDateToInvoice_Effective(today);
			// we have no source
			ic.setAD_Table_ID(0);
			ic.setRecord_ID(0);

			ic.setC_Invoice_Candidate_Agg(null);
			Services.get(IAggregationBL.class).resetHeaderAggregationKey(ic);
			ic.setLineAggregationKey(null);
			//
			invoiceCandBL.set_QtyInvoiced_NetAmtInvoiced_Aggregation(ctx, ic);
			//
			if (ic.getBill_BPartner_ID() > 0)
			{
				setPricingSystem(ctx, ic);
			}
		}
		else
		{
			ic.setC_ILCandHandler(null);
			ic.setInvoiceRule(null);
			ic.setQtyToInvoice_Override(BigDecimal.ZERO);
			// we have no source
			ic.setAD_Table_ID(0);
			ic.setRecord_ID(0);
			ic.setC_Invoice_Candidate_Agg(null);
			ic.setLineAggregationKey(null);
		}
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID)
	public void onBill_BPartner_ID(final I_C_Invoice_Candidate ic)
	{
		if (ic.isManual() && ic.getBill_BPartner_ID() > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			setPricingSystem(ctx, ic);
		}
	}

	private void setPricingSystem(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bPartnerPA = Services.get(IBPartnerDAO.class);
		final int pricingSysId = bPartnerPA.retrievePricingSystemId(ctx, ic.getBill_BPartner_ID(), ic.isSOTrx(), ITrx.TRXNAME_None);
		ic.setM_PricingSystem_ID(pricingSysId);
	}

	@CalloutMethod(columnNames = { I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override })
	public void onQualityDiscountPercentOverride(final I_C_Invoice_Candidate ic, final ICalloutField field)
	{
		ic.setIsInDispute(false);
	}
}
