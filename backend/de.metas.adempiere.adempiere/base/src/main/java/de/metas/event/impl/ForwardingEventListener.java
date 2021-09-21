package de.metas.event.impl;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * Forward {@link Event}s to a given {@link EventBus}.
 * 
 * @author tsa
 *
 */
class ForwardingEventListener implements IEventListener
{
	private final EventBus eventBusTo;

	public ForwardingEventListener(@NonNull final EventBus eventBusTo)
	{
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
