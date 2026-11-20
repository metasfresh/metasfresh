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

package de.metas.ui.web;

import de.metas.util.web.LenientRestMessageConverterConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the wiring of {@link WebRestApiApplication}: it must import {@link LenientRestMessageConverterConfiguration}.
 * Without that import, Spring Boot's own converter uses the strict {@code @Primary} mapper, and REST request bodies with properties
 * the backend does not model (e.g. a price {@code currencyCode}, a mobile {@code title} test-step label) are rejected with 400.
 * <p>
 * What the converter does under Boot's MVC auto-configuration is pinned by {@code LenientRestMessageConverterConfigurationTest};
 * that no main code switches the auto-configuration off ({@code @EnableWebMvc}) is pinned by the ArchUnit rule
 * {@code MetasfreshArchRules.noCustomSpringMvcConfiguration()}.
 */
class WebRestApiApplicationWiringTest
{
	@Test
	void imports_LenientRestMessageConverterConfiguration()
	{
		final boolean imported = AnnotatedElementUtils.findAllMergedAnnotations(WebRestApiApplication.class, Import.class)
				.stream()
				.flatMap(importAnnotation -> Arrays.stream(importAnnotation.value()))
				.anyMatch(LenientRestMessageConverterConfiguration.class::equals);

		assertTrue(imported, "WebRestApiApplication must @Import(LenientRestMessageConverterConfiguration.class)");
	}
}
