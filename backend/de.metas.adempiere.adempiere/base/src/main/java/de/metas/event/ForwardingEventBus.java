package de.metas.event;

import de.metas.event.impl.EventMDC;
import lombok.NonNull;
import org.slf4j.MDC.MDCCloseable;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Forwarding {@link IEventBus} template implementation.
 *
 * @author tsa
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
	@NonNull
	public Topic getTopic()
	{
		return delegate().getTopic();
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
	public void processEvent(final Event event)
	{
		try (final MDCCloseable ignored = EventMDC.putEvent(event))
		{
			delegate().processEvent(event);
		}
	}

	@Override
	public void enqueueEvent(final Event event)
	{
		try (final MDCCloseable ignored = EventMDC.putEvent(event))
		{
			delegate().enqueueEvent(event);
		}
	}

	@Override
	public void enqueueObject(final Object obj)
	{
		delegate().enqueueObject(obj);
	}

	@Override
	public void enqueueObjectsCollection(@NonNull final Collection<?> objs)
	{
		delegate().enqueueObjectsCollection(objs);
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
