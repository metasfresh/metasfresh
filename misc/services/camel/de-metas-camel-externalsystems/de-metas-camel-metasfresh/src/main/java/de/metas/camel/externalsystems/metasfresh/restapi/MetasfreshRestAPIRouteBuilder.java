/*
 * #%L
 * de-metas-camel-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.metasfresh.restapi;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.RestServiceAuthority;
import de.metas.camel.externalsystems.common.RestServiceRoutes;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.http.WriteRequestBodyToFileProcessor;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfig;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfigProvider;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RouteController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_JSON_REQUEST_PROCESSING_LOCATION;
import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.MASS_JSON_REQUEST_PROCESSING_LOCATION_DEFAULT;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class MetasfreshRestAPIRouteBuilder extends RouteBuilder
{
	private final FeedbackConfigProvider feedbackConfigProvider;

	public MetasfreshRestAPIRouteBuilder(@NonNull final FeedbackConfigProvider feedbackConfigProvider)
	{
		this.feedbackConfigProvider = feedbackConfigProvider;
	}

	public static final String REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID = "Metasfresh-WriteRequestBodyToFile";
	public static final String WRITE_REQUEST_BODY_TO_FILE_PROCESSOR_ID = "Metasfresh-writeRequestBodyToFileProcessorId";

	public static final String ENABLE_RESOURCE_ROUTE_ID = "Metasfresh-enableRestAPI";
	public static final String DISABLE_RESOURCE_ROUTE_ID = "Metasfresh-disableRestAPI";

	public static final String ENABLE_RESOURCE_ROUTE_PROCESSOR_ID = "Metasfresh-enableRestAPIProcessor";
	public static final String DISABLE_RESOURCE_ROUTE_PROCESSOR_ID = "Metasfresh-disableRestAPIProcessor";

	public static final String ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "Metasfresh-ER-AttachAuthenticateReqProcessorId";
	public static final String DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "Metasfresh-DR-AttachAuthenticateReqProcessorId";

	public static final String REGISTER_FEEDBACK_CONFIG_PROCESSOR_ID = "Metasfresh-registerFeedbackConfigProcessorId";
	public static final String UNREGISTER_FEEDBACK_CONFIG_PROCESSOR_ID = "Metasfresh-unregisterFeedbackConfigProcessorId";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(ENABLE_RESOURCE_ROUTE_ID))
				.routeId(ENABLE_RESOURCE_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(this::registerFeedbackConfig).id(REGISTER_FEEDBACK_CONFIG_PROCESSOR_ID)
				.process(this::attachAuthenticateRequest).id(ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
				.to(direct(REST_API_AUTHENTICATE_TOKEN))
				.process(this::enableRestAPIProcessor).id(ENABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.end();

		from(direct(DISABLE_RESOURCE_ROUTE_ID))
				.routeId(DISABLE_RESOURCE_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(this::unregisterFeedbackConfig).id(UNREGISTER_FEEDBACK_CONFIG_PROCESSOR_ID)
				.process(this::attachAuthenticateRequest).id(DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
				.to(direct(REST_API_EXPIRE_TOKEN))
				.process(this::disableRestAPIProcessor).id(DISABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.end();
		
		rest().path(RestServiceRoutes.METASFRESH.getPath())
				.post()
				.route()
				.routeId(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID)
				.autoStartup(false)
					.process(new WriteRequestBodyToFileProcessor(getWriteFileLocation(), this::computeFilename)).id(WRITE_REQUEST_BODY_TO_FILE_PROCESSOR_ID)
					.log("Route invoked!")
				.end();
		//@formatter:on
	}

	private void enableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final RouteController routeController = getContext().getRouteController();

		routeController.resumeRoute(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID);
	}

	private void unregisterFeedbackConfig(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String externalSystemConfigValue = request.getParameters().get(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE);
		feedbackConfigProvider.unregisterFeedbackConfig(externalSystemConfigValue);
	}

	private void registerFeedbackConfig(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String externalSystemConfigValue = request.getParameters().get(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE);

		getFeedbackConfig(request).ifPresentOrElse(
				config -> feedbackConfigProvider.registerFeedbackConfig(externalSystemConfigValue, config),
				() -> feedbackConfigProvider.unregisterFeedbackConfig(externalSystemConfigValue));
	}

	@NonNull
	private Optional<FeedbackConfig> getFeedbackConfig(@NonNull final JsonExternalSystemRequest request)
	{
		final String feedbackAuthKey = request.getParameters().get(ExternalSystemConstants.PARAM_FEEDBACK_RESOURCE_AUTH_TOKEN);
		if (Check.isBlank(feedbackAuthKey))
		{
			return Optional.empty();
		}

		final String feedbackURL = request.getParameters().get(ExternalSystemConstants.PARAM_FEEDBACK_RESOURCE_URL);
		if (Check.isBlank(feedbackURL))
		{
			return Optional.empty();
		}

		return Optional.of(FeedbackConfig.builder()
								   .authToken(feedbackAuthKey)
								   .feedbackResourceURL(feedbackURL)
								   .build());
	}

	private void attachAuthenticateRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final JsonAuthenticateRequest jsonAuthenticateRequest = getJsonAuthenticateRequest(request);

		exchange.getIn().setBody(jsonAuthenticateRequest);
	}

	@NonNull
	private JsonAuthenticateRequest getJsonAuthenticateRequest(@NonNull final JsonExternalSystemRequest request)
	{
		if (request.getAdPInstanceId() == null)
		{
			throw new RuntimeCamelException("Missing pInstance identifier!");
		}

		final String authKey = request.getParameters().get(ExternalSystemConstants.PARAM_CAMEL_HTTP_RESOURCE_AUTH_KEY);
		if (authKey == null)
		{
			throw new RuntimeCamelException("Missing authKey from request!");
		}

		final String externalSystemValue = request.getParameters().get(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE);
		if (externalSystemValue == null)
		{
			throw new RuntimeCamelException("Missing externalSystemValue from request!");
		}

		final String auditTrailEndpoint = request.getWriteAuditEndpoint();

		return JsonAuthenticateRequest.builder()
				.grantedAuthority(RestServiceAuthority.METASFRESH.getValue())
				.authKey(authKey)
				.pInstance(request.getAdPInstanceId())
				.externalSystemValue(externalSystemValue)
				.auditTrailEndpoint(auditTrailEndpoint)
				.orgCode(request.getOrgCode())
				.build();
	}

	private void disableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExpireTokenResponse response = exchange.getIn().getBody(JsonExpireTokenResponse.class);

		if (response != null && response.getNumberOfAuthenticatedTokens() == 0)
		{
			getContext().getRouteController().suspendRoute(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID);
		}
	}

	@NonNull
	private String getWriteFileLocation()
	{
		CamelRouteUtil.setupProperties(getContext());

		return CamelRouteUtil.resolveProperty(getContext(),
											  MASS_JSON_REQUEST_PROCESSING_LOCATION,
											  MASS_JSON_REQUEST_PROCESSING_LOCATION_DEFAULT);
	}

	@NonNull
	private String computeFilename()
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		if (credentials == null || credentials.getExternalSystemValue() == null)
		{
			throw new RuntimeCamelException("Incomplete setup! ExternalSystemValue is missing!");
		}

		return FilenameUtil.computeFileName(credentials.getExternalSystemValue(), credentials.getOrgCode());
	}
}
