package de.metas.payment.esr.api.impl;

import lombok.NonNull;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;

import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import de.metas.util.Services;

public class ESRBPBankAccountDAO implements IESRBPBankAccountDAO
{
	@Override
	public final List<I_C_BP_BankAccount> retrieveESRBPBankAccounts(
			@NonNull final String postAccountNo,
			@NonNull final String innerAccountNo)
	{
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

		if (!"0000000".equals(innerAccountNo)) // 7 x 0
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
	public final List<I_ESR_PostFinanceUserNumber> retrieveESRPostFinanceUserNumbers(@NonNull final I_C_BP_BankAccount bankAcct)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_ESR_PostFinanceUserNumber.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_PostFinanceUserNumber.COLUMNNAME_C_BP_BankAccount_ID, bankAcct.getC_BP_BankAccount_ID())
				.create()
				.list(I_ESR_PostFinanceUserNumber.class);
	}

	private static String MSG_NOT_ESR_ACCOUNT_FOR_ORG = "NoESRAccountForOrganiazation";

	@Override
	public final List<I_C_BP_BankAccount> fetchOrgEsrAccounts(@NonNull final I_AD_Org org)
	{
		final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// task 07647: we need to get the Org's BPArtner!
		final I_C_BPartner linkedBPartner = bPartnerOrgBL.retrieveLinkedBPartner(org);
		if (linkedBPartner == null)
		{
			throwMissingEsrAccount(org);
		}

		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = queryBL.createQueryBuilder(I_C_BP_BankAccount.class, org);

		queryBuilder.addOnlyActiveRecordsFilter();

		queryBuilder.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, true)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, linkedBPartner.getC_BPartner_ID());

		queryBuilder.orderBy()
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_IsDefaultESR, Direction.Descending, Nulls.Last)
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
				.endOrderBy();

		final List<I_C_BP_BankAccount> esrAccounts = queryBuilder
				.create()
				.list();

		if (esrAccounts.isEmpty())
		{
			throwMissingEsrAccount(org);
		}

		return esrAccounts;
	}

	private void throwMissingEsrAccount(final I_AD_Org org)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(org);

		final String msg = msgBL.getMsg(ctx, MSG_NOT_ESR_ACCOUNT_FOR_ORG,
				new Object[] { msgBL.translate(ctx, org.getValue())
				});
		throw new AdempiereException(msg);
	}
}
