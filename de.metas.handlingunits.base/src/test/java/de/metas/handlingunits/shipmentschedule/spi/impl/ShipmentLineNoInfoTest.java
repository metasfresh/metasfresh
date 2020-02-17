package de.metas.handlingunits.shipmentschedule.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.inout.InOutLineId;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

class ShipmentLineNoInfoTest
{

	@Test
	void test()
	{
		final ShipmentLineNoInfo shipmentLineNoInfo = new ShipmentLineNoInfo();

		assertThat(shipmentLineNoInfo.put(InOutLineId.ofRepoId(10), 0)).isTrue(); // shall be ignored, thus no collision

		assertThat(shipmentLineNoInfo.put(InOutLineId.ofRepoId(10), 10)).isTrue(); // first time, thus no collision
		assertThat(shipmentLineNoInfo.put(InOutLineId.ofRepoId(20), 10)).isFalse(); // 2nd time with same LineNo => collision

		assertThat(shipmentLineNoInfo.put(InOutLineId.ofRepoId(30), 20)).isTrue(); // no collision

		assertThat(shipmentLineNoInfo.getShipmentLineIdsWithLineNoCollisions()).containsOnly(InOutLineId.ofRepoId(10), InOutLineId.ofRepoId(20));
	}

}
