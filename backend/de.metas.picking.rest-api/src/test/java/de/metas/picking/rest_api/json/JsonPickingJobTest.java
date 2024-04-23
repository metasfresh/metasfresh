/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JsonPickingJobTest
{
	private int nextLineId = 1;

	@BeforeEach
	void beforeEach()
	{
		nextLineId = 0;
	}

	void testSerializeDeserialize(final JsonPickingJob obj) throws JsonProcessingException
	{
		System.out.println("Object: " + obj);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final String jsonString = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("JSON: " + obj);

		final JsonPickingJob objDeserialized = jsonObjectMapper.readValue(jsonString, obj.getClass());
		assertThat(objDeserialized).isEqualTo(obj);
		assertThat(objDeserialized).usingRecursiveComparison().isEqualTo(obj);
	}

	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(JsonPickingJob.builder()
				.completeStatus(JsonCompleteStatus.IN_PROGRESS)
				.lines(ImmutableList.of(
						randomJsonPickingJobLine(),
						randomJsonPickingJobLine(),
						randomJsonPickingJobLine()
				))
				.pickFromAlternatives(ImmutableList.of(
						randomJsonPickFromAlternative(),
						randomJsonPickFromAlternative(),
						randomJsonPickFromAlternative()
				))
				.build());
	}

	private JsonPickFromAlternative randomJsonPickFromAlternative()
	{
		return JsonPickFromAlternative.builder()
				.id("id_" + UUID.randomUUID())
				.locatorName("locatorName")
				.huQRCode(randomJsonDisplayableQRCode())
				.uom("uom")
				.qtyAvailable(new BigDecimal("123.456"))
				.build();
	}

	private JsonPickingJobLine randomJsonPickingJobLine()
	{
		final int id = nextLineId++;

		final PickingUnit pickingUnit = id % 2 == 0 ? PickingUnit.CU : PickingUnit.TU;
		final String packingItemName = pickingUnit == PickingUnit.TU ? "My TU " + id : "My CU " + id;

		return JsonPickingJobLine.builder()
				.pickingLineId("pickingLineId_" + id)
				.productId("productId")
				.productNo("productNo")
				.caption("caption")
				.pickingUnit(pickingUnit)
				.packingItemName(packingItemName)
				.uom("uom")
				.qtyToPick(new BigDecimal("321.456"))
				.qtyPicked(new BigDecimal("3.04"))
				.qtyRejected(new BigDecimal("0.40"))
				.qtyPickedOrRejected(new BigDecimal("3.44"))
				.qtyRemainingToPick(new BigDecimal("4.56"))
				.catchWeightUOM("catchWeightUOM")
				.allowPickingAnyHU(true)
				.steps(ImmutableList.of(
						randomJsonPickingJobStep(),
						randomJsonPickingJobStep(),
						randomJsonPickingJobStep(),
						randomJsonPickingJobStep()
				))
				.completeStatus(JsonCompleteStatus.IN_PROGRESS)
				.manuallyClosed(true)
				.build();
	}

	private JsonPickingJobStep randomJsonPickingJobStep()
	{
		return JsonPickingJobStep.builder()
				.pickingStepId("pickingStepId_" + UUID.randomUUID())
				.completeStatus(JsonCompleteStatus.IN_PROGRESS)
				.productId("productId")
				.productName("productName")
				.uom("uom")
				.qtyToPick(new BigDecimal("666.777"))
				.mainPickFrom(randomJsonPickingJobStepPickFrom())
				.pickFromAlternatives(randomJsonPickingJobStepPickFromMap(10))
				.build();
	}

	@SuppressWarnings("SameParameterValue")
	private ImmutableMap<String, JsonPickingJobStepPickFrom> randomJsonPickingJobStepPickFromMap(final int count)
	{
		ImmutableMap.Builder<String, JsonPickingJobStepPickFrom> result = ImmutableMap.builder();
		for (int i = 1; i <= count; i++)
		{
			final JsonPickingJobStepPickFrom pickFrom = randomJsonPickingJobStepPickFrom();
			result.put(pickFrom.getAlternativeId(), pickFrom);
		}
		return result.build();
	}

	private JsonPickingJobStepPickFrom randomJsonPickingJobStepPickFrom()
	{
		final String alternativeId = UUID.randomUUID().toString();
		return JsonPickingJobStepPickFrom.builder()
				.alternativeId(alternativeId)
				.locatorName("locatorName")
				.huQRCode(randomJsonDisplayableQRCode())
				.qtyPicked(new BigDecimal("111.222"))
				.qtyRejected(new BigDecimal("111.333"))
				.qtyRejectedReasonCode("qtyRejectedReasonCode")
				.build();
	}

	private JsonDisplayableQRCode randomJsonDisplayableQRCode()
	{
		final String code = UUID.randomUUID().toString();
		return JsonDisplayableQRCode.builder()
				.code(code)
				.displayable("displayable: " + code)
				.build();
	}
}