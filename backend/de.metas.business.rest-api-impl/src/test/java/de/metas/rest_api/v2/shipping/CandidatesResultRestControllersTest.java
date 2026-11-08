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
package de.metas.rest_api.v2.shipping;

import de.metas.util.web.AppServerLikeRestTestServer;
import de.metas.util.web.AppServerLikeRestTestServer.Response;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import lombok.NonNull;
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
 * Calls the {@code ...CandidatesResult} endpoints of {@link ShipmentCandidatesRestController} and {@link ReceiptCandidatesRestController}
 * through the app server's filter chain, with a synchronous API audit config like the default one shipped by migration.
 */
class CandidatesResultRestControllersTest
{
	private static final String PATH_SHIPMENT_CANDIDATES_RESULT = "/api/v2/shipments/shipmentCandidatesResult";
	private static final String PATH_RECEIPT_CANDIDATES_RESULT = "/api/v2/receipts/receiptCandidatesResult";
	private static final String REQUEST_BODY = "{\"transactionKey\":\"transactionKey1\",\"items\":[]}";

	private AppServerLikeRestTestServer server;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AppServerLikeRestTestServer.createSynchronousAuditConfig(null);

		server = AppServerLikeRestTestServer.start(
				ShipmentCandidatesRestController.class,
				ReceiptCandidatesRestController.class,
				MockedServicesConfig.class);
		// authentication is not what this test is about
		final UserAuthTokenFilterConfiguration authConfig = server.getBean(UserAuthTokenFilterConfiguration.class);
		authConfig.doNotAuthenticatePathsContaining(PATH_SHIPMENT_CANDIDATES_RESULT);
		authConfig.doNotAuthenticatePathsContaining(PATH_RECEIPT_CANDIDATES_RESULT);
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
	void shipmentCandidatesResult()
	{
		assertAcceptedAsPlainText(server.postJson(PATH_SHIPMENT_CANDIDATES_RESULT, "*/*", REQUEST_BODY), "ShipmentCandidates updated");
	}

	@Test
	void receiptCandidatesResult()
	{
		assertAcceptedAsPlainText(server.postJson(PATH_RECEIPT_CANDIDATES_RESULT, "*/*", REQUEST_BODY), "ReceiptCandidates updated");
	}

	private static void assertAcceptedAsPlainText(@NonNull final Response response, @NonNull final String expectedBody)
	{
		assertThat(response.getStatusCode()).as("response: %s", response).isEqualTo(202);
		assertThat(response.getBody()).isEqualTo(expectedBody);
		assertThat(response.isContentTypeCompatibleWith(MediaType.TEXT_PLAIN)).as("content type of response %s", response).isTrue();
		assertThat(AppServerLikeRestTestServer.countRequestAudits()).as("the request must have gone through the audit").isEqualTo(1);
	}

	@Configuration
	static class MockedServicesConfig
	{
		@Bean
		public ShipmentCandidateAPIService shipmentCandidateAPIService()
		{
			return mock(ShipmentCandidateAPIService.class);
		}

		@Bean
		public ReceiptCandidateAPIService receiptCandidateAPIService()
		{
			return mock(ReceiptCandidateAPIService.class);
		}
	}
}
