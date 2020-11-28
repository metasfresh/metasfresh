package de.metas.acct.tax.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;

import de.metas.acct.tax.ITaxDeclarationDAO;
import de.metas.util.Check;
import de.metas.util.Services;

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
