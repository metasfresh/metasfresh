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
package org.adempiere.process.rpl.requesthandler.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for IMP_RequestHandlerType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_IMP_RequestHandlerType extends org.compiere.model.PO implements I_IMP_RequestHandlerType, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1537740447L;

    /** Standard Constructor */
    public X_IMP_RequestHandlerType (Properties ctx, int IMP_RequestHandlerType_ID, String trxName)
    {
      super (ctx, IMP_RequestHandlerType_ID, trxName);
      /** if (IMP_RequestHandlerType_ID == 0)
        {
			setClassname (null);
			setEntityType (null);
			setIMP_RequestHandlerType_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_IMP_RequestHandlerType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_IMP_RequestHandlerType[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set EntitÃ¤ts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get EntitÃ¤ts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Request handler type.
		@param IMP_RequestHandlerType_ID Request handler type	  */
	@Override
	public void setIMP_RequestHandlerType_ID (int IMP_RequestHandlerType_ID)
	{
		if (IMP_RequestHandlerType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IMP_RequestHandlerType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IMP_RequestHandlerType_ID, Integer.valueOf(IMP_RequestHandlerType_ID));
	}

	/** Get Request handler type.
		@return Request handler type	  */
	@Override
	public int getIMP_RequestHandlerType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IMP_RequestHandlerType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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