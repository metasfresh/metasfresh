package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public final class ShippingNotificationCollection implements Iterable<ShippingNotification>
{
	public static final ShippingNotificationCollection EMPTY = new ShippingNotificationCollection(ImmutableList.of());
	private final ImmutableList<ShippingNotification> list;

	private ShippingNotificationCollection(final ImmutableList<ShippingNotification> list)
	{
		this.list = list;
	}

	public static ShippingNotificationCollection ofCollection(final Collection<ShippingNotification> collection)
	{
		return !collection.isEmpty()
				? new ShippingNotificationCollection(ImmutableList.copyOf(collection))
				: EMPTY;
	}

	public static Collector<ShippingNotification, ?, ShippingNotificationCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ShippingNotificationCollection::ofCollection);
	}

	@NonNull
	@Override
	public Iterator<ShippingNotification> iterator() {return list.iterator();}
}
