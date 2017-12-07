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
	public void isShipmentDeliveryDateBetterThanMovementDate_SameDate()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 18, 0, 0));

		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_InOut shipment = createShipment(today);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, today);

		// the candidate date is not better than the already existing date in shipment, because it's the same date
		assertThat(isTodayBestForShipmentDate).isFalse();
	}

	@Test
	public void isShipmentDeliveryDateBetterThanMovementDate_CandidateBeforeToday()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 50, 15, 16));

		final Timestamp yesterday = TimeUtil.getDay(2017, 11, 9);
		final Timestamp today = TimeUtil.getDay(2017, 11, 10);

		final I_M_InOut shipment = createShipment(today);

		boolean isYesterdayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, yesterday);

		// the candidate date is not better than the already existing date in shipment because it's in the past
		assertThat(isYesterdayBestForShipmentDate).isFalse();
	}

	@Test
	public void isShipmentDeliveryDateBetterThanMovementDate_CurrentYesterday_CandidateToday()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 2, 30, 20));

		final Timestamp yesterday = TimeUtil.getDay(2017, 11, 9);

		final Timestamp today = SystemTime.asDayTimestamp();

		final I_M_InOut shipment = createShipment(yesterday);

		boolean isNowBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, today);

		// the candidate date is better than the already existing date in shipment because the existing date is in the past
		assertThat(isNowBestForShipmentDate).isTrue();
	}

	@Test
	public void isShipmentDeliveryDateBetterThanMovementDate_CurrentToday_CandidateTomorrow()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 0, 51, 14));

		final Timestamp tomorrow = TimeUtil.getDay(2017, 11, 12);

		final Timestamp today = SystemTime.asDayTimestamp();

		final I_M_InOut shipment = createShipment(today);

		boolean isNowBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, tomorrow);

		// the candidate date is not better than the already existing date in shipment because the existing date is before the candidate and not in the past
		assertThat(isNowBestForShipmentDate).isFalse();
	}

	@Test
	public void isShipmentDeliveryDateBetterThanMovementDate_CurrentNextWeek_CandidateTomorrow()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 12, 30, 15));

		final Timestamp tomorrow = TimeUtil.getDay(2017, 11, 12);
		final Timestamp nextWeek = TimeUtil.getDay(2017, 11, 17);

		final I_M_InOut shipment = createShipment(nextWeek);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, tomorrow);

		// the candidate date is better than the already existing date in shipment because the existing date is after the candidate and they are both in the future
		assertThat(isTodayBestForShipmentDate).isTrue();
	}

	@Test
	public void isShipmentDeliveryDateBetterThanMovementDate_BothDatesInThePast()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 1, 1, 1));

		final Timestamp yesterday = TimeUtil.getDay(2017, 11, 9);
		final Timestamp lastWeek = TimeUtil.getDay(2017, 11, 3);

		final I_M_InOut shipment = createShipment(yesterday);

		boolean isTodayBestForShipmentDate = InOutProducerFromShipmentScheduleWithHU.isShipmentDeliveryDateBetterThanMovementDate(shipment, lastWeek);

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
	public void calculateShipmentDate_Today_IsShipmentDateTodayTrue()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 10, 15, 0));

		final Timestamp today = SystemTime.asDayTimestamp();

		final I_M_ShipmentSchedule schedule = createSchedule(today);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, true);

		assertThat(shipmentDate).isEqualTo(today);
	}

	@Test
	public void calculateShipmentDate_Today_IsShipmentDateTodayFalse()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 19, 17, 16));

		final Timestamp today = SystemTime.asDayTimestamp();

		final I_M_ShipmentSchedule schedule = createSchedule(today);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, false);

		assertThat(shipmentDate).isEqualTo(today);
	}

	@Test
	public void calculateShipmentDate_AnotherDate_IsShipmentDateTodayTrue()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 13, 13, 13));

		final Timestamp today = SystemTime.asDayTimestamp();

		final Timestamp anotherDate = TimeUtil.getDay(2017, 11, 17);

		final I_M_ShipmentSchedule schedule = createSchedule(anotherDate);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, true);

		assertThat(shipmentDate).isEqualTo(today);
	}

	@Test
	public void calculateShipmentDate_DateInFuture_IsShipmentDateTodayFalse()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 19, 4, 4));

		final Timestamp dateInFuture = TimeUtil.getDay(2017, 11, 17);

		final I_M_ShipmentSchedule schedule = createSchedule(dateInFuture);

		final Timestamp shipmentDate = InOutProducerFromShipmentScheduleWithHU.calculateShipmentDate(schedule, false);

		assertThat(shipmentDate).isEqualTo(dateInFuture);
	}

	@Test
	public void calculateShipmentDate_DateInPast_IsShipmentDateTodayFalse()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2017, 11, 10, 1, 2, 30));

		final Timestamp today = SystemTime.asDayTimestamp();
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
