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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.Role;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.web.security.UserAuthTokenFilter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
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
import org.compiere.model.I_AD_User_AuthToken;
import org.compiere.util.Env;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static de.metas.util.web.MetasfreshRestAPIConstants.ENDPOINT_API_V2;
import static org.assertj.core.api.Assertions.*;

@UtilityClass
public class RESTUtil
{

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

	public APIResponse performHTTPRequest(final String endpointPath,
			final String verb,
			final String payload,
			final String authToken,
			@Nullable final Integer statusCode) throws IOException
	{
		final CloseableHttpClient httpClient = HttpClients.createDefault();

		final String appServerPort = System.getProperty("server.port");
		final String url = "http://localhost:" + appServerPort + "/" + endpointPath;
		final HttpRequestBase request;
		switch (verb)
		{
			case "POST":
			case "PUT":
				request = handleRequestWithEntity(verb, payload, authToken, url);
				break;
			case "GET":
			case "DELETE":
				request = handleRequestWithoutEntity(verb, authToken, url);
				break;
			default:
				throw new RuntimeException("Unsupported REST verb " + verb + " Supported are 'POST', 'PUT', 'GET', 'DELETE'");
		}

		final HttpResponse response = httpClient.execute(request);
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(CoalesceUtil.coalesce(statusCode, 200));

		final Header contentType = response.getEntity().getContentType();
		final APIResponse.APIResponseBuilder apiResponseBuilder = APIResponse.builder();
		if (contentType != null)
		{
			apiResponseBuilder.contentType(contentType.getValue());
		}

		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		response.getEntity().writeTo(stream);
		final String content;

		if (endpointPath != null && endpointPath.contains(ENDPOINT_API_V2.substring(1)))
		{
			final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

			final JsonApiResponse jsonApiResponse = objectMapper.readValue(stream.toString(StandardCharsets.UTF_8.name()), JsonApiResponse.class);

			content = objectMapper.writeValueAsString(jsonApiResponse.getEndpointResponse());

			apiResponseBuilder.requestId(jsonApiResponse.getRequestId());
		}
		else
		{
			content = stream.toString(StandardCharsets.UTF_8.name());
		}

		return apiResponseBuilder
				.content(content)
				.build();
	}

	private void setHeaders(@NonNull final HttpRequestBase request, @NonNull final String userAuthToken)
	{
		request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		request.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		request.addHeader(UserAuthTokenFilter.HEADER_Authorization, userAuthToken);
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
			final String verb,
			final String payload,
			final String authToken,
			final String url) throws UnsupportedEncodingException
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

		setHeaders(request, authToken);
		if (payload != null)
		{
			final StringEntity entity = new StringEntity(payload);
			request.setEntity(entity);
		}

		return request;
	}

	private HttpRequestBase handleRequestWithoutEntity(
			final String verb,
			final String authToken,
			final String url)
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

		setHeaders(request, authToken);

		return request;
	}
}
