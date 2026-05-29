package de.metas.ui.web.window.health.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import org.adempiere.ad.element.api.AdWindowId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonWindowHealthCheckRequestTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonWindowHealthCheckRequest json = JsonWindowHealthCheckRequest.builder()
				.onlyAdWindowIds(ImmutableSet.of(AdWindowId.ofRepoId(1), AdWindowId.ofRepoId(2)))
				.knownContextVariables(ImmutableSet.of("ctxVar1", "ctxVar2"))
				.knownMissingContextVariables(ImmutableMap.<String, String>builder()
						.put("path1", "ctxVar1,ctxVar2")
						.put("path2", "ctxVar1,ctxVar2")
						.build())
				.build();
		System.out.println("JSON: " + json);

		final String str = jsonObjectMapper.writeValueAsString(json);
		System.out.println("JSON string: " + str);

		final JsonWindowHealthCheckRequest json2 = jsonObjectMapper.readValue(str, JsonWindowHealthCheckRequest.class);
		System.out.println("JSON2: " + json2);

		assertThat(json2).usingRecursiveComparison().isEqualTo(json);
		assertThat(json2).isEqualTo(json);
	}
}