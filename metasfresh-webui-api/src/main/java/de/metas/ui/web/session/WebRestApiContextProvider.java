package de.metas.ui.web.session;

import java.io.Serializable;
import java.util.Properties;

import org.adempiere.context.ContextProvider;
import org.adempiere.util.AbstractPropertiesProxy;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
public class WebRestApiContextProvider implements ContextProvider, Serializable
{
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

	private final Properties serverCtx = new Properties();

	private static final String ATTRIBUTE_UserSessionCtx = WebRestApiContextProvider.class.getName() + ".UserSessionCtx";

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
		final Properties temporaryCtx = temporaryCtxHolder.get();
		if (temporaryCtx != null)
		{
			return temporaryCtx;
		}

		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null)
		{
			Properties userSessionCtx = (Properties)requestAttributes.getAttribute(ATTRIBUTE_UserSessionCtx, RequestAttributes.SCOPE_SESSION);
			if (userSessionCtx == null)
			{
				userSessionCtx = new Properties();
				requestAttributes.setAttribute(ATTRIBUTE_UserSessionCtx, userSessionCtx, RequestAttributes.SCOPE_SESSION);
			}

			return userSessionCtx;
		}

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
			return NullAutoCloseable.instance;
		}

		final Properties previousTempCtx = temporaryCtxHolder.get();

		temporaryCtxHolder.set(ctx);

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
			}
		};
	}

	@Override
	public void reset()
	{
		temporaryCtxHolder.remove();
		serverCtx.clear();
	}
}
