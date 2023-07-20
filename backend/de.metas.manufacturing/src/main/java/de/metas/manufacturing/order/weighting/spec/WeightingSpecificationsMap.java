package de.metas.manufacturing.order.weighting.spec;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
final class WeightingSpecificationsMap
{
	private final ImmutableMap<WeightingSpecificationsId, WeightingSpecifications> byId;
	private final Optional<WeightingSpecificationsId> defaultId;

	public WeightingSpecificationsMap(final List<WeightingSpecifications> list)
	{
		byId = Maps.uniqueIndex(list, WeightingSpecifications::getId);

		defaultId = list.stream()
				.map(WeightingSpecifications::getId)
				.min(Comparator.naturalOrder());
	}

	public WeightingSpecifications getDefault()
	{
		return getById(getDefaultId());
	}

	public WeightingSpecificationsId getDefaultId()
	{
		return defaultId.orElseThrow(() -> new AdempiereException("No specifications defined"));
	}

	public WeightingSpecifications getById(@NonNull final WeightingSpecificationsId id)
	{
		final WeightingSpecifications spec = byId.get(id);
		if (spec == null)
		{
			throw new AdempiereException("Invalid ID: " + id);
		}
		return spec;
	}
}
