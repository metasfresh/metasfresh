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

/** Generated Model for C_TaxDeclarationLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_TaxDeclarationLine extends org.compiere.model.PO implements I_C_TaxDeclarationLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1123832285L;

    /** Standard Constructor */
    public X_C_TaxDeclarationLine (Properties ctx, int C_TaxDeclarationLine_ID, String trxName)
    {
      super (ctx, C_TaxDeclarationLine_ID, trxName);
      /** if (C_TaxDeclarationLine_ID == 0)
        {
			setC_TaxDeclaration_ID (0);
			setC_TaxDeclarationLine_ID (0);
			setLine (0);
        } */
    }

    /** Load Constructor */
    public X_C_TaxDeclarationLine (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
	public org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxDeclaration_ID, org.compiere.model.I_C_TaxDeclaration.class);
	}

	@Override
	public void setC_TaxDeclaration(org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxDeclaration_ID, org.compiere.model.I_C_TaxDeclaration.class, C_TaxDeclaration);
	}

	/** Set Steuererklärung.
		@param C_TaxDeclaration_ID 
		Define the declaration to the tax authorities
	  */
	@Override
	public void setC_TaxDeclaration_ID (int C_TaxDeclaration_ID)
	{
		if (C_TaxDeclaration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclaration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclaration_ID, Integer.valueOf(C_TaxDeclaration_ID));
	}

	/** Get Steuererklärung.
		@return Define the declaration to the tax authorities
	  */
	@Override
	public int getC_TaxDeclaration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxDeclaration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax Declaration Line.
		@param C_TaxDeclarationLine_ID 
		Tax Declaration Document Information
	  */
	@Override
	public void setC_TaxDeclarationLine_ID (int C_TaxDeclarationLine_ID)
	{
		if (C_TaxDeclarationLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclarationLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclarationLine_ID, Integer.valueOf(C_TaxDeclarationLine_ID));
	}

	/** Get Tax Declaration Line.
		@return Tax Declaration Document Information
	  */
	@Override
	public int getC_TaxDeclarationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxDeclarationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_VAT_Code_ID.
		@param C_VAT_Code_ID VAT Code
	  */
	@Override
	public void setC_VAT_Code_ID (int C_VAT_Code_ID)
	{
		if (C_VAT_Code_ID < 1)
			set_Value (COLUMNNAME_C_VAT_Code_ID, null);
		else
			set_Value (COLUMNNAME_C_VAT_Code_ID, Integer.valueOf(C_VAT_Code_ID));
	}

	/** Get C_VAT_Code_ID.
		@return VAT Code
	  */
	@Override
	public int getC_VAT_Code_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_VAT_Code_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set VATCode.
		@param VATCode VAT Code
	  */
	@Override
	public void setVATCode (java.lang.String VATCode)
	{
		set_Value (COLUMNNAME_VATCode, VATCode);
	}

	/** Get VATCode.
		@return VAT Code
	  */
	@Override
	public java.lang.String getVATCode ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATCode);
	}

	/** Set AmountType.
		@param AmountType Amount Type
	  */
	@Override
	public void setAmountType (java.lang.String AmountType)
	{
		set_Value (COLUMNNAME_AmountType, AmountType);
	}

	/** Get AmountType.
		@return Amount Type
	  */
	@Override
	public java.lang.String getAmountType ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_AmountType);
	}

	/** Set Amount.
		@param Amount Amount
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount
	  */
	@Override
	public java.math.BigDecimal getAmount ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LineCount.
		@param LineCount Line Count
	  */
	@Override
	public void setLineCount (java.math.BigDecimal LineCount)
	{
		set_Value (COLUMNNAME_LineCount, LineCount);
	}

	/** Get LineCount.
		@return Line Count
	  */
	@Override
	public java.math.BigDecimal getLineCount ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineCount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}