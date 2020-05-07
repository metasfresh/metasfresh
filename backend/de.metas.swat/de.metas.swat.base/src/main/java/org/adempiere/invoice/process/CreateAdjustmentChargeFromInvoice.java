package org.adempiere.invoice.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

public class CreateAdjustmentChargeFromInvoice extends JavaProcess
{
	@Param(mandatory = true, parameterName = "C_DocType_ID")
	private int p_C_DocType_ID;

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), de.metas.adempiere.model.I_C_Invoice.class, get_TrxName());

		final I_C_DocType docType = InterfaceWrapperHelper.create(getCtx(), p_C_DocType_ID, I_C_DocType.class, get_TrxName());

		final String docSubType = docType.getDocSubType();
		Services.get(IInvoiceBL.class).adjustmentCharge(invoice, docSubType);

		return MSG_OK;
	}
}
