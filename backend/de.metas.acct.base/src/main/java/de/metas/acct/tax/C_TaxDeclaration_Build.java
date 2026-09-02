package de.metas.acct.tax;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_TaxDeclaration;

public class C_TaxDeclaration_Build extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final I_C_TaxDeclaration taxDeclaration = context.getSelectedModel(I_C_TaxDeclaration.class);
		if (taxDeclaration.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Tax declaration is already processed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		taxDeclarationService.build(TaxDeclarationId.ofRepoId(getRecord_ID()));
		return MSG_OK;
	}
}
