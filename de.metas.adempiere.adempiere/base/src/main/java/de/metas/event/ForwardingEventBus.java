package de.metas.event;

import java.util.function.Consumer;

import de.metas.util.Check;

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
	@Deprecated
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
	public void postObject(final Object obj)
	{
		delegate().postObject(obj);
	}

	@Override
	public boolean isDestroyed()
	{
		return delegate().isDestroyed();
	}
}
