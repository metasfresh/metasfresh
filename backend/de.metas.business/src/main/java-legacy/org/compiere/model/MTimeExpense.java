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

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Msg;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 	Time + Expense Model
 *
 *	@author Jorg Janke
 *
 *  @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 			<li> FR [ 2520591 ] Support multiples calendar for Org
 *			@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *	@version $Id: MTimeExpense.java,v 1.4 2006/07/30 00:51:03 jjanke Exp $
 */
public class MTimeExpense extends X_S_TimeExpense implements IDocument
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1567303438502090279L;


	/**
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param S_TimeExpense_ID id
	 *	@param trxName transaction
	 */
	public MTimeExpense (Properties ctx, int S_TimeExpense_ID, String trxName)
	{
		super (ctx, S_TimeExpense_ID, trxName);
		if (S_TimeExpense_ID == 0)
		{
		//	setC_BPartner_ID (0);
			setDateReport (new Timestamp (System.currentTimeMillis ()));
		//	setDocumentNo (null);
			setIsApproved (false);
		//	setM_PriceList_ID (0);
		//	setM_Warehouse_ID (0);
			super.setProcessed (false);
			setProcessing(false);
		}
	}	//	MTimeExpense

	/**
	 * 	Load Constructor
	 * 	@param ctx context
	 * 	@param rs result set
	 *	@param trxName transaction
	 */
	public MTimeExpense (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MTimeExpense

	/** Default Locator				*/
	private int					m_M_Locator_ID = 0;
	/**	Lines						*/
	private MTimeExpenseLine[]	m_lines = null;
	/** Cached User					*/
	private int					m_AD_User_ID = 0;


	/**
	 * 	Get Lines Convenience Wrapper
	 *	@return array of lines
	 */
	public MTimeExpenseLine[] getLines ()
	{
		return getLines(true);
	}

	/**
	 * 	Get Lines
	 * 	@param requery true requeries
	 *	@return array of lines
	 */
	public MTimeExpenseLine[] getLines (boolean requery)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		int C_Currency_ID = getC_Currency_ID();
		ArrayList<MTimeExpenseLine> list = new ArrayList<>();
		//
		String sql = "SELECT * FROM S_TimeExpenseLine WHERE S_TimeExpense_ID=? ORDER BY Line";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getS_TimeExpense_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MTimeExpenseLine te = new MTimeExpenseLine(getCtx(), rs, get_TrxName());
				te.setC_Currency_Report_ID(C_Currency_ID);
				list.add(te);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error("getLines", ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//
		m_lines = new MTimeExpenseLine[list.size()];
		list.toArray(m_lines);
		return m_lines;
	}	//	getLines

	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	/**
	 *	Get Default Locator (from Warehouse)
	 *	@return locator
	 */
	public int getM_Locator_ID()
	{
		if (m_M_Locator_ID != 0)
			return m_M_Locator_ID;
		//
		String sql = "SELECT M_Locator_ID FROM M_Locator "
			+ "WHERE M_Warehouse_ID=? AND IsActive='Y' ORDER BY IsDefault DESC, Created";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getM_Warehouse_ID());
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				m_M_Locator_ID = rs.getInt(1);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error("getM_Locator_ID", ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//
		return m_M_Locator_ID;
	}	//	getM_Locator_ID

	/**
	 * 	Set Processed.
	 * 	Propergate to Lines/Taxes
	 *	@param processed processed
	 */
	@Override
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		String sql = "UPDATE S_TimeExpenseLine SET Processed='"
			+ (processed ? "Y" : "N")
			+ "' WHERE S_TimeExpense_ID=" + getS_TimeExpense_ID();
		int noLine = DB.executeUpdate(sql, get_TrxName());
		m_lines = null;
		log.debug(processed + " - Lines=" + noLine);
	}	//	setProcessed

	/**
	 * 	Get Document Info
	 *	@return document info
	 */
	@Override
	public String getDocumentInfo()
	{
		return Msg.getElement(getCtx(), "S_TimeExpense_ID") + " " + getDocumentNo();
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

	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	@Override
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, processAction); // task 09824
	}	//	processIt

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
		setDocAction(DOCACTION_Prepare);
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

		//	Std Period open? - AP (Reimbursement) Invoice
		if (!MPeriod.isOpen(getCtx(), getDateReport(), MDocType.DOCBASETYPE_APInvoice, getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return IDocument.STATUS_Invalid;
		}

		MTimeExpenseLine[] lines = getLines(false);
		if (lines.length == 0)
		{
			throw AdempiereException.noLines();
		}
		//	Add up Amounts
		BigDecimal amt = Env.ZERO;
		for (MTimeExpenseLine line : lines)
		{
			amt = amt.add(line.getApprovalAmt());
		}
		setApprovalAmt(amt);

		//	Invoiced but no BP
		for (MTimeExpenseLine line : lines)
		{
			if (line.isInvoiced() && line.getC_BPartner_ID() == 0)
			{
				m_processMsg = "@Line@ " + line.getLine() + ": Invoiced, but no Business Partner";
				return IDocument.STATUS_Invalid;
			}
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return IDocument.STATUS_Invalid;

		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
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
		if (!isApproved())
			approveIt();
		log.debug("Completed: {}", this);

		//	User Validation
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
	}	//	completeIt

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success
	 */
	@Override
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (!closeIt())
			return false;

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		return true;
	}	//	voidIt

	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
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
		// After Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;

		//	Close Not delivered Qty
	//	setDocAction(DOCACTION_None);
		return true;
	}	//	closeIt

	/**
	 * 	Reverse Correction
	 * 	@return false
	 */
	@Override
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		// Before reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		// After reverseCorrect
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSECORRECT);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseCorrectionIt

	/**
	 * 	Reverse Accrual - none
	 * 	@return false
	 */
	@Override
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		// Before reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		// After reverseAccrual
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		if (m_processMsg != null)
			return false;

		return false;
	}	//	reverseAccrualIt

	/**
	 * 	Re-activate
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

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

	//	setProcessed(false);
		return false;
	}	//	reActivateIt


	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	@Override
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
		//	: Total Lines = 123.00 (#1)
		sb.append(": ")
			.append(Msg.translate(getCtx(),"ApprovalAmt")).append("=").append(getApprovalAmt())
			.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	@Override
	public LocalDate getDocumentDate()
	{
		return TimeUtil.asLocalDate(getDateReport());
	}

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
	 *	@return AD_User_ID
	 */
	@Override
	public int getDoc_User_ID()
	{
		if (m_AD_User_ID != 0)
			return m_AD_User_ID;
		if (getC_BPartner_ID() != 0)
		{
			final List<I_AD_User> users = Services.get(IBPartnerDAO.class).retrieveContacts(getCtx(), getC_BPartner_ID(), ITrx.TRXNAME_None);
			if (!users.isEmpty())
			{
				m_AD_User_ID = users.get(0).getAD_User_ID();
				return m_AD_User_ID;
			}
		}
		return getCreatedBy();
	}	//	getDoc_User_ID


	/**
	 * 	Get Document Currency
	 *	@return C_Currency_ID
	 */
	@Override
	public int getC_Currency_ID()
	{
		final I_M_PriceList pl = Services.get(IPriceListDAO.class).getById(getM_PriceList_ID());
		return pl.getC_Currency_ID();
	}	//	getC_Currency_ID

	/**
	 * 	Document Status is Complete or Closed
	 *	@return true if CO, CL or RE
	 */
	public boolean isComplete()
	{
		String ds = getDocStatus();
		return DOCSTATUS_Completed.equals(ds)
			|| DOCSTATUS_Closed.equals(ds)
			|| DOCSTATUS_Reversed.equals(ds);
	}	//	isComplete

}	//	MTimeExpense
