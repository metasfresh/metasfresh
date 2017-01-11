package org.adempiere.invoice.process;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.model.I_C_DocType;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

public class CreateAdjustmentChargeFromInvoice extends JavaProcess
{
	private int AD_Table_ID = 0;
	private int Record_ID = 0;
	
	@Param(mandatory = true, parameterName = "C_DocType_ID")
	private int p_C_DocType_ID;

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), de.metas.adempiere.model.I_C_Invoice.class, get_TrxName());
		
		final I_C_DocType docType = InterfaceWrapperHelper.create(getCtx(), p_C_DocType_ID, I_C_DocType.class, get_TrxName());
		
		final String docSubType = docType.getDocSubType();
		final I_C_Invoice adjustmentCharge = Services.get(IInvoiceBL.class).adjustmentCharge(invoice, docSubType);
		AD_Table_ID = InterfaceWrapperHelper.getModelTableId(adjustmentCharge);
		Record_ID = InterfaceWrapperHelper.getId(adjustmentCharge);

		return null;
	}

	@Override
	protected void postProcess(boolean success)
	{
		AEnv.zoom(AD_Table_ID, Record_ID);
	}

}
