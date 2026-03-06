package de.metas.edi.callout;

import de.metas.edi.api.impl.EDIDocumentBL;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.SpringContextHolder;
import org.compiere.model.CalloutEngine;
import org.slf4j.MDC.MDCCloseable;

import de.metas.edi.model.I_C_Invoice;
import de.metas.logging.TableRecordMDC;

public class C_Invoice extends CalloutEngine
{
	@NonNull private final EDIDocumentBL ediDocumentBL = SpringContextHolder.instance.getBean(EDIDocumentBL.class);

	public String onC_BPartner_ID(final ICalloutField calloutField)
	{
		final I_C_Invoice invoice = calloutField.getModel(I_C_Invoice.class);
		try (final MDCCloseable mdcCloseable = TableRecordMDC.putTableRecordReference(invoice))
		{
			ediDocumentBL.updateEdiExportStatus(invoice);
			return CalloutEngine.NO_ERROR;
		}
	}
}
