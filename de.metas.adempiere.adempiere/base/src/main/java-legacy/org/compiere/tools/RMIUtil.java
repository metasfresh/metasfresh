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
package org.compiere.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *  Test/List RMI Registry
 *
 *  @author Jorg Janke
 *  @version $Id: RMIUtil.java,v 1.3 2006/07/30 00:51:06 jjanke Exp $
 */
public class RMIUtil
{
	/**
	 * 	RMIUtil
	 */
	public RMIUtil()
	{
	//	testPort();
		try
		{
			System.out.println("Registry ------------------------------------");
			Registry registry = LocateRegistry.getRegistry();
			System.out.println("- " + registry);
			String[] list = registry.list();
			System.out.println("- size=" + list.length);
			for (int i = 0; i < list.length; i++)
			{
				System.out.println("-- " + list[i]);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		try
		{
			System.out.println("Server --------------------------------------");
		//	System.out.println("- " + RemoteServer.getClientHost());
			String[] list = Naming.list ("rmi://localhost:1099");
			System.out.println("- size=" + list.length);
			for (int i = 0; i < list.length; i++)
			{
				System.out.println("-- " + list[i]);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}	//	RMIUtil

	private void testPort()
	{
		try
		{
			System.out.println("Test Port -----------------------------------");
			Socket socket = new Socket ("localhost", 1099);
			System.out.println("- Socket=" + socket);
			//
			InputStream in = socket.getInputStream();
			int i = 0;
			while (i >= 0)
			{
				i = in.read();
				if (i >= 0)
					System.out.println((char)i);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	/**
	 * 	Test
	 *	@param args args
	 */
	public static void main (String[] args)
	{
		new RMIUtil();
	}	//	main

}	//	RMIUtil
