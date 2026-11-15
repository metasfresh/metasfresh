package de.metas.handlingunits.rest_api.createfromlabel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.scannable_code.ScannedCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** the location QR code of the create-from-label request and preview is a {@link ScannedCode}, whose JSON form is the plain string */
class LocatorQRCodeJsonTypeTest
{
	private static final String LOCATOR_QR_CODE = "LOC#1#{\"warehouseId\":1,\"locatorId\":2,\"caption\":\"wh_l1\"}";
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Test
	void request_locatorQRCode_isReadAsScannedCode() throws Exception
	{
		final JsonNode json = jsonObjectMapper.createObjectNode()
				.put("scannedCode", "0102001234567893")
				.put("qty", "1")
				.put("locatorQRCode", LOCATOR_QR_CODE);

		final JsonCreateHUFromScannedCodeRequest request = jsonObjectMapper.readValue(jsonObjectMapper.writeValueAsString(json), JsonCreateHUFromScannedCodeRequest.class);

		assertThat(request.getLocatorQRCode().getAsString()).isEqualTo(LOCATOR_QR_CODE);
		assertThat(jsonObjectMapper.readTree(jsonObjectMapper.writeValueAsString(request)).get("locatorQRCode").asText()).isEqualTo(LOCATOR_QR_CODE);
	}

	@Test
	void proposal_locatorQRCode_isWrittenAsTheSamePlainString() throws Exception
	{
		final String proposalJson = jsonObjectMapper.writeValueAsString(jsonObjectMapper.createObjectNode().put("locatorQRCode", LOCATOR_QR_CODE));

		final JsonHUProposal proposal = jsonObjectMapper.readValue(proposalJson, JsonHUProposal.class);

		assertThat(proposal.getLocatorQRCode().getAsString()).isEqualTo(LOCATOR_QR_CODE);
		final JsonNode written = jsonObjectMapper.readTree(jsonObjectMapper.writeValueAsString(proposal)).get("locatorQRCode");
		assertThat(written.isTextual()).isTrue();
		assertThat(written.asText()).isEqualTo(LOCATOR_QR_CODE);
	}
}
