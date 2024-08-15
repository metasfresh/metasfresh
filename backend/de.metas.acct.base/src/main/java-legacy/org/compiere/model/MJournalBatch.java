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

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

/**
 *  Journal Batch Model
 *
 *    @author Jorg Janke
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li>FR [ 1948157  ]  Is necessary the reference for document reverse
 *        @see <a href="http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1948157&group_id=176962">...</a>
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org
 *            @see <a href="http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962">...</a>
 *  @author Teo Sarca, www.arhipac.ro
 * 			<li>FR [ 1776045 ] Add ReActivate action to GL Journal
 *    @version $Id: MJournalBatch.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MJournalBatch extends X_GL_JournalBatch implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2494833602067696046L;

	public MJournalBatch (Properties ctx, int GL_JournalBatch_ID, String trxName)
	{
		super (ctx, GL_JournalBatch_ID, trxName);
		if (is_new())
		{
		//	setGL_JournalBatch_ID (0);	PK
		//	setDescription (null);
		//	setDocumentNo (null);
		//	setC_DocType_ID (0);
			setPostingType (POSTINGTYPE_Actual);
			setDocAction (DOCACTION_Complete);
			setDocStatus (DOCSTATUS_Drafted);
			setTotalCr (BigDecimal.ZERO);
			setTotalDr (BigDecimal.ZERO);
			setProcessed (false);
			setProcessing (false);
			setIsApproved(false);
		}
	}	//	MJournalBatch

	@SuppressWarnings("unused")
	public MJournalBatch (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MJournalBatch

	/**
	 * 	Copy Constructor.
	 * 	Dos not copy: Dates/Period
	 *	@param original original
	 */
	public MJournalBatch (MJournalBatch original)
	{
		this (original.getCtx(), 0, original.get_TrxName());
		setClientOrg(original);
		setGL_JournalBatch_ID(original.getGL_JournalBatch_ID());
		//
	//	setC_AcctSchema_ID(original.getC_AcctSchema_ID());
	//	setGL_Budget_ID(original.getGL_Budget_ID());
		setGL_Category_ID(original.getGL_Category_ID());
		setPostingType(original.getPostingType());
		setDescription(original.getDescription());
		setC_DocType_ID(original.getC_DocType_ID());
		setControlAmt(original.getControlAmt());
		//
		setC_Currency_ID(original.getC_Currency_ID());
	//	setC_ConversionType_ID(original.getC_ConversionType_ID());
	//	setCurrencyRate(original.getCurrencyRate());

	//	setDateDoc(original.getDateDoc());
	//	setDateAcct(original.getDateAcct());
	}	//	MJournal



	/**
	 * 	Overwrite Client/Org if required
	 * 	@param AD_Client_ID client
	 * 	@param AD_Org_ID org
	 */
	@Override
	public void setClientOrg (int AD_Client_ID, int AD_Org_ID)
	{
		super.setClientOrg(AD_Client_ID, AD_Org_ID);
	}	//	setClientOrg

	/**
	 * 	Set Accounting Date.
	 * 	Set also Period if not set earlier
	 *	@param DateAcct date
	 */
	@Override
	public void setDateAcct (Timestamp DateAcct)
	{
		super.setDateAcct(DateAcct);
		if (DateAcct == null)
			return;
	}	//	setDateAcct

	/**
	 * 	Get Journal Lines
	 * 	@param requery requery
	 *	@return Array of lines
	 */
	public MJournal[] getJournals (boolean requery)
	{
		ArrayList<MJournal> list = new ArrayList<>();
		String sql = "SELECT * FROM GL_Journal WHERE GL_JournalBatch_ID=? ORDER BY DocumentNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getGL_JournalBatch_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MJournal (getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//
		MJournal[] retValue = new MJournal[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getJournals

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	@Override
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	}	//	unlockIt

	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	@Override
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		return true;
	}	//	invalidateIt

	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid)
	 */
	@Override
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return IDocument.STATUS_Invalid;
		}

		//	Add up Amounts & prepare them
		MJournal[] journals = getJournals(false);
		if (journals.length == 0)
		{
			throw AdempiereException.noLines();
		}
		
		BigDecimal TotalDr = BigDecimal.ZERO;
		BigDecimal TotalCr = BigDecimal.ZERO;		
		for (final MJournal journal : journals)
		{
			if (!journal.isActive())
				continue;
			//	Prepare if not closed
			if (DOCSTATUS_Closed.equals(journal.getDocStatus())
				|| DOCSTATUS_Voided.equals(journal.getDocStatus())
				|| DOCSTATUS_Reversed.equals(journal.getDocStatus())
				|| DOCSTATUS_Completed.equals(journal.getDocStatus()))
				;
			else
			{
				String status = journal.prepareIt();
				if (!IDocument.STATUS_InProgress.equals(status))
				{
					journal.setDocStatus(status);
					journal.save();
					m_processMsg = journal.getProcessMsg();
					return status;
				}
				journal.setDocStatus(DOCSTATUS_InProgress);
				journal.save();
			}
			//
			TotalDr = TotalDr.add(journal.getTotalDr());
			TotalCr = TotalCr.add(journal.getTotalCr());
		}
		setTotalDr(TotalDr);
		setTotalCr(TotalCr);

		//	Control Amount
		if (BigDecimal.ZERO.compareTo(getControlAmt()) != 0
			&& getControlAmt().compareTo(getTotalDr()) != 0)
		{
			m_processMsg = "@ControlAmtError@";
			return IDocument.STATUS_Invalid;
		}

		// NOTE: don't copy C_ConversionType_ID, CurrencyRate from GL_Journal to GL_JournalLine because it's OK to have different currencies on each line

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		//	Add up Amounts
		m_justPrepared = true;
		return IDocument.STATUS_InProgress;
	}	//	prepareIt

	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	@Override
	public boolean  approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt

	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	@Override
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt

	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	@Override
	public String completeIt()
	{
		log.debug("Completed: {}", this);
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		//	Implicit Approval
		approveIt();

		//	Add up Amounts & complete them
		MJournal[] journals = getJournals(true);
		BigDecimal TotalDr = BigDecimal.ZERO;
		BigDecimal TotalCr = BigDecimal.ZERO;		
		for (final MJournal journal : journals)
		{
			if (!journal.isActive())
			{
				journal.setProcessed(true);
				journal.setDocStatus(DOCSTATUS_Voided);
				journal.setDocAction(DOCACTION_None);
				journal.save();
				continue;
			}
			//	Complete if not closed
			if (DOCSTATUS_Closed.equals(journal.getDocStatus())
				|| DOCSTATUS_Voided.equals(journal.getDocStatus())
				|| DOCSTATUS_Reversed.equals(journal.getDocStatus())
				|| DOCSTATUS_Completed.equals(journal.getDocStatus()))
				;
			else
			{
				String status = journal.completeIt();
				if (!IDocument.STATUS_Completed.equals(status))
				{
					journal.setDocStatus(status);
					journal.save();
					m_processMsg = journal.getProcessMsg();
					return status;
				}
				journal.setDocStatus(DOCSTATUS_Completed);
				journal.save();
			}
			//
			TotalDr = TotalDr.add(journal.getTotalDr());
			TotalCr = TotalCr.add(journal.getTotalCr());
		}
		setTotalDr(TotalDr);
		setTotalCr(TotalCr);
		//	User Validation
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
	}	//	completeIt

	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateDoc(new Timestamp (System.currentTimeMillis()));
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
	 * 	Void Document.
	 * 	@return false
	 */
	@Override
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;
		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	@return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		MJournal[] journals = getJournals(true);
		for (MJournal journal2 : journals)
		{
			MJournal journal = journal2;
			if (!journal.isActive() && !journal.isProcessed())
			{
				journal.setProcessed(true);
				journal.setDocStatus(DOCSTATUS_Voided);
				journal.setDocAction(DOCACTION_None);
				journal.save();
				continue;
			}
			if (DOCSTATUS_Drafted.equals(journal.getDocStatus())
				|| DOCSTATUS_InProgress.equals(journal.getDocStatus())
				|| DOCSTATUS_Invalid.equals(journal.getDocStatus()))
			{
				m_processMsg = "Journal not Completed: " + journal.getSummary();
				return false;
			}

			//	Close if not closed
			if (DOCSTATUS_Closed.equals(journal.getDocStatus())
				|| DOCSTATUS_Voided.equals(journal.getDocStatus())
				|| DOCSTATUS_Reversed.equals(journal.getDocStatus()))
				;
			else
			{
				if (!journal.closeIt())
				{
					m_processMsg = "Cannot close: " + journal.getSummary();
					return false;
				}
				journal.save();
			}
		}
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction.
	 * 	As if nothing happened - same date
	 * 	@return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		MJournal[] journals = getJournals(true);
		//	check prerequisites
		for (MJournal journal2 : journals)
		{
			MJournal journal = journal2;
			if (!journal.isActive())
				continue;
			//	All need to be closed/Completed
			if (DOCSTATUS_Completed.equals(journal.getDocStatus()))
				;
			else
			{
				m_processMsg = "All Journals need to be Completed: " + journal.getSummary();
				return false;
			}
		}

		//	Reverse it
		final MJournalBatch reverse = new MJournalBatch (this);
		reverse.setDateDoc(getDateDoc());
		reverse.setDateAcct(getDateAcct());
		//	Reverse indicator
		String description = reverse.getDescription();
		if (description == null)
			description = "** " + getDocumentNo() + " **";
		else
			description += " ** " + getDocumentNo() + " **";
		reverse.setDescription(description);
		reverse.setReversal_ID(getGL_JournalBatch_ID()); //[ 1948157  ]
		reverse.setControlAmt(this.getControlAmt().negate());
		InterfaceWrapperHelper.save(reverse);
		//

		//	Reverse Journals
		for (MJournal journal2 : journals)
		{
			final MJournal journal = journal2;
			if (!journal.isActive())
				continue;
			if (journal.reverseCorrectIt(reverse.getGL_JournalBatch_ID()) == null)
			{
				throw new AdempiereException("Could not reverse " + journal);
			}
			InterfaceWrapperHelper.save(journal);
		}

		//
		// Mark the reversal journal batch as reversed
		reverse.setProcessed(true);
		reverse.setProcessing(false);
		reverse.setDocStatus(DOCSTATUS_Reversed);
		reverse.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reverse);

		//
		// Update this Journal Batch
		setReversal_ID(reverse.getGL_JournalBatch_ID()); //[ 1948157  ]
		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
		saveEx();

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual.
	 * 	Flip Dr/Cr - Use Today's date
	 * 	@return true if success
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		MJournal[] journals = getJournals(true);
		//	check prerequisites
		for (MJournal journal2 : journals)
		{
			MJournal journal = journal2;
			if (!journal.isActive())
				continue;
			//	All need to be closed/Completed
			if (DOCSTATUS_Completed.equals(journal.getDocStatus()))
				;
			else
			{
				m_processMsg = "All Journals need to be Completed: " + journal.getSummary();
				return false;
			}
		}
		//	Reverse it
		MJournalBatch reverse = new MJournalBatch (this);
		reverse.setDateDoc(new Timestamp(System.currentTimeMillis()));
		reverse.setDateAcct(reverse.getDateDoc());
		//	Reverse indicator
		String description = reverse.getDescription();
		if (description == null)
			description = "** " + getDocumentNo() + " **";
		else
			description += " ** " + getDocumentNo() + " **";
		reverse.setDescription(description);
		reverse.save();

		//	Reverse Journals
		for (MJournal journal2 : journals)
		{
			MJournal journal = journal2;
			if (!journal.isActive())
				continue;
			if (journal.reverseAccrualIt(reverse.getGL_JournalBatch_ID()) == null)
			{
				m_processMsg = "Could not reverse " + journal;
				return false;
			}
			journal.save();
		}
		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate - same as reverse correct
	 * 	@return true if success
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		for (MJournal journal : getJournals(true))
		{
			if (DOCSTATUS_Completed.equals(journal.getDocStatus()))
			{
				if (journal.processIt(DOCACTION_Re_Activate))
				{
					journal.saveEx();
				}
				else
				{
					throw new AdempiereException(journal.getProcessMsg());
				}
			}
		}
		setProcessed(false);
		setDocAction(DOCACTION_Complete);

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	reActivateIt


	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		StringBuilder sb = new StringBuilder();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ")
		.append(msgBL.translate(getCtx(),"TotalDr")).append("=").append(getTotalDr())
		.append(" ")
		.append(msgBL.translate(getCtx(),"TotalCR")).append("=").append(getTotalCr())
		.append(" (#").append(getJournals(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	@Override
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getDateDoc());
	}

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MJournalBatch[");
		sb.append(get_ID()).append(",").append(getDescription())
			.append(",DR=").append(getTotalDr())
			.append(",CR=").append(getTotalCr())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	@Override
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.error("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF


	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg

	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID (Created By)
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getCreatedBy();
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return DR amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getTotalDr();
	}	//	getApprovalAmt

}	//	MJournalBatch
