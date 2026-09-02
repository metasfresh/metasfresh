package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import lombok.NonNull;

import java.util.HashSet;

public class LUIdsAndTopLevelTUIdsCollector
{
	private final HashSet<HuId> allTopLevelHUIds = new HashSet<>();
	private final HashSet<HuId> luIds = new HashSet<>();
	private final HashSet<HuId> topLevelTUIds = new HashSet<>();

	public void addLUId(@NonNull final HuId luId)
	{
		allTopLevelHUIds.add(luId);
		luIds.add(luId);
	}

	public void addTopLevelTUId(@NonNull final HuId tuId)
	{
		allTopLevelHUIds.add(tuId);
		topLevelTUIds.add(tuId);
	}

	public boolean isEmpty()
	{
		return allTopLevelHUIds.isEmpty();
	}

	public ImmutableSet<HuId> getAllTopLevelHUIds()
	{
		return ImmutableSet.copyOf(allTopLevelHUIds);
	}

	public ImmutableSet<HuId> getLUIds()
	{
		return ImmutableSet.copyOf(luIds);
	}

	public ImmutableSet<HuId> getTopLevelTUIds()
	{
		return ImmutableSet.copyOf(topLevelTUIds);
	}
}
