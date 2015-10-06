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
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Printer_Tray
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Printer_Tray extends org.compiere.model.PO implements I_AD_Printer_Tray, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -308556475L;

    /** Standard Constructor */
    public X_AD_Printer_Tray (Properties ctx, int AD_Printer_Tray_ID, String trxName)
    {
      super (ctx, AD_Printer_Tray_ID, trxName);
      /** if (AD_Printer_Tray_ID == 0)
        {
			setAD_Printer_ID (0);
			setAD_Printer_Tray_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Printer_Tray (Properties ctx, ResultSet rs, String trxName)
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
	public I_AD_Printer getAD_Printer() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_ID, I_AD_Printer.class);
	}

	@Override
	public void setAD_Printer(I_AD_Printer AD_Printer)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_ID, I_AD_Printer.class, AD_Printer);
	}

	/** Set Logischer Drucker.
		@param AD_Printer_ID Logischer Drucker	  */
	@Override
	public void setAD_Printer_ID (int AD_Printer_ID)
	{
		if (AD_Printer_ID < 1) 
			set_Value (COLUMNNAME_AD_Printer_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Printer_ID, Integer.valueOf(AD_Printer_ID));
	}

	/** Get Logischer Drucker.
		@return Logischer Drucker	  */
	@Override
	public int getAD_Printer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Logischer Schacht.
		@param AD_Printer_Tray_ID Logischer Schacht	  */
	@Override
	public void setAD_Printer_Tray_ID (int AD_Printer_Tray_ID)
	{
		if (AD_Printer_Tray_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Tray_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_Tray_ID, Integer.valueOf(AD_Printer_Tray_ID));
	}

	/** Get Logischer Schacht.
		@return Logischer Schacht	  */
	@Override
	public int getAD_Printer_Tray_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_Tray_ID);
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
}