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
package de.metas.rest_api.v2.pricing;

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

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Calls the {@code product/search} endpoint of {@link PricesRestController} through the app server's filter chain,
 * with a synchronous API audit config like the default one shipped by migration.
 * The 422 error response carries the plain-text exception message; its content type must say so.
 */
class PricesRestControllerTest
{
	private static final String PATH = "/api/v2/prices/001/product/search";
	private static final String REQUEST_BODY = "{\"bpartnerIdentifier\":\"val-1\",\"productIdentifier\":\"val-2\",\"targetDate\":\"2022-05-10\"}";
	/**
	 * Non-ASCII on purpose: the exception message may contain e.g. German product names, and must reach the client unchanged.
	 */
	private static final String ERROR_MESSAGE = "No price found for product Größe ä ö ü €";

	private AppServerLikeRestTestServer server;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AppServerLikeRestTestServer.createSynchronousAuditConfig(null);

		server = AppServerLikeRestTestServer.start(PricesRestController.class, MockedServicesConfig.class);
		server.getBean(UserAuthTokenFilterConfiguration.class).doNotAuthenticatePathsContaining(PATH);
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
	void productPriceSearch_failure_isReturnedAs422PlainText()
	{
		final Response response = server.postJson(PATH, "*/*", REQUEST_BODY);

		assertThat(response.getStatusCode()).as("response: %s", response).isEqualTo(422);
		assertThat(response.getBodyBytes()).as("body bytes of response %s", response).isEqualTo(ERROR_MESSAGE.getBytes(StandardCharsets.UTF_8));
		assertThat(response.getMediaType()).as("content type of response %s", response).isEqualTo(MediaType.parseMediaType("text/plain;charset=UTF-8"));
		assertThat(AppServerLikeRestTestServer.countRequestAudits()).as("the request must have gone through the audit").isEqualTo(1);
	}

	@Configuration
	static class MockedServicesConfig
	{
		@Bean
		public PriceListRestService priceListRestService()
		{
			return mock(PriceListRestService.class);
		}

		@Bean
		public ProductPriceRestService productPriceRestService()
		{
			final ProductPriceRestService service = mock(ProductPriceRestService.class);
			when(service.productPriceSearch(any(), anyString())).thenThrow(new RuntimeException(ERROR_MESSAGE));
			return service;
		}
	}
}
