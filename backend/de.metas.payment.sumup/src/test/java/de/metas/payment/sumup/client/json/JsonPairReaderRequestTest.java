package de.metas.payment.sumup.client.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonPairReaderRequestTest
{
	@Test
	void testSerialize() throws JsonProcessingException
	{
		final JsonPairReaderRequest request = JsonPairReaderRequest.builder()
				.name("card reader 1")
				.pairing_code("24TXYYJUZ")
				.build();

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(request);
		assertThat(json).isEqualTo("{\"name\":\"card reader 1\",\"pairing_code\":\"24TXYYJUZ\"}");
	}
}