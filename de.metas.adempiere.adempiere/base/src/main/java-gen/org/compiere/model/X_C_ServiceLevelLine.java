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

/** Generated Model for C_ServiceLevelLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_ServiceLevelLine extends PO implements I_C_ServiceLevelLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_ServiceLevelLine (Properties ctx, int C_ServiceLevelLine_ID, String trxName)
    {
      super (ctx, C_ServiceLevelLine_ID, trxName);
      /** if (C_ServiceLevelLine_ID == 0)
        {
			setC_ServiceLevel_ID (0);
			setC_ServiceLevelLine_ID (0);
			setServiceDate (new Timestamp( System.currentTimeMillis() ));
			setServiceLevelProvided (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_ServiceLevelLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_ServiceLevelLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ServiceLevel getC_ServiceLevel() throws RuntimeException
    {
		return (I_C_ServiceLevel)MTable.get(getCtx(), I_C_ServiceLevel.Table_Name)
			.getPO(getC_ServiceLevel_ID(), get_TrxName());	}

	/** Set Service Level.
		@param C_ServiceLevel_ID 
		Product Revenue Recognition Service Level 
	  */
	public void setC_ServiceLevel_ID (int C_ServiceLevel_ID)
	{
		if (C_ServiceLevel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ServiceLevel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ServiceLevel_ID, Integer.valueOf(C_ServiceLevel_ID));
	}

	/** Get Service Level.
		@return Product Revenue Recognition Service Level 
	  */
	public int getC_ServiceLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ServiceLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Service Level Line.
		@param C_ServiceLevelLine_ID 
		Product Revenue Recognition Service Level Line
	  */
	public void setC_ServiceLevelLine_ID (int C_ServiceLevelLine_ID)
	{
		if (C_ServiceLevelLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ServiceLevelLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ServiceLevelLine_ID, Integer.valueOf(C_ServiceLevelLine_ID));
	}

	/** Get Service Level Line.
		@return Product Revenue Recognition Service Level Line
	  */
	public int getC_ServiceLevelLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ServiceLevelLine_ID);
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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

	/** Set Service date.
		@param ServiceDate 
		Date service was provided
	  */
	public void setServiceDate (Timestamp ServiceDate)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceDate, ServiceDate);
	}

	/** Get Service date.
		@return Date service was provided
	  */
	public Timestamp getServiceDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ServiceDate);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getServiceDate()));
    }

	/** Set Quantity Provided.
		@param ServiceLevelProvided 
		Quantity of service or product provided
	  */
	public void setServiceLevelProvided (BigDecimal ServiceLevelProvided)
	{
		set_ValueNoCheck (COLUMNNAME_ServiceLevelProvided, ServiceLevelProvided);
	}

	/** Get Quantity Provided.
		@return Quantity of service or product provided
	  */
	public BigDecimal getServiceLevelProvided () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ServiceLevelProvided);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}