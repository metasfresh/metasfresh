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
package org.compiere.util;

import java.util.logging.Level;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *  Email User Authentification
 *
 *  @author Jorg Janke
 *  @version $Id: EMailAuthenticator.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
public class EMailAuthenticator extends Authenticator
{
	/**
	 * 	Constructor
	 * 	@param username user name
	 * 	@param password user password
	 */
	public EMailAuthenticator (String username, String password)
	{
		m_pass = new PasswordAuthentication (username, password);
		if (username == null || username.length() == 0)
		{
			log.log(Level.SEVERE, "Username is NULL");
			Thread.dumpStack();
		}
		if (password == null || password.length() == 0)
		{
			log.log(Level.SEVERE, "Password is NULL");
			Thread.dumpStack();
		}
	}	//	EMailAuthenticator

	/**	Password		*/
	private PasswordAuthentication 	m_pass = null;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(EMailAuthenticator.class);

	/**
	 *	Ger PasswordAuthentication
	 * 	@return Password Autnetifucation
	 */
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return m_pass;
	}	//	getPasswordAuthentication

	/**
	 * 	Get String representation
	 * 	@return info
	 */
	public String toString()
	{
		if (m_pass == null)
			return "EMailAuthenticator[]";
		if (CLogMgt.isLevelFinest())
			return "EMailAuthenticator[" 
				+ m_pass.getUserName() + "/" + m_pass.getPassword() + "]";
		return "EMailAuthenticator[" 
			+ m_pass.getUserName() + "/************]";
	}	//	toString

}	//	EMailAuthenticator
