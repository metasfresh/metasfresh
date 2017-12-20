/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * User Model
 *
 * @author Jorg Janke
 * @version $Id: MUser.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2788430 ] MUser.getOfBPartner add trxName parameter
 *         https://sourceforge.net/tracker/index.php?func=detail&aid=2788430&group_id=176962&atid=879335
 */
@Deprecated
public class MUser extends X_AD_User
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7768871335027448920L;

	public MUser(Properties ctx, int AD_User_ID, String trxName)
	{
		super(ctx, AD_User_ID, trxName);	// 0 is also System
		if (is_new())
		{
			setIsFullBPAccess(true);
			setNotificationType(NOTIFICATIONTYPE_EMail);
		}
	}	// MUser

	public MUser(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MUser

	/**
	 * Get Value - 7 bit lower case alpha numerics max length 8
	 * 
	 * @return value
	 */
	@Override
	public String getValue()
	{
		String s = super.getValue();
		if (s != null)
			return s;
		setValue(null);
		return super.getValue();
	}	// getValue

	/**
	 * Set Value - 7 bit lower case alpha numerics max length 8
	 * 
	 * @param Value
	 */
	@Override
	public void setValue(String Value)
	{
		if (Value == null || Value.length() == 0)
			Value = getName();
		if (Value == null || Value.length() == 0)
			Value = "noname";
		//
		String result = cleanValue(Value);
		if (result.length() > 8)
		{
			String first = getName(Value, true);
			String last = getName(Value, false);
			if (last.length() > 0)
			{
				String temp = last;
				if (first.length() > 0)
					temp = first.substring(0, 1) + last;
				result = cleanValue(temp);
			}
			else
				result = cleanValue(first);
		}
		if (result.length() > 8)
			result = result.substring(0, 8);
		super.setValue(result);
	}	// setValue

	/**
	 * Clean Value
	 * 
	 * @param value value
	 * @return lower case cleaned value
	 */
	private String cleanValue(String value)
	{
		char[] chars = value.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++)
		{
			char ch = chars[i];
			ch = Character.toLowerCase(ch);
			if ((ch >= '0' && ch <= '9')		// digits
					|| (ch >= 'a' && ch <= 'z'))	// characters
				sb.append(ch);
		}
		return sb.toString();
	}	// cleanValue

	/**
	 * Get First/Last Name
	 * 
	 * @param name name
	 * @param getFirst if true first name is returned
	 * @return first/last name
	 */
	private String getName(String name, boolean getFirst)
	{
		if (name == null || name.length() == 0)
			return "";
		String first = null;
		String last = null;
		// Janke, Jorg R - Jorg R Janke
		// double names not handled gracefully nor titles
		// nor (former) aristrocratic world de/la/von
		boolean lastFirst = name.indexOf(',') != -1;
		StringTokenizer st = null;
		if (lastFirst)
			st = new StringTokenizer(name, ",");
		else
			st = new StringTokenizer(name, " ");
		while (st.hasMoreTokens())
		{
			String s = st.nextToken().trim();
			if (lastFirst)
			{
				if (last == null)
					last = s;
				else if (first == null)
					first = s;
			}
			else
			{
				if (first == null)
					first = s;
				else
					last = s;
			}
		}
		if (getFirst)
		{
			if (first == null)
				return "";
			return first.trim();
		}
		if (last == null)
			return "";
		return last.trim();
	}	// getName

	/**
	 * String Representation
	 * 
	 * @return Info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MUser[")
				.append(get_ID())
				.append(",Name=").append(getName())
				.append(",EMailUserID=").append(getEMailUser())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Set EMail - reset validation
	 * 
	 * @param EMail email
	 */
	@Override
	public void setEMail(String EMail)
	{
		super.setEMail(EMail);
		setEMailVerifyDate(null);
	}	// setEMail

	/**
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// New Address invalidates verification
		if (!newRecord && is_ValueChanged("EMail"))
			setEMailVerifyDate(null);
		if (newRecord || super.getValue() == null || is_ValueChanged("Value"))
			setValue(super.getValue());
		return true;
	}	// beforeSave
}	// MUser
