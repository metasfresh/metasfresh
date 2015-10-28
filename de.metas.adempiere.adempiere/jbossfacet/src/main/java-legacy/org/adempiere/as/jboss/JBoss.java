/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.as.jboss;

import java.util.Hashtable;

import javax.naming.Context;

import org.adempiere.as.IApplicationServer;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class JBoss implements IApplicationServer {
	
	//ensure client library is installed
	static {
		try {
			Class.forName("org.jboss.security.jndi.JndiLoginInitialContextFactory");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @see IApplicationServer#getInitialContextEnvironment(String, int, String, String)
	 */
	public Hashtable<String, String> getInitialContextEnvironment(
			String AppsHost, int AppsPort, String principal, String credential) {
		Hashtable<String,String> env = new Hashtable<String,String>();
		String connect = AppsHost;
		if (AppsHost.indexOf("://") == -1)
			connect = "jnp://" + AppsHost + ":" + AppsPort;
		env.put (Context.PROVIDER_URL, connect);		
		env.put (Context.URL_PKG_PREFIXES, "org.jboss.naming.client");
		//	HTTP - default timeout 0
		env.put (org.jnp.interfaces.TimedSocketFactory.JNP_TIMEOUT, "5000");	//	timeout in ms
		env.put (org.jnp.interfaces.TimedSocketFactory.JNP_SO_TIMEOUT, "5000");
		//	JNP - default timeout 5 sec
		env.put(org.jnp.interfaces.NamingContext.JNP_DISCOVERY_TIMEOUT, "5000");
		
		if (principal != null && credential != null)
		{
			env.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.security.jndi.JndiLoginInitialContextFactory");
			env.put(Context.SECURITY_PRINCIPAL, principal);
			env.put(Context.SECURITY_CREDENTIALS, credential);
		}
		else
		{
			env.put (Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
		}
		
		return env;
	}

	public int getDefaultNamingServicePort() {
		return 1099;
	}
}
