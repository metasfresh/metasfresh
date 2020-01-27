package de.metas.fresh.picking.service.impl;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.util.Services;

public class PackingServiceTest extends AbstractHUTest
{
//	private PackingService pickingService;
//	private ShipmentScheduleHelper shipmentScheduleHelper;
	
	private I_M_HU_PI huDefIFCO;
	private static final int COUNT_Tomatoes_Per_IFCO = 10;

//	private I_M_HU hu;

	@Override
	protected void initialize()
	{
//		// Service to test
//		pickingService = (PackingService)Services.get(IPackingService.class);
//
//		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);

		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomato, BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO), uomEach);
		}

		//
		// Empty Handling Unit for test
		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final IHUBuilder huBuilder = Services.get(IHandlingUnitsDAO.class).createHUBuilder(huContext);
		huBuilder.setDate(helper.getTodayZonedDateTime());
//		hu = huBuilder.create(huDefIFCO);
	}

//	/**
//	 * @task http://dewiki908/mediawiki/index.php/06548_Follow-up_for_06439_Issues_with_transactions_in_Kommissionier_Terminal_%28105442952392%29
//	 */
//	@Test
//	public void test_addRemoveProductQtyToHU()
//	{
//
//		//
//		// Create Shipment Schedule and make sure initial QtyPicked is ZERO
//		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("100"), BigDecimal.ZERO);
//
//		//
//		// Assume:
//		// * Shipment Schedule QtyPicked=0
//		// * HU is empty
//		new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedule)
//				.qtyPicked("0")
//				.assertExpected_ShipmentSchedule("shipment schedule");
//		// HUAssert.assertStorageLevel(helper.getHUContext(), hu, pTomato, new BigDecimal("0")); // throws "@NotFound@ M_HU_Storage_ID@ (@M_Product_ID@:Tomato)"
//
//		//
//		// Move 3 items from shipment schedule to HU
//		{
//			final Map<I_M_ShipmentSchedule, BigDecimal> schedules2qty = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
//			schedules2qty.put(schedule, new BigDecimal("3"));
//			pickingService.addProductQtyToHU(helper.ctx, hu, schedules2qty);
//		}
//
//		//
//		// Assume:
//		// * Shipment Schedule QtyPicked=3
//		// * HU Qty = 3
//		new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedule)
//				.qtyPicked("3")
//				.assertExpected_ShipmentSchedule("shipment schedule");
//		HUAssert.assertStorageLevel(helper.getHUContext(), hu, pTomato, new BigDecimal("3"));
//
//		//
//		// Move back 2 items (from HU to shipment schedule)
//		{
//			final Map<I_M_ShipmentSchedule, BigDecimal> schedules2qty = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
//			schedules2qty.put(schedule, new BigDecimal("2"));
//			pickingService.removeProductQtyFromHU(helper.ctx, hu, schedules2qty);
//		}
//
//		//
//		// Assume:
//		// * Shipment Schedule QtyPicked=1
//		// * HU Qty = 1
//		new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedule)
//				.qtyPicked("1")
//				.assertExpected_ShipmentSchedule("shipment schedule");
//		HUAssert.assertStorageLevel(helper.getHUContext(), hu, pTomato, new BigDecimal("1"));
//
//		//
//		// Move back 1 item (from HU to shipment schedule)
//		{
//			final Map<I_M_ShipmentSchedule, BigDecimal> schedules2qty = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
//			schedules2qty.put(schedule, new BigDecimal("1"));
//			pickingService.removeProductQtyFromHU(helper.ctx, hu, schedules2qty);
//		}
//
//		//
//		// Assume:
//		// * Shipment Schedule QtyPicked=0
//		// * HU Qty = 0
//		new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedule)
//				.qtyPicked("0")
//				.assertExpected_ShipmentSchedule("shipment schedule");
//		HUAssert.assertStorageLevel(helper.getHUContext(), hu, pTomato, new BigDecimal("0"));
//	}
}
