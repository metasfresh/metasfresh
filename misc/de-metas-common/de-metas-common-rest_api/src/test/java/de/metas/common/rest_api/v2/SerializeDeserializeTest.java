/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.tablerecordref.JsonTableRecordReference;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockNoticeRequest;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponse;
import de.metas.common.rest_api.v2.warehouse.JsonOutOfStockResponseItem;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class SerializeDeserializeTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	public void jsonApiResponse() throws Exception
	{
		JsonApiResponse apiResponse = JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(123))
				.build();

		testSerializeDeserialize(apiResponse, JsonApiResponse.class);

		apiResponse = JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(123))
				.endpointResponse("some string")
				.build();

		testSerializeDeserialize(apiResponse, JsonApiResponse.class);
	}

	@Test
	public void jsonOutOfStockNoticeRequest() throws Exception
	{
		final JsonOutOfStockNoticeRequest jsonOutOfStockNoticeRequest = JsonOutOfStockNoticeRequest.builder()
				.orgCode("orgCode")
				.productIdentifier("productIdentifier")
				.closePendingShipmentSchedules(true)
				.createInventory(true)
				.attributeSetInstance(JsonAttributeSetInstance.builder()
											  .attributeInstance(JsonAttributeInstance.builder()
																		 .attributeCode("attributeCode")
																		 .valueStr("valueStr")
																		 .build())
											  .build())
				.build();

		testSerializeDeserialize(jsonOutOfStockNoticeRequest, JsonOutOfStockNoticeRequest.class);
	}

	@Test
	public void jsonOutOfStockNoticeResponse() throws Exception
	{
		final JsonOutOfStockResponse jsonOutOfStockResponse = JsonOutOfStockResponse.builder()
				.affectedWarehouse(JsonOutOfStockResponseItem.builder()
										   .warehouseId(JsonMetasfreshId.of(1))
										   .closedShipmentSchedules(ImmutableList.of(JsonMetasfreshId.of(2)))
										   .inventoryDocNo("inventoryDocNo")
										   .build())
				.build();

		testSerializeDeserialize(jsonOutOfStockResponse, JsonOutOfStockResponse.class);
	}

	@Test
	public void jsonPInstanceLog() throws Exception
	{
		final JsonPInstanceLog jsonPInstanceLog = JsonPInstanceLog.builder()
				.tableRecordRef(JsonTableRecordReference.builder().tableName("tableName").recordId(JsonMetasfreshId.of(1)).build())
				.message("msg")
				.build();

		testSerializeDeserialize(jsonPInstanceLog, JsonPInstanceLog.class);
	}

	private <T> void testSerializeDeserialize(@NonNull final T json, final Class<T> clazz) throws IOException
	{
		final String jsonString = jsonObjectMapper.writeValueAsString(json);
		final T jsonDeserialized = jsonObjectMapper.readValue(jsonString, clazz);
		assertThat(jsonDeserialized).isEqualTo(json);
	}
}
