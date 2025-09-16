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
	private final ImmutableMap<ExternalSystemType, ExternalSystem> byValue;

	private ExternalSystemMap(@NonNull final List<ExternalSystem> list)
	{
		this.byId = Maps.uniqueIndex(list, ExternalSystem::getId);
		this.byValue = Maps.uniqueIndex(list, ExternalSystem::getType);
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
	public ExternalSystem getByValueOrNull(@NonNull final ExternalSystemType value)
	{
		return byValue.get(value);
	}

	public Optional<ExternalSystem> getOptionalByValue(@NonNull final ExternalSystemType value)
	{
		return Optional.ofNullable(getByValueOrNull(value));
	}

	@NonNull
	public ExternalSystem getByValue(@NonNull final ExternalSystemType value)
	{
		return getOptionalByValue(value)
				.orElseThrow(() -> new AdempiereException("Unknown external system value: " + value));
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
