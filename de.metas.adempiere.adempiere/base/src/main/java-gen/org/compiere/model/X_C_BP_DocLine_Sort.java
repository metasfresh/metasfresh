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

/** Generated Model for C_BP_DocLine_Sort
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BP_DocLine_Sort extends org.compiere.model.PO implements I_C_BP_DocLine_Sort, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -834647770L;

    /** Standard Constructor */
    public X_C_BP_DocLine_Sort (Properties ctx, int C_BP_DocLine_Sort_ID, String trxName)
    {
      super (ctx, C_BP_DocLine_Sort_ID, trxName);
      /** if (C_BP_DocLine_Sort_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BP_DocLine_Sort_ID (0);
			setC_DocLine_Sort_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_BP_DocLine_Sort (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner Document Line Sorting Preferences.
		@param C_BP_DocLine_Sort_ID Geschäftspartner Document Line Sorting Preferences	  */
	@Override
	public void setC_BP_DocLine_Sort_ID (int C_BP_DocLine_Sort_ID)
	{
		if (C_BP_DocLine_Sort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_DocLine_Sort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_DocLine_Sort_ID, Integer.valueOf(C_BP_DocLine_Sort_ID));
	}

	/** Get Geschäftspartner Document Line Sorting Preferences.
		@return Geschäftspartner Document Line Sorting Preferences	  */
	@Override
	public int getC_BP_DocLine_Sort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_DocLine_Sort_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocLine_Sort getC_DocLine_Sort() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocLine_Sort_ID, org.compiere.model.I_C_DocLine_Sort.class);
	}

	@Override
	public void setC_DocLine_Sort(org.compiere.model.I_C_DocLine_Sort C_DocLine_Sort)
	{
		set_ValueFromPO(COLUMNNAME_C_DocLine_Sort_ID, org.compiere.model.I_C_DocLine_Sort.class, C_DocLine_Sort);
	}

	/** Set Document Line Sorting Preferences.
		@param C_DocLine_Sort_ID Document Line Sorting Preferences	  */
	@Override
	public void setC_DocLine_Sort_ID (int C_DocLine_Sort_ID)
	{
		if (C_DocLine_Sort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_ID, Integer.valueOf(C_DocLine_Sort_ID));
	}

	/** Get Document Line Sorting Preferences.
		@return Document Line Sorting Preferences	  */
	@Override
	public int getC_DocLine_Sort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocLine_Sort_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}