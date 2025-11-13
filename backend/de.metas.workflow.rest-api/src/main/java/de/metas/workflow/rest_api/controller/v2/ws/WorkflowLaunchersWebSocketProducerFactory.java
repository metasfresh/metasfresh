package de.metas.workflow.rest_api.controller.v2.ws;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.document.DocumentNoFilter;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.rest_workflows.facets.WorkflowLaunchersFacetId;
import de.metas.scannable_code.ScannedCode;
import de.metas.security.UserAuthToken;
import de.metas.user.UserId;
import de.metas.util.StringUtils;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.security.UserAuthTokenService;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.producers.WebSocketProducer;
import de.metas.websocket.producers.WebSocketProducerFactory;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.service.WorkflowRestAPIService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

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
	private static final String TOPIC_PARAM_documentNo = "documentNo";
	private static final String TOPIC_PARAM_filterByQtyAvailableAtPickFromLocator = "filterByQtyAvailableAtPickFromLocator";

	@Override
	public String getTopicNamePrefix() {return TOPIC_PREFIX;}

	@Override
	public WebSocketProducer createProducer(@NonNull final WebsocketTopicName topicName)
	{
		return WorkflowLaunchersWebSocketProducer.builder()
				.workflowRestAPIService(workflowRestAPIService)
				.query(parseWorkflowLaunchersQuery(topicName))
				.build();
	}

	private WorkflowLaunchersQuery parseWorkflowLaunchersQuery(@NonNull final WebsocketTopicName topicName)
	{
		if (!topicName.startsWith(TOPIC_PREFIX))
		{
			throw new AdempiereException("Invalid topic: " + topicName);
		}

		try
		{
			final MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUriString(topicName.getAsString()).build().getQueryParams();

			return WorkflowLaunchersQuery.builder()
					.applicationId(extractApplicationId(queryParams))
					.userId(extractUserId(queryParams))
					.filterByQRCode(ScannedCode.ofNullableString(queryParams.getFirst(TOPIC_PARAM_qrCode)))
					.filterByDocumentNo(DocumentNoFilter.ofNullableString(queryParams.getFirst(TOPIC_PARAM_documentNo)))
					.filterByQtyAvailableAtPickFromLocator(StringUtils.toOptionalBoolean(queryParams.getFirst(TOPIC_PARAM_filterByQtyAvailableAtPickFromLocator)).orElseFalse())
					.facetIds(extractFacetIdsFromQueryParams(queryParams))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid topic: " + topicName, ex);
		}
	}

	@NonNull
	private UserId extractUserId(final MultiValueMap<String, String> queryParams)
	{
		return StringUtils.trimBlankToOptional(queryParams.getFirst(TOPIC_PARAM_userToken))
				.map(userAuthTokenService::getByToken)
				.map(UserAuthToken::getUserId)
				.orElseThrow(() -> new AdempiereException("Parameter " + TOPIC_PARAM_userToken + " is mandatory"));
	}

	@NonNull
	private static MobileApplicationId extractApplicationId(final MultiValueMap<String, String> queryParams)
	{
		return StringUtils.trimBlankToOptional(queryParams.getFirst(TOPIC_PARAM_applicationId))
				.map(MobileApplicationId::ofString)
				.orElseThrow(() -> new AdempiereException("Parameter " + TOPIC_PARAM_applicationId + " is mandatory"));
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
