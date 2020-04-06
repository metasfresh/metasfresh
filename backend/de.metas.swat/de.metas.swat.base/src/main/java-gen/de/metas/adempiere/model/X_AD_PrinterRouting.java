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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for AD_PrinterRouting
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#424 - $Id$ */
public class X_AD_PrinterRouting extends PO implements I_AD_PrinterRouting, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110714L;

    /** Standard Constructor */
    public X_AD_PrinterRouting (Properties ctx, int AD_PrinterRouting_ID, String trxName)
    {
      super (ctx, AD_PrinterRouting_ID, trxName);
      /** if (AD_PrinterRouting_ID == 0)
        {
			setAD_PrinterRouting_ID (0);
			setAD_Printer_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_PrinterRouting (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_PrinterRouting[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set System Printer Routing.
		@param AD_PrinterRouting_ID System Printer Routing	  */
	public void setAD_PrinterRouting_ID (int AD_PrinterRouting_ID)
	{
		if (AD_PrinterRouting_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterRouting_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterRouting_ID, Integer.valueOf(AD_PrinterRouting_ID));
	}

	/** Get System Printer Routing.
		@return System Printer Routing	  */
	public int getAD_PrinterRouting_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterRouting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Printer getAD_Printer() throws RuntimeException
    {
		return (I_AD_Printer)MTable.get(getCtx(), I_AD_Printer.Table_Name)
			.getPO(getAD_Printer_ID(), get_TrxName());	}

	/** Set System Printer.
		@param AD_Printer_ID System Printer	  */
	public void setAD_Printer_ID (int AD_Printer_ID)
	{
		if (AD_Printer_ID < 1) 
			set_Value (COLUMNNAME_AD_Printer_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Printer_ID, Integer.valueOf(AD_Printer_ID));
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

	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Process)MTable.get(getCtx(), org.compiere.model.I_AD_Process.Table_Name)
			.getPO(getAD_Process_ID(), get_TrxName());	}

	/** Set Prozess.
		@param AD_Process_ID 
		Prozess oder Bericht
	  */
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Prozess oder Bericht
	  */
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Role)MTable.get(getCtx(), org.compiere.model.I_AD_Role.Table_Name)
			.getPO(getAD_Role_ID(), get_TrxName());	}

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
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
}