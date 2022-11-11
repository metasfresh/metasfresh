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
import de.metas.camel.externalsystems.common.http.EmptyBodyRequestWrapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class MetasfreshRestAPIRouteBuilder extends RouteBuilder
{
	@Value("${metasfresh.mass.json.request.directory.path}")
	private String massJsonRequestProcessingLocation;

	private final FeedbackConfigProvider feedbackConfigProvider;

	public MetasfreshRestAPIRouteBuilder(@NonNull final FeedbackConfigProvider feedbackConfigProvider)
	{
		this.feedbackConfigProvider = feedbackConfigProvider;
	}

	public static final String REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID = "Metasfresh-WriteRequestBodyToFile";
	public static final String ENABLE_RESOURCE_ROUTE_ID = "Metasfresh-enableRestAPI";
	public static final String DISABLE_RESOURCE_ROUTE_ID = "Metasfresh-disableRestAPI";

	public static final String ENABLE_RESOURCE_ROUTE_PROCESSOR_ID = "Metasfresh-enableRestAPIProcessor";
	public static final String DISABLE_RESOURCE_ROUTE_PROCESSOR_ID = "Metasfresh-disableRestAPIProcessor";
	public static final String ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "Metasfresh-ER-AttachAuthenticateReqProcessorId";
	public static final String DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "Metasfresh-DR-AttachAuthenticateReqProcessorId";
	public static final String REGISTER_FEEDBACK_CONFIG_PROCESSOR_ID = "Metasfresh-registerFeedbackConfigProcessorId";
	public static final String UNREGISTER_FEEDBACK_CONFIG_PROCESSOR_ID = "Metasfresh-unregisterFeedbackConfigProcessorId";
	public static final String REGISTER_ORG_CODE_PROCESSOR_ID = "Metasfresh-registerOrgCodeProcessorId";
	public static final String UNREGISTER_ORG_CODE_PROCESSOR_ID = "Metasfresh-unregisterOrgCodeProcessorId";
	public static final String WRITE_REQUEST_BODY_TO_FILE_PROCESSOR_ID = "Metasfresh-writeRequestBodyToFileProcessorId";

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
					.process(this::enableRestAPIProcessor).id(ENABLE_RESOURCE_ROUTE_PROCESSOR_ID)
					.process(this::registerFeedbackConfig).id(REGISTER_FEEDBACK_CONFIG_PROCESSOR_ID)
					.process(this::attachAuthenticateRequest).id(ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
					.to(direct(REST_API_AUTHENTICATE_TOKEN))
				.end();

		from(direct(DISABLE_RESOURCE_ROUTE_ID))
				.routeId(DISABLE_RESOURCE_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
					.process(this::attachAuthenticateRequest).id(DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
					.process(this::unregisterFeedbackConfig).id(UNREGISTER_FEEDBACK_CONFIG_PROCESSOR_ID)
					.to(direct(REST_API_EXPIRE_TOKEN))
					.process(this::disableRestAPIProcessor).id(DISABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.end();

		rest().path(RestServiceRoutes.METASFRESH.getPath())
				.post()
				.route()
				.routeId(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID)
				.autoStartup(false)
					.process(this::writeRequestBodyToFileProcessor).id(WRITE_REQUEST_BODY_TO_FILE_PROCESSOR_ID)
					.log("Route invoked!")
				.end();
		//@formatter:on
	}

	private void writeRequestBodyToFileProcessor(@NonNull final Exchange exchange)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		if (credentials.getExternalSystemValue() == null)
		{
			throw new RuntimeCamelException("Setup not completed properly! Missing extenralSystemConfigValue!");
		}

		final SecurityContextHolderAwareRequestWrapper camelHttpServletRequest = ((SecurityContextHolderAwareRequestWrapper)exchange.getIn()
				.getHeader("CamelHttpServletRequest"));
		final EmptyBodyRequestWrapper emptyBodyRequestWrapper = ((EmptyBodyRequestWrapper)camelHttpServletRequest.getRequest());

		final String computedFilename = FilenameUtil.computeFileName(credentials.getExternalSystemValue());

		CamelRouteUtil.writeRequestBodyToFile(emptyBodyRequestWrapper.getRealStream(),
											  massJsonRequestProcessingLocation + computedFilename);
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
		final Optional<FeedbackConfig> feedbackConfig = getFeedbackConfig(request);
		if (feedbackConfig.isPresent())
		{
			feedbackConfigProvider.registerFeedbackConfig(externalSystemConfigValue, feedbackConfig.get());
		}
		else
		{
			feedbackConfigProvider.unregisterFeedbackConfig(externalSystemConfigValue);
		}
	}

	@NonNull
	private Optional<FeedbackConfig> getFeedbackConfig(@NonNull final JsonExternalSystemRequest request)
	{
		final String responseAuthKey = request.getParameters().get(ExternalSystemConstants.PARAM_HTTP_RESPONSE_AUTH_KEY);
		if (Check.isBlank(responseAuthKey))
		{
			return Optional.empty();
		}

		final String responseUrl = request.getParameters().get(ExternalSystemConstants.PARAM_HTTP_RESPONSE_URL);
		if (Check.isBlank(responseUrl))
		{
			return Optional.empty();
		}

		return Optional.of(FeedbackConfig.builder()
								   .responseAuthKey(responseAuthKey)
								   .responseUrl(responseUrl)
								   .build());
	}

	private void attachAuthenticateRequest(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final JsonAuthenticateRequest jsonAuthenticateRequest = getJsonAuthenticateRequest(request);

		exchange.getIn().setBody(jsonAuthenticateRequest);
		final String externalSystemValue = jsonAuthenticateRequest.getExternalSystemValue();
		if (externalSystemValue == null)
		{
			throw new RuntimeCamelException("No ExternalSystemConfigValue specified");
		}
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
		getContext().getRouteController().suspendRoute(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID);
	}
}
