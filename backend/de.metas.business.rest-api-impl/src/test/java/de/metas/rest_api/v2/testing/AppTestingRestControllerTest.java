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
package de.metas.rest_api.v2.testing;

import de.metas.notification.INotificationBL;
import de.metas.util.Services;
import de.metas.util.web.AppServerLikeRestTestServer;
import de.metas.util.web.AppServerLikeRestTestServer.Response;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Calls {@code GET /api/v2/test/ping/notifications} through the app server's filter chain, with a synchronous API audit config like the default one shipped by migration.
 */
class AppTestingRestControllerTest
{
	private static final String PATH_PING_NOTIFICATIONS = "/api/v2/test/ping/notifications";

	private AppServerLikeRestTestServer server;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(INotificationBL.class, mock(INotificationBL.class));
		AppServerLikeRestTestServer.createSynchronousAuditConfig("GET");

		server = AppServerLikeRestTestServer.start(AppTestingRestController.class);
		// authentication is not what this test is about
		server.getBean(UserAuthTokenFilterConfiguration.class).doNotAuthenticatePathsContaining(PATH_PING_NOTIFICATIONS);
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
	void pingNotifications()
	{
		final Response response = server.get(PATH_PING_NOTIFICATIONS + "?noEmail=true", "*/*");

		assertThat(response.getStatusCode()).as("response: %s", response).isEqualTo(200);
		assertThat(response.getBody()).startsWith("sent: ");
		assertThat(response.isContentTypeCompatibleWith(MediaType.TEXT_PLAIN)).as("content type of response %s", response).isTrue();
		assertThat(AppServerLikeRestTestServer.countRequestAudits()).as("the request must have gone through the audit").isEqualTo(1);
	}
}
