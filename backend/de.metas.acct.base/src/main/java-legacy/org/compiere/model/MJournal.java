/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

<<<<<<< HEAD
=======
import com.google.common.collect.ImmutableList;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.gljournal.IGLJournalBL;
import de.metas.acct.gljournal.IGLJournalLineBL;
import de.metas.acct.gljournal.IGLJournalLineDAO;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
<<<<<<< HEAD
import org.adempiere.util.LegacyAdapters;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.Properties;

/**
 * GL Journal Model
 *
 * @author Jorg Janke
<<<<<<< HEAD
 * @version $Id: MJournal.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1619150 ] Usability/Consistency: reversed gl journal description <li>BF [ 1775358 ] GL Journal DateAcct/C_Period_ID issue <li>FR [ 1776045 ] Add
 *         ReActivate action to GL Journal
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>FR [ 1948157 ] Is necessary the reference for document reverse
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962 <li>FR: [ 2214883 ] Remove SQL code and Replace for Query <li>FR [ 2520591 ] Support multiples calendar for
 *      Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
=======
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1619150 ] Usability/Consistency: reversed gl journal description <li>BF [ 1775358 ] GL Journal DateAcct/C_Period_ID issue <li>FR [ 1776045 ] Add
 * ReActivate action to GL Journal
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>FR [ 1948157 ] Is necessary the reference for document reverse
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
public class MJournal extends X_GL_Journal implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = -364132249042527640L;

<<<<<<< HEAD
	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param GL_Journal_ID id
	 * @param trxName transaction
	 */
	public MJournal(final Properties ctx, final int GL_Journal_ID, final String trxName)
	{
		super(ctx, GL_Journal_ID, trxName);
		if (GL_Journal_ID == 0)
=======
	public MJournal(final Properties ctx, final int GL_Journal_ID, final String trxName)
	{
		super(ctx, GL_Journal_ID, trxName);
		if (is_new())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			// setGL_Journal_ID (0); // PK
			// setC_AcctSchema_ID (0);
			// setC_Currency_ID (0);
			// setC_DocType_ID (0);
			//
			setCurrencyRate(BigDecimal.ONE);
			// setC_ConversionType_ID(0);
			setDateAcct(new Timestamp(System.currentTimeMillis()));
			setDateDoc(new Timestamp(System.currentTimeMillis()));
			// setDescription (null);
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			// setDocumentNo (null);
			// setGL_Category_ID (0);
			setPostingType(POSTINGTYPE_Actual);
			setTotalCr(BigDecimal.ZERO);
			setTotalDr(BigDecimal.ZERO);
			setIsApproved(false);
			setIsPrinted(false);
			setPosted(false);
			setProcessed(false);
		}
<<<<<<< HEAD
	}	// MJournal

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MJournal(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MJournal

	/**
	 * Parent Constructor.
	 *
	 * @param parent batch
	 */
	public MJournal(final MJournalBatch parent)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setGL_JournalBatch_ID(parent.getGL_JournalBatch_ID());
		setC_DocType_ID(parent.getC_DocType_ID());
		setPostingType(parent.getPostingType());
		//
		setDateDoc(parent.getDateDoc());
		setDateAcct(parent.getDateAcct());
		setC_Currency_ID(parent.getC_Currency_ID());
	}	// MJournal
=======
	}    // MJournal

	public MJournal(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    // MJournal


>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Copy Constructor. Dos not copy: Dates/Period
	 *
	 * @param original original
	 */
	public MJournal(final MJournal original)
	{
		this(original.getCtx(), 0, original.get_TrxName());
		setClientOrg(original);
		setGL_JournalBatch_ID(original.getGL_JournalBatch_ID());
		//
		setC_AcctSchema_ID(original.getC_AcctSchema_ID());
		setGL_Budget_ID(original.getGL_Budget_ID());
		setGL_Category_ID(original.getGL_Category_ID());
		setPostingType(original.getPostingType());
		setDescription(original.getDescription());
		setC_DocType_ID(original.getC_DocType_ID());
		setControlAmt(original.getControlAmt());
		//
		setC_Currency_ID(original.getC_Currency_ID());
		setC_ConversionType_ID(original.getC_ConversionType_ID());
		setCurrencyRate(original.getCurrencyRate());

		// setDateDoc(original.getDateDoc());
		// setDateAcct(original.getDateAcct());
<<<<<<< HEAD
	}	// MJournal
=======
	}    // MJournal
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Overwrite Client/Org if required
	 *
	 * @param AD_Client_ID client
<<<<<<< HEAD
	 * @param AD_Org_ID org
=======
	 * @param AD_Org_ID    org
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	@Override
	public void setClientOrg(final int AD_Client_ID, final int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
<<<<<<< HEAD
	}	// setClientOrg

	/**
	 * Set Accounting Date. Set also Period if not set earlier
	 *
	 * @param DateAcct date
	 */
	@Override
	public void setDateAcct(final Timestamp DateAcct)
	{
		super.setDateAcct(DateAcct);
		if (DateAcct == null)
			return;
	}	// setDateAcct
=======
	}    // setClientOrg
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Currency Info
	 *
<<<<<<< HEAD
	 * @param C_Currency_ID currency
	 * @param C_ConversionType_ID type
	 * @param CurrencyRate rate
=======
	 * @param C_Currency_ID       currency
	 * @param C_ConversionType_ID type
	 * @param CurrencyRate        rate
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	public void setCurrency(final int C_Currency_ID, final int C_ConversionType_ID, final BigDecimal CurrencyRate)
	{
		if (C_Currency_ID != 0)
			setC_Currency_ID(C_Currency_ID);
		if (C_ConversionType_ID != 0)
			setC_ConversionType_ID(C_ConversionType_ID);
		if (CurrencyRate != null && CurrencyRate.compareTo(BigDecimal.ZERO) == 0)
			setCurrencyRate(CurrencyRate);
<<<<<<< HEAD
	}	// setCurrency
=======
	}    // setCurrency
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Add to Description
	 *
	 * @param description text
	 * @since 3.1.4
	 */
	public void addDescription(final String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}

<<<<<<< HEAD
	/**************************************************************************
	 * Get Journal Lines
	 *
	 * @param requery requery
	 * @return Array of lines
	 */
	public MJournalLine[] getLines(final boolean requery)
	{
		final List<I_GL_JournalLine> lines = Services.get(IGLJournalLineDAO.class).retrieveLines(this);
		return LegacyAdapters.convertToPOArray(lines, MJournalLine.class);
	}	// getLines
=======
	private ImmutableList<I_GL_JournalLine> getActiveLines()
	{
		final IGLJournalLineDAO glJournalLineDAO = Services.get(IGLJournalLineDAO.class);
		return glJournalLineDAO.retrieveLines(this)
				.stream()
				.filter(I_GL_JournalLine::isActive)
				.collect(ImmutableList.toImmutableList());
	}    // getLines
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Copy Lines from other Journal
	 *
	 * @param fromJournal Journal
<<<<<<< HEAD
	 * @param dateAcct date used - if null original
	 * @param typeCR type of copying (C)orrect=negate - (R)everse=flip dr/cr - otherwise just copy
=======
	 * @param dateAcct    date used - if null original
	 * @param typeCR      type of copying (C)orrect=negate - (R)everse=flip dr/cr - otherwise just copy
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return number of lines copied
	 */
	private int copyLinesFrom(final MJournal fromJournal, final Timestamp dateAcct, final char typeCR)
	{
		if (isProcessed() || fromJournal == null)
			return 0;
		int count = 0;
<<<<<<< HEAD
		MJournalLine[] fromLines = fromJournal.getLines(false);
		for (final MJournalLine fromLine : fromLines)
		{
			MJournalLine toLine = new MJournalLine(getCtx(), 0, fromJournal.get_TrxName());
			PO.copyValues(fromLine, toLine, getAD_Client_ID(), getAD_Org_ID());
=======
		final ImmutableList<I_GL_JournalLine> fromLines = fromJournal.getActiveLines();
		for (final I_GL_JournalLine fromLine : fromLines)
		{
			MJournalLine toLine = new MJournalLine(getCtx(), 0, fromJournal.get_TrxName());
			PO.copyValues(InterfaceWrapperHelper.getPO(fromLine), toLine, getAD_Client_ID(), getAD_Org_ID());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			toLine.setGL_Journal(this);
			//
			if (dateAcct != null)
				toLine.setDateAcct(dateAcct);
			// Amounts
<<<<<<< HEAD
			if (typeCR == 'C')			// correct
=======
			if (typeCR == 'C')            // correct
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			{
				toLine.setAmtSourceDr(fromLine.getAmtSourceDr().negate());
				toLine.setAmtSourceCr(fromLine.getAmtSourceCr().negate());
				//
				toLine.setDR_AutoTaxAccount(fromLine.isDR_AutoTaxAccount());
				toLine.setDR_Tax_ID(fromLine.getDR_Tax_ID());
				toLine.setDR_Tax_Acct_ID(fromLine.getDR_Tax_Acct_ID());
				toLine.setDR_TaxBaseAmt(fromLine.getDR_TaxBaseAmt().negate());
				toLine.setDR_TaxAmt(fromLine.getDR_TaxAmt().negate());
				toLine.setDR_TaxTotalAmt(fromLine.getDR_TaxTotalAmt().negate());
<<<<<<< HEAD
=======

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				//
				toLine.setCR_AutoTaxAccount(fromLine.isCR_AutoTaxAccount());
				toLine.setCR_Tax_ID(fromLine.getCR_Tax_ID());
				toLine.setCR_Tax_Acct_ID(fromLine.getCR_Tax_Acct_ID());
				toLine.setCR_TaxBaseAmt(fromLine.getCR_TaxBaseAmt().negate());
				toLine.setCR_TaxAmt(fromLine.getCR_TaxAmt().negate());
				toLine.setCR_TaxTotalAmt(fromLine.getCR_TaxTotalAmt().negate());
<<<<<<< HEAD
			}
			else if (typeCR == 'R')		// reverse
=======

			}
			else if (typeCR == 'R')        // reverse
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			{
				toLine.setAmtSourceDr(fromLine.getAmtSourceCr());
				toLine.setAmtSourceCr(fromLine.getAmtSourceDr());

				if (fromLine.isDR_AutoTaxAccount() || fromLine.isCR_AutoTaxAccount())
				{
					throw new AdempiereException("Reverse accrual not supported for tax bookings");
				}
			}
			toLine.setIsGenerated(true);
			toLine.setProcessed(false);
			if (toLine.save())
				count++;
		}
<<<<<<< HEAD
		if (fromLines.length != count)
			log.error("Line difference - JournalLines=" + fromLines.length + " <> Saved=" + count);

		return count;
	}	// copyLinesFrom
=======
		if (fromLines.size() != count)
			log.error("Line difference - JournalLines=" + fromLines.size() + " <> Saved=" + count);

		return count;
	}    // copyLinesFrom
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Processed. Propagate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		String sql = "UPDATE GL_JournalLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE GL_Journal_ID=" + getGL_Journal_ID();
<<<<<<< HEAD
		int noLine = DB.executeUpdate(sql, get_TrxName());
		log.debug(processed + " - Lines=" + noLine);
	}	// setProcessed
=======
		int noLine = DB.executeUpdateAndSaveErrorOnFail(sql, get_TrxName());
		log.debug("{} - Lines={}", processed, noLine);
	}    // setProcessed
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Imported Journals may not have date
		if (getDateDoc() == null)
		{
			if (getDateAcct() == null)
				setDateDoc(new Timestamp(System.currentTimeMillis()));
			else
				setDateDoc(getDateAcct());
		}
		if (getDateAcct() == null)
			setDateAcct(getDateDoc());

		// Update DateAcct on lines - teo_sarca BF [ 1775358 ]
		if (!newRecord && is_ValueChanged(COLUMNNAME_DateAcct))
		{
			final int no = Services.get(IQueryBL.class)
					.createQueryBuilder(I_GL_JournalLine.class)
					.addEqualsFilter(I_GL_JournalLine.COLUMN_GL_Journal_ID, getGL_Journal_ID())
					.create()
					.updateDirectly()
					.addSetColumnValue(I_GL_JournalLine.COLUMNNAME_DateAcct, getDateAcct())
					.execute();
			log.trace("Updated GL_JournalLine.DateAcct #{}", no);
		}

		setAmtPrecision(this); // metas: cg: 02476

		return true;
<<<<<<< HEAD
	}	// beforeSave
=======
	}    // beforeSave
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * After Save. Update Batch Total
	 *
	 * @param newRecord true if new record
<<<<<<< HEAD
	 * @param success true if success
=======
	 * @param success   true if success
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * @return success
	 */
	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
			return success;

		updateBatch();

		return true;
<<<<<<< HEAD
	}	// afterSave
=======
	}    // afterSave
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * After Delete
	 *
	 * @param success true if deleted
	 * @return true if success
	 */
	@Override
	protected boolean afterDelete(final boolean success)
	{
		if (!success)
			return success;

		updateBatch();

		return true;
<<<<<<< HEAD
	}	// afterDelete
=======
	}    // afterDelete
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Update Batch total
	 */
	private void updateBatch()
	{
		final int glJournalBatchId = getGL_JournalBatch_ID();
<<<<<<< HEAD
		if(glJournalBatchId <= 0)
=======
		if (glJournalBatchId <= 0)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return;
		}

		final String sql = DB.convertSqlToNative("UPDATE " + I_GL_JournalBatch.Table_Name + " jb"
<<<<<<< HEAD
				+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)"
				+ " FROM GL_Journal j WHERE j.IsActive='Y' AND jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) "
				+ "WHERE GL_JournalBatch_ID=?");
		final int no = DB.executeUpdateEx(sql, new Object[] { glJournalBatchId }, get_TrxName());
=======
														 + " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)"
														 + " FROM GL_Journal j WHERE j.IsActive='Y' AND jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) "
														 + "WHERE GL_JournalBatch_ID=?");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { glJournalBatchId }, get_TrxName());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (no != 1)
		{
			throw new AdempiereException("Failed updating GL_JournalBatch_ID=" + glJournalBatchId);
		}
	}

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/**
	 * Unlock Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.info(toString());
		setProcessing(false);
		return true;
<<<<<<< HEAD
	}	// unlockIt
=======
	}    // unlockIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Invalidate Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info(toString());
		return true;
<<<<<<< HEAD
	}	// invalidateIt
=======
	}    // invalidateIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Prepare Document
	 *
	 * @return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Assert period is open
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
<<<<<<< HEAD

		// Lines
		final MJournalLine[] lines = getLines(true);
		if (lines.length == 0)
=======
		final int headerPeriodId = MPeriod.get(getCtx(), getDateAcct(), getAD_Org_ID()).getC_Period_ID();

		// Lines
		final ImmutableList<I_GL_JournalLine> lines = getActiveLines();
		if (lines.isEmpty())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			throw AdempiereException.noLines();
		}

<<<<<<< HEAD
=======
		// Make sure the line period is the same as header period, else
		// * we would have Fact_Acct.DateAcct/C_Period_ID mismatches
		// * we would need to enforce each period is open (on process and on posting)
		final IGLJournalBL glJournalBL = Services.get(IGLJournalBL.class);
		glJournalBL.assertSamePeriod(this, lines);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		// Add up Amounts
		BigDecimal AmtAcctDr = BigDecimal.ZERO;
		BigDecimal AmtAcctCr = BigDecimal.ZERO;
		for (final I_GL_JournalLine line : lines)
		{
<<<<<<< HEAD
			if (!isActive())
			{
				continue;
			}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			if (line.isAllowAccountDR())
			{
				Services.get(IGLJournalLineBL.class).assertAccountValid(line.getAccount_DR(), line);
			}
			if (line.isAllowAccountCR())
			{
				Services.get(IGLJournalLineBL.class).assertAccountValid(line.getAccount_CR(), line);
			}

			AmtAcctDr = AmtAcctDr.add(line.getAmtAcctDr());
			AmtAcctCr = AmtAcctCr.add(line.getAmtAcctCr());
		}
		setTotalDr(AmtAcctDr);
		setTotalCr(AmtAcctCr);

		// Control Amount
		if (BigDecimal.ZERO.compareTo(getControlAmt()) != 0
				&& getControlAmt().compareTo(getTotalDr()) != 0)
		{
			throw new AdempiereException("@ControlAmtError@");
		}

		// Unbalanced Jornal & Not Suspense
		if (AmtAcctDr.compareTo(AmtAcctCr) != 0)
		{
			final AcctSchema acctSchema = getAcctSchema();
			final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
			if (!acctSchemaGL.isSuspenseBalancing())
			{
				throw new AdempiereException("@UnbalancedJornal@");
			}
		}

		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		return IDocument.STATUS_InProgress;
<<<<<<< HEAD
	}	// prepareIt
=======
	}    // prepareIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	private AcctSchema getAcctSchema()
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(getC_AcctSchema_ID());
		final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
<<<<<<< HEAD
		final AcctSchema acctSchema = acctSchemasRepo.getById(acctSchemaId);
		return acctSchema;
=======
		return acctSchemasRepo.getById(acctSchemaId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * Approve Document
	 *
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.info(toString());
		setIsApproved(true);
		return true;
<<<<<<< HEAD
	}	// approveIt
=======
	}    // approveIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Reject Approval
	 *
	 * @return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info(toString());
		setIsApproved(false);
		return true;
<<<<<<< HEAD
	}	// rejectIt
=======
	}    // rejectIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Complete Document
	 *
	 * @return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();
		log.debug("Completed: {}", this);
		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return IDocument.STATUS_Completed;
<<<<<<< HEAD
	}	// completeIt
=======
	}    // completeIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete())
		{
			setDateDoc(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete())
		{
			final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
			final String value = documentNoFactory.forDocType(getC_DocType_ID(), true) // useDefiniteSequence=true
					.setDocumentModel(this)
					.setFailOnError(false)
					.build();
			if (value != null && value != IDocumentNoBuilder.NO_DOCUMENTNO)
				setDocumentNo(value);
		}
	}

	/**
	 * Void Document.
	 *
	 * @return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

<<<<<<< HEAD
		boolean ok_to_void = false;
=======
		final boolean ok_to_void;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus()))
		{
			setProcessed(true);
			setDocAction(DOCACTION_None);
			ok_to_void = true;
		}
		else
		{
			return false;
		}

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		return ok_to_void;
<<<<<<< HEAD
	}	// voidIt
=======
	}    // voidIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Close Document. Cancel not delivered Qunatities
	 *
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

<<<<<<< HEAD
		boolean ok_to_close = false;
=======
		final boolean ok_to_close;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (DOCSTATUS_Completed.equals(getDocStatus()))
		{
			setProcessed(true);
			setDocAction(DOCACTION_None);
			ok_to_close = true;
		}
		else
		{
			return false;
		}

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return ok_to_close;
<<<<<<< HEAD
	}	// closeIt
=======
	}    // closeIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Reverse Correction (in same batch). As if nothing happened - same date
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		boolean ok_correct = (reverseCorrectIt(getGL_JournalBatch_ID()) != null);

		if (!ok_correct)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return ok_correct;
<<<<<<< HEAD
	}	// reverseCorrectIt
=======
	}    // reverseCorrectIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Reverse Correction. As if nothing happened - same date
	 *
	 * @param GL_JournalBatch_ID reversal batch
	 * @return reversed Journal or null
	 */
	MJournal reverseCorrectIt(final int GL_JournalBatch_ID)
	{
		log.info("{}", this);

		// Journal
		final MJournal reverse = new MJournal(this);
		reverse.setGL_JournalBatch_ID(GL_JournalBatch_ID);
		reverse.setDateDoc(getDateDoc());
		reverse.setDateAcct(getDateAcct());
		// Reverse indicator
		reverse.addDescription("(->" + getDocumentNo() + ")");
		reverse.setReversal_ID(getGL_Journal_ID()); // FR [ 1948157 ]
		InterfaceWrapperHelper.save(reverse);
		addDescription("(" + reverse.getDocumentNo() + "<-)");

		reverse.setControlAmt(this.getControlAmt().negate());

		// Lines
		reverse.copyLinesFrom(this, null, 'C');

		// Complete the reversal and set it's status to Reversed
		if (!reverse.processIt(IDocument.ACTION_Complete))
		{
			throw new AdempiereException(reverse.getProcessMsg());
		}
		reverse.setDocStatus(DOCSTATUS_Reversed);
		reverse.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reverse);

		//
		// Update this journal
		setProcessed(true);
		setReversal_ID(reverse.getGL_Journal_ID()); // FR [ 1948157 ]
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		saveEx();

		return reverse;
<<<<<<< HEAD
	}	// reverseCorrectionIt
=======
	}    // reverseCorrectionIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Reverse Accrual (sane batch). Flip Dr/Cr - Use Today's date
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		boolean ok_reverse = (reverseAccrualIt(getGL_JournalBatch_ID()) != null);

		if (!ok_reverse)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return ok_reverse;
<<<<<<< HEAD
	}	// reverseAccrualIt
=======
	}    // reverseAccrualIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Reverse Accrual. Flip Dr/Cr - Use Today's date
	 *
	 * @param GL_JournalBatch_ID reversal batch
	 * @return reversed journal or null
	 */
	public MJournal reverseAccrualIt(final int GL_JournalBatch_ID)
	{
		log.info(toString());
		// Journal
		MJournal reverse = new MJournal(this);
		reverse.setGL_JournalBatch_ID(GL_JournalBatch_ID);
		reverse.setDateDoc(new Timestamp(System.currentTimeMillis()));
<<<<<<< HEAD
		reverse.set_ValueNoCheck("C_Period_ID", null);		// reset
=======
		reverse.set_ValueNoCheck("C_Period_ID", null);        // reset
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		reverse.setDateAcct(reverse.getDateDoc());
		// Reverse indicator
		String description = reverse.getDescription();
		if (description == null)
			description = "** " + getDocumentNo() + " **";
		else
			description += " ** " + getDocumentNo() + " **";
		reverse.setDescription(description);
		if (!reverse.save())
			return null;

		// Lines
		reverse.copyLinesFrom(this, reverse.getDateAcct(), 'R');
		//
		setProcessed(true);
		setDocAction(DOCACTION_None);
		return reverse;
<<<<<<< HEAD
	}	// reverseAccrualIt
=======
	}    // reverseAccrualIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Re-activate
	 *
	 * @return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// teo_sarca - FR [ 1776045 ] Add ReActivate action to GL Journal
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());
		Services.get(IFactAcctDAO.class).deleteForDocument(this);
		setPosted(false);
		setProcessed(false);
		setDocAction(DOCACTION_Complete);

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
<<<<<<< HEAD
		if (m_processMsg != null)
			return false;

		return true;
	}	// reActivateIt
=======
		return m_processMsg == null;
	}    // reActivateIt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		// : Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(msgBL.translate(getCtx(), "TotalDr")).append("=").append(getTotalDr())
				.append(" ")
				.append(msgBL.translate(getCtx(), "TotalCR")).append("=").append(getTotalCr())
		// .append(" (#").append(getLines(false).length).append(")")
		;
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
<<<<<<< HEAD
	}	// getSummary

=======
	}    // getSummary
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Override
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getDateDoc());
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
<<<<<<< HEAD
		StringBuffer sb = new StringBuffer("MJournal[");
		sb.append(get_ID()).append(",").append(getDescription())
				.append(",DR=").append(getTotalDr())
				.append(",CR=").append(getTotalCr())
				.append("]");
		return sb.toString();
	}	// toString
=======
		return "MJournal["
				+ get_ID() + "," + getDescription()
				+ ",DR=" + getTotalDr()
				+ ",CR=" + getTotalCr()
				+ "]";
	}    // toString
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
<<<<<<< HEAD
	}	// getDocumentInfo
=======
	}    // getDocumentInfo
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
	@Override
	public File createPDF()
	{
<<<<<<< HEAD
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	// getPDF

	/**
	 * Create PDF file
	 *
	 * @param file output file
	 * @return file if success
	 */
	public File createPDF(final File file)
	{
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}	// createPDF
=======
		return null;
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
<<<<<<< HEAD
	}	// getProcessMsg
=======
	}    // getProcessMsg
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID (Created)
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
<<<<<<< HEAD
	}	// getDoc_User_ID
=======
	}    // getDoc_User_ID
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Document Approval Amount
	 *
	 * @return DR amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getTotalDr();
<<<<<<< HEAD
	}	// getApprovalAmt
=======
	}    // getApprovalAmt
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	@Deprecated
	public boolean isComplete()
	{
		return Services.get(IGLJournalBL.class).isComplete(this);
<<<<<<< HEAD
	}	// isComplete
=======
	}    // isComplete
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	// metas: cg: 02476
	private static void setAmtPrecision(final I_GL_Journal journal)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoIdOrNull(journal.getC_AcctSchema_ID());
		if (acctSchemaId == null)
		{
			return;
		}
<<<<<<< HEAD
		
		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		final CurrencyPrecision precision = as.getStandardPrecision();
		
		final BigDecimal controlAmt = precision.roundIfNeeded(journal.getControlAmt());
		journal.setControlAmt(controlAmt);
	}
}	// MJournal
=======

		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getById(acctSchemaId);
		final CurrencyPrecision precision = as.getStandardPrecision();

		final BigDecimal controlAmt = precision.roundIfNeeded(journal.getControlAmt());
		journal.setControlAmt(controlAmt);
	}
}    // MJournal
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
