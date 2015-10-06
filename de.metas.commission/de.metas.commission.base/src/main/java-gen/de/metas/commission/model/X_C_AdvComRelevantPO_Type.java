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

/** Generated Model for C_AdvComRelevantPO_Type
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComRelevantPO_Type extends PO implements I_C_AdvComRelevantPO_Type, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComRelevantPO_Type (Properties ctx, int C_AdvComRelevantPO_Type_ID, String trxName)
    {
      super (ctx, C_AdvComRelevantPO_Type_ID, trxName);
      /** if (C_AdvComRelevantPO_Type_ID == 0)
        {
			setC_AdvCommissionRelevantPO_ID (0);
			setC_AdvComRelevantPO_Type_ID (0);
			setC_AdvComSystem_ID (0);
			setC_AdvComSystem_Type_ID (0);
			setIsProcessImmediately (false);
// N
			setSeqNo (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_AdvComRelevantPO_Type (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComRelevantPO_Type[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvCommissionRelevantPO getC_AdvCommissionRelevantPO() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionRelevantPO)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionRelevantPO.Table_Name)
			.getPO(getC_AdvCommissionRelevantPO_ID(), get_TrxName());	}

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

	/** Set C_AdvComRelevantPO_Type.
		@param C_AdvComRelevantPO_Type_ID C_AdvComRelevantPO_Type	  */
	public void setC_AdvComRelevantPO_Type_ID (int C_AdvComRelevantPO_Type_ID)
	{
		if (C_AdvComRelevantPO_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComRelevantPO_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComRelevantPO_Type_ID, Integer.valueOf(C_AdvComRelevantPO_Type_ID));
	}

	/** Get C_AdvComRelevantPO_Type.
		@return C_AdvComRelevantPO_Type	  */
	public int getC_AdvComRelevantPO_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRelevantPO_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSystem)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSystem.Table_Name)
			.getPO(getC_AdvComSystem_ID(), get_TrxName());	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
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
			set_Value (COLUMNNAME_C_AdvComSystem_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComSystem_Type_ID, Integer.valueOf(C_AdvComSystem_Type_ID));
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

	/** Set Sofort verarbeiten.
		@param IsProcessImmediately Sofort verarbeiten	  */
	public void setIsProcessImmediately (boolean IsProcessImmediately)
	{
		set_Value (COLUMNNAME_IsProcessImmediately, Boolean.valueOf(IsProcessImmediately));
	}

	/** Get Sofort verarbeiten.
		@return Sofort verarbeiten	  */
	public boolean isProcessImmediately () 
	{
		Object oo = get_Value(COLUMNNAME_IsProcessImmediately);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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