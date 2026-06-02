package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.HuPackingInstructionsId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonTUPickingTargetTest
{
	@Test
	void isGRAIScan_trueWhenGraiSet()
	{
		assertThat(JsonTUPickingTarget.builder().grai("12345").build().isGRAIScan()).isTrue();
	}

	@Test
	void isGRAIScan_falseWhenGraiNull()
	{
		assertThat(JsonTUPickingTarget.builder().build().isGRAIScan()).isFalse();
	}

	@Test
	void isGRAIScan_falseWhenGraiBlank()
	{
		assertThat(JsonTUPickingTarget.builder().grai("   ").build().isGRAIScan()).isFalse();
	}

	@Test
	void isGRAIScan_falseForManualNewTUTarget()
	{
		final JsonTUPickingTarget manualTarget = JsonTUPickingTarget.builder()
				.id("123")
				.caption("Some box")
				.tuPIId(HuPackingInstructionsId.ofRepoId(123))
				.build();
		assertThat(manualTarget.isGRAIScan()).isFalse();
	}

	@Test
	void deserialize_graiOnlyBody() throws JsonProcessingException
	{
		// The mobile UI may POST a GRAI-only body (no id/caption); it must deserialize and route to the GRAI flow.
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final JsonTUPickingTarget deserialized = objectMapper.readValue("{\"grai\":\"12345\"}", JsonTUPickingTarget.class);

		assertThat(deserialized.isGRAIScan()).isTrue();
		assertThat(deserialized.getGrai()).isEqualTo("12345");
		assertThat(deserialized.getId()).isNull();
		assertThat(deserialized.getCaption()).isNull();
	}
}
