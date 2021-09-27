/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.util.web.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.apirequest.ApiAuditLoggable;
import de.metas.audit.apirequest.HttpMethod;
import de.metas.audit.apirequest.common.HttpHeadersWrapper;
import de.metas.audit.apirequest.config.ApiAuditConfig;
import de.metas.audit.apirequest.config.ApiAuditConfigId;
import de.metas.audit.apirequest.config.ApiAuditConfigRepository;
import de.metas.audit.apirequest.request.ApiRequestAudit;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.Status;
import de.metas.audit.apirequest.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.apirequest.response.ApiResponseAudit;
import de.metas.audit.apirequest.response.ApiResponseAuditRepository;
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.audit.data.service.CompositeDataAuditService;
import de.metas.audit.data.service.GenericDataExportAuditRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserGroupUserAssignment;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.util.Env;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_PINSTANCE_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class ApiAuditService
{
	public static final String API_FILTER_REQUEST_ID_HEADER = "X-ApiFilter-Request-ID";

	private static final AdMessageKey MSG_SUCCESSFUL_API_INVOCATION =
			AdMessageKey.of("de.metas.util.web.audit.successful_invocation");

	private static final AdMessageKey MSG_API_INVOCATION_FAILED =
			AdMessageKey.of("de.metas.util.web.audit.invocation_failed");

	private static final String CFG_INTERNAL_HOST_NAME = "de.metas.util.web.audit.AppServerInternalHostName";
	/**
	 * this default works if you run both app and webapi locally. In our usual stack, it should be set to {@code "app"}.
	 */
	private static final String CFG_INTERNAL_HOST_NAME_DEFAULT = "localhost";

	public static final String CFG_INTERNAL_PORT = "de.metas.util.web.audit.AppServerInternalPort";
	private static final int CFG_INTERNAL_PORT_DEFAULT = 8282;

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	private final ApiAuditConfigRepository apiAuditConfigRepository;
	private final ApiRequestAuditRepository apiRequestAuditRepository;
	private final ApiResponseAuditRepository apiResponseAuditRepository;
	private final ApiAuditRequestLogDAO apiAuditRequestLogDAO;
	private final UserGroupRepository userGroupRepository;
	private final CompositeDataAuditService compositeDataAuditService;

	private final WebClient webClient;
	private final ConcurrentHashMap<UserId, HttpCallScheduler> callerId2Scheduler;
	private final ObjectMapper objectMapper;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public ApiAuditService(
			@NonNull final ApiAuditConfigRepository apiAuditConfigRepository,
			@NonNull final ApiRequestAuditRepository apiRequestAuditRepository,
			@NonNull final ApiResponseAuditRepository apiResponseAuditRepository,
			@NonNull final ApiAuditRequestLogDAO apiAuditRequestLogDAO,
			@NonNull final UserGroupRepository userGroupRepository,
			@NonNull final CompositeDataAuditService compositeDataAuditService)
	{
		this.apiAuditConfigRepository = apiAuditConfigRepository;
		this.apiRequestAuditRepository = apiRequestAuditRepository;
		this.apiResponseAuditRepository = apiResponseAuditRepository;
		this.apiAuditRequestLogDAO = apiAuditRequestLogDAO;
		this.userGroupRepository = userGroupRepository;
		this.compositeDataAuditService = compositeDataAuditService;

		// allow up to 50MB large results in API responses; thx to https://stackoverflow.com/a/62543241/1012103
		this.webClient = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
																		.codecs(configurer -> configurer
																				.defaultCodecs()
																				.maxInMemorySize(50 * 1024 * 1024))
																		.build())
				.build();
		this.callerId2Scheduler = new ConcurrentHashMap<>();
		this.objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	public boolean wasAlreadyFiltered(@NonNull final HttpServletRequest httpServletRequest)
	{
		return httpServletRequest.getHeader(API_FILTER_REQUEST_ID_HEADER) != null;
	}

	@NonNull
	public Optional<ApiRequestAuditId> extractApiRequestAuditId(@NonNull final HttpServletRequest httpServletRequest)
	{
		return Optional.ofNullable(httpServletRequest.getHeader(API_FILTER_REQUEST_ID_HEADER))
				.map(requestId -> ApiRequestAuditId.ofRepoId(NumberUtils.asInt(requestId, -1)));
	}

	public ApiAuditLoggable createLogger(@NonNull final ApiRequestAuditId apiRequestAuditId, @NonNull final UserId userId)
	{
		return ApiAuditLoggable.builder()
				.clientId(Env.getClientId())
				.userId(userId)
				.apiRequestAuditId(apiRequestAuditId)
				.apiAuditRequestLogDAO(apiAuditRequestLogDAO)
				.bufferSize(100)
				.build();
	}

	@NonNull
	public Optional<ApiAuditConfig> getMatchingAuditConfig(@NonNull final ServletRequest request)
	{
		final CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper((HttpServletRequest)request, objectMapper);

		if (Check.isBlank(requestWrapper.getFullPath()) || Check.isBlank(requestWrapper.getHttpMethodString()))
		{
			return Optional.empty();
		}

		final OrgId orgId = Env.getOrgId();
		final ImmutableList<ApiAuditConfig> apiAuditConfigs = apiAuditConfigRepository.getAllConfigsByOrgId(orgId);

		return apiAuditConfigs.stream()
				.filter(config -> config.matchesRequest(requestWrapper.getFullPath(), requestWrapper.getHttpMethodString()))
				.min(Comparator.comparingInt(ApiAuditConfig::getSeqNo));
	}

	public void processHttpCall(
			@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response,
			@NonNull final ApiAuditConfig apiAuditConfig) throws IOException
	{
		final OrgId orgId = apiAuditConfig.getOrgId();

		final CustomHttpRequestWrapper httpRequest = new CustomHttpRequestWrapper(request, objectMapper);

		final ApiRequestAudit requestAudit = logRequest(httpRequest, apiAuditConfig.getApiAuditConfigId(), orgId);

		final ApiAuditLoggable apiAuditLoggable = createLogger(requestAudit.getIdNotNull(), requestAudit.getUserId());

		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(apiAuditLoggable))
		{
			final CompletableFuture<ApiResponse> actualRestApiResponseCF = new CompletableFuture<>();

			final FutureCompletionContext futureCompletionContext = FutureCompletionContext.builder()
					.apiAuditConfig(apiAuditConfig)
					.apiAuditLoggable(apiAuditLoggable)
					.apiRequestAudit(requestAudit)
					.orgId(orgId)
					.build();

			final CompletableFuture<ApiResponse> whenCompleteFuture = actualRestApiResponseCF
					.whenComplete((apiResponse, throwable) -> handleFutureCompletion(apiResponse, throwable, futureCompletionContext));

			final Supplier<ApiResponse> callEndpointSupplier = () -> executeHttpCall(requestAudit);

			final ScheduledRequest scheduledRequest = new ScheduledRequest(actualRestApiResponseCF, callEndpointSupplier);

			final UserId callerUserId = Env.getLoggedUserId();

			final HttpCallScheduler httpCallScheduler = callerId2Scheduler.computeIfAbsent(callerUserId, (userId) -> new HttpCallScheduler());

			httpCallScheduler.schedule(scheduledRequest);

			handleSuccessfulResponse(apiAuditConfig, requestAudit, whenCompleteFuture, response);
		}
		catch (final Exception e)
		{
			apiAuditLoggable.addLog("Caught {} with message={}", e.getClass().getName(), e.getMessage(), e);
			handleErrorResponse(requestAudit, e, response);
		}
		finally
		{
			apiAuditLoggable.flush();
		}
	}

	/**
	 * Executes the request that is wrapped in the given {@code apiRequestAudit} using the spring {@link WebClient}.
	 * <p>
	 * Note that it makes the call against {@code http://localhost + <our-current-port> + }{@link ApiRequestAudit#getRequestURI()}
	 * and not to the URL that we are called with from the outside (i.e. {@link ApiRequestAudit#getPath()}).
	 * <p>
	 * This is to avoid problems with our reverse proxy that would otherwise be invoked once again, but with an already rewritten URL.
	 */
	@NonNull
	public ApiResponse executeHttpCall(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		if (apiRequestAudit.getMethod() == null || apiRequestAudit.getPath() == null)
		{
			throw new AdempiereException("Missing essential data from the given ApiRequestAudit!")
					.setParameter("Method", apiRequestAudit.getMethod())
					.setParameter("Path", apiRequestAudit.getPath());
		}

		final org.springframework.http.HttpMethod httpMethod = org.springframework.http.HttpMethod.resolve(apiRequestAudit.getMethod().getCode());

		final WebClient.RequestBodyUriSpec uriSpec = webClient.method(httpMethod);

		final HttpHeaders httpHeaders = new HttpHeaders();
		apiRequestAudit.getRequestHeaders(objectMapper)
				.map(HttpHeadersWrapper::getKeyValueHeaders)
				.ifPresent(httpHeaders::addAll);

		httpHeaders.add(API_FILTER_REQUEST_ID_HEADER, String.valueOf(apiRequestAudit.getIdNotNull().getRepoId()));

		httpHeaders.forEach((key, value) -> uriSpec.header(key, value.toArray(new String[0])));

		final String hostName = computeInternalHostName(apiRequestAudit);
		final int port = sysConfigBL.getIntValue(CFG_INTERNAL_PORT, CFG_INTERNAL_PORT_DEFAULT);

		final String[] split = apiRequestAudit.getPath().split("\\?");
		final String queryString = split.length > 1 ? "?" + split[1] : "";

		final URI uri = URI.create("http://" + hostName + ":" + port + apiRequestAudit.getRequestURI() + queryString);

		final WebClient.RequestBodySpec bodySpec = uriSpec.uri(uri);

		if (Check.isNotBlank(apiRequestAudit.getBody()))
		{
			bodySpec.body(Mono.just(apiRequestAudit.getRequestBody(objectMapper)), Object.class);
		}

		try
		{
			final ApiResponse apiResponse = bodySpec.exchangeToMono(cr -> cr
					.bodyToMono(String.class)
					.map(body -> ApiResponse.of(cr.rawStatusCode(), cr.headers().asHttpHeaders(), body))
					.defaultIfEmpty(ApiResponse.of(cr.rawStatusCode(), cr.headers().asHttpHeaders(), null)))
					.block();

			performDataExportAudit(apiRequestAudit, apiResponse);

			return apiResponse;
		}
		catch (final RuntimeException rte)
		{
			throw new AdempiereException("Caught " + rte.getClass().getSimpleName() + " exception while making web-client http call", rte)
					.appendParametersToMessage()
					.setParameter("URI", uri)
					.setParameter("AD_SysConfig de.metas.util.web.audit.AppServerInternalHostName", sysConfigBL.getValue(CFG_INTERNAL_HOST_NAME, CFG_INTERNAL_HOST_NAME_DEFAULT))
					.setParameter("AD_SysConfig de.metas.util.web.audit.AppServerInternalPort", sysConfigBL.getIntValue(CFG_INTERNAL_PORT, CFG_INTERNAL_PORT_DEFAULT));
		}
	}

	private String computeInternalHostName(@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final String hostName;
		if (apiRequestAudit.getPath().startsWith("http://localhost"))
		{
			hostName = "localhost";
		}
		else
		{
			hostName = sysConfigBL.getValue(CFG_INTERNAL_HOST_NAME, CFG_INTERNAL_HOST_NAME_DEFAULT);
		}
		return hostName;
	}

	public void logResponse(
			@NonNull final ApiResponse apiResponse,
			@NonNull final ApiRequestAuditId apiRequestAuditId,
			@NonNull final OrgId orgId)
	{
		try
		{
			final String bodyAsString = apiResponse.getBody() != null
					? objectMapper.writeValueAsString(apiResponse.getBody())
					: null;

			final LinkedMultiValueMap<String, String> responseHeadersMultiValueMap = new LinkedMultiValueMap<>();

			if (apiResponse.getHttpHeaders() != null)
			{
				apiResponse.getHttpHeaders().forEach(responseHeadersMultiValueMap::addAll);
			}

			final String responseHeaders = responseHeadersMultiValueMap.isEmpty()
					? null
					: objectMapper.writeValueAsString(HttpHeadersWrapper.of(responseHeadersMultiValueMap));

			final ApiResponseAudit apiResponseAudit = ApiResponseAudit.builder()
					.orgId(orgId)
					.apiRequestAuditId(apiRequestAuditId)
					.body(bodyAsString)
					.httpCode(String.valueOf(apiResponse.getStatusCode()))
					.time(Instant.now())
					.httpHeaders(responseHeaders)
					.build();

			apiResponseAuditRepository.save(apiResponseAudit);
		}
		catch (final JsonProcessingException e)
		{
			final AdempiereException exception = AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("ApiResponse", apiResponse);

			Loggables.addLog("Error when trying to parse the api response body!", exception);
		}
	}

	@NonNull
	public ApiRequestAudit updateRequestStatus(
			@NonNull final Status status,
			@NonNull final ApiRequestAudit apiRequestAudit)
	{
		final ApiRequestAudit updateApiRequestAudit = apiRequestAudit.toBuilder()
				.status(status)
				.build();

		return apiRequestAuditRepository.save(updateApiRequestAudit);
	}

	public void notifyUserInCharge(
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiRequestAudit apiRequestAudit,
			final boolean isError)
	{
		final Optional<UserGroupId> userGroupToNotify = apiAuditConfig.getUserGroupToNotify(isError);

		if (!userGroupToNotify.isPresent())
		{
			Loggables.addLog("Notification skipped due to ApiAuditConfig! UserGroupInChargeId = {}, "
									 + "ApiAuditConfigId = {}, NotifyUserInChargeTrigger = {}",
							 apiAuditConfig.getUserGroupInChargeId(), apiAuditConfig.getApiAuditConfigId(), apiAuditConfig.getNotifyUserInCharge());
			return;
		}

		final AdMessageKey messageKey = isError ? MSG_API_INVOCATION_FAILED : MSG_SUCCESSFUL_API_INVOCATION;

		final UserNotificationRequest.TargetRecordAction targetRecordAction = UserNotificationRequest
				.TargetRecordAction
				.of(I_API_Request_Audit.Table_Name, apiRequestAudit.getIdNotNull().getRepoId());

		userGroupRepository
				.getByUserGroupId(userGroupToNotify.get())
				.streamAssignmentsFor(userGroupToNotify.get(), Instant.now())
				.map(UserGroupUserAssignment::getUserId)
				.map(userId -> UserNotificationRequest.builder()
						.recipientUserId(userId)
						.contentADMessage(messageKey)
						.contentADMessageParam(apiRequestAudit.getPath())
						.targetAction(targetRecordAction)
						.build())
				.forEach(notificationBL::send);
	}

	public boolean bypassFilter(@NonNull final HttpServletRequest request)
	{
		final String contentType = request.getContentType();

		if (Check.isBlank(contentType))
		{
			return false;
		}

		return !contentType.contains(APPLICATION_JSON_VALUE);
	}

	private ApiRequestAudit logRequest(
			@NonNull final CustomHttpRequestWrapper customHttpRequest,
			@NonNull final ApiAuditConfigId apiAuditConfigId,
			@NonNull final OrgId orgId)
	{
		try
		{
			final LinkedMultiValueMap<String, String> requestHeadersMultiValueMap = new LinkedMultiValueMap<>();
			customHttpRequest.getAllHeaders().forEach(requestHeadersMultiValueMap::addAll);

			final String requestHeaders = requestHeadersMultiValueMap.isEmpty()
					? null
					: objectMapper.writeValueAsString(HttpHeadersWrapper.of(requestHeadersMultiValueMap));

			final ApiRequestAudit apiRequestAudit = ApiRequestAudit.builder()
					.apiAuditConfigId(apiAuditConfigId)
					.orgId(orgId)
					.roleId(Env.getLoggedRoleId())
					.userId(Env.getLoggedUserId())
					.status(Status.RECEIVED)
					.body(customHttpRequest.getRequestBodyAsString())
					.method(HttpMethod.ofCodeOptional(customHttpRequest.getHttpMethodString()).orElse(null))
					.path(customHttpRequest.getFullPath())
					.remoteAddress(customHttpRequest.getRemoteAddr())
					.remoteHost(customHttpRequest.getRemoteHost())
					.time(Instant.now())
					.httpHeaders(requestHeaders)
					.requestURI(customHttpRequest.getRequestURI())
					.build();

			return apiRequestAuditRepository.save(apiRequestAudit);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to create ApiRequestAudit!", e)
					.appendParametersToMessage()
					.setParameter("Path", customHttpRequest.getFullPath())
					.setParameter("Method", customHttpRequest.getHttpMethodString());

		}
	}

	private void handleFutureCompletion(
			@Nullable final ApiResponse apiResponse,
			@Nullable final Throwable throwable,
			@NonNull final FutureCompletionContext completionContext)
	{
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(completionContext.getApiAuditLoggable()))
		{
			if (apiResponse != null)
			{
				logResponse(apiResponse, completionContext.getApiRequestAudit().getIdNotNull(), completionContext.getOrgId());

				final Status requestStatus = !apiResponse.hasStatus2xx()
						? Status.ERROR
						: Status.PROCESSED;

				updateRequestStatus(requestStatus, completionContext.getApiRequestAudit());

				final boolean isError = Status.ERROR.equals(requestStatus);
				notifyUserInCharge(completionContext.getApiAuditConfig(), completionContext.getApiRequestAudit(), isError);

				Loggables.addLog("Request routed successfully!");
			}
			else
			{
				updateRequestStatus(Status.ERROR, completionContext.getApiRequestAudit());

				final boolean isError = true;
				notifyUserInCharge(completionContext.getApiAuditConfig(), completionContext.getApiRequestAudit(), isError);

				Loggables.addLog("Error encountered while routing the request!", throwable);
			}
		}
	}

	private void handleSuccessfulResponse(
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiRequestAudit apiRequestAudit,
			@NonNull final CompletableFuture<ApiResponse> whenCompleteFuture,
			@NonNull final HttpServletResponse httpServletResponse) throws IOException, InterruptedException, ExecutionException, TimeoutException
	{
		final ApiResponse actualAPIResponse = apiAuditConfig.isInvokerWaitsForResponse()
				? whenCompleteFuture.get(300, TimeUnit.SECONDS)
				: getGenericNoWaitResponse();

		final JsonApiResponse apiResponse = JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(apiRequestAudit.getIdNotNull().getRepoId()))
				.endpointResponse(actualAPIResponse.getBody())
				.build();

		if (actualAPIResponse.getHttpHeaders() != null)
		{
			forwardSomeResponseHttpHeaders(actualAPIResponse.getHttpHeaders(), httpServletResponse);
		}

		buildHttpResponse(httpServletResponse, apiResponse, actualAPIResponse.getStatusCode());
	}

	private void buildHttpResponse(
			@NonNull final HttpServletResponse httpServletResponse,
			@NonNull final JsonApiResponse apiResponse,
			final int statusCode) throws IOException
	{
		final String stringToForward = objectMapper.writeValueAsString(apiResponse);

		httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
		httpServletResponse.setStatus(statusCode);

		httpServletResponse.resetBuffer();
		if (stringToForward != null)
		{
			httpServletResponse.getWriter().write(stringToForward);
		}
		httpServletResponse.flushBuffer();
	}

	@NonNull
	private ApiResponse getGenericNoWaitResponse()
	{
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

		return ApiResponse.of(HttpStatus.ACCEPTED.value(), httpHeaders, null);
	}

	private void forwardSomeResponseHttpHeaders(@NonNull final HttpHeaders httpHeaders, @NonNull final HttpServletResponse servletResponse)
	{
		httpHeaders.keySet()
				.stream()
				.filter(key -> !key.equals(HttpHeaders.CONNECTION))
				.filter(key -> !key.equals(HttpHeaders.CONTENT_LENGTH))
				.filter(key -> !key.equals(HttpHeaders.CONTENT_TYPE))
				.filter(key -> !key.equals(HttpHeaders.TRANSFER_ENCODING)) // if we forwarded this without knowing what we do, we would annoy a possible nginx reverse proxy
				.forEach(key -> {
					final List<String> values = httpHeaders.get(key);
					if (values != null)
					{
						values.forEach(value -> servletResponse.addHeader(key, value));
					}
				});
	}

	private void performDataExportAudit(@NonNull final ApiRequestAudit apiRequestAudit, @NonNull final ApiResponse apiResponse)
	{
		if (!apiResponse.hasStatus2xx()
				|| apiRequestAudit.getRequestURI() == null
				|| apiResponse.getBody() == null)
		{
			return;
		}

		final GenericDataExportAuditRequest.GenericDataExportAuditRequestBuilder genericRequestBuilder = GenericDataExportAuditRequest
				.builder()
				.exportedObject(apiResponse.getBody())
				.requestURI(apiRequestAudit.getRequestURI());

		final Optional<HttpHeadersWrapper> requestHeaders = apiRequestAudit.getRequestHeaders(objectMapper);

		// get the AD_PInstance_ID and external-config-id if they are specified in the headers
		requestHeaders
				.map(HttpHeadersWrapper::getKeyValueHeaders)
				.map(headersMap -> headersMap.get(HEADER_EXTERNALSYSTEM_CONFIG_ID))
				.map(CollectionUtils::singleElement)
				.map(Integer::parseInt)
				.map(ExternalSystemParentConfigId::ofRepoId)
				.ifPresent(genericRequestBuilder::externalSystemParentConfigId);

		requestHeaders
				.map(HttpHeadersWrapper::getKeyValueHeaders)
				.map(headersMap -> headersMap.get(HEADER_PINSTANCE_ID))
				.map(CollectionUtils::singleElement)
				.map(Integer::parseInt)
				.map(PInstanceId::ofRepoId)
				.ifPresent(genericRequestBuilder::pInstanceId);

		final GenericDataExportAuditRequest auditRequest = genericRequestBuilder.build();

		compositeDataAuditService.performDataAuditForRequest(auditRequest);
	}

	private void handleErrorResponse(
			@NonNull final ApiRequestAudit apiRequestAudit,
			@NonNull final Exception e,
			@NonNull final HttpServletResponse httpServletResponse) throws IOException
	{
		final String language = Env.getADLanguageOrBaseLanguage();
		final JsonErrorItem error = JsonErrors.ofThrowable(e, language);

		final JsonApiResponse apiResponse = JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(apiRequestAudit.getIdNotNull().getRepoId()))
				.endpointResponse(error)
				.build();

		buildHttpResponse(httpServletResponse, apiResponse, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	@Value
	@Builder
	private static class FutureCompletionContext
	{
		@NonNull
		ApiAuditLoggable apiAuditLoggable;

		@NonNull
		ApiRequestAudit apiRequestAudit;

		@NonNull
		OrgId orgId;

		@NonNull
		ApiAuditConfig apiAuditConfig;
	}
}
