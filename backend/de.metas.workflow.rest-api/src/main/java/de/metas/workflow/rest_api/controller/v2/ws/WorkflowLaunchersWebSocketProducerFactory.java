package de.metas.workflow.rest_api.controller.v2.ws;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.security.UserAuthToken;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.producers.WebSocketProducerFactory;
import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetId;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URLDecoder;

@Component
@RequiredArgsConstructor
public class WorkflowLaunchersWebSocketProducerFactory implements WebSocketProducerFactory
{
	private final WorkflowRestAPIService workflowRestAPIService;
	private final UserAuthTokenService userAuthTokenService;

	private static final String TOPIC_PREFIX = MetasfreshRestAPIConstants.WEBSOCKET_ENDPOINT_V2 + "/userWorkflows/launchers/";
	private static final String TOPIC_PARAM_userToken = "userToken";
	private static final String TOPIC_PARAM_applicationId = "applicationId";
	private static final String TOPIC_PARAM_facetIds = "facetIds";
	private static final String TOPIC_PARAM_qrCode = "qrCode";

	@Override
	public String getTopicNamePrefix() {return TOPIC_PREFIX;}

	@Override
	public WebSocketProducer createProducer(@NonNull final WebsocketTopicName topicName)
	{
		final TopicInfo topicInfo = parseTopicInfo(topicName);

		return new WorkflowLaunchersWebSocketProducer(
				workflowRestAPIService,
				topicInfo.getApplicationId(),
				topicInfo.getUserId(),
				topicInfo.getFilterByQRCode(),
				topicInfo.getFacetIds());
	}

	@Value(staticConstructor = "of")
	private static class TopicInfo
	{
		@NonNull UserId userId;
		@NonNull MobileApplicationId applicationId;
		@Nullable GlobalQRCode filterByQRCode;
		@Nullable ImmutableSet<WorkflowLaunchersFacetId> facetIds;
	}

	private TopicInfo parseTopicInfo(@NonNull final WebsocketTopicName topicName)
	{
		if (!topicName.startsWith(TOPIC_PREFIX))
		{
			throw new AdempiereException("Invalid topic: " + topicName);
		}

		try
		{
			final MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUriString(topicName.getAsString()).build().getQueryParams();
			final UserAuthToken token = StringUtils.trimBlankToOptional(queryParams.getFirst(TOPIC_PARAM_userToken))
					.map(userAuthTokenService::getByToken)
					.orElseThrow(() -> new AdempiereException("Parameter " + TOPIC_PARAM_userToken + " is mandatory"));
			final MobileApplicationId applicationId = StringUtils.trimBlankToOptional(queryParams.getFirst(TOPIC_PARAM_applicationId))
					.map(MobileApplicationId::ofString)
					.orElseThrow(() -> new AdempiereException("Parameter " + TOPIC_PARAM_applicationId + " is mandatory"));
			final GlobalQRCode filterByQRCode = StringUtils.trimBlankToOptional(queryParams.getFirst(TOPIC_PARAM_qrCode))
					.map(GlobalQRCode::ofString)
					.orElse(null);
			final ImmutableSet<WorkflowLaunchersFacetId> facetIds = extractFacetIdsFromQueryParams(queryParams);

			return TopicInfo.of(token.getUserId(), applicationId, filterByQRCode, facetIds);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid topic: " + topicName, ex);
		}
	}

	private static ImmutableSet<WorkflowLaunchersFacetId> extractFacetIdsFromQueryParams(final MultiValueMap<String, String> queryParams) throws IOException
	{
		String facetIdsStr = StringUtils.trimBlankToNull(queryParams.getFirst(TOPIC_PARAM_facetIds));
		if (facetIdsStr == null)
		{
			return ImmutableSet.of();
		}

		facetIdsStr = URLDecoder.decode(facetIdsStr, "UTF-8");

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(facetIdsStr)
				.stream()
				.map(WorkflowLaunchersFacetId::fromJson)
				.collect(ImmutableSet.toImmutableSet());
	}
}
