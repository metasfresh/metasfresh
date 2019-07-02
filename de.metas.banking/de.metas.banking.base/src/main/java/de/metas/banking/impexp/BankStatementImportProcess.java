package de.metas.banking.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.AbstractImportProcess;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_BankStatement;

import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.service.IBankStatementDAO;
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

public class BankStatementImportProcess extends AbstractImportProcess<I_I_BankStatement>
{

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

		final String whereClause = getWhereClause();
		BankStatementImportTableSqlUpdater.updateBankStatemebtImportTable(whereClause);
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
	protected ImportRecordResult importRecord(IMutable<Object> state, I_I_BankStatement importRecord, boolean isInsertOnly) throws Exception
	{
		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

		final int importBankStatementId = importRecord.getI_BankStatement_ID();
		int bankStatementRecordId = importRecord.getC_BankStatement_ID();
		final boolean isNewBankStatement = bankStatementRecordId <= 0;
		log.debug("I_BankStatement_ID=" + importBankStatementId + ", C_BankStatement_ID=" + bankStatementRecordId);

		if (!isNewBankStatement && isInsertOnly)
		{
			// #4994 do not update
			return ImportRecordResult.Nothing;
		}
		// Bank Statement
		if (isNewBankStatement)			// Insert new Bank Statement
		{
			final I_C_BankStatement bankStatement = createBankStatement(importRecord);

			save(bankStatement);
			bankStatementRecordId = bankStatement.getC_BankStatement_ID();
			importRecord.setC_BankStatement_ID(bankStatementRecordId);
			save(importRecord);

			log.trace("Insert Bank Statement");
		}
		else
		{

			I_C_BankStatement bankStatement = bankStatementDAO.getById(bankStatementRecordId);

			if (bankStatement.getC_BP_BankAccount_ID() <= 0)
			{
				bankStatement.setC_BP_BankAccount_ID(importRecord.getC_BP_BankAccount_ID());
			}
			//
			// X_I_BankStatement imp = new X_I_BankStatement(m_ctx, rs, get_TrxName());
			// // Get the bank account for the first statement
			// if (account == null)
			// {
			// account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
			// statement = null;
			// log.info("New Statement, Account=" + account.getAccountNo());
			// }
			// // Create a new Bank Statement for every account
			// else if (account.getC_BP_BankAccount_ID() != imp.getC_BP_BankAccount_ID())
			// {
			// account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
			// statement = null;
			// log.info("New Statement, Account=" + account.getAccountNo());
			// }
			// Create a new Bank Statement for every statement name

			if (Check.isEmpty(bankStatement.getName()))
			{
				bankStatement.setName(importRecord.getName());
			}
			// else if ((statement.getName() != null) && (imp.getName() != null))
			// {
			// if (!statement.getName().equals(imp.getName()))
			// {
			// statement = null;
			// log.info("New Statement, Statement Name=" + imp.getName());
			// }
			// }
			// Create a new Bank Statement for every statement reference

			if (Check.isEmpty(bankStatement.getEftStatementReference()))
			{
				bankStatement.setEftStatementReference(importRecord.getEftStatementReference());
			}

			// else if ((statement.getEftStatementReference() != null) && (imp.getEftStatementReference() != null))
			// {
			// if (!statement.getEftStatementReference().equals(imp.getEftStatementReference()))
			// {
			// statement = null;
			// log.info("New Statement, Statement Reference=" + imp.getEftStatementReference());
			// }
			// }

			// Create a new Bank Statement for every statement date

			if (Check.isEmpty(bankStatement.getEftStatementDate()))
			{
				bankStatement.setEftStatementDate(importRecord.getEftStatementDate());
			}

			// else if ((statement.getStatementDate() != null) && (imp.getStatementDate() != null))
			// {
			// if (!statement.getStatementDate().equals(imp.getStatementDate()))
			// {
			// statement = null;
			// log.info("New Statement, Statement Date=" + imp.getStatementDate());
			// }
			// }
			save(bankStatement);
		}

		//
		// Bank Statement Line
		createUpdateBankStatementLine(importRecord);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getC_BankStatement(), IImportInterceptor.TIMING_AFTER_IMPORT);

		// #3404 Create default product planning

		return isNewBankStatement ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
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

		// Copy statement line data
		// line.setC_BPartner_ID(imp.getC_BPartner_ID());
		// line.setC_Invoice_ID(imp.getC_Invoice_ID());

		final int bankStatementId = importRecord.getC_BankStatement_ID();

		bankStatementLine.setC_BankStatement_ID(bankStatementId);
		bankStatementLine.setReferenceNo(importRecord.getReferenceNo());
		bankStatementLine.setDescription(importRecord.getLineDescription());
		bankStatementLine.setStatementLineDate(CoalesceUtil.coalesce(importRecord.getStatementLineDate(),  importRecord.getStatementDate()));
		bankStatementLine.setDateAcct(CoalesceUtil.coalesce(importRecord.getDateAcct(),importRecord.getStatementDate()));
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

		// Copy statement line reference data
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

	//

	// //Import Bank Statement
	// sql = new StringBuffer("SELECT * FROM I_BankStatement"
	// + " WHERE I_IsImported='N'"
	// + " ORDER BY C_BP_BankAccount_ID, Name, EftStatementDate, EftStatementReference");
	//
	// MBankStatement statement = null;
	// I_C_BP_BankAccount account = null;
	// PreparedStatement pstmt = null;
	// int lineNo = 10;
	// int noInsert = 0;
	// int noInsertLine = 0;
	// try
	// {
	// pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
	// ResultSet rs = pstmt.executeQuery();
	//
	// while (rs.next())
	// {
	// X_I_BankStatement imp = new X_I_BankStatement(m_ctx, rs, get_TrxName());
	// // Get the bank account for the first statement
	// if (account == null)
	// {
	// account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
	// statement = null;
	// log.info("New Statement, Account=" + account.getAccountNo());
	// }
	// // Create a new Bank Statement for every account
	// else if (account.getC_BP_BankAccount_ID() != imp.getC_BP_BankAccount_ID())
	// {
	// account = InterfaceWrapperHelper.create(m_ctx, imp.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class, getTrxName());
	// statement = null;
	// log.info("New Statement, Account=" + account.getAccountNo());
	// }
	// // Create a new Bank Statement for every statement name
	// else if ((statement.getName() != null) && (imp.getName() != null))
	// {
	// if (!statement.getName().equals(imp.getName()))
	// {
	// statement = null;
	// log.info("New Statement, Statement Name=" + imp.getName());
	// }
	// }
	// // Create a new Bank Statement for every statement reference
	// else if ((statement.getEftStatementReference() != null) && (imp.getEftStatementReference() != null))
	// {
	// if (!statement.getEftStatementReference().equals(imp.getEftStatementReference()))
	// {
	// statement = null;
	// log.info("New Statement, Statement Reference=" + imp.getEftStatementReference());
	// }
	// }
	// // Create a new Bank Statement for every statement date
	// else if ((statement.getStatementDate() != null) && (imp.getStatementDate() != null))
	// {
	// if (!statement.getStatementDate().equals(imp.getStatementDate()))
	// {
	// statement = null;
	// log.info("New Statement, Statement Date=" + imp.getStatementDate());
	// }
	// }
	//
	// // New Statement
	// if (statement == null)
	// {
	// statement = new MBankStatement(account);
	// statement.setEndingBalance(Env.ZERO);
	//
	// // Copy statement data
	// if (imp.getName() != null)
	// {
	// statement.setName(imp.getName());
	// }
	// if (imp.getStatementDate() != null)
	// {
	// statement.setStatementDate(imp.getStatementDate());
	// }
	// statement.setDescription(imp.getDescription());
	// statement.setEftStatementReference(imp.getEftStatementReference());
	// statement.setEftStatementDate(imp.getEftStatementDate());
	// if (statement.save())
	// {
	// noInsert++;
	// }
	// lineNo = 10;
	// }
	//
	// // New StatementLine
	// MBankStatementLine line = new MBankStatementLine(statement, lineNo);
	//
	// // Copy statement line data
	// //line.setC_BPartner_ID(imp.getC_BPartner_ID());
	// //line.setC_Invoice_ID(imp.getC_Invoice_ID());
	// line.setReferenceNo(imp.getReferenceNo());
	// line.setDescription(imp.getLineDescription());
	// line.setStatementLineDate(imp.getStatementLineDate());
	// line.setDateAcct(imp.getStatementLineDate());
	// line.setValutaDate(imp.getValutaDate());
	// line.setIsReversal(imp.isReversal());
	// line.setC_Currency_ID(imp.getC_Currency_ID());
	// line.setTrxAmt(imp.getTrxAmt());
	// line.setStmtAmt(imp.getStmtAmt());
	// if (imp.getC_Charge_ID() != 0)
	// {
	// line.setC_Charge_ID(imp.getC_Charge_ID());
	// }
	// line.setInterestAmt(imp.getInterestAmt());
	// line.setChargeAmt(imp.getChargeAmt());
	// line.setMemo(imp.getMemo());
	// if (imp.getC_Payment_ID() != 0)
	// {
	// line.setC_Payment_ID(imp.getC_Payment_ID());
	// }
	//
	// // Copy statement line reference data
	// line.setEftTrxID(imp.getEftTrxID());
	// line.setEftTrxType(imp.getEftTrxType());
	// line.setEftCheckNo(imp.getEftCheckNo());
	// line.setEftReference(imp.getEftReference());
	// line.setEftMemo(imp.getEftMemo());
	// line.setEftPayee(imp.getEftPayee());
	// line.setEftPayeeAccount(imp.getEftPayeeAccount());
	// line.setEftStatementLineDate(imp.getEftStatementLineDate());
	// line.setEftValutaDate(imp.getEftValutaDate());
	// line.setEftCurrency(imp.getEftCurrency());
	// line.setEftAmt(imp.getEftAmt());
	//
	// // Save statement line
	// if (line.save())
	// {
	// imp.setC_BankStatement_ID(statement.getC_BankStatement_ID());
	// imp.setC_BankStatementLine_ID(line.getC_BankStatementLine_ID());
	// imp.setI_IsImported(true);
	// imp.setProcessed(true);
	// imp.save();
	// noInsertLine++;
	// lineNo += 10;
	// }
	// line = null;
	//
	// }
	//
	// // Close database connection
	// rs.close();
	// pstmt.close();
	// rs = null;
	// pstmt = null;
	//
	// }
	// catch(Exception e)
	// {
	// log.error(sql.toString(), e);
	// }
	//
	// // Set Error to indicator to not imported
	// sql = new StringBuffer ("UPDATE I_BankStatement "
	// + "SET I_IsImported='N', Updated=now() "
	// + "WHERE I_IsImported<>'Y'").append(clientCheck);
	// no = DB.executeUpdate(sql.toString(), get_TrxName());
	// addLog (0, null, new BigDecimal (no), "@Errors@");
	// //
	// addLog (0, null, new BigDecimal (noInsert), "@C_BankStatement_ID@: @Inserted@");
	// addLog (0, null, new BigDecimal (noInsertLine), "@C_BankStatementLine_ID@: @Inserted@");
	// return "";
	//
	// } // doIt
	//
	// }
	//
	// }

}
