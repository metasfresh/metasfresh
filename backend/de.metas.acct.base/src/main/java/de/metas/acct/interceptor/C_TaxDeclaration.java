package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.ModelValidator;
import org.compiere.SpringContextHolder;

@Interceptor(I_C_TaxDeclaration.class)
public class C_TaxDeclaration
{
	@NonNull private final TaxDeclarationRepository taxDeclarationRepository = SpringContextHolder.instance.getBean(TaxDeclarationRepository.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		taxDeclarationRepository.deleteChildRows(TaxDeclarationId.ofRepoId(taxDeclaration.getC_TaxDeclaration_ID()));
	}
}
