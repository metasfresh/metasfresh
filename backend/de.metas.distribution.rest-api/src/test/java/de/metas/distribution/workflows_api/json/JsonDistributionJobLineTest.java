package de.metas.distribution.workflows_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.distribution.workflows_api.DistributionJobLineId;
import de.metas.distribution.workflows_api.DistributionJobStepId;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class JsonDistributionJobLineTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(
				JsonDistributionJobLine.builder()
						.lineId(DistributionJobLineId.ofJson(1))
						.caption("line caption")
						.productId("2")
						.productName("line product name")
						.uom("line uom")
						.qtyToMove(new BigDecimal("12.3456"))
						.pickFromLocator(JsonLocatorInfo.builder()
								.caption("pickFromLocator")
								.qrCode("pickFromLocator's qr code")
								.build())
						.dropToLocator(JsonLocatorInfo.builder()
								.caption("dropToLocator")
								.qrCode("dropToLocator's qr code")
								.build())
						.allowPickingAnyHU(true)
						.steps(ImmutableList.of(
								JsonDistributionJobStep.builder()
										.id(DistributionJobStepId.ofJson("3"))
										.productName("step product name")
										.uom("step uom")
										.qtyToMove(new BigDecimal("65.4321"))
										.qtyPicked(new BigDecimal("0.1234"))
										.pickFromLocator(JsonLocatorInfo.builder()
												.caption("pickFromLocator")
												.qrCode("pickFromLocator's qr code")
												.build())
										.pickFromHU(JsonHUInfo.builder()
												.qrCode(JsonDisplayableQRCode.builder()
														.code("code")
														.displayable("displayable")
														.build())
												.build())
										.droppedToLocator(true)
										.dropToLocator(JsonLocatorInfo.builder()
												.caption("dropToLocator")
												.qrCode("dropToLocator's qr code")
												.build())
										.build()
						))
						.build()
		);
	}

	void testSerializeDeserialize(JsonDistributionJobLine line) throws JsonProcessingException
	{
		System.out.println("line:\n" + line);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(line);
		System.out.println("JSON:\n" + json);

		final JsonDistributionJobLine deserializedLine = jsonObjectMapper.readValue(json, JsonDistributionJobLine.class);
		System.out.println("deserialized line:\n" + deserializedLine);
	}

}