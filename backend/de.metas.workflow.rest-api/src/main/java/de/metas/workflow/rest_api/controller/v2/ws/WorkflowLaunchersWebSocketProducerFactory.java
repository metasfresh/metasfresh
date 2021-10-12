package de.metas.workflow.rest_api.controller.v2.ws;

import de.metas.user.UserId;
import de.metas.util.NumberUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.producers.WebSocketProducerFactory;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

@Component
public class WorkflowLaunchersWebSocketProducerFactory implements WebSocketProducerFactory
{
	public static final String TOPIC_PREFIX = MetasfreshRestAPIConstants.WEBSOCKET_ENDPOINT_V2
			+ "/userWorkflows/launchers/";

	private final WorkflowRestAPIService workflowRestAPIService;

	private static UserId extractUserIdFromTopicName(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();
		if (topicNameString.startsWith(TOPIC_PREFIX))
		{
			final int userRepoId = NumberUtils.asInt(topicNameString.substring(TOPIC_PREFIX.length()), -1);
			final UserId userId = UserId.ofRepoIdOrNull(userRepoId);
			if (userId != null)
			{
				return userId;
			}
		}

		throw new AdempiereException("Invalid topic: " + topicName);
	}

	public WorkflowLaunchersWebSocketProducerFactory(@NonNull final WorkflowRestAPIService workflowRestAPIService)
	{
		this.workflowRestAPIService = workflowRestAPIService;
	}

	@Override
	public String getTopicNamePrefix() {return TOPIC_PREFIX;}

	@Override
	public WebSocketProducer createProducer(@NonNull final WebsocketTopicName topicName)
	{
		final UserId userId = extractUserIdFromTopicName(topicName);
		return new WorkflowLaunchersWebSocketProducer(workflowRestAPIService, userId);
	}
}
