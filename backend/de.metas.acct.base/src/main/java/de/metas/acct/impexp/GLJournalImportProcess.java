/*
 * #%L
 * de.metas.acct.base
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

package de.metas.acct.impexp;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchemaId;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.MAccount;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalBatch;
import org.compiere.model.MJournalLine;
import org.compiere.model.X_GL_JournalLine;
import org.compiere.model.X_I_GLJournal;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Properties;

/**
 * Import {@link I_I_GLJournal} records to {@link I_GL_Journal}.
 */
public class GLJournalImportProcess extends SimpleImportProcessTemplate<I_I_GLJournal>
{
	private static final Logger log = LogManager.getLogger(GLJournalImportProcess.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	@Override
	protected void updateAndValidateImportRecordsImpl()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		// get process parameters
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoIdOrNull(getParameters().getParameterAsInt(I_GL_Journal.COLUMNNAME_C_AcctSchema_ID, -1));
		final Instant dateAcct = getParameters().getParameterAsInstant(I_GL_Journal.COLUMNNAME_DateAcct);

		GLJournalImportTableSqlUpdater.builder()
				.selection(selection)
				.acctSchemaId(acctSchemaId)
				.dateAcct(dateAcct)
				.build()
				.updateGLJournal();

		checkBalance();
	}

	private void checkBalance()
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final String whereClause = getImportRecordsSelection().toSqlWhereClause();

		// Get Balance
		StringBuilder sql = new StringBuilder("SELECT SUM(AmtSourceDr)-SUM(AmtSourceCr), SUM(AmtAcctDr)-SUM(AmtAcctCr) "
				+ "FROM I_GLJournal "
				+ "WHERE I_IsImported='N'").append(whereClause);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				BigDecimal source = rs.getBigDecimal(1);
				BigDecimal acct = rs.getBigDecimal(2);
				if (source != null && source.signum() == 0
						&& acct != null && acct.signum() == 0)
				{
					log.info("Import Balance = 0");
				}
				else
				{
					log.warn("Balance Source=" + source + ", Acct=" + acct);
				}
				if (source != null)
				{
					getLoggable().addLog("@AmtSourceDr@ - @AmtSourceCr@");
				}
				if (acct != null)
				{
					getLoggable().addLog("@AmtAcctDr@ - @AmtAcctCr@");
				}
			}
		}
		catch (SQLException ex)
		{
			log.error(sql.toString(), ex);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	public static final class GLJournalImportContext
	{
		MJournal journal = null;
		MJournalBatch batch = null;
		String BatchDocumentNo = "";
		String JournalDocumentNo = "";
		Timestamp DateAcct = null;
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state,
											  @NonNull final I_I_GLJournal importRecord,
											  final boolean isInsertOnly) throws Exception
	{

		GLJournalImportContext context = (GLJournalImportContext)state.getValue();
		if (context == null)
		{
			context = new GLJournalImportContext();
			state.setValue(context);
		}

		if (context.journal != null && !importRecord.isCreateNewJournal() && isInsertOnly)
		{
			// do not update
			return ImportRecordResult.Nothing;
		}

		log.debug("I_GLJournal_ID=" + importRecord.getI_GLJournal_ID()
				+ ", GL_JournalBatch_ID=" + importRecord.getGL_JournalBatch_ID()
				+ ", GL_Journal_ID=" + importRecord.getGL_Journal_ID()
				+ ", Line=" + importRecord.getLine());

		final Properties ctx = InterfaceWrapperHelper.getCtx(importRecord);
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		boolean wasInsert = false;

		// New Batch if Batch Document No changes
		String impBatchDocumentNo = importRecord.getBatchDocumentNo();
		if (impBatchDocumentNo == null)
		{
			impBatchDocumentNo = "";
		}
		if (context.batch == null
				|| importRecord.isCreateNewBatch()
				|| context.journal.getC_AcctSchema_ID() != importRecord.getC_AcctSchema_ID()
				|| !context.BatchDocumentNo.equals(impBatchDocumentNo))
		{
			context.BatchDocumentNo = impBatchDocumentNo;    // cannot compare real DocumentNo
			context.batch = new MJournalBatch(ctx, 0, null);
			context.batch.setClientOrg(importRecord.getAD_Client_ID(), importRecord.getAD_OrgDoc_ID());
			if (importRecord.getBatchDocumentNo() != null && !importRecord.getBatchDocumentNo().isEmpty())
			{
				context.batch.setDocumentNo(importRecord.getBatchDocumentNo());
			}
			context.batch.setDateAcct(importRecord.getDateAcct());
			context.batch.setDateDoc(importRecord.getDateAcct());
			context.batch.setC_DocType_ID(importRecord.getC_DocType_ID());
			context.batch.setPostingType(importRecord.getPostingType());
			String description = importRecord.getBatchDescription();
			if (description == null || description.isEmpty())
			{
				description = "*Import-";
			}
			else
			{
				description += " *Import-";
			}
			description += new Timestamp(System.currentTimeMillis());
			context.batch.setDescription(description);
			try
			{
				InterfaceWrapperHelper.save(context.batch);
			}
			catch (Exception ex)
			{
				getLoggable().addLog(ex.getLocalizedMessage());
			}

			context.journal = null;
		}
		// Journal
		String impJournalDocumentNo = importRecord.getJournalDocumentNo();
		if (impJournalDocumentNo == null)
		{
			impJournalDocumentNo = "";
		}
		Timestamp impDateAcct = TimeUtil.getDay(importRecord.getDateAcct());
		if (context.journal == null
				|| importRecord.isCreateNewJournal()
				|| !context.JournalDocumentNo.equals(impJournalDocumentNo)
				|| context.journal.getC_DocType_ID() != importRecord.getC_DocType_ID()
				|| context.journal.getGL_Category_ID() != importRecord.getGL_Category_ID()
				|| !context.journal.getPostingType().equals(importRecord.getPostingType())
				|| context.journal.getC_Currency_ID() != importRecord.getC_Currency_ID()
				|| !impDateAcct.equals(context.DateAcct))
		{
			context.JournalDocumentNo = impJournalDocumentNo;    // cannot compare real DocumentNo
			context.DateAcct = impDateAcct;
			context.journal = new MJournal(ctx, 0, trxName);
			context.journal.setGL_JournalBatch_ID(context.batch.getGL_JournalBatch_ID());
			context.journal.setClientOrg(importRecord.getAD_Client_ID(), importRecord.getAD_OrgDoc_ID());
			//
			String description = importRecord.getBatchDescription();
			if (description == null || description.isEmpty())
			{
				description = "(Import)";
			}
			context.journal.setDescription(description);
			if (importRecord.getJournalDocumentNo() != null && !importRecord.getJournalDocumentNo().isEmpty())
			{
				context.journal.setDocumentNo(importRecord.getJournalDocumentNo());
			}
			//
			context.journal.setC_AcctSchema_ID(importRecord.getC_AcctSchema_ID());
			context.journal.setC_DocType_ID(importRecord.getC_DocType_ID());
			context.journal.setGL_Category_ID(importRecord.getGL_Category_ID());
			context.journal.setPostingType(importRecord.getPostingType());
			context.journal.setGL_Budget_ID(importRecord.getGL_Budget_ID());
			//

			//
			context.journal.setCurrency(importRecord.getC_Currency_ID(), importRecord.getC_ConversionType_ID(), importRecord.getCurrencyRate());
			//
			context.journal.setDateAcct(importRecord.getDateAcct());        // sets Period if not defined
			context.journal.setDateDoc(importRecord.getDateAcct());
			//
			try
			{
				InterfaceWrapperHelper.save(context.journal);
			}
			catch (Exception ex)
			{
				getLoggable().addLog(ex.getLocalizedMessage());
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}

		// Lines
		MJournalLine line = new MJournalLine(context.journal);
		//
		line.setDescription(importRecord.getDescription());
		line.setCurrency(importRecord.getC_Currency_ID(), importRecord.getC_ConversionType_ID(), importRecord.getCurrencyRate());
		line.setC_Activity_ID(importRecord.getC_Activity_ID());
		line.setCR_Tax_ID(importRecord.getCR_Tax_ID());
		line.setDR_Tax_ID(importRecord.getDR_Tax_ID());

		//
		line.setLine(importRecord.getLine());
		line.setAmtSourceCr(importRecord.getAmtSourceCr());
		line.setAmtSourceDr(importRecord.getAmtSourceDr());
		line.setAmtAcct(importRecord.getAmtAcctDr(), importRecord.getAmtAcctCr());    // only if not 0
		line.setDateAcct(importRecord.getDateAcct());
		//
		// Set/Get Account Combination
		if (importRecord.getC_ValidCombinationFrom_ID() == 0 && importRecord.getAccountFrom_ID() > 0)
		{
			final AccountDimension acctDim = newMinimalAccountDimension(importRecord, importRecord.getAccountFrom_ID());
			final MAccount acct = MAccount.get(getCtx(), acctDim);
			if (acct.get_ID() == 0)
			{
				acct.save();
			}
			if (acct.get_ID() == 0)
			{
				importRecord.setI_ErrorMsg("ERROR creating Account");
				importRecord.setI_IsImported(false);
				InterfaceWrapperHelper.save(importRecord);
			}
			else
			{
				importRecord.setC_ValidCombinationFrom_ID(acct.get_ID());
			}
		}

		// Set/Get Account Combination
		if (importRecord.getC_ValidCombinationTo_ID() == 0 && importRecord.getAccountTo_ID() > 0)
		{
			final AccountDimension acctDim = newMinimalAccountDimension(importRecord, importRecord.getAccountTo_ID());
			final MAccount acct = MAccount.get(getCtx(), acctDim);
			if (acct.get_ID() == 0)
			{
				acct.save();
			}
			if (acct.get_ID() == 0)
			{
				importRecord.setI_ErrorMsg("ERROR creating Account");
				importRecord.setI_IsImported(false);
				InterfaceWrapperHelper.save(importRecord);
			}
			else
			{
				importRecord.setC_ValidCombinationTo_ID(acct.get_ID());
			}
		}

		//
		line.setAccount_DR_ID(importRecord.getC_ValidCombinationFrom_ID());
		line.setAccount_CR_ID(importRecord.getC_ValidCombinationTo_ID());
		//
		line.setC_UOM_ID(importRecord.getC_UOM_ID());
		line.setQty(importRecord.getQty());
		line.setDescription(importRecord.getDescription());
		//

		// Set/Get Tax Account Combination From (Debit)
		if (importRecord.getDR_Tax_Acct_ID() > 0 && importRecord.getC_ValidCombinationTaxFrom_ID() <= 0)
		{
			final AccountDimension taxAcctDim = newMinimalAccountDimension(importRecord, importRecord.getDR_Tax_Acct_ID());
			final MAccount taxAcct = MAccount.get(getCtx(), taxAcctDim);

			importRecord.setC_ValidCombinationTaxFrom_ID(taxAcct.get_ID());
			InterfaceWrapperHelper.save(importRecord);
		}

		// Set/Get Tax Account Combination To (Credit)
		if (importRecord.getCR_Tax_Acct_ID() > 0 && importRecord.getC_ValidCombinationTaxTo_ID() <= 0)
		{
			final AccountDimension taxAcctDim = newMinimalAccountDimension(importRecord, importRecord.getCR_Tax_Acct_ID());
			final MAccount taxAcct = MAccount.get(getCtx(), taxAcctDim);

			importRecord.setC_ValidCombinationTaxTo_ID(taxAcct.get_ID());
			InterfaceWrapperHelper.save(importRecord);
		}

		// Add tax accunt and amounts if exists
		if ((importRecord.getDR_TaxTotalAmt().signum() != 0 && line.getDR_Tax_ID() > 0)
				|| (importRecord.getCR_TaxTotalAmt().signum() != 0 && line.getCR_Tax_ID() > 0))
		{
			if (importRecord.getC_ValidCombinationTaxFrom_ID() > 0 || importRecord.getC_ValidCombinationTaxTo_ID() > 0)
			{
				final CurrencyPrecision precision = getPrecision(line);

				// Set tax amounts
				if (importRecord.getDR_TaxTotalAmt().signum() != 0)
				{
					final Tax drTax = taxDAO.getTaxById(line.getDR_Tax_ID());
					final BigDecimal taxTotalAmt = importRecord.getDR_TaxTotalAmt();
					final BigDecimal taxAmt = drTax.calculateTax(taxTotalAmt, true, precision.toInt()).getTaxAmount();
					final BigDecimal taxBaseAmt = taxTotalAmt.subtract(taxAmt);

					line.setDR_TaxTotalAmt(taxTotalAmt);
					line.setDR_TaxAmt(taxAmt);
					line.setDR_TaxBaseAmt(taxBaseAmt);
				}
				if (importRecord.getCR_TaxTotalAmt().signum() != 0)
				{

					final Tax crTax = taxDAO.getTaxById(line.getCR_Tax_ID());
					final BigDecimal taxTotalAmt = importRecord.getCR_TaxTotalAmt();
					final BigDecimal taxAmt = crTax.calculateTax(taxTotalAmt, true, precision.toInt()).getTaxAmount();
					final BigDecimal taxBaseAmt = taxTotalAmt.subtract(taxAmt);

					line.setCR_TaxTotalAmt(taxTotalAmt);
					line.setCR_TaxAmt(taxAmt);
					line.setCR_TaxBaseAmt(taxBaseAmt);
				}

				// Set tax account combinations
				if (importRecord.getC_ValidCombinationTaxFrom_ID() > 0)
				{
					line.setDR_Tax_Acct_ID(importRecord.getC_ValidCombinationTaxFrom_ID());
					line.setDR_AutoTaxAccount(true);
				}
				if (importRecord.getC_ValidCombinationTaxTo_ID() > 0)
				{
					line.setCR_Tax_Acct_ID(importRecord.getC_ValidCombinationTaxTo_ID());
					line.setCR_AutoTaxAccount(true);
				}

				line.setType(X_GL_JournalLine.TYPE_Tax);
			}
		}

		if (line.save())
		{
			importRecord.setGL_JournalBatch_ID(context.batch.getGL_JournalBatch_ID());
			importRecord.setGL_Journal_ID(context.journal.getGL_Journal_ID());
			importRecord.setGL_JournalLine_ID(line.getGL_JournalLine_ID());

			importRecord.setI_IsImported(true);
			importRecord.setProcessed(true);
			importRecord.setProcessing(false);
			InterfaceWrapperHelper.save(importRecord);
			wasInsert = true;

		}

		return wasInsert ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private CurrencyPrecision getPrecision(@NonNull final I_GL_JournalLine glJournalLine)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(glJournalLine.getC_Currency_ID());
		return currencyId != null
				? currencyDAO.getStdPrecision(currencyId)
				: CurrencyPrecision.TWO;
	}

	private AccountDimension newMinimalAccountDimension(final I_I_GLJournal importRecord, final int accountId)
	{
		return AccountDimension.builder()
				.setAcctSchemaId(AcctSchemaId.ofRepoIdOrNull(importRecord.getC_AcctSchema_ID()))
				.setAD_Client_ID(importRecord.getAD_Client_ID())
				.setAD_Org_ID(importRecord.getAD_Org_ID())
				.setC_ElementValue_ID(accountId)
				.build();
	}

	@Override
	public Class<I_I_GLJournal> getImportModelClass()
	{
		return I_I_GLJournal.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_GLJournal.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_GL_Journal.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return "COALESCE(BatchDocumentNo, I_GLJournal_ID ||' '), COALESCE(JournalDocumentNo, " +
				"I_GLJournal_ID ||' '), C_AcctSchema_ID, PostingType, C_DocType_ID, GL_Category_ID, " +
				"C_Currency_ID, TRUNC(DateAcct), Line, I_GLJournal_ID";
	}

	@Override
	public I_I_GLJournal retrieveImportRecord(Properties ctx, ResultSet rs)
	{
		return new X_I_GLJournal(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}
}