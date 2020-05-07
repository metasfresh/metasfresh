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
package org.adempiere.serverRoot.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.logging.LogManager;

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
			final String namePassword = new String(DatatypeConverter.parseBase64Binary(userInfo));

			// log.debug("checkAuthorization - Name:Password=" + namePassword);
			final int index = namePassword.indexOf(':');
			final String name = namePassword.substring(0, index);
			final String password = namePassword.substring(index + 1);
			final I_AD_User user = Services.get(IUserDAO.class).retrieveLoginUserByUserIdAndPassword(name, password);
			if (user == null)
			{
				log.warn("User not found or password did not match: {}", name);
				return false;
			}

			if(!Services.get(IUserRolePermissionsDAO.class).isAdministrator(Env.getCtx(), user.getAD_User_ID()))
			{
				log.warn("Not a Sys Admin: {}", name);
				return false;
			}
			log.info("Authorization OK: {}", name);
			return true;
		}
		catch (final Exception ex)
		{
			log.error("Authorization failed", ex);
		}
		return false;
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
