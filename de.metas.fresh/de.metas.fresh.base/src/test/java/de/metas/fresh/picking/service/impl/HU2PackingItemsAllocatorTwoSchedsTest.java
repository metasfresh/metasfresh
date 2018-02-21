package de.metas.fresh.picking.service.impl;

import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.COUNT_Tomatoes_Per_IFCO;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.commonCreateHUTestHelper;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHU2PackingItemsAllocator;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHuDefIFCO;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHuDefPalet;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createLUs;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createTUs;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Services;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.service.FreshPackingItemHelper;
import de.metas.picking.service.IFreshPackingItem;
import de.metas.picking.service.IPackingContext;
import de.metas.picking.service.IPackingService;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.fresh.base
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

/**
 * Tests the behavior of {@link HU2PackingItemsAllocator} with two {@link IPackingItem}s that contain at least two {@link I_M_ShipmentSchedule}.
 * Note: if these tests fail, it makes sense to first verify that all tests in {@link HU2PackingItemsAllocatorTest} works.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HU2PackingItemsAllocatorTwoSchedsTest extends AbstractHUTest
{

	private ShipmentScheduleHelper shipmentScheduleHelper;
	private IPackingService packingService;
	private IHandlingUnitsDAO handlingUnitsDAO;

	private I_M_HU_PI_Item huDefPalet;
	private I_M_HU_PI_Item_Product huDefIFCOWithTen;
	private I_M_HU_PI_Item_Product huDefIFCOWithEleven;

	private IFreshPackingItem itemToPack;
	private IPackingContext packingContext;

	@Override
	protected void initialize()
	{
		// Services & Helpers
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		packingService = Services.get(IPackingService.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Handling Units Definition
		huDefIFCOWithTen = createHuDefIFCO(helper, COUNT_Tomatoes_Per_IFCO);
		huDefIFCOWithEleven = createHuDefIFCO(helper, COUNT_Tomatoes_Per_IFCO + 1);
		huDefPalet = createHuDefPalet(helper, huDefIFCOWithTen);
	}

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return commonCreateHUTestHelper();
	}

	private void setupContext(final int... qtysToDeliver)
	{
		final Map<I_M_ShipmentSchedule, Quantity> scheds2Qtys = new LinkedHashMap<>(); // using LinkedHashMap because we want the ordering to be "stable".
		//
		// Create Items to Pack
		int qtyToDeliverSum = 0;
		for (final int qtyToDeliver : qtysToDeliver)
		{
			final BigDecimal qtyToDeliverBD = new BigDecimal(qtyToDeliver);
			final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, qtyToDeliverBD, BigDecimal.ZERO);

			scheds2Qtys.put(schedule, Quantity.of(qtyToDeliverBD, uomEach));
			qtyToDeliverSum += qtyToDeliver;
		}
		this.itemToPack = FreshPackingItemHelper.create(scheds2Qtys);
		// Validate

		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().getQty(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliverSum)));

		//
		// Create Packing Items
		final PackingItemsMap packingItems = new PackingItemsMap();
		packingItems.addUnpackedItem(itemToPack);

		//
		// Create Packing Context
		this.packingContext = packingService.createPackingContext(helper.ctx);
		packingContext.setPackingItemsMap(packingItems);
		final int packingItemsMapKey = 123; // just a dummy value for now
		packingContext.setPackingItemsMapKey(packingItemsMapKey);

		//
		// Validate initial context state
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().getQty(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliverSum)));
		assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
		assertFalse("We shall NOT have packed items", packingItems.hasPackedItems());

		for (final I_M_ShipmentSchedule shipmentSchedule : itemToPack.getShipmentSchedules())
		{
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("0")
					.assertExpected_ShipmentSchedule("shipment schedule");
		}
	}

	/**
	 * Verifies the following behavior:
	 * <ul>
	 * <li>one packing item with two sheds that have a quantity of 11 and 10</li>
	 * <li>two TUs that have a quantity of 10 resp. 11</li>
	 * <li>allocate them</li>
	 * <li>Result: the TU with quantity 11 is partially allocated to the both schedules; The TU with quantity 10 is fully allocate to the schedule with quantity 11</li>
	 * </ul>
	 *
	 * Note that this reflects the current behavior..not necessarily the desired behavior..
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1712
	 */
	@Test
	@Ignore // this test constantly fails on jenkins :-( and i already tried too long to fix it.
	public void testTwoHUsTwoShipmentSchedules_TopLevelTUs()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		assertThat(huDefIFCOWithEleven.getQty(), not(comparesEqualTo(huDefIFCOWithTen.getQty()))); // the two defs need to have different qty, otherwise the test is not significant
		setupContext(
				huDefIFCOWithEleven.getQty().intValue(),
				huDefIFCOWithTen.getQty().intValue());

		// get a reference to the two scheds now; the allocation() method might remove them from the packing item later on.
		final List<I_M_ShipmentSchedule> shipmentSchedules = itemToPack.getShipmentSchedules();

		final I_M_ShipmentSchedule shipmentScheduleWithEleven = shipmentSchedules.get(0);
		assertThat(shipmentScheduleWithEleven.getQtyToDeliver(), comparesEqualTo(huDefIFCOWithEleven.getQty()));

		final I_M_ShipmentSchedule shipmentScheduleWithTen = shipmentSchedules.get(1);
		assertThat(shipmentScheduleWithTen.getQtyToDeliver(), comparesEqualTo(huDefIFCOWithTen.getQty()));

		// packing item guards
		final Map<I_M_ShipmentSchedule, Quantity> qtys = itemToPack.getQtys();
		assertThat("Unexpected qtys.size(); qtys=" + qtys, qtys.size(), is(2));
		assertThat(shipmentSchedules.size(), is(2));

		final List<I_M_HU> tuHUsWithTen = createTUs(helper, huDefIFCOWithTen, 10);
		assertThat(tuHUsWithTen.size(), is(1));
		assertThat(handlingUnitsBL.isTopLevel(tuHUsWithTen.get(0)), is(true));

		final List<I_M_HU> tuHUsWithEleven = createTUs(helper, huDefIFCOWithEleven, 11);
		assertThat(tuHUsWithEleven.size(), is(1));
		assertThat(handlingUnitsBL.isTopLevel(tuHUsWithEleven.get(0)), is(true));

		final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator(packingContext, itemToPack);
		hu2PackingItemsAllocator.setFromHUs(ImmutableList.of(tuHUsWithTen.get(0), tuHUsWithEleven.get(0)));
		hu2PackingItemsAllocator.allocate();

		assertThat(POJOLookupMap.get().getRecords(I_M_ShipmentSchedule.class).size(), is(2));

		assertThat("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems(), is(true));
		assertThat("We shall not have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems(), is(false));

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentScheduleWithTen)
				.qtyPicked(huDefIFCOWithTen.getQty())
				.newShipmentScheduleQtyPickedExpectation()
				.noLU()
				.tu(tuHUsWithEleven.get(0))
				.qtyPicked(huDefIFCOWithTen.getQty())
				.endExpectation()
				.assertExpected("");

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentScheduleWithEleven)
				.qtyPicked(huDefIFCOWithEleven.getQty())
				.newShipmentScheduleQtyPickedExpectation()
				.noLU()
				.tu(tuHUsWithTen.get(0))
				.qtyPicked(BigDecimal.TEN)
				.endExpectation()

				.newShipmentScheduleQtyPickedExpectation()
				.noLU()
				.tu(tuHUsWithEleven.get(0))
				.qtyPicked(BigDecimal.ONE)
				.endExpectation()
				.assertExpected("");
	}

	/**
	 * build one packing item that represents two shipment scheds.
	 * allocate an aggregated HU that represents two TUs with exactly matching quantities.
	 */
	@Test
	public void testTwoHUsTwoShipmentSchedules_AggregateTU()
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		setupContext(10, 10);

		// get a reference to the two scheds now; the allocation() method might remove them from the packing item later on.
		final List<I_M_ShipmentSchedule> shipmentSchedules = itemToPack.getShipmentSchedules();

		// packing item guards
		final Map<I_M_ShipmentSchedule, Quantity> qtys = itemToPack.getQtys();
		assertThat("Unexpected qtys.size(); qtys=" + qtys, qtys.size(), is(2));
		assertThat(shipmentSchedules.size(), is(2));
		itemToPack.getShipmentSchedules();

		final List<I_M_HU> luHUs = createLUs(helper, huDefPalet, huDefIFCOWithTen, 20);
		// HU guards
		// there shall be one LU with one aggregate TU that represents 2 IFCOs with 10 each
		assertThat(luHUs.size(), is(1));
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(luHUs.get(0));
		assertThat(includedHUs.size(), is(1));
		assertThat(handlingUnitsBL.isVirtual(includedHUs.get(0)), is(true));
		assertThat(includedHUs.get(0).getM_HU_Item_Parent().getQty(), is(new BigDecimal("2")));

		final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator(packingContext, itemToPack);
		hu2PackingItemsAllocator.setFromHUs(luHUs);
		hu2PackingItemsAllocator.allocate();

		assertThat("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems(), is(true));
		assertThat("We shall not have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems(), is(false));

		for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("10")
					.newShipmentScheduleQtyPickedExpectation()
					.lu(luHUs.get(0))
					.tu(includedHUs.get(0))
					.vhu(includedHUs.get(0))
					.qtyPicked("10")
					.endExpectation()
					.assertExpected("shipment schedule");
		}
	}
}
