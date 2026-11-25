package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** The mobile frontend reads these three property names; they stay as they are whatever the Java fields are called. */
class JsonCUQtyEntryPolicyTest
{
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	@Test
	void serializesTheThreePoliciesUnderTheNamesTheFrontendReads() throws Exception
	{
		final JsonCUQtyEntryPolicy policy = JsonCUQtyEntryPolicy.of(CUQtyEntryMode.SCAN_EACH_CU.getPolicy());

		final JsonNode json = objectMapper.readTree(objectMapper.writeValueAsString(policy));

		assertThat(json.fieldNames()).toIterable().containsExactlyInAnyOrder("labelRequired", "packIdRequired", "scanOnce");
		assertThat(json.get("labelRequired").asBoolean()).isTrue();
		assertThat(json.get("packIdRequired").asBoolean()).isTrue();
		assertThat(json.get("scanOnce").asBoolean()).isFalse();
	}

	@Test
	void readsItsOwnJsonBack() throws Exception
	{
		final JsonCUQtyEntryPolicy policy = JsonCUQtyEntryPolicy.of(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY.getPolicy());

		final JsonCUQtyEntryPolicy readBack = objectMapper.readValue(objectMapper.writeValueAsString(policy), JsonCUQtyEntryPolicy.class);

		assertThat(readBack).isEqualTo(policy);
	}
}
