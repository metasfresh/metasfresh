package de.metas.payment.sumup.webui.process;

import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpService;
import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class SUMUP_Config_UpdateCardReadersFromRemote extends JavaProcess
{
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);

	@Override
	protected String doIt()
	{
		final SumUpConfigId configId = SumUpConfigId.ofRepoId(getRecord_ID());
		sumUpService.updateCardReadersFromRemote(configId);
		return MSG_OK;
	}
}
