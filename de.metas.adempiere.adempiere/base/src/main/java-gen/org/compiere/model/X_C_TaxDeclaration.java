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
import java.util.Properties;

/** Generated Model for C_TaxDeclaration
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_TaxDeclaration extends org.compiere.model.PO implements I_C_TaxDeclaration, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1370834963L;

    /** Standard Constructor */
    public X_C_TaxDeclaration (Properties ctx, int C_TaxDeclaration_ID, String trxName)
    {
      super (ctx, C_TaxDeclaration_ID, trxName);
      /** if (C_TaxDeclaration_ID == 0)
        {
			setC_TaxDeclaration_ID (0);
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_C_TaxDeclaration (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Datum von.
		@param DateFrom 
		Starting date for a range
	  */
	@Override
	public void setDateFrom (java.sql.Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Datum von.
		@return Starting date for a range
	  */
	@Override
	public java.sql.Timestamp getDateFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Datum bis.
		@param DateTo 
		End date of a date range
	  */
	@Override
	public void setDateTo (java.sql.Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Datum bis.
		@return End date of a date range
	  */
	@Override
	public java.sql.Timestamp getDateTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTo);
	}

	/** Set Vorgangsdatum.
		@param DateTrx 
		Transaction Date
	  */
	@Override
	public void setDateTrx (java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Vorgangsdatum.
		@return Transaction Date
	  */
	@Override
	public java.sql.Timestamp getDateTrx () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTrx);
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
}