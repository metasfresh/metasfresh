package org.adempiere.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;

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
