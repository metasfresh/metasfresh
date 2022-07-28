package de.metas.invoice.callout;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapter;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.payment.PaymentRule;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Callout(I_C_Invoice.class)
@Component
public class C_Invoice
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final IDocumentLocationBL documentLocationBL;

	public C_Invoice(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.documentLocationBL = documentLocationBL;

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

		if (invoice.getC_BPartner_ID() <= 0)
		{
			return;
		}

		final I_C_BPartner partner = bpartnerDAO.getById(invoice.getC_BPartner_ID());

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

		if (invoice.getC_BPartner_Location_ID() <= 0)
		{
			return;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());
		final PricingSystemId pricingSystemId = bpartnerDAO.retrievePricingSystemIdOrNull(bpartnerId, soTrx);
		if (pricingSystemId == null)
		{
			return;
		}

		//
		// Get current dateInvoiced or use current time if it's not set
		ZonedDateTime dateInvoiced = TimeUtil.asZonedDateTime(invoice.getDateInvoiced());
		if (dateInvoiced == null)
		{
			dateInvoiced = SystemTime.asZonedDateTime();
		}

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(invoice.getC_BPartner_ID(), invoice.getC_BPartner_Location_ID());

		final I_M_PriceList priceListNew = priceListBL.getCurrentPricelistOrNull(
				pricingSystemId,
				bpartnerDAO.getCountryId(bpartnerLocationId),
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
		final I_C_DocType docTypeRecord = loadOutOfTrx(invoice.getC_DocTypeTarget_ID(), I_C_DocType.class);

		final IDocumentNoInfo documentNoInfo = documentNoBuilderFactory
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docTypeRecord)
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
		final PaymentRule paymentRule = invoiceBL.getDefaultPaymentRule();
		invoice.setPaymentRule(paymentRule.getCode());

		//
		invoiceBL.updateDescriptionFromDocTypeTargetId(invoice, null, null);
	}

	@CalloutMethod(columnNames = I_C_Invoice.COLUMNNAME_DateInvoiced)
	public void updateFromDateInvoiced(final I_C_Invoice invoice, final ICalloutField field)
	{
		invoice.setDateAcct(invoice.getDateInvoiced());
	}

	@CalloutMethod(columnNames = {
			I_C_Invoice.COLUMNNAME_C_BPartner_ID,
			I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID,
			I_C_Invoice.COLUMNNAME_AD_User_ID },
			skipIfCopying = true)
	public void updateBPartnerAddress(final I_C_Invoice invoice)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice));
	}

	@CalloutMethod(columnNames = {
			I_C_Invoice.COLUMNNAME_C_BPartner_ID,
			I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID },
			skipIfCopying = true)
	public void updateBPartnerAddressForceUpdateCapturedLocation(final I_C_Invoice invoice)
	{
		documentLocationBL.updateCapturedLocation(InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice));

	}

}
