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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.COUNT_Tomatoes_Per_IFCO;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.commonCreateHUTestHelper;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHuDefIFCO;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createHuDefPalet;
import static de.metas.fresh.picking.service.impl.HU2PackingItemTestCommons.createLUs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.service.IPackingItem;
import de.metas.picking.service.PackingItemParts;
import de.metas.picking.service.PackingItems;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.impl.HU2PackingItemsAllocator;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.junit.jupiter.api.Test;

public class HU2PackingItemsAllocatorTest extends AbstractHUTest
{
	//
	// Services & helpers
	private ShipmentScheduleHelper shipmentScheduleHelper;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHUTrxBL huTrxBL;
	private IQueryBL queryBL;

	/** TU */
	private I_M_HU_PI_Item_Product huDefIFCO;
	/** LU */
	private I_M_HU_PI_Item huDefPalet;

	//
	// Context
	private I_M_ShipmentSchedule shipmentSchedule;
	private IPackingItem itemToPack;
	// private PackingContext packingContext;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return commonCreateHUTestHelper();
	}

	@Override
	protected void initialize()
	{
		// Services & Helpers
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huTrxBL = Services.get(IHUTrxBL.class);
		queryBL = Services.get(IQueryBL.class);

		//
		// Handling Units Definition
		huDefIFCO = createHuDefIFCO(helper, COUNT_Tomatoes_Per_IFCO);
		huDefPalet = createHuDefPalet(helper, huDefIFCO);
	}

	private void setupContext(final int qtyToDeliver)
	{
		//
		// Create Item to Pack
		{
			final PackingItemParts parts = PackingItemParts.newInstance();
			this.shipmentSchedule = createAndAppendShipmentSchedule(parts, qtyToDeliver);

			this.itemToPack = PackingItems.newPackingItem(parts);

			// Validate
			assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliver)));
		}

		//
		// Validate initial context state
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliver)));

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked("0")
				.assertExpected_ShipmentSchedule("shipment schedule");
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07466_Picking_Assumtion_Failure_%28103123870103%29
	 */
	@Test
	public void test_allocate_to_LUs()
	{
		setupContext(100); // qtyToDeliver=100

		//
		// Create LUs for Qty=30 and allocate to them
		{
			final List<I_M_HU> luHUs = createLUs(helper, huDefPalet, huDefIFCO, 30);
			assertThat(luHUs).hasSize(1);

			final PackingItemsMap packingItems = PackingItemsMap.ofUnpackedItem(itemToPack);
			HU2PackingItemsAllocator.builder()
					.itemToPack(itemToPack)
					.packingItems(packingItems)
					.pickFromHUs(luHUs)
					.allocate();

			// Validate
			assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(100 - 30)));
			assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
			assertTrue("We shall have packed items", packingItems.hasPackedItems());

			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("30")
					.assertExpected("shipment schedule");

			assertValidShipmentScheduleLUAssignments(luHUs);
			assertThat(POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_QtyPicked.class)).hasSize(1); // we expect one record
		}

		//
		// Create LUs for Qty=60 and allocate to them
		{
			final List<I_M_HU> luHUs = createLUs(helper, huDefPalet, huDefIFCO, 60);
			assertThat(luHUs).hasSize(2);

			final PackingItemsMap packingItems = PackingItemsMap.ofUnpackedItem(itemToPack);
			HU2PackingItemsAllocator.builder()
					.itemToPack(itemToPack)
					.packingItems(packingItems)
					.pickFromHUs(luHUs)
					.allocate();

			// Validate
			assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(100 - 30 - 60)));
			assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
			assertTrue("We shall have packed items", packingItems.hasPackedItems());

			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("90")
					.assertExpected("shipment schedule");

			assertValidShipmentScheduleLUAssignments(luHUs);
			assertThat(POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_QtyPicked.class)).hasSize(1 + 2);
		}

		//
		// Create LUs for Qty=10 and allocate to them
		{
			final List<I_M_HU> luHUs = createLUs(helper, huDefPalet, huDefIFCO, 10);
			assertThat(luHUs).hasSize(1);

			final PackingItemsMap packingItems = PackingItemsMap.ofUnpackedItem(itemToPack);
			HU2PackingItemsAllocator.builder()
					.itemToPack(itemToPack)
					.packingItems(packingItems)
					.pickFromHUs(luHUs)
					.allocate();

			// Validate
			assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(0)));
			assertFalse("We shall NOT have unpacked items", packingItems.hasUnpackedItems());
			assertTrue("We shall have packed items", packingItems.hasPackedItems());

			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("100")
					.assertExpected("shipment schedule");
			assertValidShipmentScheduleLUAssignments(luHUs);
			assertThat(POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_QtyPicked.class)).hasSize(1 + 2 + 1);
		}
	}

	@Test
	public void test_allocate_to_TUs()
	{
		setupContext(100);

		//
		// Create TUs for Qty=30 and allocate to them
		{
			final I_M_HU luHU = null; // no LU
			final List<I_M_HU> tuHUs = createTUs(30);

			final PackingItemsMap packingItems = PackingItemsMap.ofUnpackedItem(itemToPack);
			HU2PackingItemsAllocator.builder()
					.itemToPack(itemToPack)
					.packingItems(packingItems)
					.pickFromHUs(tuHUs)
					.allocate();

			// Validate
			assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(100 - 30)));
			assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
			assertTrue("We shall have packed items", packingItems.hasPackedItems());
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("30")
					.assertExpected("shipment schedule");
			assertValidShipmentScheduleTUAssignments(luHU, tuHUs);
		}
	}

	@Test
	public void test_allocate_to_Empty_TU()
	{
		setupContext(100);

		final I_M_HU luHU = null; // no LU
		final I_M_HU tuHU = createEmptyTU();
		final List<I_M_HU> tuHUs = Collections.singletonList(tuHU);

		final PackingItemsMap packingItems = PackingItemsMap.ofUnpackedItem(itemToPack);
		HU2PackingItemsAllocator.builder()
				.itemToPack(itemToPack)
				.packingItems(packingItems)
				.pickFromHUs(tuHUs)
				.allocate();

		// Validate
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(100)));
		assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
		assertFalse("We shall NOT have packed items", packingItems.hasPackedItems());
		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked("0")
				.assertExpected("shipment schedule");
		assertNoShipmentScheduleTUAssignments(luHU, tuHUs);
	}

	@Test
	public void test_allocate_to_TU_WhichIsNotTopLevel()
	{
		setupContext(100);

		final List<I_M_HU> luHUs = createLUs(helper, huDefPalet, huDefIFCO, COUNT_Tomatoes_Per_IFCO);
		final I_M_HU luHU = luHUs.get(0);

		final List<I_M_HU> aggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		assertThat(aggregateHUs).hasSize(1);

		final I_M_HU aggregateVhu = aggregateHUs.get(0);
		assertTrue(Services.get(IHandlingUnitsBL.class).isAggregateHU(aggregateVhu));

		final PackingItemsMap packingItems = PackingItemsMap.ofUnpackedItem(itemToPack);
		HU2PackingItemsAllocator.builder()
				.itemToPack(itemToPack)
				.packingItems(packingItems)
				.pickFromHU(aggregateVhu)
				.allocate();

		// NOTE: even if we asked to allocate to a non-top level HU
		// we expect the system to figure this out and to automatically set the QtyPicked record's LU

		// Validate
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum().toBigDecimal(), comparesEqualTo(BigDecimal.valueOf(100 - COUNT_Tomatoes_Per_IFCO)));
		assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
		assertTrue("We shall have packed items", packingItems.hasPackedItems());

		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked(BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO))
				.assertExpected("shipment schedule");
		assertValidShipmentScheduleTUAssignments(luHU, aggregateVhu, aggregateVhu);
	}

	private I_M_ShipmentSchedule createAndAppendShipmentSchedule(
			final PackingItemParts parts,
			final int qtyToDeliverInt)
	{
		final Quantity qtyToDeliver = Quantity.of(qtyToDeliverInt, uomEach);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, qtyToDeliver.toBigDecimal(), BigDecimal.ZERO);

		parts.updatePart(PackingItems.newPackingItemPart(schedule)
				.qty(qtyToDeliver)
				.build());

		return schedule;
	}

	private List<I_M_HU> createTUs(final int qtyToLoad)
	{
		if (qtyToLoad % COUNT_Tomatoes_Per_IFCO != 0)
		{
			throw new AdempiereException("QtyToLoad shall be multiple of " + COUNT_Tomatoes_Per_IFCO + " else method assertValidShipmentScheduleLUTUAssignments will fail");
		}

		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final BigDecimal qtyToLoadBD = BigDecimal.valueOf(qtyToLoad);
		final List<I_M_HU> hus = helper.createHUs(huContext, huDefIFCO.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI(), pTomatoId, qtyToLoadBD, uomEach);

		return hus;
	}

	private I_M_HU createEmptyTU()
	{
		// TODO: improve how we perform this test.. i.e. trxName handling

		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);

		final I_M_HU[] hu = new I_M_HU[] { null };
		huTrxBL.createHUContextProcessorExecutor(huContext)
				.run((IHUContextProcessor)huContext1 -> {
					final IHUBuilder huBuilder = handlingUnitsDAO.createHUBuilder(huContext1);
					huBuilder.setDate(helper.getTodayZonedDateTime());
					hu[0] = huBuilder.create(huDefIFCO.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI());
					return null; // not relevant
				});

		InterfaceWrapperHelper.setThreadInheritedTrxName(hu[0]);
		return hu[0];
	}

	private final void assertValidShipmentScheduleLUAssignments(final List<I_M_HU> luHUs)
	{
		for (final I_M_HU luHU : luHUs)
		{
			final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
			assertValidShipmentScheduleTUAssignments(luHU, tuHUs);
		}
	}

	/**
	 *
	 * NOTE: this test assumes the TUs were fully allocated to shipment schedule
	 *
	 * @param luHU
	 * @param tuHUs
	 */
	private final void assertValidShipmentScheduleTUAssignments(final I_M_HU luHU, final List<I_M_HU> tuHUs)
	{
		for (final I_M_HU tuHU : tuHUs)
		{
			final List<I_M_HU> vhus = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
			for (final I_M_HU vhu : vhus)
			{
				assertValidShipmentScheduleTUAssignments(luHU, tuHU, vhu);
			}
		}
	}

	/**
	 * NOTE: this test assumes the TUs were fully allocated to shipment schedule
	 *
	 * @param luHU
	 * @param vhu
	 * @param tuHUs
	 */
	private final void assertValidShipmentScheduleTUAssignments(final I_M_HU luHU, final I_M_HU tuHU, I_M_HU vhu)
	{
		final I_M_ShipmentSchedule_QtyPicked alloc = retrieveM_ShipmentSchedule_QtyPicked_OrNull(shipmentSchedule, luHU, tuHU, vhu);
		assertNotNull("QtyPicked record shall exist for LU=" + luHU + ", TU=" + tuHU + ", VHU=" + vhu, alloc);

		final BigDecimal qtyPicked = alloc.getQtyPicked();
		assertThat("Invalid QtyPicked", qtyPicked, comparesEqualTo(BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO)));

		final BigDecimal huQty = helper.getHUContext()
				.getHUStorageFactory()
				.getStorage(vhu)
				.getProductStorage(pTomatoId)
				.getQty()
				.toBigDecimal();
		assertThat("HU Qty shall match QtyPicked", huQty, comparesEqualTo(qtyPicked));
	}

	private final void assertNoShipmentScheduleTUAssignments(final I_M_HU luHU, final List<I_M_HU> tuHUs)
	{
		for (final I_M_HU tuHU : tuHUs)
		{
			final List<I_M_HU> vhus = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
			for (final I_M_HU vhu : vhus)
			{
				final I_M_ShipmentSchedule_QtyPicked alloc = retrieveM_ShipmentSchedule_QtyPicked_OrNull(shipmentSchedule, luHU, tuHU, vhu);
				assertNull("QtyPicked record shall NOT exist for LU=" + luHU + ", TU=" + tuHU + ", VHU=" + vhu, alloc);
			}
		}
	}

	private final I_M_ShipmentSchedule_QtyPicked retrieveM_ShipmentSchedule_QtyPicked_OrNull(final I_M_ShipmentSchedule shipmentSchedule,
			final I_M_HU luHU,
			final I_M_HU tuHU,
			final I_M_HU vhu)
	{
		final Integer luHUId = luHU == null ? null : luHU.getM_HU_ID();

		final I_M_ShipmentSchedule_QtyPicked alloc = queryBL
				.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class, helper.contextProvider)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, luHUId)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID, tuHU.getM_HU_ID())
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID, vhu.getM_HU_ID())
				.create()
				.firstOnly(I_M_ShipmentSchedule_QtyPicked.class);

		return alloc;
	}
}
