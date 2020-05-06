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

/** Generated Model for I_Conversion_Rate
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_I_Conversion_Rate extends PO implements I_I_Conversion_Rate, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_I_Conversion_Rate (Properties ctx, int I_Conversion_Rate_ID, String trxName)
    {
      super (ctx, I_Conversion_Rate_ID, trxName);
      /** if (I_Conversion_Rate_ID == 0)
        {
			setI_Conversion_Rate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_Conversion_Rate (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_I_Conversion_Rate[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Conversion_Rate getC_Conversion_Rate() throws RuntimeException
    {
		return (I_C_Conversion_Rate)MTable.get(getCtx(), I_C_Conversion_Rate.Table_Name)
			.getPO(getC_Conversion_Rate_ID(), get_TrxName());	}

	/** Set Conversion Rate.
		@param C_Conversion_Rate_ID 
		Rate used for converting currencies
	  */
	public void setC_Conversion_Rate_ID (int C_Conversion_Rate_ID)
	{
		if (C_Conversion_Rate_ID < 1) 
			set_Value (COLUMNNAME_C_Conversion_Rate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Conversion_Rate_ID, Integer.valueOf(C_Conversion_Rate_ID));
	}

	/** Get Conversion Rate.
		@return Rate used for converting currencies
	  */
	public int getC_Conversion_Rate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Conversion_Rate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ConversionType getC_ConversionType() throws RuntimeException
    {
		return (I_C_ConversionType)MTable.get(getCtx(), I_C_ConversionType.Table_Name)
			.getPO(getC_ConversionType_ID(), get_TrxName());	}

	/** Set Currency Type.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Currency Type.
		@return Currency Conversion Rate Type
	  */
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
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

	public I_C_Currency getC_Currency_To() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID_To(), get_TrxName());	}

	/** Set Currency To.
		@param C_Currency_ID_To 
		Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To)
	{
		set_Value (COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
	}

	/** Get Currency To.
		@return Target currency
	  */
	public int getC_Currency_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Currency Type Key.
		@param ConversionTypeValue 
		Key value for the Currency Conversion Rate Type
	  */
	public void setConversionTypeValue (String ConversionTypeValue)
	{
		set_Value (COLUMNNAME_ConversionTypeValue, ConversionTypeValue);
	}

	/** Get Currency Type Key.
		@return Key value for the Currency Conversion Rate Type
	  */
	public String getConversionTypeValue () 
	{
		return (String)get_Value(COLUMNNAME_ConversionTypeValue);
	}

	/** Set Create Reciprocal Rate.
		@param CreateReciprocalRate 
		Create Reciprocal Rate from current information
	  */
	public void setCreateReciprocalRate (boolean CreateReciprocalRate)
	{
		set_Value (COLUMNNAME_CreateReciprocalRate, Boolean.valueOf(CreateReciprocalRate));
	}

	/** Get Create Reciprocal Rate.
		@return Create Reciprocal Rate from current information
	  */
	public boolean isCreateReciprocalRate () 
	{
		Object oo = get_Value(COLUMNNAME_CreateReciprocalRate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Divide Rate.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	public void setDivideRate (BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divide Rate.
		@return To convert Source number to Target number, the Source is divided
	  */
	public BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Import Conversion Rate.
		@param I_Conversion_Rate_ID 
		Import Currency Conversion Rate
	  */
	public void setI_Conversion_Rate_ID (int I_Conversion_Rate_ID)
	{
		if (I_Conversion_Rate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Conversion_Rate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Conversion_Rate_ID, Integer.valueOf(I_Conversion_Rate_ID));
	}

	/** Get Import Conversion Rate.
		@return Import Currency Conversion Rate
	  */
	public int getI_Conversion_Rate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Conversion_Rate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getI_Conversion_Rate_ID()));
    }

	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ISO Currency Code.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	public void setISO_Code (String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Currency Code.
		@return Three letter ISO 4217 Code of the Currency
	  */
	public String getISO_Code () 
	{
		return (String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set ISO Currency To Code.
		@param ISO_Code_To 
		Three letter ISO 4217 Code of the To Currency
	  */
	public void setISO_Code_To (String ISO_Code_To)
	{
		set_Value (COLUMNNAME_ISO_Code_To, ISO_Code_To);
	}

	/** Get ISO Currency To Code.
		@return Three letter ISO 4217 Code of the To Currency
	  */
	public String getISO_Code_To () 
	{
		return (String)get_Value(COLUMNNAME_ISO_Code_To);
	}

	/** Set Multiply Rate.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Multiply Rate.
		@return Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}