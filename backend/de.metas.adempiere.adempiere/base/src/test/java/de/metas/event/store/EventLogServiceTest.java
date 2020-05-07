package de.metas.event.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.Type;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogUserService;
import de.metas.event.log.impl.EventLogEntry;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import mockit.Expectations;
import mockit.Mocked;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class EventLogServiceTest
{
	private static final Type MOCKED_EVENT_BUS_TYPE = Type.REMOTE;

	private static final String MOCKED_EVENT_BUS_NAME = "mockedEventBusName";

	private EventLogService eventLogService;

	@Mocked
	private IEventBus eventBus;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		eventLogService = new EventLogService(new EventLogUserService());

		// @formatter:off
		new Expectations()
		{{
			eventBus.getName(); result = MOCKED_EVENT_BUS_NAME;
			eventBus.getType(); result = MOCKED_EVENT_BUS_TYPE;
		}};	// @formatter:on
	}

	@Test
	public void storeEvent()
	{
		final Event event = createSimpleEvent();
		eventLogService.storeEvent(event, eventBus);

		final POJOLookupMap pojoLookupMap = POJOLookupMap.get();
		final List<I_AD_EventLog> eventLogRecords = pojoLookupMap.getRecords(I_AD_EventLog.class);
		assertThat(eventLogRecords).hasSize(1);

		final I_AD_EventLog eventLogRecord = eventLogRecords.get(0);
		assertThat(eventLogRecord.getEvent_UUID()).isEqualTo(event.getUuid().toString());
		assertThat(eventLogRecord.getEventTopicName()).isEqualTo(MOCKED_EVENT_BUS_NAME);
		assertThat(eventLogRecord.getEventTypeName()).isEqualTo(MOCKED_EVENT_BUS_TYPE.toString());
		assertThat(eventLogRecord.getEventData())
				.isEqualToNormalizingWhitespace("{\n" +
						"  \"uuid\" : \"5fc6dbeb-aee4-4ac7-89ca-d31ff50d6421\",\n" +
						"  \"when\" : 1514876894.521000000,\n" +
						"  \"senderId\" : \"testSenderId\"\n" +
						"}");

		final List<I_AD_EventLog_Entry> eventLogEntryRecords = pojoLookupMap.getRecords(I_AD_EventLog_Entry.class);
		assertThat(eventLogEntryRecords).isEmpty();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void loadEvent()
	{
		final Event event = createSimpleEvent();
		eventLogService.storeEvent(event, eventBus);
		final EventLogEntry eventLog1 = EventLogEntry.builder()
				.uuid(event.getUuid())
				.clientId(20)
				.orgId(30)
				.processed(true)
				.message("logs as processed, but doesn't provide handler class info")
				.build();
		eventLogService.storeEventLogEntry(eventLog1);

		final EventLogEntry eventLog2 = EventLogEntry.builder()
				.uuid(event.getUuid())
				.clientId(20)
				.orgId(30)
				.processed(false)
				.eventHandlerClass(String.class)
				.message("logs as not (yet) processed and provides handler class info")
				.build();
		eventLogService.storeEventLogEntry(eventLog2);

		final EventLogEntry eventLog3 = EventLogEntry.builder()
				.uuid(event.getUuid())
				.clientId(20)
				.orgId(30)
				.processed(true)
				.eventHandlerClass(String.class)
				.message("logs as processed and provides handler class info")
				.build();
		eventLogService.storeEventLogEntry(eventLog3);

		final EventLogEntry eventLog4 = EventLogEntry.builder()
				.uuid(event.getUuid())
				.clientId(20)
				.orgId(30)
				.processed(true)
				.eventHandlerClass(Integer.class)
				.message("logs as processed and provides handler class info")
				.build();
		eventLogService.storeEventLogEntry(eventLog4);

		final EventLogEntry eventLog5 = EventLogEntry.builder()
				.uuid(event.getUuid())
				.clientId(20)
				.orgId(30)
				.processed(false)
				.eventHandlerClass(Boolean.class)
				.message("logs as not processed and provides handler class info")
				.build();
		eventLogService.storeEventLogEntry(eventLog5);

		final POJOLookupMap pojoLookupMap = POJOLookupMap.get();
		final List<I_AD_EventLog> eventLogRecords = pojoLookupMap.getRecords(I_AD_EventLog.class);
		assertThat(eventLogRecords).hasSize(1);

		final Event loadedEvent = eventLogService.loadEventForReposting(eventLogRecords.get(0));
		final List<Object> processedbyHandlerInfo = loadedEvent.getProperty(EventLogUserService.PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES);
		assertThat(processedbyHandlerInfo).isNotNull();
		assertThat(processedbyHandlerInfo).isInstanceOf(List.class);
		assertThat((List)processedbyHandlerInfo).containsOnly(Integer.class.getName(), String.class.getName());
	}

	private Event createSimpleEvent()
	{
		final Event event = Event.builder()
				.setUUID(UUID.fromString("5fc6dbeb-aee4-4ac7-89ca-d31ff50d6421"))
				.setWhen(Instant.ofEpochSecond(1514876894, 521000000))
				.setSenderId("testSenderId")
				.build();
		return event;
	}
}
