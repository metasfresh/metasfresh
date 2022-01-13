/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.to_mf.v2.api.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

	@Test
	public void givenBPRetrieveCamelRequest_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockBPRetrieveCamelRequest());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private BPRetrieveCamelRequest getMockBPRetrieveCamelRequest()
	{
		return BPRetrieveCamelRequest.builder()
				.bPartnerIdentifier("bpIdentifier")
				.externalSystemConfigId(JsonMetasfreshId.of(1))
				.adPInstanceId(JsonMetasfreshId.of(2))
				.build();
	}
}
