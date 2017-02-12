package de.metas.banking.process;

import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;

import de.metas.banking.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/**
 * Creates an links {@link I_C_Payment}s to {@link I_C_PaySelectionLine}s.
 *
 * @author tsa
 *
 */
public class C_PaySelection_CreatePayments extends JavaProcess implements IProcessPrecondition
{
	// services
	private final transient IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final I_C_PaySelection paySelection = context.getSelectedModel(I_C_PaySelection.class);
		if (!paySelection.isProcessed())
		{
			return ProcessPreconditionsResolution.reject("not processed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_PaySelection paySelection = getRecord(I_C_PaySelection.class);
		paySelectionBL.createPayments(paySelection);
		return MSG_OK;
	}
}
