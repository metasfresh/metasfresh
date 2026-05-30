package de.metas.acct.tax;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_TaxDeclaration_CheckDrift extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final TaxDeclarationService taxDeclarationService =
			SpringContextHolder.instance.getBean(TaxDeclarationService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		taxDeclarationService.checkDrift(TaxDeclarationId.ofRepoId(getRecord_ID()));
		return MSG_OK;
	}
}
