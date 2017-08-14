package de.metas.handlingunits.trace;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.event.SimpleObjectSerializer;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.HUTraceSpecification.RecursionMode;

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

public class HUTraceRepositoryTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final HUTraceEventBuilder commonEventBuilder = HUTraceEvent.builder()
			.vhuStatus(X_M_HU.HUSTATUS_Active)
			.qty(BigDecimal.valueOf(100))
			.productId(23)
			.type(HUTraceType.TRANSFORM_LOAD);

	private HUTraceRepository huTraceRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		huTraceRepository = new HUTraceRepository();

	}

	@Test
	public void testEmptySpec()
	{
		createAndAddEvents(); // just use this method to store some events. we don't want to see and of them in the result..

		final HUTraceSpecification query = HUTraceSpecification.builder()
				.recursionMode(RecursionMode.NONE)
				.build();

		assertThat(huTraceRepository.query(query).isEmpty(), is(true));
		assertThat(huTraceRepository.query(query.withRecursionMode(RecursionMode.FORWARD)).isEmpty(), is(true));
		assertThat(huTraceRepository.query(query.withRecursionMode(RecursionMode.BACKWARD)).isEmpty(), is(true));
	}

	/**
	 * Adds two equal events (same huId and eventTime) and verifies than only one is stored in the DB.
	 */
	@Test
	public void testAddEvent()
	{
		final Instant eventTime = Instant.now();

		huTraceRepository.addEvent(HUTraceEvent.builder()
				.eventTime(eventTime)
				.topLevelHuId(2)
				.productId(23)
				.qty(BigDecimal.TEN)
				.vhuStatus(X_M_HU.HUSTATUS_Active)
				.vhuId(12)
				.type(HUTraceType.TRANSFORM_LOAD)
				.build());

		final HUTraceSpecification query = HUTraceSpecification.builder()
				.vhuId(12)
				.recursionMode(RecursionMode.NONE)
				.build();

		final List<HUTraceEvent> result = huTraceRepository.query(query);
		assertThat(result.size(), is(1));
		assertThat(result.get(0).getVhuId(), is(12));

		// add an equal event, again
		huTraceRepository.addEvent(HUTraceEvent.builder()
				.eventTime(eventTime)
				.topLevelHuId(2)
				.productId(23)
				.qty(BigDecimal.TEN)
				.vhuStatus(X_M_HU.HUSTATUS_Active)
				.vhuId(12)
				.type(HUTraceType.TRANSFORM_LOAD)
				.build());

		final List<HUTraceEvent> result2 = huTraceRepository.query(query);
		assertThat(result2.size(), is(1)); // still just one..
	}

	/**
	 * verifies that the source HU id is correctly stored and retrieved
	 */
	@Test
	public void testAddWithSourceHuId()
	{
		final Instant eventTime = Instant.now();

		huTraceRepository.addEvent(commonEventBuilder
				.eventTime(eventTime)
				.topLevelHuId(2)
				.vhuId(12)
				.vhuSourceId(13)
				.build());

		final HUTraceSpecification query = HUTraceSpecification.builder()
				.vhuId(12)
				.recursionMode(RecursionMode.NONE)
				.build();

		final List<HUTraceEvent> result = huTraceRepository.query(query);
		assertThat(result.size(), is(1));

		assertThat(result.get(0).getTopLevelHuId(), is(2));
		assertThat(result.get(0).getVhuId(), is(12));
		assertThat(result.get(0).getVhuSourceId(), is(13));
	}

	/**
	 * verifies that the source HU id is correctly stored and retrieved
	 */
	@Test
	public void testRetrieveWithSourceHuId()
	{
		final Instant eventTime = Instant.now();

		final HUTraceEvent event1 = commonEventBuilder
				.eventTime(eventTime)
				.topLevelHuId(2)
				.vhuId(12)
				.build();
		huTraceRepository.addEvent(event1);

		final HUTraceEvent event2 = commonEventBuilder
				.eventTime(eventTime)
				.topLevelHuId(3)
				.vhuId(13)
				.vhuSourceId(14)
				.build();
		huTraceRepository.addEvent(event2);

		final HUTraceSpecification query = HUTraceSpecification.builder()
				.vhuSourceId(14)
				.recursionMode(RecursionMode.NONE)
				.build();

		final List<HUTraceEvent> result = huTraceRepository.query(query);
		assertThat(result.size(), is(1));

		assertThat(result.get(0).getVhuId(), is(13));
		assertThat(result.get(0).getVhuSourceId(), is(14));
	}

	@Test
	public void testRetrieveNoneRecursiveTrace()
	{
		final List<HUTraceEvent> events = createAndAddEvents();
		// events.forEach(e -> System.out.println(e));

		// query with the huId of the first event from the "middle" group.
		// the first group has no event with getHuSourceId > 0, that's why we go with the middle one
		final List<HUTraceEvent> result = huTraceRepository.query(HUTraceSpecification.builder()
				.vhuId(events.get(5).getVhuId())
				.recursionMode(RecursionMode.NONE)
				.build());

		assertThat(result.size(), is(6));
		for (int i = 0; i < result.size(); i++)
		{
			assertThat("i=" + i, result.get(i).getEventTime(), is(events.get(i + 5).getEventTime()));
			assertThat("i=" + i, result.get(i).getVhuId(), is(events.get(i + 5).getVhuId()));
			assertThat("i=" + i, result.get(i).getVhuSourceId(), is(events.get(i + 5).getVhuSourceId()));
			assertThat("i=" + i, result.get(i).getInOutId(), is(events.get(i + 5).getInOutId()));
			assertThat("i=" + i, result.get(i).getMovementId(), is(events.get(i + 5).getMovementId()));
			assertThat("i=" + i, result.get(i).getCostCollectorId(), is(events.get(i + 5).getCostCollectorId()));
			assertThat("i=" + i, result.get(i).getShipmentScheduleId(), is(events.get(i + 5).getShipmentScheduleId()));
		}
	}

	@Test
	public void testRetrieveBackwardRecursiveOneHop()
	{
		final List<HUTraceEvent> events = createAndAddEvents();
		// events.forEach(e -> System.out.println(e));

		// query with the huId of the first event from the "middle" group.
		// that means that we expect a record for each event of that group plus a record for each event of the "preceding" group
		final List<HUTraceEvent> result = huTraceRepository.query(HUTraceSpecification.builder()
				.vhuId(events.get(5).getVhuId())
				.recursionMode(RecursionMode.BACKWARD)
				.build());

		result.sort(Comparator.comparing(HUTraceEvent::getEventTime));
		events.sort(Comparator.comparing(HUTraceEvent::getEventTime));

		events.forEach(e -> System.out.println(e));
		result.forEach(r -> System.out.println(r));

		assertThat(result.size(), is(11));
		// expect matching result records for the first 11 events
		for (int i = 0; i < result.size(); i++)
		{
			assertThat("i=" + i, result.get(i).getEventTime(), is(events.get(i).getEventTime()));
			assertThat("i=" + i, result.get(i).getVhuId(), is(events.get(i).getVhuId()));
			assertThat("i=" + i, result.get(i).getVhuSourceId(), is(events.get(i).getVhuSourceId()));
			assertThat("i=" + i, result.get(i).getInOutId(), is(events.get(i).getInOutId()));
			assertThat("i=" + i, result.get(i).getMovementId(), is(events.get(i).getMovementId()));
			assertThat("i=" + i, result.get(i).getCostCollectorId(), is(events.get(i).getCostCollectorId()));
			assertThat("i=" + i, result.get(i).getShipmentScheduleId(), is(events.get(i).getShipmentScheduleId()));
		}
	}

	@Test
	public void testRetrieveBackwardRecursiveTwoHops()
	{
		final List<HUTraceEvent> events = createAndAddEvents();
		// events.forEach(e -> System.out.println(e));

		// query with the huId of the first event from the "last" group.
		// that means that we expect a record for each event event we added (because there is one directly preceding group and one indirectly preceding group).
		final List<HUTraceEvent> result = huTraceRepository.query(HUTraceSpecification.builder()
				.vhuId(events.get(11).getVhuId())
				.recursionMode(RecursionMode.BACKWARD)
				.build());
		result.sort(Comparator.comparing(HUTraceEvent::getEventTime));
		events.sort(Comparator.comparing(HUTraceEvent::getEventTime));

		// events.forEach(e -> System.out.println(e));
		// result.forEach(r -> System.out.println(r));

		assertThat(result.size(), is(17));
		for (int i = 0; i < result.size(); i++)
		{
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getEventTime(), is(events.get(i).getEventTime()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getVhuId(), is(events.get(i).getVhuId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getVhuSourceId(), is(events.get(i).getVhuSourceId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getInOutId(), is(events.get(i).getInOutId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getMovementId(), is(events.get(i).getMovementId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getCostCollectorId(), is(events.get(i).getCostCollectorId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getShipmentScheduleId(), is(events.get(i).getShipmentScheduleId()));
		}
	}

	@Test
	public void testRetrieveForwardRecursiveOneHop()
	{
		final List<HUTraceEvent> events = createAndAddEvents();
		// events.forEach(e -> System.out.println(e));

		// query with the huId of the first event from the "middle" group.
		// that means that we expect a record for each event of that group plus a record for each event of the "following" group
		final List<HUTraceEvent> result = huTraceRepository.query(HUTraceSpecification.builder()
				.vhuId(events.get(5).getVhuId())
				.recursionMode(RecursionMode.FORWARD)
				.build());

		assertThat(result.size(), is(12));
		for (int i = 0; i < result.size(); i++)
		{
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getEventTime(), is(events.get(i + 5).getEventTime()));
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getVhuId(), is(events.get(i + 5).getVhuId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getVhuSourceId(), is(events.get(i + 5).getVhuSourceId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getInOutId(), is(events.get(i + 5).getInOutId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getMovementId(), is(events.get(i + 5).getMovementId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getCostCollectorId(), is(events.get(i + 5).getCostCollectorId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i + 5) + ";\nresult=" + result.get(i), result.get(i).getShipmentScheduleId(), is(events.get(i + 5).getShipmentScheduleId()));
		}
	}

	@Test
	public void testRetrieveForwardRecursiveTwoHops()
	{
		final List<HUTraceEvent> events = createAndAddEvents();
		// events.forEach(e -> System.out.println(e));

		// query with the huId of the first event from the "first" group.
		// that means that we expect a record for each event event we added (because there is one directly preceding group and one indirectly preceding group).
		final List<HUTraceEvent> result = huTraceRepository.query(HUTraceSpecification.builder()
				.vhuId(events.get(0).getVhuId())
				.recursionMode(RecursionMode.FORWARD)
				.build());
		result.sort(Comparator.comparing(HUTraceEvent::getEventTime));
		events.sort(Comparator.comparing(HUTraceEvent::getEventTime));

		// events.forEach(e -> System.out.println(e));
		// result.forEach(r -> System.out.println(r));

		assertThat(result.size(), is(17));
		for (int i = 0; i < result.size(); i++)
		{
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getEventTime(), is(events.get(i).getEventTime()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getVhuId(), is(events.get(i).getVhuId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getVhuSourceId(), is(events.get(i).getVhuSourceId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getInOutId(), is(events.get(i).getInOutId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getMovementId(), is(events.get(i).getMovementId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getCostCollectorId(), is(events.get(i).getCostCollectorId()));
			assertThat("i=" + i + ";\nevent=" + events.get(i) + ";\nresult=" + result.get(i), result.get(i).getShipmentScheduleId(), is(events.get(i).getShipmentScheduleId()));
		}
	}

	@Test
	public void printJson()
	{
		final List<HUTraceEvent> events = createAndAddEvents();

		// this invocation is borrowed from testRetrieveForwardRecursiveTwoHops.
		// it should return records for all events we added
		final List<HUTraceEvent> result = huTraceRepository.query(HUTraceSpecification.builder()
				.vhuId(events.get(0).getVhuId())
				.recursionMode(RecursionMode.FORWARD)
				.build());
		final String resultJsonString = SimpleObjectSerializer.get().serialize(result);
		System.out.println(resultJsonString);
	}

	private List<HUTraceEvent> createAndAddEvents()
	{
		final List<HUTraceEvent> result = new ArrayList<>();

		final Instant eventTime = Instant.now();

		final HUTraceEventBuilder eventBefore = commonEventBuilder
				.eventTime(eventTime)
				.topLevelHuId(4)
				.vhuId(14);

		result.add(eventBefore.build());
		result.add(eventBefore.eventTime(eventTime.plusSeconds(1)).inOutId(24).build());
		result.add(eventBefore.eventTime(eventTime.plusSeconds(2)).movementId(34).build());
		result.add(eventBefore.eventTime(eventTime.plusSeconds(3)).costCollectorId(44).build());
		result.add(eventBefore.eventTime(eventTime.plusSeconds(4)).shipmentScheduleId(54).build());
		// eventBefore is the first of three and therefore has no sourceHuId

		final HUTraceEventBuilder eventMiddle = commonEventBuilder
				.eventTime(eventTime.plusSeconds(5))
				.topLevelHuId(5)
				.vhuId(15);

		result.add(eventMiddle.build());
		result.add(eventMiddle.eventTime(eventTime.plusSeconds(6)).inOutId(25).build());
		result.add(eventMiddle.eventTime(eventTime.plusSeconds(7)).movementId(35).build());
		result.add(eventMiddle.eventTime(eventTime.plusSeconds(8)).costCollectorId(45).build());
		result.add(eventMiddle.eventTime(eventTime.plusSeconds(9)).shipmentScheduleId(55).build());
		result.add(eventMiddle.eventTime(eventTime.plusSeconds(10)).vhuSourceId(14).build()); // this event is the middle one of three and has the M_HU_ID of 'eventBefore' as its source

		final HUTraceEventBuilder eventAfter = commonEventBuilder
				.eventTime(eventTime.plusSeconds(11))
				.topLevelHuId(6)
				.vhuId(16);

		result.add(eventAfter.build());
		result.add(eventAfter.eventTime(eventTime.plusSeconds(12)).inOutId(26).build());
		result.add(eventAfter.eventTime(eventTime.plusSeconds(13)).movementId(36).build());
		result.add(eventAfter.eventTime(eventTime.plusSeconds(14)).costCollectorId(46).build());
		result.add(eventAfter.eventTime(eventTime.plusSeconds(15)).shipmentScheduleId(56).build());
		result.add(eventAfter.eventTime(eventTime.plusSeconds(16)).vhuSourceId(15).build()); // this event is the last of three and has the M_HU_ID of 'eventMiddle' as its source

		result.forEach(e -> huTraceRepository.addEvent(e));

		return result;
	}
}
