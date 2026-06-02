package de.metas.acct.tax;

import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_TaxDeclaration;

public class C_TaxDeclaration_CheckDrift extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_NotLatest = AdMessageKey.of("TaxDeclaration_CheckCorrectionNeed_NotLatest");

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

		final I_C_TaxDeclaration td = context.getSelectedModel(I_C_TaxDeclaration.class);
		if (td == null || !td.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Declaration is not yet completed");
		}

		if (!taxDeclarationService.isLatestInChain(TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_ID())))
		{
			return ProcessPreconditionsResolution.reject(MSG_NotLatest);
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
