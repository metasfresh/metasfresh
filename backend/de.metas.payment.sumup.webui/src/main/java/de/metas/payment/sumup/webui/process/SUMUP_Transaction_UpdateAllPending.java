package de.metas.payment.sumup.webui.process;

import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.repository.UpdateByPendingStatusResult;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class SUMUP_Transaction_UpdateAllPending extends JavaProcess
{
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final UpdateByPendingStatusResult result = sumUpService.updateAllPendingTransactions();
		return "Successfully updated " + result.getCountOK() + " transactions. Got " + result.getCountError() + " errors.";
	}
}
