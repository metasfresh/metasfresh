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

/** Generated Model for AD_PrintLabel
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_PrintLabel extends PO implements I_AD_PrintLabel, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_PrintLabel (Properties ctx, int AD_PrintLabel_ID, String trxName)
    {
      super (ctx, AD_PrintLabel_ID, trxName);
      /** if (AD_PrintLabel_ID == 0)
        {
			setAD_LabelPrinter_ID (0);
			setAD_PrintLabel_ID (0);
			setAD_Table_ID (0);
			setIsLandscape (false);
			setLabelHeight (0);
			setLabelWidth (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_PrintLabel (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_PrintLabel[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Label printer.
		@param AD_LabelPrinter_ID 
		Label Printer Definition
	  */
	public void setAD_LabelPrinter_ID (int AD_LabelPrinter_ID)
	{
		if (AD_LabelPrinter_ID < 1) 
			set_Value (COLUMNNAME_AD_LabelPrinter_ID, null);
		else 
			set_Value (COLUMNNAME_AD_LabelPrinter_ID, Integer.valueOf(AD_LabelPrinter_ID));
	}

	/** Get Label printer.
		@return Label Printer Definition
	  */
	public int getAD_LabelPrinter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_LabelPrinter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Print Label.
		@param AD_PrintLabel_ID 
		Label Format to print
	  */
	public void setAD_PrintLabel_ID (int AD_PrintLabel_ID)
	{
		if (AD_PrintLabel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrintLabel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrintLabel_ID, Integer.valueOf(AD_PrintLabel_ID));
	}

	/** Get Print Label.
		@return Label Format to print
	  */
	public int getAD_PrintLabel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintLabel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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

	/** Set Landscape.
		@param IsLandscape 
		Landscape orientation
	  */
	public void setIsLandscape (boolean IsLandscape)
	{
		set_Value (COLUMNNAME_IsLandscape, Boolean.valueOf(IsLandscape));
	}

	/** Get Landscape.
		@return Landscape orientation
	  */
	public boolean isLandscape () 
	{
		Object oo = get_Value(COLUMNNAME_IsLandscape);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Label Height.
		@param LabelHeight 
		Height of the label
	  */
	public void setLabelHeight (int LabelHeight)
	{
		set_Value (COLUMNNAME_LabelHeight, Integer.valueOf(LabelHeight));
	}

	/** Get Label Height.
		@return Height of the label
	  */
	public int getLabelHeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LabelHeight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Label Width.
		@param LabelWidth 
		Width of the Label
	  */
	public void setLabelWidth (int LabelWidth)
	{
		set_Value (COLUMNNAME_LabelWidth, Integer.valueOf(LabelWidth));
	}

	/** Get Label Width.
		@return Width of the Label
	  */
	public int getLabelWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LabelWidth);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Printer Name.
		@param PrinterName 
		Name of the Printer
	  */
	public void setPrinterName (String PrinterName)
	{
		set_Value (COLUMNNAME_PrinterName, PrinterName);
	}

	/** Get Printer Name.
		@return Name of the Printer
	  */
	public String getPrinterName () 
	{
		return (String)get_Value(COLUMNNAME_PrinterName);
	}
}