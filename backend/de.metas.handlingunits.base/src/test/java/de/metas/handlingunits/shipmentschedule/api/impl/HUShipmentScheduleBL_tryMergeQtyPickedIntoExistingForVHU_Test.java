/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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
package de.metas.handlingunits.shipmentschedule.api.impl;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.shipmentschedule.api.AddQtyPickedRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UomId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Cheap-guard tests for {@link HUShipmentScheduleBL#tryMergeQtyPickedIntoExistingForVHU(AddQtyPickedRequest)}.
 *
 * <p>The merge predicate has multiple early returns based on request shape (no HU lookup,
 * no DAO query, no side effects). Those are the contract the listener relies on for
 * non-listener-shaped picks to fall through to {@code addQtyPickedAndUpdateHU}. This test
 * locks each one in so we don't accidentally widen the merge scope in a future refactor.</p>
 *
 * <p>Happy-path coverage (qty actually summed into an existing row) is in
 * {@link HUShipmentScheduleBL_mergeQtyPickedHappyPath_Test} and the mobile-webui Playwright spec
 * {@code recreate_shipment_after_void.spec.js}.</p>
 */
class HUShipmentScheduleBL_tryMergeQtyPickedIntoExistingForVHU_Test
{
	private static final int VHU_ID = 1_111_111;

	private HUShipmentScheduleBL bl;
	private I_M_ShipmentSchedule schedule;
	private I_M_HU vhu;
	private I_C_UOM uom;
	private IHUContext huContext;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		bl = new HUShipmentScheduleBL();

		schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setM_Product_ID(7777);
		InterfaceWrapperHelper.saveRecord(schedule);

		vhu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.saveRecord(vhu);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setUOMSymbol("PCE");
		InterfaceWrapperHelper.saveRecord(uom);

		huContext = mock(IHUContext.class);
	}

	@Test
	void rejects_when_pickingJobScheduleId_is_set()
	{
		final AddQtyPickedRequest request = baseRequestBuilder(positiveStockQty(BigDecimal.TEN))
				.scheduleId(ShipmentScheduleAndJobScheduleId.of(
						ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()),
						PickingJobScheduleId.ofRepoId(8_888)))
				.cachedShipmentSchedule(null)
				.build();

		assertThat(bl.tryMergeQtyPickedIntoExistingForVHU(request)).isFalse();
	}

	@Test
	void rejects_when_anonymous_on_the_fly_is_set()
	{
		final AddQtyPickedRequest request = baseRequestBuilder(positiveStockQty(BigDecimal.TEN))
				.anonymousHuPickedOnTheFly(true)
				.build();

		assertThat(bl.tryMergeQtyPickedIntoExistingForVHU(request)).isFalse();
	}

	@Test
	void rejects_when_request_carries_catch_weight()
	{
		final StockQtyAndUOMQty qtyWithCatch = StockQtyAndUOMQty.builder()
				.productId(ProductId.ofRepoId(schedule.getM_Product_ID()))
				.stockQty(Quantity.of(BigDecimal.TEN, uom))
				.uomQty(Quantity.of(new BigDecimal("9.7"), uom))
				.build();
		final AddQtyPickedRequest request = baseRequestBuilder(qtyWithCatch).build();

		assertThat(bl.tryMergeQtyPickedIntoExistingForVHU(request)).isFalse();
	}

	@Test
	void rejects_when_qty_is_zero()
	{
		final AddQtyPickedRequest request = baseRequestBuilder(positiveStockQty(BigDecimal.ZERO)).build();

		assertThat(bl.tryMergeQtyPickedIntoExistingForVHU(request)).isFalse();
	}

	@Test
	void rejects_when_qty_is_negative()
	{
		// Un-allocation (negative trx-line qty) must remain its own audit row so the netting is visible.
		final AddQtyPickedRequest request = baseRequestBuilder(positiveStockQty(new BigDecimal("-2"))).build();

		assertThat(bl.tryMergeQtyPickedIntoExistingForVHU(request)).isFalse();
	}

	@Test
	void rejects_when_hu_is_not_virtual()
	{
		// Give the HU a non-virtual PI version (any id but the virtual 101); isVirtual(vhu) is then false.
		// With a clean positive, non-catch-weight, non-anonymous, no-job-schedule request, all earlier
		// guards pass and the non-virtual guard is the one that returns false (a non-VHU pick is never
		// listener-shaped).
		vhu.setM_HU_PI_Version_ID(500);
		InterfaceWrapperHelper.saveRecord(vhu);
		final AddQtyPickedRequest request = baseRequestBuilder(positiveStockQty(BigDecimal.TEN)).build();

		assertThat(bl.tryMergeQtyPickedIntoExistingForVHU(request)).isFalse();
	}

	private AddQtyPickedRequest.AddQtyPickedRequestBuilder baseRequestBuilder(final StockQtyAndUOMQty qty)
	{
		return AddQtyPickedRequest.builder()
				.scheduleId(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(
						ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID())))
				.cachedShipmentSchedule(schedule)
				.qtyPicked(qty)
				.hu(vhu)
				.huContext(huContext)
				.anonymousHuPickedOnTheFly(false);
	}

	private StockQtyAndUOMQty positiveStockQty(final BigDecimal amount)
	{
		return StockQtyAndUOMQty.builder()
				.productId(ProductId.ofRepoId(schedule.getM_Product_ID()))
				.stockQty(Quantity.of(amount, uom))
				.build();
	}
}
