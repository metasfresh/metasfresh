package de.metas.ui.web.document.filter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JSONDocumentFilterTest
{
	@Test
	void serialize_deserialize() throws JsonProcessingException
	{
		final JSONDocumentFilter json = JSONDocumentFilter.builder()
				.filterId("filterId")
				.caption("caption")
				.parameter(JSONDocumentFilterParam.builder()
						.parameterName("param1")
						.value("1234")
						.valueTo("5678")
						.build())
				.build();
		System.out.printf("JSON: " + json);

		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String jsonString = objectMapper.writeValueAsString(json);
		System.out.printf("JSON string: " + jsonString);

		final JSONDocumentFilter jsonDeserialized = objectMapper.readValue(jsonString, JSONDocumentFilter.class);
		System.out.printf("JSON deserialize: " + jsonDeserialized);

		assertThat(jsonDeserialized).usingRecursiveComparison().isEqualTo(json);
		assertThat(jsonDeserialized).isEqualTo(json);
	}
}