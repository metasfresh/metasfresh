package org.adempiere.acct.api.impl;

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


import org.adempiere.acct.api.ITaxDeclarationDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;

public class TaxDeclarationDAO implements ITaxDeclarationDAO
{
	@Override
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		// NOTE: first delete the _Accts because there could be some dependencies to lines
		deleteTaxDeclarationAccts(taxDeclaration);
		deleteTaxDeclarationLines(taxDeclaration);
	}

	@Override
	public int deleteTaxDeclarationLines(final I_C_TaxDeclaration taxDeclaration)
	{
		Check.assumeNotNull(taxDeclaration, "taxDeclaration not null");
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxDeclarationLine.class, taxDeclaration)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, taxDeclaration.getC_TaxDeclaration_ID())
				.create()
				.deleteDirectly();
	}

	@Override
	public int deleteTaxDeclarationAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		Check.assumeNotNull(taxDeclaration, "taxDeclaration not null");
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxDeclarationAcct.class, taxDeclaration)
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_C_TaxDeclaration_ID, taxDeclaration.getC_TaxDeclaration_ID())
				.create()
				.deleteDirectly();
	}

	@Override
	public int retrieveLastLineNoOfTaxDeclarationLine(final I_C_TaxDeclaration taxDeclaration)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxDeclarationLine.class, taxDeclaration)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, taxDeclaration.getC_TaxDeclaration_ID())
				.create()
				.aggregate(I_C_TaxDeclarationLine.COLUMNNAME_Line, Aggregate.MAX, Integer.class);
	}

	@Override
	public int retrieveLastLineNoOfTaxDeclarationAcct(final I_C_TaxDeclaration taxDeclaration)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxDeclarationAcct.class, taxDeclaration)
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_C_TaxDeclaration_ID, taxDeclaration.getC_TaxDeclaration_ID())
				.create()
				.aggregate(I_C_TaxDeclarationAcct.COLUMNNAME_Line, Aggregate.MAX, Integer.class);
	}

}
