/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.CamelContext;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.properties.PropertiesComponent;

import javax.annotation.Nullable;

@UtilityClass
public class CamelRouteUtil
{
	@NonNull
	public JacksonDataFormat setupJacksonDataFormatFor(
			@NonNull final CamelContext context,
			@NonNull final Class<?> unmarshalType)
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(context);
		jacksonDataFormat.setObjectMapper(objectMapper);
		jacksonDataFormat.setUnmarshalType(unmarshalType);
		return jacksonDataFormat;
	}

	public void setupProperties(@NonNull final CamelContext context)
	{
		final PropertiesComponent pc = new PropertiesComponent();
		pc.setEncoding("UTF-8");
		pc.setLocation("classpath:application.properties");
		context.setPropertiesComponent(pc);
	}

	@NonNull
	public String resolveProperty(
			@NonNull final CamelContext context,
			@NonNull final String property,
			@Nullable final String defaultValue)
	{
		final var propertyOpt = context
				.getPropertiesComponent()
				.resolveProperty(property);

		if (defaultValue != null)
		{
			return propertyOpt.orElse(defaultValue);
		}
		else
		{
			return propertyOpt.orElseThrow(() -> new RuntimeCamelException("Missing property " + property));
		}
	}
}
