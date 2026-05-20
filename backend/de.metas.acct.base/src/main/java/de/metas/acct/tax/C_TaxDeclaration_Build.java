package de.metas.acct.tax;

import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_TaxDeclaration_Build extends JavaProcess
{
	@NonNull private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);

	@Override
	protected String doIt()
	{
		taxDeclarationService.build(TaxDeclarationId.ofRepoId(getRecord_ID()));
		return MSG_OK;
	}
}
