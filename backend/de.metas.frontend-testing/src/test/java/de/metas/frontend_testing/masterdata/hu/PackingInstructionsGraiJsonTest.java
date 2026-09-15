package de.metas.frontend_testing.masterdata.hu;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAIRequired;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves the GRAI e2e contract that the Playwright spec
 * {@code e2e/mobile-webui/tests/spec/picking/picking-grai-scan.spec.js} relies on:
 * <ul>
 *   <li>request: {@code packingInstructions.<id>.graiMapping: true} is deserialized into the command request</li>
 *   <li>response: {@code packingInstructions.<id>.grai} is the canonical GRAI dot-string</li>
 * </ul>
 */
public class PackingInstructionsGraiJsonTest
{
	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Test
	public void request_graiMapping_isDeserialized() throws Exception
	{
		final String json = "{\"lu\":\"LU_MAIN\",\"qtyTUsPerLU\":20,\"tu\":\"TU_MAPPED\",\"product\":\"P1\",\"qtyCUsPerTU\":4,\"graiMapping\":true}";

		final JsonPackingInstructionsRequest request = jsonMapper.readValue(json, JsonPackingInstructionsRequest.class);

		assertThat(request.isGraiMapping()).isTrue();
	}

	@Test
	public void response_grai_isSerializedAsCanonicalString() throws Exception
	{
		final GRAI grai = GRAI.ofCanonicalString("7613204.00307.1234567890");

		final JsonPackingInstructionsResponse response = JsonPackingInstructionsResponse.builder()
				.tuName("TU_MAPPED")
				.grai(grai)
				.build();

		final String json = jsonMapper.writeValueAsString(response);

		assertThat(json).contains("\"grai\":\"7613204.00307.1234567890\"");
	}

	@Test
	public void bpartner_graiRequired_isDeserializedByCode() throws Exception
	{
		final JsonCreateBPartnerRequest request = jsonMapper.readValue("{\"graiRequired\":\"Y\"}", JsonCreateBPartnerRequest.class);
		assertThat(request.getGraiRequired()).isEqualTo(GRAIRequired.Yes);
	}

	@Test
	public void bpartner_graiRequired_isSerializedByCode() throws Exception
	{
		final JsonCreateBPartnerRequest request = JsonCreateBPartnerRequest.builder()
				.graiRequired(GRAIRequired.YesWithDummyGRAIs)
				.build();
		assertThat(jsonMapper.writeValueAsString(request)).contains("\"graiRequired\":\"D\"");
	}
}
