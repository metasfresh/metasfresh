package de.metas.payment.sumup.webui.process;

import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class SUMUP_Config_AddCardReader extends JavaProcess
{
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);

	@Param(parameterName = "Name", mandatory = true)
	private String p_Name;

	@Param(parameterName = "SUMUP_PairingCode", mandatory = true)
	private String p_SUMUP_PairingCode;

	@Override
	protected String doIt()
	{
		final SumUpConfigId configId = SumUpConfigId.ofRepoId(getRecord_ID());

		sumUpService.pairCardReader(configId, p_Name, p_SUMUP_PairingCode);
		return MSG_OK;
	}
}
