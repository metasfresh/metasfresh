/*
 * #%L
 * metasfresh-webui-api
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

import de.metas.ui.web.login.json.JSONLoginAuthRequest;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.Schema;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins that springdoc can build the webui-api's OpenAPI document ({@code /v3/api-docs}) on this module's classpath.
 * <p>
 * springdoc resolves every request/response type through swagger-core's {@link ModelConverters}, whose model resolver
 * needs the {@code jakarta.xml.bind.annotation} package (JAXB 3+ API). When only the javax-namespace JAXB 2.3 API is on
 * the classpath, the resolution dies with {@code NoClassDefFoundError: jakarta/xml/bind/annotation/XmlRootElement}
 * and {@code /v3/api-docs} answers HTTP 500, so swagger-ui shows no API.
 */
class OpenApiModelResolutionTest
{
	@Test
	void resolvesRestDtoSchema()
	{
		final Map<String, Schema> schemas = ModelConverters.getInstance().readAll(JSONLoginAuthRequest.class);

		assertThat(schemas).containsKey(JSONLoginAuthRequest.class.getSimpleName());
		assertThat(schemas.get(JSONLoginAuthRequest.class.getSimpleName()).getProperties())
				.containsKeys("type", "username", "password", "token");
	}
}
