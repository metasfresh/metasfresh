package de.metas.event.jms;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import lombok.NonNull;

/**
 * Forward {@link Event}s from {@link IEventBus} to JMS.
 *
 * @author tsa
 *
 */
class EventBus2JMSHandler implements IEventListener
{
	private final IJMSEndpoint jms;

	public EventBus2JMSHandler(@NonNull final IJMSEndpoint jms)
	{
		this.jms = jms;
	}

	@Override
	public void onEvent(final IEventBus eventBus, final Event event)
	{
		final String topicName = eventBus.getName();
		jms.sendEvent(topicName, event);
	}
}
