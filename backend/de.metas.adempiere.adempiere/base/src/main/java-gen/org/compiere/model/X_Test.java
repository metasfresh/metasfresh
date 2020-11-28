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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for Test
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_Test extends PO implements I_Test, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_Test (Properties ctx, int Test_ID, String trxName)
    {
      super (ctx, Test_ID, trxName);
      /** if (Test_ID == 0)
        {
			setName (null);
			setTest_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Test (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_Test[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getAccount_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getAccount_Acct(), get_TrxName());	}

	/** Set Account_Acct.
		@param Account_Acct Account_Acct	  */
	public void setAccount_Acct (int Account_Acct)
	{
		set_Value (COLUMNNAME_Account_Acct, Integer.valueOf(Account_Acct));
	}

	/** Get Account_Acct.
		@return Account_Acct	  */
	public int getAccount_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BinaryData.
		@param BinaryData 
		Binary Data
	  */
	public void setBinaryData (int BinaryData)
	{
		set_Value (COLUMNNAME_BinaryData, Integer.valueOf(BinaryData));
	}

	/** Get BinaryData.
		@return Binary Data
	  */
	public int getBinaryData () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BinaryData);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Character Data.
		@param CharacterData 
		Long Character Field
	  */
	public void setCharacterData (String CharacterData)
	{
		set_Value (COLUMNNAME_CharacterData, CharacterData);
	}

	/** Get Character Data.
		@return Long Character Field
	  */
	public String getCharacterData () 
	{
		return (String)get_Value(COLUMNNAME_CharacterData);
	}

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Payment.
		@return Payment identifier
	  */
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	public I_M_Locator getM_Locator() throws RuntimeException
    {
		return (I_M_Locator)MTable.get(getCtx(), I_M_Locator.Table_Name)
			.getPO(getM_Locator_ID(), get_TrxName());	}

	/** Set Locator.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Locator.
		@return Warehouse Locator
	  */
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Amount.
		@param T_Amount Amount	  */
	public void setT_Amount (BigDecimal T_Amount)
	{
		set_Value (COLUMNNAME_T_Amount, T_Amount);
	}

	/** Get Amount.
		@return Amount	  */
	public BigDecimal getT_Amount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_T_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Date.
		@param T_Date Date	  */
	public void setT_Date (Timestamp T_Date)
	{
		set_Value (COLUMNNAME_T_Date, T_Date);
	}

	/** Get Date.
		@return Date	  */
	public Timestamp getT_Date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_T_Date);
	}

	/** Set DateTime.
		@param T_DateTime DateTime	  */
	public void setT_DateTime (Timestamp T_DateTime)
	{
		set_Value (COLUMNNAME_T_DateTime, T_DateTime);
	}

	/** Get DateTime.
		@return DateTime	  */
	public Timestamp getT_DateTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_T_DateTime);
	}

	/** Set Test ID.
		@param Test_ID Test ID	  */
	public void setTest_ID (int Test_ID)
	{
		if (Test_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Test_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Test_ID, Integer.valueOf(Test_ID));
	}

	/** Get Test ID.
		@return Test ID	  */
	public int getTest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Test_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Integer.
		@param T_Integer Integer	  */
	public void setT_Integer (int T_Integer)
	{
		set_Value (COLUMNNAME_T_Integer, Integer.valueOf(T_Integer));
	}

	/** Get Integer.
		@return Integer	  */
	public int getT_Integer () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Integer);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Number.
		@param T_Number Number	  */
	public void setT_Number (BigDecimal T_Number)
	{
		set_Value (COLUMNNAME_T_Number, T_Number);
	}

	/** Get Number.
		@return Number	  */
	public BigDecimal getT_Number () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_T_Number);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qty.
		@param T_Qty Qty	  */
	public void setT_Qty (BigDecimal T_Qty)
	{
		set_Value (COLUMNNAME_T_Qty, T_Qty);
	}

	/** Get Qty.
		@return Qty	  */
	public BigDecimal getT_Qty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_T_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}