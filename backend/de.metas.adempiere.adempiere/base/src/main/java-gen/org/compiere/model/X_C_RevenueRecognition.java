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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_RevenueRecognition
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_RevenueRecognition extends PO implements I_C_RevenueRecognition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_RevenueRecognition (Properties ctx, int C_RevenueRecognition_ID, String trxName)
    {
      super (ctx, C_RevenueRecognition_ID, trxName);
      /** if (C_RevenueRecognition_ID == 0)
        {
			setC_RevenueRecognition_ID (0);
			setIsTimeBased (false);
			setName (null);
			setRecognitionFrequency (null);
        } */
    }

    /** Load Constructor */
    public X_C_RevenueRecognition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_RevenueRecognition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Revenue Recognition.
		@param C_RevenueRecognition_ID 
		Method for recording revenue
	  */
	public void setC_RevenueRecognition_ID (int C_RevenueRecognition_ID)
	{
		if (C_RevenueRecognition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RevenueRecognition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RevenueRecognition_ID, Integer.valueOf(C_RevenueRecognition_ID));
	}

	/** Get Revenue Recognition.
		@return Method for recording revenue
	  */
	public int getC_RevenueRecognition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RevenueRecognition_ID);
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

	/** Set Time based.
		@param IsTimeBased 
		Time based Revenue Recognition rather than Service Level based
	  */
	public void setIsTimeBased (boolean IsTimeBased)
	{
		set_Value (COLUMNNAME_IsTimeBased, Boolean.valueOf(IsTimeBased));
	}

	/** Get Time based.
		@return Time based Revenue Recognition rather than Service Level based
	  */
	public boolean isTimeBased () 
	{
		Object oo = get_Value(COLUMNNAME_IsTimeBased);
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

	/** Set Number of Months.
		@param NoMonths Number of Months	  */
	public void setNoMonths (int NoMonths)
	{
		set_Value (COLUMNNAME_NoMonths, Integer.valueOf(NoMonths));
	}

	/** Get Number of Months.
		@return Number of Months	  */
	public int getNoMonths () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NoMonths);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** RecognitionFrequency AD_Reference_ID=196 */
	public static final int RECOGNITIONFREQUENCY_AD_Reference_ID=196;
	/** Month = M */
	public static final String RECOGNITIONFREQUENCY_Month = "M";
	/** Quarter = Q */
	public static final String RECOGNITIONFREQUENCY_Quarter = "Q";
	/** Year = Y */
	public static final String RECOGNITIONFREQUENCY_Year = "Y";
	/** Set Recognition frequency.
		@param RecognitionFrequency Recognition frequency	  */
	public void setRecognitionFrequency (String RecognitionFrequency)
	{

		set_Value (COLUMNNAME_RecognitionFrequency, RecognitionFrequency);
	}

	/** Get Recognition frequency.
		@return Recognition frequency	  */
	public String getRecognitionFrequency () 
	{
		return (String)get_Value(COLUMNNAME_RecognitionFrequency);
	}
}