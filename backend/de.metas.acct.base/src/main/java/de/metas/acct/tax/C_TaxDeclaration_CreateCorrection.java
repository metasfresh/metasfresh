package de.metas.acct.tax;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_TaxDeclaration;

public class C_TaxDeclaration_CreateCorrection extends JavaProcess implements IProcessPrecondition
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
		if (!taxDeclaration.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Tax declaration is not yet locked");
		}

		if (taxDeclaration.isIsCorrection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Cannot create correction of a correction");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final TaxDeclarationId correctionId = taxDeclarationService.createCorrection(TaxDeclarationId.ofRepoId(getRecord_ID()));
		return "@Created@ " + correctionId.getRepoId();
	}
}
