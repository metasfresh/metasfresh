package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonGetHUInfoByScannedCodeRequestTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonGetHUInfoByScannedCodeRequest request = JsonGetHUInfoByScannedCodeRequest.builder()
				.scannedCode("HU#1#{\"id\":\"53c5f490\",\"product\":{\"name\":\"Brlauchkse \\\"Bio VK\\\" Laib\"},\"attributes\":[{\"code\":\"HU_BestBeforeDate\",\"value\":\"2026-05-05\"}]}")
				.build();

		final String json = objectMapper.writeValueAsString(request);
		final JsonGetHUInfoByScannedCodeRequest deserialized = objectMapper.readValue(json, JsonGetHUInfoByScannedCodeRequest.class);

		Assertions.assertThat(deserialized)
				.usingRecursiveComparison()
				.isEqualTo(request);
	}
}
