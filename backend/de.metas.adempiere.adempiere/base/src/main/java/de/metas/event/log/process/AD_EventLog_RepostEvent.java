package de.metas.event.log.process;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.log.EventLogId;
import de.metas.event.log.EventLogService;
import de.metas.event.model.I_AD_EventLog;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;

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

public class AD_EventLog_RepostEvent extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final I_AD_EventLog eventLogRecord = getRecord(I_AD_EventLog.class);

		Check.assumeNotNull(eventLogRecord.getEventTopicName(), "EventTopicName is null");
		Check.assumeNotNull(eventLogRecord.getEventTypeName(), "EventTypeName is null");

		final Topic topic = Topic.builder()
				.name(eventLogRecord.getEventTopicName())
				.type(Type.valueOf(eventLogRecord.getEventTypeName()))
				.build();

		final boolean typeMismatchBetweenTopicAndBus = !Type.valueOf(eventLogRecord.getEventTypeName()).equals(topic.getType());
		if (typeMismatchBetweenTopicAndBus)
		{
			addLog("The given event log record has a different topic than the event bus!");
		}

		final EventLogId eventLogId = EventLogId.ofRepoId(eventLogRecord.getAD_EventLog_ID());

		final EventLogService eventLogService = SpringContextHolder.instance.getBean(EventLogService.class);
		final Event event = eventLogService.loadEventForReposting(eventLogId);

		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(topic);
		eventBus.enqueueEvent(event);

		return MSG_OK;
	}

}
