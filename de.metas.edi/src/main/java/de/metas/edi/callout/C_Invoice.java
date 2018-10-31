package de.metas.edi.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.CalloutEngine;

import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_C_Invoice;
import de.metas.util.Services;

public class C_Invoice extends CalloutEngine
{
	public String onC_BPartner_ID(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);

		Services.get(IEDIDocumentBL.class).updateEdiEnabled(invoice);

		return CalloutEngine.NO_ERROR;
	}
}
