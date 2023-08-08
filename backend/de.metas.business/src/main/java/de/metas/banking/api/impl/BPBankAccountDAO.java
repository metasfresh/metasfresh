package de.metas.banking.api.impl;

import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
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
				.bankId(BankId.ofRepoIdOrNull(record.getC_Bank_ID())) // C_BP_BankAccount.C_Bank_ID is not mandatory!
				.accountName(StringUtils.trimBlankToNull(record.getA_Name()))
				.esrRenderedAccountNo(record.getESR_RenderedAccountNo())
				.IBAN(StringUtils.trimBlankToNull(record.getIBAN()))
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

}
