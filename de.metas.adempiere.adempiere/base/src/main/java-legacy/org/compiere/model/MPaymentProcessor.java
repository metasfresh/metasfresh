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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *  Payment Processor Model
 *
 *  @author Jorg Janke
 *  @version $Id: MPaymentProcessor.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPaymentProcessor extends X_C_PaymentProcessor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1825454310856682804L;

	public static MPaymentProcessor[] find (Properties ctx,
			String tender, String CCType,
			int AD_Client_ID, int AD_Org_ID, int C_Currency_ID, BigDecimal Amt, String trxName)
		{
			return find(ctx, tender, CCType, AD_Client_ID, C_Currency_ID, Amt, trxName);
		}
	
	/**
	 * 	Get BankAccount & PaymentProcessor
	 * 	@param ctx context
	 *  @param tender optional Tender see TENDER_
	 *  @param CCType optional CC Type see CC_
	 *  @param AD_Client_ID Client
	 *  @param C_Currency_ID Currency (ignored)
	 *  @param Amt Amount (ignored)
	 *	@param trxName transaction
	 *  @return Array of BankAccount[0] & PaymentProcessor[1] or null
	 */
	protected static MPaymentProcessor[] find (Properties ctx,
		String tender, String CCType,
		int AD_Client_ID, int C_Currency_ID, BigDecimal Amt, String trxName)
	{
		ArrayList<MPaymentProcessor> list = new ArrayList<MPaymentProcessor>();
		StringBuffer sql = new StringBuffer("SELECT * "
			+ "FROM C_PaymentProcessor "
			+ "WHERE AD_Client_ID=? AND IsActive='Y'"				//	#1
			+ " AND (C_Currency_ID IS NULL OR C_Currency_ID=?)"		//	#2
			+ " AND (MinimumAmt IS NULL OR MinimumAmt = 0 OR MinimumAmt <= ?)");	//	#3
		if (MPayment.TENDERTYPE_DirectDeposit.equals(tender))
			sql.append(" AND AcceptDirectDeposit='Y'");
		else if (MPayment.TENDERTYPE_DirectDebit.equals(tender))
			sql.append(" AND AcceptDirectDebit='Y'");
		else if (MPayment.TENDERTYPE_Check.equals(tender))
			sql.append(" AND AcceptCheck='Y'");
		//  CreditCards
		else if (MPayment.CREDITCARDTYPE_ATM.equals(CCType))
			sql.append(" AND AcceptATM='Y'");
		else if (MPayment.CREDITCARDTYPE_Amex.equals(CCType))
			sql.append(" AND AcceptAMEX='Y'");
		else if (MPayment.CREDITCARDTYPE_Visa.equals(CCType))
			sql.append(" AND AcceptVISA='Y'");
		else if (MPayment.CREDITCARDTYPE_MasterCard.equals(CCType))
			sql.append(" AND AcceptMC='Y'");
		else if (MPayment.CREDITCARDTYPE_Diners.equals(CCType))
			sql.append(" AND AcceptDiners='Y'");
		else if (MPayment.CREDITCARDTYPE_Discover.equals(CCType))
			sql.append(" AND AcceptDiscover='Y'");
		else if (MPayment.CREDITCARDTYPE_PurchaseCard.equals(CCType))
			sql.append(" AND AcceptCORPORATE='Y'");
		//
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), trxName);
			pstmt.setInt(1, AD_Client_ID);
			pstmt.setInt(2, C_Currency_ID);
			pstmt.setBigDecimal(3, Amt);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MPaymentProcessor (ctx, rs, trxName));
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			s_log.log(Level.SEVERE, "find - " + sql, e);
			return null;
		}
		//
		if (list.size() == 0)
			s_log.warning("find - not found - AD_Client_ID=" + AD_Client_ID
				+ ", C_Currency_ID=" + C_Currency_ID + ", Amt=" + Amt);
		else
			s_log.fine("find - #" + list.size() + " - AD_Client_ID=" + AD_Client_ID
				+ ", C_Currency_ID=" + C_Currency_ID + ", Amt=" + Amt);
		MPaymentProcessor[] retValue = new MPaymentProcessor[list.size()];
		list.toArray(retValue);
		return retValue;
	}   //  find
	
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MPaymentProcessor.class);

	
	/**************************************************************************
	 *	Payment Processor Model
	 * 	@param ctx context
	 * 	@param C_PaymentProcessor_ID payment processor
	 *	@param trxName transaction
	 */
	public MPaymentProcessor (Properties ctx, int C_PaymentProcessor_ID, String trxName)
	{
		super (ctx, C_PaymentProcessor_ID, trxName);
		if (C_PaymentProcessor_ID == 0)
		{
		//	setC_BankAccount_ID (0);		//	Parent
		//	setUserID (null);
		//	setPassword (null);
		//	setHostAddress (null);
		//	setHostPort (0);
			setCommission (Env.ZERO);
			setAcceptVisa (false);
			setAcceptMC (false);
			setAcceptAMEX (false);
			setAcceptDiners (false);
			setCostPerTrx (Env.ZERO);
			setAcceptCheck (false);
			setRequireVV (false);
			setAcceptCorporate (false);
			setAcceptDiscover (false);
			setAcceptATM (false);
			setAcceptDirectDeposit(false);
			setAcceptDirectDebit(false);
		//	setName (null);
		}
	}	//	MPaymentProcessor

	/**
	 *	Payment Processor Model
	 * 	@param ctx context
	 * 	@param rs result set
	 *	@param trxName transaction
	 */
	public MPaymentProcessor (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPaymentProcessor

	/**
	 * 	String representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MPaymentProcessor[")
			.append(get_ID ()).append("-").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Does Payment Processor accepts tender / CC
	 *	@param TenderType tender type
	 *	@param CreditCardType credit card type
	 *	@return true if acceptes
	 */
	public boolean accepts (String TenderType, String CreditCardType)
	{
		if ((MPayment.TENDERTYPE_DirectDeposit.equals(TenderType) && isAcceptDirectDeposit())
			|| (MPayment.TENDERTYPE_DirectDebit.equals(TenderType) && isAcceptDirectDebit())
			|| (MPayment.TENDERTYPE_Check.equals(TenderType) && isAcceptCheck())
			//
			|| (MPayment.CREDITCARDTYPE_ATM.equals(CreditCardType) && isAcceptATM())
			|| (MPayment.CREDITCARDTYPE_Amex.equals(CreditCardType) && isAcceptAMEX())
			|| (MPayment.CREDITCARDTYPE_PurchaseCard.equals(CreditCardType) && isAcceptCorporate())
			|| (MPayment.CREDITCARDTYPE_Diners.equals(CreditCardType) && isAcceptDiners())
			|| (MPayment.CREDITCARDTYPE_Discover.equals(CreditCardType) && isAcceptDiscover())
			|| (MPayment.CREDITCARDTYPE_MasterCard.equals(CreditCardType) && isAcceptMC())
			|| (MPayment.CREDITCARDTYPE_Visa.equals(CreditCardType) && isAcceptVisa()))
			return true;
		return false;
	}	//	accepts

}	//	MPaymentProcessor
