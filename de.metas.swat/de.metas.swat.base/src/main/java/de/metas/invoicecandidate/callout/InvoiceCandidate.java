package de.metas.invoicecandidate.callout;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;

public class InvoiceCandidate extends CalloutEngine
{
	public String amt(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		final IInvoiceCandBL invoiceScheduleBL = Services.get(IInvoiceCandBL.class);

		final I_C_Invoice_Candidate ic = GridTabWrapper.create(mTab, I_C_Invoice_Candidate.class);

		if (ic.getC_ILCandHandler_ID() <= 0)
		{
			// Case: this is a new record and C_ILCandHandler_ID was not already set. Skip it for now
			return NO_ERROR;
		}

		// first chek price actual override
		invoiceScheduleBL.setPriceActual_Override(ic); 
		
		invoiceScheduleBL.setNetAmtToInvoice(ic);
		
		invoiceScheduleBL.setPriceActualNet(ic);

		return NO_ERROR;
	}

	public String IsManual(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		final IInvoiceCandidateHandlerDAO handlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);

		final I_C_Invoice_Candidate ic = GridTabWrapper.create(mTab, I_C_Invoice_Candidate.class);
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

		return NO_ERROR;
	}

	public String Bill_BPartner_ID(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{

		final I_C_Invoice_Candidate ic = GridTabWrapper.create(mTab, I_C_Invoice_Candidate.class);

		if (ic.isManual() && ic.getBill_BPartner_ID() > 0)
		{
			setPricingSystem(ctx, ic);
		}

		return NO_ERROR;
	}

	private void setPricingSystem(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bPartnerPA = Services.get(IBPartnerDAO.class);
		final int pricingSysId = bPartnerPA.retrievePricingSystemId(ctx, ic.getBill_BPartner_ID(), ic.isSOTrx(), ITrx.TRXNAME_None);
		ic.setM_PricingSystem_ID(pricingSysId);
	}

	public static final String METHODNAME_setHeaderAggregationKey = "setHeaderAggregationKey";

	/**
	 * Update HeaderAggregationKey
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String setHeaderAggregationKey(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		final IAggregationBL aggregationBL = Services.get(IAggregationBL.class);
		
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(mTab, I_C_Invoice_Candidate.class);
		aggregationBL.setHeaderAggregationKey(ic);

		return NO_ERROR;
	}
}
