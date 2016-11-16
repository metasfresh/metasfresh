package de.metas.banking.process;

import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;

import de.metas.banking.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_Payment;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.process.ISvrProcessPrecondition;
import de.metas.process.SvrProcess;

/**
 * Creates an links {@link I_C_Payment}s to {@link I_C_PaySelectionLine}s.
 *
 * @author tsa
 *
 */
public class C_PaySelection_CreatePayments extends SvrProcess implements ISvrProcessPrecondition
{
	// services
	private final transient IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_C_PaySelection paySelection = context.getModel(I_C_PaySelection.class);
		if (!paySelection.isProcessed())
		{
			return false;
		}

		return true;
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_PaySelection paySelection = getRecord(I_C_PaySelection.class);
		paySelectionBL.createPayments(paySelection);
		return MSG_OK;
	}
}
