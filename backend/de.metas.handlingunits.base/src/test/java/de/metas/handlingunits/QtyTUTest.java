package de.metas.handlingunits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class QtyTUTest
{
	@Test
	void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserialize(QtyTU.ofInt(5));
	}

	@Test
	void testDeserializeFromString() throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = "{ \"value\": \"5\" }";
		// System.out.println("JSON: " + json);
		// System.out.println("AAAA: " + jsonObjectMapper.writeValueAsString(QtyTUContainer.builder().value(QtyTU.ofInt(5)).build()));

		final QtyTUContainer objDeserialized = jsonObjectMapper.readValue(json, QtyTUContainer.class);
		Assertions.assertThat(objDeserialized).isEqualTo(QtyTUContainer.builder()
				.value(QtyTU.ofInt(5))
				.build());
	}

	public void testSerializeDeserialize(final Object obj) throws IOException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(obj);
		final Object objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class QtyTUContainer
	{
		@NonNull QtyTU value;
	}
}