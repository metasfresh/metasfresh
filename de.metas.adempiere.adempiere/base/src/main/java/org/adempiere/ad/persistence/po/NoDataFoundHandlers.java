package org.adempiere.ad.persistence.po;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.lang.IContextAware;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Singleton where {@link INoDataFoundHandler}s can be registered (see {@link #addHandler(INoDataFoundHandler)}) and invoked (see {@link #invokeHandlers(String, Object[], IContextAware)}).
 * Note that it's up to the caller to determine if the handlers actually need to be called.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class NoDataFoundHandlers
{
	private static final transient Logger logger = LogManager.getLogger(NoDataFoundHandlers.class);

	private static final NoDataFoundHandlers INSTANCE = new NoDataFoundHandlers();

	/**
	 * Our registered handlers
	 */
	private final List<INoDataFoundHandler> noDataFoundHandlers = new CopyOnWriteArrayList<>();

	/**
	 * Used to avoid StackOverflowErrors. See https://github.com/metasfresh/metasfresh/issues/1076.
	 */
	private final ThreadLocal<Map<INoDataFoundHandler, Set<ArrayKey>>> currentlyActiveHandlers = ThreadLocal.withInitial(() -> new IdentityHashMap<>());

	public static NoDataFoundHandlers get()
	{
		return INSTANCE;
	}

	private NoDataFoundHandlers()
	{
	};

	public NoDataFoundHandlers addHandler(final INoDataFoundHandler handler)
	{
		noDataFoundHandlers.add(handler);
		return this;
	}

	/**
	 * Invoke all registered handlers on the given parameters. If one of them returns {@code true}, then this method also returns {@code true}.
	 * In any case, all handlers are invoked.
	 * <p>
	 * Hint: the caller of this method might want to throw a {@link NoDataFoundHandlerRetryRequestException} if this method returned {@code true} to it.
	 *
	 * @param tableName
	 * @param ids
	 * @param ctx
	 * @return
	 */
	public boolean invokeHandlers(final String tableName, final Object[] ids, final IContextAware ctx)
	{
		boolean atLeastOneHandlerFixedIt = false;
		for (final INoDataFoundHandler currentHandler : noDataFoundHandlers)
		{
			final boolean currentHandlerFixedIt = invokeCurrentHandler(currentHandler, tableName, ids, ctx);

			if (currentHandlerFixedIt && !atLeastOneHandlerFixedIt)
			{
				atLeastOneHandlerFixedIt = true;
			}
		}
		return atLeastOneHandlerFixedIt;
	}

	/**
	 * Invoke the current handler, unless the current invocation is already happening within an earlier invocation.
	 * So this method might actually <i>not</i> call the given handler in order to avoid a {@link StackOverflowError} .
	 * 
	 * @param handler
	 * @param tableName
	 * @param ids
	 * @param ctx
	 * @return
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1076
	 */
	@VisibleForTesting
	/* package */ boolean invokeCurrentHandler(final INoDataFoundHandler handler,
			final String tableName,
			final Object[] ids,
			final IContextAware ctx)
	{
		final ArrayKeyBuilder keyBuilder = ArrayKey.builder().append(tableName);
		for (final Object id : ids)
		{
			keyBuilder.append(id);
		}
		final ArrayKey key = keyBuilder.build();

		final Set<ArrayKey> currentlyInvokedOnRecords = currentlyActiveHandlers.get()
				.computeIfAbsent(handler, h -> new HashSet<>());

		if (!currentlyInvokedOnRecords.add(key))
		{
			logger.warn(
					"The current handler was already invoked with tableName={} and ids={} earlier in this same stack. Returning to avoid a stack overflowError; key={}; handler={}",
					tableName, ids, key, handler);
			return false;
		}

		try
		{
			return handler.invoke(tableName, ids, ctx);
		}
		finally
		{
			currentlyInvokedOnRecords.remove(key);
		}
	}
}
