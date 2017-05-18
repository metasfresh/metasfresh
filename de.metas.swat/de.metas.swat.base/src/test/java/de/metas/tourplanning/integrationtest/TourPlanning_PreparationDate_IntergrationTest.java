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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Order;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;

public class TourPlanning_PreparationDate_IntergrationTest extends TourPlanningTestBase
{
	//
	// Master data
	private I_M_Tour tour;
	private I_M_TourVersion tourVersion;
	private I_C_BPartner bpartner;
	private I_C_BPartner_Location bpLocation;

	@Override
	protected void afterInit()
	{
		//
		// Create master data
		tour = createTour("tour01");
		tourVersion = createTourVersion(tour, createDate("2014-01-01"));
		bpartner = createBPartner("bp1");
		bpLocation = createBPLocation(bpartner);
	}

	@Test
	public void test()
	{
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5);
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5); // => we expect this one to be used
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5);

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);
		
		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999" // date promised
				);
		Assert.assertEquals("Invalid order PreparationDate: " + order,
				toDateTimeTimestamp("08.09.2014 15:00:00.000"),
				order.getPreparationDate());

		//
		// Create and validate Shipment Schedule
		final I_M_ShipmentSchedule shipmentSchedule = createShipmentSchedule(order);
		Assert.assertEquals("Invalid shipment schedule's DeliveryDate: " + shipmentSchedule,
				toDateTimeTimestamp("08.09.2014 23:59:59.999"),
				shipmentSchedule.getDeliveryDate());
		Assert.assertEquals("Invalid shipment schedule's PreparationDate: " + shipmentSchedule,
				toDateTimeTimestamp("08.09.2014 15:00:00.000"),
				shipmentSchedule.getPreparationDate());

		assertDeliveryDayAlloc(dd2, shipmentSchedule);
	}

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

	private I_C_Order createOrder(final String datePromisedStr)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class, contextProvider);
		order.setC_BPartner(bpartner);
		order.setC_BPartner_Location(bpLocation);

		order.setIsSOTrx(true);
		order.setDatePromised(toDateTimeTimestamp(datePromisedStr));
		order.setPreparationDate(null); // to be updated

		InterfaceWrapperHelper.save(order);

		// NOTE: we expect PreparationDate to be set by model validator

		return order;
	}

	private I_M_ShipmentSchedule createShipmentSchedule(final I_C_Order order)
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, order);
		shipmentSchedule.setC_Order(order);
		shipmentSchedule.setC_BPartner(order.getC_BPartner());
		shipmentSchedule.setC_BPartner_Location(order.getC_BPartner_Location());

		shipmentScheduleDeliveryDayBL.updateDeliveryDayInfo(shipmentSchedule);

		InterfaceWrapperHelper.save(shipmentSchedule);

		Assert.assertEquals("Invalid shipment schedule's DeliveryDate: " + shipmentSchedule,
				order.getDatePromised(),
				shipmentSchedule.getDeliveryDate());
		Assert.assertEquals("Invalid shipment schedule's PreparationDate: " + shipmentSchedule,
				order.getPreparationDate(),
				shipmentSchedule.getPreparationDate());

		return shipmentSchedule;
	}
}
