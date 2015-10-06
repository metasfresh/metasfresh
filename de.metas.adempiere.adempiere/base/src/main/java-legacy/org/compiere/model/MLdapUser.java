/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

/*
 * MLdapUser, data object stores the user information such as userid,
 * password, organization and so on.
 */
public class MLdapUser 
{
	/** organization */
	private String org = null;
	/** organization unit */
	private String orgUnit = null;
	/** user password */
	private String passwd = null;
	/** user id */
	private String userId = null;
	/** error string */
	private String errStr = null;
	
	public MLdapUser()
	{
	}
	
	/*
	 * Reset attributes
	 */
	public void reset()
	{
		org = null;
		orgUnit = null;
		passwd = null;
		userId = null;
		errStr = null;
	}  // reset()
	
	/**
	 * Set the organization
	 * @param org organization
	 */
	public void setOrg(String org)
	{
		this.org = org;
	}  // setOrg()
	
	/**
	 * Set the organization unit
	 * @param orgUnit organization unit
	 */
	public void setOrgUnit(String orgUnit)
	{
		this.orgUnit = orgUnit;
	}  // setOrg()
	
	/**
	 * Set the user password
	 * @param passwd User password string
	 */
	public void setPassword(String passwd)
	{
		this.passwd = passwd;
	}  // setPassword()
	
	/**
	 * Set the user id
	 * @param passwd User id string
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}  // setUserId()
	
	/**
	 * Set the error String
	 * @param errStr Error String
	 */
	public void setErrorString(String errStr)
	{
		this.errStr = errStr;
	}  // setErrorStr()
	
	/**
	 * Get the organization
	 * @return org organization
	 */
	public String getOrg()
	{
		return org;
	}  // getOrg()
	
	/**
	 * Get the organization unit
	 * @return orgUnit organization unit
	 */
	public String getOrgUnit()
	{
		return orgUnit;
	}  // getOrgUnit()
	
	/**
	 * Get the user password
	 * @return passwd User password string
	 */
	public String getPassword()
	{
		return passwd;
	}  // getPassword()
	
	/**
	 * Get the user id
	 * @return User id string
	 */
	public String getUserId()
	{
		return userId;
	}  // getUserId()
	
	/**
	 * Get the error string
	 * @return errStr Error String
	 */
	public String getErrorMsg()
	{
		return errStr;
	}  // getErrorString()
}  // MLdapUser
