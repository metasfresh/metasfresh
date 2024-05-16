/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.CamelRoutesGroup;
import de.metas.camel.externalsystems.common.ErrorBuilderHelper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.RestServiceAuthority;
import de.metas.camel.externalsystems.common.RestServiceRoutes;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.error.ErrorProcessor;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import de.metas.common.rest_api.v2.JsonError;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.spi.RouteController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.GRSSIGNUM_SYSTEM_NAME;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.JSON_PROPERTY_FLAG;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GRSRestAPIRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	//routes
	public static final String REST_API_ROUTE_ID = "GRSSignum_RestAPI";

	private static final String ENABLE_RESOURCE_ROUTE = "enableRestAPI";
	private static final String DISABLE_RESOURCE_ROUTE = "disableRestAPI";

	public static final String ENABLE_RESOURCE_ROUTE_ID = GRSSIGNUM_SYSTEM_NAME + "-" + ENABLE_RESOURCE_ROUTE;
	public static final String DISABLE_RESOURCE_ROUTE_ID = GRSSIGNUM_SYSTEM_NAME + "-" + DISABLE_RESOURCE_ROUTE;

	//processors
	public static final String ENABLE_RESOURCE_ROUTE_PROCESSOR_ID = "GRS-enableRestAPIProcessor";
	public static final String DISABLE_RESOURCE_ROUTE_PROCESSOR_ID = "GRS-disableRestAPIProcessor";

	public static final String ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "GRS-ER-AttachAuthenticateReqProcessorId";
	public static final String DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "GRS-DR-AttachAuthenticateReqProcessorId";

	public static final String ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "GRS-ER-PrepareStatusReqProcessorId";
	public static final String DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "GRS-DR-PrepareStatusReqProcessorId";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@NonNull
	private final ProducerTemplate producerTemplate;

	public GRSRestAPIRouteBuilder(
			final @NonNull ProducerTemplate producerTemplate)
	{
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void configure()
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(ENABLE_RESOURCE_ROUTE_ID))
				.routeId(ENABLE_RESOURCE_ROUTE_ID)
				.log("Route invoked!")
				.process(this::prepareRestAPIContext)
				.process(this::attachAuthenticateRequest).id(ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
				.to(direct(REST_API_AUTHENTICATE_TOKEN))
				.process(this::enableRestAPIProcessor).id(ENABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Active)).id(ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		from(direct(DISABLE_RESOURCE_ROUTE_ID))
				.routeId(DISABLE_RESOURCE_ROUTE_ID)
				.log("Route invoked!")
				.process(this::prepareRestAPIContext)
				.process(this::attachAuthenticateRequest).id(DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
				.to(direct(REST_API_EXPIRE_TOKEN))
				.process(this::disableRestAPIProcessor).id(DISABLE_RESOURCE_ROUTE_PROCESSOR_ID)
				.process(exchange -> this.prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Inactive)).id(DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		rest().path(RestServiceRoutes.GRS.getPath())
				.post()
				.route()
				.routeId(REST_API_ROUTE_ID)
				.group(CamelRoutesGroup.START_ON_DEMAND.getCode())
				.doTry()
					.process(this::restAPIProcessor)
					.process(this::prepareSuccessResponse)
				.doCatch(JsonProcessingException.class)
					.to(direct(ERROR_WRITE_TO_ADISSUE))
					.process(this::prepareErrorResponse)
					.marshal(setupJacksonDataFormatFor(getContext(), JsonError.class))
				.doCatch(HttpOperationFailedException.class)
					.to(direct(MF_ERROR_ROUTE_ID))
					.process(this::prepareHttpErrorResponse)
					.marshal(setupJacksonDataFormatFor(getContext(), JsonError.class))
				.doCatch(Exception.class)
					.to(direct(MF_ERROR_ROUTE_ID))
					.process(this::prepareErrorResponse)
					.marshal(setupJacksonDataFormatFor(getContext(), JsonError.class))
				.endDoTry()
				.end();
		//@formatter:on
	}

	private void enableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final RouteController routeController = getContext().getRouteController();

		routeController.resumeRoute(REST_API_ROUTE_ID);
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

		return JsonAuthenticateRequest.builder()
				.grantedAuthority(RestServiceAuthority.GRS.getValue())
				.authKey(authKey)
				.pInstance(request.getAdPInstanceId())
				.orgCode(request.getOrgCode())
				.externalSystemValue(request.getExternalSystemChildConfigValue())
				.build();
	}

	private void restAPIProcessor(@NonNull final Exchange exchange) throws JsonProcessingException
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		if (credentials == null)
		{
			throw new RuntimeCamelException("Missing credentials!");
		}

		exchange.getIn().setHeader(HEADER_PINSTANCE_ID, credentials.getPInstance().getValue());

		final String requestBody = exchange.getIn().getBody(String.class);

		final JsonNode rootJsonNode = objectMapper.readValue(requestBody, JsonNode.class);

		final Integer flag = objectMapper.treeToValue(rootJsonNode.get(JSON_PROPERTY_FLAG), Integer.class);

		final Endpoint endpoint = Endpoint.ofFlag(flag);

		exchange.getIn().setBody(requestBody);

		producerTemplate.send("direct:" + endpoint.getTargetRoute(), exchange);
	}

	private void disableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExpireTokenResponse response = exchange.getIn().getBody(JsonExpireTokenResponse.class);

		if (response != null && response.getNumberOfAuthenticatedTokens() == 0)
		{
			getContext().getRouteController().suspendRoute(REST_API_ROUTE_ID);
		}
	}

	private void prepareErrorResponse(@NonNull final Exchange exchange)
	{
		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

		if (exception == null)
		{
			exchange.getIn().setBody(null);
			exchange.getIn().setHeader(HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
			return;
		}

		final int httpCode = exception instanceof JsonProcessingException
				? HttpStatus.BAD_REQUEST.value()
				: HttpStatus.INTERNAL_SERVER_ERROR.value();

		final JsonError jsonError = JsonError.ofSingleItem(ErrorBuilderHelper.buildJsonErrorItem(exchange));

		exchange.getIn().setBody(jsonError);
		exchange.getIn().setHeader(HTTP_RESPONSE_CODE, httpCode);
	}

	private void prepareSuccessResponse(@NonNull final Exchange exchange)
	{
		exchange.getIn().setBody(null);
		exchange.getIn().setHeader(HTTP_RESPONSE_CODE, HttpStatus.OK.value()
		);
	}

	private void prepareRestAPIContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final GRSRestAPIContext context = GRSRestAPIContext.builder()
				.request(request)
				.build();

		exchange.setProperty(GRSSignumConstants.ROUTE_PROPERTY_GRS_REST_API_CONTEXT, context);
	}

	private void prepareExternalStatusCreateRequest(@NonNull final Exchange exchange, @NonNull final JsonExternalStatus externalStatus)
	{
		final GRSRestAPIContext context = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_GRS_REST_API_CONTEXT, GRSRestAPIContext.class);

		final JsonExternalSystemRequest request = context.getRequest();

		final JsonStatusRequest jsonStatusRequest = JsonStatusRequest.builder()
				.status(externalStatus)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ExternalStatusCreateCamelRequest camelRequest = ExternalStatusCreateCamelRequest.builder()
				.jsonStatusRequest(jsonStatusRequest)
				.externalSystemConfigType(getExternalSystemTypeCode())
				.externalSystemChildConfigValue(request.getExternalSystemChildConfigValue())
				.serviceValue(getServiceValue())
				.build();

		exchange.getIn().setBody(camelRequest, JsonExternalSystemRequest.class);
	}

	private void prepareHttpErrorResponse(@NonNull final Exchange exchange)
	{
		final JsonError jsonError = ErrorProcessor.processHttpErrorEncounteredResponse(exchange);
		exchange.getIn().setBody(jsonError);
	}

	@Override
	public String getServiceValue()
	{
		return "defaultRestAPIGRS";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return GRSSIGNUM_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return ENABLE_RESOURCE_ROUTE;
	}

	@Override
	public String getDisableCommand()
	{
		return DISABLE_RESOURCE_ROUTE;
	}
}