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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthAccessToken;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthAccessTokenRequest;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthIdentity;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthTokenManager;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.endpoint.JsonEndpointAuthType;
import de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.attachment.JsonAttachment;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.common.rest_api.v2.attachment.JsonTableRecordReference;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ERROR_CONTEXT;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ATTACHMENT_FILE_NAME;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_DOCUMENT_NO;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Invokes a given script on a given request-body and forwards the result to an an API-request.
 */
@RequiredArgsConstructor
@Component
public class ScriptedAdapterConvertMsgFromMFRouteBuilder extends RouteBuilder
{
	public static final String HEADER_AUTH_TYPE = "AuthType";
	public static final String HEADER_TRANSPORT_TYPE = "TransportType";

	public static final String ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID = "ScriptedExportConversion-ConvertMsgFromMF";

	/** Internal route that processes one element of a fan-out array. Called per iteration from the main split. */
	@VisibleForTesting
	static final String ScriptedExportConversion_FanOutIteration_ROUTE_ID = "ScriptedExportConversion-FanOutIteration";

	public static final String PROPERTY_SCRIPTING_REPO_BASE_DIR = "metasfresh.scriptedadapter.repo.baseDir";

	@VisibleForTesting
	static final String ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID = "ScriptedExportConversionOutboundHttpEPId";

	/** Exchange property holding the parsed {@link ArrayNode} when fan-out mode is active; null/missing otherwise. */
	@VisibleForTesting
	static final String EXCHANGE_PROPERTY_FAN_OUT_ARRAY = "fanOutArray";

	/** Exchange property holding the total element count (matches {@link #EXCHANGE_PROPERTY_FAN_OUT_ARRAY} size). */
	@VisibleForTesting
	static final String EXCHANGE_PROPERTY_FAN_OUT_TOTAL = "fanOutTotal";

	/** Exchange property holding the 1-based index of the current iteration inside the fan-out split. */
	@VisibleForTesting
	static final String EXCHANGE_PROPERTY_FAN_OUT_INDEX = "fanOutIndex";

	/** Exchange property holding the {@link FanOutResult} aggregator across all iterations of one fan-out. */
	@VisibleForTesting
	static final String EXCHANGE_PROPERTY_FAN_OUT_RESULT = "fanOutResult";

	private JavaScriptRepo javaScriptRepo;

	@NonNull
	private final OAuthTokenManager oauthTokenManager;

	@NonNull
	private final SftpDeliveryProcessor sftpDeliveryProcessor;

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final JavaScriptExecutorService javaScriptExecutorService = new JavaScriptExecutorService();

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		javaScriptRepo = new JavaScriptRepo(getContext().resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));

		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID))
			.routeId(ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID)
			.log("Route invoked!")
			.process(this::buildAndSetContext)
			.process(this::executeJavaScript)
			.process(this::splitOnArrayIfRequested)
			.choice()
				// Fan-out branch: dispatch one downstream call per array element
				.when(simple("${exchangeProperty." + EXCHANGE_PROPERTY_FAN_OUT_ARRAY + "} != null"))
					.process(this::initFanOutResult)
					.split(simple("${exchangeProperty." + EXCHANGE_PROPERTY_FAN_OUT_ARRAY + "}"))
						// Continue iterating even when one element fails; aggregate via FanOutResult.
						// Per-iteration logic is in the FanOutIteration sub-route so we can wrap it in doTry/doCatch.
						.doTry()
							.to(direct(ScriptedExportConversion_FanOutIteration_ROUTE_ID))
							.process(this::recordFanOutSuccess)
						.doCatch(Exception.class)
							.process(this::recordFanOutFailure)
						.end()
					.end() // close split
					.process(this::finalizeFanOut)
					.endChoice()
				// Single-request branch (original behaviour)
				.otherwise()
					.process(this::extractTransportTypeToHeader)
					.choice()
						// SFTP transport branch
						.when(header(HEADER_TRANSPORT_TYPE).isEqualTo("SFTP"))
							.log("Using SFTP transport")
							.process(sftpDeliveryProcessor)
							.process(this::prepareSftpAttachmentRequest)
							.log(LoggingLevel.DEBUG, "Calling metasfresh-api to save SFTP attachment log: ${body}")
							.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
						// HTTP transport branch (default)
						.otherwise()
							.process(this::extractAuthTypeToHeader)
							.choice()
								.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.Token))
									.process(this::prepareHttpRequestForTokenAuth)
								.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.OAuth))
									.process(this::prepareHttpRequestForOAuth)
								.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.SAS))
									.process(this::prepareHttpRequestForSasAuth)
								.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.Basic))
									.process(this::prepareHttpRequestForBasicAuth)
								.otherwise()
									.throwException(new RuntimeCamelException("Unsupported authentication type"))
							.end()

							// Make the rest-call and handle the case of a stale OAuth token
							.toD("${header." + Exchange.HTTP_URI + "}").id(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.choice()
								.when(simple("${header.CamelHttpResponseCode} == 401 && ${header." + HEADER_AUTH_TYPE + "} == 'OAuth'"))
									.log(LoggingLevel.WARN, "Received 401, refreshing OAuth token and retrying once...")
									.process(this::forceRefreshOAuthToken)
									.toD("${header." + Exchange.HTTP_URI + "}").id(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID + "_RETRY")
							.end()

							.process(this::prepareJsonAttachmentRequest)
							.log(LoggingLevel.DEBUG, "Calling metasfresh-api to save attachment: ${body}")
							.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
					.end()
			.end();

		// Sub-route called once per array element when fan-out mode is active. Lives in its own route
		// so the main split can wrap it in doTry/doCatch and aggregate per-element success/failure
		// (Camel's doTry/doCatch only nests cleanly across simple linear steps, not across nested choices).
		from(direct(ScriptedExportConversion_FanOutIteration_ROUTE_ID))
			.routeId(ScriptedExportConversion_FanOutIteration_ROUTE_ID)
			.errorHandler(noErrorHandler()) // rethrow so the outer doTry/doCatch can record the failure
			.process(this::prepareFanOutIteration)
			.log("ScriptedAdapter fan-out: dispatching element ${exchangeProperty." + EXCHANGE_PROPERTY_FAN_OUT_INDEX
					+ "}/${exchangeProperty." + EXCHANGE_PROPERTY_FAN_OUT_TOTAL + "}")
			.process(this::extractTransportTypeToHeader)
			.choice()
				.when(header(HEADER_TRANSPORT_TYPE).isEqualTo("SFTP"))
					.process(sftpDeliveryProcessor)
					.process(this::prepareSftpAttachmentRequest)
					.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
				.otherwise()
					.process(this::extractAuthTypeToHeader)
					.choice()
						.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.Token))
							.process(this::prepareHttpRequestForTokenAuth)
						.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.OAuth))
							.process(this::prepareHttpRequestForOAuth)
						.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.SAS))
							.process(this::prepareHttpRequestForSasAuth)
						.when(header(HEADER_AUTH_TYPE).isEqualTo(JsonEndpointAuthType.Basic))
							.process(this::prepareHttpRequestForBasicAuth)
						.otherwise()
							.throwException(new RuntimeCamelException("Unsupported authentication type"))
					.end()
					.toD("${header." + Exchange.HTTP_URI + "}").id(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID + "_FANOUT")
					.choice()
						.when(simple("${header.CamelHttpResponseCode} == 401 && ${header." + HEADER_AUTH_TYPE + "} == 'OAuth'"))
							.log(LoggingLevel.WARN, "Received 401, refreshing OAuth token and retrying once...")
							.process(this::forceRefreshOAuthToken)
							.toD("${header." + Exchange.HTTP_URI + "}").id(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID + "_FANOUT_RETRY")
					.end()
					.process(this::prepareJsonAttachmentRequest)
					.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
			.end();
		//@formatter:on
	}

	private void buildAndSetContext(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> parameters = request.getParameters();

		final JsonExternalSystemEndpoint endpointParameters = deserializeEndpointParameters(parameters);

		// Extract and set error context header for error handling
		final String errorContext = parameters.get(PARAM_ERROR_CONTEXT);
		if (errorContext != null)
		{
			exchange.getIn().setHeader(HEADER_ERROR_CONTEXT, errorContext);
		}

		final MsgFromMfContext msgFromMfContext = MsgFromMfContext.builder()
				.orgCode(request.getOrgCode())
				.scriptingRequestBody(parameters.get(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT))
				.scriptIdentifier(parameters.get(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER))
				.endpointParameters(endpointParameters)
				.outboundRecordTableName(parameters.get(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME))
				.outboundRecordId(parameters.get(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID))
				.outboundDocumentNo(parameters.get(PARAM_SCRIPTEDADAPTER_OUTBOUND_DOCUMENT_NO))
				.build();

		exchange.setProperty(ROUTE_MSG_FROM_MF_CONTEXT, msgFromMfContext);
	}

	private JsonExternalSystemEndpoint deserializeEndpointParameters(@NonNull final Map<String, String> parameters)
	{
		final String jsonString = parameters.get(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS);
		if (Check.isBlank(jsonString))
		{
			throw new RuntimeCamelException("Missing parameter '" + PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS + "' in request!");
		}

		try
		{
			return mapper.readValue(jsonString, JsonExternalSystemEndpoint.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeCamelException("Unable to deserialize value of parameter '" + PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS + "' from request! Value=" + jsonString, e);
		}
	}

	private void executeJavaScript(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final String script = javaScriptRepo.get(msgFromMfContext.getScriptIdentifier());
		msgFromMfContext.setScript(script);

		final String javaScriptResult = javaScriptExecutorService.executeScript(
				msgFromMfContext.getScriptIdentifier(),
				msgFromMfContext.getScript(),
				msgFromMfContext.getScriptingRequestBody());

		msgFromMfContext.setScriptReturnValue(javaScriptResult);
	}

	private static MsgFromMfContext getMsgFromMfContext(@NonNull final Exchange exchange)
	{
		return ProcessorHelper.getPropertyOrThrowError(exchange,
				ROUTE_MSG_FROM_MF_CONTEXT,
				MsgFromMfContext.class);
	}

	/**
	 * If {@link JsonExternalSystemEndpoint#getArrayFanOut()} is TRUE and the JS-transformed script return value
	 * parses as a non-empty JSON array, store the parsed array + element count on the context and on the exchange
	 * properties so the route's fan-out branch can split over it.
	 * <p>
	 * No-ops (and logs a warning) when fan-out is enabled but the JS output is an empty array or not an array at all.
	 */
	@VisibleForTesting
	void splitOnArrayIfRequested(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		if (!isFanOutEnabled(msgFromMfContext.getEndpointParameters()))
		{
			return;
		}

		final String scriptReturnValue = msgFromMfContext.getScriptReturnValue();
		if (Check.isBlank(scriptReturnValue))
		{
			log.warn("ScriptedAdapter fan-out enabled but JS returned blank value for record {}/{} script={}; falling through to single-request behaviour",
					msgFromMfContext.getOutboundRecordTableName(),
					msgFromMfContext.getOutboundRecordId(),
					msgFromMfContext.getScriptIdentifier());
			return;
		}

		final JsonNode parsed;
		try
		{
			parsed = mapper.readTree(scriptReturnValue);
		}
		catch (final JsonProcessingException e)
		{
			log.warn("ScriptedAdapter fan-out enabled but JS output is not valid JSON for record {}/{} script={}; falling through to single-request behaviour",
					msgFromMfContext.getOutboundRecordTableName(),
					msgFromMfContext.getOutboundRecordId(),
					msgFromMfContext.getScriptIdentifier(),
					e);
			return;
		}

		if (!(parsed instanceof ArrayNode))
		{
			log.warn("ScriptedAdapter fan-out enabled but JS returned non-array for record {}/{} script={}; falling through to single-request behaviour",
					msgFromMfContext.getOutboundRecordTableName(),
					msgFromMfContext.getOutboundRecordId(),
					msgFromMfContext.getScriptIdentifier());
			return;
		}

		final ArrayNode arrayNode = (ArrayNode)parsed;
		if (arrayNode.size() == 0)
		{
			log.warn("ScriptedAdapter fan-out enabled but JS returned empty array for record {}/{} script={}; nothing to dispatch",
					msgFromMfContext.getOutboundRecordTableName(),
					msgFromMfContext.getOutboundRecordId(),
					msgFromMfContext.getScriptIdentifier());
			return;
		}

		msgFromMfContext.setFanOutArray(arrayNode);
		msgFromMfContext.setFanOutTotal(arrayNode.size());

		// Expose to Camel simple-expression splitter via exchange property
		exchange.setProperty(EXCHANGE_PROPERTY_FAN_OUT_ARRAY, arrayNode);
		exchange.setProperty(EXCHANGE_PROPERTY_FAN_OUT_TOTAL, arrayNode.size());

		log.info("ScriptedAdapter fan-out activated: {} element(s) will be dispatched for record {}/{} script={}",
				arrayNode.size(),
				msgFromMfContext.getOutboundRecordTableName(),
				msgFromMfContext.getOutboundRecordId(),
				msgFromMfContext.getScriptIdentifier());
	}

	private static boolean isFanOutEnabled(@NonNull final JsonExternalSystemEndpoint endpointParameters)
	{
		return Boolean.TRUE.equals(endpointParameters.getArrayFanOut());
	}

	/**
	 * Initialises the per-fan-out {@link FanOutResult} aggregator on the exchange property
	 * before the split starts iterating.
	 */
	private void initFanOutResult(@NonNull final Exchange exchange)
	{
		exchange.setProperty(EXCHANGE_PROPERTY_FAN_OUT_RESULT, new FanOutResult());
	}

	/**
	 * Per-iteration: copies the current array element JSON onto the context's
	 * {@code scriptReturnValue} so the existing per-iteration HTTP/SFTP processors
	 * (which read the body from {@code MsgFromMfContext#getScriptReturnValue}) work unchanged.
	 * Also records the 1-based iteration index on the exchange property for downstream logging
	 * and attachment naming.
	 */
	private void prepareFanOutIteration(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final Object splitBody = exchange.getIn().getBody();
		final String elementJson;
		if (splitBody instanceof JsonNode)
		{
			final JsonNode node = (JsonNode)splitBody;
			// For string-typed elements (JS returns array of EDIFACT strings), unwrap the text value
			elementJson = node.isTextual() ? node.asText() : node.toString();
		}
		else if (splitBody instanceof CharSequence)
		{
			elementJson = splitBody.toString();
		}
		else
		{
			elementJson = exchange.getIn().getBody(String.class);
		}

		msgFromMfContext.setScriptReturnValue(elementJson);
		exchange.getIn().setBody(elementJson);

		// Camel's split exchange property "CamelSplitIndex" is 0-based; expose 1-based for log/filename use
		final Integer splitIndex = exchange.getProperty(Exchange.SPLIT_INDEX, Integer.class);
		final int oneBasedIndex = (splitIndex != null ? splitIndex : 0) + 1;
		exchange.setProperty(EXCHANGE_PROPERTY_FAN_OUT_INDEX, oneBasedIndex);
	}

	/**
	 * Per-iteration success bookkeeping.
	 */
	private void recordFanOutSuccess(@NonNull final Exchange exchange)
	{
		final FanOutResult result = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_RESULT, FanOutResult.class);
		final Integer index = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_INDEX, Integer.class);
		final Integer total = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_TOTAL, Integer.class);
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);
		if (result != null)
		{
			result.recordSuccess();
		}
		log.info("ScriptedAdapter fan-out: element {}/{} dispatched successfully for record {}/{} script={}",
				index, total,
				msgFromMfContext.getOutboundRecordTableName(),
				msgFromMfContext.getOutboundRecordId(),
				msgFromMfContext.getScriptIdentifier());
	}

	/**
	 * Per-iteration failure bookkeeping. The exception is logged and swallowed so the split
	 * proceeds to the next element (continue-on-failure aggregation).
	 */
	private void recordFanOutFailure(@NonNull final Exchange exchange)
	{
		final FanOutResult result = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_RESULT, FanOutResult.class);
		final Integer index = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_INDEX, Integer.class);
		final Integer total = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_TOTAL, Integer.class);
		final Exception caught = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		final String message = caught != null ? caught.getMessage() : "(unknown)";
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);
		if (result != null)
		{
			result.recordFailure("element " + index + "/" + total + ": " + message);
		}
		log.warn("ScriptedAdapter fan-out: element {}/{} FAILED for record {}/{} script={}: {}",
				index, total,
				msgFromMfContext.getOutboundRecordTableName(),
				msgFromMfContext.getOutboundRecordId(),
				msgFromMfContext.getScriptIdentifier(),
				message,
				caught);
	}

	/**
	 * Post-split: log the aggregated counters and throw a {@link RuntimeCamelException} when ALL
	 * elements failed (so the caller knows nothing was delivered). When at least one element
	 * succeeded the fan-out is considered successful overall.
	 */
	private void finalizeFanOut(@NonNull final Exchange exchange)
	{
		final FanOutResult result = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_RESULT, FanOutResult.class);
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		if (result == null)
		{
			// Should not happen because initFanOutResult ran before the split
			log.warn("ScriptedAdapter fan-out: no FanOutResult on exchange — skipping finalisation");
			return;
		}

		log.info("ScriptedAdapter fan-out completed: {} successful + {} failed downstream calls for record {}/{} script={}",
				result.getSuccessCount(),
				result.getFailureCount(),
				msgFromMfContext.getOutboundRecordTableName(),
				msgFromMfContext.getOutboundRecordId(),
				msgFromMfContext.getScriptIdentifier());

		if (result.isAllFailed())
		{
			throw new RuntimeCamelException(
					"ScriptedAdapter fan-out: all " + result.getFailureCount() + " element(s) failed for record "
							+ msgFromMfContext.getOutboundRecordTableName() + "/" + msgFromMfContext.getOutboundRecordId()
							+ " script=" + msgFromMfContext.getScriptIdentifier()
							+ "; failures: " + String.join(" | ", result.getFailureMessages()));
		}
	}

	private void extractTransportTypeToHeader(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);
		final String transportType = msgFromMfContext.getEndpointParameters().getTransportType();
		// Default to HTTP if not specified
		exchange.getIn().setHeader(HEADER_TRANSPORT_TYPE, Check.isBlank(transportType) ? "HTTP" : transportType);
	}

	private void extractAuthTypeToHeader(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);
		exchange.getIn().setHeader(HEADER_AUTH_TYPE, msgFromMfContext.getEndpointParameters().getAuthType());
	}

	private void prepareHttpRequestForTokenAuth(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final JsonExternalSystemEndpoint endpointParameters = msgFromMfContext.getEndpointParameters();
		Check.assumeEquals(endpointParameters.getAuthType(), JsonEndpointAuthType.Token);

		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().setHeader(AUTHORIZATION, endpointParameters.getToken());
		exchange.getIn().setHeader(Exchange.HTTP_URI, endpointParameters.getEndpointUrl());
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, resolveContentType(endpointParameters));
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.valueOf(endpointParameters.getMethod()));
		exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
	}

	private void prepareHttpRequestForSasAuth(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final JsonExternalSystemEndpoint endpointParameters = msgFromMfContext.getEndpointParameters();
		Check.assumeEquals(endpointParameters.getAuthType(), JsonEndpointAuthType.SAS);

		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().setHeader(Exchange.HTTP_URI, endpointParameters.getEndpointUrl() + "&sig=" + endpointParameters.getSasSignature());
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, resolveContentType(endpointParameters));
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.valueOf(endpointParameters.getMethod()));
		exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
	}

	private void prepareHttpRequestForOAuth(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final JsonExternalSystemEndpoint endpointParameters = msgFromMfContext.getEndpointParameters();
		Check.assumeEquals(endpointParameters.getAuthType(), JsonEndpointAuthType.OAuth);

		final OAuthAccessToken accessToken = oauthTokenManager.getAccessToken(
				OAuthAccessTokenRequest.builder()
						.identity(extractOAuthIdentity(endpointParameters))
						.clientSecret(endpointParameters.getClientSecret())
						.password(endpointParameters.getPassword())
						.build());

		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().setHeader(AUTHORIZATION, "Bearer " + accessToken.getAccessToken());
		exchange.getIn().setHeader(Exchange.HTTP_URI, endpointParameters.getEndpointUrl());
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, resolveContentType(endpointParameters));
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.valueOf(endpointParameters.getMethod()));
		exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
	}

	private void prepareHttpRequestForBasicAuth(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final JsonExternalSystemEndpoint endpointParameters = msgFromMfContext.getEndpointParameters();
		Check.assumeEquals(endpointParameters.getAuthType(), JsonEndpointAuthType.Basic);

		final String username = endpointParameters.getUser();
		final String password = endpointParameters.getPassword();

		final String credentials = username + ":" + password;
		final String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().setHeader(AUTHORIZATION, "Basic " + encoded);
		exchange.getIn().setHeader(Exchange.HTTP_URI, endpointParameters.getEndpointUrl());
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, resolveContentType(endpointParameters));
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.valueOf(endpointParameters.getMethod()));
		exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
	}

	private static OAuthIdentity extractOAuthIdentity(final JsonExternalSystemEndpoint endpointParameters)
	{
		return OAuthIdentity.builder()
				.tokenUrl(extractBaseUrl(endpointParameters.getEndpointUrl()) + "/login")
				.clientId(endpointParameters.getClientId())
				.username(endpointParameters.getUser())
				.build();
	}

	@NonNull
	private static MediaType resolveContentType(@NonNull final JsonExternalSystemEndpoint endpointParameters)
	{
		return Optional.ofNullable(StringUtils.trimBlankToNull(endpointParameters.getContentType()))
				.map(MediaType::parseMediaType)
				.orElse(MediaType.APPLICATION_JSON);
	}

	@NonNull
	private static String extractBaseUrl(@NonNull final String endpointUrl)
	{
		final URL url;
		try
		{
			url = new URL(endpointUrl);
		}
		catch (final MalformedURLException e)
		{
			throw new RuntimeCamelException("Failed to parse endpoint URL: " + endpointUrl, e);
		}

		final String protocol = url.getProtocol();
		final String host = url.getHost();
		final int port = url.getPort(); // returns -1 if not specified

		return port == -1
				? String.format("%s://%s", protocol, host)
				: String.format("%s://%s:%d", protocol, host, port);
	}

	private void forceRefreshOAuthToken(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final JsonExternalSystemEndpoint endpointParameters = msgFromMfContext.getEndpointParameters();

		// Invalidate the cached token so the next request will get a fresh token
		oauthTokenManager.invalidateToken(extractOAuthIdentity(endpointParameters));

		// Re-prepare request with fresh token
		prepareHttpRequestForOAuth(exchange);
	}

	private void prepareJsonAttachmentRequest(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final JsonAttachment attachment = JsonAttachment.builder()
				.fileName(buildAttachmentFileName(exchange))
				.data(buildBase64FileData(exchange))
				.type(JsonAttachmentSourceType.Data)
				.build();

		final JsonTableRecordReference jsonTableRecordReference = JsonTableRecordReference.builder()
				.tableName(msgFromMfContext.getOutboundRecordTableName())
				.recordId(JsonMetasfreshId.of(msgFromMfContext.getOutboundRecordId()))
				.build();

		final JsonAttachmentRequest jsonAttachmentRequest = JsonAttachmentRequest.builder()
				.attachment(attachment)
				.orgCode(msgFromMfContext.getOrgCode())
				.reference(jsonTableRecordReference)
				.build();

		exchange.getIn().setBody(jsonAttachmentRequest);
	}

	private void prepareSftpAttachmentRequest(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		if (Check.isBlank(msgFromMfContext.getOutboundRecordId()) || Check.isBlank(msgFromMfContext.getOutboundRecordTableName()))
		{
			log.warn("No outbound record ID or table name — skipping SFTP delivery log attachment");
			return;
		}

		final String fileContent = "=== Scripted Adapter Log (SFTP) ===\n"
				+ "Timestamp: " + LocalDateTime.now() + "\n"
				+ "Script Name: " + msgFromMfContext.getScriptIdentifier() + "\n"
				+ "Script Returned Value: " + msgFromMfContext.getScriptReturnValue() + "\n"
				+ "SFTP Host: " + msgFromMfContext.getEndpointParameters().getSftpHost() + "\n"
				+ "SFTP Port: " + (msgFromMfContext.getEndpointParameters().getSftpPort() != null ? msgFromMfContext.getEndpointParameters().getSftpPort() : 22) + "\n"
				+ "SFTP Remote Path: " + msgFromMfContext.getEndpointParameters().getSftpRemotePath() + "\n"
				+ "SFTP Filename Pattern: " + msgFromMfContext.getEndpointParameters().getSftpFilenamePattern() + "\n"
				+ "Delivery: SUCCESS\n";

		final String base64Data = Base64.getEncoder().encodeToString(fileContent.getBytes());

		final JsonAttachment attachment = JsonAttachment.builder()
				.fileName(buildAttachmentFileName(exchange))
				.data(base64Data)
				.type(JsonAttachmentSourceType.Data)
				.build();

		final JsonTableRecordReference jsonTableRecordReference = JsonTableRecordReference.builder()
				.tableName(msgFromMfContext.getOutboundRecordTableName())
				.recordId(JsonMetasfreshId.of(msgFromMfContext.getOutboundRecordId()))
				.build();

		final JsonAttachmentRequest jsonAttachmentRequest = JsonAttachmentRequest.builder()
				.attachment(attachment)
				.orgCode(msgFromMfContext.getOrgCode())
				.reference(jsonTableRecordReference)
				.build();

		exchange.getIn().setBody(jsonAttachmentRequest);
	}

	/**
	 * Returns the audit-attachment filename. When fan-out is active for this exchange,
	 * inserts {@code -{i}-of-{n}} before the file extension so each per-element call
	 * produces a uniquely named audit log on the metasfresh record (e.g.
	 * {@code scripted-adapter-log-1-of-3.txt}). Otherwise returns the unchanged base name.
	 */
	@NonNull
	private static String buildAttachmentFileName(@NonNull final Exchange exchange)
	{
		final Integer index = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_INDEX, Integer.class);
		final Integer total = exchange.getProperty(EXCHANGE_PROPERTY_FAN_OUT_TOTAL, Integer.class);
		if (index == null || total == null)
		{
			return ATTACHMENT_FILE_NAME;
		}

		final String suffix = "-" + index + "-of-" + total;
		final int lastDot = ATTACHMENT_FILE_NAME.lastIndexOf('.');
		if (lastDot < 0)
		{
			return ATTACHMENT_FILE_NAME + suffix;
		}
		return ATTACHMENT_FILE_NAME.substring(0, lastDot) + suffix + ATTACHMENT_FILE_NAME.substring(lastDot);
	}

	@NonNull
	private String buildBase64FileData(@NonNull final Exchange exchange)
	{
		final MsgFromMfContext msgFromMfContext = getMsgFromMfContext(exchange);

		final String endpointResponse = exchange.getIn().getBody(String.class);

		final String fileContent = "=== Scripted Adapter Log ===\n"
				+ "Timestamp: " + LocalDateTime.now() + "\n"
				+ "Script Name: " + msgFromMfContext.getScriptIdentifier() + "\n"
				+ "Script Returned Value: " + msgFromMfContext.getScriptReturnValue() + "\n"
				+ "HTTP Endpoint: " + msgFromMfContext.getEndpointParameters().getEndpointUrl() + "\n"
				+ "HTTP Response: " + endpointResponse + "\n";

		return Base64.getEncoder().encodeToString(fileContent.getBytes());
	}
}
