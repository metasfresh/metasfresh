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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 *	Invoice Payment Schedule Model 
 *	
 *  @author Jorg Janke
 *  @version $Id: MInvoicePaySchedule.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInvoicePaySchedule extends X_C_InvoicePaySchedule
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1529278048406862670L;


	/**
	 * 	Get Payment Schedule of the invoice
	 * 	@param ctx context
	 * 	@param C_Invoice_ID invoice id (direct)
	 * 	@param C_InvoicePaySchedule_ID id (indirect)
	 *	@param trxName transaction
	 *	@return array of schedule
	 */
	public static MInvoicePaySchedule[] getInvoicePaySchedule(Properties ctx, 
		int C_Invoice_ID, int C_InvoicePaySchedule_ID, String trxName)
	{
		String sql = "SELECT * FROM C_InvoicePaySchedule ips ";
		if (C_Invoice_ID != 0)
			sql += "WHERE C_Invoice_ID=? ";
		else
			sql += "WHERE EXISTS (SELECT * FROM C_InvoicePaySchedule x"
			+ " WHERE x.C_InvoicePaySchedule_ID=? AND ips.C_Invoice_ID=x.C_Invoice_ID) ";
		sql += "ORDER BY DueDate";
		//
		ArrayList<MInvoicePaySchedule> list = new ArrayList<MInvoicePaySchedule>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			if (C_Invoice_ID != 0)
				pstmt.setInt(1, C_Invoice_ID);
			else
				pstmt.setInt(1, C_InvoicePaySchedule_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add (new MInvoicePaySchedule(ctx, rs, trxName));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "getInvoicePaySchedule", e); 
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		
		MInvoicePaySchedule[] retValue = new MInvoicePaySchedule[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getSchedule

	/** Static Logger					*/
	private static CLogger		s_log = CLogger.getCLogger (MInvoicePaySchedule.class);

	/** 100								*/
	private final static BigDecimal		HUNDRED = new BigDecimal(100);

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_InvoicePaySchedule_ID id
	 *	@param trxName transaction
	 */
	public MInvoicePaySchedule (Properties ctx, int C_InvoicePaySchedule_ID, String trxName)
	{
		super(ctx, C_InvoicePaySchedule_ID, trxName);
		if (C_InvoicePaySchedule_ID == 0)
		{
		//	setC_Invoice_ID (0);
		//	setDiscountAmt (Env.ZERO);
		//	setDiscountDate (new Timestamp(System.currentTimeMillis()));
		//	setDueAmt (Env.ZERO);
		//	setDueDate (new Timestamp(System.currentTimeMillis()));
			setIsValid (false);
		}
	}	//	MInvoicePaySchedule

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MInvoicePaySchedule (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInvoicePaySchedule

	/**
	 * 	Parent Constructor
	 *	@param invoice invoice
	 *	@param paySchedule payment schedule
	 */
	public MInvoicePaySchedule (MInvoice invoice, MPaySchedule paySchedule)
	{
		super (invoice.getCtx(), 0, invoice.get_TrxName());
		m_parent = invoice;
		setClientOrg(invoice);
		setC_Invoice_ID(invoice.getC_Invoice_ID());
		setC_PaySchedule_ID(paySchedule.getC_PaySchedule_ID());
		
		//	Amounts
		int scale = MCurrency.getStdPrecision(getCtx(), invoice.getC_Currency_ID());
		BigDecimal due = invoice.getGrandTotal();
		if (due.compareTo(Env.ZERO) == 0)
		{
			setDueAmt (Env.ZERO);
			setDiscountAmt (Env.ZERO);
			setIsValid(false);
		}
		else
		{
			due = due.multiply(paySchedule.getPercentage())
				.divide(HUNDRED, scale, BigDecimal.ROUND_HALF_UP);
			setDueAmt (due);
			BigDecimal discount = due.multiply(paySchedule.getDiscount())
				.divide(HUNDRED, scale, BigDecimal.ROUND_HALF_UP);
			setDiscountAmt (discount);
			setIsValid(true);
		}
		
		//	Dates		
		Timestamp dueDate = TimeUtil.addDays(invoice.getDateInvoiced(), paySchedule.getNetDays());
		setDueDate (dueDate);
		Timestamp discountDate = TimeUtil.addDays(invoice.getDateInvoiced(), paySchedule.getDiscountDays());
		setDiscountDate (discountDate);
	}	//	MInvoicePaySchedule
	
	/**	Parent						*/
	private MInvoice	m_parent = null;

	
	/**
	 * @return Returns the parent.
	 */
	public MInvoice getParent ()
	{
		if (m_parent == null)
			m_parent = new MInvoice (getCtx(), getC_Invoice_ID(), get_TrxName()); 
		return m_parent;
	}	//	getParent
	
	/**
	 * @param parent The parent to set.
	 */
	public void setParent (MInvoice parent)
	{
		m_parent = parent;
	}	//	setParent
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MInvoicePaySchedule[");
		sb.append(get_ID()).append("-Due=" + getDueDate() + "/" + getDueAmt())
			.append(";Discount=").append(getDiscountDate() + "/" + getDiscountAmt())
			.append("]");
		return sb.toString();
	}	//	toString
	
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (is_ValueChanged("DueAmt"))
		{
			log.fine("beforeSave");
			setIsValid(false);
		}
		return true;
	}	//	beforeSave

	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (is_ValueChanged("DueAmt"))
		{
			log.fine("afterSave");
			getParent();
			m_parent.validatePaySchedule();
			m_parent.save();
		}
		return success;
	}	//	afterSave

	
}	//	MInvoicePaySchedule
