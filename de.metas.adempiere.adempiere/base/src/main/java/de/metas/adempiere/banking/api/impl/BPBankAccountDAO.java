package de.metas.adempiere.banking.api.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_BankAccount;

import de.metas.adempiere.banking.api.IBPBankAccountDAO;

public class BPBankAccountDAO implements IBPBankAccountDAO
{

	@Override
	public List<I_C_BP_BankAccount> retrieveBankAccountsForPartnerAndCurrency(Properties ctx, int partnerID, int currencyID)
	{
		final IQueryBuilder<I_C_BP_BankAccount> qb = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_BankAccount.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMN_C_BPartner_ID, partnerID);

		if (currencyID > 0)
		{
			qb.addEqualsFilter(I_C_BP_BankAccount.COLUMN_C_Currency_ID, currencyID);
		}

		final List<I_C_BP_BankAccount> bpBankAccounts = qb.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_BP_BankAccount.COLUMN_C_BP_BankAccount_ID)
				.endOrderBy()
				.create()
				.list();

		return bpBankAccounts;
		// return LegacyAdapters.convertToPOArray(bpBankAccounts, MBPBankAccount.class);
	}	// getOfBPartner
}
