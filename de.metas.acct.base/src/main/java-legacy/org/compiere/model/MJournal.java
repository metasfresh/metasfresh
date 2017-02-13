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

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.acct.api.IGLJournalBL;
import org.adempiere.acct.api.IGLJournalLineBL;
import org.adempiere.acct.api.IGLJournalLineDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;

/**
 * GL Journal Model
 *
 * @author Jorg Janke
 * @version $Id: MJournal.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1619150 ] Usability/Consistency: reversed gl journal description <li>BF [ 1775358 ] GL Journal DateAcct/C_Period_ID issue <li>FR [ 1776045 ] Add
 *         ReActivate action to GL Journal
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>FR [ 1948157 ] Is necessary the reference for document reverse
 * @see http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962 <li>FR: [ 2214883 ] Remove SQL code and Replace for Query <li>FR [ 2520591 ] Support multiples calendar for
 *      Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 */
public class MJournal extends X_GL_Journal implements DocAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -364132249042527640L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param GL_Journal_ID id
	 * @param trxName transaction
	 */
	public MJournal(Properties ctx, int GL_Journal_ID, String trxName)
	{
		super(ctx, GL_Journal_ID, trxName);
		if (GL_Journal_ID == 0)
		{
			// setGL_Journal_ID (0); // PK
			// setC_AcctSchema_ID (0);
			// setC_Currency_ID (0);
			// setC_DocType_ID (0);
			//
			setCurrencyRate(Env.ONE);
			// setC_ConversionType_ID(0);
			setDateAcct(new Timestamp(System.currentTimeMillis()));
			setDateDoc(new Timestamp(System.currentTimeMillis()));
			// setDescription (null);
			setDocAction(DOCACTION_Complete);
			setDocStatus(DOCSTATUS_Drafted);
			// setDocumentNo (null);
			// setGL_Category_ID (0);
			setPostingType(POSTINGTYPE_Actual);
			setTotalCr(Env.ZERO);
			setTotalDr(Env.ZERO);
			setIsApproved(false);
			setIsPrinted(false);
			setPosted(false);
			setProcessed(false);
		}
	}	// MJournal

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MJournal(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MJournal

	/**
	 * Parent Constructor.
	 *
	 * @param parent batch
	 */
	public MJournal(MJournalBatch parent)
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

	/**
	 * Copy Constructor. Dos not copy: Dates/Period
	 *
	 * @param original original
	 */
	public MJournal(MJournal original)
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
	}	// MJournal

	/**
	 * Overwrite Client/Org if required
	 * 
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	@Override
	public void setClientOrg(int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	// setClientOrg

	/**
	 * Set Accounting Date. Set also Period if not set earlier
	 *
	 * @param DateAcct date
	 */
	@Override
	public void setDateAcct(Timestamp DateAcct)
	{
		super.setDateAcct(DateAcct);
		if (DateAcct == null)
			return;
	}	// setDateAcct

	/**
	 * Set Currency Info
	 *
	 * @param C_Currency_ID currenct
	 * @param C_ConversionType_ID type
	 * @param CurrencyRate rate
	 */
	public void setCurrency(int C_Currency_ID, int C_ConversionType_ID, BigDecimal CurrencyRate)
	{
		if (C_Currency_ID != 0)
			setC_Currency_ID(C_Currency_ID);
		if (C_ConversionType_ID != 0)
			setC_ConversionType_ID(C_ConversionType_ID);
		if (CurrencyRate != null && CurrencyRate.compareTo(Env.ZERO) == 0)
			setCurrencyRate(CurrencyRate);
	}	// setCurrency

	/**
	 * Add to Description
	 * 
	 * @param description text
	 * @since 3.1.4
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}

	/**************************************************************************
	 * Get Journal Lines
	 * 
	 * @param requery requery
	 * @return Array of lines
	 */
	public MJournalLine[] getLines(boolean requery)
	{
		final List<I_GL_JournalLine> lines = Services.get(IGLJournalLineDAO.class).retrieveLines(this);
		return LegacyAdapters.convertToPOArray(lines, MJournalLine.class);
	}	// getLines

	/**
	 * Copy Lines from other Journal
	 *
	 * @param fromJournal Journal
	 * @param dateAcct date used - if null original
	 * @param typeCR type of copying (C)orrect=negate - (R)everse=flip dr/cr - otherwise just copy
	 * @return number of lines copied
	 */
	public int copyLinesFrom(MJournal fromJournal, Timestamp dateAcct, char typeCR)
	{
		if (isProcessed() || fromJournal == null)
			return 0;
		int count = 0;
		MJournalLine[] fromLines = fromJournal.getLines(false);
		for (int i = 0; i < fromLines.length; i++)
		{
			final MJournalLine fromLine = fromLines[i];
			
			MJournalLine toLine = new MJournalLine(getCtx(), 0, fromJournal.get_TrxName());
			PO.copyValues(fromLine, toLine, getAD_Client_ID(), getAD_Org_ID());
			toLine.setGL_Journal(this);
			//
			if (dateAcct != null)
				toLine.setDateAcct(dateAcct);
			// Amounts
			if (typeCR == 'C')			// correct
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
				//
				toLine.setCR_AutoTaxAccount(fromLine.isCR_AutoTaxAccount());
				toLine.setCR_Tax_ID(fromLine.getCR_Tax_ID());
				toLine.setCR_Tax_Acct_ID(fromLine.getCR_Tax_Acct_ID());
				toLine.setCR_TaxBaseAmt(fromLine.getCR_TaxBaseAmt().negate());
				toLine.setCR_TaxAmt(fromLine.getCR_TaxAmt().negate());
				toLine.setCR_TaxTotalAmt(fromLine.getCR_TaxTotalAmt().negate());
			}
			else if (typeCR == 'R')		// reverse
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
		if (fromLines.length != count)
			log.error("Line difference - JournalLines=" + fromLines.length + " <> Saved=" + count);

		return count;
	}	// copyLinesFrom

	/**
	 * Set Processed. Propagate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		String sql = "UPDATE GL_JournalLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE GL_Journal_ID=" + getGL_Journal_ID();
		int noLine = DB.executeUpdate(sql, get_TrxName());
		log.debug(processed + " - Lines=" + noLine);
	}	// setProcessed

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
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
		if (is_ValueChanged(COLUMNNAME_DateAcct))
		{
			int no = DB.executeUpdate(
					"UPDATE GL_JournalLine SET " + MJournalLine.COLUMNNAME_DateAcct + "=? WHERE GL_Journal_ID=?",
					new Object[] { getDateAcct(), getGL_Journal_ID() },
					false, get_TrxName());
			log.trace("Updated GL_JournalLine.DateAcct #" + no);
		}

		setAmtPrecision(this); // metas: cg: 02476

		return true;
	}	// beforeSave

	/**
	 * After Save. Update Batch Total
	 *
	 * @param newRecord true if new record
	 * @param success true if success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		return updateBatch();
	}	// afterSave

	/**
	 * After Delete
	 *
	 * @param success true if deleted
	 * @return true if success
	 */
	@Override
	protected boolean afterDelete(boolean success)
	{
		if (!success)
			return success;
		return updateBatch();
	}	// afterDelete

	/**
	 * Update Batch total
	 *
	 * @return true if ok
	 */
	private boolean updateBatch()
	{
		String sql = DB.convertSqlToNative("UPDATE GL_JournalBatch jb"
				+ " SET (TotalDr, TotalCr) = (SELECT COALESCE(SUM(TotalDr),0), COALESCE(SUM(TotalCr),0)"
				+ " FROM GL_Journal j WHERE j.IsActive='Y' AND jb.GL_JournalBatch_ID=j.GL_JournalBatch_ID) "
				+ "WHERE GL_JournalBatch_ID=" + getGL_JournalBatch_ID());
		int no = DB.executeUpdate(sql, get_TrxName());
		if (no != 1)
			log.warn("afterSave - Update Batch #" + no);
		return no == 1;
	}	// updateBatch

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction());
	}	// process

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
	}	// unlockIt

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
	}	// invalidateIt

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
			return DocAction.STATUS_Invalid;
		}

		// Assert period is open
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), getC_DocType_ID(), getAD_Org_ID());

		// Lines
		final MJournalLine[] lines = getLines(true);
		if (lines.length == 0)
		{
			throw new AdempiereException("@NoLines@");
		}

		// Add up Amounts
		BigDecimal AmtAcctDr = Env.ZERO;
		BigDecimal AmtAcctCr = Env.ZERO;
		for (final I_GL_JournalLine line : lines)
		{
			if (!isActive())
			{
				continue;
			}

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
		if (Env.ZERO.compareTo(getControlAmt()) != 0
				&& getControlAmt().compareTo(getTotalDr()) != 0)
		{
			throw new AdempiereException("@ControlAmtError@");
		}

		// Unbalanced Jornal & Not Suspense
		if (AmtAcctDr.compareTo(AmtAcctCr) != 0)
		{
			final MAcctSchemaGL gl = MAcctSchemaGL.get(getCtx(), getC_AcctSchema_ID());
			if (gl == null || !gl.isUseSuspenseBalancing())
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
			return DocAction.STATUS_Invalid;
		}

		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	}	// prepareIt

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
	}	// approveIt

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
	}	// rejectIt

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
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Implicit Approval
		if (!isApproved())
			approveIt();
		log.debug("Completed: {}", this);
		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		// Set the definite document number after completed (if needed)
		setDefiniteDocumentNo();

		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	// completeIt

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
					.setTrxName(get_TrxName())
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

		boolean ok_to_void = false;
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
	}	// voidIt

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

		boolean ok_to_close = false;
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
	}	// closeIt

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
	}	// reverseCorrectIt

	/**
	 * Reverse Correction. As if nothing happened - same date
	 * 
	 * @param GL_JournalBatch_ID reversal batch
	 * @return reversed Journal or null
	 */
	MJournal reverseCorrectIt(int GL_JournalBatch_ID)
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
		if (!reverse.processIt(DocAction.ACTION_Complete))
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
	}	// reverseCorrectionIt

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
	}	// reverseAccrualIt

	/**
	 * Reverse Accrual. Flip Dr/Cr - Use Today's date
	 * 
	 * @param GL_JournalBatch_ID reversal batch
	 * @return reversed journal or null
	 */
	public MJournal reverseAccrualIt(int GL_JournalBatch_ID)
	{
		log.info(toString());
		// Journal
		MJournal reverse = new MJournal(this);
		reverse.setGL_JournalBatch_ID(GL_JournalBatch_ID);
		reverse.setDateDoc(new Timestamp(System.currentTimeMillis()));
		reverse.set_ValueNoCheck("C_Period_ID", null);		// reset
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
	}	// reverseAccrualIt

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
		if (m_processMsg != null)
			return false;

		return true;
	}	// reActivateIt

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
	}	// getSummary

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MJournal[");
		sb.append(get_ID()).append(",").append(getDescription())
				.append(",DR=").append(getTotalDr())
				.append(",CR=").append(getTotalCr())
				.append("]");
		return sb.toString();
	}	// toString

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
	}	// getDocumentInfo

	/**
	 * Create PDF
	 *
	 * @return File or null
	 */
	@Override
	public File createPDF()
	{
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
	public File createPDF(File file)
	{
		// ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
		// if (re == null)
		return null;
		// return re.getPDF(file);
	}	// createPDF

	/**
	 * Get Process Message
	 *
	 * @return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	// getProcessMsg

	/**
	 * Get Document Owner (Responsible)
	 *
	 * @return AD_User_ID (Created)
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}	// getDoc_User_ID

	/**
	 * Get Document Approval Amount
	 *
	 * @return DR amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getTotalDr();
	}	// getApprovalAmt

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	@Deprecated
	public boolean isComplete()
	{
		return Services.get(IGLJournalBL.class).isComplete(this);
	}	// isComplete

	// metas: cg: 02476
	public static void setAmtPrecision(I_GL_Journal journal)
	{
		if (journal.getC_AcctSchema_ID() <= 0)
		{
			return;
		}
		final Properties ctx = InterfaceWrapperHelper.getCtx(journal);
		final MAcctSchema as = MAcctSchema.get(ctx, journal.getC_AcctSchema_ID());
		final int precision = as.getStdPrecision();
		if (journal.getControlAmt().scale() > precision)
			journal.setControlAmt(journal.getControlAmt().setScale(precision, BigDecimal.ROUND_HALF_UP));
	}
}	// MJournal
