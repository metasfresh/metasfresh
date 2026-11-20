/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static de.metas.util.web.filter.PathNormalizationTestSupport.assertOK;
import static de.metas.util.web.filter.PathNormalizationTestSupport.mockMvcWithParameterlessFilters;
import static de.metas.util.web.filter.PathNormalizationTestSupport.tomcatLikeGet;

/**
 * Drives requests through the webui-api's own path-normalizing filters (every parameterless
 * {@code FilterRegistrationBean} bean of {@link WebConfig}) into a real handler mapping.
 * <p>
 * The requests are shaped like Tomcat delivers them behind {@code frontend/docker/nginx/default.conf}'s
 * {@code location /rest { proxy_pass .../rest/; }}, which forwards {@code /rest/api/...} as {@code /rest//api/...}.
 */
class WebConfigPathNormalizationTest
{
	private MockMvc mockMvc;

	@RestController
	@RequestMapping(WebConfig.ENDPOINT_ROOT + "/login")
	static class DummyRestController
	{
		@GetMapping("/isLoggedIn")
		public String isLoggedIn() {return "OK";}
	}

	@BeforeEach
	void beforeEach() throws Exception
	{
		mockMvc = mockMvcWithParameterlessFilters(new DummyRestController(), new WebConfig());
	}

	@Test
	void plainPath()
	{
		assertOK(mockMvc, tomcatLikeGet("/rest/api/login/isLoggedIn", "/rest/api/login/isLoggedIn"));
	}

	@Test
	void trailingSlash()
	{
		assertOK(mockMvc, tomcatLikeGet("/rest/api/login/isLoggedIn/", "/rest/api/login/isLoggedIn/"));
	}

	@Test
	void doubleSlashAfterRest_asSentByFrontendNginx()
	{
		assertOK(mockMvc, tomcatLikeGet("/rest//api/login/isLoggedIn", "/rest/api/login/isLoggedIn"));
	}

	@Test
	void doubleSlashAndTrailingSlash()
	{
		assertOK(mockMvc, tomcatLikeGet("/rest//api/login/isLoggedIn/", "/rest/api/login/isLoggedIn/"));
	}
}
