/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.apirequest.request.ApiRequestAuditId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.error.AdIssueId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.web.security.UserAuthTokenFilter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_User_AuthToken;
import org.compiere.model.I_API_Request_Audit;
import org.compiere.model.I_API_Request_Audit_Log;
import org.compiere.model.I_API_Response_Audit;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static de.metas.util.web.MetasfreshRestAPIConstants.ENDPOINT_API_V2;
import static de.metas.util.web.audit.ApiAuditService.API_RESPONSE_HEADER_REQUEST_AUDIT_ID;
import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class RESTUtil
{
	private static final Logger logger = LogManager.getLogger(RESTUtil.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public String getAuthToken(@NonNull final String userLogin, @NonNull final String roleName)
	{
		final IUserDAO userDAO = Services.get(IUserDAO.class);
		final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

		final UserId userId = userDAO.retrieveUserIdByLogin(userLogin);
		if (userId == null)
		{
			throw new AdempiereException("Missing AD_User with login " + userLogin);
		}
		final Role role = roleDAO
				.getUserRoles(userId)
				.stream()
				.filter(r -> r.getName().equals(roleName))
				.findAny()
				.orElseThrow(() -> new AdempiereException("User with login=" + userLogin + " and AD_User_ID=" + userId.getRepoId() + " has no role with name " + roleName));

		final I_AD_User_AuthToken userAuthTokenRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_User_AuthToken.class);
		userAuthTokenRecord.setAD_User_ID(userId.getRepoId());
		userAuthTokenRecord.setAD_Role_ID(role.getId().getRepoId());
		userAuthTokenRecord.setAD_Org_ID(1000000);
		InterfaceWrapperHelper.saveRecord(userAuthTokenRecord);

		Env.setLoggedUserId(Env.getCtx(), userId);
		Env.setOrgId(Env.getCtx(), OrgId.ofRepoId(1000000));

		return userAuthTokenRecord.getAuthToken();
	}

	public APIResponse performHTTPRequest(@NonNull final APIRequest apiRequest) throws IOException
	{
		final HttpRequestBase httpRequest = createHttpRequest(apiRequest);

		try (final CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			final HttpResponse httpResponse = httpClient.execute(httpRequest);
			final APIResponse apiResponse = extractAPIResponse(httpResponse, apiRequest);
			validateAPIResponse(apiResponse, apiRequest);
			return apiResponse;
		}
	}

	private static HttpRequestBase createHttpRequest(@NonNull final APIRequest apiRequest)
	{
		final String appServerPort = System.getProperty("server.port");
		final String url = "http://localhost:" + appServerPort + "/" + apiRequest.getEndpointPath();
		final String method = apiRequest.getMethod();
		final String authToken = apiRequest.getAuthToken();

		try
		{
			final HttpRequestBase httpRequest;
			switch (method)
			{
				case "POST":
				case "PUT":
					httpRequest = handleRequestWithEntity(url, method, apiRequest.getPayload());
					break;
				case "GET":
				case "DELETE":
					httpRequest = handleRequestWithoutEntity(url, method);
					break;
				default:
					throw new RuntimeException("Unsupported REST method " + method + " Supported are 'POST', 'PUT', 'GET', 'DELETE'");
			}

			setHeaders(httpRequest, authToken, apiRequest.getAdditionalHeaders());

			return httpRequest;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed creating HTTP request from " + apiRequest, e)
					.setParameter("url", url);
		}
	}

	private static APIResponse extractAPIResponse(final HttpResponse httpResponse, final APIRequest apiRequest)
	{
		try
		{
			final APIResponse.APIResponseBuilder apiResponseBuilder = APIResponse.builder();

			final int actualStatusCode = httpResponse.getStatusLine().getStatusCode();
			apiResponseBuilder.statusCode(actualStatusCode);

			final Header contentType = httpResponse.getEntity().getContentType();
			if (contentType != null)
			{
				apiResponseBuilder.contentType(contentType.getValue());
			}

			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
			httpResponse.getEntity().writeTo(stream);

			final String endpointPath = apiRequest.getEndpointPath();

			if (endpointPath.contains(ENDPOINT_API_V2.substring(1)))
			{
				final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

				try
				{
					final JsonApiResponse jsonApiResponse = objectMapper.readValue(stream.toString(StandardCharsets.UTF_8), JsonApiResponse.class);

					final String content = objectMapper.writeValueAsString(jsonApiResponse.getEndpointResponse());
					apiResponseBuilder.content(content);

					final ApiRequestAuditId requestId = jsonApiResponse.getRequestId() != null ? ApiRequestAuditId.ofRepoId(jsonApiResponse.getRequestId().getValue()) : null;
					apiResponseBuilder.requestId(requestId);

					if (requestId != null)
					{
						logDetails(requestId);
					}
				}
				catch (final MismatchedInputException mismatchedInputException)
				{
					apiResponseBuilder.content(stream.toString(StandardCharsets.UTF_8.name()));

					final ApiRequestAuditId requestId = extractRequestAuditIdFromHeader(httpResponse);
					if (requestId != null)
					{
						apiResponseBuilder.requestId(requestId);
						logDetails(requestId);
					}

				}
				catch (final JsonParseException jsonParseException)
				{
					apiResponseBuilder.content(stream.toString(StandardCharsets.UTF_8));
				}
			}
			else
			{
				apiResponseBuilder.content(stream.toString(StandardCharsets.UTF_8));
			}

			return apiResponseBuilder.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed extracting API response from " + httpResponse, ex);
		}
	}

	private static void validateAPIResponse(final APIResponse apiResponse, final APIRequest apiRequest) throws JsonProcessingException
	{
		final Integer expectedStatusCode = apiRequest.getExpectedStatusCode();
		if (expectedStatusCode != null)
		{
			final Integer actualStatusCode = apiResponse.getStatusCode();
			assertThat(actualStatusCode)
					.withFailMessage(() -> MessageFormat.format("HTTP Response status code did not match! expected: {0}, actual: {1} ! See full httpResponse:\n{2}",
							expectedStatusCode,
							actualStatusCode,
							apiResponse.getContent()))
					.isEqualTo(expectedStatusCode);
		}

		final String expectErrorContaining = apiRequest.getExpectedErrorMessageContaining();
		final Boolean expectErrorUserFriendly = apiRequest.getExpectErrorUserFriendly();
		final boolean isExpectError = expectErrorContaining != null || expectErrorUserFriendly != null;
		if (isExpectError)
		{
			final JsonError jsonError = apiResponse.getContentAs(JsonError.class);
			final JsonErrorItem jsonErrorItem = CollectionUtils.singleElement(jsonError.getErrors());

			if (expectErrorContaining != null)
			{
				assertThat(jsonErrorItem.getMessage())
						.as(() -> "Error Message of " + jsonError)
						.contains(expectErrorContaining);
			}
			if (expectErrorUserFriendly != null)
			{
				assertThat(jsonErrorItem.isUserFriendlyError())
						.as(() -> "UserFriendlyError of " + jsonError)
						.isEqualTo(expectErrorUserFriendly);
			}
		}
	}

	private static void setHeaders(
			@NonNull final HttpRequestBase request,
			@NonNull final String userAuthToken,
			@Nullable final Map<String, String> additionalHeaders)
	{
		request.addHeader(UserAuthTokenFilter.HEADER_Authorization, userAuthToken);

		if (additionalHeaders == null)
		{
			request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		}
		else
		{
			request.addHeader(HttpHeaders.CONTENT_TYPE,
					CoalesceUtil.coalesceNotNull(additionalHeaders.get(HttpHeaders.CONTENT_TYPE), MediaType.APPLICATION_JSON_VALUE));
			request.addHeader(HttpHeaders.ACCEPT,
					CoalesceUtil.coalesceNotNull(additionalHeaders.get(HttpHeaders.ACCEPT), MediaType.APPLICATION_JSON_VALUE));

			additionalHeaders.forEach(request::addHeader);
		}
	}

	@Nullable
	public static SyncAdvise mapSyncAdvise(@NonNull final String syncAdvise)
	{
		if (EmptyUtil.isBlank(syncAdvise))
		{
			return null;
		}

		switch (syncAdvise)
		{
			case "CREATE_OR_MERGE":
				return SyncAdvise.CREATE_OR_MERGE;
			case "JUST_CREATE_IF_NOT_EXISTS":
				return SyncAdvise.JUST_CREATE_IF_NOT_EXISTS;
			case "READ_ONLY":
				return SyncAdvise.READ_ONLY;
			default:
				throw new AdempiereException("Invalid SyncAdvise: " + syncAdvise);
		}
	}

	@Nullable
	public static de.metas.common.rest_api.v1.SyncAdvise mapSyncAdviseV1(@NonNull final String syncAdvise)
	{
		if (EmptyUtil.isBlank(syncAdvise))
		{
			return null;
		}

		switch (syncAdvise)
		{
			case "CREATE_OR_MERGE":
				return de.metas.common.rest_api.v1.SyncAdvise.CREATE_OR_MERGE;
			case "JUST_CREATE_IF_NOT_EXISTS":
				return de.metas.common.rest_api.v1.SyncAdvise.JUST_CREATE_IF_NOT_EXISTS;
			case "READ_ONLY":
				return de.metas.common.rest_api.v1.SyncAdvise.READ_ONLY;
			default:
				throw new AdempiereException("Invalid SyncAdvise: " + syncAdvise);
		}
	}

	private HttpRequestBase handleRequestWithEntity(
			@NonNull final String url,
			@NonNull final String verb,
			@Nullable final String payload) throws UnsupportedEncodingException
	{
		final HttpEntityEnclosingRequestBase request;
		switch (verb)
		{
			case "POST":
				request = new HttpPost(url);
				break;
			case "PUT":
				request = new HttpPut(url);
				break;
			default:
				throw new RuntimeException("Unsupported REST verb " + verb + " Supported are 'POST' and 'PUT'");
		}

		if (payload != null)
		{
			final StringEntity entity = new StringEntity(payload);
			request.setEntity(entity);
		}

		return request;
	}

	private HttpRequestBase handleRequestWithoutEntity(
			@NonNull final String url,
			@NonNull final String verb)
	{
		final HttpRequestBase request;
		switch (verb)
		{
			case "GET":
				request = new HttpGet(url);
				break;
			case "DELETE":
				request = new HttpDelete(url);
				break;
			default:
				throw new RuntimeException("Unsupported REST verb " + verb + " Supported are 'GET' and 'DELETE'");
		}

		return request;
	}

	private void logDetails(@NonNull final ApiRequestAuditId apiRequestAuditId)
	{
		final I_API_Request_Audit apiRequestAuditRecord = InterfaceWrapperHelper.load(apiRequestAuditId, I_API_Request_Audit.class);

		logger.info("*** API_Request_Audit_ID : {}\n Path ->  {}\n RequestHttpHeaders -> {}\n RequestBody -> {}",
				apiRequestAuditId.getRepoId(), apiRequestAuditRecord.getPath(), apiRequestAuditRecord.getHttpHeaders(), apiRequestAuditRecord.getBody());

		getAPIResponseAudit(apiRequestAuditId)
				.forEach(auditResponse -> logger.info("*** API_Request_Audit_ID : {} - API_Response_Audit_ID -> {}\n ResponseHttpCode -> {}\n ResponseHttpHeaders ->  {}\n ResponseBody ->  {}",
						apiRequestAuditId.getRepoId(), auditResponse.getAPI_Response_Audit_ID(), auditResponse.getHttpCode(), auditResponse.getHttpHeaders(), auditResponse.getBody()));

		final ImmutableList<I_API_Request_Audit_Log> apiReqLogs = getApiRequestAuditLogs(apiRequestAuditId);
		apiReqLogs.forEach(log -> {
			if (EmptyUtil.isNotBlank(log.getLogmessage()))
			{
				logger.info("*** API_Request_Audit_ID : {} - API_Request_Audit_Log_ID -> {}\n Log message -> {}",
						apiRequestAuditId.getRepoId(), log.getAPI_Request_Audit_Log_ID(), log.getLogmessage());
			}
		});

		getAdIssues(apiReqLogs)
				.forEach(issue -> logger.info("*** API_Request_Audit_ID : {} - AD_Issue_ID -> {} \n IssueSummary -> {}\n StackTrace -> {}",
						apiRequestAuditId.getRepoId(), issue.getAD_Issue_ID(), issue.getIssueSummary(), issue.getStackTrace()));
	}

	private static Stream<I_AD_Issue> getAdIssues(final Collection<I_API_Request_Audit_Log> apiReqLogs)
	{
		final ImmutableSet<AdIssueId> issueIds = extractAdIssueIds(apiReqLogs);
		if (issueIds.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_AD_Issue.class)
				.addInArrayFilter(I_AD_Issue.COLUMNNAME_AD_Issue_ID, issueIds)
				.orderBy(I_AD_Issue.COLUMNNAME_AD_Issue_ID)
				.create()
				.stream();
	}

	private static ImmutableSet<AdIssueId> extractAdIssueIds(final Collection<I_API_Request_Audit_Log> apiReqLogs)
	{
		return apiReqLogs.stream()
				.map(o -> AdIssueId.ofRepoIdOrNull(o.getAD_Issue_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableList<I_API_Request_Audit_Log> getApiRequestAuditLogs(final ApiRequestAuditId apiRequestAuditId)
	{
		return queryBL.createQueryBuilder(I_API_Request_Audit_Log.class)
				.addEqualsFilter(I_API_Request_Audit_Log.COLUMNNAME_API_Request_Audit_ID, apiRequestAuditId)
				.create()
				.listImmutable(I_API_Request_Audit_Log.class);
	}

	private static Stream<I_API_Response_Audit> getAPIResponseAudit(final ApiRequestAuditId apiRequestAuditId)
	{
		return queryBL.createQueryBuilder(I_API_Response_Audit.class)
				.addEqualsFilter(I_API_Response_Audit.COLUMNNAME_API_Request_Audit_ID, apiRequestAuditId)
				.create()
				.stream();
	}

	@Nullable
	private ApiRequestAuditId extractRequestAuditIdFromHeader(@NonNull final HttpResponse response)
	{
		final Header requestIdParam = response.getFirstHeader(API_RESPONSE_HEADER_REQUEST_AUDIT_ID);
		if (requestIdParam == null)
		{
			return null;
		}

		return ApiRequestAuditId.ofRepoId(Integer.parseInt(requestIdParam.getValue()));
	}
}
