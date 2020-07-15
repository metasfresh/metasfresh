/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.adempiere.model;

/** Generated Model - DO NOT CHANGE */


import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterRouting
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterRouting extends org.compiere.model.PO implements I_AD_PrinterRouting, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 938499557L;

    /** Standard Constructor */
    public X_AD_PrinterRouting (Properties ctx, int AD_PrinterRouting_ID, String trxName)
    {
      super (ctx, AD_PrinterRouting_ID, trxName);
      /** if (AD_PrinterRouting_ID == 0)
        {
			setAD_Printer_ID (0);
			setAD_PrinterRouting_ID (0);
			setIsDirectPrint (null); // N
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_PrinterRouting
        } */
    }

    /** Load Constructor */
    public X_AD_PrinterRouting (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.adempiere.model.I_AD_Printer getAD_Printer()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Printer_ID, de.metas.adempiere.model.I_AD_Printer.class);
	}

	@Override
	public void setAD_Printer(de.metas.adempiere.model.I_AD_Printer AD_Printer)
	{
		set_ValueFromPO(COLUMNNAME_AD_Printer_ID, de.metas.adempiere.model.I_AD_Printer.class, AD_Printer);
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

	/** Set Drucker-Zuordnung.
		@param AD_PrinterRouting_ID Drucker-Zuordnung	  */
	@Override
	public void setAD_PrinterRouting_ID (int AD_PrinterRouting_ID)
	{
		if (AD_PrinterRouting_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_PrinterRouting_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_PrinterRouting_ID, Integer.valueOf(AD_PrinterRouting_ID));
	}

	/** Get Drucker-Zuordnung.
		@return Drucker-Zuordnung	  */
	@Override
	public int getAD_PrinterRouting_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterRouting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID
		Prozess oder Bericht
	  */
	@Override
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
	@Override
	public int getAD_Process_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set User/Contact.
		@param AD_User_ID
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_Value (COLUMNNAME_AD_User_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegart.
		@param C_DocType_ID
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
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
	@Override
	public int getC_DocType_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
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

	/**
	 * IsDirectPrint AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISDIRECTPRINT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISDIRECTPRINT_Yes = "Y";
	/** No = N */
	public static final String ISDIRECTPRINT_No = "N";
	/** Set Direct print.
		@param IsDirectPrint
		Print without dialog
	  */
	@Override
	public void setIsDirectPrint (java.lang.String IsDirectPrint)
	{

		set_Value (COLUMNNAME_IsDirectPrint, IsDirectPrint);
	}

	/** Get Direct print.
		@return Print without dialog
	  */
	@Override
	public java.lang.String getIsDirectPrint ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_IsDirectPrint);
	}

	/** Set Letzte Seiten.
		@param LastPages Letzte Seiten	  */
	@Override
	public void setLastPages (int LastPages)
	{
		set_Value (COLUMNNAME_LastPages, Integer.valueOf(LastPages));
	}

	/** Get Letzte Seiten.
		@return Letzte Seiten	  */
	@Override
	public int getLastPages ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LastPages);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}