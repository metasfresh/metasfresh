package de.metas.banking.impexp;

import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_BankStatement;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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

		BankStatementImportTableSqlUpdater.updateBPBankAccount(p_C_BP_BankAccount_ID, selection);
		BankStatementImportTableSqlUpdater.updateBankStatementImportTable(selection, bankStatementName, bankStatementDate);
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
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

		final int existingBankStatementId = retrieveExistingBankStatementId(importRecord);

		final boolean isNewBankStatement = existingBankStatementId <= 0;

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
			importRecord.setC_BankStatement_ID(existingBankStatementId);
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

		createUpdateBankStatementLine(importRecord);

		save(importRecord);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getC_BankStatement(), IImportInterceptor.TIMING_AFTER_IMPORT);

		return isNewBankStatement ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private int retrieveExistingBankStatementId(@NonNull final I_I_BankStatement importRecord)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_BankStatement.class)
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_Name, importRecord.getName())
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_StatementDate, importRecord.getStatementDate())
				.addEqualsFilter(I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID, importRecord.getC_BP_BankAccount_ID())
				.create()
				.firstId();
	}

	private void createUpdateBankStatementLine(final I_I_BankStatement importRecord)
	{
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
		final int importLineId = importRecord.getC_BankStatementLine_ID();

		final I_C_BankStatementLine bankStatementLine;
		if (importLineId > 0)
		{
			bankStatementLine = bankStatementDAO.getLineById(importLineId);
		}
		else
		{
			bankStatementLine = newInstance(I_C_BankStatementLine.class);
		}

		updateBankStatementLine(bankStatementLine, importRecord);

	}

	private void updateBankStatementLine(final I_C_BankStatementLine bankStatementLine, final I_I_BankStatement importRecord)
	{
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

		final int bankStatementId = importRecord.getC_BankStatement_ID();

		bankStatementLine.setC_BankStatement_ID(bankStatementId);
		bankStatementLine.setImportedBillPartnerName(importRecord.getBill_BPartner_Name());
		bankStatementLine.setImportedBillPartnerIBAN(importRecord.getIBAN_To());
		bankStatementLine.setC_BP_BankAccountTo_ID(importRecord.getC_BP_BankAccountTo_ID());
		bankStatementLine.setC_BPartner_ID(importRecord.getC_BPartner_ID());
		bankStatementLine.setReferenceNo(importRecord.getReferenceNo());
		bankStatementLine.setDescription(importRecord.getLineDescription());
		bankStatementLine.setStatementLineDate(CoalesceUtil.coalesce(importRecord.getStatementLineDate(), importRecord.getStatementDate()));
		bankStatementLine.setDateAcct(CoalesceUtil.coalesce(importRecord.getDateAcct(), importRecord.getStatementDate()));
		bankStatementLine.setValutaDate(importRecord.getValutaDate());
		bankStatementLine.setIsReversal(importRecord.isReversal());
		bankStatementLine.setC_Currency_ID(importRecord.getC_Currency_ID());
		bankStatementLine.setTrxAmt(importRecord.getTrxAmt());
		bankStatementLine.setStmtAmt(importRecord.getStmtAmt());
		if (importRecord.getC_Charge_ID() != 0)
		{
			bankStatementLine.setC_Charge_ID(importRecord.getC_Charge_ID());
		}
		bankStatementLine.setInterestAmt(importRecord.getInterestAmt());
		bankStatementLine.setChargeAmt(importRecord.getChargeAmt());
		bankStatementLine.setMemo(importRecord.getMemo());
		if (importRecord.getC_Payment_ID() != 0)
		{
			bankStatementLine.setC_Payment_ID(importRecord.getC_Payment_ID());
		}

		bankStatementLine.setEftTrxID(importRecord.getEftTrxID());
		bankStatementLine.setEftTrxType(importRecord.getEftTrxType());
		bankStatementLine.setEftCheckNo(importRecord.getEftCheckNo());
		bankStatementLine.setEftReference(importRecord.getEftReference());
		bankStatementLine.setEftMemo(importRecord.getEftMemo());
		bankStatementLine.setEftPayee(importRecord.getEftPayee());
		bankStatementLine.setEftPayeeAccount(importRecord.getEftPayeeAccount());
		bankStatementLine.setEftStatementLineDate(importRecord.getEftStatementLineDate());
		bankStatementLine.setEftValutaDate(importRecord.getEftValutaDate());
		bankStatementLine.setEftCurrency(importRecord.getEftCurrency());
		bankStatementLine.setEftAmt(importRecord.getEftAmt());

		bankStatementLine.setLine(importRecord.getLine());

		if (bankStatementLine.getLine() <= 0)
		{
			final List<I_C_BankStatementLine> bankStatementLines = bankStatementDAO.retrieveLines(bankStatementDAO.getById(bankStatementId), I_C_BankStatementLine.class);

			final int maxLineNo = bankStatementLines.stream()
					.max(Comparator.comparing(line -> line.getLine()))
					.map(line -> line.getLine())
					.orElse(0);

			bankStatementLine.setLine(maxLineNo + 10);
		}

		save(bankStatementLine);

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
