package de.metas.hu_consolidation.mobile.rest_api.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.scannable_code.ScannedCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonConsolidateByScanRequestTest
{
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private ScannedCode deserializeScannedCode(final String json) throws Exception
	{
		return jsonObjectMapper.readValue(json, JsonConsolidateByScanRequest.class).getScannedCode();
	}

	@Test
	void scannedCode_isReadAsScannedCode() throws Exception
	{
		assertThat(deserializeScannedCode("{\"scannedCode\":\"  GRAI-1 \"}")).isEqualTo(ScannedCode.ofString("GRAI-1"));
	}

	@Test
	void blankScannedCode_isNull() throws Exception
	{
		assertThat(deserializeScannedCode("{\"scannedCode\":\"  \"}")).isNull();
	}

	@Test
	void missingScannedCode_isNull() throws Exception
	{
		assertThat(deserializeScannedCode("{}")).isNull();
	}
}
