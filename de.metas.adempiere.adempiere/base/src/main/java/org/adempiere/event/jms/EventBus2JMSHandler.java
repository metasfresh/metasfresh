package org.adempiere.event.jms;

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


import org.adempiere.event.Event;
import org.adempiere.event.IEventBus;
import org.adempiere.event.IEventListener;
import org.adempiere.util.Check;

/**
 * Forward {@link Event}s from {@link IEventBus} to JMS.
 * @author tsa
 *
 */
class EventBus2JMSHandler implements IEventListener
{
	private final IJMSEndpoint jms;

	public EventBus2JMSHandler(final IJMSEndpoint jms)
	{
		super();
		Check.assumeNotNull(jms, "jms not null");
		this.jms = jms;
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final String topicName = eventBus.getName();
		jms.sendEvent(topicName, event);
	}
}
