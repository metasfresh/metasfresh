package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class ShippingNotificationListenersRegistry
{
	private static final Logger logger = LogManager.getLogger(ShippingNotificationListenersRegistry.class);
	private final CompositeShippingNotificationListener listeners;

	public ShippingNotificationListenersRegistry(
			@NonNull final Optional<List<ShippingNotificationListener>> optionalListners)
	{
		this.listeners = new CompositeShippingNotificationListener(optionalListners);
		logger.info("Listeners: {}", this.listeners);
	}

	public void fireAfterComplete(@NonNull final ShippingNotification shippingNotification)
	{
		listeners.onAfterComplete(shippingNotification);
	}

	public void fireAfterReverse(@NonNull final ShippingNotification reversal, @NonNull final ShippingNotification original)
	{
		listeners.onAfterReverse(reversal, original);
	}

	//
	//
	//

	@ToString(of = "list")
	private static class CompositeShippingNotificationListener implements ShippingNotificationListener
	{
		private final ImmutableList<ShippingNotificationListener> list;

		public CompositeShippingNotificationListener(
				@NonNull final Optional<List<ShippingNotificationListener>> optionalListners)
		{
			this.list = optionalListners.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		}

		@Override
		public void onAfterComplete(@NonNull final ShippingNotification shippingNotification)
		{
			list.forEach(listener -> listener.onAfterComplete(shippingNotification));
		}

		@Override
		public void onAfterReverse(@NonNull final ShippingNotification reversal, @NonNull final ShippingNotification original)
		{
			list.forEach(listener -> listener.onAfterReverse(reversal, original));
		}
	}
}
