package de.metas.payment.esr.api.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import lombok.NonNull;

public abstract class AbstractBPBankAccountDAO implements IESRBPBankAccountDAO
{
	@Override
	public List<I_C_BP_BankAccount> retrieveESRBPBankAccounts(@NonNull final String postAccountNo, @NonNull final String innerAccountNo)
	{
		Check.assumeNotEmpty(postAccountNo, "postAccountNo not null");
		Check.assumeNotEmpty(innerAccountNo, "innerAccountNo not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_BP_BankAccount> bpBankAccountESRRenderedAccountNo = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_ESR_RenderedAccountNo, postAccountNo);

		final IQueryBuilder<I_C_BP_BankAccount> esrPostalFinanceUserESRRenderedAccountNo = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.andCollectChildren(I_ESR_PostFinanceUserNumber.COLUMN_C_BP_BankAccount_ID, I_ESR_PostFinanceUserNumber.class)
				.addEqualsFilter(I_ESR_PostFinanceUserNumber.COLUMNNAME_ESR_RenderedAccountNo, postAccountNo)
				.andCollect(I_ESR_PostFinanceUserNumber.COLUMN_C_BP_BankAccount_ID, I_C_BP_BankAccount.class);

		final IQueryFilter<I_C_BP_BankAccount> esrAccountNoFromBankAcctOrESRPostFinanceUser = queryBL.createCompositeQueryFilter(I_C_BP_BankAccount.class)
				.setJoinOr()
				.addInSubQueryFilter(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, bpBankAccountESRRenderedAccountNo.create())
				.addInSubQueryFilter(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, esrPostalFinanceUserESRRenderedAccountNo.create());

		final IQueryBuilder<I_C_BP_BankAccount> bankAccountQuery = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, true)
				.filter(esrAccountNoFromBankAcctOrESRPostFinanceUser);

		if (!innerAccountNo.equals("0000000")) // 7 x 0
		{
			bankAccountQuery.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_AccountNo, innerAccountNo);
		}

		bankAccountQuery
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return bankAccountQuery
				.create()
				.list();
	}

	@Override
	public List<I_ESR_PostFinanceUserNumber> retrieveESRPostFinanceUserNumbers(@NonNull final I_C_BP_BankAccount bankAcct)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_ESR_PostFinanceUserNumber.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_PostFinanceUserNumber.COLUMNNAME_C_BP_BankAccount_ID, bankAcct.getC_BP_BankAccount_ID())
				.create()
				.list(I_ESR_PostFinanceUserNumber.class);
	}
}
