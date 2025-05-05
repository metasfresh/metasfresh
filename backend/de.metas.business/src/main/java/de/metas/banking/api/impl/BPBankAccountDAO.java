package de.metas.banking.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.CreateBPBankAccountRequest;
import de.metas.banking.api.GetBPBankAccountQuery;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.DisplayNameQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ad.dao.impl.CleanWhitespaceQueryFilterModifier;
import org.compiere.model.I_C_BP_BankAccount;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

public class BPBankAccountDAO extends de.metas.bpartner.service.impl.BPBankAccountDAO implements IBPBankAccountDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BankAccountId, BankAccount> bankAccountsById = CCache.<BankAccountId, BankAccount>builder()
			.tableName(I_C_BP_BankAccount.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	private final CCache<BPartnerId, Optional<BankAccount>> bankAccountByBPartnerId = CCache.<BPartnerId, Optional<BankAccount>>builder()
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
				.bPartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.bankId(BankId.ofRepoIdOrNull(record.getC_Bank_ID())) // C_BP_BankAccount.C_Bank_ID is not mandatory!
				.accountName(StringUtils.trimBlankToNull(record.getA_Name()))
				.name(StringUtils.trimBlankToNull(record.getName()))
				.esrRenderedAccountNo(record.getESR_RenderedAccountNo())
				.IBAN(StringUtils.trimBlankToNull(record.getIBAN()))
				.SwiftCode(StringUtils.trimBlankToNull(record.getSwiftCode()))
				.QR_IBAN(StringUtils.trimBlankToNull(record.getQR_IBAN()))
				.SEPA_CreditorIdentifier(StringUtils.trimBlankToNull(record.getSEPA_CreditorIdentifier()))
				.accountNo(record.getAccountNo())
				.currencyId(CurrencyId.ofRepoId(record.getC_Currency_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.routingNo(record.getRoutingNo())
				.build();
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
	public BankId getBankId(@NonNull final BankAccountId bankAccountId)
	{
		return getById(bankAccountId).getBankId();
	}

	@Override
	@NonNull
	public Optional<BankAccount> getDefaultBankAccount(@NonNull final BPartnerId bPartnerId)
	{
		return retrieveDefaultBankAccountInTrx(bPartnerId)
				.map(BPBankAccountDAO::toBankAccount);
	}

	@Override
	public Optional<BankAccount> getDefaultESRBankAccount(@NonNull final BPartnerId bpartnerId)
	{
		return bankAccountByBPartnerId.getOrLoad(bpartnerId, this::retrieveDefaultESRBankAccount);
	}

	private Optional<BankAccount> retrieveDefaultESRBankAccount(@NonNull final BPartnerId bpartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, true)
				.orderByDescending(I_C_BP_BankAccount.COLUMNNAME_IsDefaultESR)
				.orderByDescending(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
				.create()
				.firstOptional(I_C_BP_BankAccount.class)
				.map(BPBankAccountDAO::toBankAccount);
	}

	@Override
	@NonNull
	public Optional<BankAccountId> getBankAccountId(
			@NonNull final BankId bankId,
			@NonNull final String accountNo)
	{
		return queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_AccountNo, accountNo)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID, bankId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_C_BP_BankAccount.class)
				.map(bpBankAccount -> BankAccountId.ofRepoId(bpBankAccount.getC_BP_BankAccount_ID()));
	}

	@Override
	@NonNull
	public Optional<BankAccountId> getBankAccountIdByIBAN(
			@NonNull final String iban)
	{
		return getBankAccountByIBAN(iban)
				.map(BankAccount::getId);
	}

	@Override
	@NonNull
	public Optional<BankAccount> getBankAccountByIBAN(
			@NonNull final String iban)
	{
		return queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IBAN, iban, CleanWhitespaceQueryFilterModifier.getInstance())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_C_BP_BankAccount.class)
				.map(BPBankAccountDAO::toBankAccount);
	}

	@NonNull
	public BankAccount update(@NonNull final BankAccount bankAccount)
	{
		final I_C_BP_BankAccount record = InterfaceWrapperHelper.load(bankAccount.getId(), I_C_BP_BankAccount.class);

		updateRecord(record, bankAccount);

		InterfaceWrapperHelper.save(record);

		return toBankAccount(record);
	}

	@NonNull
	public BankAccount create(@NonNull final CreateBPBankAccountRequest createBPBankAccountRequest)
	{
		final I_C_BP_BankAccount record = createRecord(createBPBankAccountRequest);

		InterfaceWrapperHelper.save(record);

		return toBankAccount(record);
	}

	@NonNull
	public ImmutableList<BankAccount> listByQuery(@NonNull final GetBPBankAccountQuery query)
	{
		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, query.getBPartnerIds());

		if (Check.isNotBlank(query.getSearchTerm()))
		{
			queryBuilder.addFilter(DisplayNameQueryFilter.of(I_C_BP_BankAccount.class, query.getSearchTerm()));
		}

		return queryBuilder.create()
				.stream()
				.map(BPBankAccountDAO::toBankAccount)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static I_C_BP_BankAccount updateRecord(
			@NonNull final I_C_BP_BankAccount record,
			@NonNull final BankAccount bankAccount)
	{
		record.setAccountNo(bankAccount.getAccountNo());
		record.setRoutingNo(bankAccount.getRoutingNo());
		record.setName(bankAccount.getName());
		record.setA_Name(bankAccount.getAccountName());
		record.setIBAN(bankAccount.getIBAN());
		record.setQR_IBAN(bankAccount.getQR_IBAN());
		record.setSEPA_CreditorIdentifier(bankAccount.getSEPA_CreditorIdentifier());
		record.setESR_RenderedAccountNo(bankAccount.getEsrRenderedAccountNo());

		record.setC_Currency_ID(bankAccount.getCurrencyId().getRepoId());
		record.setAD_Org_ID(bankAccount.getOrgId().getRepoId());
		record.setC_BPartner_ID(bankAccount.getBPartnerId().getRepoId());
		record.setC_Bank_ID(BankId.toRepoId(bankAccount.getBankId()));

		return record;
	}

	@NonNull
	private static I_C_BP_BankAccount createRecord(@NonNull final CreateBPBankAccountRequest request)
	{
		final I_C_BP_BankAccount record = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);

		record.setAccountNo(request.getAccountNo());
		record.setRoutingNo(request.getRoutingNo());
		record.setName(request.getName());
		record.setIBAN(request.getIban());

		record.setC_Currency_ID(request.getCurrencyId().getRepoId());
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_BPartner_ID(request.getBPartnerId().getRepoId());

		return record;
	}
}
