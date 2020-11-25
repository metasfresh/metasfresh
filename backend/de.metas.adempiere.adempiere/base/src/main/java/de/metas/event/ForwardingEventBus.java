package de.metas.event;

import java.util.function.Consumer;

import org.slf4j.MDC.MDCCloseable;

import de.metas.event.impl.EventMDC;
import lombok.NonNull;

/**
 * Forwarding {@link IEventBus} template implementation.
 *
 * @author tsa
 *
 */
abstract class ForwardingEventBus implements IEventBus
{
	private final IEventBus delegate;

	public ForwardingEventBus(@NonNull final IEventBus delegate)
	{
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
	public String getTopicName()
	{
		return delegate().getTopicName();
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
	public <T> void subscribeOn(final Class<T> type, final Consumer<T> eventConsumer)
	{
		delegate().subscribeOn(type, eventConsumer);
	}

	@Override
	public void unsubscribe(final IEventListener listener)
	{
		delegate().unsubscribe(listener);
	}

	@Override
	public void postEvent(final Event event)
	{
		try (final MDCCloseable mdc = EventMDC.putEvent(event))
		{
			delegate().postEvent(event);
		}
	}

	@Override
	public void postObject(final Object obj)
	{
		delegate().postObject(obj);
	}

	@Override
	public boolean isDestroyed()
	{
		return delegate().isDestroyed();
	}

	@Override
	public boolean isAsync()
	{
		return delegate().isAsync();
	}

	@Override
	public EventBusStats getStats()
	{
		return delegate().getStats();
	}
}
