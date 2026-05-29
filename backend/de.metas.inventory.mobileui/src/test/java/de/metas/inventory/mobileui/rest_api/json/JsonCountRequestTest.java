package de.metas.inventory.mobileui.rest_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.scannable_code.ScannedCode;
import de.metas.workflow.rest_api.model.WFProcessId;
import org.adempiere.mm.attributes.AttributeCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class JsonCountRequestTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	public void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonCountRequest.builder()
				.wfProcessId(WFProcessId.ofString("mobileApp-1234"))
				.lineId(InventoryLineId.ofRepoId(567))
				.scannedCode(ScannedCode.ofString("abcdef1234567890"))
				.huId(HuId.ofRepoId(89))
				.qtyCount(new BigDecimal("111.000222"))
				.lineCountingDone(true)
				.attributes(ImmutableList.of(
						JsonCountRequest.Attribute.builder()
								.code(AttributeCode.ofString("MyAttribute1"))
								.value(null)
								.build(),
						JsonCountRequest.Attribute.builder()
								.code(AttributeCode.ofString("MyAttribute1"))
								.value("MyValue1")
								.build()
				))
				.build());
	}
	
	@Test
	void testDeserialize() throws JsonProcessingException
	{
		final String json = "{\"wfProcessId\":\"inventory-1000169\",\"lineId\":1000169,\"scannedCode\":\"TODO\",\"huId\":1003822,\"qtyCount\":1,\"lineCountingDone\":false,\"attributes\":[{\"code\":\"HU_BestBeforeDate\",\"value\":null},{\"code\":\"Lot-Nummer\",\"value\":\"dsa\"}]}";
		final Object objDeserialized = jsonObjectMapper.readValue(json, JsonCountRequest.class);
		System.out.println("Object deserialized: " + objDeserialized);
	}

	private void testSerializeDeserialize(final Object obj) throws JsonProcessingException
	{
		System.out.println("Object: " + obj);

		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}