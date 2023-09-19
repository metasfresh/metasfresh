package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderAndLineId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
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

	public Quantity sumQtyByOrderLineId(
			@NonNull final OrderAndLineId salesOrderAndLineId,
			@NonNull final UomId targetUomId,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		return list.stream()
				.map(shippingNotification -> shippingNotification.getLineBySalesOrderLineId(salesOrderAndLineId).orElse(null))
				.filter(Objects::nonNull)
				.map(line -> uomConverter.convertQuantityTo(line.getQty(), line.getProductId(), targetUomId))
				.reduce(Quantity::add)
				.orElseGet(() -> Quantitys.createZero(targetUomId));
	}
}
