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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;

import org.compiere.interfaces.Status;

/**
 *	Apps Server Connection Test
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ConnectTest.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public class ConnectTest
{
	/**
	 * 	Connection Test Constructor
	 * 	@param serverName server name or IP
	 */
	public ConnectTest (String serverName)
	{
		System.out.println("ConnectTest: " + serverName);
		System.out.println();
		//
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		env.put(InitialContext.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		env.put(InitialContext.PROVIDER_URL, serverName);
	//	env.put(InitialContext.SECURITY_PROTOCOL, "");				//	"ssl"
	//	env.put(InitialContext.SECURITY_AUTHENTICATION, "none");	//	"none", "simple", "strong"
	//	env.put(InitialContext.SECURITY_PRINCIPAL, "");
	//	env.put(InitialContext.SECURITY_CREDENTIALS, "");

		//	Get Context
		System.out.println ("Creating context ...");
		System.out.println ("  " + env);
		InitialContext context = null;
		try
		{
			context = new InitialContext(env);
		}
		catch (Exception e)
		{
			System.err.println("ERROR: Could not create context: " + e);
			return;
		}

		testJNP (serverName, context);
		testEJB (serverName, context);

	}	//	ConnectTest

	/**
	 * 	Test JNP
	 * 	@param serverName server name
	 *  @param context context
	 */
	private void testJNP (String serverName, InitialContext context)
	{
		//	Connect to MBean
		System.out.println();
		System.out.println ("Connecting to MBean ...");
		/**
		try
		{
			String connectorName = "jmx:" + serverName + ":rmi";
			RMIAdaptor server = (RMIAdaptor) context.lookup (connectorName);
			System.out.println("- have Server");
			System.out.println("- Default Domain=" + server.getDefaultDomain());
			System.out.println("- MBeanCount = " + server.getMBeanCount());

	//		ObjectName serviceName = new ObjectName ("Adempiere:service=AdempiereCtrl");
	//		System.out.println("- " + serviceName + " is registered=" + server.isRegistered(serviceName));

	//		System.out.println("  - AdempiereSummary= "
	//				+ server.getAttribute(serviceName, "AdempiereSummary"));

			Object[] params = {};
			String[] signature = {};
		}
		catch (Exception e)
		{
			System.err.println("ERROR: Could not contact MBean: " + e);
			return;
		}
		**/

		//	List Context
		System.out.println();
		System.out.println(" Examining context ....");
		try
		{
			System.out.println("  Namespace=" + context.getNameInNamespace());
			System.out.println("  Environment=" + context.getEnvironment());
			System.out.println("  Context '/':");
			NamingEnumeration ne = context.list("/");
			while (ne.hasMore())
				System.out.println("  - " + ne.nextElement());
			//
			System.out.println("  Context 'ejb':");
			ne = context.list("ejb");
			while (ne.hasMore())
				System.out.println("  - " + ne.nextElement());
			//
			System.out.println("  Context 'ejb/adempiere':");
			ne = context.list("ejb/adempiere");
			while (ne.hasMore())
				System.out.println("  - " + ne.nextElement());
		}
		catch (Exception e)
		{
			System.err.println("ERROR: Could not examine context: " + e);
			return;
		}
	}	//	testJNP

	/**
	 * 	Test EJB
	 * 	@param serverName server name
	 *  @param context context
	 */
	private void testEJB (String serverName, InitialContext context)
	{
		System.out.println();
		System.out.println ("Connecting to EJB server ...");
		try
		{
			System.out.println("  Name=" + Status.JNDI_NAME);
			Status sta = (Status)context.lookup (Status.JNDI_NAME);
			System.out.println("  .. bean created");
			System.out.println("  ServerVersion=" + sta.getMainVersion() + " " + sta.getDateVersion());
			System.out.println("  .. bean removed");
		}
		catch (Exception e)
		{
			System.err.println("ERROR: Could not connect: " + e);
			return;
		}

		System.out.println();
		System.out.println("SUCCESS !!");
	}	//	testEJB


	/**************************************************************************
	 * 	Start Method
	 *  @param args serverName
	 */
	public static void main(String[] args)
	{
		String serverName = null;
		if (args.length > 0)
			serverName = args[0];
		if (serverName == null || serverName.length() == 0)
		{
			try
			{
				serverName = InetAddress.getLocalHost().getHostName();
			}
			catch (UnknownHostException ex)
			{
				ex.printStackTrace();
			}
		}


		//	Start
		ConnectTest ct = new ConnectTest (serverName);
	}	//	main

}	//	ConnectionTest
