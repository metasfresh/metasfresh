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

/** Generated Model for CM_WebProject
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_WebProject extends PO implements I_CM_WebProject, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_WebProject (Properties ctx, int CM_WebProject_ID, String trxName)
    {
      super (ctx, CM_WebProject_ID, trxName);
      /** if (CM_WebProject_ID == 0)
        {
			setCM_WebProject_ID (0);
			setMeta_Author (null);
// @AD_User_Name@
			setMeta_Content (null);
// 'text/html; charset=UTF-8'
			setMeta_Copyright (null);
// @AD_Client_Name@
			setMeta_Publisher (null);
// @AD_Client_Name@
			setMeta_RobotsTag (null);
// 'INDEX,FOLLOW'
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_WebProject (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_WebProject[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Tree getAD_TreeCMC() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_TreeCMC_ID(), get_TrxName());	}

	/** Set Container Tree.
		@param AD_TreeCMC_ID 
		Container Tree
	  */
	public void setAD_TreeCMC_ID (int AD_TreeCMC_ID)
	{
		if (AD_TreeCMC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMC_ID, Integer.valueOf(AD_TreeCMC_ID));
	}

	/** Get Container Tree.
		@return Container Tree
	  */
	public int getAD_TreeCMC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_TreeCMC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_TreeCMM() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_TreeCMM_ID(), get_TrxName());	}

	/** Set Media Tree.
		@param AD_TreeCMM_ID 
		Media Tree
	  */
	public void setAD_TreeCMM_ID (int AD_TreeCMM_ID)
	{
		if (AD_TreeCMM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMM_ID, Integer.valueOf(AD_TreeCMM_ID));
	}

	/** Get Media Tree.
		@return Media Tree
	  */
	public int getAD_TreeCMM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_TreeCMM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_TreeCMS() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_TreeCMS_ID(), get_TrxName());	}

	/** Set Stage Tree.
		@param AD_TreeCMS_ID 
		Stage Tree
	  */
	public void setAD_TreeCMS_ID (int AD_TreeCMS_ID)
	{
		if (AD_TreeCMS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMS_ID, Integer.valueOf(AD_TreeCMS_ID));
	}

	/** Get Stage Tree.
		@return Stage Tree
	  */
	public int getAD_TreeCMS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_TreeCMS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_TreeCMT() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_TreeCMT_ID(), get_TrxName());	}

	/** Set Template Tree.
		@param AD_TreeCMT_ID 
		Template Tree
	  */
	public void setAD_TreeCMT_ID (int AD_TreeCMT_ID)
	{
		if (AD_TreeCMT_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMT_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_TreeCMT_ID, Integer.valueOf(AD_TreeCMT_ID));
	}

	/** Get Template Tree.
		@return Template Tree
	  */
	public int getAD_TreeCMT_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_TreeCMT_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Project.
		@param CM_WebProject_ID 
		A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public void setCM_WebProject_ID (int CM_WebProject_ID)
	{
		if (CM_WebProject_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_WebProject_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_WebProject_ID, Integer.valueOf(CM_WebProject_ID));
	}

	/** Get Web Project.
		@return A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public int getCM_WebProject_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_WebProject_ID);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Meta Author.
		@param Meta_Author 
		Author of the content
	  */
	public void setMeta_Author (String Meta_Author)
	{
		set_Value (COLUMNNAME_Meta_Author, Meta_Author);
	}

	/** Get Meta Author.
		@return Author of the content
	  */
	public String getMeta_Author () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Author);
	}

	/** Set Meta Content Type.
		@param Meta_Content 
		Defines the type of content i.e. "text/html; charset=UTF-8"
	  */
	public void setMeta_Content (String Meta_Content)
	{
		set_Value (COLUMNNAME_Meta_Content, Meta_Content);
	}

	/** Get Meta Content Type.
		@return Defines the type of content i.e. "text/html; charset=UTF-8"
	  */
	public String getMeta_Content () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Content);
	}

	/** Set Meta Copyright.
		@param Meta_Copyright 
		Contains Copyright information for the content
	  */
	public void setMeta_Copyright (String Meta_Copyright)
	{
		set_Value (COLUMNNAME_Meta_Copyright, Meta_Copyright);
	}

	/** Get Meta Copyright.
		@return Contains Copyright information for the content
	  */
	public String getMeta_Copyright () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Copyright);
	}

	/** Set Meta Publisher.
		@param Meta_Publisher 
		Meta Publisher defines the publisher of the content
	  */
	public void setMeta_Publisher (String Meta_Publisher)
	{
		set_Value (COLUMNNAME_Meta_Publisher, Meta_Publisher);
	}

	/** Get Meta Publisher.
		@return Meta Publisher defines the publisher of the content
	  */
	public String getMeta_Publisher () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Publisher);
	}

	/** Set Meta RobotsTag.
		@param Meta_RobotsTag 
		RobotsTag defines how search robots should handle this content
	  */
	public void setMeta_RobotsTag (String Meta_RobotsTag)
	{
		set_Value (COLUMNNAME_Meta_RobotsTag, Meta_RobotsTag);
	}

	/** Get Meta RobotsTag.
		@return RobotsTag defines how search robots should handle this content
	  */
	public String getMeta_RobotsTag () 
	{
		return (String)get_Value(COLUMNNAME_Meta_RobotsTag);
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