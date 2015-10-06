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
package org.compiere.db;

import java.util.Hashtable;
import java.util.logging.Level;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.ldap.InitialLdapContext;

import org.compiere.util.CLogger;


/**
 *	LDAP Management Interface
 *	
 *  @author Jorg Janke
 *  @version $Id: LDAP.java,v 1.2 2006/07/30 00:55:13 jjanke Exp $
 */
public class LDAP
{
	/**
	 * 	Validate User
	 *	@param ldapURL provider url - e.g. ldap://dc.compiere.org
	 *	@param domain domain name = e.g. compiere.org
	 *	@param userName user name - e.g. jjanke
	 *	@param password password 
	 *	@return true if validated with ldap
	 */
	public static boolean validate (String ldapURL, String domain, String userName, String password)
	{
		Hashtable<String,String> env = new Hashtable<String,String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		//	ldap://dc.compiere.org
		env.put(Context.PROVIDER_URL, ldapURL);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		//	jjanke@compiere.org
		// For OpenLDAP uncomment the next line  
		// StringBuffer principal = new StringBuffer("uid=").append(userName).append(",").append(domain);
		StringBuffer principal = new StringBuffer(userName).append("@").append(domain);
		env.put(Context.SECURITY_PRINCIPAL, principal.toString());
		env.put(Context.SECURITY_CREDENTIALS, password);
		//
		try
		{
			// Create the initial context
			InitialLdapContext ctx = new InitialLdapContext(env, null);
		//	DirContext ctx = new InitialDirContext(env);
			
			//	Test - Get the attributes
			Attributes answer = ctx.getAttributes("");

		    // Print the answer
		    if (false)
		    	dump (answer);
		}
		catch (AuthenticationException e)
		{
			log.info("Error: " + principal + " - " + e.getLocalizedMessage());
			return false;
		}
		catch (Exception e) 
		{
			log.log (Level.SEVERE, ldapURL + " - " + principal, e);
		    return false;
		}
		log.info("OK: " + principal);
		return true;
	}	//	validate
	
	/**	Logger	*/
	private static CLogger log = CLogger.getCLogger (LDAP.class);
	
	
	/**
	 * 	Test NT
	 *	@throws LoginException
	 *
	private static void testNT () throws LoginException
	{
		try
		{
			System.out.println ("NT system ----------------------------");
			NTSystem ntsystem = new NTSystem ();
			System.out.println (ntsystem);
			System.out.println (ntsystem.getDomain ());
			System.out.println (ntsystem.getDomainSID ());
			System.out.println (ntsystem.getName ());
			System.out.println (ntsystem.getUserSID ());
			System.out.println ("NT login ----------------------------");
			NTLoginModule ntlogin = new NTLoginModule ();
			System.out.println (ntlogin);
			Map<String,String> map = new HashMap<String,String>();
			map.put ("debug", "true");
			ntlogin.initialize (null, null, null, map);
			System.out.println (ntlogin.login ());
		}
		catch (LoginException le)
		{
			System.err.println ("Authentication attempt failed" + le);
		}
	} //	testNT
	
	
	/**
	 * 	testKerberos
	 *	@throws LoginException
	 *
	private static void testKerberos ()
		throws LoginException
	{
		System.out.println ("Krb login ----------------------------");
		Map<String,String> map = new HashMap<String,String>();
		// map.put("debug", "true");
		// map.put("debugNative", "true");
		Krb5LoginModule klogin = new Krb5LoginModule ();
		System.out.println (klogin);
		map.put ("principal", "username@compiere.org");
		map.put ("credential", "pass");
		klogin.initialize (null, null, null, map);
		System.out.println (klogin.login ());
		/***********************************************************************
		 * ** No krb5.ini file found in entire system Debug is true storeKey
		 * false useTicketCache false useKeyTab false doNotPrompt false
		 * ticketCache is null KeyTab is null refreshKrb5Config is false
		 * principal is jjanke tryFirstPass is false useFirstPass is false
		 * storePass is false clearPass is false [Krb5LoginModule]
		 * authentication failed Could not load configuration file
		 * c:\winnt\krb5.ini (The system cannot find the file specified)
		 * javax.security.auth.login.LoginException: Could not load
		 * configuration file c:\winnt\krb5.ini (The system cannot find the file
		 * specified)
		 *
	} //	testKerbos
	/**/
	
	/**
	 * 	Print Attributes to System.out
	 *	@param attrs
	 */
	 private static void dump (Attributes attrs)
	{
		if (attrs == null)
		{
			System.out.println ("No attributes");
		}
		else
		{
			/* Print each attribute */
			try
			{
				for (NamingEnumeration<? extends Attribute> ae = attrs.getAll (); ae.hasMore ();)
				{
					Attribute attr = ae.next ();
					System.out.println ("attribute: " + attr.getID ());
					/* print each value */
					for (NamingEnumeration<?> e = attr.getAll(); 
						e.hasMore (); 
						System.out.println ("    value: " + e.next()))
						;
				}
			}
			catch (NamingException e)
			{
				e.printStackTrace ();
			}
		}
	}	//	dump
		
	 /**
	  * Test
	  *	@param args ignored
	  */
	public static void main (String[] args)
	{
		try
		{
			validate("ldap://dc.compiere.org", "compiere.org", "jjanke", "ikeepforgetting");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	//	main
	
}	//	LDAP

