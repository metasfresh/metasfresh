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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_DunningLevel
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_DunningLevel extends PO implements I_C_DunningLevel, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20091123L;

    /** Standard Constructor */
    public X_C_DunningLevel (Properties ctx, int C_DunningLevel_ID, String trxName)
    {
      super (ctx, C_DunningLevel_ID, trxName);
      /** if (C_DunningLevel_ID == 0)
        {
			setC_Dunning_ID (0);
			setC_DunningLevel_ID (0);
			setChargeFee (false);
			setChargeInterest (false);
			setDaysAfterDue (Env.ZERO);
			setDaysBetweenDunning (0);
			setIsSetCreditStop (false);
			setIsSetPaymentTerm (false);
			setIsShowAllDue (false);
			setIsShowNotDue (false);
			setIsStatement (false);
// N
			setName (null);
			setPrintName (null);
        } */
    }

    /** Load Constructor */
    public X_C_DunningLevel (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_DunningLevel[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Dunning getC_Dunning() throws RuntimeException
    {
		return (I_C_Dunning)MTable.get(getCtx(), I_C_Dunning.Table_Name)
			.getPO(getC_Dunning_ID(), get_TrxName());	}

	/** Set Dunning.
		@param C_Dunning_ID 
		Dunning Rules for overdue invoices
	  */
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Dunning.
		@return Dunning Rules for overdue invoices
	  */
	public int getC_Dunning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Level.
		@param C_DunningLevel_ID Dunning Level	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Dunning Level.
		@return Dunning Level	  */
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Charge fee.
		@param ChargeFee 
		Indicates if fees will be charged for overdue invoices
	  */
	public void setChargeFee (boolean ChargeFee)
	{
		set_Value (COLUMNNAME_ChargeFee, Boolean.valueOf(ChargeFee));
	}

	/** Get Charge fee.
		@return Indicates if fees will be charged for overdue invoices
	  */
	public boolean isChargeFee () 
	{
		Object oo = get_Value(COLUMNNAME_ChargeFee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Charge Interest.
		@param ChargeInterest 
		Indicates if interest will be charged on overdue invoices
	  */
	public void setChargeInterest (boolean ChargeInterest)
	{
		set_Value (COLUMNNAME_ChargeInterest, Boolean.valueOf(ChargeInterest));
	}

	/** Get Charge Interest.
		@return Indicates if interest will be charged on overdue invoices
	  */
	public boolean isChargeInterest () 
	{
		Object oo = get_Value(COLUMNNAME_ChargeInterest);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (I_C_PaymentTerm)MTable.get(getCtx(), I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Days after due date.
		@param DaysAfterDue 
		Days after due date to dun (if negative days until due)
	  */
	public void setDaysAfterDue (BigDecimal DaysAfterDue)
	{
		set_Value (COLUMNNAME_DaysAfterDue, DaysAfterDue);
	}

	/** Get Days after due date.
		@return Days after due date to dun (if negative days until due)
	  */
	public BigDecimal getDaysAfterDue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DaysAfterDue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Days between dunning.
		@param DaysBetweenDunning 
		Days between sending dunning notices
	  */
	public void setDaysBetweenDunning (int DaysBetweenDunning)
	{
		set_Value (COLUMNNAME_DaysBetweenDunning, Integer.valueOf(DaysBetweenDunning));
	}

	/** Get Days between dunning.
		@return Days between sending dunning notices
	  */
	public int getDaysBetweenDunning () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DaysBetweenDunning);
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

	public I_AD_PrintFormat getDunning_PrintFormat() throws RuntimeException
    {
		return (I_AD_PrintFormat)MTable.get(getCtx(), I_AD_PrintFormat.Table_Name)
			.getPO(getDunning_PrintFormat_ID(), get_TrxName());	}

	/** Set Dunning Print Format.
		@param Dunning_PrintFormat_ID 
		Print Format for printing Dunning Letters
	  */
	public void setDunning_PrintFormat_ID (int Dunning_PrintFormat_ID)
	{
		if (Dunning_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_Dunning_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_Dunning_PrintFormat_ID, Integer.valueOf(Dunning_PrintFormat_ID));
	}

	/** Get Dunning Print Format.
		@return Print Format for printing Dunning Letters
	  */
	public int getDunning_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Dunning_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fee Amount.
		@param FeeAmt 
		Fee amount in invoice currency
	  */
	public void setFeeAmt (BigDecimal FeeAmt)
	{
		set_Value (COLUMNNAME_FeeAmt, FeeAmt);
	}

	/** Get Fee Amount.
		@return Fee amount in invoice currency
	  */
	public BigDecimal getFeeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FeeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Interest in percent.
		@param InterestPercent 
		Percentage interest to charge on overdue invoices
	  */
	public void setInterestPercent (BigDecimal InterestPercent)
	{
		set_Value (COLUMNNAME_InterestPercent, InterestPercent);
	}

	/** Get Interest in percent.
		@return Percentage interest to charge on overdue invoices
	  */
	public BigDecimal getInterestPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InterestPercent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** InvoiceCollectionType AD_Reference_ID=394 */
	public static final int INVOICECOLLECTIONTYPE_AD_Reference_ID=394;
	/** Dunning = D */
	public static final String INVOICECOLLECTIONTYPE_Dunning = "D";
	/** Collection Agency = C */
	public static final String INVOICECOLLECTIONTYPE_CollectionAgency = "C";
	/** Legal Procedure = L */
	public static final String INVOICECOLLECTIONTYPE_LegalProcedure = "L";
	/** Uncollectable = U */
	public static final String INVOICECOLLECTIONTYPE_Uncollectable = "U";
	/** Set Collection Status.
		@param InvoiceCollectionType 
		Invoice Collection Status
	  */
	public void setInvoiceCollectionType (String InvoiceCollectionType)
	{

		set_Value (COLUMNNAME_InvoiceCollectionType, InvoiceCollectionType);
	}

	/** Get Collection Status.
		@return Invoice Collection Status
	  */
	public String getInvoiceCollectionType () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceCollectionType);
	}

	/** Set Credit Stop.
		@param IsSetCreditStop 
		Set the business partner to credit stop
	  */
	public void setIsSetCreditStop (boolean IsSetCreditStop)
	{
		set_Value (COLUMNNAME_IsSetCreditStop, Boolean.valueOf(IsSetCreditStop));
	}

	/** Get Credit Stop.
		@return Set the business partner to credit stop
	  */
	public boolean isSetCreditStop () 
	{
		Object oo = get_Value(COLUMNNAME_IsSetCreditStop);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Set Payment Term.
		@param IsSetPaymentTerm 
		Set the payment term of the Business Partner
	  */
	public void setIsSetPaymentTerm (boolean IsSetPaymentTerm)
	{
		set_Value (COLUMNNAME_IsSetPaymentTerm, Boolean.valueOf(IsSetPaymentTerm));
	}

	/** Get Set Payment Term.
		@return Set the payment term of the Business Partner
	  */
	public boolean isSetPaymentTerm () 
	{
		Object oo = get_Value(COLUMNNAME_IsSetPaymentTerm);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Show All Due.
		@param IsShowAllDue 
		Show/print all due invoices
	  */
	public void setIsShowAllDue (boolean IsShowAllDue)
	{
		set_Value (COLUMNNAME_IsShowAllDue, Boolean.valueOf(IsShowAllDue));
	}

	/** Get Show All Due.
		@return Show/print all due invoices
	  */
	public boolean isShowAllDue () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowAllDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Show Not Due.
		@param IsShowNotDue 
		Show/print all invoices which are not due (yet).
	  */
	public void setIsShowNotDue (boolean IsShowNotDue)
	{
		set_Value (COLUMNNAME_IsShowNotDue, Boolean.valueOf(IsShowNotDue));
	}

	/** Get Show Not Due.
		@return Show/print all invoices which are not due (yet).
	  */
	public boolean isShowNotDue () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowNotDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Statement.
		@param IsStatement 
		Dunning Level is a definition of a statement
	  */
	public void setIsStatement (boolean IsStatement)
	{
		set_Value (COLUMNNAME_IsStatement, Boolean.valueOf(IsStatement));
	}

	/** Get Is Statement.
		@return Dunning Level is a definition of a statement
	  */
	public boolean isStatement () 
	{
		Object oo = get_Value(COLUMNNAME_IsStatement);
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

	/** Set Note.
		@param Note 
		Optional additional user defined information
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Note.
		@return Optional additional user defined information
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
	}

	/** Set Print Text.
		@param PrintName 
		The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Print Text.
		@return The label text to be printed on a document or correspondence.
	  */
	public String getPrintName () 
	{
		return (String)get_Value(COLUMNNAME_PrintName);
	}
}