package de.metas.tourplanning.integrationtest;

/*
 * #%L
 * de.metas.swat.base
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_Tour_Instance;

/**
 * Test a scenario which involves
 * <ul>
 * <li>delivery days
 * <li>orders and shipment schedules allocated to delivery days
 * <li>checking if tour instance is automatically created
 * <li>changing shipment schedules which were allocated to processed delivery days
 * </ul>
 * 
 * @author tsa
 *
 */
public class TourInstance_DeliveryDay_ShipmentSchedule_IntegrationTest extends TourPlanningTestBase
{
	//
	// Master data
	protected I_M_Tour tour;
	protected I_M_TourVersion tourVersion;
	protected I_C_BPartner bpartner;
	protected I_C_BPartner_Location bpLocation;
	protected I_M_Product product;

	@Override
	protected void afterInit()
	{
		//
		// Create master data
		tour = createTour("tour01");
		tourVersion = createTourVersion(tour, LocalDate.of(2014, 1, 1));
		bpartner = createBPartner("bp1");
		bpLocation = createBPLocation(bpartner);

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class, contextProvider);
		InterfaceWrapperHelper.save(product);
	}

	@Test
	public void test()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5);
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5);
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5);

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);
		
		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999", // date promised
				dd2.getDeliveryDate() // preparation date expected
		);

		//
		// Create and validate Shipment Schedule
		final I_M_ShipmentSchedule shipmentSchedule = createShipmentSchedule(order, 10);
		assertDeliveryDayAlloc(dd2, shipmentSchedule);

		//
		// Change Shipment Schedule QtyOrdered and make sure allocation is updated
		{
			performTourPlanningRelevantChangeAndExpectException(shipmentSchedule, false); // exceptionExpected = false

			// Validate allocation
			assertDeliveryDayAlloc(dd2, shipmentSchedule);
		}

		//
		// Make sure a generic tour instance was created because we have amounts to rollup
		final I_M_Tour_Instance tourInstance;
		{
			InterfaceWrapperHelper.refresh(dd2);
			tourInstance = dd2.getM_Tour_Instance();
			Assert.assertNotNull("tour instance shall be created for " + dd2, tourInstance);
			assertProcessed(false, tourInstance);
		}

		//
		// Process tour instance
		{
			tourInstanceBL.process(tourInstance);
			assertProcessed(true, tourInstance);
		}

		//
		// Try to do an unrelevant change to shipment schedule
		// => shall not update the allocation => shall be ok
		{
			InterfaceWrapperHelper.refresh(shipmentSchedule);
			shipmentSchedule.setProductDescription("bla bla bla - " + UUID.randomUUID());
			InterfaceWrapperHelper.save(shipmentSchedule); // => shall be ok, no exceptions thrown
		}

		//
		// Now, let change shipment schedule's QtyOrdered
		// => shall throw an exception because tour instance is processed (and delivery day too)
		performTourPlanningRelevantChangeAndExpectException(shipmentSchedule, true); // exceptionExpected = true

		//
		// Create and test a second Order, similar with first one
		// Note: because the tour instance was processed, the delivery date from d2 is no longer available!
		final I_C_Order order2 = createOrder(
				"08.09.2014 23:59:59.999", // date promised
				dd1.getDeliveryDate() // preparation date expected
		);

		//
		// Create and validate a second Shipment Schedule
		final I_M_ShipmentSchedule shipmentSchedule2 = createShipmentSchedule(order2, 10);
		assertDeliveryDayAlloc(dd1, shipmentSchedule2);
		
		SystemTime.resetTimeSource(); // cleanup
	}

	/**
	 * 
	 * @param shipmentSchedule
	 * @return true if the change which was performed is relevant to tour planning module (so delivery day is expected to change)
	 */
	protected boolean performTourPlanningRelevantChange(final I_M_ShipmentSchedule shipmentSchedule)
	{
		// Increase QtyOrdered by 10
		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(shipmentSchedule);
		shipmentSchedule.setQtyOrdered_Calculated(qtyOrdered.add(BigDecimal.valueOf(10)));

		// NOTE: because the de.metas.tourplanning.api.impl.ShipmentScheduleDeliveryDayHandler.updateDeliveryDayWhenAllocationChanged(I_M_DeliveryDay, I_M_DeliveryDay_Alloc, I_M_DeliveryDay_Alloc)
		// method does nothing, we cannot perform a relevant change
		return false;
	}

	protected final void performTourPlanningRelevantChangeAndExpectException(final I_M_ShipmentSchedule shipmentSchedule, final boolean exceptionExpected)
	{
		InterfaceWrapperHelper.refresh(shipmentSchedule);
		
		boolean exceptionExpectedActual = exceptionExpected;
		final boolean hasRelevantChange = performTourPlanningRelevantChange(shipmentSchedule);
		if (!hasRelevantChange)
		{
			exceptionExpectedActual = false;
		}

		Exception exception = null;
		try
		{
			InterfaceWrapperHelper.save(shipmentSchedule);
		}
		catch (RuntimeException e)
		{
			if (!exceptionExpectedActual)
			{
				throw e;
			}
			exception = e;
		}

		if (exceptionExpectedActual && exception == null)
		{
			Assert.fail("An exception was expected when saving " + shipmentSchedule);
		}
	}

	@Override
	protected I_M_DeliveryDay createDeliveryDay(final String deliveryDateTimeStr, final int bufferHours)
	{
		final I_M_DeliveryDay deliveryDay = InterfaceWrapperHelper.newInstance(I_M_DeliveryDay.class, contextProvider);
		deliveryDay.setC_BPartner(bpartner);
		deliveryDay.setC_BPartner_Location(bpLocation);
		deliveryDay.setDeliveryDate(toDateTimeTimestamp(deliveryDateTimeStr));
		deliveryDay.setBufferHours(bufferHours);
		deliveryDay.setIsManual(false);
		deliveryDay.setIsToBeFetched(false);
		deliveryDay.setM_Tour(tour);
		deliveryDay.setM_TourVersion(tourVersion);
		deliveryDay.setM_Tour_Instance(null);
		deliveryDay.setProcessed(false);

		deliveryDayBL.setDeliveryDateTimeMax(deliveryDay);
		Assert.assertNotNull("DeliveryDateTimeMax shall be set", deliveryDay.getDeliveryDateTimeMax());

		InterfaceWrapperHelper.save(deliveryDay);

		return deliveryDay;
	}

	private I_C_Order createOrder(final String datePromisedStr, final Timestamp preparationDateExpected)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class, contextProvider);
		order.setC_BPartner(bpartner);
		order.setC_BPartner_Location(bpLocation);

		order.setIsSOTrx(true);
		order.setDatePromised(toDateTimeTimestamp(datePromisedStr));
		order.setPreparationDate(null); // to be updated

		InterfaceWrapperHelper.save(order);

		// NOTE: we expect PreparationDate to be set by model validator

		Assert.assertEquals("Invalid order PreparationDate: " + order,
				preparationDateExpected,
				order.getPreparationDate());

		return order;
	}

	@Override
	protected I_M_DeliveryDay_Alloc assertDeliveryDayAlloc(final I_M_DeliveryDay deliveryDayExpected, final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_DeliveryDay_Alloc alloc = super.assertDeliveryDayAlloc(deliveryDayExpected, shipmentSchedule);

		// task 09005: make sure the correct qtyOrdered is taken from the shipmentSchedule
		final BigDecimal qtyOrdered = Services.get(IShipmentScheduleEffectiveBL.class).computeQtyOrdered(shipmentSchedule);
		
		Assert.assertEquals("Invalid allocation QtyOrdered: " + alloc, qtyOrdered, alloc.getQtyOrdered());

		return alloc;
	}

}
