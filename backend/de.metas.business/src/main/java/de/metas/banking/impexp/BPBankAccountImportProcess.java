/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.banking.impexp;

import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.UpsertBPBankAccountRequest;
import de.metas.banking.model.I_I_BP_BankAccount;
import de.metas.banking.model.X_I_BP_BankAccount;
import de.metas.bpartner.BPartnerId;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Stream;

public class BPBankAccountImportProcess extends SimpleImportProcessTemplate<I_I_BP_BankAccount>
{
	private final SpringContextHolder.Lazy<BankAccountService> bankAccountServiceHolder =
			SpringContextHolder.lazyBean(BankAccountService.class);

	@Override
	public Class<I_I_BP_BankAccount> getImportModelClass()
	{
		return I_I_BP_BankAccount.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_BP_BankAccount.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BP_BankAccount.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		BPBankAccountImportTableSqlUpdater.updateAndValidateBPBankAccountImportTable(selection);

		final int countErrorRecords = BPBankAccountImportTableSqlUpdater.countRecordsWithErrors(selection);

		getResultCollector().setCountImportRecordsWithValidationErrors(countErrorRecords);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_BP_BankAccount.COLUMNNAME_I_BP_BankAccount_ID;
	}

	@Override
	protected I_I_BP_BankAccount retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_BP_BankAccount(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> stateHolder,
			final @NonNull I_I_BP_BankAccount importRecord,
			final boolean isInsertOnly) throws Exception
	{
		bankAccountServiceHolder.get().upsertBankAccount(getUpsertRequest(importRecord));

		return ImportRecordResult.Inserted;
	}

	@NonNull
	private static UpsertBPBankAccountRequest getUpsertRequest(@NonNull final I_I_BP_BankAccount importRecord)
	{
		return UpsertBPBankAccountRequest.builder()
				.orgId(OrgId.ofRepoId(importRecord.getAD_Org_ID()))
				.bPartnerId(BPartnerId.ofRepoId(importRecord.getC_BPartner_ID()))
				.currencyId(CurrencyId.ofRepoId(importRecord.getC_Currency_ID()))

				.accountNo(importRecord.getAccountNo())
				.routingNo(importRecord.getRoutingNo())
				.iban(computeIBAN(importRecord))
				.name(importRecord.getA_Name())
				.build();
	}

	@Nullable
	private static String computeIBAN(@NonNull final I_I_BP_BankAccount importRecord)
	{
		final boolean missingIBANComponents = Stream.of(importRecord.getReference_Details(),
													importRecord.getRoutingNo(),
													importRecord.getAccountNo())
				.anyMatch(Objects::isNull);

		if (missingIBANComponents)
		{
			return null;
		}

		return String.join("", importRecord.getReference_Details(),
						   importRecord.getRoutingNo(),
						   importRecord.getAccountNo());
	}
}
