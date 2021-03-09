/*
 * #%L
 * de-metas-common-externalreference
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

package de.metas.common.externalreference;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.JsonMetasfreshId;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SerializeDeserializeTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	void jsonExternalReferenceLookupRequest() throws Exception
	{
		final JsonExternalReferenceLookupRequest jsonExternalReferenceLookupRequest = JsonExternalReferenceLookupRequest.builder()
				.systemName(JsonExternalSystemName.of("systemName"))
				.item(JsonExternalReferenceLookupItem.builder()
						.id("item1Id")
						.type("item1Type").build())
				.item(JsonExternalReferenceLookupItem.builder()
						.id("item2Id")
						.type("item2Type").build())
				.build();
		testSerializeDeserialize(jsonExternalReferenceLookupRequest, JsonExternalReferenceLookupRequest.class);
	}

	@Test
	void jsonExternalReferenceLookupRequest_fromString() throws IOException
	{
		String jsonString = "{\n"
				+ "  \"systemName\": \"Github\",\n"
				+ "  \"items\": [\n"
				+ "    { \"id\": \"noIssueWithThisId\", \"type\": \"IssueID\" }\n"
				+ "  ]\n"
				+ "}";

		final JsonExternalReferenceLookupRequest jsonDeserialized = jsonObjectMapper.readValue(jsonString, JsonExternalReferenceLookupRequest.class);
		assertThat(jsonDeserialized).isNotNull();
	}

	@Test
	void jsonExternalReferenceCreateRequest() throws Exception
	{
		final JsonExternalReferenceCreateRequest build = JsonExternalReferenceCreateRequest.builder()
				.systemName(JsonExternalSystemName.of("systemName"))
				.item(JsonExternalReferenceItem.of(
						JsonExternalReferenceLookupItem.builder().id("item1Id").type("item1Type").build(),
						JsonMetasfreshId.of(24)))
				.item(JsonExternalReferenceItem.of(
						JsonExternalReferenceLookupItem.builder().id("item2Id").type("item2Type").build(),
						JsonMetasfreshId.of(25)))
				.build();
		testSerializeDeserialize(build, JsonExternalReferenceCreateRequest.class);
	}

	@Test
	void jsonExternalReferenceCreateRequest_fromString() throws IOException
	{
		final String jsonString = "{\n"
				+ "  \"systemName\": \"Github\",\n"
				+ "  \"items\": [\n"
				+ "    { \"lookupItem\": { \"id\": \"existingId\", \"type\": \"IssueID\" }, \"metasfreshId\": 43 }\n"
				+ "  ]\n"
				+ "}";

		final JsonExternalReferenceCreateRequest jsonDeserialized = jsonObjectMapper.readValue(jsonString, JsonExternalReferenceCreateRequest.class);
		assertThat(jsonDeserialized).isNotNull();
	}

	@Test
	public void jsonExternalReferenceLookupResponse() throws Exception
	{
		final JsonExternalReferenceLookupResponse jsonExternalReferenceLookupResponse = JsonExternalReferenceLookupResponse.builder()
				.item(JsonExternalReferenceItem.of(JsonExternalReferenceLookupItem.builder().type("item1Type").id("item1Id").build()))
				.item(JsonExternalReferenceItem.of(
						JsonExternalReferenceLookupItem.builder().type("item2Type").id("item2Id").build(),
						JsonMetasfreshId.of(23)))
				.build();
		testSerializeDeserialize(jsonExternalReferenceLookupResponse, JsonExternalReferenceLookupResponse.class);
	}

	private <T> void testSerializeDeserialize(@NonNull final T json, Class<T> clazz) throws IOException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(json);
		final T jsonDeserialized = jsonObjectMapper.readValue(jsonString, clazz);
		assertThat(jsonDeserialized).isEqualTo(json);
	}

}
