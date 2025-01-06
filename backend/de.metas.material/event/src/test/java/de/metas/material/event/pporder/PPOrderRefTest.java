package de.metas.material.event.pporder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PPOrderRefTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(PPOrderRef.builder()
				.ppOrderCandidateId(1)
				.ppOrderLineCandidateId(2)
				.ppOrderId(PPOrderId.ofRepoId(3))
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(4))
				.build());
	}

	void testSerializeDeserialize(final PPOrderRef obj) throws JsonProcessingException
	{
		System.out.println("Object: " + obj);
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("JSON: " + json);

		final PPOrderRef objDeserialized = jsonObjectMapper.readValue(json, PPOrderRef.class);
		System.out.println("Object (deserialized): " + objDeserialized);

		assertThat(objDeserialized).isEqualTo(obj);
	}

}