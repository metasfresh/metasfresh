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

/** Generated Model for K_Comment
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_K_Comment extends PO implements I_K_Comment, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_K_Comment (Properties ctx, int K_Comment_ID, String trxName)
    {
      super (ctx, K_Comment_ID, trxName);
      /** if (K_Comment_ID == 0)
        {
			setIsPublic (true);
// Y
			setK_Comment_ID (0);
			setK_Entry_ID (0);
			setRating (0);
			setTextMsg (null);
        } */
    }

    /** Load Constructor */
    public X_K_Comment (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_K_Comment[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Session getAD_Session() throws RuntimeException
    {
		return (I_AD_Session)MTable.get(getCtx(), I_AD_Session.Table_Name)
			.getPO(getAD_Session_ID(), get_TrxName());	}

	/** Set Session.
		@param AD_Session_ID 
		User Session Online or Web
	  */
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	/** Get Session.
		@return User Session Online or Web
	  */
	public int getAD_Session_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Public.
		@param IsPublic 
		Public can read entry
	  */
	public void setIsPublic (boolean IsPublic)
	{
		set_Value (COLUMNNAME_IsPublic, Boolean.valueOf(IsPublic));
	}

	/** Get Public.
		@return Public can read entry
	  */
	public boolean isPublic () 
	{
		Object oo = get_Value(COLUMNNAME_IsPublic);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Entry Comment.
		@param K_Comment_ID 
		Knowledge Entry Comment
	  */
	public void setK_Comment_ID (int K_Comment_ID)
	{
		if (K_Comment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_K_Comment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_K_Comment_ID, Integer.valueOf(K_Comment_ID));
	}

	/** Get Entry Comment.
		@return Knowledge Entry Comment
	  */
	public int getK_Comment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_Comment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getK_Comment_ID()));
    }

	public I_K_Entry getK_Entry() throws RuntimeException
    {
		return (I_K_Entry)MTable.get(getCtx(), I_K_Entry.Table_Name)
			.getPO(getK_Entry_ID(), get_TrxName());	}

	/** Set Entry.
		@param K_Entry_ID 
		Knowledge Entry
	  */
	public void setK_Entry_ID (int K_Entry_ID)
	{
		if (K_Entry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_K_Entry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_K_Entry_ID, Integer.valueOf(K_Entry_ID));
	}

	/** Get Entry.
		@return Knowledge Entry
	  */
	public int getK_Entry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_Entry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rating.
		@param Rating 
		Classification or Importance
	  */
	public void setRating (int Rating)
	{
		set_Value (COLUMNNAME_Rating, Integer.valueOf(Rating));
	}

	/** Get Rating.
		@return Classification or Importance
	  */
	public int getRating () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Rating);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Text Message.
		@param TextMsg 
		Text Message
	  */
	public void setTextMsg (String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Text Message.
		@return Text Message
	  */
	public String getTextMsg () 
	{
		return (String)get_Value(COLUMNNAME_TextMsg);
	}
}