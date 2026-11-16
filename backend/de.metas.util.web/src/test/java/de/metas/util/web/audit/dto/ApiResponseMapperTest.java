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

package de.metas.util.web.audit.dto;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiResponseMapperTest
{
	/**
	 * The body bytes are decoded with the charset the response declares, not with the JVM's default charset.
	 */
	@Test
	void plainTextBody_isDecodedWithResponseCharset() throws IOException
	{
		final String text = "Größe ä ö ü";

		final HttpServletResponse servletResponse = mock(HttpServletResponse.class);
		when(servletResponse.getStatus()).thenReturn(200);
		when(servletResponse.getContentType()).thenReturn("text/plain;charset=ISO-8859-1");
		when(servletResponse.getCharacterEncoding()).thenReturn("ISO-8859-1");

		final ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(servletResponse);
		responseWrapper.getOutputStream().write(text.getBytes(StandardCharsets.ISO_8859_1));

		final ApiResponse apiResponse = ApiResponseMapper.map(responseWrapper);

		assertThat(apiResponse.getBody()).isEqualTo(text);
		assertThat(apiResponse.getCharset()).isEqualTo(StandardCharsets.ISO_8859_1);
	}
}
