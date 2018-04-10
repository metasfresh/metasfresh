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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;

/**
 * Bank Statement Model
 *
 * @author Eldir Tomassen/Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com <li>BF [ 1933645 ] Wrong balance Bank Statement
 * @see http://sourceforge.net/tracker/?func=detail&atid=879332&aid=1933645&group_id=176962 <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962 <li>BF [ 2824951 ] The payments is not release when Bank Statement is void
 * @see http://sourceforge.net/tracker/?func=detail&aid=2824951&group_id=176962&atid=879332
 * @author Teo Sarca, http://www.arhipac.ro <li>FR [ 2616330 ] Use MPeriod.testPeriodOpen instead of isOpen https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2616330&group_id=176962
 * 
 * @version $Id: MBankStatement.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBankStatement extends X_C_BankStatement implements IDocument
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -859925588789443186L;

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param C_BankStatement_ID id
	 * @param trxName transaction
	 */
	public MBankStatement(Properties ctx, int C_BankStatement_ID, String trxName)
	{
		super(ctx, C_BankStatement_ID, trxName);
		if (C_BankStatement_ID == 0)
		{
			// setC_BP_BankAccount_ID (0); // parent
			setStatementDate(new Timestamp(System.currentTimeMillis()));	// @Date@
			setDocAction(DOCACTION_Complete);	// CO
			setDocStatus(DOCSTATUS_Drafted);	// DR
			setBeginningBalance(Env.ZERO);
			setStatementDifference(Env.ZERO);
			setEndingBalance(Env.ZERO);
			setIsApproved(false);	// N
			setIsManual(true);	// Y
			setPosted(false);	// N
			super.setProcessed(false);
		}
	}	// MBankStatement

	/**
	 * Load Constructor
	 * 
	 * @param ctx Current context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MBankStatement(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MBankStatement

	/**
	 * Parent Constructor
	 *
	 * @param account Bank Account
	 * @param isManual Manual statement
	 **/
	public MBankStatement(I_C_BP_BankAccount account, boolean isManual)
	{
		this(InterfaceWrapperHelper.getCtx(account), 0, InterfaceWrapperHelper.getTrxName(account));
		setAD_Org_ID(account.getAD_Org_ID());
		setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		setStatementDate(new Timestamp(System.currentTimeMillis()));

		setName(getStatementDate().toString());
		setIsManual(isManual);
	}	// MBankStatement

	/**
	 * Create a new Bank Statement
	 *
	 * @param account Bank Account
	 */
	public MBankStatement(final I_C_BP_BankAccount account)
	{
		this(account, false);
	}	// MBankStatement

	/** Lines */
	private MBankStatementLine[] m_lines = null;

	/**
	 * Get Bank Statement Lines
	 * 
	 * @param requery requery
	 * @return line array
	 */
	public MBankStatementLine[] getLines(boolean requery)
	{
		if (m_lines != null && !requery)
		{
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}

		// metas: replaced with Query API
		List<MBankStatementLine> list = new Query(getCtx(), I_C_BankStatementLine.Table_Name, I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID + "=?", get_TrxName())
				.setParameters(this.getC_BankStatement_ID())
				.setOrderBy(I_C_BankStatementLine.COLUMNNAME_Line)
				.list(MBankStatementLine.class);

		MBankStatementLine[] retValue = new MBankStatementLine[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getLines

	/**
	 * Add to Description
	 *
	 * @param description text
	 */
	public void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	// addDescription

	/**
	 * Set Processed. Propergate to Lines/Taxes
	 *
	 * @param processed processed
	 */
	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
			return;
		String sql = "UPDATE C_BankStatementLine SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE C_BankStatement_ID=" + getC_BankStatement_ID();
		int noLine = DB.executeUpdate(sql, get_TrxName());
		m_lines = null;
		log.debug("setProcessed - {} - Lines={}", processed, noLine);
	}	// setProcessed

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	@Override
	public String getDocumentInfo()
	{
		return getC_BP_BankAccount().getA_Name() + " " + getDocumentNo();
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
			log.error("Could not create PDF", e);
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
		log.debug("unlockIt - {}", this);
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
		log.debug("invalidateIt - {}", this);
		setDocAction(DOCACTION_Prepare);
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
		log.debug("Prepare: {}", this);
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		// Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getStatementDate(), MDocType.DOCBASETYPE_BankStatement, getAD_Org_ID());
		MBankStatementLine[] lines = getLines(true);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return IDocument.STATUS_Invalid;
		}
		// Lines
		BigDecimal total = Env.ZERO;
		Timestamp minDate = getStatementDate();
		Timestamp maxDate = minDate;
		for (int i = 0; i < lines.length; i++)
		{
			MBankStatementLine line = lines[i];
			total = total.add(line.getStmtAmt());
			if (line.getDateAcct().before(minDate))
				minDate = line.getDateAcct();
			if (line.getDateAcct().after(maxDate))
				maxDate = line.getDateAcct();
		}
		setStatementDifference(total);
		setEndingBalance(getBeginningBalance().add(total));
		MPeriod.testPeriodOpen(getCtx(), minDate, MDocType.DOCBASETYPE_BankStatement, 0);
		MPeriod.testPeriodOpen(getCtx(), maxDate, MDocType.DOCBASETYPE_BankStatement, 0);

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return IDocument.STATUS_InProgress;
	}	// prepareIt

	/**
	 * Approve Document
	 * 
	 * @return true if success
	 */
	@Override
	public boolean approveIt()
	{
		log.debug("approveIt - {}", this);
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
		log.debug("rejectIt - {}", this);
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

		// Set Payment reconciled
		MBankStatementLine[] lines = getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MBankStatementLine line = lines[i];
			//
			// metas: tsa: cash/bank transfer
			if (line.getC_BP_BankAccountTo_ID() > 0)
			{
				if (line.getLink_BankStatementLine_ID() > 0)
				{
					final I_C_BankStatementLine lineFrom = line.getLink_BankStatementLine();
					if (lineFrom.getLink_BankStatementLine_ID() > 0
							&& lineFrom.getLink_BankStatementLine_ID() != line.getC_BankStatementLine_ID())
					{
						throw new AdempiereException("Bank Statement Line is allocated to another line"); // TODO: translate
					}

					final boolean sameCurrency = lineFrom.getC_Currency_ID() == line.getC_Currency_ID();
					if (sameCurrency && lineFrom.getTrxAmt().negate().compareTo(line.getTrxAmt()) != 0)
					{
						throw new AdempiereException("Transfer amount differs"); // TODO: translate
					}

					lineFrom.setC_BP_BankAccountTo_ID(this.getC_BP_BankAccount_ID());
					lineFrom.setLink_BankStatementLine_ID(line.getC_BankStatementLine_ID());
					InterfaceWrapperHelper.save(lineFrom);
				}
			}
			// metas: end
			
			//
			// Reconcile the payment
			if (line.getC_Payment_ID() > 0)
			{
				I_C_Payment payment = line.getC_Payment();
				payment.setIsReconciled(true);
				InterfaceWrapperHelper.save(payment);
			}
		}

		// User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return IDocument.STATUS_Invalid;
		}
		//
		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return IDocument.STATUS_Completed;
	}	// completeIt

	/**
	 * Void Document.
	 * 
	 * @return false
	 */
	@Override
	public boolean voidIt()
	{
		log.debug("{}", this);
		
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			setDocAction(DOCACTION_None);
			return false;
		}

		// Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Genehmigt.equals(getDocStatus())
				|| DOCSTATUS_NichtGenehmigt.equals(getDocStatus()))
			;
		// Std Period open?
		else
		{
			MPeriod.testPeriodOpen(getCtx(), getStatementDate(), MDocType.DOCBASETYPE_BankStatement, getAD_Org_ID());
			Services.get(IFactAcctDAO.class).deleteForDocument(this);
		}

		// Set lines to 0
		final MBankStatementLine[] lines = getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			final MBankStatementLine line = lines[i];
			if (line.getStmtAmt().compareTo(Env.ZERO) != 0)
			{
				String description = Msg.getMsg(getCtx(), "Voided") + " ("
						+ Msg.translate(getCtx(), "StmtAmt") + "=" + line.getStmtAmt();
				if (line.getTrxAmt().compareTo(Env.ZERO) != 0)
					description += ", " + Msg.translate(getCtx(), "TrxAmt") + "=" + line.getTrxAmt();
				if (line.getChargeAmt().compareTo(Env.ZERO) != 0)
					description += ", " + Msg.translate(getCtx(), "ChargeAmt") + "=" + line.getChargeAmt();
				if (line.getInterestAmt().compareTo(Env.ZERO) != 0)
					description += ", " + Msg.translate(getCtx(), "InterestAmt") + "=" + line.getInterestAmt();
				description += ")";
				line.addDescription(description);
				//
				line.setStmtAmt(Env.ZERO);
				line.setTrxAmt(Env.ZERO);
				line.setChargeAmt(Env.ZERO);
				line.setInterestAmt(Env.ZERO);
				
				//
				// metas: tsa: cash/bank transfer
				if (line.getLink_BankStatementLine_ID() > 0)
				{
					final I_C_BankStatementLine lineFrom = line.getLink_BankStatementLine();
					if (lineFrom.getLink_BankStatementLine_ID() == line.getC_BankStatementLine_ID())
					{
						lineFrom.setLink_BankStatementLine(null);
						InterfaceWrapperHelper.save(lineFrom);
					}
				}
				// metas: tsa: end
				
				//
				// Unlink payment
				if (line.getC_Payment_ID() > 0)
				{
					final I_C_Payment payment = line.getC_Payment();
					payment.setIsReconciled(false);
					InterfaceWrapperHelper.save(payment);
					line.setC_Payment(null);
				}
				
				line.saveEx();
			}
		}
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setStatementDifference(Env.ZERO);

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

	/**
	 * Close Document.
	 * 
	 * @return true if success
	 */
	@Override
	public boolean closeIt()
	{
		log.debug("closeIt - {}", this);
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

	/**
	 * Reverse Correction.
	 * 
	 * @return false because it's not supported.
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.debug("reverseCorrectIt - {}", this);
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	// reverseCorrectionIt

	/**
	 * Reverse Accrual
	 * 
	 * @return false because it's not supported
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.debug("reverseAccrualIt - {}", this);
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	// reverseAccrualIt

	/**
	 * Re-activate
	 * 
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		log.debug("reActivateIt - {}", this);
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		return false;
	}	// reActivateIt

	/*************************************************************************
	 * Get Summary
	 *
	 * @return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		// : Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(Msg.translate(getCtx(), "StatementDifference")).append("=").append(getStatementDifference())
				.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	// getSummary

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
	 * @return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	}	// getDoc_User_ID

	/**
	 * Get Document Approval Amount. Statement Difference
	 *
	 * @return amount
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getStatementDifference();
	}	// getApprovalAmt

	/**
	 * Get Document Currency
	 *
	 * @return C_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
		// MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());
		// return pl.getC_Currency_ID();
		return 0;
	}	// getC_Currency_ID

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}	// isComplete

}	// MBankStatement
