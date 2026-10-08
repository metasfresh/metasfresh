package de.metas.cost.classification;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.stream.Collector;

class CostClassificationMap
{
	private static final CostClassificationMap EMPTY = new CostClassificationMap(ImmutableList.of());

	private final ImmutableMap<CostClassificationId, CostClassification> byId;

	private CostClassificationMap(final Collection<CostClassification> collection)
	{
		this.byId = Maps.uniqueIndex(collection, CostClassification::getId);
	}

	public static Collector<CostClassification, ?, CostClassificationMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(CostClassificationMap::ofCollection);
	}

	public static CostClassificationMap ofCollection(@NonNull final Collection<CostClassification> collection)
	{
		return collection.isEmpty() ? EMPTY : new CostClassificationMap(collection);
	}

	public CostClassification getById(final CostClassificationId id)
	{
		final CostClassification costClassification = byId.get(id);
		if (costClassification == null)
		{
			throw new AdempiereException("No Cost Classification found for " + id);
		}
		return costClassification;
	}
}
