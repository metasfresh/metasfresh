package de.metas.product.allergen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.List;

final class AllergenMap
{
	private final ImmutableMap<AllergenId, Allergen> byId;

	public AllergenMap(@NonNull final List<Allergen> list)
	{
		byId = Maps.uniqueIndex(list, Allergen::getId);
	}

	public ImmutableList<Allergen> getByIds(@NonNull final Collection<AllergenId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		return ids.stream()
				.map(this::getById)
				.collect(ImmutableList.toImmutableList());
	}

	public Allergen getById(@NonNull final AllergenId id)
	{
		final Allergen Allergen = byId.get(id);
		if (Allergen == null)
		{
			throw new AdempiereException("No Hazard Symbol found for " + id);
		}
		return Allergen;
	}
}
