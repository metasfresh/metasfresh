/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jackson.JacksonDataFormat;

/**
 * @deprecated please use (and extend if needed) {@link de.metas.camel.externalsystems.common.CamelRouteUtil}.
 */
@Deprecated
public class CamelRouteUtil
{
	@NonNull
	public static JacksonDataFormat setupJacksonDataFormatFor(
			@NonNull final CamelContext context,
			@NonNull final Class<?> unmarshalType)
	{
		final ObjectMapper objectMapper = (new ObjectMapper())
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);

		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(context);
		jacksonDataFormat.setObjectMapper(objectMapper);
		jacksonDataFormat.setUnmarshalType(unmarshalType);
		return jacksonDataFormat;
	}
}
