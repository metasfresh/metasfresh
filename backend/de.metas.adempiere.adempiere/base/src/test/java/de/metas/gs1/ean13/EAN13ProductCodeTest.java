package de.metas.gs1.ean13;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EAN13ProductCodeTest
{
	@Test
	public void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(EAN13ProductCode.ofString("123412345"));
	}

	private static void testSerializeDeserialize(final EAN13ProductCode ean13ProductCode) throws JsonProcessingException
	{
		System.out.println("EAN13ProductCode: " + ean13ProductCode);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(ean13ProductCode);
		System.out.println("JSON: " + json);

		final EAN13ProductCode ean13ProductCodeDeserialized = jsonObjectMapper.readValue(json, EAN13ProductCode.class);
		System.out.println("EAN13ProductCode deserialized: " + ean13ProductCodeDeserialized);

		assertThat(ean13ProductCodeDeserialized).isEqualTo(ean13ProductCode);
	}

}