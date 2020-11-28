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

/** Generated Model for PA_Achievement
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PA_Achievement extends PO implements I_PA_Achievement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PA_Achievement (Properties ctx, int PA_Achievement_ID, String trxName)
    {
      super (ctx, PA_Achievement_ID, trxName);
      /** if (PA_Achievement_ID == 0)
        {
			setIsAchieved (false);
			setManualActual (Env.ZERO);
			setName (null);
			setPA_Achievement_ID (0);
			setPA_Measure_ID (0);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_PA_Achievement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_PA_Achievement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Achieved.
		@param IsAchieved 
		The goal is achieved
	  */
	public void setIsAchieved (boolean IsAchieved)
	{
		set_Value (COLUMNNAME_IsAchieved, Boolean.valueOf(IsAchieved));
	}

	/** Get Achieved.
		@return The goal is achieved
	  */
	public boolean isAchieved () 
	{
		Object oo = get_Value(COLUMNNAME_IsAchieved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manual Actual.
		@param ManualActual 
		Manually entered actual value
	  */
	public void setManualActual (BigDecimal ManualActual)
	{
		set_Value (COLUMNNAME_ManualActual, ManualActual);
	}

	/** Get Manual Actual.
		@return Manually entered actual value
	  */
	public BigDecimal getManualActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ManualActual);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Achievement.
		@param PA_Achievement_ID 
		Performance Achievement
	  */
	public void setPA_Achievement_ID (int PA_Achievement_ID)
	{
		if (PA_Achievement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_Achievement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_Achievement_ID, Integer.valueOf(PA_Achievement_ID));
	}

	/** Get Achievement.
		@return Performance Achievement
	  */
	public int getPA_Achievement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Achievement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_PA_Measure getPA_Measure() throws RuntimeException
    {
		return (I_PA_Measure)MTable.get(getCtx(), I_PA_Measure.Table_Name)
			.getPO(getPA_Measure_ID(), get_TrxName());	}

	/** Set Measure.
		@param PA_Measure_ID 
		Concrete Performance Measurement
	  */
	public void setPA_Measure_ID (int PA_Measure_ID)
	{
		if (PA_Measure_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_Measure_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_Measure_ID, Integer.valueOf(PA_Measure_ID));
	}

	/** Get Measure.
		@return Concrete Performance Measurement
	  */
	public int getPA_Measure_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Measure_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}