/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 * Portions created by Jorg Janke are Copyright (C) 1999-2005 Jorg Janke, parts
 * created by ComPiere are Copyright (C) ComPiere, Inc.;   All Rights Reserved.
 * Portions created by Victor Perez are Copyright (C) 1999-2005 e-Evolution,S.C
 * Contributor(s): Victor Perez
 *****************************************************************************/
package org.compiere.install;

import java.net.InetAddress;
import java.sql.Connection;

import org.compiere.db.DB_PostgreSQL;

/**
 *	PostgreSQL Configuration
 *	
 *  @author Victor Perez e-Evolution
 *  @version $Id: ConfigPostgreSQL.java,v 1.0 2005/01/31 06:08:15 vpj-cd Exp $
 */
public class ConfigPostgreSQL extends Config
{

	/**
	 * 	ConfigPostgreSQL
	 *	@param data
	 */
	public ConfigPostgreSQL (ConfigurationData data)
	{
		super (data);
	}	//	ConfigPostgreSQL

	/** Discovered TNS			*/
	private String[] 			p_discovered = null;
	/**	PostgreSQL DB Info			*/
	private DB_PostgreSQL			p_db = new DB_PostgreSQL();
	
	/**
	 * 	Init
	 */
	public void init()
	{
		p_data.setDatabasePort(String.valueOf(DB_PostgreSQL.DEFAULT_PORT));
	}	//	init

	/**
	 * 	Discover Databases.
	 * 	To be overwritten by database configs
	 *	@param selected selected database
	 *	@return array of databases
	 */
	public String[] discoverDatabases(String selected)
	{
		if (p_discovered != null)
			return p_discovered;
		p_discovered = new String[]{};
		return p_discovered;
	}	//	discoveredDatabases
	
	
	/**************************************************************************
	 * 	Test
	 *	@return error message or null if OK
	 */
	public String test()
	{
		//	Database Server
		String server = p_data.getDatabaseServer();
		boolean pass = server != null && server.length() > 0;
		// vpj-cd e-evolution && server.toLowerCase().indexOf("localhost") == -1                        
		// vpj-cd e-evolution && !server.equals("127.0.0.1");
                        
		String error = "Not correct: DB Server = " + server;
		InetAddress databaseServer = null;
		try
		{
			if (pass)
				databaseServer = InetAddress.getByName(server);
		}
		catch (Exception e)
		{
			error += " - " + e.getMessage();
			pass = false;
		}
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseServer, "ErrorDatabaseServer", 
				pass, true, error); 
		log.info("OK: Database Server = " + databaseServer);
		setProperty(ConfigurationData.ADEMPIERE_DB_SERVER, databaseServer.getHostName());
		setProperty(ConfigurationData.ADEMPIERE_DB_TYPE, p_data.getDatabaseType());
		setProperty(ConfigurationData.ADEMPIERE_DB_PATH, p_data.getDatabaseType());

		//	Database Port
		int databasePort = p_data.getDatabasePort();
		pass = p_data.testPort (databaseServer, databasePort, true);
		error = "DB Server Port = " + databasePort;
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseServer, "ErrorDatabasePort",
				pass, true, error);
		if (!pass)
			return error;
		log.info("OK: Database Port = " + databasePort);
		setProperty(ConfigurationData.ADEMPIERE_DB_PORT, String.valueOf(databasePort));


		//	JDBC Database Info
		String databaseName = p_data.getDatabaseName();	//	Service Name
		String systemPassword = p_data.getDatabaseSystemPassword();

		//	URL (derived)
		String urlSystem = p_db.getConnectionURL(databaseServer.getHostName(), databasePort, 
			p_db.getSystemDatabase(databaseName), p_db.getSystemUser());
		pass = testJDBC(urlSystem, p_db.getSystemUser(), systemPassword);
		error = "Error connecting: " + urlSystem 
			+ " - " + p_db.getSystemUser() + "/" + systemPassword;
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseSystem, "ErrorJDBC",
				pass, true, error);
		if (!pass)
			return error;
		log.info("OK: System Connection = " + urlSystem);
		setProperty(ConfigurationData.ADEMPIERE_DB_SYSTEM, systemPassword);


		//	Database User Info
		String databaseUser = p_data.getDatabaseUser();	//	UID
		String databasePassword = p_data.getDatabasePassword();	//	PWD
		pass = databasePassword != null && databasePassword.length() > 0;
		error = "Invalid Database User Password";
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseUser, "ErrorJDBC",
				pass, true, error); 
		if (!pass)
			return error;
		//
		String url= p_db.getConnectionURL(databaseServer.getHostName(), databasePort, 
			databaseName, databaseUser);
		//	Ignore result as it might not be imported
		pass = testJDBC(url, databaseUser, databasePassword);
		error = "Database imported? Cannot connect to User: " + databaseUser + "/" + databasePassword;
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseUser, "ErrorJDBC",
				pass, false, error);
		if (pass)
			log.info("OK: Database User = " + databaseUser);
		else
			log.warning(error);
		setProperty(ConfigurationData.ADEMPIERE_DB_URL, url);
		setProperty(ConfigurationData.ADEMPIERE_DB_NAME, databaseName);
		setProperty(ConfigurationData.ADEMPIERE_DB_USER, databaseUser);
		setProperty(ConfigurationData.ADEMPIERE_DB_PASSWORD, databasePassword);

		return null;
	}	//	test
	
	/**
	 * 	Test JDBC Connection to Server
	 * 	@param url connection string
	 *  @param uid user id
	 *  @param pwd password
	 * 	@return true if OK
	 */
	private boolean testJDBC (String url, String uid, String pwd)
	{
		try
		{
			@SuppressWarnings("unused")
			Connection conn = p_db.getDriverConnection(url, uid, pwd);
		}
		catch (Exception e)
		{
			log.severe(e.toString());
			return false;
		}
		return true;
	}	//	testJDBC
	
}	//	ConfigPostgreSQL
