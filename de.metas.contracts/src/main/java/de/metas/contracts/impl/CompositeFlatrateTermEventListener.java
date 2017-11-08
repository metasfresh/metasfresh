package de.metas.contracts.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.spi.IFlatrateTermEventListener;

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

final class CompositeFlatrateTermEventListener implements IFlatrateTermEventListener
{
	public static final IFlatrateTermEventListener compose(final IFlatrateTermEventListener handler, final IFlatrateTermEventListener handlerToAdd)
	{
		if (handler == null)
		{
			return handlerToAdd;
		}
		if (handlerToAdd == null)
		{
			return handler;
		}

		if (handler instanceof CompositeFlatrateTermEventListener)
		{
			final CompositeFlatrateTermEventListener handlerComposite = (CompositeFlatrateTermEventListener)handler;
			handlerComposite.addHandler(handlerToAdd);
			return handlerComposite;
		}
		else
		{
			final CompositeFlatrateTermEventListener handlerComposite = new CompositeFlatrateTermEventListener();
			handlerComposite.addHandler(handler);
			handlerComposite.addHandler(handlerToAdd);
			return handlerComposite;
		}
	}

	private final CopyOnWriteArrayList<IFlatrateTermEventListener> handlers = new CopyOnWriteArrayList<>();

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("handlers", handlers)
				.toString();
	}

	public void addHandler(final IFlatrateTermEventListener handler)
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
	public void afterSaveOfNextTermForPredecessor(I_C_Flatrate_Term next, I_C_Flatrate_Term predecessor)
	{
		handlers.forEach(h -> h.afterSaveOfNextTermForPredecessor(next, predecessor));
	}
	
	@Override
	public void afterFlatrateTermEnded(I_C_Flatrate_Term term)
	{
		handlers.forEach(h -> h.afterFlatrateTermEnded(term));
	}
	
	@Override
	public void beforeSaveOfNextTermForPredecessor(I_C_Flatrate_Term next,  I_C_Flatrate_Term predecessor)
	{
		handlers.forEach(h -> h.beforeSaveOfNextTermForPredecessor(next,predecessor));
	}
}
