package de.metas.payment.sumup.webui.process;

import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.repository.BulkUpdateByQueryResult;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

public class SUMUP_Transaction_UpdateSingleDocument extends JavaProcess
{
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final SumUpTransactionId id = SumUpTransactionId.ofRepoId(getRecord_ID());
		final BulkUpdateByQueryResult result = sumUpService.bulkUpdateTransactions(SumUpTransactionQuery.ofLocalId(id), true);
		return result.getSummary().translate(Env.getADLanguageOrBaseLanguage());
	}
}
