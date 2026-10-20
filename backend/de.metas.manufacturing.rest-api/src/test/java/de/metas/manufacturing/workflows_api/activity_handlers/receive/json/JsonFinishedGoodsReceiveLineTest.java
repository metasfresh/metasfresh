package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonFinishedGoodsReceiveLineTest
{
	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	private static JsonFinishedGoodsReceiveLine.JsonFinishedGoodsReceiveLineBuilder lineBuilder()
	{
		return JsonFinishedGoodsReceiveLine.builder()
				.id("1")
				.productName("product")
				.uom("PCE")
				.hazardSymbols(ImmutableList.of())
				.allergens(ImmutableList.of())
				.qtyToReceive(BigDecimal.TEN)
				.qtyReceived(BigDecimal.ZERO)
				.availableReceivingTargets(JsonNewLUTargetsList.emptyWithoutReason())
				.availableReceivingTUTargets(JsonNewTUTargetList.ofList(ImmutableList.of()));
	}

	@Test
	void catchWeightScanRequired_isFalseByDefault()
	{
		final JsonNode json = jsonMapper.valueToTree(lineBuilder().build());

		assertThat(json.has("catchWeightScanRequired")).isTrue();
		assertThat(json.get("catchWeightScanRequired").asBoolean()).isFalse();
	}

	@Test
	void catchWeightScanRequired_isSentToTheClient()
	{
		final JsonNode json = jsonMapper.valueToTree(lineBuilder().catchWeightScanRequired(true).build());

		assertThat(json.get("catchWeightScanRequired").asBoolean()).isTrue();
	}
}
