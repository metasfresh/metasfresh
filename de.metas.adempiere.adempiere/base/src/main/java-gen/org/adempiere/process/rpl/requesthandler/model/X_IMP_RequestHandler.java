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

/** Generated Model for IMP_RequestHandler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_IMP_RequestHandler extends org.compiere.model.PO implements I_IMP_RequestHandler, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 66269131L;

    /** Standard Constructor */
    public X_IMP_RequestHandler (Properties ctx, int IMP_RequestHandler_ID, String trxName)
    {
      super (ctx, IMP_RequestHandler_ID, trxName);
      /** if (IMP_RequestHandler_ID == 0)
        {
			setIMP_RequestHandler_ID (0);
        } */
    }

    /** Load Constructor */
    public X_IMP_RequestHandler (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_IMP_RequestHandler[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	@Override
	public org.compiere.model.I_EXP_Format getEXP_Format() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_EXP_Format_ID, org.compiere.model.I_EXP_Format.class);
	}

	@Override
	public void setEXP_Format(org.compiere.model.I_EXP_Format EXP_Format)
	{
		set_ValueFromPO(COLUMNNAME_EXP_Format_ID, org.compiere.model.I_EXP_Format.class, EXP_Format);
	}

	/** Set Export Format.
		@param EXP_Format_ID Export Format	  */
	@Override
	public void setEXP_Format_ID (int EXP_Format_ID)
	{
		if (EXP_Format_ID < 1) 
			set_Value (COLUMNNAME_EXP_Format_ID, null);
		else 
			set_Value (COLUMNNAME_EXP_Format_ID, Integer.valueOf(EXP_Format_ID));
	}

	/** Get Export Format.
		@return Export Format	  */
	@Override
	public int getEXP_Format_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_Format_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request handler.
		@param IMP_RequestHandler_ID Request handler	  */
	@Override
	public void setIMP_RequestHandler_ID (int IMP_RequestHandler_ID)
	{
		if (IMP_RequestHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_IMP_RequestHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_IMP_RequestHandler_ID, Integer.valueOf(IMP_RequestHandler_ID));
	}

	/** Get Request handler.
		@return Request handler	  */
	@Override
	public int getIMP_RequestHandler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IMP_RequestHandler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType getIMP_RequestHandlerType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_IMP_RequestHandlerType_ID, org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType.class);
	}

	@Override
	public void setIMP_RequestHandlerType(org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType IMP_RequestHandlerType)
	{
		set_ValueFromPO(COLUMNNAME_IMP_RequestHandlerType_ID, org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType.class, IMP_RequestHandlerType);
	}

	/** Set Request handler type.
		@param IMP_RequestHandlerType_ID Request handler type	  */
	@Override
	public void setIMP_RequestHandlerType_ID (int IMP_RequestHandlerType_ID)
	{
		if (IMP_RequestHandlerType_ID < 1) 
			set_Value (COLUMNNAME_IMP_RequestHandlerType_ID, null);
		else 
			set_Value (COLUMNNAME_IMP_RequestHandlerType_ID, Integer.valueOf(IMP_RequestHandlerType_ID));
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

	/** Set SuchschlÃ¼ssel.
		@param Value 
		SuchschlÃ¼ssel fÃ¼r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get SuchschlÃ¼ssel.
		@return SuchschlÃ¼ssel fÃ¼r den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}