package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_TaxDeclaration.class)
public class C_TaxDeclaration
{
	private static final AdMessageKey MSG_Locked = AdMessageKey.of("TaxDeclaration_Locked");

	private final TaxDeclarationRepository taxDeclarationRepository;

	public C_TaxDeclaration(@NonNull final TaxDeclarationRepository taxDeclarationRepository)
	{
		this.taxDeclarationRepository = taxDeclarationRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void rejectEditsWhenLocked(final I_C_TaxDeclaration taxDeclaration)
	{
		final I_C_TaxDeclaration old = InterfaceWrapperHelper.createOld(taxDeclaration, I_C_TaxDeclaration.class);
		// Allow the reactivate flow: Processed Y -> N. Reject every other edit while Processed='Y'.
		if (old.isProcessed() && taxDeclaration.isProcessed())
		{
			throw new AdempiereException(MSG_Locked);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		if (taxDeclaration.isProcessed())
		{
			throw new AdempiereException(MSG_Locked);
		}
		taxDeclarationRepository.deleteChildRows(TaxDeclarationId.ofRepoId(taxDeclaration.getC_TaxDeclaration_ID()));
	}
}
