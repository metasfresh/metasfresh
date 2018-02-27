package de.metas.handlingunits.trace.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceEventQuery.RecursionMode;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceRepositoryTests;

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

public class RetrieveDbRecordsUtilTest
{

	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	private final Instant eventTime = Instant.now();


	private HUTraceRepository huTraceRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		huTraceRepository = new HUTraceRepository();
	}

	@Test
	public void configureQueryBuilder_orgId()
	{
		final HUTraceEventQuery query = HUTraceEventQuery.builder().orgId(30).build();

		final IQueryBuilder<I_M_HU_Trace> queryBuilder = RetrieveDbRecordsUtil.createQueryBuilderOrNull(query);
		assertThat(queryBuilder).isNotNull();
	}

	@Test
	public void configureQueryBuilder_huTraceEventId()
	{
		final HUTraceEventQuery query = HUTraceEventQuery.builder().huTraceEventId(OptionalInt.of(30)).build();

		final IQueryBuilder<I_M_HU_Trace> queryBuilder = RetrieveDbRecordsUtil.createQueryBuilderOrNull(query);
		assertThat(queryBuilder).isNotNull();
	}

	@Test
	public void queryToSelection_single_event_no_match()
	{
		final Instant eventTime = Instant.now();

		final HUTraceEvent event1_1 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(eventTime)
				.inOutId(10)
				.topLevelHuId(101)
				.vhuId(11)
				.build();
		huTraceRepository.addEvent(event1_1);

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.recursionMode(RecursionMode.BOTH)
				.inOutId(20).build();

		final int selectionId = RetrieveDbRecordsUtil.queryToSelection(query);
		assertThat(selectionId).isGreaterThan(0);

		final List<I_M_HU_Trace> result = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.setOnlySelection(selectionId)
				.create()
				.list();

		assertThat(result).isEmpty();
	}

	@Test
	public void queryToSelection_with_two_pairs_of_events_linked_via_VhuSource_Id()
	{
		createFourEvents();

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.recursionMode(RecursionMode.BOTH)
				.inOutId(10).build();
		final List<I_M_HU_Trace> result = invoke_queryToSelection(query);

		assertThat(result).hasSize(4);
	}

	private List<I_M_HU_Trace> invoke_queryToSelection(final HUTraceEventQuery query)
	{
		final int selectionId = RetrieveDbRecordsUtil.queryToSelection(query);
		assertThat(selectionId).isGreaterThan(0);

		final List<I_M_HU_Trace> result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Trace.class)
				.setOnlySelection(selectionId)
				.create()
				.list();
		return result;
	}

	@Test
	public void queryToList_with_two_pairs_of_events_linked_via_VhuSource_Id()
	{
		createFourEvents();

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.recursionMode(RecursionMode.BOTH)
				.inOutId(10).build();
		final List<HUTraceEvent> result = invoke_query(query);
		assertThat(result).hasSize(4);
	}

	private List<HUTraceEvent> invoke_query(final HUTraceEventQuery query)
	{
		final List<HUTraceEvent> result = RetrieveDbRecordsUtil.query(query);
		return result;
	}

	private void createFourEvents()
	{
		createTwoEvents_Linked_Via_VhuSourceID();

		final HUTraceEvent event2_1 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(eventTime)
				.inOutId(10)
				.topLevelHuId(202)
				.vhuId(21)
				.build();
		huTraceRepository.addEvent(event2_1);

		final HUTraceEvent event2_2 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(eventTime)
				.topLevelHuId(202)
				.vhuSourceId(21)
				.vhuId(22)
				.build();
		huTraceRepository.addEvent(event2_2);
	}

	private void createTwoEvents_Linked_Via_VhuSourceID()
	{
		final HUTraceEvent event1_1 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(eventTime)
				.inOutId(10)
				.topLevelHuId(101)
				.vhuId(11)
				.build();
		huTraceRepository.addEvent(event1_1);

		final HUTraceEvent event1_2 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(eventTime)
				.topLevelHuId(102)
				.vhuSourceId(11)
				.vhuId(12)
				.build();
		huTraceRepository.addEvent(event1_2);
	}

	@Test
	public void queryToSelection_with_pair_of_events_Linked_By_Same_VhuId()
	{
		createTwoEvents_Linked_By_Same_VhuId();

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.recursionMode(RecursionMode.BOTH)
				.inOutId(10).build();
		final List<I_M_HU_Trace> result = invoke_queryToSelection(query);

		assertThat(result).hasSize(2);
	}

	@Test
	public void queryToList_with_pair_of_events_Linked_By_Same_VhuId()
	{
		createTwoEvents_Linked_By_Same_VhuId();

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.recursionMode(RecursionMode.BOTH)
				.inOutId(10).build();
		final List<HUTraceEvent> result = invoke_query(query);

		assertThat(result).hasSize(2);
	}

	private void createTwoEvents_Linked_By_Same_VhuId()
	{
		final HUTraceEvent event1_1 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(eventTime)
				.inOutId(10)
				.topLevelHuId(101)
				.vhuId(11)
				.build();
		huTraceRepository.addEvent(event1_1);

		final HUTraceEvent event1_2 = HUTraceRepositoryTests.createCommonEventBuilder()
				.eventTime(TimeUtil.addHours(new Date(eventTime.toEpochMilli()), 1).toInstant())
				.topLevelHuId(102)
				.vhuId(11)
				.build();
		huTraceRepository.addEvent(event1_2);
	}
}
