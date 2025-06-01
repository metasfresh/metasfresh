/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.adempiere.serverRoot.servlet;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.bind.DatatypeConverter;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Server Monitor Filter. Application Server independent check of username/password
 *
 * @author Jorg Janke
 * @author Michael Judd BF [ 2736817 ] - remove deprecated BASE64Encoder classes
 * @author tsa
 */
@WebFilter({ "/serverMonitor/*", "/test/*" })
@Profile(Profiles.PROFILE_App)
public class ServerMonitorFilter implements Filter
{
	public ServerMonitorFilter()
	{
		super();
		authorizationKey = UUID.randomUUID().toString();
	}

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(ServerMonitorFilter.class);

	/** Authorization ID */
	private static final String ATTRIBUTE_AUTHORIZATIONKEY = "ServerAuthorization";
	/** Authorization Marker */
	private final String authorizationKey;

	private static final String errorPage = "/error.html";

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		log.info("");
	}	// Init

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		try
		{
			if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse))
			{
				request.getRequestDispatcher(errorPage).forward(request, response);
				return;
			}

			boolean pass = false;

			HttpServletRequest req = (HttpServletRequest)request;
			HttpServletResponse resp = (HttpServletResponse)response;
			// Previously checked
			HttpSession session = req.getSession(true);
			final String sessionAuthorizationKey = (String)session.getAttribute(ATTRIBUTE_AUTHORIZATIONKEY);
			if (sessionAuthorizationKey != null && sessionAuthorizationKey.equals(authorizationKey))
			{
				pass = true;
			}
			else if (checkAuthorization(req.getHeader("Authorization")))
			{
				session.setAttribute(ATTRIBUTE_AUTHORIZATIONKEY, authorizationKey);
				pass = true;
			}

			// --------------------------------------------
			if (pass)
			{
				chain.doFilter(request, response);
			}
			else
			{
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				resp.setHeader("WWW-Authenticate", "BASIC realm=\"System Administrator Authentication\"");
			}
			return;
		}
		catch (Exception e)
		{
			log.error("filter", e);
		}
		request.getRequestDispatcher(errorPage).forward(request, response);
	}	// doFilter

	/**
	 * Check Authorization
	 *
	 * @param authorization authorization
	 * @return true if authenticated
	 */
	private boolean checkAuthorization(final String authorization)
	{
		if (authorization == null)
		{
			return false;
		}
		try
		{
			final String userInfo = authorization.substring(6).trim();
			final String nameAndPassword = new String(DatatypeConverter.parseBase64Binary(userInfo));

			final int index = nameAndPassword.indexOf(':');
			final String name = nameAndPassword.substring(0, index);
			final HashableString password = HashableString.ofPlainValue(nameAndPassword.substring(index + 1));

			final IUserDAO usersRepo = Services.get(IUserDAO.class);
			final I_AD_User user = usersRepo.retrieveLoginUserByUserId(name);
			if (user == null)
			{
				log.warn("User not found: {}", name);
				return false;
			}

			final IUserBL usersBL = Services.get(IUserBL.class);
			if (!usersBL.isPasswordMatching(user, password))
			{
				log.warn("Password did not match: {}", name);
				return false;
			}

			final ClientId clientId = Env.getClientId();
			final UserId userId = UserId.ofRepoId(user.getAD_User_ID());
			final LocalDate date = Env.getLocalDate();
			if (!Services.get(IUserRolePermissionsDAO.class).isAdministrator(clientId, userId, date))
			{
				log.warn("Not a Sys Admin: {}", name);
				return false;
			}
			else
			{
				log.info("Authorization OK: {}", name);
				return true;
			}
		}
		catch (final Exception ex)
		{
			log.error("Authorization failed", ex);
			return false;
		}
	}	// check

	/**
	 * Destroy
	 */
	@Override
	public void destroy()
	{
		log.info("");
	}	// destroy

}
