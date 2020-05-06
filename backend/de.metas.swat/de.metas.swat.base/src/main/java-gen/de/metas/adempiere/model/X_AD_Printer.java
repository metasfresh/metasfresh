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
package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Printer
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#424 - $Id$ */
public class X_AD_Printer extends PO implements I_AD_Printer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110714L;

    /** Standard Constructor */
    public X_AD_Printer (Properties ctx, int AD_Printer_ID, String trxName)
    {
      super (ctx, AD_Printer_ID, trxName);
      /** if (AD_Printer_ID == 0)
        {
			setAD_Printer_ID (0);
			setPrinterName (null);
			setPrinterType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Printer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Printer[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set System Printer.
		@param AD_Printer_ID System Printer	  */
	public void setAD_Printer_ID (int AD_Printer_ID)
	{
		if (AD_Printer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Printer_ID, Integer.valueOf(AD_Printer_ID));
	}

	/** Get System Printer.
		@return System Printer	  */
	public int getAD_Printer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Printer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** IsDirectPrint AD_Reference_ID=319 */
	public static final int ISDIRECTPRINT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISDIRECTPRINT_Yes = "Y";
	/** No = N */
	public static final String ISDIRECTPRINT_No = "N";
	/** Set Direct print.
		@param IsDirectPrint 
		Print without dialog
	  */
	public void setIsDirectPrint (String IsDirectPrint)
	{

		set_Value (COLUMNNAME_IsDirectPrint, IsDirectPrint);
	}

	/** Get Direct print.
		@return Print without dialog
	  */
	public String getIsDirectPrint () 
	{
		return (String)get_Value(COLUMNNAME_IsDirectPrint);
	}

	/** Set Drucker.
		@param PrinterName 
		Name of the Printer
	  */
	public void setPrinterName (String PrinterName)
	{
		set_Value (COLUMNNAME_PrinterName, PrinterName);
	}

	/** Get Drucker.
		@return Name of the Printer
	  */
	public String getPrinterName () 
	{
		return (String)get_Value(COLUMNNAME_PrinterName);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getPrinterName());
    }

	/** PrinterType AD_Reference_ID=540227 */
	public static final int PRINTERTYPE_AD_Reference_ID=540227;
	/** General = G */
	public static final String PRINTERTYPE_General = "G";
	/** Fax = F */
	public static final String PRINTERTYPE_Fax = "F";
	/** Label = L */
	public static final String PRINTERTYPE_Label = "L";
	/** Set Printer Type.
		@param PrinterType Printer Type	  */
	public void setPrinterType (String PrinterType)
	{

		set_Value (COLUMNNAME_PrinterType, PrinterType);
	}

	/** Get Printer Type.
		@return Printer Type	  */
	public String getPrinterType () 
	{
		return (String)get_Value(COLUMNNAME_PrinterType);
	}
}
