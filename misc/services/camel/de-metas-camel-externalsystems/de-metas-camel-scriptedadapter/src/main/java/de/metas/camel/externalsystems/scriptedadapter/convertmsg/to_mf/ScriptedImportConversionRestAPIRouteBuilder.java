/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ErrorBuilderHelper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.error.ErrorProcessor;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.FIELD_ERROR_MESSAGE;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PREFIX_IMPORT_AUTHORITY;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_ENDPOINT_NAME;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.PROPERTY_SCRIPTED_SCRIPTED_IMPORTED_CONVERSION_CONTEXT;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
@RequiredArgsConstructor
public class ScriptedImportConversionRestAPIRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	//routes
	public static final String REST_API_ROUTE_ID = "ScriptedImportConversion_import-catchall";

	private static final String ENABLE_RESOURCE_ROUTE = "enableRestAPI";
	private static final String DISABLE_RESOURCE_ROUTE = "disableRestAPI";

	public static final String ENABLE_RESOURCE_ROUTE_ID = SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME + "-" + ENABLE_RESOURCE_ROUTE;
	public static final String DISABLE_RESOURCE_ROUTE_ID = SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME + "-" + DISABLE_RESOURCE_ROUTE;

	//processors
	public static final String ENABLE_RESOURCE_ROUTE_PROCESSOR_ID = "ScriptedImportConversion-enableRestAPIProcessor";
	public static final String DISABLE_RESOURCE_ROUTE_PROCESSOR_ID = "ScriptedImportConversion-disableRestAPIProcessor";

	public static final String ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-ER-AttachAuthenticateReqProcessorId";
	public static final String DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-DR-AttachAuthenticateReqProcessorId";

	public static final String ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-ER-PrepareStatusReqProcessorId";
	public static final String DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-DR-PrepareStatusReqProcessorId";

	@NonNull private final ProducerTemplate producerTemplate;

	private JavaScriptRepo javaScriptRepo;
	private JavaScriptExecutorService javaScriptExecutorService;

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		javaScriptRepo = new JavaScriptRepo(getContext().resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		javaScriptExecutorService = new JavaScriptExecutorService();

		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		// enable http-endpoint
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

		rest("/interchange/import")
				.post("*")
				.to("direct:" + REST_API_ROUTE_ID);

		from("direct:" + REST_API_ROUTE_ID)
				.routeId(REST_API_ROUTE_ID)
				.log("Route invoked!")
				.doTry()
					.process(this::restAPIProcessor)
					.toD("direct:${exchangeProperty.endpointName}")
					.process(this::prepareResponse)
					.marshal(setupJacksonDataFormatFor(getContext(), Object.class))
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
				.endDoTry();

		// disable http-endpoint
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
		//@formatter:on
	}

	private void prepareResponse(@NonNull final Exchange exchange)
	{
		@SuppressWarnings("unchecked") final List<Object> list = exchange.getIn().getBody(List.class);
		if (list == null)
		{
			throw new RuntimeCamelException("Nothing has been processed!");
		}

		if (list.size() == 1)
		{
			final String singleResponse = String.valueOf(list.get(0));
			if (singleResponse.contains(FIELD_ERROR_MESSAGE))
			{
				throw new RuntimeCamelException(singleResponse);
			}
			exchange.getIn().setHeader(HTTP_RESPONSE_CODE, HttpStatus.OK.value());
		}
		else
		{
			final boolean hasErrors = list.stream()
					.map(String::valueOf)
					.anyMatch(responseStr -> responseStr.contains(FIELD_ERROR_MESSAGE));
			if (hasErrors)
			{
				exchange.getIn().setHeader(HTTP_RESPONSE_CODE, HttpStatus.MULTI_STATUS.value());
			}
			else
			{
				exchange.getIn().setHeader(HTTP_RESPONSE_CODE, HttpStatus.OK.value());
			}
		}
	}

	private void prepareHttpErrorResponse(@NonNull final Exchange exchange)
	{
		final JsonError jsonError = ErrorProcessor.processHttpErrorEncounteredResponse(exchange);
		exchange.getIn().setBody(jsonError);
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

	private void restAPIProcessor(@NonNull final Exchange exchange)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();
		if (credentials == null)
		{
			throw new RuntimeCamelException("Missing credentials!");
		}

		exchange.getIn().setHeader(HEADER_PINSTANCE_ID, credentials.getPInstance().getValue());

		final String path = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
		final String endpointName = path.substring(path.lastIndexOf('/') + 1);
		exchange.setProperty(PROPERTY_ENDPOINT_NAME, endpointName);
	}

	private void enableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final ScriptedImportConversionRestAPIContext context = ProcessorHelper.getPropertyOrThrowError(exchange, PROPERTY_SCRIPTED_SCRIPTED_IMPORTED_CONVERSION_CONTEXT, ScriptedImportConversionRestAPIContext.class);

		final JsonExternalSystemRequest request = context.getRequest();
		final String endpointName = request.getParameters().get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);
		final String scriptIdentifier = request.getParameters().get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER);
		final CamelContext camelContext = getCamelContext();

		camelContext.addRoutes(new ScriptedImportConversionDynamicRouteBuilder(endpointName, scriptIdentifier, javaScriptRepo, javaScriptExecutorService, producerTemplate));

		camelContext.getRouteController().startRoute(endpointName);

		log.info("Dynamic REST API route '{}' started successfully.", endpointName);
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

		final String authKey = request.getParameters().get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN);
		if (Check.isEmpty(authKey))
		{
			throw new RuntimeCamelException("Missing authKey from request!");
		}

		final String endpointName = request.getParameters().get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);
		if (Check.isEmpty(endpointName))
		{
			throw new RuntimeCamelException("Missing endpointName from request!");
		}

		return JsonAuthenticateRequest.builder()
				.grantedAuthority(PREFIX_IMPORT_AUTHORITY + endpointName)
				.authKey(authKey)
				.pInstance(request.getAdPInstanceId())
				.orgCode(request.getOrgCode())
				.externalSystemValue(request.getExternalSystemChildConfigValue())
				.build();
	}

	private void disableRestAPIProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final ScriptedImportConversionRestAPIContext context = ProcessorHelper.getPropertyOrThrowError(exchange, PROPERTY_SCRIPTED_SCRIPTED_IMPORTED_CONVERSION_CONTEXT, ScriptedImportConversionRestAPIContext.class);
		final String endpointName = context.getRequest().getParameters().get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);

		final JsonExpireTokenResponse response = exchange.getIn().getBody(JsonExpireTokenResponse.class);

		if (response != null && response.getNumberOfAuthenticatedTokens() == 0)
		{
			getContext().getRouteController().suspendRoute(endpointName);
		}
	}

	private void prepareRestAPIContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final ScriptedImportConversionRestAPIContext context = ScriptedImportConversionRestAPIContext.builder()
				.request(request)
				.build();

		exchange.setProperty(PROPERTY_SCRIPTED_SCRIPTED_IMPORTED_CONVERSION_CONTEXT, context);
	}

	private void prepareExternalStatusCreateRequest(@NonNull final Exchange exchange, @NonNull final JsonExternalStatus externalStatus)
	{
		final ScriptedImportConversionRestAPIContext context = ProcessorHelper.getPropertyOrThrowError(exchange, PROPERTY_SCRIPTED_SCRIPTED_IMPORTED_CONVERSION_CONTEXT, ScriptedImportConversionRestAPIContext.class);
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

	@Override
	public String getServiceValue()
	{
		return "defaultRestAPIScriptedImportConversion";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME;
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