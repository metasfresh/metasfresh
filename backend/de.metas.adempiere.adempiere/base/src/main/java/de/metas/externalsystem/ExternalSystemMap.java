package de.metas.externalsystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

public class ExternalSystemMap
{
	private static final ExternalSystemMap EMPTY = new ExternalSystemMap(ImmutableList.of());

	private final ImmutableMap<ExternalSystemId, ExternalSystem> byId;
	private final ImmutableMap<ExternalSystemType, ExternalSystem> byType;

	private ExternalSystemMap(@NonNull final List<ExternalSystem> list)
	{
		this.byId = Maps.uniqueIndex(list, ExternalSystem::getId);
		this.byType = Maps.uniqueIndex(list, ExternalSystem::getType);
	}

	public static ExternalSystemMap ofList(final List<ExternalSystem> list)
	{
		return list.isEmpty() ? EMPTY : new ExternalSystemMap(list);
	}

	public static Collector<ExternalSystem, ?, ExternalSystemMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ExternalSystemMap::ofList);
	}

	@Nullable
	public ExternalSystem getByTypeOrNull(@NonNull final ExternalSystemType type)
	{
		return byType.get(type);
	}

	public Optional<ExternalSystem> getOptionalByType(@NonNull final ExternalSystemType type)
	{
		return Optional.ofNullable(getByTypeOrNull(type));
	}

	@NonNull
	public ExternalSystem getByType(@NonNull final ExternalSystemType type)
	{
		return getOptionalByType(type)
				.orElseThrow(() -> new AdempiereException("Unknown external system type: " + type));
	}

	public @NonNull ExternalSystem getById(final @NonNull ExternalSystemId id)
	{
		final ExternalSystem externalSystem = byId.get(id);
		if (externalSystem == null)
		{
			throw new AdempiereException("No active external system found for id: " + id);
		}
		return externalSystem;
	}
}
