/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.banking.model.BankStatementId;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Bank Statement Model
 *
 * @author Eldir Tomassen/Jorg Janke
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>BF [ 1933645 ] Wrong balance Bank Statement
 * @see http://sourceforge.net/tracker/?func=detail&atid=879332&aid=1933645&group_id=176962
 *      <li>FR [ 2520591 ] Support multiples calendar for Org
 * @see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *      <li>BF [ 2824951 ] The payments is not release when Bank Statement is void
 * @see http://sourceforge.net/tracker/?func=detail&aid=2824951&group_id=176962&atid=879332
 * @author Teo Sarca, http://www.arhipac.ro
 *         <li>FR [ 2616330 ] Use MPeriod.testPeriodOpen instead of isOpen https://sourceforge.net/tracker/?func=detail&atid=879335&aid=2616330&group_id=176962
 *
 * @version $Id: MBankStatement.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
@SuppressWarnings("serial")
public class MBankStatement extends X_C_BankStatement implements IDocument
{
	private String m_processMsg = null;
	private boolean m_justPrepared = false;

	public MBankStatement(Properties ctx, int C_BankStatement_ID, String trxName)
	{
		super(ctx, C_BankStatement_ID, trxName);
		if (is_new())
		{
			// setC_BP_BankAccount_ID (0); // parent
			setStatementDate(new Timestamp(System.currentTimeMillis()));	// @Date@
			setDocAction(DOCACTION_Complete);	// CO
			setDocStatus(DOCSTATUS_Drafted);	// DR
			setBeginningBalance(BigDecimal.ZERO);
			setStatementDifference(BigDecimal.ZERO);
			setEndingBalance(BigDecimal.ZERO);
			setIsApproved(false);	// N
			setIsManual(true);	// Y
			setPosted(false);	// N
			super.setProcessed(false);
		}
	}

	public MBankStatement(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	private List<I_C_BankStatementLine> getLines()
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoIdOrNull(getC_BankStatement_ID());
		if (bankStatementId == null)
		{
			return ImmutableList.of();
		}

		final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
		return bankStatementDAO.retrieveLines(bankStatementId);
	}

	private void addDescription(String description)
	{
		String desc = getDescription();
		if (desc == null)
		{
			setDescription(description);
		}
		else
		{
			setDescription(desc + " | " + description);
		}
	}	// addDescription

	@Override
	public void setProcessed(boolean processed)
	{
		super.setProcessed(processed);

		updateBankStatementLinesProcessedFlag(processed);
	}

	private void updateBankStatementLinesProcessedFlag(boolean processed)
	{
		final BankStatementId bankStatementId = BankStatementId.ofRepoIdOrNull(getC_BankStatement_ID());
		if (bankStatementId == null)
		{
			return;
		}
		
		final String sql = "UPDATE C_BankStatementLine SET Processed=? WHERE C_BankStatement_ID=?";
		final int updateCount = DB.executeUpdateEx(
				sql,
				new Object[] { processed, bankStatementId },
				ITrx.TRXNAME_ThreadInherited);
		log.debug("setProcessed - {} - Lines={}", processed, updateCount);
	}

	@Override
	public String getDocumentInfo()
	{
		final StringBuilder documentInfo = new StringBuilder();

		final String bankAccountName = getC_BP_BankAccount().getA_Name();
		if (!Check.isBlank(bankAccountName))
		{
			documentInfo.append(bankAccountName.trim());
		}

		if (documentInfo.length() > 0)
		{
			documentInfo.append(" ");
		}
		documentInfo.append(getDocumentNo());

		return documentInfo.toString();
	}

	@Override
	public File createPDF()
	{
		return null;
	}

	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction);
	}

	@Override
	public boolean unlockIt()
	{
		log.debug("unlockIt - {}", this);
		setProcessing(false);
		return true;
	}	// unlockIt

	@Override
	public boolean invalidateIt()
	{
		log.debug("invalidateIt - {}", this);
		setDocAction(DOCACTION_Prepare);
		return true;
	}	// invalidateIt

	@Override
	public String prepareIt()
	{
		log.debug("Prepare: {}", this);
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		// Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getStatementDate(), MDocType.DOCBASETYPE_BankStatement, getAD_Org_ID());
		final List<I_C_BankStatementLine> lines = getLines();
		if (lines.isEmpty())
		{
			m_processMsg = "@NoLines@";
			return IDocument.STATUS_Invalid;
		}
		// Lines
		BigDecimal total = BigDecimal.ZERO;
		Timestamp minDate = getStatementDate();
		Timestamp maxDate = minDate;
		for (final I_C_BankStatementLine line : lines)
		{
			total = total.add(line.getStmtAmt());
			if (line.getDateAcct().before(minDate))
			{
				minDate = line.getDateAcct();
			}
			if (line.getDateAcct().after(maxDate))
			{
				maxDate = line.getDateAcct();
			}
		}
		setStatementDifference(total);
		setEndingBalance(getBeginningBalance().add(total));
		MPeriod.testPeriodOpen(getCtx(), minDate, MDocType.DOCBASETYPE_BankStatement, 0);
		MPeriod.testPeriodOpen(getCtx(), maxDate, MDocType.DOCBASETYPE_BankStatement, 0);

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return IDocument.STATUS_Invalid;
		}

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}
		return IDocument.STATUS_InProgress;
	}	// prepareIt

	@Override
	public boolean approveIt()
	{
		log.debug("approveIt - {}", this);
		setIsApproved(true);
		return true;
	}	// approveIt

	@Override
	public boolean rejectIt()
	{
		log.debug("rejectIt - {}", this);
		setIsApproved(false);
		return true;
	}	// rejectIt

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		m_processMsg = null;
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		// Set Payment reconciled
		for (I_C_BankStatementLine line : getLines())
		{
			completeLine(line);
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

	private void completeLine(I_C_BankStatementLine line)
	{
		//
		// Cash/Bank transfer
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

		//
		// Reconcile the payment
		if (line.getC_Payment_ID() > 0)
		{
			I_C_Payment payment = line.getC_Payment();
			payment.setIsReconciled(true);
			InterfaceWrapperHelper.save(payment);
		}
	}

	@Override
	public boolean voidIt()
	{
		log.debug("{}", this);

		// Before Void
		m_processMsg = null;
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);

		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			throw new AdempiereException("Document Closed");
		}

		// Not Processed
		if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()))
		{
			// Std Period open?
		}
		else
		{
			MPeriod.testPeriodOpen(getCtx(), getStatementDate(), MDocType.DOCBASETYPE_BankStatement, getAD_Org_ID());
			Services.get(IFactAcctDAO.class).deleteForDocument(this);
		}

		// Set lines to 0
		for (final I_C_BankStatementLine line : getLines())
		{
			voidDraftLine(line);
		}
		addDescription(Msg.getMsg(getCtx(), "Voided"));
		setStatementDifference(BigDecimal.ZERO);

		// After Void
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);

		setProcessed(true);
		setDocAction(DOCACTION_None);
		return true;
	}	// voidIt

	private void voidDraftLine(final I_C_BankStatementLine line)
	{
		if (line.getStmtAmt().signum() == 0)
		{
			return;
		}

		String description = Msg.getMsg(getCtx(), "Voided") + " ("
				+ Msg.translate(getCtx(), "StmtAmt") + "=" + line.getStmtAmt();
		if (line.getTrxAmt().compareTo(BigDecimal.ZERO) != 0)
		{
			description += ", " + Msg.translate(getCtx(), "TrxAmt") + "=" + line.getTrxAmt();
		}
		if (line.getChargeAmt().compareTo(BigDecimal.ZERO) != 0)
		{
			description += ", " + Msg.translate(getCtx(), "ChargeAmt") + "=" + line.getChargeAmt();
		}
		if (line.getInterestAmt().compareTo(BigDecimal.ZERO) != 0)
		{
			description += ", " + Msg.translate(getCtx(), "InterestAmt") + "=" + line.getInterestAmt();
		}
		description += ")";
		addDescription(line, description);
		//
		line.setStmtAmt(BigDecimal.ZERO);
		line.setTrxAmt(BigDecimal.ZERO);
		line.setChargeAmt(BigDecimal.ZERO);
		line.setInterestAmt(BigDecimal.ZERO);

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

		saveRecord(line);
	}

	private static void addDescription(final I_C_BankStatementLine line, final String descriptionToAppend)
	{
		final String description = line.getDescription();
		if (description == null)
		{
			line.setDescription(descriptionToAppend);
		}
		else
		{
			line.setDescription(description + " | " + descriptionToAppend);
		}
	}

	@Override
	public boolean closeIt()
	{
		log.debug("closeIt - {}", this);
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(DOCACTION_None);
		return true;
	}	// closeIt

	@Override
	public boolean reverseCorrectIt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean reverseAccrualIt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean reActivateIt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSummary()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getName());
		// : Total Lines = 123.00 (#1)
		sb.append(": ")
				.append(Msg.translate(getCtx(), "StatementDifference")).append("=").append(getStatementDifference())
				.append(" (#").append(getLines().size()).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
		return sb.toString();
	}

	@Override
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getStatementDate());
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID()
	{
		return getUpdatedBy();
	}

	/**
	 * @return Statement Difference
	 */
	@Override
	public BigDecimal getApprovalAmt()
	{
		return getStatementDifference();
	}

	@Override
	public int getC_Currency_ID()
	{
		return 0;
	}

	/**
	 * @return true if CO, CL or RE
	 */
	boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}	// isComplete

}	// MBankStatement
