package de.metas.tourplanning.api.impl;

import java.time.LocalDate;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Assert;
import org.junit.Test;

import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_Tour_Instance;

public class TourInstanceBLTest extends TourPlanningTestBase
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
		tourVersion = createTourVersion(tour, LocalDate.of(2014, 1, 1));
		bpartner = createBPartner("bp1");
		bpLocation = createBPLocation(bpartner);
	}

	/**
	 * Make sure M_DeliveryDay.Processed is updated when M_Tour_Instance.Processed is changed
	 * 
	 * @see de.metas.tourplanning.model.validator.M_Tour_Instance#updateDeliveryDays(I_M_Tour_Instance)
	 */
	@Test
	public void test_process_unprocess()
	{
		// Create tour instance with delivery days
		final I_M_Tour_Instance tourInstance = createTourInstance();
		final List<I_M_DeliveryDay> deliveryDays = Arrays.asList(
				createDeliveryDay("07.09.2014 15:00:00.000", 5, tourInstance),
				createDeliveryDay("08.09.2014 15:00:00.000", 5, tourInstance),
				createDeliveryDay("09.09.2014 15:00:00.000", 5, tourInstance));

		//
		// Assert not processed
		assertProcessed(false, tourInstance);
		assertProcessed(false, deliveryDays);

		//
		// Process tour instance
		tourInstanceBL.process(tourInstance);

		//
		// Assert processed
		assertProcessed(true, tourInstance);
		assertProcessed(false, deliveryDays); // we need to refresh to get the current status
		refresh(deliveryDays);
		assertProcessed(true, deliveryDays);

		//
		// UnProcess tour instance
		tourInstanceBL.unprocess(tourInstance);

		//
		// Assert not processed
		assertProcessed(false, tourInstance);
		assertProcessed(true, deliveryDays); // we need to refresh to get the current status
		refresh(deliveryDays);
		assertProcessed(false, deliveryDays);
	}

	private final void refresh(final List<I_M_DeliveryDay> deliveryDays)
	{
		for (final I_M_DeliveryDay dd : deliveryDays)
		{
			InterfaceWrapperHelper.refresh(dd);
		}
	}

	private I_M_DeliveryDay createDeliveryDay(final String deliveryDateTimeStr, final int bufferHours, final I_M_Tour_Instance tourInstance)
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
		deliveryDay.setM_Tour_Instance(tourInstance);
		deliveryDay.setProcessed(false);

		deliveryDayBL.setDeliveryDateTimeMax(deliveryDay);
		Assert.assertNotNull("DeliveryDateTimeMax shall be set", deliveryDay.getDeliveryDateTimeMax());

		InterfaceWrapperHelper.save(deliveryDay);

		return deliveryDay;
	}

	private I_M_Tour_Instance createTourInstance()
	{
		final I_M_Tour_Instance tourInstance = InterfaceWrapperHelper.newInstance(I_M_Tour_Instance.class, contextProvider);
		tourInstance.setM_Tour(tour);
		InterfaceWrapperHelper.save(tourInstance);

		return tourInstance;
	}

}
