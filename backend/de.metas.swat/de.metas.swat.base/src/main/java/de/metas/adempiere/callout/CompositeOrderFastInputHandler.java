package de.metas.adempiere.callout;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.apps.search.impl.CompositeGridTabRowBuilder;
import org.compiere.model.GridTab;

public class CompositeOrderFastInputHandler implements IOrderFastInputHandler
{
	private final List<IOrderFastInputHandler> handlers = new ArrayList<IOrderFastInputHandler>();

	/**
	 * This is the default handler. This composite handler give precedence to all other handlers that are explicitly added.
	 */
	private final IOrderFastInputHandler defaultHandler;

	/**
	 * 
	 * @param defaultHandler this handler will be invoked last
	 */
	public CompositeOrderFastInputHandler(final IOrderFastInputHandler defaultHandler)
	{
		super();
		Check.assumeNotNull(defaultHandler, "defaultHandler not null");
		this.defaultHandler = defaultHandler;
	}
	
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void addHandler(final IOrderFastInputHandler handler)
	{
		Check.assumeNotNull(handler, "handler not null");

		if (handler.equals(defaultHandler))
		{
			return;
		}

		if (handlers.contains(handler))
		{
			// already registered
			return;
		}

		handlers.add(handler);
	}

	@Override
	public void clearFields(GridTab gridTab)
	{
		for (IOrderFastInputHandler handler : handlers)
		{
			handler.clearFields(gridTab);
		}
		defaultHandler.clearFields(gridTab);
	}

	@Override
	public boolean requestFocus(final GridTab gridTab)
	{
		for (final IOrderFastInputHandler handler : handlers)
		{
			final boolean requested = handler.requestFocus(gridTab);
			if (requested)
			{
				return true;
			}
		}
		return defaultHandler.requestFocus(gridTab);
	}

	@Override
	public IGridTabRowBuilder createLineBuilderFromHeader(final Object model)
	{
		final CompositeGridTabRowBuilder builders = new CompositeGridTabRowBuilder();

		for (final IOrderFastInputHandler handler : handlers)
		{
			final IGridTabRowBuilder builder = handler.createLineBuilderFromHeader(model);
			builders.addGridTabRowBuilder(builder);
		}
		builders.addGridTabRowBuilder(defaultHandler.createLineBuilderFromHeader(model));
		return builders;
	}
}
