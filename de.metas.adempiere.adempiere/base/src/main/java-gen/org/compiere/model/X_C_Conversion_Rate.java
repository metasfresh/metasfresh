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

/** Generated Model for C_Conversion_Rate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Conversion_Rate extends org.compiere.model.PO implements I_C_Conversion_Rate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1654171835L;

    /** Standard Constructor */
    public X_C_Conversion_Rate (Properties ctx, int C_Conversion_Rate_ID, String trxName)
    {
      super (ctx, C_Conversion_Rate_ID, trxName);
      /** if (C_Conversion_Rate_ID == 0)
        {
			setC_Conversion_Rate_ID (0);
			setC_ConversionType_ID (0);
			setC_Currency_ID (0);
			setC_Currency_ID_To (0);
			setDivideRate (Env.ZERO);
			setMultiplyRate (Env.ZERO);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_Conversion_Rate (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Wechselkurs.
		@param C_Conversion_Rate_ID 
		Rate used for converting currencies
	  */
	@Override
	public void setC_Conversion_Rate_ID (int C_Conversion_Rate_ID)
	{
		if (C_Conversion_Rate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Conversion_Rate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Conversion_Rate_ID, Integer.valueOf(C_Conversion_Rate_ID));
	}

	/** Get Wechselkurs.
		@return Rate used for converting currencies
	  */
	@Override
	public int getC_Conversion_Rate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Conversion_Rate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class);
	}

	@Override
	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType)
	{
		set_ValueFromPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class, C_ConversionType);
	}

	/** Set Kursart.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Currency Conversion Rate Type
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency_To() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID_To, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency_To(org.compiere.model.I_C_Currency C_Currency_To)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID_To, org.compiere.model.I_C_Currency.class, C_Currency_To);
	}

	/** Set Zielwährung.
		@param C_Currency_ID_To 
		Target currency
	  */
	@Override
	public void setC_Currency_ID_To (int C_Currency_ID_To)
	{
		set_Value (COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
	}

	/** Get Zielwährung.
		@return Target currency
	  */
	@Override
	public int getC_Currency_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Divisor.
		@param DivideRate 
		To convert Source number to Target number, the Source is divided
	  */
	@Override
	public void setDivideRate (java.math.BigDecimal DivideRate)
	{
		set_Value (COLUMNNAME_DivideRate, DivideRate);
	}

	/** Get Divisor.
		@return To convert Source number to Target number, the Source is divided
	  */
	@Override
	public java.math.BigDecimal getDivideRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DivideRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Faktor.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	@Override
	public void setMultiplyRate (java.math.BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Faktor.
		@return Rate to multiple the source by to calculate the target.
	  */
	@Override
	public java.math.BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Valid to including this date (last day)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}