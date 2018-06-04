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

import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.Assert;
import org.junit.Test;

import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.api.IDeliveryDayQueryParams;
import de.metas.tourplanning.api.PlainDeliveryDayQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;

/**
 * Tests for {@link DeliveryDayDAO#retrieveDeliveryDay(org.adempiere.model.IContextAware, IDeliveryDayQueryParams)}.
 * 
 * @author tsa
 *
 */
public class DeliveryDayDAO_retrieveDeliveryDay_Test extends TourPlanningTestBase
{

	@Override
	protected void afterInit()
	{
		tour = createTour("tour01");
		tourVersion = createTourVersion(tour, LocalDate.of(2014, 1, 1));
		bpartner = createBPartner("bp1");
		bpLocation = createBPLocation(bpartner);
	}

	@Test
	public void test_StandardUseCase()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5);
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5);
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5);

		testRetrieveDeliveryDay(null, "06.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd2, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");
	}

	@Test
	public void test_M_DeliveryDay_DeliveryDate_Plus_Buffer_ExceedingTheDay()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 19:00:00.000", 5);
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 19:00:00.000", 5);
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 19:00:00.000", 5);

		testRetrieveDeliveryDay(null, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd2, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");
	}

	@Test
	public void test_M_DeliveryDay_SkipProcessed()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5);
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5);
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5);

		testRetrieveDeliveryDay(null, "06.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd2, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");

		// Make Delivery Day 2 as processed
		dd2.setProcessed(true);
		InterfaceWrapperHelper.save(dd2);

		// Re-test again: those who were matched to dd2 now shall be matched to dd1
		testRetrieveDeliveryDay(null, "06.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");
	}

	private IDeliveryDayQueryParams createDeliveryDayQueryParams(final String deliveryDateStr)
	{
		final PlainDeliveryDayQueryParams params = new PlainDeliveryDayQueryParams();
		params.setC_BPartner_Location_ID(bpLocation.getC_BPartner_Location_ID());
		params.setToBeFetched(false);
		params.setDeliveryDate(toDateTimeTimestamp(deliveryDateStr));
		params.setProcessed(false);
		return params;
	}

	/**
	 * Convenient method for calling {@link DeliveryDayDAO#retrieveDeliveryDay(org.adempiere.model.IContextAware, IDeliveryDayQueryParams)}
	 * 
	 * @param deliveryDateStr
	 * @return
	 */
	private I_M_DeliveryDay retrieveDeliveryDay(final String deliveryDateStr)
	{
		final IDeliveryDayQueryParams params = createDeliveryDayQueryParams(deliveryDateStr);
		return deliveryDayDAO.retrieveDeliveryDay(contextProvider, params);
	}

	private void testRetrieveDeliveryDay(final I_M_DeliveryDay deliveryDayExpected, final String deliveryDateStr)
	{
		final I_M_DeliveryDay deliveryDayActual = retrieveDeliveryDay(deliveryDateStr);
		Assert.assertEquals("Invalid M_DeliveryDay for: " + deliveryDateStr, deliveryDayExpected, deliveryDayActual);
	}

}
