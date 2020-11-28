package de.metas.handlingunits.allocation.impl;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.expectations.AllocationResultExpectation;
import de.metas.handlingunits.expectations.ShipmentScheduleQtyPickedExpectations;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.util.ShipmentScheduleHelper;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.junit.jupiter.api.Test;

public class ShipmentScheduleListAllocationSourceTest extends AbstractHUTest
{
	private ShipmentScheduleHelper shipmentScheduleHelper;
	// private IShipmentScheduleBL shipmentScheduleBL = null;
	private I_M_HU_PI huDefIFCO;

	@Override
	protected void initialize()
	{
		shipmentScheduleHelper = new ShipmentScheduleHelper(helper);
		// shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		// NOTE: not needed, it's already called by AbstractHUTest
		// new de.metas.handlingunits.model.validator.Main().registerFactories();

		//
		// Handling Units Definition
		huDefIFCO = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(huDefIFCO);
			helper.assignProduct(itemMA, pTomatoId, new BigDecimal("100"), uomEach);
			helper.createHU_PI_Item_PackingMaterial(huDefIFCO, pmIFCO);
		}
	}

	@Test
	public void test()
	{
		final List<I_M_ShipmentSchedule> schedules = Arrays.asList(
				shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), new BigDecimal("1")),
				shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), new BigDecimal("2")),
				shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), new BigDecimal("3")));
		final ShipmentScheduleListAllocationSource source = new ShipmentScheduleListAllocationSource(schedules);

		final IMutableHUContext huContext = helper.getHUContext();
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, pTomato, new BigDecimal("20"), uomEach, helper.getTodayZonedDateTime());
		final IAllocationResult result = source.unload(request);

		final List<IHUTransactionCandidate> trxs = new ArrayList<>(result.getTransactions());

		//
		// Validate result
		new AllocationResultExpectation()
				.qtyToAllocate("0")
				.qtyAllocated("20")
				.completed(true)
				.assertExpected(result);

		//
		// Validate result transactions
		// NOTE: at this point Shipment Schedule's QtyPicked is not changed. It will be changed when transactions will be processed
		Assert.assertEquals("Invalid transactions count", 3, trxs.size());
		shipmentScheduleHelper.assertValidTransaction(trxs.get(0), schedules.get(0), new BigDecimal("-9"), new BigDecimal("1"));
		shipmentScheduleHelper.assertValidTransaction(trxs.get(1), schedules.get(1), new BigDecimal("-8"), new BigDecimal("2"));
		shipmentScheduleHelper.assertValidTransaction(trxs.get(2), schedules.get(2), new BigDecimal("-3"), new BigDecimal("3"));

		//
		// Process transactions:
		{
			final I_M_HU_Item dummyItem = createDummyItem();

			final List<IHUTransactionCandidate> trxsWithCounterparts = shipmentScheduleHelper.createHUTransactionDummyCounterparts(trxs, dummyItem);
			final IAllocationResult allocationResult = AllocationUtils.createQtyAllocationResult(
					BigDecimal.ZERO, // qtyToAllocate // N/A, not important
					BigDecimal.ZERO, // qtyAllocated // N/A, not important
					trxsWithCounterparts,
					Collections.<IHUTransactionAttribute> emptyList());
			helper.trxBL.createTrx(huContext, allocationResult);
		}

		//
		// Validate result transactions (after processing them)
		shipmentScheduleHelper.assertValidTransaction(trxs.get(0), schedules.get(0), new BigDecimal("-9"), new BigDecimal("10"));
		shipmentScheduleHelper.assertValidTransaction(trxs.get(1), schedules.get(1), new BigDecimal("-8"), new BigDecimal("10"));
		shipmentScheduleHelper.assertValidTransaction(trxs.get(2), schedules.get(2), new BigDecimal("-3"), new BigDecimal("6"));
	}

	/**
	 * Scenario:
	 * <ul>
	 * <li>create some shipment schedules with initial QtyPicked
	 * <li>create a HU
	 * <li>pick items from shipment schedule and allocate them to HU
	 * <li>reverse and destroy the HU
	 * <li>make sure HU is empty and destroyed
	 * </ul>
	 */
	@Test
	public void test_with_HULoader_and_HUProducerDestination_then_reverseAndDestroy()
	{
		final IMutableHUContext huContext = helper.getHUContext();
		//
		// Allocation Source
		final List<I_M_ShipmentSchedule> schedules = Arrays.asList(
				// Product / UOM / QtyToDeliver // QtyPickedInitial
				shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), new BigDecimal("1")),
				shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), new BigDecimal("2")),
				shipmentScheduleHelper.createShipmentSchedule(pTomato, uomEach, new BigDecimal("10"), new BigDecimal("3")));
		final ShipmentScheduleListAllocationSource source = new ShipmentScheduleListAllocationSource(schedules);

		//
		// Allocation Destination: IFCO hu producer
		final HUProducerDestination destination = HUProducerDestination.of(huDefIFCO);

		//
		// Allocation Request
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, pTomato, new BigDecimal("20"), uomEach, helper.getTodayZonedDateTime());

		//
		// Execute HU Load
		final IAllocationResult result = HULoader.of(source, destination)
				.load(request);
		// NOTE: transactions are already processed at this point

		Assert.assertTrue("Request shall be completelly allocated: " + result, result.isCompleted());

		//
		// Validate created HU
		Assert.assertEquals("There shall be only one HU created: " + destination.getCreatedHUs(),
				1, // expecteds
				destination.getCreatedHUs().size());
		final I_M_HU createdHU = destination.getCreatedHUs().get(0);

		//
		// Validate result transactions
		{
			Assert.assertEquals("Invalid trx count in result: " + result, 6, result.getTransactions().size());
			final List<IHUTransactionCandidate> scheduleTrxs = Arrays.asList(
					result.getTransactions().get(0),
					// result.getTransactions().get(1), // HU_Item trx
					result.getTransactions().get(2),
					// result.getTransactions().get(3), // HU_Item trx
					result.getTransactions().get(4)
			// result.getTransactions().get(5), // HU_Item trx
			);

			shipmentScheduleHelper.assertValidTransaction(scheduleTrxs.get(0), schedules.get(0), new BigDecimal("-9"), new BigDecimal("10"));
			//@formatter:off
			new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(schedules.get(0))
				.newShipmentScheduleQtyPickedExpectation()
					.qtyPicked("1").noHUs()
					.endExpectation()
				.newShipmentScheduleQtyPickedExpectation()
					.qtyPicked("9").tu(createdHU)
					.endExpectation()
				.assertExpected_PickedButNotDelivered("schedule 0");
			//@formatter:on

			shipmentScheduleHelper.assertValidTransaction(scheduleTrxs.get(1), schedules.get(1), new BigDecimal("-8"), new BigDecimal("10"));
			//@formatter:off
			new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(schedules.get(1))
				.newShipmentScheduleQtyPickedExpectation()
					.qtyPicked("2").noHUs()
					.endExpectation()
				.newShipmentScheduleQtyPickedExpectation()
					.qtyPicked("8").tu(createdHU)
					.endExpectation()
				.assertExpected_PickedButNotDelivered("schedule 1");
			//@formatter:on

			shipmentScheduleHelper.assertValidTransaction(scheduleTrxs.get(2), schedules.get(2), new BigDecimal("-3"), new BigDecimal("6"));
			//@formatter:off
			new ShipmentScheduleQtyPickedExpectations()
				.shipmentSchedule(schedules.get(2))
				.newShipmentScheduleQtyPickedExpectation()
					.qtyPicked("3").noHUs()
					.endExpectation()
				.newShipmentScheduleQtyPickedExpectation()
					.qtyPicked("3").tu(createdHU)
					.endExpectation()
				.assertExpected_PickedButNotDelivered("schedule 2");
			//@formatter:on
		}

		// //
		// // Destroy created HUs
		// Services.get(IHandlingUnitsBL.class).reverseAndDestroy(destination.getCreatedHUs());
		// HUAssert.assertHUStoragesAreEmpty(destination.getCreatedHUs());
		// HUAssert.assertAllStoragesAreValid();
		//
		// //
		// // Validate Shipment Schedules QtyPicked after HU was destroyed
		// {
//			//@formatter:off
//			new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedules.get(0))
//				.qtyPicked("1")
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("1").noHUs()
//					.endExpectation()
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("9").tu(createdHU)
//					.endExpectation()
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("-9").tu(createdHU)
//					.endExpectation()
//				.assertExpected_PickedButNotDelivered("schedule 0");
//			//@formatter:on
		//
//			//@formatter:off
//			new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedules.get(1))
//				.qtyPicked("2")
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("2").noHUs()
//					.endExpectation()
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("8").tu(createdHU)
//					.endExpectation()
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("-8").tu(createdHU)
//					.endExpectation()
//				.assertExpected_PickedButNotDelivered("schedule 1");
//			//@formatter:on
		//
//			//@formatter:off
//			new ShipmentScheduleQtyPickedExpectations()
//				.shipmentSchedule(schedules.get(2))
//				.qtyPicked("3")
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("3").noHUs()
//					.endExpectation()
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("3").tu(createdHU)
//					.endExpectation()
//				.newShipmentScheduleQtyPickedExpectation()
//					.qtyPicked("-3").tu(createdHU)
//					.endExpectation()
//				.assertExpected_PickedButNotDelivered("schedule 2");
//			//@formatter:on
		// }
	}

	private I_M_HU_Item createDummyItem()
	{
		final IHandlingUnitsDAO dao = Services.get(IHandlingUnitsDAO.class);
		final IHUContext huContext = helper.getHUContext();

		final I_M_HU_PI pi = dao.retrieveVirtualPI(huContext.getCtx());

		final IHUBuilder huBuilder = dao.createHUBuilder(huContext);
		huBuilder.setM_HU_Item_Parent(null); // no parent
		huBuilder.setBPartnerId(null); // no BPartner

		final I_M_HU hu = huBuilder.create(pi);

		return dao.retrieveItems(hu).get(0);
	}

}
