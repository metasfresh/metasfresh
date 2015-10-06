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

/** Generated Model for C_AdvCommissionSalaryGroup
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommissionSalaryGroup extends PO implements I_C_AdvCommissionSalaryGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommissionSalaryGroup (Properties ctx, int C_AdvCommissionSalaryGroup_ID, String trxName)
    {
      super (ctx, C_AdvCommissionSalaryGroup_ID, trxName);
      /** if (C_AdvCommissionSalaryGroup_ID == 0)
        {
			setC_AdvCommissionSalaryGroup_ID (0);
			setC_AdvComRankCollection_ID (0);
			setName (null);
			setSeqNo (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionSalaryGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionSalaryGroup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Vergütungsgruppe.
		@param C_AdvCommissionSalaryGroup_ID Vergütungsgruppe	  */
	public void setC_AdvCommissionSalaryGroup_ID (int C_AdvCommissionSalaryGroup_ID)
	{
		if (C_AdvCommissionSalaryGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, Integer.valueOf(C_AdvCommissionSalaryGroup_ID));
	}

	/** Get Vergütungsgruppe.
		@return Vergütungsgruppe	  */
	public int getC_AdvCommissionSalaryGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionSalaryGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvComRankCollection getC_AdvComRankCollection() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComRankCollection)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComRankCollection.Table_Name)
			.getPO(getC_AdvComRankCollection_ID(), get_TrxName());	}

	/** Set Vergütungsgruppensammlung.
		@param C_AdvComRankCollection_ID Vergütungsgruppensammlung	  */
	public void setC_AdvComRankCollection_ID (int C_AdvComRankCollection_ID)
	{
		if (C_AdvComRankCollection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComRankCollection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComRankCollection_ID, Integer.valueOf(C_AdvComRankCollection_ID));
	}

	/** Get Vergütungsgruppensammlung.
		@return Vergütungsgruppensammlung	  */
	public int getC_AdvComRankCollection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRankCollection_ID);
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

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}