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

/** Generated Model for C_AdvComInstanceParam
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComInstanceParam extends PO implements I_C_AdvComInstanceParam, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComInstanceParam (Properties ctx, int C_AdvComInstanceParam_ID, String trxName)
    {
      super (ctx, C_AdvComInstanceParam_ID, trxName);
      /** if (C_AdvComInstanceParam_ID == 0)
        {
			setAD_Reference_ID (0);
			setC_AdvComInstanceParam_ID (0);
			setC_AdvComSystem_Type_ID (0);
// 0
			setDisplayName (null);
			setName (null);
			setParamValue (null);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComInstanceParam (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComInstanceParam[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set AD_Reference_ID.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get AD_Reference_ID.
		@return Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_Value_ID(), get_TrxName());	}

	/** Set AD_Reference_Value_ID.
		@param AD_Reference_Value_ID 
		Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get AD_Reference_Value_ID.
		@return Muss definiert werden, wenn die Validierungsart Tabelle oder Liste ist.
	  */
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_AdvComInstanceParam_ID.
		@param C_AdvComInstanceParam_ID C_AdvComInstanceParam_ID	  */
	public void setC_AdvComInstanceParam_ID (int C_AdvComInstanceParam_ID)
	{
		if (C_AdvComInstanceParam_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComInstanceParam_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComInstanceParam_ID, Integer.valueOf(C_AdvComInstanceParam_ID));
	}

	/** Get C_AdvComInstanceParam_ID.
		@return C_AdvComInstanceParam_ID	  */
	public int getC_AdvComInstanceParam_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComInstanceParam_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSystem_Type)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSystem_Type.Table_Name)
			.getPO(getC_AdvComSystem_Type_ID(), get_TrxName());	}

	/** Set Vergütungsplan - Provisionsart.
		@param C_AdvComSystem_Type_ID Vergütungsplan - Provisionsart	  */
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID)
	{
		if (C_AdvComSystem_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_Type_ID, Integer.valueOf(C_AdvComSystem_Type_ID));
	}

	/** Get Vergütungsplan - Provisionsart.
		@return Vergütungsplan - Provisionsart	  */
	public int getC_AdvComSystem_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_Type_ID);
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

	/** Set Name (Anzeige).
		@param DisplayName Name (Anzeige)	  */
	public void setDisplayName (String DisplayName)
	{
		set_Value (COLUMNNAME_DisplayName, DisplayName);
	}

	/** Get Name (Anzeige).
		@return Name (Anzeige)	  */
	public String getDisplayName () 
	{
		return (String)get_Value(COLUMNNAME_DisplayName);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set ParamValue.
		@param ParamValue ParamValue	  */
	public void setParamValue (String ParamValue)
	{
		set_Value (COLUMNNAME_ParamValue, ParamValue);
	}

	/** Get ParamValue.
		@return ParamValue	  */
	public String getParamValue () 
	{
		return (String)get_Value(COLUMNNAME_ParamValue);
	}

	/** Set SeqNo.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get SeqNo.
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