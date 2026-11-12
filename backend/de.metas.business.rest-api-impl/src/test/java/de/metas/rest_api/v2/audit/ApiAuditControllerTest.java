/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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
package de.metas.rest_api.v2.audit;

import de.metas.util.web.AppServerLikeRestTestServer;
import de.metas.util.web.AppServerLikeRestTestServer.Response;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Calls {@code GET /api/v2/audit/requests/{requestId}/response} through the app server's filter chain, with a synchronous API audit config like the default one shipped by migration.
 * The 500 error response carries a plain-text message; its content type must say so.
 */
class ApiAuditControllerTest
{
	private static final String PATH_AUDIT_REQUESTS = "/api/v2/audit/requests/";

	private AppServerLikeRestTestServer server;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AppServerLikeRestTestServer.createSynchronousAuditConfig("GET");

		server = AppServerLikeRestTestServer.start(ApiAuditController.class);
		// authentication is not what this test is about
		server.getBean(UserAuthTokenFilterConfiguration.class).doNotAuthenticatePathsContaining(PATH_AUDIT_REQUESTS);
	}

	@AfterEach
	void afterEach()
	{
		if (server != null)
		{
			server.close();
			server = null;
		}
	}

	@Test
	void mimicOriginalResponse_invalidRequestId_isReturnedAs500PlainText()
	{
		final Response response = server.get(PATH_AUDIT_REQUESTS + "abc/response", MediaType.APPLICATION_JSON_VALUE);

		assertThat(response.getStatusCode()).as("response: %s", response).isEqualTo(500);
		assertThat(response.getBody()).isEqualTo("Error while mimicking original response: For input string: \"abc\"");
		assertThat(response.getMediaType()).as("content type of response %s", response).isEqualTo(MediaType.parseMediaType("text/plain;charset=UTF-8"));
		assertThat(AppServerLikeRestTestServer.countRequestAudits()).as("the request must have gone through the audit").isEqualTo(1);
	}
}
