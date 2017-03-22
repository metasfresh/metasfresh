package de.metas.fresh.picking.service.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.form.PackingItemsMap;
import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.IFreshPackingItem;
import de.metas.fresh.picking.service.IPackingContext;
import de.metas.fresh.picking.service.IPackingService;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class HU2PackingItemsAllocatorTest extends AbstractHUTest
{
	//
	// Services & helpers
	private ShipmentScheduleHelper shipmentScheduleHelper;
	private IPackingService packingService;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHUTrxBL huTrxBL;
	private IQueryBL queryBL;

	/** TU */
	private I_M_HU_PI huDefIFCO;
	/** LU */
	private I_M_HU_PI huDefPalet;

	private static final int COUNT_IFCOs_Per_Palet = 5;
	private static final int COUNT_Tomatoes_Per_IFCO = 10;

	//
	// Context
//	private HU2PackingItemsAllocator hu2PackingItemsAllocator;
	private I_M_ShipmentSchedule shipmentSchedule;
	private IFreshPackingItem itemToPack;
	private IPackingContext packingContext;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				// no transaction by default
				return ITrx.TRXNAME_None;
			}
		};
	}

	@Override
	protected void initialize()
	{
		// Services & Helpers
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		packingService = Services.get(IPackingService.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huTrxBL = Services.get(IHUTrxBL.class);
		queryBL = Services.get(IQueryBL.class);

		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomato, BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO), uomEach);
		}
		huDefPalet = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			final I_C_BPartner bpartner = null;
			helper.createHU_PI_Item_IncludedHU(huDefPalet, huDefIFCO, BigDecimal.valueOf(COUNT_IFCOs_Per_Palet), bpartner);
		}
	}

	private void setupContext(final int qtyToDeliver)
	{
		//
		// Create Item to Pack
		{
			final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
			this.shipmentSchedule = createAndAppendShipmentSchedule(scheds2Qtys, qtyToDeliver);

			this.itemToPack = FreshPackingItemHelper.create(scheds2Qtys);

			// Validate
			assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliver)));
		}

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
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), comparesEqualTo(BigDecimal.valueOf(qtyToDeliver)));
		assertTrue("We shall have unpacked items", packingItems.hasUnpackedItems());
		assertFalse("We shall NOT have packed items", packingItems.hasPackedItems());
		
		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked("0")
				.assertExpected_ShipmentSchedule("shipment schedule");
	}

	/**
	 * Creates HU to Packing Items Allocator (i.e. class under test)
	 */
	private HU2PackingItemsAllocator createHU2PackingItemsAllocator()
	{
		final HU2PackingItemsAllocator hu2PackingItemsAllocator = new HU2PackingItemsAllocator();
		hu2PackingItemsAllocator.setItemToPack(itemToPack);
		hu2PackingItemsAllocator.setPackingContext(packingContext);
		return hu2PackingItemsAllocator;
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
			final List<I_M_HU> luHUs = createLUs(30);
			final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator();
			hu2PackingItemsAllocator.setFromHUs(luHUs);
			hu2PackingItemsAllocator.allocate();

			// Validate
			Assert.assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), Matchers.comparesEqualTo(BigDecimal.valueOf(100 - 30)));
			Assert.assertTrue("We shall have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems());
			Assert.assertTrue("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems());
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("30")
					.assertExpected_ShipmentSchedule("shipment schedule");
			assertValidShipmentScheduleLUAssignments(luHUs);
		}

		//
		// Create LUs for Qty=60 and allocate to them
		{
			final List<I_M_HU> luHUs = createLUs(60);
			final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator();
			hu2PackingItemsAllocator.setFromHUs(luHUs);
			hu2PackingItemsAllocator.allocate();

			// Validate
			Assert.assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), Matchers.comparesEqualTo(BigDecimal.valueOf(100 - 30 - 60)));
			Assert.assertTrue("We shall have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems());
			Assert.assertTrue("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems());
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("90")
					.assertExpected_ShipmentSchedule("shipment schedule");
			assertValidShipmentScheduleLUAssignments(luHUs);
		}

		//
		// Create LUs for Qty=10 and allocate to them
		{
			final List<I_M_HU> luHUs = createLUs(10);
			final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator();
			hu2PackingItemsAllocator.setFromHUs(luHUs);
			hu2PackingItemsAllocator.allocate();

			// Validate
			Assert.assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), Matchers.comparesEqualTo(BigDecimal.valueOf(0)));
			Assert.assertFalse("We shall NOT have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems());
			Assert.assertTrue("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems());
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("100")
					.assertExpected_ShipmentSchedule("shipment schedule");
			assertValidShipmentScheduleLUAssignments(luHUs);
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
			final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator();
			hu2PackingItemsAllocator.setFromHUs(tuHUs);
			hu2PackingItemsAllocator.allocate();

			// Validate
			Assert.assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), Matchers.comparesEqualTo(BigDecimal.valueOf(100 - 30)));
			Assert.assertTrue("We shall have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems());
			Assert.assertTrue("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems());
			new ShipmentScheduleQtyPickedExpectations()
					.shipmentSchedule(shipmentSchedule)
					.qtyPicked("30")
					.assertExpected_ShipmentSchedule("shipment schedule");
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
		final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator();
		hu2PackingItemsAllocator.setFromHUs(tuHUs);
		hu2PackingItemsAllocator.allocate();

		// Validate
		Assert.assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), Matchers.comparesEqualTo(BigDecimal.valueOf(100)));
		Assert.assertTrue("We shall have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems());
		Assert.assertFalse("We shall NOT have packed items", packingContext.getPackingItemsMap().hasPackedItems());
		new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(shipmentSchedule)
				.qtyPicked("0")
				.assertExpected_ShipmentSchedule("shipment schedule");
		assertNoShipmentScheduleTUAssignments(luHU, tuHUs);
	}

	@Test
	public void test_allocate_to_TU_WhichIsNotTopLevel()
	{
		setupContext(100);

		final List<I_M_HU> luHUs = createLUs(COUNT_Tomatoes_Per_IFCO);
		final I_M_HU luHU = luHUs.get(0);
		
		final List<I_M_HU> aggregateHUs = handlingUnitsDAO.retrieveIncludedHUs(luHU);
		assertThat(aggregateHUs.size(), is(1));
		
		final I_M_HU aggregateVhu = aggregateHUs.get(0);
		assertTrue(Services.get(IHandlingUnitsBL.class).isAggregateHU(aggregateVhu));

		final HU2PackingItemsAllocator hu2PackingItemsAllocator = createHU2PackingItemsAllocator();
		hu2PackingItemsAllocator.setFromHUs(Collections.singletonList(aggregateVhu));
		hu2PackingItemsAllocator.allocate();

		// NOTE: even if we asked to allocate to a non-top level HU
		// we expect the system to figure this out and to automatically set the QtyPicked record's LU

		// Validate
		assertThat("Invalid itemToPack - Qty", itemToPack.getQtySum(), comparesEqualTo(BigDecimal.valueOf(100 - COUNT_Tomatoes_Per_IFCO)));
		assertTrue("We shall have unpacked items", packingContext.getPackingItemsMap().hasUnpackedItems());
		assertTrue("We shall have packed items", packingContext.getPackingItemsMap().hasPackedItems());

		new ShipmentScheduleQtyPickedExpectations()
				.qtyPicked(BigDecimal.valueOf(COUNT_Tomatoes_Per_IFCO))
				.assertExpected_ShipmentSchedule("shipment schedule", shipmentSchedule);
		assertValidShipmentScheduleTUAssignments(luHU, aggregateVhu, aggregateVhu);
	}

	private final I_M_ShipmentSchedule createAndAppendShipmentSchedule(final Map<I_M_ShipmentSchedule, BigDecimal> scheds2Qtys, final int qtyToDeliver)
	{
		final BigDecimal qtyToDeliverBD = new BigDecimal(qtyToDeliver);
		final I_M_ShipmentSchedule schedule = shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, qtyToDeliverBD, BigDecimal.ZERO);

		scheds2Qtys.put(schedule, qtyToDeliverBD);

		return schedule;
	}

	private List<I_M_HU> createLUs(final int qtyToLoad)
	{
		if (qtyToLoad % COUNT_Tomatoes_Per_IFCO != 0)
		{
			throw new AdempiereException("QtyToLoad shall be multiple of " + COUNT_Tomatoes_Per_IFCO + " else method assertValidShipmentScheduleLUTUAssignments will fail");
		}

		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final BigDecimal qtyToLoadBD = BigDecimal.valueOf(qtyToLoad);
		final List<I_M_HU> hus = helper.createHUs(huContext, huDefPalet, pTomato, qtyToLoadBD, uomEach);

		return hus;
	}

	private List<I_M_HU> createTUs(final int qtyToLoad)
	{
		if (qtyToLoad % COUNT_Tomatoes_Per_IFCO != 0)
		{
			throw new AdempiereException("QtyToLoad shall be multiple of " + COUNT_Tomatoes_Per_IFCO + " else method assertValidShipmentScheduleLUTUAssignments will fail");
		}

		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final BigDecimal qtyToLoadBD = BigDecimal.valueOf(qtyToLoad);
		final List<I_M_HU> hus = helper.createHUs(huContext, huDefIFCO, pTomato, qtyToLoadBD, uomEach);

		return hus;
	}

	private I_M_HU createEmptyTU()
	{
		// TODO: improve how we perform this test.. i.e. trxName handling

		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);

		final I_M_HU[] hu = new I_M_HU[] { null };
		huTrxBL.createHUContextProcessorExecutor(huContext)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(IHUContext huContext)
					{
						final IHUBuilder huBuilder = handlingUnitsDAO.createHUBuilder(huContext);
						huBuilder.setDate(helper.getTodayDate());
						hu[0] = huBuilder.create(huDefIFCO);
						return null; // not relevant
					}
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
				.getProductStorage(pTomato)
				.getQty();
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
				Assert.assertNull("QtyPicked record shall NOT exist for LU=" + luHU + ", TU=" + tuHU + ", VHU=" + vhu, alloc);
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
