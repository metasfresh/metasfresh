package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;

/**
 * Which HUs count as crates of a job: the active top-level HUs among the given ones.
 */
final class HUConsolidationCrateQuery
{
	private HUConsolidationCrateQuery() {}

	/**
	 * @return the query over the given HUs, empty for an empty set
	 * (HUQueryBuilder.addOnlyHUIds(empty) drops the filter, so the query would run over all active HUs).
	 */
	static Optional<IHUQueryBuilder> ofHuIds(@NonNull final IHandlingUnitsDAO handlingUnitsDAO, @NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(handlingUnitsDAO
				.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setOnlyTopLevelHUs()
				.addOnlyHUIds(huIds));
	}
}
