package de.metas.payment.esr.api.impl;

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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class ESRBPBankAccountDAO implements IESRBPBankAccountDAO
{
	@Override
	public final List<I_C_BP_BankAccount> retrieveESRBPBankAccounts(
			@NonNull final String postAccountNo,
			@NonNull final String innerAccountNo)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableSet<String> matchingESRAccountNumbers = createMatchingESRAccountNumbers(postAccountNo);

		final IQueryBuilder<I_C_BP_BankAccount> bpBankAccountESRRenderedAccountNo = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_ESR_RenderedAccountNo, matchingESRAccountNumbers);

		final IQueryBuilder<I_C_BP_BankAccount> esrPostalFinanceUserESRRenderedAccountNo = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.andCollectChildren(I_ESR_PostFinanceUserNumber.COLUMN_C_BP_BankAccount_ID, I_ESR_PostFinanceUserNumber.class)
				.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_ESR_RenderedAccountNo, matchingESRAccountNumbers)
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

	/**
	 * Explode the given {@code esrString} into a number of syntactically equivalent strings that can be matched against {@code C_BP_BankAccount.ESR_RenderedAccountNo}.
	 * <p>
	 * Formatting done according to https://www.gkb.ch/de/Documents/DC/Beratung-Produkte/Factsheets-Flyers/Handbuch-ESR/ESR-Handbuch-Postfinance-DE.pdf
	 * <p>
	 * 01-1067-4
	 * 010010674
	 * 010106704 NOT created
	 */
	@NonNull
	@VisibleForTesting
	static ImmutableSet<String> createMatchingESRAccountNumbers(@NonNull final String esrString)
	{
		final ImmutableSet.Builder<String> builder = ImmutableSet.<String>builder();
		builder.add(esrString);

		final String[] split = esrString.split("-");
		if (split.length == 3)
		{
			// case 01-1067-4 => generate 010010674
			String str = split[0];
			str += StringUtils.lpadZero(split[1], 6, "createMatchingESRAccountNumbers");
			str += split[2];
			builder.add(str);
		}
		else if (split.length == 1)
		{
			// case 010010674 => generate 01-1067-4

			// i dont have a better idea right now
			// get rid of leading 0
			final int i = Integer.parseInt(esrString.substring(2, 8));
			final int len = Integer.toString(i).length();
			String str = esrString.substring(0, 2);

			if (len == 6 - 2)
			{
				str += "-" + i + "-";
				str += esrString.substring(8);
				builder.add(str);
			}
			else if (len == 6 - 1)
			{
				str += i + "-";
				str += esrString.substring(8);
				builder.add(str);
			}
		}

		return builder.build();
	}

	@Override
	public final List<I_ESR_PostFinanceUserNumber> retrieveESRPostFinanceUserNumbers(@NonNull final BankAccountId bankAcctId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_ESR_PostFinanceUserNumber.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ESR_PostFinanceUserNumber.COLUMNNAME_C_BP_BankAccount_ID, bankAcctId)
				.create()
				.list(I_ESR_PostFinanceUserNumber.class);
	}

	private static  AdMessageKey MSG_NOT_ESR_ACCOUNT_FOR_ORG =  AdMessageKey.of("NoESRAccountForOrganiazation");

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
