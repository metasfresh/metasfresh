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

/** Generated Model for R_MailText
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_R_MailText extends PO implements I_R_MailText, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_R_MailText (Properties ctx, int R_MailText_ID, String trxName)
    {
      super (ctx, R_MailText_ID, trxName);
      /** if (R_MailText_ID == 0)
        {
			setIsHtml (false);
			setMailText (null);
			setName (null);
			setR_MailText_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_MailText (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_R_MailText[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set HTML.
		@param IsHtml 
		Text has HTML tags
	  */
	public void setIsHtml (boolean IsHtml)
	{
		set_Value (COLUMNNAME_IsHtml, Boolean.valueOf(IsHtml));
	}

	/** Get HTML.
		@return Text has HTML tags
	  */
	public boolean isHtml () 
	{
		Object oo = get_Value(COLUMNNAME_IsHtml);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Subject.
		@param MailHeader 
		Mail Header (Subject)
	  */
	public void setMailHeader (String MailHeader)
	{
		set_Value (COLUMNNAME_MailHeader, MailHeader);
	}

	/** Get Subject.
		@return Mail Header (Subject)
	  */
	public String getMailHeader () 
	{
		return (String)get_Value(COLUMNNAME_MailHeader);
	}

	/** Set Mail Text.
		@param MailText 
		Text used for Mail message
	  */
	public void setMailText (String MailText)
	{
		set_Value (COLUMNNAME_MailText, MailText);
	}

	/** Get Mail Text.
		@return Text used for Mail message
	  */
	public String getMailText () 
	{
		return (String)get_Value(COLUMNNAME_MailText);
	}

	/** Set Mail Text 2.
		@param MailText2 
		Optional second text part used for Mail message
	  */
	public void setMailText2 (String MailText2)
	{
		set_Value (COLUMNNAME_MailText2, MailText2);
	}

	/** Get Mail Text 2.
		@return Optional second text part used for Mail message
	  */
	public String getMailText2 () 
	{
		return (String)get_Value(COLUMNNAME_MailText2);
	}

	/** Set Mail Text 3.
		@param MailText3 
		Optional third text part used for Mail message
	  */
	public void setMailText3 (String MailText3)
	{
		set_Value (COLUMNNAME_MailText3, MailText3);
	}

	/** Get Mail Text 3.
		@return Optional third text part used for Mail message
	  */
	public String getMailText3 () 
	{
		return (String)get_Value(COLUMNNAME_MailText3);
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

	/** Set Mail Template.
		@param R_MailText_ID 
		Text templates for mailings
	  */
	public void setR_MailText_ID (int R_MailText_ID)
	{
		if (R_MailText_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_MailText_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_MailText_ID, Integer.valueOf(R_MailText_ID));
	}

	/** Get Mail Template.
		@return Text templates for mailings
	  */
	public int getR_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}