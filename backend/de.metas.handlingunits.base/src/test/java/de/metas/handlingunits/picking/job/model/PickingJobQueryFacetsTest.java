/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link PickingJobQuery.Facets#isMatching(PickingJobReference)} — the filter applied to
 * ALREADY-STARTED picking jobs. Distinct from the SQL-side filter in {@code PackagingDAO}, which only
 * covers not-yet-started packageables: a facet wired into one but not the other filters half the
 * launcher list, which is exactly the defect these tests pin.
 */
class PickingJobQueryFacetsTest
{
	private static final ZoneId ZONE = ZoneId.of("Europe/Berlin");
	private static final LocalDate DAY_1 = LocalDate.of(2026, 8, 11);
	private static final LocalDate DAY_2 = LocalDate.of(2026, 8, 12);

	@Test
	void noFilterActive_matchesEverything()
	{
		assertThat(PickingJobQuery.Facets.EMPTY.isMatching(reference(DAY_1))).isTrue();
	}

	/**
	 * A job with no preparation date must still pass when nothing is selected — the "no filter" check has
	 * to precede the null check, otherwise such jobs disappear from the launcher entirely.
	 */
	@Test
	void noFilterActive_matchesAJobWithoutAPreparationDate()
	{
		assertThat(PickingJobQuery.Facets.EMPTY.isMatching(reference(null))).isTrue();
	}

	@Test
	void preparationDaySelected_matchesOnlyThatDay()
	{
		final PickingJobQuery.Facets facets = PickingJobQuery.Facets.builder().preparationDay(DAY_1).build();

		assertThat(facets.isMatching(reference(DAY_1))).isTrue();
		assertThat(facets.isMatching(reference(DAY_2))).isFalse();
	}

	/** Strict once a day is selected, mirroring the SQL-side filter. */
	@Test
	void preparationDaySelected_excludesAJobWithoutAPreparationDate()
	{
		final PickingJobQuery.Facets facets = PickingJobQuery.Facets.builder().preparationDay(DAY_1).build();

		assertThat(facets.isMatching(reference(null))).isFalse();
	}

	/**
	 * A delivery date MUST be set here even though these tests are about the preparation date, because
	 * {@link PickingJobQuery.Facets#isMatching(PickingJobReference)} also consults
	 * {@code isDeliveryDateMatching}, which returns false for a null delivery date REGARDLESS of whether a
	 * delivery-day filter is active. Leaving it null made three of these tests fail and — worse — made
	 * {@link #preparationDaySelected_excludesAJobWithoutAPreparationDate()} pass for the wrong reason
	 * (rejected on the missing delivery date, not the missing preparation date).
	 * <p>
	 * That null-before-filter ordering is a pre-existing defect in the delivery-date matcher, not
	 * something introduced here: an already-started job with no delivery date never reaches the launcher
	 * even with no filter selected. Pinning a delivery date isolates the behaviour under test; the defect
	 * itself is reported separately rather than changed as a side effect of this feature.
	 */
	private static PickingJobReference reference(@Nullable final LocalDate preparationDay)
	{
		return PickingJobReference.builder()
				.pickingJobId(PickingJobId.ofRepoId(1))
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.products(PickingJobCandidateProducts.newInstance())
				.deliveryDate(DAY_1.atStartOfDay(ZONE))
				.preparationDate(preparationDay != null ? preparationDay.atStartOfDay(ZONE) : null)
				.build();
	}
}
