package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementId;
import org.adempiere.warehouse.LocatorId;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class DDOrderMoveSchedulePickedHUs
{
	private final ImmutableMap<HuId, DDOrderMoveSchedulePickedHU> byActualHUIdPicked;

	private DDOrderMoveSchedulePickedHUs(@NonNull final List<DDOrderMoveSchedulePickedHU> list)
	{
		Check.assumeNotEmpty(list, "list is not empty");
		this.byActualHUIdPicked = Maps.uniqueIndex(list, DDOrderMoveSchedulePickedHU::getActualHUIdPicked);
	}

	public static DDOrderMoveSchedulePickedHUs ofList(@NonNull final List<DDOrderMoveSchedulePickedHU> list)
	{
		return new DDOrderMoveSchedulePickedHUs(list);
	}

	public static DDOrderMoveSchedulePickedHUs of(@NonNull final DDOrderMoveSchedulePickedHU pickedHU)
	{
		return new DDOrderMoveSchedulePickedHUs(ImmutableList.of(pickedHU));
	}

	public static Collector<DDOrderMoveSchedulePickedHU, ?, DDOrderMoveSchedulePickedHUs> collect() {return GuavaCollectors.collectUsingListAccumulator(DDOrderMoveSchedulePickedHUs::ofList);}

	public ImmutableCollection<DDOrderMoveSchedulePickedHU> toCollection()
	{
		return byActualHUIdPicked.values();
	}

	public Quantity getQtyPicked()
	{
		return byActualHUIdPicked.values()
				.stream()
				.map(DDOrderMoveSchedulePickedHU::getQtyPicked)
				.reduce(Quantity::add)
				.orElseThrow(() -> new IllegalStateException("empty list: " + this));
	}

	public boolean isDroppedTo()
	{
		return byActualHUIdPicked.values().stream().allMatch(DDOrderMoveSchedulePickedHU::isDroppedTo);
	}

	public void setDropToMovementId(@NonNull final MovementId dropToMovementId)
	{
		for (final DDOrderMoveSchedulePickedHU pickedHU : byActualHUIdPicked.values())
		{
			pickedHU.setDropToMovementId(dropToMovementId);
		}
	}

	public LocatorId getInTransitLocatorId()
	{
		final ImmutableSet<LocatorId> inTransitLocatorIds = byActualHUIdPicked.values()
				.stream()
				.map(DDOrderMoveSchedulePickedHU::getInTransitLocatorId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (inTransitLocatorIds.isEmpty())
		{
			// shall not happen
			throw new AdempiereException("No in transit locator found for " + this);
		}
		else if (inTransitLocatorIds.size() == 1)
		{
			return inTransitLocatorIds.iterator().next();
		}
		else
		{
			// shall not happen
			throw new AdempiereException("More than one in transit locator found for " + this);
		}
	}

	public ImmutableSet<HuId> getActualHUIdsPicked()
	{
		return byActualHUIdPicked.keySet();
	}
}
