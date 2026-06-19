package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.scannable_code.ScannedCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonTUPickingTargetTest
{
	@Test
	void getGrai_returnsScannedCode_whenGraiSet()
	{
		final JsonTUPickingTarget target = JsonTUPickingTarget.builder().grai(ScannedCode.ofString("12345")).build();
		assertThat(target.getGrai()).isEqualTo(ScannedCode.ofString("12345"));
	}

	@Test
	void getGrai_null_whenNoGrai()
	{
		assertThat(JsonTUPickingTarget.builder().build().getGrai()).isNull();
	}

	@Test
	void getGrai_null_forManualNewTUTarget()
	{
		final JsonTUPickingTarget manualTarget = JsonTUPickingTarget.builder()
				.id("123")
				.caption("Some box")
				.tuPIId(HuPackingInstructionsId.ofRepoId(123))
				.build();
		assertThat(manualTarget.getGrai()).isNull();
	}

	@Test
	void deserialize_graiOnlyBody() throws JsonProcessingException
	{
		// The mobile UI may POST a GRAI-only body (no id/caption); the scanned code must deserialize so the controller can route to the GRAI flow.
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final JsonTUPickingTarget deserialized = objectMapper.readValue("{\"grai\":\"12345\"}", JsonTUPickingTarget.class);

		assertThat(deserialized.getGrai()).isEqualTo(ScannedCode.ofString("12345"));
		assertThat(deserialized.getId()).isNull();
		assertThat(deserialized.getCaption()).isNull();
	}

	@Test
	void of_carriesGraiFromNewTuTarget()
	{
		// A new-TU target produced by the GRAI scan flow carries the resolved GRAI; the outbound JSON
		// (the HTTP 200 response body the mobile UI reads back) must surface it as a scanned code.
		final GRAI grai = GRAI.parse("7613204.00307.999999");
		final TUPickingTarget target = TUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(123), "Some box", grai);

		final JsonTUPickingTarget json = JsonTUPickingTarget.of(target);

		assertThat(json.getGrai())
				.as("the scanned GRAI carried by the new-TU target must be surfaced on the response")
				.isEqualTo(ScannedCode.ofString(grai.toCanonicalString()));
	}

	@Test
	void deserialize_blankGrai_toNull() throws JsonProcessingException
	{
		// A blank scanned code normalizes to null at the JSON boundary (ScannedCode.ofNullableObject),
		// so the controller does NOT treat it as a GRAI scan.
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final JsonTUPickingTarget deserialized = objectMapper.readValue("{\"grai\":\"   \"}", JsonTUPickingTarget.class);

		assertThat(deserialized.getGrai()).isNull();
	}
}
