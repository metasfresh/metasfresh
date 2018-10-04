package de.metas.invoice.callout;

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

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.interfaces.I_C_BPartner;
import de.metas.lang.SOTrx;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.util.Services;

@Callout(I_C_Invoice.class)
public class C_Invoice
{
	@CalloutMethod(columnNames = { I_C_Invoice.COLUMNNAME_C_BPartner_ID, I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID, I_C_Invoice.COLUMNNAME_DateInvoiced })
	public void setPriceListFromBPartner(final I_C_Invoice invoice, final ICalloutField field)
	{
		if (invoice == null)
		{
			return;
		}

		final I_C_BPartner partner = InterfaceWrapperHelper.create(invoice.getC_BPartner(), I_C_BPartner.class);
		if (partner == null)
		{
			return;
		}

		//
		// TODO: Maybe replace this with Check.assume()?
		//
		final SOTrx soTrx = SOTrx.ofBoolean(invoice.isSOTrx());
		if (soTrx.isSales() && !partner.isCustomer())
		{
			//
			// SO => Partner must be a customer
			return;
		}
		else if (soTrx.isPurchase() && !partner.isVendor())
		{
			//
			// PO => Partner must be a vendor
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		final String trxName = InterfaceWrapperHelper.getTrxName(invoice);

		final I_C_BPartner_Location location = invoice.getC_BPartner_Location();
		if (location == null)
		{
			return;
		}

		final PricingSystemId pricingSystemId = Services.get(IBPartnerDAO.class).retrievePricingSystemId(ctx, partner.getC_BPartner_ID(), soTrx, trxName);
		if (pricingSystemId == null)
		{
			return;
		}

		//
		// Get current dateInvoiced or use current time if it's not set
		Timestamp dateInvoiced = invoice.getDateInvoiced();
		if (dateInvoiced == null)
		{
			dateInvoiced = new Timestamp(System.currentTimeMillis());
		}

		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
		final I_M_PriceList priceListNew = priceListBL.getCurrentPricelistOrNull(
				pricingSystemId,
				location.getC_Location().getC_Country_ID(),
				dateInvoiced,
				soTrx);
		if (priceListNew == null)
		{
			return;
		}

		invoice.setM_PriceList_ID(priceListNew.getM_PriceList_ID());
	}

	@CalloutMethod(columnNames = { I_C_Invoice.COLUMNNAME_C_DocTypeTarget_ID, I_C_Invoice.COLUMNNAME_AD_Org_ID })
	public void updateFromDocType(final I_C_Invoice invoice, final ICalloutField field)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(invoice.getC_DocTypeTarget())
				.setOldDocumentNo(invoice.getDocumentNo())
				.setDocumentModel(invoice)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		// FRESH-488: Kept from old callout
		Env.setContext(field.getCtx(), field.getWindowNo(), I_C_DocType.COLUMNNAME_HasCharges, documentNoInfo.isHasChanges());

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			invoice.setDocumentNo(documentNoInfo.getDocumentNo());
		}

		// FRESH-488: Kept from old callout
		// DocBaseType - Set Context
		final String docBaseType = documentNoInfo.getDocBaseType();
		Env.setContext(field.getCtx(), field.getWindowNo(), I_C_DocType.COLUMNNAME_DocBaseType, docBaseType);

		// Task FRESH-488: Set the payment rule to the one from the sys config independent of doctype-letters
		final String paymentRuleToUse = Services.get(IInvoiceBL.class).getDefaultPaymentRule();
		invoice.setPaymentRule(paymentRuleToUse);

		//
		Services.get(IInvoiceBL.class).updateDescriptionFromDocTypeTargetId(invoice, null, null);
	}
}
