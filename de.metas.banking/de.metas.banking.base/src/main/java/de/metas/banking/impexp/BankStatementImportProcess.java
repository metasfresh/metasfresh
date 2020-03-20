package de.metas.banking.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_BankStatement;
import org.compiere.util.TimeUtil;

import de.metas.banking.model.BankStatementId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest.ElectronicFundsTransfer;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.ChargeId;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private int p_C_BP_BankAccount_ID = 0;

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

		p_C_BP_BankAccount_ID = getParameters().getParameterAsInt(I_I_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, -1);
		final String bankStatementName = getParameters().getParameterAsString(I_I_BankStatement.COLUMNNAME_Name);
		final LocalDate bankStatementDate = getParameters().getParameterAsLocalDate(I_I_BankStatement.COLUMNNAME_StatementDate);
		final int bankStatementIdInt = getParameters().getParameterAsInt(I_I_BankStatement.COLUMNNAME_C_BankStatement_ID, 0);
		final BankStatementId bankStatementId = BankStatementId.ofRepoIdOrNull(bankStatementIdInt);

		BankStatementImportTableSqlUpdater.updateBPBankAccount(p_C_BP_BankAccount_ID, selection);
		BankStatementImportTableSqlUpdater.updateBankStatementImportTable(selection, bankStatementName, bankStatementDate, bankStatementId);
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_BankStatement.COLUMNNAME_I_BankStatement_ID;
	}

	@Override
	protected I_I_BankStatement retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		return new X_I_BankStatement(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_BankStatement importRecord, final boolean isInsertOnly) throws Exception
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
			final I_C_BankStatement bankStatement = createBankStatement(importRecord);

			save(bankStatement);

			final int bankStatementRecordId = bankStatement.getC_BankStatement_ID();
			importRecord.setC_BankStatement_ID(bankStatementRecordId);
			save(importRecord);

		}
		else
		{
			importRecord.setC_BankStatement_ID(existingBankStatementId.getRepoId());
			save(importRecord);

			final I_C_BankStatement bankStatement = bankStatementDAO.getById(existingBankStatementId);

			if (bankStatement.getC_BP_BankAccount_ID() <= 0)
			{
				bankStatement.setC_BP_BankAccount_ID(importRecord.getC_BP_BankAccount_ID());
			}

			if (Check.isEmpty(bankStatement.getName()))
			{
				bankStatement.setName(importRecord.getName());
			}

			if (Check.isEmpty(bankStatement.getEftStatementReference()))
			{
				bankStatement.setEftStatementReference(importRecord.getEftStatementReference());
			}

			if (Check.isEmpty(bankStatement.getEftStatementDate()))
			{
				bankStatement.setEftStatementDate(importRecord.getEftStatementDate());
			}

			save(bankStatement);
		}

		createBankStatementLine(importRecord);
		save(importRecord);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, null, IImportInterceptor.TIMING_AFTER_IMPORT);

		return isNewBankStatement ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private BankStatementId retrieveExistingBankStatementId(@NonNull final I_I_BankStatement importRecord)
	{
		final BankStatementId bankStatementId = queryBL.createQueryBuilder(I_C_BankStatement.class)
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_C_BankStatement_ID, importRecord.getC_BankStatement_ID())
				.create()
				.firstId(BankStatementId::ofRepoIdOrNull);
		if (bankStatementId != null)
		{
			return bankStatementId;

		}

		// I believe this fallback is not needed anymore due to the above query, as the record should already have a C_BankStatement_ID set from de.metas.banking.impexp.BankStatementImportTableSqlUpdater.updateBankStatement.
		// Not sure if deleting this query will bring other bugs though.
		// Note: there may be bank statements with same name, statement date and BP bank accounts, so the below query may return the wrong result, and also different results for different calls, since there is no order by
		return queryBL.createQueryBuilder(I_C_BankStatement.class)
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_Name, importRecord.getName())
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_StatementDate, importRecord.getStatementDate())
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, importRecord.getC_BP_BankAccount_ID())
				.create()
				.firstId(BankStatementId::ofRepoIdOrNull);
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

	private I_C_BankStatement createBankStatement(@NonNull final I_I_BankStatement importBankStatement)
	{
		final I_C_BankStatement bankStatement = newInstance(I_C_BankStatement.class);

		bankStatement.setEndingBalance(BigDecimal.ZERO);
		bankStatement.setAD_Org_ID(importBankStatement.getAD_Org_ID());
		bankStatement.setC_BP_BankAccount_ID(importBankStatement.getC_BP_BankAccount_ID());
		bankStatement.setDescription(importBankStatement.getDescription());
		bankStatement.setDocumentNo(null); // TODO add documentNo to import
		bankStatement.setEftStatementDate(importBankStatement.getEftStatementDate());
		bankStatement.setEftStatementReference(importBankStatement.getEftStatementReference());
		bankStatement.setMatchStatement(importBankStatement.getMatchStatement());
		bankStatement.setName(importBankStatement.getName());
		bankStatement.setStatementDate(importBankStatement.getStatementDate());

		return bankStatement;
	}

}
