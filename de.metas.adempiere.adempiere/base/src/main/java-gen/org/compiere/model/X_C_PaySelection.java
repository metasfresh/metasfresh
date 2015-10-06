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
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_PaySelection
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PaySelection extends org.compiere.model.PO implements I_C_PaySelection, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -469857148L;

    /** Standard Constructor */
    public X_C_PaySelection (Properties ctx, int C_PaySelection_ID, String trxName)
    {
      super (ctx, C_PaySelection_ID, trxName);
      /** if (C_PaySelection_ID == 0)
        {
			setC_BP_BankAccount_ID (0);
			setC_PaySelection_ID (0);
			setIsApproved (false);
			setName (null);
// @#Date@
			setPayDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setProcessed (false);
			setProcessing (false);
			setTotalAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_PaySelection (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_PaySelection[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlung auswählen.
		@param C_PaySelection_ID 
		Payment Selection
	  */
	@Override
	public void setC_PaySelection_ID (int C_PaySelection_ID)
	{
		if (C_PaySelection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
	}

	/** Get Zahlung auswählen.
		@return Payment Selection
	  */
	@Override
	public int getC_PaySelection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaySelection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Position(en) kopieren von.
		@param CreateFrom 
		Process which will generate a new document lines based on an existing document
	  */
	@Override
	public void setCreateFrom (java.lang.String CreateFrom)
	{
		set_Value (COLUMNNAME_CreateFrom, CreateFrom);
	}

	/** Get Position(en) kopieren von.
		@return Process which will generate a new document lines based on an existing document
	  */
	@Override
	public java.lang.String getCreateFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateFrom);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Freigegeben.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
		@return Indicates if this document requires approval
	  */
	@Override
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public org.compiere.util.KeyNamePair getKeyNamePair() 
    {
        return new org.compiere.util.KeyNamePair(get_ID(), getName());
    }

	/** Set Payment date.
		@param PayDate 
		Date Payment made
	  */
	@Override
	public void setPayDate (java.sql.Timestamp PayDate)
	{
		set_Value (COLUMNNAME_PayDate, PayDate);
	}

	/** Get Payment date.
		@return Date Payment made
	  */
	@Override
	public java.sql.Timestamp getPayDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PayDate);
	}

	/** Set PaySelection_includedTab.
		@param PaySelection_includedTab PaySelection_includedTab	  */
	@Override
	public void setPaySelection_includedTab (java.lang.String PaySelection_includedTab)
	{
		set_Value (COLUMNNAME_PaySelection_includedTab, PaySelection_includedTab);
	}

	/** Get PaySelection_includedTab.
		@return PaySelection_includedTab	  */
	@Override
	public java.lang.String getPaySelection_includedTab () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaySelection_includedTab);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
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

	/** Set Gesamtbetrag.
		@param TotalAmt Gesamtbetrag	  */
	@Override
	public void setTotalAmt (java.math.BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Gesamtbetrag.
		@return Gesamtbetrag	  */
	@Override
	public java.math.BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}