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
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.compiere.model.I_AD_User_AuthToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

@UtilityClass
public class RESTUtil
{

	public String getAuthToken(@NonNull final String userLogin, @NonNull final String roleName) throws IOException
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
		InterfaceWrapperHelper.saveRecord(userAuthTokenRecord);

		return userAuthTokenRecord.getAuthToken();
	}

	public APIResponse performHTTPRequest(final String endpointPath,
			final String verb,
			final String payload, final String authToken) throws IOException
	{
		final CloseableHttpClient httpClient = HttpClients.createDefault();

		final StringEntity entity = new StringEntity(payload);

		final String appServerPort = System.getProperty("server.port");
		final String url = "http://localhost:" + appServerPort + "/" + endpointPath;

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
		request.setEntity(entity);

		final HttpResponse response = httpClient.execute(request);
		assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

		final Header contentType = response.getEntity().getContentType();
		final APIResponse.APIResponseBuilder apiResponseBuilder = APIResponse.builder();
		if (contentType != null)
		{
			apiResponseBuilder.contentType(contentType.getValue());
		}

		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		response.getEntity().writeTo(stream);
		final String content = stream.toString(StandardCharsets.UTF_8.name());

		return apiResponseBuilder
				.content(content)
				.build();
	}

	private void setHeaders(@NonNull final HttpEntityEnclosingRequestBase request, @NonNull final String userAuthToken)
	{
		request.addHeader("content-type", "application/json");
		request.addHeader(UserAuthTokenFilter.HEADER_Authorization, userAuthToken);
	}
}
