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
package org.adempiere.appdict.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_JavaClass_Type
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_JavaClass_Type extends org.compiere.model.PO implements I_AD_JavaClass_Type, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1585863616L;

    /** Standard Constructor */
    public X_AD_JavaClass_Type (Properties ctx, int AD_JavaClass_Type_ID, String trxName)
    {
      super (ctx, AD_JavaClass_Type_ID, trxName);
      /** if (AD_JavaClass_Type_ID == 0)
        {
			setAD_EntityType_ID (0);
			setAD_JavaClass_Type_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_JavaClass_Type (Properties ctx, ResultSet rs, String trxName)
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
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_JavaClass_Type[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_EntityType getAD_EntityType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_EntityType_ID, org.compiere.model.I_AD_EntityType.class);
	}

	@Override
	public void setAD_EntityType(org.compiere.model.I_AD_EntityType AD_EntityType)
	{
		set_ValueFromPO(COLUMNNAME_AD_EntityType_ID, org.compiere.model.I_AD_EntityType.class, AD_EntityType);
	}

	/** Set Entitäts-Art.
		@param AD_EntityType_ID 
		Systementitäts-Art
	  */
	@Override
	public void setAD_EntityType_ID (int AD_EntityType_ID)
	{
		if (AD_EntityType_ID < 1) 
			set_Value (COLUMNNAME_AD_EntityType_ID, null);
		else 
			set_Value (COLUMNNAME_AD_EntityType_ID, Integer.valueOf(AD_EntityType_ID));
	}

	/** Get Entitäts-Art.
		@return Systementitäts-Art
	  */
	@Override
	public int getAD_EntityType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_EntityType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Java Class Type.
		@param AD_JavaClass_Type_ID Java Class Type	  */
	@Override
	public void setAD_JavaClass_Type_ID (int AD_JavaClass_Type_ID)
	{
		if (AD_JavaClass_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_JavaClass_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_JavaClass_Type_ID, Integer.valueOf(AD_JavaClass_Type_ID));
	}

	/** Get Java Class Type.
		@return Java Class Type	  */
	@Override
	public int getAD_JavaClass_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Java-Klasse.
		@param Classname Java-Klasse	  */
	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Java-Klasse.
		@return Java-Klasse	  */
	@Override
	public java.lang.String getClassname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}