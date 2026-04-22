/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.material.planning;

import org.eevolution.productioncandidate.material.planning.PPOrderCandidatePojoSupplier.PlanningDates;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eevolution.productioncandidate.material.planning.PPOrderCandidatePojoSupplier.computePlanningDates;

class PPOrderCandidatePojoSupplierTest
{
	@Test
	void overdueDemand_sameDayDifferentWallClock_producesIdenticalDates()
	{
		final Instant demandDate = Instant.parse("2026-04-20T09:00:00Z");
		final int durationDays = 1;

		final Instant firstRequestAt = Instant.parse("2026-04-20T11:58:07.835Z");
		final Instant secondRequestAt = Instant.parse("2026-04-20T11:58:13.780Z");

		final PlanningDates first = computePlanningDates(demandDate, durationDays, firstRequestAt);
		final PlanningDates second = computePlanningDates(demandDate, durationDays, secondRequestAt);

		assertThat(first).isEqualTo(second);
		assertThat(first.getDateStartSchedule()).isEqualTo(Instant.parse("2026-04-20T00:00:00Z"));
		assertThat(first.getDatePromised()).isEqualTo(Instant.parse("2026-04-21T00:00:00Z"));
	}

	@Test
	void overdueDemand_truncatesNowToDayPrecision()
	{
		final Instant demandDate = Instant.parse("2026-04-19T09:00:00Z");
		final int durationDays = 0;

		final PlanningDates result = computePlanningDates(demandDate, durationDays, Instant.parse("2026-04-20T15:42:07.123Z"));

		assertThat(result.getDateStartSchedule()).isEqualTo(Instant.parse("2026-04-20T00:00:00Z"));
		assertThat(result.getDatePromised()).isEqualTo(Instant.parse("2026-04-20T00:00:00Z"));
	}

	@Test
	void forwardPlannedDemand_preservesDemandDateExactly()
	{
		final Instant demandDate = Instant.parse("2026-05-15T21:00:00Z");
		final int durationDays = 3;
		final Instant now = Instant.parse("2026-04-20T11:58:07.835Z");

		final PlanningDates result = computePlanningDates(demandDate, durationDays, now);

		assertThat(result.getDatePromised()).isEqualTo(demandDate);
		assertThat(result.getDateStartSchedule()).isEqualTo(Instant.parse("2026-05-12T21:00:00Z"));
	}
}
