package de.metas.event;

import java.util.function.Consumer;

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

/**
 * Forwarding {@link IEventBus} template implementation.
 *
 * @author tsa
 *
 */
abstract class ForwardingEventBus implements IEventBus
{
	private final IEventBus delegate;

	public ForwardingEventBus(final IEventBus delegate)
	{
		super();
		Check.assumeNotNull(delegate, "delegate not null");
		this.delegate = delegate;
	}

	protected final IEventBus delegate()
	{
		return delegate;
	}

	@Override
	public String toString()
	{
		return delegate().toString();
	}

	@Override
	public String getName()
	{
		return delegate().getName();
	}

	@Override
	public Type getType()
	{
		return delegate().getType();
	}

	@Override
	public void subscribe(final IEventListener listener)
	{
		delegate().subscribe(listener);
	}
	
	@Override
	public void subscribe(final Consumer<Event> eventConsumer)
	{
		delegate().subscribe(eventConsumer);
	}

	@Override
	public void subscribeWeak(final IEventListener listener)
	{
		delegate().subscribeWeak(listener);
	}

	@Override
	public void postEvent(final Event event)
	{
		delegate().postEvent(event);
	}

	@Override
	public boolean isDestroyed()
	{
		return delegate().isDestroyed();
	}
}
