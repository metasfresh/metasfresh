package org.adempiere.ad.persistence.po;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.IContextAware;

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
	private static final NoDataFoundHandlers INSTANCE = new NoDataFoundHandlers();

	public static NoDataFoundHandlers get()
	{
		return INSTANCE;
	}

	private final List<INoDataFoundHandler> noDataFoundHandlers = new ArrayList<>();

	private NoDataFoundHandlers()
	{
	};

	public NoDataFoundHandlers addHandler(INoDataFoundHandler handler)
	{
		noDataFoundHandlers.add(handler);
		return this;
	}

	/**
	 * Invoke all registered handlers on the given parameters. If one of them returns {@code true}, then this method also returns {@code true}.
	 * In any case, all handlers are invoked.
	 * <p>
	 * Hint: the caller of this method might want to throw a {@link NoDataFoundHandlerRetryRequestException} if this method returned {@code true}.
	 * 
	 * @param tableName
	 * @param ids
	 * @param ctx
	 * @return
	 */
	public boolean invokeHandlers(String tableName, Object[] ids, IContextAware ctx)
	{
		boolean atLeastOneHandlerFixedIt = false;
		for (final INoDataFoundHandler handler : noDataFoundHandlers)
		{
			if (handler.invoke(tableName, ids, ctx) && !atLeastOneHandlerFixedIt)
			{
				atLeastOneHandlerFixedIt = true;
			}
		}
		return atLeastOneHandlerFixedIt;
	}
}
