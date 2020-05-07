package de.metas.handlingunits.shipmentschedule.integrationtest;

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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilder;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

/**
 * Test case:
 * <ul>
 * <li>create a 2TUs, and different products
 * <li>assign each TU to a different shipment schedule
 * </ul>
 */
/* package */abstract class AbstractAggregateShipment2SSchedHUShipmentProcessIntegrationTest extends AbstractHUShipmentProcessIntegrationTest
{
	@Override
	protected final void step10_createShipmentSchedules()
	{
		shipmentSchedules = Arrays.asList(
				createShipmentSchedule(true, // on new order
						pTomato, productUOM, BigDecimal.valueOf(30)), // shipment schedule 0
				createShipmentSchedule(true, // on new order
						pSalad, productUOM, BigDecimal.valueOf(50)) // shipment schedule 1
		);
	}

	@Override
	protected final void step20_pickTUs()
	{
		//
		// Get shipment schedules
		final I_M_ShipmentSchedule shipmentSchedule1 = shipmentSchedules.get(0);
		final I_M_ShipmentSchedule shipmentSchedule2 = shipmentSchedules.get(1);

		//
		// Create initial TU
		final IMutable<I_M_HU> tu1 = new Mutable<>();
		final IMutable<I_M_HU> vhu1 = new Mutable<>();
		final IMutable<I_M_HU> tu2 = new Mutable<>();
		final IMutable<I_M_HU> vhu2 = new Mutable<>();
		final IMutable<I_M_HU> tu3 = new Mutable<>();
		final IMutable<I_M_HU> vhu3 = new Mutable<>();
		final IMutable<I_M_HU> tu4 = new Mutable<>();
		final IMutable<I_M_HU> vhu4 = new Mutable<>();
		final IMutable<I_M_HU> tu5 = new Mutable<>();
		final IMutable<I_M_HU> vhu5 = new Mutable<>();
		final IMutable<I_M_HU> tu6 = new Mutable<>();
		final IMutable<I_M_HU> vhu6 = new Mutable<>();
		//@formatter:off
		afterPick_HUExpectations = new HUsExpectation()
			//
			// TU 1
			.newHUExpectation()
				.capture(tu1)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// TU 1 VHU 1
					.newIncludedVirtualHU()
						.capture(vhu1)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(pTomato).uom(productUOM).qty("30")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			//
			// TU 2
			.newHUExpectation()
				.capture(tu2)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// VHU 2
					.newIncludedVirtualHU()
						.capture(vhu2)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(pSalad).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			//
			// TU 3
			.newHUExpectation()
				.capture(tu3)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// VHU 3
					.newIncludedVirtualHU()
						.capture(vhu3)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(pSalad).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			//
			// TU 4
			.newHUExpectation()
				.capture(tu4)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// VHU 4
					.newIncludedVirtualHU()
						.capture(vhu4)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(pSalad).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			//
			// TU 5
			.newHUExpectation()
				.capture(tu5)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// VHU 5
					.newIncludedVirtualHU()
						.capture(vhu5)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(pSalad).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation()
			//
			// TU 6
			.newHUExpectation()
				.capture(tu6)
				.huStatus(X_M_HU.HUSTATUS_Picked)
				.huPI(piTU)
				.bPartner(bpartner)
				.bPartnerLocation(bpartnerLocation)				
				.newHUItemExpectation()
					.itemType(X_M_HU_PI_Item.ITEMTYPE_Material)
					.huPIItem(piTU_Item)
					//
					// VHU 6
					.newIncludedVirtualHU()
						.capture(vhu6)
						.huStatus(X_M_HU.HUSTATUS_Picked)
						.newVirtualHUItemExpectation()
							.newItemStorageExpectation()
								.product(pSalad).uom(productUOM).qty("10")
								.endExpectation()
							.endExpectation()
						.endExpectation()
					.endExpectation()
				.endExpectation();
		//@formatter:on
		afterPick_HUExpectations.createHUs();

		//
		// Assign VHU1 to shipmentSchedule1
		// Assign VHU2 to shipmentSchedule2
		//@formatter:off
		new ShipmentScheduleQtyPickedExpectations()
			//
			// Shipment Schedule 1
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule1)
				.noLU().tu(tu1).vhu(vhu1).qtyPicked("30")
				.endExpectation()
			//
			// Shipment Schedule 2
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule2)
				.noLU().tu(tu2).vhu(vhu2).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule2)
				.noLU().tu(tu3).vhu(vhu3).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule2)
				.noLU().tu(tu4).vhu(vhu4).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule2)
				.noLU().tu(tu5).vhu(vhu5).qtyPicked("10")
				.endExpectation()
			.newShipmentScheduleQtyPickedExpectation()
				.shipmentSchedule(shipmentSchedule2)
				.noLU().tu(tu6).vhu(vhu6).qtyPicked("10")
				.endExpectation()
			.createM_ShipmentSchedule_QtyPickeds(helper.getContextProvider());
		//@formatter:on
	}

	/**
	 * Creates and executes an {@link IHUSplitBuilder} to split the given {@code qty} of the given {@code product}
	 * from the given {@code tuHU} onto an LU.
	 * 
	 * @param tuHU
	 * @param product
	 * @param qty
	 * @return
	 */
	protected final List<I_M_HU> splitOnLU(final I_M_HU tuHU, final I_M_Product product, final BigDecimal qty)
	{
		final List<I_M_HU> splitLUs = new HUSplitBuilder(helper.ctx)
				.setHUToSplit(tuHU)
				.setCUQty(qty)
				// LU
				.setLU_M_HU_PI_Item(piLU_Item)
				.setMaxLUToAllocate(new BigDecimal("1"))
				// TU
				.setTU_M_HU_PI_Item(piTU_Item)
				.setTUPerLU(new BigDecimal("1"))
				// CU
				.setCUProduct(product)
				.setCUPerTU(qty)
				.setCUUOM(productUOM)
				//
				.split();
		return splitLUs;
	}
}
