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
package org.compiere.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;

import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;

/**
 *	Document Action Interface
 *
 *  @author Jorg Janke
 *  @version $Id: DocAction.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public interface DocAction
{
	/** Complete = CO */
	public static final String ACTION_Complete = "CO";
	/** Wait Complete = WC */
	public static final String ACTION_WaitComplete = "WC";
	/** Approve = AP */
	public static final String ACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String ACTION_Reject = "RJ";
	/** Post = PO */
	public static final String ACTION_Post = "PO";
	/** UnPost = UP */ // NOTE: this action is not in database
	public static final String ACTION_UnPost = "UP";
	/** Void = VO */
	public static final String ACTION_Void = "VO";
	/** Close = CL */
	public static final String ACTION_Close = "CL";
	/** UnClose = UC */
	public static final String ACTION_UnClose = "UC";
	/** Reverse - Correct = RC */
	public static final String ACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String ACTION_Reverse_Accrual = "RA";
	/** ReActivate = RE */
	public static final String ACTION_ReActivate = "RE";
	/** <None> = -- */
	public static final String ACTION_None = "--";
	/** Prepare = PR */
	public static final String ACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String ACTION_Unlock = "XL";
	/** Invalidate = IN */
	public static final String ACTION_Invalidate = "IN";
	/** ReOpen = OP */
	public static final String ACTION_ReOpen = "OP";

	/** Drafted = DR */
	public static final String STATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String STATUS_Completed = "CO";
	/** Approved = AP */
	public static final String STATUS_Approved = "AP";
	/** Invalid = IN */
	public static final String STATUS_Invalid = "IN";
	/** Not Approved = NA */
	public static final String STATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String STATUS_Voided = "VO";
	/** Reversed = RE */
	public static final String STATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String STATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String STATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String STATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String STATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String STATUS_WaitingConfirmation = "WC";

	/** DocAction Ref_List values **/
	public static final int AD_REFERENCE_ID = 135;

	/**
	 * 	Set Doc Status
	 *	@param newStatus new Status
	 */
	public void setDocStatus (String newStatus);

	/**
	 * 	Get Doc Status
	 *	@return Document Status
	 */
	public String getDocStatus();


	/*************************************************************************
	 * 	Process document
	 *	@param action document action
	 *	@return true if performed
	 *	@throws Exception
	 */
	public boolean processIt (String action) throws Exception;

	/**
	 * 	Unlock Document.
	 * 	@return true if success
	 */
	public boolean unlockIt();
	/**
	 * 	Invalidate Document
	 * 	@return true if success
	 */
	public boolean invalidateIt();
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid)
	 */
	public String prepareIt();
	/**
	 * 	Approve Document
	 * 	@return true if success
	 */
	public boolean  approveIt();
	/**
	 * 	Reject Approval
	 * 	@return true if success
	 */
	public boolean rejectIt();
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt();
	/**
	 * 	Void Document
	 * 	@return true if success
	 */
	public boolean voidIt();
	/**
	 * 	Close Document
	 * 	@return true if success
	 */
	public boolean closeIt();

// @Formatter:off
//  ts: Note: not declaring this method, because i don't want each DocAction to have to implement it
//	/**
//	 * 	Unclose Document
//	 * 	@return true if success
//	 */
//	public boolean unCloseIt();
// @Formatter:on

	/**
	 * 	Reverse Correction
	 * 	@return true if success
	 */
	public boolean reverseCorrectIt();
	/**
	 * 	Reverse Accrual
	 * 	@return true if success
	 */
	public boolean reverseAccrualIt();
	/**
	 * 	Re-activate
	 * 	@return true if success
	 */
	public boolean reActivateIt();

	/**************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary();

	/**
	 * 	Get Document No
	 *	@return Document No
	 */
	public String getDocumentNo();

	/**
	 * 	Get Document Info
	 *	@return Type and Document No
	 */
	public String getDocumentInfo();

	/**
	 * 	Create PDF
	 *	@return file
	 */
	public File createPDF ();

	/**
	 * 	Get Process Message
	 *	@return clear text message
	 */
	public String getProcessMsg ();

	/**
	 * 	Get Document Owner
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID();

	/**
	 * 	Get Document Currency
	 *	@return C_Currency_ID
	 */
	public int getC_Currency_ID();

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt();

	/**
	 * 	Get Document Client
	 *	@return AD_Client_ID
	 */
	public int getAD_Client_ID();
	public I_AD_Client getAD_Client();

	/**
	 * 	Get Document Organization
	 *	@return AD_Org_ID
	 */
	public int getAD_Org_ID();

	/**
	 * 	Get Doc Action
	 *	@return Document Action
	 */
	public String getDocAction();

	/**
	 * 	Save Document
	 *	@return true if saved
	 */
	public boolean save();

	/**
	 * 	Get Context
	 *	@return context
	 */
	public Properties getCtx();

	/**
	 * 	Get ID of record
	 *	@return ID
	 */
	public int get_ID();

	/**
	 * 	Get AD_Table_ID
	 *	@return AD_Table_ID
	 */
	public int get_Table_ID();

	/**
	 * 	Get Logger
	 *	@return logger
	 */
	public Logger get_Logger();

	/**
	 * 	Get Transaction
	 *	@return trx name
	 */
	public String get_TrxName();

	public void set_TrxName (String trxName);

	/**
	 * Checks if document is active
	 *
	 * @return true if document is active (i.e. IsActive flag is set to Yes)
	 */
	public boolean isActive();
}	//	DocAction
