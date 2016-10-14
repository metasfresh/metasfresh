package de.metas.printing.model.validator;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MSession;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Login;

import de.metas.adempiere.model.I_AD_Session;
import de.metas.hostkey.api.IHostKeyBL;
import de.metas.printing.model.I_AD_User_Login;

@Interceptor(I_AD_User_Login.class)
public class AD_User_Login
{
	public static final transient AD_User_Login instance = new AD_User_Login();

	private AD_User_Login()
	{
		super();
	}

	/**
	 * Actually this is doing the User Login for requests which are coming from replication.
	 * 
	 * TODO: the exceptions don't make it to the client; instead of throwing exceptions, define a "loginResult" column, set its value an return it to the printing clients via export format.
	 * 
	 * @param loginRequest
	 * @task http://dewiki908/mediawiki/index.php/08283_Provide_Host_Independent_Print_Client
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void processLoginRequest(final I_AD_User_Login loginRequest)
	{
		//
		// If already logged in (i.e. record was created from somewhere else)
		// => do nothing
		if (loginRequest.getAD_Session_ID() > 0)
		{
			return;
		}

		//
		// Parent context
		final Properties ctx = InterfaceWrapperHelper.getCtx(loginRequest);

		if (Env.getAD_Session_ID(ctx) > 0)
		{
			// NOTE: we assume this request comes from Replication import and the AD_Session_ID is not yet configured....
			// that's why we are here.
			throw new AdempiereException("There shall be no session in context at this point! AD_Session_ID=" + Env.getAD_Session_ID(ctx));
		}

		//
		// Create login context
		// because we don't want to alter the parent's one.
		final Properties loginCtx = Env.deriveCtx(ctx);

		//
		// Perform Login
		// 1. Get roles
		// => Context update: AD_Session_ID
		final String loginUsername = loginRequest.getUserName();
		final String loginPassword = loginRequest.getPassword();
		final Login login = new Login(loginCtx);
		final Set<KeyNamePair> userRoles = login.authenticate(loginUsername, loginPassword);
		if (userRoles.isEmpty())
		{
			throw new AdempiereException("User has no roles assigned: " + loginUsername);
		}
		else if (userRoles.size() != 1)
		{
			// NOTE: don't show which roles are there because this message goes to client and it could be a security issue.
			throw new AdempiereException("User has more than one role assigned.");
		}
		final KeyNamePair userRole = userRoles.iterator().next();
		Check.assume(userRole != null && userRole.getKey() > 0, "Role shall exist: {}", userRole);
		final int adSessionId = Env.getAD_Session_ID(loginCtx);
		if (adSessionId <= 0)
		{
			throw new AdempiereException("Login failed: session was not created");
		}

		//
		// 1.1. Get/Generate a host key
		// NOTE: we need to create a static HostKey because Admins will be use that in configurations.
		String hostKey = loginRequest.getHostKey();
		if (Check.isEmpty(hostKey, true))
		{
			hostKey = Services.get(IHostKeyBL.class).generateHostKey();
		}
		Check.assumeNotEmpty(hostKey, "hostKey not empty");

		//
		// 2. Get AD_Clients
		// => Context update: AD_Role_ID
		final Set<KeyNamePair> userADClients = login.setRoleAndGetClients(userRole);
		if (userADClients == null || userADClients.isEmpty())
		{
			throw new AdempiereException("User is not assigned to an AD_Client");
		}
		else if (userADClients.size() != 1)
		{
			throw new AdempiereException("User is assigned to more than one AD_Client");
		}
		final KeyNamePair userADClient = userADClients.iterator().next();
		Check.assumeNotNull(userADClient, "userADClient not null");

		//
		// 3. Get AD_Orgs
		// => Context update: AD_Client_ID
		final Set<KeyNamePair> userOrgs = login.setClientAndGetOrgs(userADClient);
		final KeyNamePair userOrg;
		if (userOrgs == null || userOrgs.isEmpty())
		{
			throw new AdempiereException("User is not assigned to an AD_Org");
		}
		else if (userOrgs.size() != 1)
		{
			// if there are more then Orgs, we are going with organization "*"
			userOrg = new KeyNamePair(0, "*");
		}
		else
		{
			userOrg = userOrgs.iterator().next();
		}

		//
		// 4. Perform login
		// => AD_Session will get updated
		final boolean fireLoginComplete = false; // we don't need to because we are just doing a quick-login, server side. Before activating this, pls test and check the implications.
		final String loginError = login.validateLogin(userOrg, fireLoginComplete);
		if (!Check.isEmpty(loginError, true))
		{
			throw new AdempiereException("Login refused: " + loginError);
		}

		//
		// Update newly create AD_Session
		final I_AD_Session session = InterfaceWrapperHelper.create(MSession.get(ctx, adSessionId), I_AD_Session.class);
		Check.assumeNotNull(session, "session not null");
		session.setHostKey(hostKey);
		InterfaceWrapperHelper.save(session);

		//
		// Update the login request
		loginRequest.setPassword(null); // make sure the password is not saved
		loginRequest.setAD_Session_ID(adSessionId);
		loginRequest.setAD_User_ID(Env.getAD_User_ID(loginCtx));
	}
}
