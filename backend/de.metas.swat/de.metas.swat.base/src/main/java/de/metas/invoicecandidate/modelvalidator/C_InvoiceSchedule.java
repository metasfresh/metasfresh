package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.invoicecandidate.api.IInvoiceCandBL;

public class C_InvoiceSchedule implements ModelValidator
{
	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		engine.addModelChange(I_C_InvoiceSchedule.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_DELETE)
		{
			final I_C_InvoiceSchedule invoiceSchedule = InterfaceWrapperHelper.create(po, I_C_InvoiceSchedule.class);
			final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

			invoiceCandBL.invalidateForInvoiceSchedule(invoiceSchedule);
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

}
