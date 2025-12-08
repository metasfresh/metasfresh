package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class PickingCustomerConfigsCollection implements Iterable<PickingCustomerConfig>
{
	public static final PickingCustomerConfigsCollection EMPTY = new PickingCustomerConfigsCollection(ImmutableList.of());

	private final ImmutableMap<BPartnerId, PickingCustomerConfig> byCustomerId;

	public PickingCustomerConfigsCollection(final Collection<PickingCustomerConfig> collection)
	{
		this.byCustomerId = Maps.uniqueIndex(collection, PickingCustomerConfig::getCustomerId);
	}

	public static PickingCustomerConfigsCollection ofCollection(final Collection<PickingCustomerConfig> collection)
	{
		return !collection.isEmpty() ? new PickingCustomerConfigsCollection(collection) : EMPTY;
	}

	public static Collector<PickingCustomerConfig, ?, PickingCustomerConfigsCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PickingCustomerConfigsCollection::ofCollection);
	}

	@Override
	@NonNull
	public Iterator<PickingCustomerConfig> iterator() {return byCustomerId.values().iterator();}

	public Optional<PickingJobOptionsId> getPickingJobOptionsId(@NonNull final BPartnerId customerId)
	{
		return Optional.ofNullable(byCustomerId.get(customerId))
				.map(PickingCustomerConfig::getPickingJobOptionsId);
	}

	@NonNull
	public ImmutableSet<BPartnerId> getCustomerIds() {return byCustomerId.keySet();}
}
