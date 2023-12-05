package org.adempiere.ui.process;

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

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
				.name(p_TopicName)
				.type(Type.DISTRIBUTED)
				.build();

		final IEventBus eventBus = Services.get(IEventBusFactory.class)
				.getEventBus(topic);

		for (int i = 1; i <= p_Counter; i++)
		{
			final String detail = "Topic name: " + p_TopicName
					+ "\n Index: " + i + "/" + p_Counter
					+ "\n Recipient: " + p_AD_User_ID
					+ "\n Sender: " + getAD_User_ID()
					+ "\n Summary: " + p_Summary;
			eventBus.enqueueEvent(Event.builder()
					.setSummary(p_Summary)
					.setDetailPlain(detail)
					.addRecipient_User_ID(p_AD_User_ID)
					.build());
		}

		return MSG_OK;
	}

}
