/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class TestUtil
{
	public <T> T loadJSON(
			@NonNull final Object callingInstance,
			@NonNull final String resourceName,
			@NonNull final Class<T> clazz) throws IOException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final InputStream inputStream = callingInstance.getClass().getResourceAsStream(resourceName);
		Check.errorIf(inputStream == null, "Unable to get InputStream for resource {0}", resourceName);
		return mapper.readValue(inputStream, clazz);
	}
}
