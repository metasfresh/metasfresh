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
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_POProcessor
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#303 - $Id$ */
public class X_AD_POProcessor extends PO implements I_AD_POProcessor, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110617L;

    /** Standard Constructor */
    public X_AD_POProcessor (Properties ctx, int AD_POProcessor_ID, String trxName)
    {
      super (ctx, AD_POProcessor_ID, trxName);
      /** if (AD_POProcessor_ID == 0)
        {
			setAD_POProcessor_ID (0);
			setClassname (null);
			setEntityType (null);
// U
			setIsProcessDirectly (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_POProcessor (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_AD_POProcessor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set PO Processor.
		@param AD_POProcessor_ID PO Processor	  */
	public void setAD_POProcessor_ID (int AD_POProcessor_ID)
	{
		if (AD_POProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_POProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_POProcessor_ID, Integer.valueOf(AD_POProcessor_ID));
	}

	/** Get PO Processor.
		@return PO Processor	  */
	public int getAD_POProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_POProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Table getAD_Table_Source() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_Source_ID(), get_TrxName());	}

	/** Set Quell-Tabelle.
		@param AD_Table_Source_ID Quell-Tabelle	  */
	public void setAD_Table_Source_ID (int AD_Table_Source_ID)
	{
		if (AD_Table_Source_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_Source_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_Source_ID, Integer.valueOf(AD_Table_Source_ID));
	}

	/** Get Quell-Tabelle.
		@return Quell-Tabelle	  */
	public int getAD_Table_Source_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_Source_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Classname.
		@param Classname 
		Java Classname
	  */
	public void setClassname (String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Classname.
		@return Java Classname
	  */
	public String getClassname () 
	{
		return (String)get_Value(COLUMNNAME_Classname);
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

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitaets-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitaets-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Sofort verarbeiten.
		@param IsProcessDirectly Sofort verarbeiten	  */
	public void setIsProcessDirectly (boolean IsProcessDirectly)
	{
		set_Value (COLUMNNAME_IsProcessDirectly, Boolean.valueOf(IsProcessDirectly));
	}

	/** Get Sofort verarbeiten.
		@return Sofort verarbeiten	  */
	public boolean isProcessDirectly () 
	{
		Object oo = get_Value(COLUMNNAME_IsProcessDirectly);
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
}