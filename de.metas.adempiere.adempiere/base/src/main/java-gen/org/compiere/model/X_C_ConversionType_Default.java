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

/** Generated Model for C_ConversionType_Default
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_ConversionType_Default extends org.compiere.model.PO implements I_C_ConversionType_Default, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1805732048L;

    /** Standard Constructor */
    public X_C_ConversionType_Default (Properties ctx, int C_ConversionType_Default_ID, String trxName)
    {
      super (ctx, C_ConversionType_Default_ID, trxName);
      /** if (C_ConversionType_Default_ID == 0)
        {
			setC_ConversionType_Default_ID (0);
			setC_ConversionType_ID (0);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_ConversionType_Default (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Kursart (default).
		@param C_ConversionType_Default_ID Kursart (default)	  */
	@Override
	public void setC_ConversionType_Default_ID (int C_ConversionType_Default_ID)
	{
		if (C_ConversionType_Default_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ConversionType_Default_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ConversionType_Default_ID, Integer.valueOf(C_ConversionType_Default_ID));
	}

	/** Get Kursart (default).
		@return Kursart (default)	  */
	@Override
	public int getC_ConversionType_Default_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_Default_ID);
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
		Kursart
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Kursart
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_ValueNoCheck (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}