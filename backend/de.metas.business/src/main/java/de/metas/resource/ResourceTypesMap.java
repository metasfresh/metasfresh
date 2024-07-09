package de.metas.resource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Collector;

@ToString
class ResourceTypesMap
{
	private final ImmutableMap<ResourceTypeId, ResourceType> byId;

	ResourceTypesMap(@NonNull final List<ResourceType> list)
	{
		this.byId = Maps.uniqueIndex(list, ResourceType::getId);
	}

	public static Collector<ResourceType, ?, ResourceTypesMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ResourceTypesMap::new);
	}

	public ResourceType getById(@NonNull ResourceTypeId id)
	{
		final ResourceType resourceType = byId.get(id);
		if (resourceType == null)
		{
			throw new AdempiereException("No resource type found for " + id);
		}
		return resourceType;
	}
}
