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
import org.compiere.util.KeyNamePair;

/** Generated Model for CM_ChatType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_ChatType extends PO implements I_CM_ChatType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_ChatType (Properties ctx, int CM_ChatType_ID, String trxName)
    {
      super (ctx, CM_ChatType_ID, trxName);
      /** if (CM_ChatType_ID == 0)
        {
			setAD_Table_ID (0);
			setCM_ChatType_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_ChatType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_ChatType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Chat Type.
		@param CM_ChatType_ID 
		Type of discussion / chat
	  */
	public void setCM_ChatType_ID (int CM_ChatType_ID)
	{
		if (CM_ChatType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_ChatType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_ChatType_ID, Integer.valueOf(CM_ChatType_ID));
	}

	/** Get Chat Type.
		@return Type of discussion / chat
	  */
	public int getCM_ChatType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatType_ID);
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

	/** ModerationType AD_Reference_ID=395 */
	public static final int MODERATIONTYPE_AD_Reference_ID=395;
	/** Not moderated = N */
	public static final String MODERATIONTYPE_NotModerated = "N";
	/** Before Publishing = B */
	public static final String MODERATIONTYPE_BeforePublishing = "B";
	/** After Publishing = A */
	public static final String MODERATIONTYPE_AfterPublishing = "A";
	/** Set Moderation Type.
		@param ModerationType 
		Type of moderation
	  */
	public void setModerationType (String ModerationType)
	{

		set_Value (COLUMNNAME_ModerationType, ModerationType);
	}

	/** Get Moderation Type.
		@return Type of moderation
	  */
	public String getModerationType () 
	{
		return (String)get_Value(COLUMNNAME_ModerationType);
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