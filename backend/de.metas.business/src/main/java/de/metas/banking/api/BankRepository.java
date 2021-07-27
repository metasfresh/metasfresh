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

package de.metas.banking.api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Bank;
import org.springframework.stereotype.Repository;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.BankId;
import de.metas.cache.CCache;
import de.metas.location.LocationId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;

@Repository
public class BankRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BankId, Bank> banksById = CCache.<BankId, Bank> builder()
			.tableName(I_C_Bank.Table_Name)
			.build();

	private final CCache<String, Optional<BankId>> bankIdsBySwiftCode = CCache.<String, Optional<BankId>> builder()
			.tableName(I_C_Bank.Table_Name)
			.build();

	public Bank getById(final BankId bankId)
	{
		return banksById.getOrLoad(bankId, this::retrieveBankById);
	}

	private Bank retrieveBankById(@NonNull final BankId bankId)
	{
		final I_C_Bank record = InterfaceWrapperHelper.load(bankId, I_C_Bank.class);
		final Bank bank = toBank(record);

		if (bank.getSwiftCode() != null)
		{
			bankIdsBySwiftCode.put(bank.getSwiftCode(), Optional.of(bank.getBankId()));
		}

		return bank;
	}

	private static Bank toBank(@NonNull final I_C_Bank record)
	{
		return Bank.builder()
				.bankId(BankId.ofRepoId(record.getC_Bank_ID()))
				.bankName(StringUtils.trimBlankToNull(record.getName()))
				.swiftCode(StringUtils.trimBlankToNull(record.getSwiftCode()))
				.routingNo(StringUtils.trimBlankToNull(record.getRoutingNo()))
				.cashBank(record.isCashBank())
				.locationId(LocationId.ofRepoIdOrNull(record.getC_Location_ID()))
				//
				// ESR:
				.esrPostBank(record.isESR_PostBank())
				//
				.build();
	}

	public Optional<BankId> getBankIdBySwiftCode(final String swiftCode)
	{
		return bankIdsBySwiftCode.getOrLoad(swiftCode, this::retrieveBankIdBySwiftCode);
	}

	private Optional<BankId> retrieveBankIdBySwiftCode(final String swiftCode)
	{
		final int bankRepoId = queryBL.createQueryBuilderOutOfTrx(I_C_Bank.class)
				.addEqualsFilter(I_C_Bank.COLUMNNAME_SwiftCode, swiftCode)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Bank.COLUMNNAME_C_Bank_ID)
				.create()
				.firstId();

		return BankId.optionalOfRepoId(bankRepoId);
	}

	public boolean isCashBank(@NonNull final BankId bankId)
	{
		return getById(bankId).isCashBank();
	}

	public Bank createBank(@NonNull final BankCreateRequest request)
	{
		final I_C_Bank record = newInstance(I_C_Bank.class);
		record.setName(request.getBankName());
		record.setSwiftCode(request.getSwiftCode());
		record.setRoutingNo(request.getRoutingNo());
		record.setIsCashBank(request.isCashBank());
		record.setC_Location_ID(LocationId.toRepoId(request.getLocationId()));

		// ESR:
		record.setESR_PostBank(request.isEsrPostBank());

		saveRecord(record);

		return toBank(record);
	}
}
