package de.metas.rest_api.v2.warehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.rest_api.v2.warehouse.json.JsonResolveLocatorRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonResolveLocatorRequestTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonResolveLocatorRequest request = JsonResolveLocatorRequest.builder()
				.scannedBarcode("LOC#1#{\"warehouseId\":1000001,\"locatorId\":1000002,\"caption\":\"locator1\"}")
				.build();

		final String json = objectMapper.writeValueAsString(request);
		final JsonResolveLocatorRequest deserialized = objectMapper.readValue(json, JsonResolveLocatorRequest.class);

		Assertions.assertThat(deserialized)
				.usingRecursiveComparison()
				.isEqualTo(request);
	}
}
