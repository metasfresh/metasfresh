package de.metas.invoicecandidate.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.util.Services;

@Callout(I_C_ILCandHandler.class)
public class C_ILCandHandler
{
	@CalloutMethod(columnNames=I_C_ILCandHandler.COLUMNNAME_Classname)
	public void onClassname(final I_C_ILCandHandler ilCandGenerator)
	{
		final boolean failIfClassNotFound = false;
		Services.get(IInvoiceCandidateHandlerBL.class).evalClassName(ilCandGenerator, failIfClassNotFound);
	}
}
