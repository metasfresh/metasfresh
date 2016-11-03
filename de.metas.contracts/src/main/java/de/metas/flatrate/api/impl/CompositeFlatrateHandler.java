package de.metas.flatrate.api.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.flatrate.api.IFlatrateHandler;
import de.metas.flatrate.model.I_C_Flatrate_Term;

/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class CompositeFlatrateHandler implements IFlatrateHandler
{
	public static final IFlatrateHandler compose(final IFlatrateHandler handler, final IFlatrateHandler handlerToAdd)
	{
		if (handler == null)
		{
			return handlerToAdd;
		}
		if (handlerToAdd == null)
		{
			return handler;
		}

		if (handler instanceof CompositeFlatrateHandler)
		{
			final CompositeFlatrateHandler handlerComposite = (CompositeFlatrateHandler)handler;
			handlerComposite.addHandler(handlerToAdd);
			return handlerComposite;
		}
		else
		{
			final CompositeFlatrateHandler handlerComposite = new CompositeFlatrateHandler();
			handlerComposite.addHandler(handler);
			handlerComposite.addHandler(handlerToAdd);
			return handlerComposite;
		}
	}

	private final CopyOnWriteArrayList<IFlatrateHandler> handlers = new CopyOnWriteArrayList<>();

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("handlers", handlers)
				.toString();
	}

	public void addHandler(final IFlatrateHandler handler)
	{
		Check.assumeNotNull(handler, "handler not null");
		handlers.addIfAbsent(handler);
	}

	@Override
	public void beforeFlatrateTermReactivate(final I_C_Flatrate_Term term)
	{
		handlers.forEach(h -> h.beforeFlatrateTermReactivate(term));
	}

	@Override
	public void afterExtendFlatrateTermCreated(I_C_Flatrate_Term oldTerm, I_C_Flatrate_Term newTerm)
	{
		handlers.forEach(h -> h.afterExtendFlatrateTermCreated(oldTerm, newTerm));
	}
}
