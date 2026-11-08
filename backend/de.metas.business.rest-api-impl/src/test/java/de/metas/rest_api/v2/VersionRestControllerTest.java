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
package de.metas.rest_api.v2;

import de.metas.util.Services;
import de.metas.util.web.AppServerLikeRestTestServer;
import de.metas.util.web.AppServerLikeRestTestServer.Response;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_System;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Calls {@code GET /api/v2/version} through the app server's filter chain, with a synchronous API audit config like the default one shipped by migration.
 */
class VersionRestControllerTest
{
	private static final String DB_VERSION = "5.175.4-test-release.353";

	private AppServerLikeRestTestServer server;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// AdempiereTestHelper already created the one AD_System record
		final I_AD_System system = Services.get(IQueryBL.class).createQueryBuilder(I_AD_System.class).create().firstOnlyNotNull(I_AD_System.class);
		system.setDBVersion(DB_VERSION);
		saveRecord(system);

		AppServerLikeRestTestServer.createSynchronousAuditConfig("GET");

		server = AppServerLikeRestTestServer.start(VersionRestController.class);
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

	/**
	 * What curl sends.
	 */
	@Test
	void acceptAll()
	{
		assertVersionAsPlainText(server.get(VersionRestController.ENDPOINT, "*/*"));
		assertThat(AppServerLikeRestTestServer.countRequestAudits()).as("the request must have gone through the audit").isEqualTo(1);
	}

	@Test
	void noAcceptHeader()
	{
		assertVersionAsPlainText(server.get(VersionRestController.ENDPOINT, null));
	}

	/**
	 * A client that asks for JSON keeps getting the version (no 406).
	 */
	@Test
	void acceptJson()
	{
		assertVersionAsPlainText(server.get(VersionRestController.ENDPOINT, MediaType.APPLICATION_JSON_VALUE));
	}

	private static void assertVersionAsPlainText(@NonNull final Response response)
	{
		assertThat(response.getStatusCode()).as("response: %s", response).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(DB_VERSION);
		assertThat(response.isContentTypeCompatibleWith(MediaType.TEXT_PLAIN)).as("content type of response %s", response).isTrue();
	}
}
