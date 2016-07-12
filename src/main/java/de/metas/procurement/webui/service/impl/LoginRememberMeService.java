package de.metas.procurement.webui.service.impl;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.base.Throwables;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;

import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 *
 * @author metas-dev <dev@metas-fresh.com>
 * @task https://metasfresh.atlassian.net/browse/FRESH-197
 */
@Service
public class LoginRememberMeService
{
	public static final String COOKIENAME_RememberMe = "rememberMe";

	@Value("${mfprocurement.login.rememberMe.enabled:true}")
	private boolean enabled;

	@Value("${mfprocurement.login.rememberMe.cookieMaxAgeDays:365}")
	private long cookieMaxAgeDays;

	private static final Logger logger = LoggerFactory.getLogger(LoginRememberMeService.class);

	@Autowired
	@Lazy
	private ObjectMapper jsonObjectMapper;

	@Autowired
	@Lazy
	private ILoginService loginService;

	public boolean isEnabled()
	{
		if (!enabled)
		{
			return false;
		}

		final Page page = Page.getCurrent();
		if (page != null && page.getWebBrowser().isChrome())
		{
			logger.trace("Considering feature disabled for chome because Chrome's password manager is known to work");
			return false;
		}

		return true;
	}

	public RememberMeToken getRememberMeTokenIfEnabled()
	{
		if (!isEnabled())
		{
			logger.trace("Returning no token because feature is not enabled");
			return null;
		}

		final String rememberMeToken = getRememberMeCookieValue();
		if(rememberMeToken == null)
		{
			logger.trace("Returning no cookie");
			return null;
		}
		try
		{
			final RememberMeToken token = jsonObjectMapper.readValue(rememberMeToken, RememberMeToken.class);
			logger.trace("Returning {} for string: {}", token, rememberMeToken);
			return token;
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}
	
	private String getRememberMeCookieValue()
	{
		final Cookie cookie = getRememberMeCookie();
		if (cookie == null)
		{
			return null;
		}
		final String rememberMeToken = cookie.getValue();
		return rememberMeToken;
	}

	private Cookie getRememberMeCookie()
	{
		final VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
		if(vaadinRequest == null)
		{
			logger.warn("Could not get the VaadinRequest. It might be that we are called from a websocket connection.");
			return null;
		}
		
		//
		// Get the remember me cookie
		final Cookie[] cookies = vaadinRequest.getCookies();
		if (cookies == null)
		{
			return null;
		}

		for (final Cookie cookie : cookies)
		{
			if (COOKIENAME_RememberMe.equals(cookie.getName()))
			{
				return cookie;
			}
		}

		return null;
	}

	private String createRememberMeToken(final User user)
	{
		final String passwordHash = loginService.createPasswordHash(user.getPassword());
		final RememberMeToken token = new RememberMeToken();
		token.setUser(user.getEmail());
		token.setToken(passwordHash);

		try
		{
			final String tokenAsString = jsonObjectMapper.writeValueAsString(token);
			return tokenAsString;
		}
		catch (final JsonProcessingException e)
		{
			throw Throwables.propagate(e);
		}
	}

	public void createRememberMeCookieIfEnabled(final User user)
	{
		if (isEnabled())
		{
			createRememberMeCookie(user);
		}
		else
		{
			removeRememberMeCookie();
		}
	}

	private void createRememberMeCookie(final User user)
	{
		try
		{
			final String rememberMeToken = createRememberMeToken(user);
			final Cookie rememberMeCookie = new Cookie(COOKIENAME_RememberMe, rememberMeToken);
			
			final int maxAge = (int)TimeUnit.SECONDS.convert(cookieMaxAgeDays, TimeUnit.DAYS);
			rememberMeCookie.setMaxAge(maxAge);
			
			final String path = "/"; // (VaadinService.getCurrentRequest().getContextPath());
			rememberMeCookie.setPath(path); 
			VaadinService.getCurrentResponse().addCookie(rememberMeCookie);
			logger.debug("Cookie added for {}: {} (maxAge={}, path={})", user, rememberMeToken, maxAge, path);
		}
		catch (final Exception e)
		{
			logger.warn("Failed creating cookie for user: {}. Skipped.", user, e);
		}
	}

	private void removeRememberMeCookie()
	{
		try
		{
			Cookie cookie = getRememberMeCookie();
			if (cookie == null)
			{
				return;
			}

			cookie = new Cookie(COOKIENAME_RememberMe, null);
			cookie.setValue(null);
			cookie.setMaxAge(0); // by setting the cookie maxAge to 0 it will deleted immediately
			cookie.setPath("/");
			VaadinService.getCurrentResponse().addCookie(cookie);
			
			logger.debug("Cookie removed");
		}
		catch (final Exception e)
		{
			logger.warn("Failed removing the cookie", e);
		}
	}

	public static class RememberMeToken
	{
		private String user;
		private String token;

		@Override
		public String toString()
		{
			return Objects.toStringHelper(this)
					.add("user", user)
					.add("token", token)
					.toString();
		}

		public String getUser()
		{
			return user;
		}

		public void setUser(final String user)
		{
			this.user = user;
		}

		public String getToken()
		{
			return token;
		}

		public void setToken(final String token)
		{
			this.token = token;
		}
	}
}
