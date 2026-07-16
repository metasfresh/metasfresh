package de.metas.inoutcandidate.invalidation.impl;

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

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegments;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.inoutcandidate.picking_bom.PickingBOMsReversedIndex;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Regression guard for {@link ShipmentScheduleInvalidateBL#explodeByPickingBOMs(IShipmentScheduleSegment)}.
 * <p>
 * Since the warehouse-identity model (a segment carries a {@code warehouseId} instead of enumerating the
 * warehouse's locators), the {@code notifySegmentChangedFor*} callers build <b>warehouse-only</b> segments
 * (empty {@code locatorIds}). When such a segment's product has a Picking-BOM parent, {@code explodeByPickingBOMs}
 * must reconstruct the BOM-parent segment carrying the <b>same warehouse scope</b> — otherwise the reconstructed
 * segment has both {@code locatorIds} and {@code warehouseIds} empty, is {@link IShipmentScheduleSegment#isInvalid()},
 * and is silently dropped from the recompute WHERE clause: the BOM-parent product's shipment schedules in that
 * warehouse are then never invalidated (silent invalidation loss).
 */
class ShipmentScheduleInvalidateBLTest
{
	private static final ProductId COMPONENT_PRODUCT_ID = ProductId.ofRepoId(1_000_001);
	private static final ProductId BOM_PARENT_PRODUCT_ID = ProductId.ofRepoId(1_000_002);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(2_000_001);

	private ShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final PickingBOMService pickingBOMService = mock(PickingBOMService.class);
		final PickingBOMsReversedIndex reversedIndex = PickingBOMsReversedIndex.ofBOMProductIdsByComponentId(
				ImmutableSetMultimap.of(COMPONENT_PRODUCT_ID, BOM_PARENT_PRODUCT_ID));
		when(pickingBOMService.getPickingBOMsReversedIndex()).thenReturn(reversedIndex);

		shipmentScheduleInvalidateBL = new ShipmentScheduleInvalidateBL(pickingBOMService);
	}

	@Test
	void explodeByPickingBOMs_warehouseDerivedSegment_bomParentSegmentKeepsWarehouseScope()
	{
		// A warehouse-derived source segment, exactly as createSegmentForShipmentSchedule builds it:
		// warehouse identity set, no locators.
		final IShipmentScheduleSegment warehouseSegment = ShipmentScheduleSegments.builder()
				.bpartnerId(0)
				.productId(COMPONENT_PRODUCT_ID)
				.warehouseId(WAREHOUSE_ID)
				.attributeSetInstanceId(0)
				.build();

		final List<IShipmentScheduleSegment> exploded = shipmentScheduleInvalidateBL
				.explodeByPickingBOMs(warehouseSegment)
				.collect(Collectors.toList());

		// The BOM-parent segment (the exploded one carrying the BOM-parent product) must remain a VALID,
		// warehouse-scoped segment — otherwise it is silently dropped and the BOM parent is never invalidated.
		final IShipmentScheduleSegment bomParentSegment = exploded.stream()
				.filter(s -> s.getProductIds().contains(BOM_PARENT_PRODUCT_ID.getRepoId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("expected an exploded segment for the picking-BOM parent product"));

		assertThat(bomParentSegment.isInvalid())
				.as("BOM-parent segment must not be invalid (both locatorIds and warehouseIds empty ⇒ dropped)")
				.isFalse();
		assertThat(bomParentSegment.getWarehouseIds())
				.as("BOM-parent segment must inherit the source segment's warehouse scope")
				.containsExactly(WAREHOUSE_ID.getRepoId());
	}
}
