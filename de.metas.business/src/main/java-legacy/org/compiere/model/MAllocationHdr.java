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

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.acct.api.IFactAcctDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;

/**
 * Payment Allocation Model.
 * Allocation Trigger update C_BPartner
 *
 * @author Jorg Janke
 * @version $Id: MAllocationHdr.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>FR [ 1866214 ]
 *         <li>http://sourceforge.net/tracker/index.php?func=detail&aid=1866214&group_id=176962&atid=879335
 *         <li>
 *         FR [ 2520591 ] Support multiples calendar for Org
 *         <li>http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *         <li>BF [ 2880182 ] Error you can allocate a
 *         payment to invoice that was paid
 *         <li>https://sourceforge.net/tracker/index.php?func=detail&aid=2880182&group_id=176962&atid=879332
 */
public final class MAllocationHdr extends X_C_AllocationHdr implements DocAction
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8726957992840702609L;

	/**
	 * Get Allocations of Payment
	 *
	 * @param ctx context
	 * @param C_Payment_ID payment
	 * @return allocations of payment
	 * @param trxName transaction
	 */
	public static MAllocationHdr[] getOfPayment(final Properties ctx, final int C_Payment_ID, final String trxName)
	{
		final String sql = "SELECT * FROM C_AllocationHdr h "
				+ "WHERE IsActive='Y'"
				+ " AND EXISTS (SELECT * FROM C_AllocationLine l "
				+ "WHERE h.C_AllocationHdr_ID=l.C_AllocationHdr_ID AND l.C_Payment_ID=?)";
		final ArrayList<MAllocationHdr> list = new ArrayList<MAllocationHdr>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, C_Payment_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MAllocationHdr(ctx, rs, trxName));
			}
		}
		catch (final Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		final MAllocationHdr[] retValue = new MAllocationHdr[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getOfPayment

	/**
	 * Get Allocations of Invoice
	 *
	 * @param ctx context
	 * @param C_Invoice_ID payment
	 * @return allocations of payment
	 * @param trxName transaction
	 */
	public static MAllocationHdr[] getOfInvoice(final Properties ctx, final int C_Invoice_ID, final String trxName)
	{
		final String sql = "SELECT * FROM C_AllocationHdr h "
				+ "WHERE IsActive='Y'"
				+ " AND EXISTS (SELECT * FROM C_AllocationLine l "
				+ "WHERE h.C_AllocationHdr_ID=l.C_AllocationHdr_ID AND l.C_Invoice_ID=?)";
		final ArrayList<MAllocationHdr> list = new ArrayList<MAllocationHdr>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, C_Invoice_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MAllocationHdr(ctx, rs, trxName));
			}
		}
		catch (final Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		final MAllocationHdr[] retValue = new MAllocationHdr[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getOfInvoice

	// FR [ 1866214 ]
	/**
	 * Get Allocations of Cash
	 *
	 * @param ctx context
	 * @param C_Cash_ID Cash ID
	 * @return allocations of payment
	 * @param trxName transaction
	 */
	public static MAllocationHdr[] getOfCash(final Properties ctx, final int C_Cash_ID, final String trxName)
	{
		final String whereClause = "IsActive='Y'"
				+ " AND EXISTS (SELECT 1 FROM C_CashLine cl, C_AllocationLine al "
				+ "where cl.C_Cash_ID=? and al.C_CashLine_ID=cl.C_CashLine_ID "
				+ "and C_AllocationHdr.C_AllocationHdr_ID=al.C_AllocationHdr_ID)";
		final Query query = MTable.get(ctx, InterfaceWrapperHelper.getTableId(I_C_AllocationHdr.class))
				.createQuery(whereClause, trxName);
		query.setParameters(new Object[] { C_Cash_ID });
		final List<MAllocationHdr> list = query.list();
		final MAllocationHdr[] retValue = new MAllocationHdr[list.size()];
		list.toArray(retValue);
		return retValue;
	}	// getOfCash

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MAllocationHdr.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param C_AllocationHdr_ID id
	 * @param trxName transaction
	 */
	public MAllocationHdr(final Properties ctx, final int C_AllocationHdr_ID, final String trxName)
	{
		super(ctx, C_AllocationHdr_ID, trxName);
		if (C_AllocationHdr_ID == 0)
		{
			// setDocumentNo (null);
			setDateTrx(new Timestamp(System.currentTimeMillis()));
			setDateAcct(getDateTrx());
			setDocAction(DOCACTION_Complete);	// CO
			setDocStatus(DOCSTATUS_Drafted);	// DR
			// setC_Currency_ID (0);
			setApprovalAmt(Env.ZERO);
			setIsApproved(false);
			setIsManual(false);
			//
			setPosted(false);
			setProcessed(false);
			setProcessing(false);
		}
	}	// MAllocation

	/**
	 * Mandatory New Constructor
	 *
	 * @param ctx context
	 * @param IsManual manual trx
	 * @param DateTrx date (if null today)
	 * @param C_Currency_ID currency
	 * @param description description
	 * @param trxName transaction
	 */
	public MAllocationHdr(final Properties ctx, final boolean IsManual, final Timestamp DateTrx,
			final int C_Currency_ID, final String description, final String trxName)
	{
		this(ctx, 0, trxName);
		setIsManual(IsManual);
		if (DateTrx != null)
		{
			setDateTrx(DateTrx);
			setDateAcct(DateTrx);
		}
		setC_Currency_ID(C_Currency_ID);
		if (description != null)
		{
			setDescription(description);
		}
	}	// create Allocation

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MAllocationHdr(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MAllocation

	/** Lines */
	// private MAllocationLine[] m_lines = null;

	/**
	 *
	 * @param IGNORED
	 * @return
	 * @deprecated please use {@link IAllocationDAO#retrieveAllLines(I_C_AllocationHdr)}.
	 */
	@Deprecated
	public MAllocationLine[] getLines(final boolean IGNORED)
	{
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

		return LegacyAdapters.convertToPOArray(
				allocationDAO.retrieveAllLines(this),
				MAllocationLine.class);
	}

	// /**
	// * Get Lines
	// * @param requery if true requery
	// * @return lines
	// */
	// public MAllocationLine[] getLines (boolean requery)
	// {
	// if (m_lines != null && m_lines.length != 0 && !requery) {
	// set_TrxName(m_lines, get_TrxName());
	// return m_lines;
	// }
	// //
	// String sql = "SELECT * FROM C_AllocationLine WHERE C_AllocationHdr_ID=?";
	// ArrayList<MAllocationLine> list = new ArrayList<MAllocationLine>();
	// PreparedStatement pstmt = null;
	// ResultSet rs = null;
	// try
	// {
	// pstmt = DB.prepareStatement (sql, get_TrxName());
	// pstmt.setInt (1, getC_AllocationHdr_ID());
	// rs = pstmt.executeQuery ();
	// while (rs.next ())
	// {
	// MAllocationLine line = new MAllocationLine(getCtx(), rs, get_TrxName());
	// line.setParent(this);
	// list.add (line);
	// }
	// }
	// catch (Exception e)
	// {
	// log.error(sql, e);
	// }
	// finally
	// {
	// DB.close(rs, pstmt);
	// rs = null; pstmt = null;
	// }
	// //
	// m_lines = new MAllocationLine[list.size ()];
	// list.toArray (m_lines);
	// return m_lines;
	// } // getLines

	/**
	 * Set Processed
	 *
	 * @param processed Processed
	 */
	@Override
	public void setProcessed(final boolean processed)
	{
		super.setProcessed(processed);
		if (get_ID() == 0)
		{
			return;
		}
		final String sql = "UPDATE C_AllocationHdr SET Processed='"
				+ (processed ? "Y" : "N")
				+ "' WHERE C_AllocationHdr_ID=" + getC_AllocationHdr_ID();
		final int no = DB.executeUpdate(sql, get_TrxName());

		log.debug(processed + " - #" + no);
	}	// setProcessed

	/**************************************************************************
	 * Before Save
	 *
	 * @param newRecord
	 * @return save
	 */
	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// Changed from Not to Active
		if (!newRecord && is_ValueChanged("IsActive") && isActive())
		{
			log.error("Cannot Re-Activate deactivated Allocations");
			return false;
		}
		return true;
	}	// beforeSave

	/**
	 * Before Delete.
	 *
	 * @return true if acct was deleted
	 */
	@Override
	protected boolean beforeDelete()
	{
		final String trxName = get_TrxName();
		if (trxName == null || trxName.length() == 0)
		{
			log.warn("No transaction");
		}
		if (isPosted())
		{
			MPeriod.testPeriodOpen(getCtx(), getDateTrx(), X_C_DocType.DOCBASETYPE_PaymentAllocation, getAD_Org_ID());
			setPosted(false);
			Services.get(IFactAcctDAO.class).deleteForDocument(this);
		}

		// Unlink
		final HashSet<Integer> bps = new HashSet<Integer>();
		final MAllocationLine[] lines = getLines(true);
		for (int i = 0; i < lines.length; i++)
		{
			final MAllocationLine line = lines[i];
			bps.add(line.getC_BPartner_ID());
			line.deleteEx(true, trxName);
		}
		updateBP(bps);
		return true;
	}	// beforeDelete

	/**************************************************************************
	 * Process document
	 *
	 * @param processAction document action
	 * @return true if performed
	 */
	@Override
	public boolean processIt(final String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocActionBL.class).processIt(this, processAction); // task 09824
	}	// processIt

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
		log.info("{}", this);
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		// Std Period open?
		MPeriod.testPeriodOpen(getCtx(), getDateAcct(), X_C_DocType.DOCBASETYPE_PaymentAllocation, getAD_Org_ID());
		MAllocationLine[] lines = getLines(false);
		if (lines.length == 0)
		{
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		// Stop the Document Workflow if invoice to allocate is as paid
// @formatter:off
// 04627: metas.ts: commenting out, because:
// we want to allocate, even if the invoice is paid (in that case with amout=0), because a customer might
// inadvertedly overpay an invoice, but still this shall also be documented in the allocation
//		if (this.getReversal_ID() <= 0) { // metas: tsa: 02181: check only if is not a reversal
//		for (MAllocationLine line :m_lines)
//		{
//			if (line.getC_Invoice_ID() != 0)
//			{
//				final String whereClause = I_C_Invoice.COLUMNNAME_C_Invoice_ID + "=? AND "
//								   + I_C_Invoice.COLUMNNAME_IsPaid + "=? AND "
//								   + I_C_Invoice.COLUMNNAME_DocStatus + " NOT IN (?,?)";
//				boolean InvoiceIsPaid = new Query(getCtx(), I_C_Invoice.Table_Name, whereClause, get_TrxName())
//				.setClient_ID()
//				.setParameters(new Object[]{line.getC_Invoice_ID(), "Y", MInvoice.DOCSTATUS_Voided, MInvoice.DOCSTATUS_Reversed})
//				.match();
//				if(InvoiceIsPaid)
//					throw new  AdempiereException("@ValidationError@ @C_Invoice_ID@ @IsPaid@");
//			}
//		}
//		}  // metas: tsa: 02181: check only if is not a reversal
// @formatter:on
		// Add up Amounts & validate
		lines = getLines(true);
		BigDecimal approval = Env.ZERO;
		for (int i = 0; i < lines.length; i++)
		{
			final MAllocationLine line = lines[i];
			approval = approval.add(line.getWriteOffAmt()).add(line.getDiscountAmt());
			// Make sure there is BP
			if (line.getC_BPartner_ID() <= 0)
			{
				m_processMsg = "No Business Partner";
				return DocAction.STATUS_Invalid;
			}
		}
		setApprovalAmt(approval);
		//
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
		{
			setDocAction(DOCACTION_Complete);
		}

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
			final String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
		{
			return DocAction.STATUS_Invalid;
		}

		// Implicit Approval
		if (!isApproved())
		{
			approveIt();
		}
		log.debug("Completed: {}", this);

		// Link
		final MAllocationLine[] lines = getLines(false);
		final Set<Integer> bpartnerIds = new HashSet<Integer>();
		for (int i = 0; i < lines.length; i++)
		{
			final MAllocationLine line = lines[i];
			final int bpartnerId = line.processIt(false); // not reverse
			bpartnerIds.add(bpartnerId);
		}

		// Update BP Statistics

		Services.get(IBPartnerStatisticsUpdater.class)
				.updateBPartnerStatistics(Env.getCtx(), bpartnerIds, ITrx.TRXNAME_None);

		// User Validation
		final String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}

		setProcessed(true);
		setDocAction(DOCACTION_Reverse_Correct); // issue #347
		return DocAction.STATUS_Completed;
	}	// completeIt

	/**
	 * Void Document.
	 * Same as Close.
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
		{
			return false;
		}

		// metas: tsa: begin: 2181
		// boolean retValue = reverseIt();
		// metas: tsa: changed
		if (DOCSTATUS_Closed.equals(getDocStatus())
				|| DOCSTATUS_Reversed.equals(getDocStatus())
				|| DOCSTATUS_Voided.equals(getDocStatus()))
		{
			m_processMsg = "Document Closed: " + getDocStatus();
			setDocAction(DOCACTION_None);
			return false;
		}
		// Not Processed
		else if (DOCSTATUS_Drafted.equals(getDocStatus())
				|| DOCSTATUS_Invalid.equals(getDocStatus())
				|| DOCSTATUS_InProgress.equals(getDocStatus())
				|| DOCSTATUS_Approved.equals(getDocStatus())
				|| DOCSTATUS_NotApproved.equals(getDocStatus()))
		{
			voidIt0();
		}
		else
		{
			reverseCorrectIt0();
		}

		// metas: tsa: end: 2181

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(DOCACTION_None);

		return true;
	}	// voidIt

	/**
	 * Close Document.
	 * Cancel not delivered Qunatities
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

	/**
	 * Reverse Correction
	 *
	 * @return true if success
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info(toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		// boolean retValue = reverseIt(); // metas: tsa: 2181: original
		final boolean retValue = true;
		reverseCorrectIt0(); // metas: tsa: 2181: changed

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(DOCACTION_None);
		return retValue;
	}	// reverseCorrectionIt

	/**
	 * Reverse Accrual - none
	 *
	 * @return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info(toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		// boolean retValue = reverseIt(); // metas: tsa: 02181: original
		final boolean retValue = false; // metas: tsa: 02181: changed: because actually method is not supported

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
		{
			return false;
		}

		setDocAction(DOCACTION_None);
		return retValue;
	}	// reverseAccrualIt

	/**
	 * Re-activate
	 *
	 * @return false
	 */
	@Override
	public boolean reActivateIt()
	{
		log.info(toString());
		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
		{
			return false;
		}

		return false;
	}	// reActivateIt

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MAllocationHdr[");
		sb.append(get_ID()).append("-").append(getSummary()).append("]");
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
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		return msgBL.translate(getCtx(), "C_AllocationHdr_ID") + " " + getDocumentNo();
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
			final File temp = File.createTempFile(get_TableName() + get_ID() + "_", ".pdf");
			return createPDF(temp);
		}
		catch (final Exception e)
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
				.append(msgBL.translate(getCtx(), "ApprovalAmt")).append("=").append(getApprovalAmt())
				.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (getDescription() != null && getDescription().length() > 0)
		{
			sb.append(" - ").append(getDescription());
		}
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
		return getCreatedBy();
	}	// getDoc_User_ID

	/**************************************************************************
	 * Reverse Allocation.
	 * Period needs to be open
	 *
	 * @return true if reversed
	 */
	// metas: tsa: 2181: changed method name to reflect what is doing
	private boolean voidIt0()
	{
		if (!isActive())
		{
			throw new IllegalStateException("Allocation already reversed (not active)");
		}

		// Can we delete posting
		MPeriod.testPeriodOpen(getCtx(), getDateTrx(), X_C_DocType.DOCBASETYPE_PaymentAllocation, getAD_Org_ID());

		// Set Inactive
		setIsActive(false);
		setDocumentNo(getDocumentNo() + "^");
		setDocStatus(DOCSTATUS_Reversed);	// for direct calls
		if (!save() || isActive())
		{
			throw new IllegalStateException("Cannot de-activate allocation");
		}

		// Delete Posting
		Services.get(IFactAcctDAO.class).deleteForDocument(this);

		// Unlink Invoices
		final MAllocationLine[] lines = getLines(true);
		final Set<Integer> bpartnerIds = new HashSet<Integer>();
		for (int i = 0; i < lines.length; i++)
		{
			final MAllocationLine line = lines[i];
			line.setIsActive(false);
			line.save();
			final int bpartnerId = line.processIt(true); // reverse
			bpartnerIds.add(bpartnerId);
		}
		updateBP(bpartnerIds);
		return true;
	}	// reverse

	/**
	 * Update Open Balance of BP's
	 *
	 * @param bpartnerIds list of business partners
	 */
	private void updateBP(final Set<Integer> bpartnerIds)
	{
		final Boolean disabled = IBPartnerStatisticsUpdater.DYNATTR_DisableUpdateTotalOpenBalances.getValue(this);
		if (disabled != null && disabled)
		{
			return;
		}

		// FRESH-152 update bpartner stats
		final IBPartnerStatisticsUpdater bpartnerTotalOpenBalanceUpdater = Services.get(IBPartnerStatisticsUpdater.class);

		bpartnerTotalOpenBalanceUpdater.updateBPartnerStatistics(getCtx(), bpartnerIds, get_TrxName());

	}	// updateBP

	/**
	 * Document Status is Complete or Closed
	 *
	 * @return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		final String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
				|| DOCSTATUS_Closed.equals(ds)
				|| DOCSTATUS_Reversed.equals(ds);
	}	// isComplete

	// metas: begin
	/**
	 * Reverse current allocation (another allocation is produced)
	 * NOTE: this method is not saving current object's modifications
	 *
	 * @see http://dewiki908/mediawiki/index.php/02181:_Verbuchungsfehler_bei_Zuordnungen_%282011092910000015%29
	 */
	private void reverseCorrectIt0()
	{
		final MAllocationHdr reversal = new MAllocationHdr(getCtx(), 0, get_TrxName());

		final boolean copyHonorIsCalculated = true;
		copyValues(this, reversal, copyHonorIsCalculated);

		//
		// 07570: Keep AD_Org of original document
		reversal.setAD_Org_ID(getAD_Org_ID());

		reversal.setDocumentNo(""); // let it generate a new document#
		reversal.setProcessing(false);
		reversal.setProcessed(false);
		reversal.setDocStatus(DOCSTATUS_Drafted);
		reversal.setDocAction(DOCACTION_Complete);
		reversal.setPosted(false);
		reversal.setReversal_ID(getC_AllocationHdr_ID());
		InterfaceWrapperHelper.save(reversal);

		setReversal_ID(reversal.getC_AllocationHdr_ID());
		InterfaceWrapperHelper.save(this);

		// task 09610: needed to set the correct counter-IDs if any
		final Map<Integer, Integer> lineId2reversalLineId = new HashMap<Integer, Integer>();
		final Map<Integer, I_C_AllocationLine> reversalLineId2reversalLine = new HashMap<Integer, I_C_AllocationLine>();

		for (final MAllocationLine line : getLines(true))
		{
			final MAllocationLine reversalLine = new MAllocationLine(reversal);
			PO.copyValues(line, reversalLine, copyHonorIsCalculated);

			//
			// 07570: Keep AD_Org of original document
			reversalLine.setAD_Org_ID(line.getAD_Org_ID());

			reversalLine.setC_AllocationHdr_ID(reversal.getC_AllocationHdr_ID());
			reversalLine.setAmount(reversalLine.getAmount().negate());
			reversalLine.setDiscountAmt(reversalLine.getDiscountAmt().negate());
			reversalLine.setOverUnderAmt(reversalLine.getOverUnderAmt().negate());
			reversalLine.setWriteOffAmt(reversalLine.getWriteOffAmt().negate());
			reversalLine.setReversalLine_ID(line.getC_AllocationLine_ID());
			InterfaceWrapperHelper.save(reversalLine);

			line.setReversalLine_ID(reversalLine.getC_AllocationLine_ID());
			InterfaceWrapperHelper.save(line);

			lineId2reversalLineId.put(line.getC_AllocationLine_ID(), reversalLine.getC_AllocationLine_ID());
			reversalLineId2reversalLine.put(reversalLine.getC_AllocationLine_ID(), reversalLine);
		}

		// task 09610: iterate again and set the reversal lines' counter-IDs according to the original line's counter-IDs
		for (final MAllocationLine line : getLines(false))
		{
			if (line.getCounter_AllocationLine_ID() <= 0)
			{
				continue; // nothing to do
			}

			final Integer reversalLineId = lineId2reversalLineId.get(line.getC_AllocationLine_ID());
			final I_C_AllocationLine reversalLine = reversalLineId2reversalLine.get(reversalLineId);

			final Integer reversalLineCounterLineId = lineId2reversalLineId.get(line.getCounter_AllocationLine_ID());

			reversalLine.setCounter_AllocationLine_ID(reversalLineCounterLineId);
			InterfaceWrapperHelper.save(reversalLine);
		}

		if (!reversal.processIt(DOCACTION_Complete))
		{
			throw new AdempiereException(reversal.getProcessMsg());
		}
		reversal.setDocStatus(DOCSTATUS_Reversed);
		reversal.setDocAction(DOCACTION_None);
		InterfaceWrapperHelper.save(reversal);

		setDocStatus(DOCSTATUS_Reversed);
		setDocAction(DOCACTION_None);
	}
	// metas: end
}   // MAllocation
