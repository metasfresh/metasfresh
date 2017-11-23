package org.adempiere.impexp.impl;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.impexp.IImportListener;

import com.google.common.base.MoreObjects;

import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class CompositeImportListener implements IImportListener
{

	public static final IImportListener compose(final IImportListener handler, final IImportListener handlerToAdd)
	{
		if (handler == null)
		{
			return handlerToAdd;
		}
		if (handlerToAdd == null)
		{
			return handler;
		}

		if (handler instanceof CompositeImportListener)
		{
			final CompositeImportListener handlerComposite = (CompositeImportListener)handler;
			handlerComposite.addHandler(handlerToAdd);
			return handlerComposite;
		}
		else
		{
			final CompositeImportListener handlerComposite = new CompositeImportListener();
			handlerComposite.addHandler(handler);
			handlerComposite.addHandler(handlerToAdd);
			return handlerComposite;
		}
	}

	private final CopyOnWriteArrayList<IImportListener> handlers = new CopyOnWriteArrayList<>();

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("handlers", handlers)
				.toString();
	}

	public void addHandler(@NonNull final IImportListener handler)
	{
		handlers.addIfAbsent(handler);
	}

	@Override
	public void onImport(Object importRecord, Object model)
	{
		handlers.forEach(h -> h.onImport(importRecord, model));
	}
}
