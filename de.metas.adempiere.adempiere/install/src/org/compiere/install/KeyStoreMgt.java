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
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.swing.JFrame;

import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.compiere.Adempiere;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;

import sun.security.tools.KeyTool;

/**
 *	Class to manage SSL KeyStore
 *	
 *  @author Jorg Janke
 *  @version $Id: KeyStoreMgt.java,v 1.3 2006/07/30 00:57:42 jjanke Exp $
 */
@IgnoreJRERequirement // task 06687
public class KeyStoreMgt
{
	/**
	 * 	Constructor.
	 *	@param keyStoreFileName key store file name
	 *	@param keyStorePassword - password to open the keystore
	 *  @param certificateAlias if <code>null</code>, then {@link #DEFAULT_CERTIFICATE_ALIAS} will be used.
	 *  @param certificatePassword password to access the certificate
	 */
	public KeyStoreMgt (final String keyStoreFileName, final char[] keyStorePassword, final String certificateAlias, final char[] certificatePassword)
	{
		log.info("Keystore file: " + keyStoreFileName);
		m_file = new File (keyStoreFileName);
		m_keyStorePassword = keyStorePassword;
		m_certificateAlias = certificateAlias == null ? DEFAULT_CERTIFICATE_ALIAS : certificateAlias;
		m_certificatePassword = certificatePassword;
	}	//	KeyStoreMgt
	
	/**	Logger					*/
	private static CLogger		log	= CLogger.getCLogger(KeyStoreMgt.class);
	/**	KeyStore File			*/
	private File 		m_file = null;
	/**	KeyStore Password		*/
	private char[]		m_keyStorePassword = null; // task 005047
	
	private String m_certificateAlias;
	
	/**	Key Password		*/
	private char[]		m_certificatePassword = null; // task 005047
	
	/** KeyStore				*/
	private KeyStore	m_keyStore = null;
	private String organizationUnit;
	private String location;
	private String state;
	private String country;
	private String commonName;
	private String organization;
	
	
	/**	Directory below ADEMPIERE_HOME	*/
	public static String		KEYSTORE_DIRECTORY = "keystore";
	/** Name of KeyStore				*/
	public static String		DEFAULT_KEYSTORE_NAME = "myKeystore";
	/** Certificate Alias				*/
	public static String		DEFAULT_CERTIFICATE_ALIAS = "adempiere";
	

	/**
	 * 	Verify/Create Key Store
	 * 	@param parent frame
	 *	@return null or error message
	 */
	public String verify (JFrame parent)
	{
		KeyStore ks = null;
		try
		{
			ks = getKeyStore();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "get KeyStore", e);
			return e.getMessage();
		}
		//	No KeyStore
		if (ks == null)
		{
			createCertificate(m_certificateAlias, parent);
			try
			{
				ks = getKeyStore();
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "new KeyStore", e);
				return e.getMessage();
			}
		}	//	new key store

		//	No KeyStore
		if (ks == null)
			return "No Key Store";
		
		//	Verify Certificate
		Certificate cert = null;
		try
		{
			cert = getCertificate(m_certificateAlias);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "certificate", e);
			return e.getMessage();
		}
		if (cert == null)
			return "No Certificate found";
		
		return null;	//	OK
	}	//	verify
	
	/**
	 * 	Get KeyStore
	 *	@return KeyStore or null
	 *	@throws Exception
	 */
	public KeyStore getKeyStore() throws Exception
	{
		try
		{
			m_keyStore = KeyStore.getInstance("JKS");
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Instance", e);
		}
		//	Load Existing
		if (m_file.exists())
		{
			log.fine(m_file.toString());
			InputStream is = null;
			try
			{
				is = new FileInputStream (m_file);
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "load", e);
				return null;
			}
			m_keyStore.load(is, m_keyStorePassword);
		}
		else
			return null;	//	does not exist
		//
		log.fine("Provider=" + m_keyStore.getProvider()
				+ " - Type=" + m_keyStore.getType());
		//
		return m_keyStore;
	}	//	getKeyStore
	
	/**
	 * 	Get Certificate
	 *	@param alias alias
	 *	@return certificate or null
	 *	@throws Exception
	 */
	public Certificate getCertificate (String alias) throws Exception
	{
		log.config("Alias=" + alias);
		
		Date date = m_keyStore.getCreationDate(alias);
		if (date == null)	//	no certificate
			return null;
		log.fine("Created=" + date);
		//
		Key key = m_keyStore.getKey(alias, m_certificatePassword);
		if (CLogMgt.isLevelFinest())
			log.info("Key=" + key);				//	Multiple lines
		else
			log.fine(key.getAlgorithm());
		//
		Certificate cert = m_keyStore.getCertificate(alias);
		if (CLogMgt.isLevelFinest())
			log.info("Certificate = " + cert);	//	Multiple lines
		else
			log.fine(cert.getType());
		
	//	log.fine("Certificate - Type=" + cert.getType()
	//			+ " - PublicKey=" + cert.getPublicKey());
		return cert;
	}	//	getCertificate
	
	
	/**************************************************************************
	 * 	Create Certificate
	 *	@param alias alias
	 * 	@param parent interactive dialog
	 */
	private void createCertificate (String alias, JFrame parent)
	{
		log.info("");
		try
		{
			File dir = m_file.getParentFile();
			if (!dir.exists())
				dir.mkdir();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "directory", e);
		}
		
		String dname = getDname(this, parent);
		if (dname == null)
			return;
		//
		try
		{
			genkey (alias, m_keyStorePassword, m_certificatePassword, m_file.getAbsolutePath(), dname);
			selfcert (alias, m_keyStorePassword, m_certificatePassword, m_file.getAbsolutePath(), dname);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "certificate", e);
		}
	}	//	createCertificate
	
	public void setCommonName(String cn)
	{
		commonName = cn;
	}
	
	public void setOrganization(String o)
	{
		organization = o; 
	}
	
	public void setOrganizationUnit(String o)
	{
		organizationUnit = o;
	}
	
	public void setLocation(String l)
	{
		location = l;
	}
	
	public void setState(String s)
	{
		state = s;
	}
	
	public void setCountry(String c)
	{
		country = c;
	}
	
	/**
	 * 	Get Distinguised Name
	 * 	@param parent interactive dialog
	 *	@return dname or null
	 */
	public static String getDname(KeyStoreMgt mgt, JFrame parent)
	{
		String cn = mgt.commonName;
		if (cn == null)
		{
			try
			{
				InetAddress address = InetAddress.getLocalHost();
				cn = address.getCanonicalHostName();
			}
			catch (Exception e)
			{
			}
		}
		
		String ou = mgt.organization != null
			? mgt.organization
			: System.getProperty("user.name");
		String o = mgt.organizationUnit != null
			? mgt.organizationUnit
			: "AdempiereUser";
		String l = mgt.location != null
			? mgt.location
			: "MyTown";
		String s = mgt.state != null
			? mgt.state
			: "";
		String c = mgt.country != null
			? mgt.country
			: System.getProperty("user.country");
		//
		if (parent != null)
		{
			KeyStoreDialog skd = new KeyStoreDialog(parent,
				cn, ou, o, l, s, c);
			if (!skd.isOK())
				return null;
			cn = skd.getCN();
			ou = skd.getOU();
			o = skd.getO();
			l = skd.getL();
			s = skd.getS();
			c = skd.getC();
		}

		//
		if (cn == null || cn.length() == 0)
		{
			log.warning("No Common Name (CN)");
			return null;
		}
		if (ou == null || ou.length() == 0)
		{
			log.warning("No Organization Unit (OU)");
			return null;
		}
		if (o == null || o.length() == 0)
		{
			log.warning("No Organization (O)");
			return null;
		}
		if (c == null || c.length() == 0)
		{
			log.warning("No Country (C)");
			return null;
		}
		
		//	Escape commas
		StringBuffer dname = new StringBuffer();
		dname.append("CN=").append(escapeCommas(cn));		//	common name
		dname.append(", OU=").append(escapeCommas(ou));		//	org unit
		dname.append(", O=").append(escapeCommas(o));		//	org
		if (l != null && l.length() > 0)
			dname.append(", L=").append(escapeCommas(l));	//	locality
		if (s != null && s.length() > 0)
			dname.append(", S=").append(escapeCommas(s));	//	state
		dname.append(", C=").append(escapeCommas(c));		//	country
		return dname.toString();
	}	//	getDname
	
	/**
	 * 	Escape Commas
	 *	@param in input string
	 *	@return excaped string
	 */
	public static String escapeCommas(String in)
	{
		if (in == null || in.indexOf(',') == -1)
			return in;
		StringBuffer out = new StringBuffer();
		char[] chars = in.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == ',')
				out.append('\\').append(',');
			else
				out.append(chars[i]);
		}
		return out.toString();
	}	//	escapeCommas
	
	/**
	 * 	Generate Key
	 *	@param alias adempiere
	 *	@param password password
	 *	@param fileName key store file name (may have spaces)
	 *	@param dname distinguished name
	 */
	public static void genkey (String alias, char[] keyStorePassword, char[] keyPass, String fileName, String dname)
	{
		StringBuffer cmd = new StringBuffer ("-genkey -keyalg rsa");
		cmd.append(" -alias ").append(alias);
		cmd.append(" -dname \"").append(dname).append("\"");
		cmd.append(" -keypass ").append(keyPass).append(" -validity 999");
		if (fileName.indexOf(' ') != -1)
			cmd.append(" -keystore \"").append(fileName).append("\" -storepass ").append(keyStorePassword);
		else
			cmd.append(" -keystore ").append(fileName).append(" -storepass ").append(keyStorePassword);
		keytool (cmd.toString());
	}	//	genkey
	
	/**
	 * 	Generate Key
	 *	@param alias adempiere
	 *	@param password password
	 *	@param fileName key store file name (may have spaces)
	 *	@param dname distinguished name
	 */
	public static void selfcert (String alias, char[] keyStorePassword, char[] keyPassword, String fileName, String dname)
	{
		StringBuffer cmd = new StringBuffer ("-selfcert");
		cmd.append(" -alias ").append(alias);
		cmd.append(" -dname \"").append(dname).append("\"");
		cmd.append(" -keypass ").append(keyPassword).append(" -validity 999");
		if (fileName.indexOf(' ') != -1)
			cmd.append(" -keystore \"").append(fileName).append("\" -storepass ").append(keyStorePassword);
		else
			cmd.append(" -keystore ").append(fileName).append(" -storepass ").append(keyStorePassword);
		keytool (cmd.toString());
	}	//	selfcert
	
	/**
	 * 	Submit Command to Key Tool
	 *	@param cmd command
	 */
	public static void keytool(String cmd)
	{
		log.info("keytool " + cmd);
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(cmd, " ");
		String quoteBuffer = null;
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
		//	System.out.println("= " + token + " = quoteBuffer=" + quoteBuffer + " - Size=" + list.size() );
			if (quoteBuffer == null)
			{
				if (token.startsWith("\""))
					quoteBuffer = token.substring(1);
				else
					list.add(token);
			}
			else
				quoteBuffer += " " + token;
			if (token.endsWith("\""))
			{
				String str = quoteBuffer.substring(0, quoteBuffer.length()-1); 
			//	System.out.println("  Buffer= " + str );
				list.add(str);
				quoteBuffer = null;
			}
		}	//	all tokens
		
		//
		String[] args = new String[list.size()];
		list.toArray(args);
	//	System.out.println(" args #" + args.length);
                //vpj-cd add support java 6
                try
                {
		KeyTool.main(args);
                }
                catch (Exception e)
                {                     
                }
	}	//	ketyool
	
	/**
	 * 	Get Keystore File Name
	 *	@param baseDir ADEMPIERE_HOME
	 *  @param may be <code>null</code>. If null then {@link #DEFAULT_KEYSTORE_NAME} is assumed
	 *	@return file name
	 *
	 */
	public static String getKeystoreFileName (final String baseDir, final String keystoreName)
	{
		String fileName = baseDir;
		if (fileName == null)
		{
			fileName = "";
		}
		else if (!fileName.endsWith(File.separator))
		{
			fileName += File.separator;
		}
		
		fileName += KEYSTORE_DIRECTORY + File.separator + (keystoreName == null ? DEFAULT_KEYSTORE_NAME: keystoreName);
		return fileName;
	}	//	getKeystoreFileName
	
	
	/**************************************************************************
	 * 	Test
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
		Adempiere.startupEnvironment(true);
		System.out.println(new KeyStoreMgt (
			"C:/Adempiere/keystore/myKeystore2", "myPassword".toCharArray(), DEFAULT_CERTIFICATE_ALIAS, "myPassword".toCharArray()).verify(null));
	}	//	main
	
}	//	MyKeyStore
