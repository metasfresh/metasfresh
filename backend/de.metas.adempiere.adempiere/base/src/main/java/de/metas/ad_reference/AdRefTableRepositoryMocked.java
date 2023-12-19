package de.metas.ad_reference;

import de.metas.i18n.ExplainedOptional;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.HashMap;

public class AdRefTableRepositoryMocked implements AdRefTableRepository
{
	private final HashMap<ReferenceId, ADRefTable> map = new HashMap<>();

	public void put(@NonNull final ReferenceId referenceId, @NonNull final ADRefTable adRefTable)
	{
		map.put(referenceId, adRefTable);
	}

	@Override
	public ExplainedOptional<ADRefTable> getById(final ReferenceId referenceId)
	{
		final ADRefTable adRefTable = map.get(referenceId);
		if (adRefTable == null)
		{
			return ExplainedOptional.emptyBecause("No ADRefTable found for " + referenceId
					+ "\nFollowing references are available: " + adRefTable);
		}
		else
		{
			return ExplainedOptional.of(adRefTable);
		}
	}

	@Override
	public boolean hasTableReference(@Nullable final ReferenceId referenceId)
	{
		return referenceId != null && map.get(referenceId) != null;
	}
}
