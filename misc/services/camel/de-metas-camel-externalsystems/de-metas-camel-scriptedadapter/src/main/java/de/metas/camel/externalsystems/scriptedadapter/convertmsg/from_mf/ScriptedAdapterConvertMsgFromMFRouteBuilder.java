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
import com.fasterxml.jackson.databind.ObjectMapper;
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

	public static final String PROPERTY_SCRIPTING_REPO_BASE_DIR = "metasfresh.scriptedadapter.repo.baseDir";

	@VisibleForTesting
	static final String ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID = "ScriptedExportConversionOutboundHttpEPId";

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
				.fileName(ATTACHMENT_FILE_NAME)
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
				.fileName(ATTACHMENT_FILE_NAME)
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
