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
import de.metas.audit.ApiAuditLoggable;
import de.metas.audit.HttpMethod;
import de.metas.audit.config.ApiAuditConfig;
import de.metas.audit.config.ApiAuditConfigId;
import de.metas.audit.config.ApiAuditConfigRepository;
import de.metas.audit.request.ApiRequestAudiRepository;
import de.metas.audit.request.ApiRequestAudit;
import de.metas.audit.request.ApiRequestAuditId;
import de.metas.audit.request.RequestHeaders;
import de.metas.audit.request.Status;
import de.metas.audit.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.response.ApiResponseAudit;
import de.metas.audit.response.ApiResponseAuditRepository;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@Service
public class ApiAuditService
{
	public static final String API_FILTER_REQUEST_ID_HEADER = "X-ApiFilter-Request-ID";

	private final ApiAuditConfigRepository apiAuditConfigRepository;
	private final ApiRequestAudiRepository apiRequestAudiRepository;
	private final ApiResponseAuditRepository apiResponseAuditRepository;
	private final ApiAuditRequestLogDAO apiAuditRequestLogDAO;

	private final WebClient webClient;
	private final ConcurrentHashMap<UserId, HttpCallScheduler> callerId2Scheduler;
	private final ObjectMapper objectMapper;

	public ApiAuditService(
			@NonNull final ApiAuditConfigRepository apiAuditConfigRepository,
			@NonNull final ApiRequestAudiRepository apiRequestAudiRepository,
			@NonNull final ApiResponseAuditRepository apiResponseAuditRepository,
			@NonNull final ApiAuditRequestLogDAO apiAuditRequestLogDAO)
	{
		this.apiAuditConfigRepository = apiAuditConfigRepository;
		this.apiRequestAudiRepository = apiRequestAudiRepository;
		this.apiResponseAuditRepository = apiResponseAuditRepository;
		this.apiAuditRequestLogDAO = apiAuditRequestLogDAO;
		this.webClient = WebClient.create();
		this.callerId2Scheduler = new ConcurrentHashMap<>();
		this.objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	public boolean wasAlreadyFiltered(@NonNull final HttpServletRequest httpServletRequest)
	{
		return httpServletRequest.getHeader(API_FILTER_REQUEST_ID_HEADER) != null;
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
			@NonNull final ApiAuditConfig apiAuditConfig)
	{
		final OrgId orgId = apiAuditConfig.getOrgId();

		final CustomHttpRequestWrapper httpRequest = new CustomHttpRequestWrapper(request, objectMapper);

		final ApiRequestAudit requestAudit = logRequest(httpRequest, apiAuditConfig.getApiAuditConfigId(), orgId);

		final ApiAuditLoggable apiAuditLoggable = createLogger(requestAudit.getIdNotNull());

		try (final IAutoCloseable loggableRestorer = Loggables.temporarySetLoggable(apiAuditLoggable))
		{
			final CompletableFuture<ApiResponse> actualRestApiResponseCF = new CompletableFuture<>();

			actualRestApiResponseCF
					.whenComplete((apiResponse, throwable) -> handleFutureCompletion(apiResponse, throwable, apiAuditLoggable, requestAudit.getIdNotNull(), orgId));

			final Supplier<ApiResponse> callEndpointSupplier = () -> executeHttpCall(requestAudit);

			final ScheduleRequest scheduleRequest = new ScheduleRequest(actualRestApiResponseCF, callEndpointSupplier);

			final UserId callerUserId = Env.getLoggedUserId();

			final HttpCallScheduler httpCallScheduler = callerId2Scheduler.computeIfAbsent(callerUserId, (userId) -> new HttpCallScheduler());

			httpCallScheduler.schedule(scheduleRequest);

			handleSuccessfulResponse(apiAuditConfig, requestAudit, actualRestApiResponseCF, response);
		}
		catch (final Exception e)
		{
			apiAuditLoggable.addLog(e.getMessage(), e);
			throw AdempiereException.wrapIfNeeded(e);
		}
		finally
		{
			apiAuditLoggable.flush();
		}
	}

	@Nullable
	private ApiResponse executeHttpCall(@NonNull final ApiRequestAudit apiRequestAudit)
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
				.map(RequestHeaders::getKeyValueHeaders)
				.ifPresent(httpHeaders::addAll);

		httpHeaders.add(API_FILTER_REQUEST_ID_HEADER, String.valueOf(apiRequestAudit.getIdNotNull().getRepoId()));

		httpHeaders
				.forEach((key, value) -> uriSpec.header(key, value.toArray(new String[0])));

		final URI uri = URI.create(apiRequestAudit.getPath());

		final WebClient.RequestBodySpec bodySpec = uriSpec.uri(uri);

		if (Check.isNotBlank(apiRequestAudit.getBody()))
		{
			bodySpec.body(Mono.just(apiRequestAudit.getRequestBody(objectMapper)), Object.class);
		}

		return bodySpec.exchangeToMono(cr -> cr
				.bodyToMono(Object.class)

				.defaultIfEmpty(ApiResponse.of(String.valueOf(cr.rawStatusCode()), null))

				.map(body -> ApiResponse.of(String.valueOf(cr.rawStatusCode()), body)))
				.block();
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
					: objectMapper.writeValueAsString(RequestHeaders.of(requestHeadersMultiValueMap));

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
					.build();

			return apiRequestAudiRepository.save(apiRequestAudit);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to create ApiRequestAudit!")
					.appendParametersToMessage()
					.setParameter("Path", customHttpRequest.getFullPath())
					.setParameter("Method", customHttpRequest.getHttpMethodString());

		}
	}

	private void logResponse(
			@NonNull final ApiResponse apiResponse,
			@NonNull final ApiRequestAuditId apiRequestAuditId,
			@NonNull final OrgId orgId)
	{
		try
		{

			final String bodyAsString = apiResponse.getBody() != null
					? objectMapper.writeValueAsString(apiResponse.getBody())
					: null;

			final ApiResponseAudit apiResponseAudit = ApiResponseAudit.builder()
					.orgId(orgId)
					.apiRequestAuditId(apiRequestAuditId)
					.body(bodyAsString)
					.httpCode(apiResponse.getStatusCode())
					.time(Instant.now())
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

	private ApiAuditLoggable createLogger(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		return ApiAuditLoggable.builder()
				.clientId(Env.getClientId())
				.userId(Env.getLoggedUserId())
				.apiRequestAuditId(apiRequestAuditId)
				.apiAuditRequestLogDAO(apiAuditRequestLogDAO)
				.bufferSize(100)
				.build();
	}

	private void handleFutureCompletion(
			@Nullable final ApiResponse apiResponse,
			@Nullable final Throwable throwable,
			@NonNull final ApiAuditLoggable apiAuditLoggable,
			@NonNull final ApiRequestAuditId apiRequestAuditId,
			@NonNull final OrgId orgId)
	{
		try (final IAutoCloseable loggableRestorer = Loggables.temporarySetLoggable(apiAuditLoggable))
		{
			if (apiResponse != null)
			{
				logResponse(apiResponse, apiRequestAuditId, orgId);
			}
			else
			{
				Loggables.addLog("ERROR encounter inside completable future!", throwable);
			}
		}
	}

	private void handleSuccessfulResponse(
			@NonNull final ApiAuditConfig apiAuditConfig,
			@NonNull final ApiRequestAudit apiRequestAudit,
			@NonNull final CompletableFuture<ApiResponse> actualRestApiResponseCF,
			@NonNull final HttpServletResponse httpServletResponse) throws IOException, InterruptedException, ExecutionException, TimeoutException
	{
		final Object actualRestApiResponseBody;
		final int httpStatus;
		if (apiAuditConfig.isInvokerWaitsForResponse())
		{
			final ApiResponse actualRestApiResponse = actualRestApiResponseCF.get(120, TimeUnit.SECONDS); // todo establish a reasonable value
			actualRestApiResponseBody = actualRestApiResponse.getBody();
			httpStatus = Integer.parseInt(actualRestApiResponse.getStatusCode());
		}
		else
		{
			actualRestApiResponseBody = null;
			httpStatus = HttpStatus.ACCEPTED.value();
		}

		final JsonApiResponse apiResponse = JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(apiRequestAudit.getIdNotNull().getRepoId()))
				.endpointResponse(actualRestApiResponseBody)
				.build();

		httpServletResponse.resetBuffer();
		httpServletResponse.setStatus(httpStatus);
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(apiResponse));
		httpServletResponse.flushBuffer();
	}
}
