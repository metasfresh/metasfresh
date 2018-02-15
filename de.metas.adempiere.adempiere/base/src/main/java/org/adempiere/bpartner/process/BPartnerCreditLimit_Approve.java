package org.adempiere.bpartner.process;

import org.compiere.model.I_C_BPartner_CreditLimit;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;

public class BPartnerCreditLimit_Approve extends JavaProcess implements IProcessPrecondition
{

	@Param(parameterName = I_C_BPartner_CreditLimit.COLUMNNAME_ApprovedBy_ID, mandatory = true)
	private int approvedBy_ID;

	@Override
	protected String doIt()
	{

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
