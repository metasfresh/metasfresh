package org.adempiere.bpartner.process;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_CreditLimit;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

public class BPartnerCreditLimit_Approve extends JavaProcess implements IProcessPrecondition
{

	@Override
	protected String doIt()
	{
		final I_C_BPartner_CreditLimit bpCreditLimit = getRecord(I_C_BPartner_CreditLimit.class);
		bpCreditLimit.setApprovedBy_ID(getAD_User_ID());
		bpCreditLimit.setProcessed(true);
		InterfaceWrapperHelper.save(bpCreditLimit);
		return "@Success@";
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_BPartner_CreditLimit.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on C_BPartner_CreditLimit table");
		}

		return ProcessPreconditionsResolution.accept();
	}
}
