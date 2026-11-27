package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.scannable_code.ScannedCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class JsonPickingEventsListTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonPickingEventsList object = JsonPickingEventsList.builder()
				.events(ImmutableList.of(
						JsonPickingStepEvent.builder()
								.wfProcessId("wfProcessId")
								.wfActivityId("wfActivityId")
								.pickingLineId("pickingLineId")
								.pickingStepId("pickingStepId")
								.type(JsonPickingStepEvent.EventType.PICK)
								.huQRCode("some dummy HU QR code")
								.qtyPicked(new BigDecimal("123.45"))
								.build()
				))
				.build();

		final String json = objectMapper.writeValueAsString(object);

		final JsonPickingEventsList objectDeserialized = objectMapper.readValue(json, JsonPickingEventsList.class);

		Assertions.assertThat(objectDeserialized)
				.usingRecursiveComparison()
				.isEqualTo(object);
	}

	@Test
	void cuLabel_isReadAsScannedCode_blankIsNoLabel() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String jsonPrefix = "{\"wfProcessId\":\"p\",\"wfActivityId\":\"a\",\"pickingLineId\":\"l\",\"pickingStepId\":\"s\",\"type\":\"PICK\",\"huQRCode\":\"qr\",\"cuLabel\":";

		final JsonPickingStepEvent withLabel = objectMapper.readValue(jsonPrefix + "\" 0104 label \"}", JsonPickingStepEvent.class);
		final JsonPickingStepEvent blankLabel = objectMapper.readValue(jsonPrefix + "\"  \"}", JsonPickingStepEvent.class);

		Assertions.assertThat(withLabel.getCuLabel()).isEqualTo(ScannedCode.ofString("0104 label"));
		Assertions.assertThat(blankLabel.getCuLabel()).isNull();
	}
}
