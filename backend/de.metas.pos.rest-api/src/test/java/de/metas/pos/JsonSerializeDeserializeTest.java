package de.metas.pos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.pos.websocket.json.JsonPOSOrderChangedWebSocketEvent;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JsonSerializeDeserializeTest
{
	<T> void testSerializeDeserialize(@NonNull final T obj) throws JsonProcessingException
	{
		//noinspection unchecked
		final Class<T> type = (Class<T>)obj.getClass();

		System.out.println("  obj: " + obj);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println(" json: " + json);
		final T obj2 = jsonObjectMapper.readValue(json, type);
		System.out.println(" obj2: " + obj2);

		assertThat(obj2).usingRecursiveComparison().isEqualTo(obj);
		assertThat(obj2).isEqualTo(obj);
	}

	@Test
	void test_JsonPOSOrderChangedWebSocketEvent() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonPOSOrderChangedWebSocketEvent.builder()
				.posOrderId(POSOrderExternalId.ofString(UUID.randomUUID().toString()))
				.build());
	}

}