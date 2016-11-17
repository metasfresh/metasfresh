package de.metas.inoutcandidate.process;

import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;

/**
 * Re-open closed receipt schedule.
 *
 * This is counter-part of {@link M_ReceiptSchedule_Close}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08480_Korrekturm%C3%B6glichkeit_Wareneingang_-_Menge%2C_Packvorschrift%2C_Merkmal_%28109195602347%29
 */
public class M_ReceiptSchedule_ReOpen extends JavaProcess implements IProcessPrecondition
{
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		final I_M_ReceiptSchedule receiptSchedule = context.getModel(I_M_ReceiptSchedule.class);

		// Make sure receipt schedule is already closed
		if (!receiptScheduleBL.isClosed(receiptSchedule))
		{
			return false;
		}

		return true;
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getRecord(I_M_ReceiptSchedule.class);
		receiptScheduleBL.reopen(receiptSchedule);

		return MSG_OK;
	}
}
