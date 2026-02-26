package de.metas.picking.api;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
public class ShipmentScheduleAndJobScheduleIdSet implements Iterable<ShipmentScheduleAndJobScheduleId>
{
	public static final ShipmentScheduleAndJobScheduleIdSet EMPTY = new ShipmentScheduleAndJobScheduleIdSet(ImmutableSet.of());
	@NonNull private final ImmutableSet<ShipmentScheduleAndJobScheduleId> ids;

	private static final Splitter JSON_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

	private ShipmentScheduleAndJobScheduleIdSet(@NonNull final ImmutableSet<ShipmentScheduleAndJobScheduleId> ids)
	{
		this.ids = ids;
	}

	public static ShipmentScheduleAndJobScheduleIdSet ofCollection(@NonNull final Collection<ShipmentScheduleAndJobScheduleId> ids)
	{
		return ids.isEmpty() ? EMPTY : new ShipmentScheduleAndJobScheduleIdSet(ImmutableSet.copyOf(ids));
	}

	public static ShipmentScheduleAndJobScheduleIdSet of(@NonNull final ShipmentScheduleAndJobScheduleId id)
	{
		return new ShipmentScheduleAndJobScheduleIdSet(ImmutableSet.of(id));
	}

	public static ShipmentScheduleAndJobScheduleIdSet of(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return new ShipmentScheduleAndJobScheduleIdSet(ImmutableSet.of(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(shipmentScheduleId)));
	}

	public static ShipmentScheduleAndJobScheduleIdSet ofShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		return shipmentScheduleIds.stream().map(ShipmentScheduleAndJobScheduleId::ofShipmentScheduleId).collect(collect());
	}

	public static Collector<ShipmentScheduleAndJobScheduleId, ?, ShipmentScheduleAndJobScheduleIdSet> collect()
	{
		return GuavaCollectors.collectUsingHashSetAccumulator(ShipmentScheduleAndJobScheduleIdSet::ofCollection);
	}

	@Override
	@Deprecated
	public String toString() {return toJsonString();}

	@JsonValue
	public String toJsonString()
	{
		if (ids.isEmpty())
		{
			return "";
		}

		return ids.stream()
				.map(ShipmentScheduleAndJobScheduleId::toJsonString)
				.collect(Collectors.joining(","));
	}

	@JsonValue
	public static ShipmentScheduleAndJobScheduleIdSet ofNullableJsonString(@Nullable final String json)
	{
		try
		{
			final String jsonNorm = StringUtils.trimBlankToNull(json);
			if (jsonNorm == null)
			{
				return ShipmentScheduleAndJobScheduleIdSet.EMPTY;
			}

			return JSON_SPLITTER
					.splitToList(jsonNorm)
					.stream()
					.map(ShipmentScheduleAndJobScheduleId::ofJsonString)
					.collect(collect());
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid JSON: " + json, ex);
		}
	}

	public boolean isEmpty() {return ids.isEmpty();}

	public int size() {return ids.size();}

	@Override
	@NonNull
	public Iterator<ShipmentScheduleAndJobScheduleId> iterator() {return ids.iterator();}

	public Stream<ShipmentScheduleAndJobScheduleId> stream() {return ids.stream();}

	public Set<ShipmentScheduleAndJobScheduleId> toSet() {return ids;}

	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return streamShipmentScheduleIds().collect(ImmutableSet.toImmutableSet());
	}

	public Stream<ShipmentScheduleId> streamShipmentScheduleIds()
	{
		return ids.stream()
				.map(ShipmentScheduleAndJobScheduleId::getShipmentScheduleId)
				.distinct();
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIdsWithoutJobSchedules()
	{
		return ids.stream()
				.filter(scheduleId -> !scheduleId.isJobSchedule())
				.map(ShipmentScheduleAndJobScheduleId::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<PickingJobScheduleId> getJobScheduleIds()
	{
		return ids.stream()
				.map(ShipmentScheduleAndJobScheduleId::getJobScheduleId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<PickingJobScheduleId> getJobScheduleIds(final @NonNull ShipmentScheduleId shipmentScheduleId)
	{
		return ids.stream()
				.filter(id -> ShipmentScheduleId.equals(id.getShipmentScheduleId(), shipmentScheduleId))
				.map(ShipmentScheduleAndJobScheduleId::getJobScheduleId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void forEachShipmentScheduleId(@NonNull final BiConsumer<ShipmentScheduleId, Set<PickingJobScheduleId>> consumer)
	{
		final LinkedHashMap<ShipmentScheduleId, HashSet<PickingJobScheduleId>> map = new LinkedHashMap<>();

		ids.forEach(id -> {
			final HashSet<PickingJobScheduleId> jobScheduleIds = map.computeIfAbsent(id.getShipmentScheduleId(), key -> new HashSet<>());
			final PickingJobScheduleId jobScheduleId = id.getJobScheduleId();
			if (jobScheduleId != null)
			{
				jobScheduleIds.add(jobScheduleId);
			}
		});

		map.forEach(consumer);
	}

	public ShipmentScheduleAndJobScheduleIdSet retainOnlyShipmentScheduleIds(@NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (isEmpty()) {return this;}

		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		final ImmutableSet<ShipmentScheduleAndJobScheduleId> retainedIds = ids.stream()
				.filter(id -> shipmentScheduleIds.contains(id.getShipmentScheduleId()))
				.collect(ImmutableSet.toImmutableSet());

		return Objects.equals(this.ids, retainedIds)
				? this
				: ofCollection(retainedIds);
	}

	@Nullable
	public ShipmentScheduleAndJobScheduleId singleOrNull()
	{
		return ids.size() == 1 ? ids.iterator().next() : null;
	}
}
