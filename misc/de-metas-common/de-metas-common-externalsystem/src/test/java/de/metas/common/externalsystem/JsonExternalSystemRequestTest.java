/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JsonExternalSystemRequestTest
{
	final ObjectMapper mapper = new ObjectMapper();

	@Test
	void serializeDeserialize() throws IOException
	{
		final JsonExternalSystemRequest request = JsonExternalSystemRequest.builder().externalSystemName(JsonExternalSystemName.of("externalSystem"))
				.orgCode("orgCode")
				.command("command")
				.parameter("parameterName1", "parameterValue1")
				.parameter("parameterName2", "parameterValue2")
				.build();

		final String valueAsString = mapper.writeValueAsString(request);

		final JsonExternalSystemRequest readValue = mapper.readValue(valueAsString, JsonExternalSystemRequest.class);

		assertThat(readValue).isEqualTo(request);
	}
}