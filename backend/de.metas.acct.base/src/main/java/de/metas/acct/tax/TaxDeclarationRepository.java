package de.metas.acct.tax;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

@Repository
public class TaxDeclarationRepository
{
	public I_C_TaxDeclaration getById(final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = InterfaceWrapperHelper.load(id.getRepoId(), I_C_TaxDeclaration.class);
		if (record == null)
		{
			throw new AdempiereException("No C_TaxDeclaration found for id=" + id.getRepoId());
		}
		return record;
	}

	public void deleteChildRows(final TaxDeclarationId id)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM C_TaxDeclarationAcct WHERE C_TaxDeclaration_ID=?",
				new Object[] { id.getRepoId() },
				trxName);
		DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM C_TaxDeclarationLine WHERE C_TaxDeclaration_ID=?",
				new Object[] { id.getRepoId() },
				trxName);
	}
}
