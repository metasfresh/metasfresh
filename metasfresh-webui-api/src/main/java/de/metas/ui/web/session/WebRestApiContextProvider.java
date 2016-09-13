package de.metas.ui.web.session;

import java.io.Serializable;
import java.util.Properties;

import org.adempiere.context.ContextProvider;
import org.adempiere.util.AbstractPropertiesProxy;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
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

@SuppressWarnings("serial")
public final class WebRestApiContextProvider implements ContextProvider, Serializable
{
	private static final Logger logger = LogManager.getLogger(WebRestApiContextProvider.class);

	private static final String ATTRIBUTE_UserSessionCtx = WebRestApiContextProvider.class.getName() + ".UserSessionCtx";
	private static final String CTXNAME_IsServerContext = "#IsWebuiServerContext";

	private final InheritableThreadLocal<Properties> temporaryCtxHolder = new InheritableThreadLocal<Properties>();
	private final AbstractPropertiesProxy ctxProxy = new AbstractPropertiesProxy()
	{
		@Override
		protected Properties getDelegate()
		{
			return getActualContext();
		}

		private static final long serialVersionUID = 0;
	};

	private final Properties serverCtx;

	public WebRestApiContextProvider()
	{
		super();

		//
		// Create the server context
		serverCtx = new Properties();
		Env.setContext(serverCtx, CTXNAME_IsServerContext, true);
	}

	@Override
	public void init()
	{
		// nothing to do here
	}

	@Override
	public Properties getContext()
	{
		return ctxProxy;
	}

	private final Properties getActualContext()
	{
		//
		// IMPORTANT: this method will be called very often, so please make sure it's FAST!
		//

		//
		// If there is currently a temporary context active, return it first
		final Properties temporaryCtx = temporaryCtxHolder.get();
		if (temporaryCtx != null)
		{
			logger.trace("Returning temporary context: {}", temporaryCtx);
			return temporaryCtx;
		}

		//
		// Get the context from current session
		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null)
		{
			Properties userSessionCtx = (Properties)requestAttributes.getAttribute(ATTRIBUTE_UserSessionCtx, RequestAttributes.SCOPE_SESSION);
			if (userSessionCtx == null)
			{
				// Create user session context
				userSessionCtx = new Properties();
				Env.setContext(userSessionCtx, CTXNAME_IsServerContext, false);

				requestAttributes.setAttribute(ATTRIBUTE_UserSessionCtx, userSessionCtx, RequestAttributes.SCOPE_SESSION);

				if (logger.isDebugEnabled())
				{
					logger.debug("Created user session context: sessionId={}, context={}", requestAttributes.getSessionId(), userSessionCtx);
				}
			}

			logger.trace("Returning user session context: {}", temporaryCtx);
			return userSessionCtx;
		}

		//
		// If there was no current session it means we are running on server side, so return the server context
		logger.trace("Returning server context: {}", temporaryCtx);
		return serverCtx;
	}

	@Override
	public IAutoCloseable switchContext(final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctx not null");

		// If we were asked to set the context proxy (the one which we are returning everytime),
		// then it's better to do nothing because this could end in a StackOverflowException.
		if (ctx == ctxProxy)
		{
			logger.trace("Not switching context because the given temporary context it's actually our context proxy: {}", ctx);
			return NullAutoCloseable.instance;
		}

		final Properties previousTempCtx = temporaryCtxHolder.get();

		temporaryCtxHolder.set(ctx);

		logger.trace("Switched to temporary context. \n New temporary context: {} \n Previous temporary context: {}", ctx, previousTempCtx);

		return new IAutoCloseable()
		{
			private boolean closed = false;

			@Override
			public void close()
			{
				if (closed)
				{
					return;
				}

				if (previousTempCtx != null)
				{
					temporaryCtxHolder.set(previousTempCtx);
				}
				else
				{
					temporaryCtxHolder.remove();
				}

				closed = true;
				
				logger.trace("Switched back from temporary context");
			}
		};
	}

	@Override
	public void reset()
	{
		temporaryCtxHolder.remove();
		serverCtx.clear();
		
		logger.debug("Reset done");
	}
}
