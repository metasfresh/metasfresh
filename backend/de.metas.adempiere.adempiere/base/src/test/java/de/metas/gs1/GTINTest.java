package de.metas.gs1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GTINTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(GTIN.ofString("97311876341811"));
	}

	private void testSerializeDeserialize(@NonNull GTIN gtin) throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(gtin);
		assertThat(json).isEqualTo("\"" + gtin.getAsString() + "\"");

		final GTIN gtin2 = jsonObjectMapper.readValue(json, GTIN.class);
		assertThat(gtin2).usingRecursiveComparison().isEqualTo(gtin);
		assertThat(gtin2).isEqualTo(gtin);
	}

}