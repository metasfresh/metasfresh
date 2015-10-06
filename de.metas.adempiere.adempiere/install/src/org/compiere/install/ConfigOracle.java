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
package org.compiere.install;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.regex.Pattern;

import oracle.jdbc.OracleDriver;

import org.compiere.db.DB_Oracle;


/**
 *	Oracle Configuration
 *	
 *  @author Jorg Janke
 *  @version $Id: ConfigOracle.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
public class ConfigOracle extends Config
{
	/**
	 * 	ConfigOracle
	 * 	@param data configuration
	 * 	@param XE express edition
	 */
	public ConfigOracle (ConfigurationData data, boolean XE)
	{
		super (data);
		m_XE = XE;
	}	//	ConfigOracle
	
	/**	Oracle Driver			*/
	private static OracleDriver s_oracleDriver = null;
	/** Discoverd TNS			*/
	private String[] 			p_discovered = null;
	/** Discovered Database Name */
	private String[] 			p_dbname = null;
	/** Last Connection			*/
	private Connection			m_con = null;
	/** Express Edition			*/
	private boolean 			m_XE = false;
	
	/**
	 * 	Init
	 */
	public void init()
	{
		p_data.setDatabasePort(String.valueOf(DB_Oracle.DEFAULT_PORT));
		//
		p_data.setDatabaseSystemPassword(true);
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
		//
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> dblist = new ArrayList<String>();
		//	default value to lowercase or null
		String def = selected;
		if (def != null && def.trim().length() == 0)
			def = null;
		if (def != null) {
			list.add(def.toLowerCase());
			dblist.add(def.toLowerCase());
		}
		
		if (m_XE)
		{
			String serviceName = "xe";
			if (!list.contains(serviceName))
			{
				list.add(serviceName);
				dblist.add(serviceName);
			}
		}
		
		String path = System.getenv("ORACLE_HOME");
		if (path == null) 
		{
			//	Search for Oracle Info
			path = System.getProperty("java.library.path");
			String[] entries = path.split(File.pathSeparator);
			for (int e = 0; e < entries.length; e++)
			{
				String entry = entries[e].toLowerCase();
				if (entry.indexOf("ora") != -1 && entry.endsWith("bin"))
				{
					StringBuffer sb = getTNS_File (entries[e].substring(0, entries[e].length()-4));
					String[] tnsnames = getTNS_Names (sb, true);
					String[] dbNames = getTNS_Names (sb, false);
					//fallback to tnsnames
					if (dbNames == null) dbNames = tnsnames;
					if (tnsnames != null)
					{
						for (int i = 0; i < tnsnames.length; i++)
						{
							String tns = tnsnames[i];	//	 is lower case
							//check bound, fallback to tnsname
							String db = (i < dbNames.length) ? dbNames[i] : tns;
							if (!tns.equals(def)) {
								list.add(tns);
								dblist.add(db);
							} else {
								dblist.remove(0);
								dblist.add(0, db);
							}
						}
						break;
					}
				}
			}	//	for all path entries
		}
		else
		{
			StringBuffer sb = getTNS_File (path);
			String[] tnsnames = getTNS_Names (sb, true);
			String[] dbNames = getTNS_Names (sb, false);
			//fallback to tnsnames
			if (dbNames == null) dbNames = tnsnames;
			if (tnsnames != null)
			{
				for (int i = 0; i < tnsnames.length; i++)
				{
					String tns = tnsnames[i];	//	 is lower case
					//check bound, fallback to tnsname
					String db = (i < dbNames.length) ? dbNames[i] : tns;
					if (!tns.equals(def)) {
						list.add(tns);
						dblist.add(db);
					} else {
						dblist.remove(0);
						dblist.add(0, db);
					}
				}
			}
		}

		p_discovered = new String[list.size()];
		list.toArray(p_discovered);
		p_dbname = new String[dblist.size()];
		dblist.toArray(p_dbname);
		return p_discovered;
	}	//	discoverDatabases
	
	@Override
	public String getDatabaseName(String nativeConnectioName) 
	{
		int idx = -1;
		if (p_discovered == null) return nativeConnectioName;
		for (int i = 0; i < p_discovered.length; i++)
		{
			if (p_discovered[i].equals(nativeConnectioName))
			{
				idx = i;
				break;
			}
		}
		if (idx >= 0 
				&& p_dbname != null 
				&& idx < p_dbname.length)
			return p_dbname[idx];
		else
			return nativeConnectioName;
	}

	/**
	 * 	Get File tnmsnames.ora in StringBuffer
	 * 	@param oraHome ORACLE_HOME
	 * 	@return tnsnames.ora or null
	 */
	private StringBuffer getTNS_File (String oraHome)
	{
		String tnsnames = oraHome + File.separator
			+ "network" + File.separator
			+ "admin" + File.separator
			+ "tnsnames.ora";
		File tnsfile = new File (tnsnames);
		if (!tnsfile.exists())
			return null;

		log.fine(tnsnames);
		StringBuffer sb = new StringBuffer();
		try
		{
			FileReader fr = new FileReader (tnsfile);
			int c;
			while ((c = fr.read()) != -1)
				sb.append((char)c);
		}
		catch (IOException ex)
		{
			log.warning("Error Reading " + tnsnames);
			ex.printStackTrace();
			return null;
		}
		if (sb.length() == 0)
			return null;
		return sb;
	}	//	getTNS_File

	/**
	 * 	Get TNS Names entries.
	 * 	Assumes standard tnsmanes.ora formatting of NetMgr
	 * 	@param tnsnames content of tnsnames.ora
	 * 	@return service names or null
	 */
	private String[] getTNS_Names (StringBuffer tnsnames, boolean tns)
	{
		if (tnsnames == null)
			return null;

		ArrayList<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("$", Pattern.MULTILINE);
		String[] lines = pattern.split(tnsnames);
		for (int i = 0; i < lines.length; i++)
		{
			String line = lines[i].trim();
			log.finest(i + ": " + line);
			if (tns)	//	get TNS Name
			{
				if (line.length() > 0
					&& Character.isLetter(line.charAt(0))	//	no # (
					&& line.indexOf('=') != -1
					&& line.indexOf("EXTPROC_") == -1
					&& line.indexOf("_HTTP") == -1)
				{
					String entry = line.substring(0, line.indexOf('=')).trim().toLowerCase();
					log.fine(entry);
					list.add(entry);
				}
			}
			else	//	search service names
			{
				if (line.length() > 0
					&& line.toUpperCase().indexOf("SERVICE_NAME") != -1)
				{
					String entry = line.substring(line.indexOf('=')+1).trim().toLowerCase();
					int index = entry.indexOf(')');
					if (index != 0)
						entry = entry.substring(0, index).trim();
					log.fine(entry);
					list.add(entry);
				}
				
			}
		}
		//	Convert to Array
		if (list.size() == 0)
			return null;
		String[] retValue = new String[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getTNS_Names
	
	
	/**************************************************************************
	 * 	Test
	 *	@return error message or null if OK
	 */
	public String test()
	{
		//	Database Server
		String server = p_data.getDatabaseServer();
		boolean pass = server != null && server.length() > 0
			&& server.toLowerCase().indexOf("localhost") == -1 
			&& !server.equals("127.0.0.1");
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
		pass = systemPassword != null && systemPassword.length() > 0;
		error = "No Database System Password entered";
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseSystem, "ErrorJDBC",
				pass, true,	error);
		if (!pass)
			return error;
		//
		//	URL (derived)	jdbc:oracle:thin:@//prod1:1521/prod1
		String url = "jdbc:oracle:thin:@//" + databaseServer.getHostName()
			+ ":" + databasePort
			+ "/" + databaseName;
		pass = testJDBC(url, "system", systemPassword);
		error = "Error connecting: " + url 
			+ " - as system/" + systemPassword;
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseSystem, "ErrorJDBC",
				pass, true, error);
		if (!pass)
			return error;
		log.info("OK: Connection = " + url);
		setProperty(ConfigurationData.ADEMPIERE_DB_URL, url);
		log.info("OK: Database System User " + databaseName);
		setProperty(ConfigurationData.ADEMPIERE_DB_NAME, databaseName);
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
		//	Ignore result as it might not be imported
		pass = testJDBC(url, databaseUser, databasePassword);
		error = "Cannot connect to User: " + databaseUser + "/" + databasePassword + " - Database may not be imported yet (OK on initial run).";
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseUser, "ErrorJDBC",
				pass, false, error);
		if (pass)
		{
			log.info("OK: Database User = " + databaseUser);
			if (m_con != null)
				setProperty(ConfigurationData.ADEMPIERE_WEBSTORES, getWebStores(m_con));
		}
		else
			log.warning(error);
		setProperty(ConfigurationData.ADEMPIERE_DB_USER, databaseUser);
		setProperty(ConfigurationData.ADEMPIERE_DB_PASSWORD, databasePassword);

		//	TNS Name Info via sqlplus 
		String sqlplus = "sqlplus system/" + systemPassword + "@" 
			+ databaseServer.getHostName() + "/" + databaseName
			+ " @utils/oracle/Test.sql";
		log.config(sqlplus);
		pass = testSQL(sqlplus);
		error = "Error connecting via: " + sqlplus;
		if (getPanel() != null)
			signalOK(getPanel().okDatabaseSQL, "ErrorTNS", 
				pass, true, error);
		if (pass)
			log.info("OK: Database SQL Connection");
		
		//	OCI Test
		if (System.getProperty("TestOCI", "N").equals("Y"))
		{
			url = "jdbc:oracle:oci8:@" + databaseName;
			pass = testJDBC(url, "system", systemPassword);
			if (pass)
				log.info("OK: Connection = " + url);
			else
				log.warning("Cannot connect via Net8: " + url);
		}
			log.info("OCI Test Skipped");
		
		//
		m_con = null;
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
		log.fine("Url=" + url + ", UID=" + uid);
		try
		{
			if (s_oracleDriver == null)
			{
				s_oracleDriver = new OracleDriver();
				DriverManager.registerDriver(s_oracleDriver);
			}
			m_con = DriverManager.getConnection(url, uid, pwd);
		}
		catch (UnsatisfiedLinkError ule)
		{
			log.warning("Check [ORACLE_HOME]/jdbc/Readme.txt for (OCI) driver setup");
			log.warning(ule.toString());
		}
		catch (Exception e)
		{
			log.warning(e.toString());
			return false;
		}
		return true;
	}	//	testJDBC

	/**
	 * 	Test TNS Connection
	 *  @param sqlplus sqlplus command line
	 * 	@return true if OK
	 */
	private boolean testSQL (String sqlplus)
	{
		StringBuffer sbOut = new StringBuffer();
		StringBuffer sbErr = new StringBuffer();
		int result = -1;
		try
		{
			Process p = Runtime.getRuntime().exec (sqlplus);
			InputStream in = p.getInputStream();
			int c;
			while ((c = in.read()) != -1)
			{
				sbOut.append((char)c);
				System.out.print((char)c);
			}
			in.close();
			in = p.getErrorStream();
			while ((c = in.read()) != -1)
				sbErr.append((char)c);
			in.close();
			//	Get result
			try
			{
				Thread.yield();
				result = p.exitValue();
			}
			catch (Exception e)		//	Timing issue on Solaris.
			{
				Thread.sleep(200);	//	.2 sec
				result = p.exitValue();
			}
		}
		catch (Exception ex)
		{
			log.warning(ex.toString());
		}
		log.finer(sbOut.toString());
		if (sbErr.length() > 0)
			log.warning(sbErr.toString());
		return result == 0;
	}	//	testSQL
			
}	//	ConfigOracle
