/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for U_POSTerminal
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_U_POSTerminal extends PO implements I_U_POSTerminal, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_U_POSTerminal (Properties ctx, int U_POSTerminal_ID, String trxName)
    {
      super (ctx, U_POSTerminal_ID, trxName);
      /** if (U_POSTerminal_ID == 0)
        {
			setAutoLock (false);
// N
			setCashBookTransferType (null);
			setC_CashBook_ID (0);
			setC_CashBPartner_ID (0);
			setU_POSTerminal_ID (0);
        } */
    }

    /** Load Constructor */
    public X_U_POSTerminal (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_U_POSTerminal[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Auto Lock.
		@param AutoLock 
		Whether to automatically lock the terminal when till is closed
	  */
	public void setAutoLock (boolean AutoLock)
	{
		set_Value (COLUMNNAME_AutoLock, Boolean.valueOf(AutoLock));
	}

	/** Get Auto Lock.
		@return Whether to automatically lock the terminal when till is closed
	  */
	public boolean isAutoLock () 
	{
		Object oo = get_Value(COLUMNNAME_AutoLock);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_BankAccount getCard_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getCard_BankAccount_ID(), get_TrxName());	}

	/** Set Card Bank Account.
		@param Card_BankAccount_ID 
		Bank Account on which card transactions will be processed
	  */
	public void setCard_BankAccount_ID (int Card_BankAccount_ID)
	{
		if (Card_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_Card_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_Card_BankAccount_ID, Integer.valueOf(Card_BankAccount_ID));
	}

	/** Get Card Bank Account.
		@return Bank Account on which card transactions will be processed
	  */
	public int getCard_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Card_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BankAccount getCardTransferBankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getCardTransferBankAccount_ID(), get_TrxName());	}

	/** Set Transfer Card trx to.
		@param CardTransferBankAccount_ID 
		Bank account on which to transfer Card transactions
	  */
	public void setCardTransferBankAccount_ID (int CardTransferBankAccount_ID)
	{
		if (CardTransferBankAccount_ID < 1) 
			set_Value (COLUMNNAME_CardTransferBankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_CardTransferBankAccount_ID, Integer.valueOf(CardTransferBankAccount_ID));
	}

	/** Get Transfer Card trx to.
		@return Bank account on which to transfer Card transactions
	  */
	public int getCardTransferBankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CardTransferBankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CashBook getCardTransferCashBook() throws RuntimeException
    {
		return (I_C_CashBook)MTable.get(getCtx(), I_C_CashBook.Table_Name)
			.getPO(getCardTransferCashBook_ID(), get_TrxName());	}

	/** Set Transfer Card trx to.
		@param CardTransferCashBook_ID 
		Cash Book on which to transfer all Card transactions
	  */
	public void setCardTransferCashBook_ID (int CardTransferCashBook_ID)
	{
		if (CardTransferCashBook_ID < 1) 
			set_Value (COLUMNNAME_CardTransferCashBook_ID, null);
		else 
			set_Value (COLUMNNAME_CardTransferCashBook_ID, Integer.valueOf(CardTransferCashBook_ID));
	}

	/** Get Transfer Card trx to.
		@return Cash Book on which to transfer all Card transactions
	  */
	public int getCardTransferCashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CardTransferCashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CardTransferType AD_Reference_ID=52002 */
	public static final int CARDTRANSFERTYPE_AD_Reference_ID=52002;
	/** Bank Account = B */
	public static final String CARDTRANSFERTYPE_BankAccount = "B";
	/** CashBook = C */
	public static final String CARDTRANSFERTYPE_CashBook = "C";
	/** Set Card Transfer Type.
		@param CardTransferType Card Transfer Type	  */
	public void setCardTransferType (String CardTransferType)
	{

		set_Value (COLUMNNAME_CardTransferType, CardTransferType);
	}

	/** Get Card Transfer Type.
		@return Card Transfer Type	  */
	public String getCardTransferType () 
	{
		return (String)get_Value(COLUMNNAME_CardTransferType);
	}

	/** CashBookTransferType AD_Reference_ID=52002 */
	public static final int CASHBOOKTRANSFERTYPE_AD_Reference_ID=52002;
	/** Bank Account = B */
	public static final String CASHBOOKTRANSFERTYPE_BankAccount = "B";
	/** CashBook = C */
	public static final String CASHBOOKTRANSFERTYPE_CashBook = "C";
	/** Set Cash Book Transfer Type.
		@param CashBookTransferType 
		Where the money in the cash book should be transfered to. Either a Bank Account or another Cash Book
	  */
	public void setCashBookTransferType (String CashBookTransferType)
	{

		set_Value (COLUMNNAME_CashBookTransferType, CashBookTransferType);
	}

	/** Get Cash Book Transfer Type.
		@return Where the money in the cash book should be transfered to. Either a Bank Account or another Cash Book
	  */
	public String getCashBookTransferType () 
	{
		return (String)get_Value(COLUMNNAME_CashBookTransferType);
	}

	public I_C_BankAccount getCashTransferBankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getCashTransferBankAccount_ID(), get_TrxName());	}

	/** Set Transfer Cash trx to.
		@param CashTransferBankAccount_ID 
		Bank Account on which to transfer all Cash transactions
	  */
	public void setCashTransferBankAccount_ID (int CashTransferBankAccount_ID)
	{
		if (CashTransferBankAccount_ID < 1) 
			set_Value (COLUMNNAME_CashTransferBankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_CashTransferBankAccount_ID, Integer.valueOf(CashTransferBankAccount_ID));
	}

	/** Get Transfer Cash trx to.
		@return Bank Account on which to transfer all Cash transactions
	  */
	public int getCashTransferBankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashTransferBankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CashBook getCashTransferCashBook() throws RuntimeException
    {
		return (I_C_CashBook)MTable.get(getCtx(), I_C_CashBook.Table_Name)
			.getPO(getCashTransferCashBook_ID(), get_TrxName());	}

	/** Set Transfer Cash trx to.
		@param CashTransferCashBook_ID 
		Cash Book on which to transfer all Cash transactions
	  */
	public void setCashTransferCashBook_ID (int CashTransferCashBook_ID)
	{
		if (CashTransferCashBook_ID < 1) 
			set_Value (COLUMNNAME_CashTransferCashBook_ID, null);
		else 
			set_Value (COLUMNNAME_CashTransferCashBook_ID, Integer.valueOf(CashTransferCashBook_ID));
	}

	/** Get Transfer Cash trx to.
		@return Cash Book on which to transfer all Cash transactions
	  */
	public int getCashTransferCashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CashTransferCashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CashBook getC_CashBook() throws RuntimeException
    {
		return (I_C_CashBook)MTable.get(getCtx(), I_C_CashBook.Table_Name)
			.getPO(getC_CashBook_ID(), get_TrxName());	}

	/** Set Cash Book.
		@param C_CashBook_ID 
		Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	/** Get Cash Book.
		@return Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner getC_CashBPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_CashBPartner_ID(), get_TrxName());	}

	/** Set Cash BPartner.
		@param C_CashBPartner_ID 
		BPartner to be used for Cash transactions
	  */
	public void setC_CashBPartner_ID (int C_CashBPartner_ID)
	{
		if (C_CashBPartner_ID < 1) 
			set_Value (COLUMNNAME_C_CashBPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBPartner_ID, Integer.valueOf(C_CashBPartner_ID));
	}

	/** Get Cash BPartner.
		@return BPartner to be used for Cash transactions
	  */
	public int getC_CashBPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BankAccount getCheck_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getCheck_BankAccount_ID(), get_TrxName());	}

	/** Set Check Bank Account.
		@param Check_BankAccount_ID 
		Bank Account to be used for processing Check transactions
	  */
	public void setCheck_BankAccount_ID (int Check_BankAccount_ID)
	{
		if (Check_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_Check_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_Check_BankAccount_ID, Integer.valueOf(Check_BankAccount_ID));
	}

	/** Get Check Bank Account.
		@return Bank Account to be used for processing Check transactions
	  */
	public int getCheck_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Check_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BankAccount getCheckTransferBankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getCheckTransferBankAccount_ID(), get_TrxName());	}

	/** Set Tranfer Check trx to.
		@param CheckTransferBankAccount_ID 
		Bank account on which to transfer Check transactions
	  */
	public void setCheckTransferBankAccount_ID (int CheckTransferBankAccount_ID)
	{
		if (CheckTransferBankAccount_ID < 1) 
			set_Value (COLUMNNAME_CheckTransferBankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_CheckTransferBankAccount_ID, Integer.valueOf(CheckTransferBankAccount_ID));
	}

	/** Get Tranfer Check trx to.
		@return Bank account on which to transfer Check transactions
	  */
	public int getCheckTransferBankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CheckTransferBankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_CashBook getCheckTransferCashBook() throws RuntimeException
    {
		return (I_C_CashBook)MTable.get(getCtx(), I_C_CashBook.Table_Name)
			.getPO(getCheckTransferCashBook_ID(), get_TrxName());	}

	/** Set Transfer Check trx to.
		@param CheckTransferCashBook_ID 
		Cash Book on which to transfer all Check transactions
	  */
	public void setCheckTransferCashBook_ID (int CheckTransferCashBook_ID)
	{
		if (CheckTransferCashBook_ID < 1) 
			set_Value (COLUMNNAME_CheckTransferCashBook_ID, null);
		else 
			set_Value (COLUMNNAME_CheckTransferCashBook_ID, Integer.valueOf(CheckTransferCashBook_ID));
	}

	/** Get Transfer Check trx to.
		@return Cash Book on which to transfer all Check transactions
	  */
	public int getCheckTransferCashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CheckTransferCashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CheckTransferType AD_Reference_ID=52002 */
	public static final int CHECKTRANSFERTYPE_AD_Reference_ID=52002;
	/** Bank Account = B */
	public static final String CHECKTRANSFERTYPE_BankAccount = "B";
	/** CashBook = C */
	public static final String CHECKTRANSFERTYPE_CashBook = "C";
	/** Set Check Transfer Type.
		@param CheckTransferType Check Transfer Type	  */
	public void setCheckTransferType (String CheckTransferType)
	{

		set_Value (COLUMNNAME_CheckTransferType, CheckTransferType);
	}

	/** Get Check Transfer Type.
		@return Check Transfer Type	  */
	public String getCheckTransferType () 
	{
		return (String)get_Value(COLUMNNAME_CheckTransferType);
	}

	public I_C_BPartner getC_TemplateBPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_TemplateBPartner_ID(), get_TrxName());	}

	/** Set Template BPartner.
		@param C_TemplateBPartner_ID 
		BPartner that is to be used as template when new customers are created
	  */
	public void setC_TemplateBPartner_ID (int C_TemplateBPartner_ID)
	{
		if (C_TemplateBPartner_ID < 1) 
			set_Value (COLUMNNAME_C_TemplateBPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_TemplateBPartner_ID, Integer.valueOf(C_TemplateBPartner_ID));
	}

	/** Get Template BPartner.
		@return BPartner that is to be used as template when new customers are created
	  */
	public int getC_TemplateBPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TemplateBPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Last Lock Time.
		@param LastLockTime 
		Last time at which the terminal was locked
	  */
	public void setLastLockTime (Timestamp LastLockTime)
	{
		set_Value (COLUMNNAME_LastLockTime, LastLockTime);
	}

	/** Get Last Lock Time.
		@return Last time at which the terminal was locked
	  */
	public Timestamp getLastLockTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastLockTime);
	}

	/** Set Locked.
		@param Locked 
		Whether the terminal is locked
	  */
	public void setLocked (boolean Locked)
	{
		set_Value (COLUMNNAME_Locked, Boolean.valueOf(Locked));
	}

	/** Get Locked.
		@return Whether the terminal is locked
	  */
	public boolean isLocked () 
	{
		Object oo = get_Value(COLUMNNAME_Locked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lock Time.
		@param LockTime 
		Time in minutes the terminal should be kept in a locked state.
	  */
	public void setLockTime (int LockTime)
	{
		set_Value (COLUMNNAME_LockTime, Integer.valueOf(LockTime));
	}

	/** Get Lock Time.
		@return Time in minutes the terminal should be kept in a locked state.
	  */
	public int getLockTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LockTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public I_M_PriceList getPO_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getPO_PriceList_ID(), get_TrxName());	}

	/** Set Purchase Pricelist.
		@param PO_PriceList_ID 
		Price List used by this Business Partner
	  */
	public void setPO_PriceList_ID (int PO_PriceList_ID)
	{
		if (PO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_PO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_PO_PriceList_ID, Integer.valueOf(PO_PriceList_ID));
	}

	/** Get Purchase Pricelist.
		@return Price List used by this Business Partner
	  */
	public int getPO_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PO_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Printer Name.
		@param PrinterName 
		Name of the Printer
	  */
	public void setPrinterName (String PrinterName)
	{
		set_Value (COLUMNNAME_PrinterName, PrinterName);
	}

	/** Get Printer Name.
		@return Name of the Printer
	  */
	public String getPrinterName () 
	{
		return (String)get_Value(COLUMNNAME_PrinterName);
	}

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_PriceList getSO_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getSO_PriceList_ID(), get_TrxName());	}

	/** Set Sales Pricelist.
		@param SO_PriceList_ID Sales Pricelist	  */
	public void setSO_PriceList_ID (int SO_PriceList_ID)
	{
		if (SO_PriceList_ID < 1) 
			set_Value (COLUMNNAME_SO_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_SO_PriceList_ID, Integer.valueOf(SO_PriceList_ID));
	}

	/** Get Sales Pricelist.
		@return Sales Pricelist	  */
	public int getSO_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SO_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UnlockingTime.
		@param UnlockingTime 
		Time at which the terminal should be unlocked
	  */
	public void setUnlockingTime (Timestamp UnlockingTime)
	{
		set_Value (COLUMNNAME_UnlockingTime, UnlockingTime);
	}

	/** Get UnlockingTime.
		@return Time at which the terminal should be unlocked
	  */
	public Timestamp getUnlockingTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_UnlockingTime);
	}

	/** Set POS Terminal.
		@param U_POSTerminal_ID POS Terminal	  */
	public void setU_POSTerminal_ID (int U_POSTerminal_ID)
	{
		if (U_POSTerminal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_U_POSTerminal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_U_POSTerminal_ID, Integer.valueOf(U_POSTerminal_ID));
	}

	/** Get POS Terminal.
		@return POS Terminal	  */
	public int getU_POSTerminal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_U_POSTerminal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}