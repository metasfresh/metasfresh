package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_TaxDeclaration.class)
public class C_TaxDeclaration
{
	private final TaxDeclarationRepository taxDeclarationRepository;

	public C_TaxDeclaration(@NonNull final TaxDeclarationRepository taxDeclarationRepository)
	{
		this.taxDeclarationRepository = taxDeclarationRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		taxDeclarationRepository.deleteChildRows(TaxDeclarationId.ofRepoId(taxDeclaration.getC_TaxDeclaration_ID()));
	}
}
