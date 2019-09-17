package de.metas.invoice.callout;

import java.time.LocalDate;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.interfaces.I_C_BPartner;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.payment.PaymentRule;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

@Callout(I_C_Invoice.class)
@Component
public class C_Invoice
{
	public C_Invoice()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

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

		final I_C_BPartner_Location location = invoice.getC_BPartner_Location();
		if (location == null)
		{
			return;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());
		final PricingSystemId pricingSystemId = Services.get(IBPartnerDAO.class).retrievePricingSystemId(bpartnerId, soTrx);
		if (pricingSystemId == null)
		{
			return;
		}

		//
		// Get current dateInvoiced or use current time if it's not set
		LocalDate dateInvoiced = TimeUtil.asLocalDate(invoice.getDateInvoiced());
		if (dateInvoiced == null)
		{
			dateInvoiced = SystemTime.asLocalDate();
		}

		final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
		final I_M_PriceList priceListNew = priceListBL.getCurrentPricelistOrNull(
				pricingSystemId,
				CountryId.ofRepoId(location.getC_Location().getC_Country_ID()),
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
		final PaymentRule paymentRule = Services.get(IInvoiceBL.class).getDefaultPaymentRule();
		invoice.setPaymentRule(paymentRule.getCode());

		//
		Services.get(IInvoiceBL.class).updateDescriptionFromDocTypeTargetId(invoice, null, null);
	}
}
