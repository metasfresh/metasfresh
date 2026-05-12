package de.metas.acct.tax;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

@Service
public class TaxDeclarationService
{
	public void build(final TaxDeclarationId id)
	{
		DB.executeUpdateAndThrowExceptionOnFail(
				"SELECT de_metas_acct.tax_declaration_build(?)",
				new Object[] { id.getRepoId() },
				ITrx.TRXNAME_ThreadInherited);
	}
}
