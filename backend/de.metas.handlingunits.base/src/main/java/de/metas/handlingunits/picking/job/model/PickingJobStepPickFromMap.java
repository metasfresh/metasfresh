package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HUBarcode;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

@EqualsAndHashCode
@ToString
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PickingJobStepPickFromMap
{
	@NonNull private final ImmutableMap<PickingJobStepPickFromKey, PickingJobStepPickFrom> map;

	private final boolean reportedOnAllPickFroms;
	@NonNull private final Optional<Quantity> qtyPickedSum;

	public static PickingJobStepPickFromMap ofList(@NonNull List<PickingJobStepPickFrom> pickFroms)
	{
		return new PickingJobStepPickFromMap(Maps.uniqueIndex(pickFroms, PickingJobStepPickFrom::getPickFromKey));
	}

	private PickingJobStepPickFromMap(@NonNull final ImmutableMap<PickingJobStepPickFromKey, PickingJobStepPickFrom> map)
	{
		if (!map.containsKey(PickingJobStepPickFromKey.MAIN))
		{
			throw new AdempiereException("Main PickFrom shall be present in " + map);
		}

		this.map = map;

		boolean reportedOnAllPickFroms = true;
		Quantity qtyPickedSum = null;
		for (final PickingJobStepPickFrom pickFrom : map.values())
		{
			if (pickFrom.isPicked())
			{
				final PickingJobStepPickedTo pickedTo = Objects.requireNonNull(pickFrom.getPickedTo());
				qtyPickedSum = Quantity.addNullables(qtyPickedSum, pickedTo.getQtyPicked());
			}
			else
			{
				reportedOnAllPickFroms = false;
			}
		}

		this.reportedOnAllPickFroms = reportedOnAllPickFroms;
		this.qtyPickedSum = Optional.ofNullable(qtyPickedSum);
	}

	public ImmutableSet<PickingJobStepPickFromKey> getKeys()
	{
		return map.keySet();
	}

	public PickingJobStepPickFrom getPickFrom(final PickingJobStepPickFromKey key)
	{
		final PickingJobStepPickFrom pickFrom = map.get(key);
		if (pickFrom == null)
		{
			throw new AdempiereException("No Pick From defined for " + key);
		}
		return pickFrom;
	}

	public PickingJobStepPickFrom getPickFromByHUBarcode(@NonNull final HUBarcode huBarcode)
	{
		return map.values()
				.stream()
				.filter(pickFrom -> pickFrom.getPickFromHU().getBarcode().equals(huBarcode))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No HU found for " + huBarcode));
	}

	public PickingJobProgress computeProgress(@NonNull final Quantity qtyToPick)
	{
		// Consider picked if user reported on all PickFroms
		if (reportedOnAllPickFroms)
		{
			return PickingJobProgress.DONE;
		}

		// Definitely not picked if user did not report on any of the PickFroms
		if (!qtyPickedSum.isPresent())
		{
			return PickingJobProgress.NOT_STARTED;
		}

		// Consider picked if user actually picked the whole required qty
		final Quantity qtyToPickRemaining = qtyToPick.subtract(qtyPickedSum.get());
		return qtyToPickRemaining.isZero()
				? PickingJobProgress.DONE
				: PickingJobProgress.IN_PROGRESS;
	}

	public boolean isPicked(@NonNull final Quantity qtyToPick)
	{
		// Consider picked if user reported on all PickFroms
		if (reportedOnAllPickFroms)
		{
			return true;
		}

		// Definitely not picked if user did not report on any of the PickFroms
		if (!qtyPickedSum.isPresent())
		{
			return false;
		}

		// Consider picked if user actually picked the whole required qty
		final Quantity qtyToPickRemaining = qtyToPick.subtract(qtyPickedSum.get());
		return qtyToPickRemaining.isZero();
	}

	public PickingJobStepPickFromMap reduceWithPickedEvent(
			@NonNull PickingJobStepPickFromKey key,
			@NonNull PickingJobStepPickedTo pickedTo)
	{
		return withChangedPickFrom(key, pickFrom -> pickFrom.withPickedEvent(pickedTo));
	}

	public PickingJobStepPickFromMap reduceWithUnpickEvent(
			@NonNull PickingJobStepPickFromKey key,
			@NonNull PickingJobStepUnpickInfo unpicked)
	{
		return withChangedPickFrom(key, pickFrom -> pickFrom.withUnPickedEvent(unpicked));
	}

	private PickingJobStepPickFromMap withChangedPickFrom(
			@NonNull PickingJobStepPickFromKey key,
			@NonNull UnaryOperator<PickingJobStepPickFrom> pickFromMapper)
	{
		if (!map.containsKey(key))
		{
			throw new AdempiereException("No PickFrom " + key + " found in " + this);
		}

		final ImmutableMap<PickingJobStepPickFromKey, PickingJobStepPickFrom> newMap = CollectionUtils.mapValue(map, key, pickFromMapper);
		return !Objects.equals(this.map, newMap)
				? new PickingJobStepPickFromMap(newMap)
				: this;
	}
}
