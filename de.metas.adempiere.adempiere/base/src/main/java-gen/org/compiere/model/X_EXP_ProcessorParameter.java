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

/** Generated Model for EXP_ProcessorParameter
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_EXP_ProcessorParameter extends PO implements I_EXP_ProcessorParameter, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100614L;

    /** Standard Constructor */
    public X_EXP_ProcessorParameter (Properties ctx, int EXP_ProcessorParameter_ID, String trxName)
    {
      super (ctx, EXP_ProcessorParameter_ID, trxName);
      /** if (EXP_ProcessorParameter_ID == 0)
        {
			setEXP_Processor_ID (0);
			setEXP_ProcessorParameter_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_EXP_ProcessorParameter (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_EXP_ProcessorParameter[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_EXP_Processor getEXP_Processor() throws RuntimeException
    {
		return (org.compiere.model.I_EXP_Processor)MTable.get(getCtx(), org.compiere.model.I_EXP_Processor.Table_Name)
			.getPO(getEXP_Processor_ID(), get_TrxName());	}

	/** Set Export Processor.
		@param EXP_Processor_ID Export Processor	  */
	public void setEXP_Processor_ID (int EXP_Processor_ID)
	{
		if (EXP_Processor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_Processor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_Processor_ID, Integer.valueOf(EXP_Processor_ID));
	}

	/** Get Export Processor.
		@return Export Processor	  */
	public int getEXP_Processor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_Processor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processor Parameter.
		@param EXP_ProcessorParameter_ID Processor Parameter	  */
	public void setEXP_ProcessorParameter_ID (int EXP_ProcessorParameter_ID)
	{
		if (EXP_ProcessorParameter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_ProcessorParameter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EXP_ProcessorParameter_ID, Integer.valueOf(EXP_ProcessorParameter_ID));
	}

	/** Get Processor Parameter.
		@return Processor Parameter	  */
	public int getEXP_ProcessorParameter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_ProcessorParameter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
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

	/** Set Parameter Value.
		@param ParameterValue Parameter Value	  */
	public void setParameterValue (String ParameterValue)
	{
		set_Value (COLUMNNAME_ParameterValue, ParameterValue);
	}

	/** Get Parameter Value.
		@return Parameter Value	  */
	public String getParameterValue () 
	{
		return (String)get_Value(COLUMNNAME_ParameterValue);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}