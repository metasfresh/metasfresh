package de.metas.distribution.rest_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.distribution.rest_api.JsonDistributionEvent.JsonDistributionEventBuilder;
import de.metas.distribution.workflows_api.DistributionJobId;
import de.metas.distribution.workflows_api.DistributionJobLineId;
import de.metas.distribution.workflows_api.DistributionJobStepId;
import de.metas.distribution.workflows_api.DistributionMobileApplication;
import de.metas.scannable_code.ScannedCode;
import de.metas.workflow.rest_api.model.WFProcessId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDistributionEventTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	void beforeEach()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(
				newEvent().pickFrom(JsonDistributionEvent.PickFrom.builder()
								.qrCode("qr code ...")
								.qtyPicked(new BigDecimal("32.432"))
								.build())
						.build()
		);
		testSerializeDeserialize(
				newEvent().unpick(JsonDistributionEvent.Unpick.builder()
								.unpickToTargetQRCode("unpickToTargetQRCode ...")
								.build())
						.build()
		);
		testSerializeDeserialize(
				newEvent().dropTo(JsonDistributionEvent.DropTo.builder()
								.qrCode(ScannedCode.ofString("qr code ...."))
								.build())
						.build()
		);
	}

	private static JsonDistributionEventBuilder newEvent()
	{
		return JsonDistributionEvent.builder()
				.wfProcessId(WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, DistributionJobId.ofJson("123").toDDOrderId()).getAsString())
				.wfActivityId("dummy activity")
				.lineId(DistributionJobLineId.ofJson("456"))
				.distributionStepId(DistributionJobStepId.ofJson("789"));
	}

	void testSerializeDeserialize(final JsonDistributionEvent event) throws JsonProcessingException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(event);
		final JsonDistributionEvent event2 = jsonObjectMapper.readValue(jsonString, JsonDistributionEvent.class);
		assertThat(event2).usingRecursiveComparison().isEqualTo(event);
		assertThat(event2).isEqualTo(event);
	}

}