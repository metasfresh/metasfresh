package de.metas.workflow.rest_api.controller.v2.ws;

import de.metas.security.UserAuthToken;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.producers.WebSocketProducerFactory;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

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
		final TopicInfo topicInfo = extractTopicInfo(topicName);
		
		return new WorkflowLaunchersWebSocketProducer(
				workflowRestAPIService,
				topicInfo.getApplicationId(),
				topicInfo.getUserId());
	}

	@Value(staticConstructor = "of")
	private static class TopicInfo
	{
		@NonNull UserId userId;
		@Nullable MobileApplicationId applicationId;
	}

	private TopicInfo extractTopicInfo(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();
		if (topicNameString.startsWith(TOPIC_PREFIX))
		{
			final String[] tokenAndApplicationId = topicNameString.substring(TOPIC_PREFIX.length())
					.split("/");
			final String tokenString;
			final MobileApplicationId applicationId;
			if (tokenAndApplicationId.length == 1)
			{
				tokenString = tokenAndApplicationId[0];
				applicationId = null;
			}
			else if (tokenAndApplicationId.length == 2)
			{
				tokenString = tokenAndApplicationId[0];
				applicationId = MobileApplicationId.ofString(tokenAndApplicationId[1]);
			}
			else
			{
				throw new AdempiereException("Invalid topic: " + topicName);
			}

			final UserAuthToken token = userAuthTokenService.getByToken(tokenString);

			return TopicInfo.of(token.getUserId(), applicationId);
		}
		else
		{
			throw new AdempiereException("Invalid topic: " + topicName);
		}
	}
}
