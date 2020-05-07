package de.metas.edi.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;

import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_C_Invoice;

public class C_Invoice extends CalloutEngine
{
	public String onC_BPartner_ID(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);

		Services.get(IEDIDocumentBL.class).updateEdiEnabled(invoice);

		return CalloutEngine.NO_ERROR;
	}
}
