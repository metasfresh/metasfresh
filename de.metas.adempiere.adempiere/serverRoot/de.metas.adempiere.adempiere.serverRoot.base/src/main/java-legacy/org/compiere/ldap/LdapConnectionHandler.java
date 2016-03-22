/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.
 * This program is free software; you can redistribute it and/or modify it
 * under the terms version 2 of the GNU General Public License as published
 * by the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 * You may reach us at: ComPiere, Inc. - http://www.compiere.org/license.html
 * 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA or info@compiere.org 
 *****************************************************************************/
package org.compiere.ldap;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.ldap.InitialLdapContext;

import org.compiere.model.MLdapProcessor;
import org.compiere.model.MLdapUser;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * 	LDAP Connection Handler
 *	
 *  Only "simple" authentication and the following protocol are supported:
 *		bind
 *  	unbind
 *  	search
 *  The following distinguished name are supported:
 *  	o - organization
 *  	ou - organization unit
 *  	cn - common name
 *  Due to some of the ldap client might not unbind and close the connection,
 *  whenever error occurs and authenticate done, we will close the connection.
 *  
 *  Basically, tested with two type of ldap authentication, java client and
 *  apache ldap support. 
 *  For the apache support, here's the tested definition:
 *  	AuthType Basic
 *  	AuthLDAPAuthoritative on
 *		AuthLDAPEnabled on
 *		AuthLDAPURL ldap://<ip address>:<port no>/o=<organization>,ou=<organization unit>?uid?sub
 *  The protocol for the apache ldap:
 *  	- bind to server
 *  	- search for the object name with user input userid
 *  	- bind again with returned object name and password
 *  The protocol for the java client, please refer to the sample code in main().
 * 
 *  @author Jorg Janke
 *  @version $Id: LdapConnectionHandler.java,v 1.1 2006/10/09 00:23:16 jjanke Exp $
 */
public class LdapConnectionHandler extends Thread
{
	/**
	 * 	Ldap Connection Handler
	 *	@param socket server socket
	 *	@param model model
	 */
	public LdapConnectionHandler(Socket socket, MLdapProcessor model)
	{
		try
		{
			m_socket = socket;
			m_socket.setTcpNoDelay(true);	//	should not be required
			m_model = model;
		}
		catch (Exception e)
		{
			log.error("", e);
		}	//	no timeout
	}	//	LdapConnectionHandler
	
	/** Socket				*/
	private Socket m_socket = null;
	/** Ldap Model			*/
	private MLdapProcessor m_model = null;
	/**	Logger	*/
	private static Logger log = LogManager.getLogger(LdapConnectionHandler.class);
	
	
	/**
	 * 	Do Work
	 */
	@Override
	public void run()
	{
		try
		{
			if (m_socket == null || m_socket.isClosed())
				return;
			
			LdapMessage msg = new LdapMessage();
			MLdapUser ldapUser = new MLdapUser();
			LdapResult result = new LdapResult();
			boolean activeSession = true;
			while (activeSession)
			{
				InputStream in = m_socket.getInputStream();
				BufferedOutputStream out = new BufferedOutputStream(m_socket.getOutputStream());
				//	Read
				byte[] buffer = new byte[512];
				int length = in.read(buffer, 0, 512);
				
				// Decode the input message buffer
				result.reset(msg, ldapUser);
				msg.reset(result);
				msg.decode(buffer, length);
				if (msg.getOperation() == LdapMessage.UNBIND_REQUEST)
				{
					out.close();
					break;
				}
				
				// Not unbind, so we can create a response 
				byte[] bytes = result.getResult(m_model);
				
				// Send the response back
				out.write(bytes);
				out.flush();
				
				// If there's error or successfully authenticated the user,
				// close the connection to avoid too many open connection
				if (result.getDone())
				{
					out.close();
					break;
				}
			}  // while(activeSession)
		}
		catch (IOException e)
		{
			log.error("", e);
		}
		
		try
		{
			m_socket.close();
		}
		catch (Exception e)
		{
			log.warn("Socket", e);
		}
		m_socket = null;
	}	//	run

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("LdapConnectionHandler[");
		sb.append (hashCode()).append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 * 	Test using the java client.
	 *  Ldap v3 won't need to do any bind, search, bind anymore.  
	 *  When new InitialLdapContext() is called, it will bind with the
	 *  dn and password, the ldap server should be authenticate with it.
	 *  
	 *	@param args
	 */
	public static void main(String[] args)
	{
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		//	ldap://dc.compiere.org
		env.put(Context.PROVIDER_URL, "ldap://10.104.139.160:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		// Compiere server only support cn/o/ou, and cn should be the user id.
		// Only one entry for cn.
		env.put(Context.SECURITY_PRINCIPAL, "cn=cboss@compiere.org,o=GardenWorld,ou=LawnCare");
		env.put(Context.SECURITY_CREDENTIALS, "carlboss");
		
		try
		{
			// Create the initial context
			new InitialLdapContext(env, null);
			// If not successfully authenticated, exception should be thrown 
			System.out.println("Successfully authenticated ...");
		}
		catch (AuthenticationException e)
		{
			e.printStackTrace();
			return;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		    return;
		}
	}  // main()
}	//	LdapConnectionHandler
