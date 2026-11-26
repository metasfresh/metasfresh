package de.metas.handlingunits.picking.job.model;

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

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins what is told about the HUs a schedule's picking recorded.
 */
class PickedHUsByScheduleTest
{
	private final ShipmentScheduleId schedule1 = ShipmentScheduleId.ofRepoId(1);
	private final ShipmentScheduleId schedule2 = ShipmentScheduleId.ofRepoId(2);

	@Test
	void ofNothing_isEMPTY_andEverySchedulesHasNoPickedHU()
	{
		assertThat(PickedHUsBySchedule.of(ImmutableSetMultimap.of())).isSameAs(PickedHUsBySchedule.EMPTY);
		assertThat(PickedHUsBySchedule.EMPTY.getPickedHuIds(schedule1)).isEmpty();
	}

	@Test
	void ofSomeHUs_tellsThemPerSchedule()
	{
		final PickedHUsBySchedule picked = PickedHUsBySchedule.of(ImmutableSetMultimap.of(schedule1, HuId.ofRepoId(10), schedule1, HuId.ofRepoId(11)));

		assertThat(picked.getPickedHuIds(schedule1)).containsExactlyInAnyOrder(HuId.ofRepoId(10), HuId.ofRepoId(11));
		assertThat(picked.getPickedHuIds(schedule2)).isEmpty();
	}
}
