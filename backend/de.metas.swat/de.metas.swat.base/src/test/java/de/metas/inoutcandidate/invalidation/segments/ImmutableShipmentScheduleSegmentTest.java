package de.metas.inoutcandidate.invalidation.segments;

import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.storage
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ImmutableShipmentScheduleSegmentTest
{
	@Test
	public void testEmptyBuilderDoesntReturnNull()
	{
		final ImmutableShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder().build();
		assertThat(segment.getAttributes()).isEmpty();
		assertThat(segment.getBpartnerIds()).isEmpty();
		assertThat(segment.getLocatorIds()).isEmpty();
		assertThat(segment.getProductIds()).isEmpty();
	}

	@Test
	public void warehouseIdBuilder_carriesWarehouseIdentity_notEnumeratedLocators()
	{
		final int warehouseRepoId = 100;

		final ImmutableShipmentScheduleSegment segment = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.warehouseId(WarehouseId.ofRepoId(warehouseRepoId))
				.build();

		// the segment must carry the WAREHOUSE identity (as repo-id, mirroring locatorIds/productIds Set<Integer>) ...
		assertThat(segment.getWarehouseIds()).containsExactly(warehouseRepoId);
		// ... and must NOT have enumerated the warehouse's locators
		assertThat(segment.getLocatorIds()).isEmpty();
		assertThat(segment.isAnyWarehouse()).isFalse();
	}

	@Test
	public void twoSegmentsFromSameWarehouseProductBPartner_areEqual()
	{
		final int warehouseRepoId = 100;

		final ImmutableShipmentScheduleSegment segment1 = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.warehouseId(WarehouseId.ofRepoId(warehouseRepoId))
				.build();

		final ImmutableShipmentScheduleSegment segment2 = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.warehouseId(WarehouseId.ofRepoId(warehouseRepoId))
				.build();

		assertThat(segment1).isEqualTo(segment2);
	}

	@Test
	public void locatorIdBuilder_keepsSingleLocator_withNoWarehouseIdentity()
	{
		final int locatorRepoId = 555;

		final ImmutableShipmentScheduleSegment segment = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.locatorId(locatorRepoId)
				.build();

		assertThat(segment.getLocatorIds()).contains(locatorRepoId);
		assertThat(segment.getWarehouseIds()).isEmpty();
	}
}
