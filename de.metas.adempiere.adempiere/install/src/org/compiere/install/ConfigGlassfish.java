/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Praneet Tiwari.                                       *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Trifon Trifonov (trifonnt@users.sourceforge.net)                  *
*                                                                     *
* Sponsors:                                                           *
* - D3 Soft (http://www.d3-soft.com)                                  *
***********************************************************************/

package org.compiere.install;

import java.io.File;
import java.net.InetAddress;

/**
 *	GlassFish v2UR1 Apps Server Configuration
 *	
 *  @author Praneet Tiwari
 *  @author Trifon Trifonov
 *  @version $Id:  $
 */

public class ConfigGlassfish extends Config {

	/**
	 * 	ConfigGlassfish
	 * 	@param data configuration
	 */
	public ConfigGlassfish (ConfigurationData data)
	{
		super (data);
	}	//	ConfigGlassfish
	
	/**
	 * 	Initialize
	 */
	public void init()
	{
		p_data.setAppsServerDeployDir(getDeployDir());
		p_data.setAppsServerDeployDir(false);
		//
		p_data.setAppsServerJNPPort("3700");
		p_data.setAppsServerJNPPort(true);
		p_data.setAppsServerWebPort("8080");
		p_data.setAppsServerWebPort(true);
		p_data.setAppsServerSSLPort("443");
		p_data.setAppsServerSSLPort(true);
	}	//	init

	/**
	 * 	Get Deployment Dir
	 *	@return deployment dir
	 */
	private String getDeployDir()
	{
		// TODO - check deployment directory
		return p_data.getAdempiereHome() + File.separator + "glassfish";
                /*Commented for now
			+ File.separator + "glassfish"
			+ File.separator + "domains"
			+ File.separator + "domain1" ;
                 * */
	}	//	getDeployDir
	
	/**
	 * 	Test
	 *	@return error message or null if OK
	 */
	public String test()
	{
		//	AppsServer
		String server = p_data.getAppsServer();
		boolean pass = server != null && server.length() > 0
			&& server.toLowerCase().indexOf("localhost") == -1
			&& !server.equals("127.0.0.1");
		InetAddress appsServer = null;
		String error = "Not correct: AppsServer = " + server; 
		try
		{
			if (pass)
				appsServer = InetAddress.getByName(server);
		}
		catch (Exception e)
		{
			error += " - " + e.getMessage();
			pass = false;
		}
		if (getPanel() != null)
			signalOK(getPanel().okAppsServer, "ErrorAppsServer",
				pass, true, error); 
		if (!pass)
			return error;
		log.info("OK: AppsServer = " + appsServer);
		setProperty(ConfigurationData.ADEMPIERE_APPS_SERVER, appsServer.getHostName());
		setProperty(ConfigurationData.ADEMPIERE_APPS_TYPE, p_data.getAppsServerType());

		setProperty(ConfigurationData.ADEMPIERE_APPS_DEPLOY, p_data.getAppsServerDeployDir());
		
		//	JNP Port
		int JNPPort = p_data.getAppsServerJNPPort();
		setProperty(ConfigurationData.ADEMPIERE_JNP_PORT, String.valueOf(JNPPort));

		//	Web Port
		int WebPort = p_data.getAppsServerWebPort();
		pass = !p_data.testPort ("http", appsServer.getHostName(), WebPort, "/") 
			&& p_data.testServerPort(WebPort);
		error = "Not correct: Web Port = " + WebPort;
		if (getPanel() != null)
			signalOK(getPanel().okWebPort, "ErrorWebPort",
				pass, true, error); 
		if (!pass)
			return error;
		log.info("OK: Web Port = " + WebPort);
		setProperty(ConfigurationData.ADEMPIERE_WEB_PORT, String.valueOf(WebPort));
		
		//	SSL Port
		int sslPort = p_data.getAppsServerSSLPort();
		pass = !p_data.testPort ("https", appsServer.getHostName(), sslPort, "/") 
			&& p_data.testServerPort(sslPort);
		error = "Not correct: SSL Port = " + sslPort;
		if (getPanel() != null)
			signalOK(getPanel().okSSLPort, "ErrorWebPort",
				pass, true, error); 
		if (!pass)
			return error;
		log.info("OK: SSL Port = " + sslPort);
		setProperty(ConfigurationData.ADEMPIERE_SSL_PORT, String.valueOf(sslPort));
		//
		return null;
	}	//	test
	
}	//	ConfigGlassfish

