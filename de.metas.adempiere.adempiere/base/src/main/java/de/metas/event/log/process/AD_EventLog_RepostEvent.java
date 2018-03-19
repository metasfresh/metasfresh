package de.metas.event.log.process;

import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.log.EventLogService;
import de.metas.event.model.I_AD_EventLog;
import de.metas.process.JavaProcess;

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

		final Topic topic = Topic.builder()
				.name(eventLogRecord.getEventTopicName())
				.type(Type.valueOf(eventLogRecord.getEventTypeName()))
				.build();
		final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBus(topic);
		if (topic.getType().equals(Type.REMOTE) && !eventBus.getType().equals(Type.REMOTE))
		{
			addLog("The given event log record has a REMOTE topic, but we only got a LOCAL event bus!");
		}

		final EventLogService eventLogService = Adempiere.getBean(EventLogService.class);
		final Event event = eventLogService.loadEventForReposting(eventLogRecord);

		eventBus.postEvent(event);

		return MSG_OK;
	}

}
