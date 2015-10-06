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

/** Generated Model for CM_Media_Server
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_Media_Server extends PO implements I_CM_Media_Server, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_Media_Server (Properties ctx, int CM_Media_Server_ID, String trxName)
    {
      super (ctx, CM_Media_Server_ID, trxName);
      /** if (CM_Media_Server_ID == 0)
        {
			setCM_Media_Server_ID (0);
			setCM_WebProject_ID (0);
			setIsPassive (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_Media_Server (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_Media_Server[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Media Server.
		@param CM_Media_Server_ID 
		Media Server list to which content should get transfered
	  */
	public void setCM_Media_Server_ID (int CM_Media_Server_ID)
	{
		if (CM_Media_Server_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Media_Server_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Media_Server_ID, Integer.valueOf(CM_Media_Server_ID));
	}

	/** Get Media Server.
		@return Media Server list to which content should get transfered
	  */
	public int getCM_Media_Server_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Media_Server_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_WebProject getCM_WebProject() throws RuntimeException
    {
		return (I_CM_WebProject)MTable.get(getCtx(), I_CM_WebProject.Table_Name)
			.getPO(getCM_WebProject_ID(), get_TrxName());	}

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

	/** Set Folder.
		@param Folder 
		A folder on a local or remote system to store data into
	  */
	public void setFolder (String Folder)
	{
		set_Value (COLUMNNAME_Folder, Folder);
	}

	/** Get Folder.
		@return A folder on a local or remote system to store data into
	  */
	public String getFolder () 
	{
		return (String)get_Value(COLUMNNAME_Folder);
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

	/** Set IP Address.
		@param IP_Address 
		Defines the IP address to transfer data to
	  */
	public void setIP_Address (String IP_Address)
	{
		set_Value (COLUMNNAME_IP_Address, IP_Address);
	}

	/** Get IP Address.
		@return Defines the IP address to transfer data to
	  */
	public String getIP_Address () 
	{
		return (String)get_Value(COLUMNNAME_IP_Address);
	}

	/** Set Transfer passive.
		@param IsPassive 
		FTP passive transfer
	  */
	public void setIsPassive (boolean IsPassive)
	{
		set_Value (COLUMNNAME_IsPassive, Boolean.valueOf(IsPassive));
	}

	/** Get Transfer passive.
		@return FTP passive transfer
	  */
	public boolean isPassive () 
	{
		Object oo = get_Value(COLUMNNAME_IsPassive);
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

	/** Set Password.
		@param Password 
		Password of any length (case sensitive)
	  */
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Password.
		@return Password of any length (case sensitive)
	  */
	public String getPassword () 
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL () 
	{
		return (String)get_Value(COLUMNNAME_URL);
	}

	/** Set Registered EMail.
		@param UserName 
		Email of the responsible for the System
	  */
	public void setUserName (String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get Registered EMail.
		@return Email of the responsible for the System
	  */
	public String getUserName () 
	{
		return (String)get_Value(COLUMNNAME_UserName);
	}
}