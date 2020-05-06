package de.metas.acct.tax;

import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;

import de.metas.util.ISingletonService;

public interface ITaxDeclarationDAO extends ISingletonService
{
	/**
	 * Deletes {@link I_C_TaxDeclarationLine}s and {@link I_C_TaxDeclarationAcct}s.
	 * 
	 * @param taxDeclaration
	 */
	void deleteTaxDeclarationLinesAndAccts(I_C_TaxDeclaration taxDeclaration);

	/**
	 * 
	 * @param taxDeclaration
	 * @return how many {@link I_C_TaxDeclarationLine}s were deleted
	 */
	int deleteTaxDeclarationLines(I_C_TaxDeclaration taxDeclaration);

	/**
	 * 
	 * @param taxDeclaration
	 * @return how many {@link I_C_TaxDeclarationAcct}s were deleted
	 */
	int deleteTaxDeclarationAccts(I_C_TaxDeclaration taxDeclaration);

	int retrieveLastLineNoOfTaxDeclarationLine(I_C_TaxDeclaration taxDeclaration);

	int retrieveLastLineNoOfTaxDeclarationAcct(I_C_TaxDeclaration taxDeclaration);
}
