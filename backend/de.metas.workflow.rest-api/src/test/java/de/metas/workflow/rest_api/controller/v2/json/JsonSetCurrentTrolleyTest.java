package de.metas.workflow.rest_api.controller.v2.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.scannable_code.ScannedCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

class JsonSetCurrentTrolleyTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserialize(JsonSetCurrentTrolley.builder()
				.scannedCode(ScannedCode.ofString(UUID.randomUUID().toString()))
				.build());
	}

	private void testSerializeDeserialize(final Object obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}

}