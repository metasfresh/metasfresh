package de.metas.workflow.rest_api.controller.v2.ws;

import de.metas.security.UserAuthToken;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenService;
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
	private final UserAuthTokenService userAuthTokenService;

	public WorkflowLaunchersWebSocketProducerFactory(
			@NonNull final WorkflowRestAPIService workflowRestAPIService,
			@NonNull final UserAuthTokenService userAuthTokenService)
	{
		this.workflowRestAPIService = workflowRestAPIService;
		this.userAuthTokenService = userAuthTokenService;
	}

	@Override
	public String getTopicNamePrefix() {return TOPIC_PREFIX;}

	@Override
	public WebSocketProducer createProducer(@NonNull final WebsocketTopicName topicName)
	{
		final UserAuthToken token = extractAuthTokenFromTopicName(topicName);
		return new WorkflowLaunchersWebSocketProducer(workflowRestAPIService, token.getUserId());
	}

	private UserAuthToken extractAuthTokenFromTopicName(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();
		if (topicNameString.startsWith(TOPIC_PREFIX))
		{
			final String tokenString = topicNameString.substring(TOPIC_PREFIX.length());
			return userAuthTokenService.getByToken(tokenString);
		}

		throw new AdempiereException("Invalid topic: " + topicName);
	}

}
