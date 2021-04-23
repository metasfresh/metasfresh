package de.metas.event;

import de.metas.event.impl.EventMDC;
import lombok.NonNull;
import org.slf4j.MDC.MDCCloseable;

import java.util.function.Consumer;

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
	public <T> IEventListener subscribeOn(final Class<T> type, final Consumer<T> eventConsumer)
	{
		return delegate().subscribeOn(type, eventConsumer);
	}

	@Override
	public void unsubscribe(final IEventListener listener)
	{
		delegate().unsubscribe(listener);
	}

	@Override
	public void postEvent(final Event event)
	{
		try (final MDCCloseable ignored = EventMDC.putEvent(event))
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
