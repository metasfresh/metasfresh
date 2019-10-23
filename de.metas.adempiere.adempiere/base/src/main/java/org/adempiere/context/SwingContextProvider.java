package org.adempiere.context;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;

import de.metas.util.AbstractPropertiesProxy;
import de.metas.util.Check;

/**
 * A simple {@link ContextProvider} provider suitable for Swing client.
 *
 * Basically it creates only one context which will be used in entire application. Only for {@link #switchContext(Properties)}, a thread-local ctx is created.
 *
 * @author tsa
 */
public class SwingContextProvider implements ContextProvider
{
	private final Properties rootCtx = new Properties();
	private final InheritableThreadLocal<Properties> temporaryCtxHolder = new InheritableThreadLocal<>();

	private final AbstractPropertiesProxy ctxProxy = new AbstractPropertiesProxy()
	{
		@Override
		protected Properties getDelegate()
		{
			return getActualContext();
		}

		private static final long serialVersionUID = 0;
	};

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
		return rootCtx;
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
		rootCtx.clear();
	}

}
