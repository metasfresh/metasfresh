/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.bank;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Bank;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class BankRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<String, Optional<BankId>> bankIdsBySwiftCode = CCache.<String, Optional<BankId>>builder()
			.tableName(I_C_Bank.Table_Name)
			.build();

	public Optional<BankId> getBankIdBySwiftCode(final String swiftCode)
	{
		return bankIdsBySwiftCode.getOrLoad(swiftCode, this::findBankIdBySwiftCode);
	}

	public Optional<BankId> findBankIdBySwiftCode(final String swiftCode)
	{
		final int bankRepoId = queryBL.createQueryBuilderOutOfTrx(I_C_Bank.class)
				.addEqualsFilter(I_C_Bank.COLUMNNAME_SwiftCode, swiftCode)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Bank.COLUMNNAME_C_Bank_ID)
				.create()
				.firstId();

		return BankId.optionalOfRepoId(bankRepoId);
	}

	@Nullable
	public BankId getBankId(final @NonNull BPartnerBankAccountId bPartnerBankAccountId)
	{
		return queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, bPartnerBankAccountId.getRepoId())
				.andCollect(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID, I_C_Bank.class)
				.orderBy(I_C_Bank.COLUMNNAME_C_Bank_ID)
				.create()
				.firstId(BankId::ofRepoIdOrNull);
	}

	public boolean isCashBank(@NonNull final BankId bankId)
	{
		final I_C_Bank bank = InterfaceWrapperHelper.load(bankId, I_C_Bank.class);
		return bank.isCashBank();
	}
}
