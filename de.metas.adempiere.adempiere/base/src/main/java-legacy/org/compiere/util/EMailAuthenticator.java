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
package org.compiere.util;

import java.io.Serializable;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/**
 * Email User Authentification
 *
 * @author Jorg Janke
 * @version $Id: EMailAuthenticator.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
public class EMailAuthenticator extends Authenticator implements Serializable
{
	private static final long serialVersionUID = -1453447115062744524L;
	
	public static final EMailAuthenticator of(final String username, final String password)
	{
		return new EMailAuthenticator(username, password);
	}

	/** Password */
	private final PasswordAuthentication passwordAuthenticator;

	/**
	 * Constructor
	 *
	 * @param username user name
	 * @param password user password
	 */
	private EMailAuthenticator(final String username, final String password)
	{
		super();
		
		Check.assumeNotEmpty(username, "username not empty");
		Check.assumeNotEmpty(password, "password not empty");
		passwordAuthenticator = new PasswordAuthentication(username, password);
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return passwordAuthenticator;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("username", passwordAuthenticator.getUserName())
				.add("password", "********")
				.toString();
	}

}
