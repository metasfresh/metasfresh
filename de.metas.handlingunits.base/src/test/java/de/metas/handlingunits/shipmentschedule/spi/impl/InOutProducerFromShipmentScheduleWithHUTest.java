package de.metas.handlingunits.shipmentschedule.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.FixedTimeSource;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class InOutProducerFromShipmentScheduleWithHUTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void isCandidateShipmentDateFitForShipment_SameDate_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_InOut shipment = createShipment(today);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isCandidateShipmentDateBestForShipment(shipment, today);

		// the candidate date is not better than the already existing date in shipment, because it's the same date
		assertThat(isTodayBestForShipmentDate).isFalse();
	}

	@Test
	public void isCandidateShipmentDateFitForShipment_CandidateBeforeToday_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp yesterday = TimeUtil.getDay(2017, 11, 9);
		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_InOut shipment = createShipment(today);

		boolean isYesterdayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isCandidateShipmentDateBestForShipment(shipment, yesterday);

		// the candidate date is not better than the already existing date in shipment because it's in the past
		assertThat(isYesterdayBestForShipmentDate).isFalse();
	}

	@Test
	public void isCandidateShipmentDateFitForShipment_CurrentYesterday_CandidateToday_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp yesterday = TimeUtil.getDay(2017, 11, 9);
		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_InOut shipment = createShipment(yesterday);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isCandidateShipmentDateBestForShipment(shipment, today);

		// the candidate date is better than the already existing date in shipment because the existing date is in the past
		assertThat(isTodayBestForShipmentDate).isTrue();
	}

	@Test
	public void isCandidateShipmentDateFitForShipment_CurrentToday_CandidateTomorrow_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp tomorrow = TimeUtil.getDay(2017, 11, 12);
		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_InOut shipment = createShipment(today);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isCandidateShipmentDateBestForShipment(shipment, tomorrow);

		// the candidate date is not better than the already existing date in shipment because the existing date is before the candidate and not in the past
		assertThat(isTodayBestForShipmentDate).isFalse();
	}

	@Test
	public void isCandidateShipmentDateFitForShipment_CurrentNextWeek_CandidateTomorrow_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp tomorrow = TimeUtil.getDay(2017, 11, 12);
		final Timestamp nextWeek = TimeUtil.getDay(2017, 11, 17);

		final I_M_InOut shipment = createShipment(nextWeek);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isCandidateShipmentDateBestForShipment(shipment, tomorrow);

		// the candidate date is better than the already existing date in shipment because the existing date is after the candidate and they are both in the future
		assertThat(isTodayBestForShipmentDate).isTrue();
	}

	@Test
	public void isCandidateShipmentDateFitForShipment_BothDatesInThePast_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp yesterday = TimeUtil.getDay(2017, 11, 9);
		final Timestamp lastWeek = TimeUtil.getDay(2017, 11, 3);

		final I_M_InOut shipment = createShipment(yesterday);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isCandidateShipmentDateBestForShipment(shipment, lastWeek);

		// the candidate date is not better than the already existing date in shipment because they are both in the past.
		assertThat(isTodayBestForShipmentDate).isFalse();
	}

	private I_M_InOut createShipment(final Timestamp date)
	{
		final I_M_InOut shipment = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		shipment.setMovementDate(date);
		InterfaceWrapperHelper.save(shipment);

		return shipment;
	}

	@Test
	public void calculateShipmentDate_Today_IsShipmentDateTodayTrue_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_ShipmentSchedule schedule = createSchedule(today);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, true);

		assertThat(shipmentDate).isEqualTo(today);
	}

	@Test
	public void calculateShipmentDate_Today_IsShipmentDateTodayFalse_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_ShipmentSchedule schedule = createSchedule(today);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, false);

		assertThat(shipmentDate).isEqualTo(today);
	}

	@Test
	public void calculateShipmentDate_AnotherDate_IsShipmentDateTodayTrue_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp today = TimeUtil.getDay(2017, 11, 10);
		final Timestamp anotherDate = TimeUtil.getDay(2017, 11, 17);

		final I_M_ShipmentSchedule schedule = createSchedule(anotherDate);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, true);

		assertThat(shipmentDate).isEqualTo(today);
	}

	@Test
	public void calculateShipmentDate_DateInFuture_IsShipmentDateTodayFalse_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp dateInFuture = TimeUtil.getDay(2017, 11, 17);

		final I_M_ShipmentSchedule schedule = createSchedule(dateInFuture);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, false);

		assertThat(shipmentDate).isEqualTo(dateInFuture);
	}

	@Test
	public void calculateShipmentDate_DateInPast_IsShipmentDateTodayFalse_Test()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 0, 0));

		final Timestamp today = TimeUtil.getDay(2017, 11, 10);
		final Timestamp dateInPast = TimeUtil.getDay(2017, 11, 3);

		final I_M_ShipmentSchedule schedule = createSchedule(dateInPast);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, false);

		assertThat(shipmentDate).isEqualTo(today);
	}

	private I_M_ShipmentSchedule createSchedule(final Timestamp date)
	{
		final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		schedule.setDeliveryDate(date);
		InterfaceWrapperHelper.save(schedule);
		return schedule;
	}

}
