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

/** Generated Model for C_ChargeType_DocType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_ChargeType_DocType extends PO implements I_C_ChargeType_DocType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_ChargeType_DocType (Properties ctx, int C_ChargeType_DocType_ID, String trxName)
    {
      super (ctx, C_ChargeType_DocType_ID, trxName);
      /** if (C_ChargeType_DocType_ID == 0)
        {
			setC_ChargeType_ID (0);
			setC_DocType_ID (0);
			setIsAllowNegative (true);
// Y
			setIsAllowPositive (true);
// Y
        } */
    }

    /** Load Constructor */
    public X_C_ChargeType_DocType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_ChargeType_DocType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ChargeType getC_ChargeType() throws RuntimeException
    {
		return (I_C_ChargeType)MTable.get(getCtx(), I_C_ChargeType.Table_Name)
			.getPO(getC_ChargeType_ID(), get_TrxName());	}

	/** Set Charge Type.
		@param C_ChargeType_ID Charge Type	  */
	public void setC_ChargeType_ID (int C_ChargeType_ID)
	{
		if (C_ChargeType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ChargeType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ChargeType_ID, Integer.valueOf(C_ChargeType_ID));
	}

	/** Get Charge Type.
		@return Charge Type	  */
	public int getC_ChargeType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ChargeType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Allow Negative.
		@param IsAllowNegative Allow Negative	  */
	public void setIsAllowNegative (boolean IsAllowNegative)
	{
		set_Value (COLUMNNAME_IsAllowNegative, Boolean.valueOf(IsAllowNegative));
	}

	/** Get Allow Negative.
		@return Allow Negative	  */
	public boolean isAllowNegative () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowNegative);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Positive.
		@param IsAllowPositive Allow Positive	  */
	public void setIsAllowPositive (boolean IsAllowPositive)
	{
		set_Value (COLUMNNAME_IsAllowPositive, Boolean.valueOf(IsAllowPositive));
	}

	/** Get Allow Positive.
		@return Allow Positive	  */
	public boolean isAllowPositive () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowPositive);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}