package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableMap;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class PickingJobOptionsCollection
{
	public static final PickingJobOptionsCollection EMPTY = new PickingJobOptionsCollection(ImmutableMap.of());

	@NonNull private final ImmutableMap<PickingJobOptionsId, PickingJobOptions> byId;

	private PickingJobOptionsCollection(@NonNull final ImmutableMap<PickingJobOptionsId, PickingJobOptions> byId)
	{
		this.byId = byId;
	}

	private static PickingJobOptionsCollection ofMap(final Map<PickingJobOptionsId, PickingJobOptions> map)
	{
		return !map.isEmpty() ? new PickingJobOptionsCollection(ImmutableMap.copyOf(map)) : EMPTY;
	}

	public static Collector<Map.Entry<PickingJobOptionsId, PickingJobOptions>, ?, PickingJobOptionsCollection> collect()
	{
		return GuavaCollectors.collectUsingMapAccumulator(PickingJobOptionsCollection::ofMap);
	}

	@NonNull
	public PickingJobOptions getById(@NotNull PickingJobOptionsId id)
	{
		final PickingJobOptions pickingJobOptions = byId.get(id);
		if (pickingJobOptions == null)
		{
			throw new AdempiereException("No picking job options found for " + id);
		}
		return pickingJobOptions;
	}
}
