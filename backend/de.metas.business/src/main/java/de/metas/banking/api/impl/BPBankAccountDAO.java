package de.metas.banking.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BP_BankAccount;

import com.google.common.collect.ImmutableListMultimap;

import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BPBankAccountDAO implements IBPBankAccountDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BankAccountId, BankAccount> bankAccountsById = CCache.<BankAccountId, BankAccount> builder()
			.tableName(I_C_BP_BankAccount.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@Override
	public BankAccount getById(@NonNull final BankAccountId bankAccountId)
	{
		return bankAccountsById.getOrLoad(bankAccountId, this::retrieveBankAccount);
	}

	private BankAccount retrieveBankAccount(@NonNull final BankAccountId bankAccountId)
	{
		final I_C_BP_BankAccount record = loadOutOfTrx(bankAccountId, I_C_BP_BankAccount.class);
		return toBankAccount(record);
	}

	private static BankAccount toBankAccount(@NonNull final I_C_BP_BankAccount record)
	{
		return BankAccount.builder()
				.id(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.bankId(BankId.ofRepoId(record.getC_Bank_ID()))
				.accountName(StringUtils.trimBlankToNull(record.getA_Name()))
				.esrRenderedAccountNo(record.getESR_RenderedAccountNo())
				.accountNo(record.getAccountNo())
				.currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	@Override
	public ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> getAllByBPartnerIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryBL.createQueryBuilderOutOfTrx(I_C_BP_BankAccount.class)
				.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()),
						record -> record));
	}

	@Override
	public List<I_C_BP_BankAccount> retrieveBankAccountsForPartnerAndCurrency(Properties ctx, int partnerID, int currencyID)
	{
		final IQueryBuilder<I_C_BP_BankAccount> qb = queryBL
				.createQueryBuilder(I_C_BP_BankAccount.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, partnerID);

		if (currencyID > 0)
		{
			qb.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID, currencyID);
		}

		final List<I_C_BP_BankAccount> bpBankAccounts = qb.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last) // DESC (Y, then N)
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
				.endOrderBy()
				.create()
				.list();

		return bpBankAccounts;
	}

	@Override
	public Optional<BankAccountId> retrieveByBPartnerAndCurrencyAndIBAN(@NonNull final BPartnerId bPartnerId, @NonNull final CurrencyId currencyId, @NonNull final String iban)
	{
		final BankAccountId bankAccountId = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID, currencyId)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IBAN, iban)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(BankAccountId::ofRepoIdOrNull);

		return Optional.ofNullable(bankAccountId);
	}

	@Override
	public Optional<I_C_BP_BankAccount> retrieveDefaultBankAccountInTrx(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BP_BankAccount bankAccount = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_C_BP_BankAccount.COLUMNNAME_IsDefault) // DESC (Y, then N)
				.create()
				.first();

		return Optional.ofNullable(bankAccount);
	}

	@Override
	public void deactivateIBANAccountsByBPartnerExcept(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Collection<BPartnerBankAccountId> exceptIds)
	{
		final ICompositeQueryUpdater<I_C_BP_BankAccount> columnUpdater = queryBL
				.createCompositeQueryUpdater(I_C_BP_BankAccount.class)
				.addSetColumnValue(I_C_BP_BankAccount.COLUMNNAME_IsActive, false);

		queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_BP_BankAccount.COLUMNNAME_IBAN)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addNotInArrayFilter(I_C_BP_BankAccount.COLUMN_C_BP_BankAccount_ID, exceptIds)
				.create()
				.update(columnUpdater);
	}

	@Override
	public BankId getBankId(@NonNull final BankAccountId bankAccountId)
	{
		return getById(bankAccountId).getBankId();
	}
}
