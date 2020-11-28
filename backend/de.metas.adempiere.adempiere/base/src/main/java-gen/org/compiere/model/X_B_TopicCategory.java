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

/** Generated Model for B_TopicCategory
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_B_TopicCategory extends PO implements I_B_TopicCategory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_B_TopicCategory (Properties ctx, int B_TopicCategory_ID, String trxName)
    {
      super (ctx, B_TopicCategory_ID, trxName);
      /** if (B_TopicCategory_ID == 0)
        {
			setB_TopicCategory_ID (0);
			setB_TopicType_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_B_TopicCategory (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_B_TopicCategory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Topic Category.
		@param B_TopicCategory_ID 
		Auction Topic Category
	  */
	public void setB_TopicCategory_ID (int B_TopicCategory_ID)
	{
		if (B_TopicCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_B_TopicCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_B_TopicCategory_ID, Integer.valueOf(B_TopicCategory_ID));
	}

	/** Get Topic Category.
		@return Auction Topic Category
	  */
	public int getB_TopicCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_TopicCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_B_TopicType getB_TopicType() throws RuntimeException
    {
		return (I_B_TopicType)MTable.get(getCtx(), I_B_TopicType.Table_Name)
			.getPO(getB_TopicType_ID(), get_TrxName());	}

	/** Set Topic Type.
		@param B_TopicType_ID 
		Auction Topic Type
	  */
	public void setB_TopicType_ID (int B_TopicType_ID)
	{
		if (B_TopicType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_B_TopicType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_B_TopicType_ID, Integer.valueOf(B_TopicType_ID));
	}

	/** Get Topic Type.
		@return Auction Topic Type
	  */
	public int getB_TopicType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_TopicType_ID);
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