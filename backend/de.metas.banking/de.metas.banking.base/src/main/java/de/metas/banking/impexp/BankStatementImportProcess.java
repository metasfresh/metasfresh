package de.metas.banking.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_BankStatement;
import org.compiere.util.TimeUtil;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest.ElectronicFundsTransfer;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.ChargeId;
import de.metas.document.engine.DocStatus;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class BankStatementImportProcess extends SimpleImportProcessTemplate<I_I_BankStatement>
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@Override
	public Class<I_I_BankStatement> getImportModelClass()
	{
		return I_I_BankStatement.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_BankStatement.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BankStatement.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		final BankAccountId orgBankAccountId = BankAccountId.ofRepoIdOrNull(getParameters().getParameterAsInt(I_I_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, -1));
		final String bankStatementName = getParameters().getParameterAsString(I_I_BankStatement.COLUMNNAME_Name);
		final LocalDate bankStatementDate = getParameters().getParameterAsLocalDate(I_I_BankStatement.COLUMNNAME_StatementDate);
		final BankStatementId bankStatementId = BankStatementId.ofRepoIdOrNull(getParameters().getParameterAsInt(I_I_BankStatement.COLUMNNAME_C_BankStatement_ID, -1));

		BankStatementImportTableSqlUpdater.updateBankStatementImportTable(selection, orgBankAccountId, bankStatementName, bankStatementDate, bankStatementId);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_BankStatement.COLUMNNAME_I_BankStatement_ID;
	}

	@Override
	protected I_I_BankStatement retrieveImportRecord(Properties ctx, ResultSet rs)
	{
		return new X_I_BankStatement(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_BankStatement importRecord, final boolean isInsertOnly)
	{
		final BankStatementId existingBankStatementId = retrieveExistingBankStatementId(importRecord);
		final boolean isNewBankStatement = existingBankStatementId == null;

		if (!isNewBankStatement && isInsertOnly)
		{
			// #4994 do not update
			return ImportRecordResult.Nothing;
		}

		if (isNewBankStatement)
		{
			final BankStatementId bankStatementId = createBankStatement(importRecord);
			importRecord.setC_BankStatement_ID(bankStatementId.getRepoId());
		}
		else
		{
			importRecord.setC_BankStatement_ID(existingBankStatementId.getRepoId());
		}

		createBankStatementLine(importRecord);

		save(importRecord);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, null, IImportInterceptor.TIMING_AFTER_IMPORT);

		return isNewBankStatement ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private BankStatementId retrieveExistingBankStatementId(@NonNull final I_I_BankStatement importRecord)
	{
		final BankStatementId existingBankStatementId = BankStatementId.ofRepoIdOrNull(importRecord.getC_BankStatement_ID());
		if (existingBankStatementId != null)
		{
			return existingBankStatementId;
		}

		// I believe this fallback is not needed anymore due to the above query, as the record should already have a C_BankStatement_ID set from de.metas.banking.impexp.BankStatementImportTableSqlUpdater.updateBankStatement.
		// Not sure if deleting this query will bring other bugs though.
		// Note: there may be bank statements with same name, statement date and BP bank accounts, so the below query may return the wrong result, and also different results for different calls, since there is no order by
		final BankAccountId orgBankAccountId = BankAccountId.ofRepoIdOrNull(importRecord.getC_BP_BankAccount_ID());
		final LocalDate statementDate = TimeUtil.asLocalDate(importRecord.getStatementDate());
		final String name = importRecord.getName();
		if (orgBankAccountId != null
				&& statementDate != null
				&& !Check.isBlank(name))
		{
			final BankStatementId bankStatementId = bankStatementDAO.getFirstIdMatching(orgBankAccountId, statementDate, name, DocStatus.Drafted)
					.orElse(null);
			if (bankStatementId != null)
			{
				return bankStatementId;
			}
		}

		// Fallack: no existing bank statement found
		return null;
	}

	private void createBankStatementLine(final I_I_BankStatement importRecord)
	{
		if (importRecord.getC_BankStatementLine_ID() > 0)
		{
			throw new AdempiereException("bank statement line already imported: " + importRecord);
		}

		final CurrencyId currencyId = CurrencyId.ofRepoId(importRecord.getC_Currency_ID());

		final BankStatementLineId bankStatementLineId = bankStatementDAO.createBankStatementLine(BankStatementLineCreateRequest.builder()
				.bankStatementId(BankStatementId.ofRepoId(importRecord.getC_BankStatement_ID()))
				.orgId(OrgId.ofRepoId(importRecord.getAD_Org_ID()))
				.lineNo(importRecord.getLine())
				//
				.bpartnerId(BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID()))
				.importedBillPartnerName(importRecord.getBill_BPartner_Name())
				.importedBillPartnerIBAN(importRecord.getIBAN_To())
				//
				.referenceNo(importRecord.getReferenceNo())
				.lineDescription(importRecord.getLineDescription())
				.memo(importRecord.getMemo())
				//
				.statementLineDate(TimeUtil.asLocalDate(CoalesceUtil.coalesce(importRecord.getStatementLineDate(), importRecord.getStatementDate())))
				.dateAcct(TimeUtil.asLocalDate(CoalesceUtil.coalesce(importRecord.getDateAcct(), importRecord.getStatementDate())))
				.valutaDate(TimeUtil.asLocalDate(importRecord.getValutaDate()))
				//
				.statementAmt(Money.of(importRecord.getStmtAmt(), currencyId))
				.trxAmt(Money.of(importRecord.getTrxAmt(), currencyId))
				.chargeAmt(Money.of(importRecord.getChargeAmt(), currencyId))
				.interestAmt(Money.of(importRecord.getInterestAmt(), currencyId))
				.chargeId(ChargeId.ofRepoIdOrNull(importRecord.getC_Charge_ID()))
				//
				.eft(ElectronicFundsTransfer.builder()
						.trxId(importRecord.getEftTrxID())
						.trxType(importRecord.getEftTrxType())
						.checkNo(importRecord.getEftCheckNo())
						.reference(importRecord.getEftReference())
						.memo(importRecord.getEftMemo())
						.payee(importRecord.getEftPayee())
						.payeeAccount(importRecord.getEftPayeeAccount())
						.statementLineDate(TimeUtil.asLocalDate(importRecord.getEftStatementLineDate()))
						.valutaDate(TimeUtil.asLocalDate(importRecord.getEftValutaDate()))
						.currency(importRecord.getEftCurrency())
						.amt(importRecord.getEftAmt())
						.build())
				//
				.build());

		importRecord.setC_BankStatementLine_ID(bankStatementLineId.getRepoId());
	}

	private BankStatementId createBankStatement(@NonNull final I_I_BankStatement importBankStatement)
	{
		return bankStatementDAO.createBankStatement(BankStatementCreateRequest.builder()
				.orgId(OrgId.ofRepoId(importBankStatement.getAD_Org_ID()))
				.orgBankAccountId(BankAccountId.ofRepoId(importBankStatement.getC_BP_BankAccount_ID()))
				// TODO add documentNo to import
				.statementDate(TimeUtil.asLocalDate(importBankStatement.getStatementDate()))
				.name(importBankStatement.getName())
				.description(importBankStatement.getDescription())
				.eft(BankStatementCreateRequest.ElectronicFundsTransfer.builder()
						.statementDate(TimeUtil.asLocalDate(importBankStatement.getEftStatementDate()))
						.statementReference(importBankStatement.getEftStatementReference())
						.build())
				.build());
	}

}
