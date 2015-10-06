/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com
 * _____________________________________________
 *****************************************************************************/
package org.adempiere.as.jboss;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;

/**
 * JAAS login module for adempiere
 * @author Low Heng Sin
 */
public class AdempiereLoginModule implements LoginModule {

	private String unauthenticatedIdentity;
	private CallbackHandler handler;
	private Subject subject;
	private KeyNamePair[] roles;
	private String name;
	
	/**
	 * abort authentication process, reset state
	 */
	@Override
	public boolean abort() throws LoginException {
		roles = null;
		name = null;
		return true;
	}

	/**
	 * commit/complete the authentication project, add identity and roles to subject.
	 */
	@Override
	public boolean commit() throws LoginException {
		//note that jboss require all user role to be put under the group Roles
		if (roles == null || roles.length == 0)
		{
			//not authenticated or authentication failed
			subject.getPrincipals().add(new SimplePrincipal(unauthenticatedIdentity));
			SimpleGroup roleGroup = new SimpleGroup("Roles");
			subject.getPrincipals().add(roleGroup);
		}
		else
		{
			subject.getPrincipals().add(new SimplePrincipal(name));
			SimpleGroup roleGroup = new SimpleGroup("Roles");
			//fixed role use in ejb deployment descriptor
			roleGroup.addMember(new SimplePrincipal("adempiereUsers"));
			//dynamic role loaded from db, can be use with isCallerInRole for 
			//additional security check
			for(int i = 0; i < roles.length; i++)
			{
				roleGroup.addMember(new SimplePrincipal(roles[i].getName()));
			}
			subject.getPrincipals().add(roleGroup);
		}
		return true;
	}

	/**
	 * Initialize the login module, get options from configuration
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		unauthenticatedIdentity = (String)options.get("unauthenticatedIdentity");
		handler = callbackHandler;
		this.subject = subject;
	}

	/**
	 * Perform login process
	 */
	@Override
	public boolean login() throws LoginException {
		//get username and password from standard callback
		Callback callbacks[] = new Callback[]{new NameCallback("Login:"), new PasswordCallback("Password:", false)};
		try {
			handler.handle(callbacks);
		} catch (IOException e) {
		} catch (UnsupportedCallbackException e) {
		}
		name = ((NameCallback)callbacks[0]).getName();
		char[] pass = ((PasswordCallback)callbacks[1]).getPassword();
		String passwd = pass != null ? new String(pass) : null;
		
		//do authentication
		if (name != null && passwd != null)
		{
			//perform db authentication
			Login login = new Login(Env.getCtx());
			roles = login.getRoles(name, passwd);
		}
		else
		{
			//no username or password
			roles = null;
		}
		
		return true;
	}

	/**
	 * logout, reset state
	 */
	@Override
	public boolean logout() throws LoginException {
		roles = null;
		name = null;
		
		return true;
	}

}
