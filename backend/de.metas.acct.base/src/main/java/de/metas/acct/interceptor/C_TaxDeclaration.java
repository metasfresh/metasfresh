package de.metas.acct.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.ModelValidator;

import de.metas.acct.tax.ITaxDeclarationDAO;
import de.metas.util.Services;

@Interceptor(I_C_TaxDeclaration.class)
public class C_TaxDeclaration
{
	private final ITaxDeclarationDAO taxDeclarationDAO = Services.get(ITaxDeclarationDAO.class);
	
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		taxDeclarationDAO.deleteTaxDeclarationLinesAndAccts(taxDeclaration);
	}
}
