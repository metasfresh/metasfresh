package de.metas.acct.tax;

import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import org.compiere.SpringContextHolder;

public class C_TaxDeclaration_Build extends JavaProcess
{
	private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		taxDeclarationService.build(TaxDeclarationId.ofRepoId(getRecord_ID()));
		return MSG_OK;
	}
}
