package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
public class PickingJobStepPickFromMap
{
	@NonNull private final ImmutableMap<PickingJobStepPickFromKey, PickingJobStepPickFrom> map;

	@NonNull @Getter private final PickingJobProgress progress;

	public static PickingJobStepPickFromMap ofList(@NonNull final List<PickingJobStepPickFrom> pickFroms)
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

		this.progress = computeProgress(map);
	}

	private static PickingJobProgress computeProgress(final @NonNull ImmutableMap<PickingJobStepPickFromKey, PickingJobStepPickFrom> map)
	{
		final boolean isSomethingReported = map.values().stream().anyMatch(PickingJobStepPickFrom::isPicked);
		return isSomethingReported ? PickingJobProgress.DONE : PickingJobProgress.NOT_STARTED;
	}

	public ImmutableSet<PickingJobStepPickFromKey> getKeys()
	{
		return map.keySet();
	}

	public PickingJobStepPickFrom getMainPickFrom() {return getPickFrom(PickingJobStepPickFromKey.MAIN);}

	public PickingJobStepPickFrom getPickFrom(final PickingJobStepPickFromKey key)
	{
		final PickingJobStepPickFrom pickFrom = map.get(key);
		if (pickFrom == null)
		{
			throw new AdempiereException("No Pick From defined for " + key);
		}
		return pickFrom;
	}

	public PickingJobStepPickFrom getPickFromByHUQRCode(@NonNull final HUQRCode qrCode)
	{
		return map.values()
				.stream()
				.filter(pickFrom -> HUQRCode.equals(pickFrom.getPickFromHU().getQrCode(), qrCode))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No HU found for " + qrCode));
	}

	public boolean isNothingPicked()
	{
		return map.values().stream().allMatch(PickingJobStepPickFrom::isNotPicked);
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

	public Optional<Quantity> getQtyPicked()
	{
		return map.values()
				.stream()
				.map(pickFrom -> pickFrom.getQtyPicked().orElse(null))
				.filter(Objects::nonNull)
				.reduce(Quantity::add);
	}

	public Optional<Quantity> getQtyRejected()
	{
		// returning only from mainPickFrom because I wanted to keep the same logic we already have in misc/services/mobile-webui/mobile-webui-frontend/src/utils/picking.js, getQtyPickedOrRejectedTotalForLine  
		return getMainPickFrom().getQtyRejected();
	}

}
