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

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.organization.OrgId;
import de.metas.printing.model.I_AD_User_Login;
import de.metas.security.Role;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.compiere.util.Login;

import java.util.List;
import java.util.Properties;
import java.util.Set;

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
	 * Task http://dewiki908/mediawiki/index.php/08283_Provide_Host_Independent_Print_Client
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
		final HashableString loginPassword = HashableString.ofPlainValue(loginRequest.getPassword());
		final Login login = new Login(loginCtx);
		final List<Role> userRoles = login.authenticate(loginUsername, loginPassword).getAvailableRoles();
		if (userRoles.isEmpty())
		{
			throw new AdempiereException("User has no roles assigned: " + loginUsername);
		}
		else if (userRoles.size() != 1)
		{
			// NOTE: don't show which roles are there because this message goes to client and it could be a security issue.
			throw new AdempiereException("User has more than one role assigned.");
		}
		final Role userRole = userRoles.iterator().next();
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
		final Set<ClientId> clientIds = login.setRoleAndGetClients(userRole.getId());
		if (clientIds == null || clientIds.isEmpty())
		{
			throw new AdempiereException("User is not assigned to an AD_Client");
		}
		else if (clientIds.size() != 1)
		{
			throw new AdempiereException("User is assigned to more than one AD_Client");
		}
		final ClientId clientId = clientIds.iterator().next();
		final String clientName = Services.get(IClientDAO.class).getClientNameById(clientId);

		//
		// 3. Get AD_Orgs
		// => Context update: AD_Client_ID
		final Set<OrgId> orgIds = login.setClientAndGetOrgs(clientId, clientName);
		final OrgId orgId;
		if (orgIds == null || orgIds.isEmpty())
		{
			throw new AdempiereException("User is not assigned to an AD_Org");
		}
		else if (orgIds.size() != 1)
		{
			// if there are more then Orgs, we are going with organization "*"
			orgId = OrgId.ANY;
		}
		else
		{
			orgId = orgIds.iterator().next();
		}

		//
		// 4. Perform login
		// => AD_Session will get updated
		final boolean fireLoginComplete = false; // we don't need to because we are just doing a quick-login, server side. Before activating this, pls test and check the implications.
		final String loginError = login.validateLogin(orgId, fireLoginComplete);
		if (!Check.isEmpty(loginError, true))
		{
			throw new AdempiereException("Login refused: " + loginError);
		}

		//
		// Update newly create AD_Session
		final MFSession session = Services.get(ISessionBL.class).getSessionById(ctx, adSessionId);
		Check.assumeNotNull(session, "session not null");
		session.setHostKey(hostKey, loginCtx);
		session.updateContext(loginCtx);

		//
		// Update the login request
		loginRequest.setPassword(null); // make sure the password is not saved
		loginRequest.setAD_Session_ID(adSessionId);
		loginRequest.setAD_User_ID(Env.getAD_User_ID(loginCtx));
	}
}
