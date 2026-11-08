/*
 * #%L
 * de.metas.manufacturing.rest-api
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
package de.metas.manufacturing.rest_api.v2;

import de.metas.util.web.AppServerLikeRestTestServer;
import de.metas.util.web.AppServerLikeRestTestServer.Response;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Calls {@code POST /api/v2/manufacturing/orders/exportStatus} through the app server's filter chain, with a synchronous API audit config like the default one shipped by migration.
 */
class ManufacturingOrderRestControllerTest
{
	private static final String PATH_EXPORT_STATUS = "/api/v2/manufacturing/orders/exportStatus";

	private AppServerLikeRestTestServer server;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AppServerLikeRestTestServer.createSynchronousAuditConfig(null);

		server = AppServerLikeRestTestServer.start(ManufacturingOrderRestController.class, MockedServicesConfig.class);
		// authentication is not what this test is about
		server.getBean(UserAuthTokenFilterConfiguration.class).doNotAuthenticatePathsContaining(PATH_EXPORT_STATUS);
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
	void exportStatus()
	{
		final Response response = server.postJson(PATH_EXPORT_STATUS, "*/*", "{\"transactionKey\":\"transactionKey1\",\"items\":[]}");

		assertThat(response.getStatusCode()).as("response: %s", response).isEqualTo(202);
		assertThat(response.getBody()).isEqualTo("Manufacturing orders updated");
		assertThat(response.isContentTypeCompatibleWith(MediaType.TEXT_PLAIN)).as("content type of response %s", response).isTrue();
		assertThat(AppServerLikeRestTestServer.countRequestAudits()).as("the request must have gone through the audit").isEqualTo(1);
	}

	@Configuration
	static class MockedServicesConfig
	{
		@Bean
		public ManufacturingOrderAPIService manufacturingOrderAPIService()
		{
			return mock(ManufacturingOrderAPIService.class);
		}
	}
}
