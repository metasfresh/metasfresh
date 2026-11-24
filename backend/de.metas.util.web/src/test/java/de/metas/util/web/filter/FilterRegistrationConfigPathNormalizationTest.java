/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.filter;

import de.metas.util.web.MetasfreshRestAPIConstants;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.metas.util.web.filter.PathNormalizationTestSupport.assertOK;
import static de.metas.util.web.filter.PathNormalizationTestSupport.mockMvcWithParameterlessFilters;
import static de.metas.util.web.filter.PathNormalizationTestSupport.tomcatLikeGet;

/**
 * Drives requests through the app server's own path-normalizing filters (every parameterless
 * {@code FilterRegistrationBean} bean of {@link FilterRegistrationConfig}) into a real handler mapping.
 * <p>
 * The requests are shaped like Tomcat delivers them behind a reverse proxy that maps {@code /app/...} to {@code //...},
 * e.g. {@code /app/api/...} to {@code /api//...} and {@code /app/info} to {@code //info}.
 */
class FilterRegistrationConfigPathNormalizationTest
{
	private MockMvc mockMvc;

	@RestController
	static class DummyRestController
	{
		@GetMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/version")
		public String version() {return "OK";}

		/**
		 * Like an actuator endpoint, which the app server exposes at the root ({@code management.endpoints.web.base-path=/}).
		 */
		@GetMapping("/info")
		public String info() {return "OK";}
	}

	@BeforeEach
	void beforeEach() throws Exception
	{
		AdempiereTestHelper.get().init();
		mockMvc = mockMvcWithParameterlessFilters(new DummyRestController(), new FilterRegistrationConfig());
	}

	@Test
	void plainPath()
	{
		assertOK(mockMvc, tomcatLikeGet("/api/v2/version", "/api/v2/version"));
	}

	@Test
	void trailingSlash()
	{
		assertOK(mockMvc, tomcatLikeGet("/api/v2/version/", "/api/v2/version/"));
	}

	@Test
	void doubleSlashAfterApi_asSentByReverseProxy()
	{
		assertOK(mockMvc, tomcatLikeGet("/api//v2/version", "/api/v2/version"));
	}

	@Test
	void multipleDuplicateSlashes()
	{
		assertOK(mockMvc, tomcatLikeGet("/api//v2///version", "/api/v2/version"));
	}

	@Test
	void doubleSlashAndTrailingSlash()
	{
		assertOK(mockMvc, tomcatLikeGet("/api//v2/version/", "/api/v2/version/"));
	}

	@Test
	void nonApiPath()
	{
		assertOK(mockMvc, tomcatLikeGet("/info", "/info"));
	}

	@Test
	void doubleSlashBeforeNonApiPath_asSentByReverseProxy()
	{
		assertOK(mockMvc, tomcatLikeGet("//info", "/info"));
	}
}
