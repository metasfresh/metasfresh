/*
 * #%L
 * de-metas-common-shipmentschedule
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.shipmentschedule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static de.metas.common.shipmentschedule.JsonRequestShipmentScheduleResult.*;
import static org.assertj.core.api.Assertions.assertThat;

class JsonSerializeDeserializeTests
{

	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		assertThat(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class).isNotNull(); // just to get a compile error if not present
		objectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	@Test
	void shipmentSchedule() throws IOException
	{
		// given
		final JsonResponseShipmentSchedule scheduleOrig = JsonResponseShipmentSchedule
				.builder()
				.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 48))
				.poReference("poReference")
				.orderDocumentNo("orderDocumentNo")
				.productNo("productNo")
				.build();

		final String jsonString = objectMapper.writeValueAsString(scheduleOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final JsonResponseShipmentSchedule schedule = objectMapper.readValue(jsonString, JsonResponseShipmentSchedule.class);

		// then
		assertThat(schedule).isEqualTo(scheduleOrig);
	}

	@Test
	void shipmentScheduleList() throws IOException
	{
		// given
		final JsonResponseShipmentScheduleList scheduleListOrig = JsonResponseShipmentScheduleList.builder()
				.item(JsonResponseShipmentSchedule
						.builder()
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 01))
						.poReference("poReference_1")
						.orderDocumentNo("orderDocumentNo_1")
						.productNo("productNo_1")
						.build())
				.item(JsonResponseShipmentSchedule
						.builder()
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 05, 02))
						.poReference("poReference_2")
						.orderDocumentNo("orderDocumentNo_2")
						.productNo("productNo_2")
						.build())
				.build();

		final String jsonString = objectMapper.writeValueAsString(scheduleListOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final JsonResponseShipmentScheduleList scheduleList = objectMapper.readValue(jsonString, JsonResponseShipmentScheduleList.class);

		// then
		assertThat(scheduleList).isEqualTo(scheduleListOrig);
	}

	@Test
	void jsonRequestShipmentScheduleResult() throws IOException
	{
		final JsonRequestShipmentScheduleResult statusOrig = builder().outcome(Outcome.OK).build();

		final String jsonString = objectMapper.writeValueAsString(statusOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final JsonRequestShipmentScheduleResult status = objectMapper.readValue(jsonString, JsonRequestShipmentScheduleResult.class);

		// thenCopyRecordSupport
		assertThat(status).isEqualTo(statusOrig);
	}

	@Test
	void jsonRequestShipmentScheduleResultList() throws IOException
	{

	final	JsonRequestShipmentScheduleResultList resultListOrig = JsonRequestShipmentScheduleResultList.builder()
				.item(builder().outcome(Outcome.OK).build())
				.item(builder().outcome(Outcome.ERROR)
						.error(JsonError.builder()
								.error(JsonErrorItem.builder().message("errorMessage").build())
								.build())
						.build())
				.build();

		final String jsonString = objectMapper.writeValueAsString(resultListOrig);
		assertThat(jsonString).isNotEmpty();

		// when
		final JsonRequestShipmentScheduleResultList status = objectMapper.readValue(jsonString, JsonRequestShipmentScheduleResultList.class);

		// then
		assertThat(status).isEqualTo(resultListOrig);
	}
}