package org.adempiere.ui.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import org.adempiere.util.Services;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Process used to test the distributed event bus by sending events to a given topic.
 * 
 * @author tsa
 *
 */
public class EventBus_SendTestEvent extends JavaProcess
{

	private String p_TopicName;
	private String p_Summary;
	private int p_AD_User_ID = -1;
	private int p_Counter = 1;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}

			final String name = para.getParameterName();
			if ("TopicName".equals(name))
			{
				p_TopicName = para.getParameterAsString();
			}
			else if ("Summary".equals(name))
			{
				p_Summary = para.getParameterAsString();
			}
			else if ("AD_User_ID".equals(name))
			{
				p_AD_User_ID = para.getParameterAsInt();
			}
			else if ("Counter".equals(name))
			{
				p_Counter = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final Topic topic = Topic.builder()
				.setName(p_TopicName)
				.setType(Type.REMOTE)
				.build();
		
		final IEventBus eventBus = Services.get(IEventBusFactory.class)
				.getEventBus(topic);

		for (int i = 1; i <= p_Counter; i++)
		{
			final String detail = "Topic name: " + p_TopicName
					+ "\n Index: " + i + "/" + p_Counter
					+ "\n Recipient: " + p_AD_User_ID
					+ "\n Sender: " + getAD_User_ID()
			//
			;
			eventBus.postEvent(Event.builder()
					.setSummary(p_Summary)
					.setDetailPlain(detail)
					.addRecipient_User_ID(p_AD_User_ID)
					.build());
		}

		return MSG_OK;
	}

}
