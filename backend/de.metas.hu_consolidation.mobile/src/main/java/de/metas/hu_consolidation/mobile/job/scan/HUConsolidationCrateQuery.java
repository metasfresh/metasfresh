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

		return Optional.of(allCrates(handlingUnitsDAO).addOnlyHUIds(huIds));
	}

	/**
	 * @return the query over all crates, i.e. all active top-level HUs, in or outside any job
	 */
	static IHUQueryBuilder allCrates(@NonNull final IHandlingUnitsDAO handlingUnitsDAO)
	{
		return handlingUnitsDAO
				.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setOnlyTopLevelHUs();
	}
}
