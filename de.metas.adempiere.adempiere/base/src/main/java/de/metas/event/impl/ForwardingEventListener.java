package de.metas.event.impl;

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


import org.adempiere.util.Check;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;

/**
 * Forward {@link Event}s to a given {@link EventBus}.
 * 
 * @author tsa
 *
 */
class ForwardingEventListener implements IEventListener
{
	private final EventBus eventBusTo;

	public ForwardingEventListener(final EventBus eventBusTo)
	{
		super();

		Check.assumeNotNull(eventBusTo, "eventBusTo not null");
		this.eventBusTo = eventBusTo;
	}

	@Override
	public void onEvent(final IEventBus eventBusFrom, final Event event)
	{
		if (eventBusTo == eventBusFrom)
		{
			return;
		}

		this.eventBusTo.postEvent(event);
	}
}
