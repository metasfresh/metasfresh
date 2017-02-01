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
import org.compiere.model.*;

/** Generated Model for AD_Relation
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Relation extends PO implements I_AD_Relation, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110328L;

    /** Standard Constructor */
    public X_AD_Relation (Properties ctx, int AD_Relation_ID, String trxName)
    {
      super (ctx, AD_Relation_ID, trxName);
      /** if (AD_Relation_ID == 0)
        {
			setAD_Relation_ID (0);
			setAD_RelationType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Relation (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_AD_Relation[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Relation.
		@param AD_Relation_ID Relation	  */
	public void setAD_Relation_ID (int AD_Relation_ID)
	{
		if (AD_Relation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Relation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Relation_ID, Integer.valueOf(AD_Relation_ID));
	}

	/** Get Relation.
		@return Relation	  */
	public int getAD_Relation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Relation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_RelationType getAD_RelationType() throws RuntimeException
    {
		return (org.compiere.model.I_AD_RelationType)MTable.get(getCtx(), org.compiere.model.I_AD_RelationType.Table_Name)
			.getPO(getAD_RelationType_ID(), get_TrxName());	}

	/** Set Relation Type.
		@param AD_RelationType_ID Relation Type	  */
	public void setAD_RelationType_ID (int AD_RelationType_ID)
	{
		if (AD_RelationType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_RelationType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_RelationType_ID, Integer.valueOf(AD_RelationType_ID));
	}

	/** Get Relation Type.
		@return Relation Type	  */
	public int getAD_RelationType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_RelationType_ID);
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

	/** Set Quell-Datensatz-ID.
		@param Record_Source_ID Quell-Datensatz-ID	  */
	public void setRecord_Source_ID (int Record_Source_ID)
	{
		if (Record_Source_ID < 1) 
			set_Value (COLUMNNAME_Record_Source_ID, null);
		else 
			set_Value (COLUMNNAME_Record_Source_ID, Integer.valueOf(Record_Source_ID));
	}

	/** Get Quell-Datensatz-ID.
		@return Quell-Datensatz-ID	  */
	public int getRecord_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ziel-Datensatz-ID.
		@param Record_Target_ID Ziel-Datensatz-ID	  */
	public void setRecord_Target_ID (int Record_Target_ID)
	{
		if (Record_Target_ID < 1) 
			set_Value (COLUMNNAME_Record_Target_ID, null);
		else 
			set_Value (COLUMNNAME_Record_Target_ID, Integer.valueOf(Record_Target_ID));
	}

	/** Get Ziel-Datensatz-ID.
		@return Ziel-Datensatz-ID	  */
	public int getRecord_Target_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_Target_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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