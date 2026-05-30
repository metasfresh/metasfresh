package de.metas.acct.tax;

import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_TaxDeclaration;

public class C_TaxDeclaration_CreateCorrection extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_DraftExists = AdMessageKey.of("TaxDeclaration_CreateCorrection_DraftExists");

	@NonNull private final TaxDeclarationService taxDeclarationService = SpringContextHolder.instance.getBean(TaxDeclarationService.class);
	@NonNull private final TaxDeclarationRepository taxDeclarationRepository = SpringContextHolder.instance.getBean(TaxDeclarationRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final I_C_TaxDeclaration td = context.getSelectedModel(I_C_TaxDeclaration.class);
		if (td == null || !td.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Tax declaration is not yet locked");
		}

		final TaxDeclarationId originalId = resolveOriginalId(td);
		if (taxDeclarationRepository.hasUnprocessedCorrectionFor(originalId, TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_ID())))
		{
			return ProcessPreconditionsResolution.reject(MSG_DraftExists);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final TaxDeclarationId correctionId = taxDeclarationService.createCorrectionWithDriftCheck(TaxDeclarationId.ofRepoId(getRecord_ID()));
		taxDeclarationService.build(correctionId);
		getResult().setRecordToOpen(I_C_TaxDeclaration.Table_Name, correctionId);
		return MSG_OK;
	}

	private static TaxDeclarationId resolveOriginalId(@NonNull final I_C_TaxDeclaration td)
	{
		return td.isCorrection()
				? TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_Original_ID())
				: TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_ID());
	}
}
