package de.metas.frontend_testing.expectations.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.frontend_testing.masterdata.Identifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonSalesOrderExpectationTest
{
	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	/**
	 * The {@code carrierAdvise} map is keyed by the product {@link Identifier} (like every other product
	 * reference in the expectation DTOs). This pins that the JSON object key (a masterdata map key such as
	 * "P1") round-trips into an {@code Identifier} key via Jackson.
	 */
	@Test
	void carrierAdvise_isKeyedByProductIdentifier() throws Exception
	{
		final String json = "{\"carrierAdvise\":{\"P1\":{\"advisingStatus\":\"CO\",\"carrierProductSet\":true}}}";

		final JsonSalesOrderExpectation parsed = jsonMapper.readValue(json, JsonSalesOrderExpectation.class);

		assertThat(parsed.getCarrierAdvise()).containsOnlyKeys(Identifier.ofString("P1"));
		assertThat(parsed.getCarrierAdvise().get(Identifier.ofString("P1")).getAdvisingStatus()).isEqualTo("CO");
	}
}
