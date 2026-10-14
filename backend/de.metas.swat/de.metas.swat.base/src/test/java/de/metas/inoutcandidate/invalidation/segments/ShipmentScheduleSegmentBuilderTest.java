package de.metas.inoutcandidate.invalidation.segments;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShipmentScheduleSegmentBuilderTest
{
	private static final int PRODUCT_ID = 1_000_001;
	private static final int LOCATOR_ID = 3_000_001;
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(2_000_001);

	@Test
	void warehouseAndLocator_areMutuallyExclusive_onOneBuilder()
	{
		// Setting BOTH warehouse and locator on one builder would AND into an under-invalidating intersection
		// in the recompute WHERE clause — build() must fail fast rather than silently under-invalidate.
		assertThatThrownBy(() -> ShipmentScheduleSegments.builder()
				.productId(PRODUCT_ID)
				.anyBPartnerId()
				.warehouseId(WAREHOUSE_ID)
				.locatorId(LOCATOR_ID)
				.build())
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("must not both be set");
	}

	@Test
	void warehouseOnlySegment_buildsAndIsValid()
	{
		final IShipmentScheduleSegment segment = ShipmentScheduleSegments.builder()
				.productId(PRODUCT_ID)
				.anyBPartnerId()
				.warehouseId(WAREHOUSE_ID)
				.build();

		assertThat(segment.isInvalid()).isFalse();
		assertThat(segment.getWarehouseIds()).containsExactly(WAREHOUSE_ID.getRepoId());
		assertThat(segment.getLocatorIds()).isEmpty();
	}

	@Test
	void locatorOnlySegment_buildsAndIsValid()
	{
		final IShipmentScheduleSegment segment = ShipmentScheduleSegments.builder()
				.productId(PRODUCT_ID)
				.anyBPartnerId()
				.locatorId(LOCATOR_ID)
				.build();

		assertThat(segment.isInvalid()).isFalse();
		assertThat(segment.getLocatorIds()).containsExactly(LOCATOR_ID);
		assertThat(segment.getWarehouseIds()).isEmpty();
	}
}
