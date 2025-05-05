package de.metas.manufacturing.job.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Collection;

public class ManufacturingJobDefaultFilterCollection
{
	@NonNull private static final ManufacturingJobDefaultFilterCollection EMPTY = new ManufacturingJobDefaultFilterCollection(ImmutableSet.of());
	@NonNull private final ImmutableSet<ManufacturingJobDefaultFilter> set;

	public ManufacturingJobDefaultFilterCollection(@NonNull final ImmutableSet<ManufacturingJobDefaultFilter> set)
	{
		this.set = set;
	}

	public static ManufacturingJobDefaultFilterCollection ofCollection(@NonNull final Collection<ManufacturingJobDefaultFilter> collection)
	{
		if (collection.isEmpty())
		{
			return EMPTY;
		}
		return new ManufacturingJobDefaultFilterCollection(ImmutableSet.copyOf(collection));
	}

	public boolean contains(@NonNull final ManufacturingJobDefaultFilter filter)
	{
		return set.contains(filter);
	}

	@VisibleForTesting
	ImmutableSet<ManufacturingJobDefaultFilter> toSet() {return set;}
}
