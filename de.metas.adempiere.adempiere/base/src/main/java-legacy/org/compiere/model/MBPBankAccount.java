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

import java.sql.ResultSet;
import java.util.Properties;

/**
 *  BP Bank Account Model
 *
 *  @author Jorg Janke
 *  @version $Id: MBPBankAccount.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBPBankAccount extends X_C_BP_BankAccount
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2580706419593695062L;

	/**
	 * 	Get Accounst Of BPartner
	 *	@param ctx context
	 *	@param C_BPartner_ID bpartner
	 *	@return
	 */
//	public static MBPBankAccount[] getOfBPartner(final Properties ctx, final int C_BPartner_ID)
//	{
//		final List<I_C_BP_BankAccount> bpBankAccounts = Services.get(IQueryBL.class)
//				.createQueryBuilder(I_C_BP_BankAccount.class, ctx, ITrx.TRXNAME_None)
//				.addEqualsFilter(I_C_BP_BankAccount.COLUMN_C_BPartner_ID, C_BPartner_ID)
//				.addOnlyActiveRecordsFilter()
//				.orderBy()
//				.addColumn(I_C_BP_BankAccount.COLUMN_C_BP_BankAccount_ID)
//				.endOrderBy()
//				.create()
//				.list();
//		
//		return LegacyAdapters.convertToPOArray(bpBankAccounts, MBPBankAccount.class);
//	}	//	getOfBPartner
	
	/**************************************************************************
	 * 	Constructor
	 *	@param ctx context
	 *	@param C_BP_BankAccount_ID BP bank account
	 *	@param trxName transaction
	 */
	public MBPBankAccount (Properties ctx, int C_BP_BankAccount_ID, String trxName)
	{
		super (ctx, C_BP_BankAccount_ID, trxName);
		if (C_BP_BankAccount_ID == 0)
		{
		//	setC_BPartner_ID (0);
			setIsACH (false);
			setBPBankAcctUse(BPBANKACCTUSE_Both);
		}
	}	//	MBP_BankAccount

	/**
	 * 	Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MBPBankAccount (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBP_BankAccount

	/**
	 * 	Constructor
	 *	@param ctx context
	 * 	@param bp BP
	 *	@param bpc BP Contact
	 * 	@param location Location
	 */
	public MBPBankAccount (Properties ctx, MBPartner bp, MUser bpc, MLocation location)
	{
		this(ctx, 0, bp.get_TrxName());
		setIsACH (false);
		//
		setC_BPartner_ID(bp.getC_BPartner_ID());
		//
		setA_Name(bpc.getName());
		setA_EMail(bpc.getEMail());
		//
		setA_Street(location.getAddress1());
		setA_City(location.getCity());
		setA_Zip(location.getPostal());
		setA_State(location.getRegionName(true));
		setA_Country(location.getCountryName());
	}	//	MBP_BankAccount

	/**
	 * 	Is Direct Deposit
	 *	@return true if dd
	 */
	public boolean isDirectDeposit()
	{
		if (!isACH())
		{
			return false;
		}
		String s = getBPBankAcctUse();
		if (s == null)
		{
			return true;
		}
		return (s.equals(BPBANKACCTUSE_Both) || s.equals(BPBANKACCTUSE_DirectDeposit));
	}	//	isDirectDeposit
	
	/**
	 * 	Is Direct Debit
	 *	@return true if dd
	 */
	public boolean isDirectDebit()
	{
		if (!isACH())
		{
			return false;
		}
		String s = getBPBankAcctUse();
		if (s == null)
		{
			return true;
		}
		return (s.equals(BPBANKACCTUSE_Both) || s.equals(BPBANKACCTUSE_DirectDebit));
	}	//	isDirectDebit
	
	/**
	 * 	Get Routing No
	 *	@return routing No
	 */
	@Override
	public String getRoutingNo() 
	{
		I_C_Bank bank = getC_Bank();
		String rt = super.getRoutingNo();
		if (bank != null && bank.getC_Bank_ID() > 0)
			rt = bank.getRoutingNo();
		return rt;
	}	//	getRoutingNo

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord) 
	{
		//	maintain routing on bank level
		if (isACH() && getC_Bank_ID() > 0)
		{
			setRoutingNo(null);
		}
		//
		return true;
	}	//	beforeSave
	
	/**
	 *	String Representation
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MBP_BankAccount[")
			.append (get_ID ())
			.append(", Name=").append(getA_Name())
			.append ("]");
		return sb.toString ();
	}	//	toString

}	//	MBPBankAccount
