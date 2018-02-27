package de.metas.handlingunits.tourplanning.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Random;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.IHUDeliveryQuantities;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.tourplanning.model.I_M_DeliveryDay;
import de.metas.handlingunits.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.handlingunits.tourplanning.model.I_M_Tour_Instance;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.impl.ShipmentScheduleDeliveryDayHandler;

public class HUShipmentScheduleDeliveryDayHandlerTest
{
	/* Class under test */
	private HUShipmentScheduleDeliveryDayHandler huShipmentScheduleDeliveryDayHandler;

	private Random random;
	private IContextAware contextProvider;

	private I_M_ShipmentSchedule shipmentSchedule;
	private I_M_DeliveryDay_Alloc deliveryDayAlloc;
	private I_M_DeliveryDay deliveryDay;
	private I_M_Tour_Instance tourInstance;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		random = new Random(System.currentTimeMillis());

		huShipmentScheduleDeliveryDayHandler = HUShipmentScheduleDeliveryDayHandler.instance;
		contextProvider = PlainContextAware.newWithTrxName(Env.getCtx(), ITrx.TRXNAME_None);

		shipmentSchedule = createShipmentSchedule();
		tourInstance = createTourInstance();
		deliveryDay = createDeliveryDay(tourInstance);
		deliveryDayAlloc = createDeliveryDayAlloc(deliveryDay, shipmentSchedule);
	}

	@Test
	public void test_updateDeliveryDayAllocFromModel()
	{
		setRandomQtys(shipmentSchedule);
		InterfaceWrapperHelper.save(shipmentSchedule);

		huShipmentScheduleDeliveryDayHandler.updateDeliveryDayAllocFromModel(deliveryDayAlloc, asDeliveryDayAllocable(shipmentSchedule));
		assertEquals(shipmentSchedule, deliveryDayAlloc);
	}

	@Test
	public void test_updateDeliveryDayWhenAllocationChanged()
	{
		setRandomQtys(deliveryDayAlloc);
		InterfaceWrapperHelper.save(deliveryDayAlloc);

		final I_M_DeliveryDay_Alloc deliveryDayAllocOld = null;
		huShipmentScheduleDeliveryDayHandler.updateDeliveryDayWhenAllocationChanged(deliveryDay, deliveryDayAlloc, deliveryDayAllocOld);

		assertEquals(deliveryDayAlloc, deliveryDay);
	}

	@Test
	public void test_updateTourInstanceWhenDeliveryDayChanged()
	{
		setRandomQtys(deliveryDay);
		InterfaceWrapperHelper.save(deliveryDay);

		final I_M_DeliveryDay deliveryDayOld = null;
		huShipmentScheduleDeliveryDayHandler.updateTourInstanceWhenDeliveryDayChanged(tourInstance, deliveryDay, deliveryDayOld);

		assertEquals(deliveryDay, tourInstance);
	}

	public static void assertEquals(
			final IHUDeliveryQuantities expected,
			final IHUDeliveryQuantities actual)
	{
		Assert.assertThat("Invalid QtyOrdered_LU",
				actual.getQtyOrdered_LU(),
				Matchers.comparesEqualTo(expected.getQtyOrdered_LU())
				);
		Assert.assertThat("Invalid QtyOrdered_TU",
				actual.getQtyOrdered_TU(),
				Matchers.comparesEqualTo(expected.getQtyOrdered_TU())
				);

		Assert.assertThat("Invalid QtyDelivered_LU",
				actual.getQtyDelivered_LU(),
				Matchers.comparesEqualTo(expected.getQtyDelivered_LU())
				);
		Assert.assertThat("Invalid QtyDelivered_TU",
				actual.getQtyDelivered_TU(),
				Matchers.comparesEqualTo(expected.getQtyDelivered_TU())
				);
	}

	public static void assertEquals(
			final I_M_ShipmentSchedule expected,
			final IHUDeliveryQuantities actual)
	{
		Assert.assertThat("Invalid QtyOrdered_LU",
				actual.getQtyOrdered_LU(),
				Matchers.comparesEqualTo(expected.getQtyOrdered_LU())
				);
		Assert.assertThat("Invalid QtyOrdered_TU",
				actual.getQtyOrdered_TU(),
				Matchers.comparesEqualTo(expected.getQtyOrdered_TU())
				);
	}

	private void setRandomQtys(final I_M_ShipmentSchedule record)
	{
		record.setQtyOrdered_LU(BigDecimal.valueOf(random.nextInt(100000)));
		record.setQtyOrdered_TU(BigDecimal.valueOf(random.nextInt(100000)));
	}

	private void setRandomQtys(final IHUDeliveryQuantities record)
	{
		record.setQtyOrdered_LU(BigDecimal.valueOf(random.nextInt(100000)));
		record.setQtyOrdered_TU(BigDecimal.valueOf(random.nextInt(100000)));

		record.setQtyDelivered_LU(BigDecimal.valueOf(random.nextInt(100000)));
		record.setQtyDelivered_TU(BigDecimal.valueOf(random.nextInt(100000)));
	}

	private IDeliveryDayAllocable asDeliveryDayAllocable(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ShipmentScheduleDeliveryDayHandler.INSTANCE.asDeliveryDayAllocable(shipmentSchedule);
	}

	private I_M_DeliveryDay createDeliveryDay(final I_M_Tour_Instance tourInstance)
	{
		final I_M_DeliveryDay deliveryDay = InterfaceWrapperHelper.newInstance(I_M_DeliveryDay.class, contextProvider);
		// deliveryDay.setC_BPartner(bpartner);
		// deliveryDay.setC_BPartner_Location(bpLocation);
		// deliveryDay.setDeliveryDate(toDateTimeTimestamp(deliveryDateTimeStr));
		// deliveryDay.setBufferHours(bufferHours);
		deliveryDay.setIsManual(false);
		deliveryDay.setIsToBeFetched(false);
		// deliveryDay.setM_Tour(tour);
		// deliveryDay.setM_TourVersion(tourVersion);
		deliveryDay.setM_Tour_Instance(tourInstance);
		deliveryDay.setProcessed(false);

		// deliveryDayBL.setDeliveryDateTimeMax(deliveryDay);
		// Assert.assertNotNull("DeliveryDateTimeMax shall be set", deliveryDay.getDeliveryDateTimeMax());

		InterfaceWrapperHelper.save(deliveryDay);

		return deliveryDay;
	}

	private I_M_ShipmentSchedule createShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, contextProvider);
		InterfaceWrapperHelper.save(shipmentSchedule);
		return shipmentSchedule;
	}

	private I_M_DeliveryDay_Alloc createDeliveryDayAlloc(final I_M_DeliveryDay deliveryDay, final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_DeliveryDay_Alloc alloc = InterfaceWrapperHelper.newInstance(I_M_DeliveryDay_Alloc.class, contextProvider);
		alloc.setM_DeliveryDay(deliveryDay);
		alloc.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(shipmentSchedule));
		alloc.setRecord_ID(shipmentSchedule.getM_ShipmentSchedule_ID());
		InterfaceWrapperHelper.save(alloc);

		return alloc;
	}

	private I_M_Tour_Instance createTourInstance()
	{
		final I_M_Tour_Instance tourInstance = InterfaceWrapperHelper.newInstance(I_M_Tour_Instance.class, contextProvider);
		InterfaceWrapperHelper.save(tourInstance);
		return tourInstance;
	}

}
