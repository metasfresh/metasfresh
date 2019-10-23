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

/** Generated Model for AD_Val_Rule_Included
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Val_Rule_Included extends PO implements I_AD_Val_Rule_Included, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120917L;

    /** Standard Constructor */
    public X_AD_Val_Rule_Included (Properties ctx, int AD_Val_Rule_Included_ID, String trxName)
    {
      super (ctx, AD_Val_Rule_Included_ID, trxName);
      /** if (AD_Val_Rule_Included_ID == 0)
        {
			setAD_Val_Rule_Included_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Val_Rule_Included (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_AD_Val_Rule_Included[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
    {
		return (I_AD_Val_Rule)MTable.get(getCtx(), I_AD_Val_Rule.Table_Name)
			.getPO(getAD_Val_Rule_ID(), get_TrxName());	}

	/** Set Dynamische Validierung.
		@param AD_Val_Rule_ID 
		Regel für die  dynamische Validierung
	  */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamische Validierung.
		@return Regel für die  dynamische Validierung
	  */
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Val_Rule_Included.
		@param AD_Val_Rule_Included_ID AD_Val_Rule_Included	  */
	public void setAD_Val_Rule_Included_ID (int AD_Val_Rule_Included_ID)
	{
		if (AD_Val_Rule_Included_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_Included_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Val_Rule_Included_ID, Integer.valueOf(AD_Val_Rule_Included_ID));
	}

	/** Get AD_Val_Rule_Included.
		@return AD_Val_Rule_Included	  */
	public int getAD_Val_Rule_Included_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_Included_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Included_Val_Rule_ID.
		@param Included_Val_Rule_ID 
		Validation rule to be included.
	  */
	public void setIncluded_Val_Rule_ID (int Included_Val_Rule_ID)
	{
		if (Included_Val_Rule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Included_Val_Rule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Included_Val_Rule_ID, Integer.valueOf(Included_Val_Rule_ID));
	}

	/** Get Included_Val_Rule_ID.
		@return Validation rule to be included.
	  */
	public int getIncluded_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Included_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public void setSeqNo (String SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	public String getSeqNo () 
	{
		return (String)get_Value(COLUMNNAME_SeqNo);
	}
}