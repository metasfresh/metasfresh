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
package de.metas.commission.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_AdvCommissionRelevantPO
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommissionRelevantPO extends PO implements I_C_AdvCommissionRelevantPO, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommissionRelevantPO (Properties ctx, int C_AdvCommissionRelevantPO_ID, String trxName)
    {
      super (ctx, C_AdvCommissionRelevantPO_ID, trxName);
      /** if (C_AdvCommissionRelevantPO_ID == 0)
        {
			setAD_Reference_ID (0);
			setC_AdvCommissionRelevantPO_ID (0);
			setDateDocColumn_ID (0);
			setInfo (false);
// N
			setName (null);
			setSeqNo (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionRelevantPO (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionRelevantPO[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Referenz.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getBPartnerColumn() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
			.getPO(getBPartnerColumn_ID(), get_TrxName());	}

	/** Set Geschäftspartner-Spalte.
		@param BPartnerColumn_ID 
		Spalte (der Referenz-Tabelle), aus der der Geschäftspartner entnommen werden soll
	  */
	public void setBPartnerColumn_ID (int BPartnerColumn_ID)
	{
		if (BPartnerColumn_ID < 1) 
			set_Value (COLUMNNAME_BPartnerColumn_ID, null);
		else 
			set_Value (COLUMNNAME_BPartnerColumn_ID, Integer.valueOf(BPartnerColumn_ID));
	}

	/** Get Geschäftspartner-Spalte.
		@return Spalte (der Referenz-Tabelle), aus der der Geschäftspartner entnommen werden soll
	  */
	public int getBPartnerColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BPartnerColumn_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Def. relevanter Datensatz.
		@param C_AdvCommissionRelevantPO_ID Def. relevanter Datensatz	  */
	public void setC_AdvCommissionRelevantPO_ID (int C_AdvCommissionRelevantPO_ID)
	{
		if (C_AdvCommissionRelevantPO_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionRelevantPO_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionRelevantPO_ID, Integer.valueOf(C_AdvCommissionRelevantPO_ID));
	}

	/** Get Def. relevanter Datensatz.
		@return Def. relevanter Datensatz	  */
	public int getC_AdvCommissionRelevantPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionRelevantPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Column getDateDocColumn() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
			.getPO(getDateDocColumn_ID(), get_TrxName());	}

	/** Set DateDocColumn_ID.
		@param DateDocColumn_ID 
		Spalte (der Referenz-Tabelle), aus der das Buchungdatum entnommen werden soll.
	  */
	public void setDateDocColumn_ID (int DateDocColumn_ID)
	{
		if (DateDocColumn_ID < 1) 
			set_Value (COLUMNNAME_DateDocColumn_ID, null);
		else 
			set_Value (COLUMNNAME_DateDocColumn_ID, Integer.valueOf(DateDocColumn_ID));
	}

	/** Get DateDocColumn_ID.
		@return Spalte (der Referenz-Tabelle), aus der das Buchungdatum entnommen werden soll.
	  */
	public int getDateDocColumn_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DateDocColumn_ID);
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

	/** Set Info.
		@param Info 
		Information
	  */
	public void setInfo (boolean Info)
	{
		set_Value (COLUMNNAME_Info, Boolean.valueOf(Info));
	}

	/** Get Info.
		@return Information
	  */
	public boolean isInfo () 
	{
		Object oo = get_Value(COLUMNNAME_Info);
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}