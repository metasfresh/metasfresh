package de.metas.dunning.invoice.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.invoice.api.IInvoiceSourceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

/** Gets all open invoices for the selected org and updates dunning grace dates */
public class C_Invoice_UpdateAutomaticDunningGrace extends JavaProcess
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceSourceBL invoiceSourceBL = Services.get(IInvoiceSourceBL.class);

	private static final String PARAM_AD_Org_ID = "AD_Org_ID";

	@Param(mandatory=true, parameterName=PARAM_AD_Org_ID)
	private int p_AD_Org_ID = -1;

	@Override
	protected String doIt()
	{
		final I_AD_Org adOrg = InterfaceWrapperHelper.create(getCtx(), p_AD_Org_ID, I_AD_Org.class, get_TrxName());

		final Iterable<I_C_Invoice> iterable = () -> invoiceDAO.retrieveOpenInvoicesByOrg(adOrg);

		for (final org.compiere.model.I_C_Invoice invoice : iterable)
		{
			invoiceSourceBL.setDunningGraceIfManaged(invoice);
			InterfaceWrapperHelper.save(invoice);
		}

		return MSG_OK;
	}
}
