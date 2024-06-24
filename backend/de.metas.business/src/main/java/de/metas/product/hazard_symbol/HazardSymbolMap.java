package de.metas.product.hazard_symbol;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.List;

class HazardSymbolMap
{
	private final ImmutableMap<HazardSymbolId, HazardSymbol> byId;

	public HazardSymbolMap(@NonNull final List<HazardSymbol> list)
	{
		byId = Maps.uniqueIndex(list, HazardSymbol::getId);
	}

	public ImmutableList<HazardSymbol> getByIds(@NonNull final Collection<HazardSymbolId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		return ids.stream()
				.map(this::getById)
				.collect(ImmutableList.toImmutableList());
	}

	public HazardSymbol getById(@NonNull final HazardSymbolId id)
	{
		final HazardSymbol hazardSymbol = byId.get(id);
		if (hazardSymbol == null)
		{
			throw new AdempiereException("No Hazard Symbol found for " + id);
		}
		return hazardSymbol;
	}
}
