/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.BankId;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.location.LocationId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Bank;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class BankRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<BankId, Bank> banksById = CCache.<BankId, Bank>builder()
			.tableName(I_C_Bank.Table_Name)
			.build();

	private final CCache<String, Optional<BankId>> bankIdsBySwiftCode = CCache.<String, Optional<BankId>>builder()
			.tableName(I_C_Bank.Table_Name)
			.build();
	/**
	 * #12372: Javadoc and constant copy-pasted from de.metas.banking.process.C_BankStatement_ImportAttachment
	 * Having DataImportConfigId hardcoded is fine.
	 * We could use a sysconfig, but what shall we store there? the ID? the InternalName? (internal name is editable by user). That won't change/fix anything.
	 * If you have a better suggestion, please <strike>ping me</strike> create a followup.
	 */
	public static final DataImportConfigId HARDCODED_BANK_STATEMENT_DATA_IMPORT_REPO_ID = DataImportConfigId.ofRepoId(540009);

	public DataImportConfigId retrieveDefaultBankDataImportConfigId()
	{
		return HARDCODED_BANK_STATEMENT_DATA_IMPORT_REPO_ID;
	}

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
				.dataImportConfigId(DataImportConfigId.ofRepoIdOrNull(record.getC_DataImport_ID()))
				.cashBank(record.isCashBank())
				.locationId(LocationId.ofRepoIdOrNull(record.getC_Location_ID()))
				//
				// ESR:
				.esrPostBank(record.isESR_PostBank())
				//
				.importAsSingleSummaryLine(record.isImportAsSingleSummaryLine())
				.build();
	}

	@NonNull
	public Optional<BankId> getBankIdBySwiftCode(final String swiftCode)
	{
		return bankIdsBySwiftCode.getOrLoad(swiftCode, this::retrieveBankIdBySwiftCode);
	}

	@NonNull
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

		record.setC_DataImport_ID(DataImportConfigId.toRepoId(request.getDataImportConfigId()));

		saveRecord(record);

		return toBank(record);
	}

	@NonNull
	public DataImportConfigId retrieveDataImportConfigIdForBank(@Nullable final BankId bankId)
	{
		if (bankId == null)
		{
			return retrieveDefaultBankDataImportConfigId();
		}

		final Bank bank = getById(bankId);

		return CoalesceUtil.coalesceSuppliersNotNull(
				bank::getDataImportConfigId,
				this::retrieveDefaultBankDataImportConfigId);
	}

	public boolean isImportAsSingleSummaryLine(@NonNull final BankId bankId)	{ return getById(bankId).isImportAsSingleSummaryLine(); 	}

	@NonNull
	public Set<BankId> retrieveBankIdsByName(@NonNull final String bankName)
	{
		return queryBL.createQueryBuilder(I_C_Bank.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(I_C_Bank.COLUMNNAME_Name, bankName, true)
				.create()
				.listIds(BankId::ofRepoId);
	}
}
