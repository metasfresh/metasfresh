package de.metas.shipper.client.nshift;

/*
 * #%L
 * de.metas.shipper.client.nshift
 * %%
 * Copyright (C) 2026 metas GmbH
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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.response.JsonOrderAdviceResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponse;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Deserializes a real nShift {@code OrderAdvice} response (sample captured from the live API) to guard the
 * envelope unwrap and the request/response key asymmetry on the line goods type.
 */
class JsonOrderAdviceResponseDeserializationTest
{
	// mirrors NShiftClientConfig#nShiftObjectMapper
	private final ObjectMapper objectMapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Test
	void deserialize_unwrapsShipment_andReadsLineGoodsTypeId() throws Exception
	{
		final JsonOrderAdviceResponse response;
		try (final InputStream is = getClass().getResourceAsStream("/de/metas/shipper/client/nshift/orderAdviceResponse-sample.json"))
		{
			assertThat(is).as("sample resource on classpath").isNotNull();
			response = objectMapper.readValue(is, JsonOrderAdviceResponse.class);
		}

		assertThat(response.getStatus()).isEqualTo("0 - Success");

		// OrderAdvice wraps the booked shipment under "Shipment" — must unwrap, else everything is null
		final JsonShipmentResponse shipment = response.getShipment();
		assertThat(shipment).as("shipment unwrapped from OrderAdvice envelope").isNotNull();
		assertThat(shipment.getProdCSID()).isEqualTo(10672);

		final List<JsonLine> lines = shipment.getLines();
		assertThat(lines).hasSize(2);
		// JsonLine maps the goods type from "GoodsTypeID"; guards against it deserializing to null.
		assertThat(lines.get(0).getGoodsTypeID()).isEqualTo(2);
		assertThat(lines.get(0).getGoodsTypeName()).isEqualTo("Customer supplied package");
		assertThat(lines.get(1).getGoodsTypeID()).isEqualTo(2);
	}

	@Test
	void deserialize_readsTopLevelServices() throws Exception
	{
		final String json = "{ \"ProdConceptID\" : 9303, \"ProdName\" : \"Home Delivery\", \"Services\" : [337011, 337012] }";

		final JsonShipmentResponse shipment = objectMapper.readValue(json, JsonShipmentResponse.class);

		assertThat(shipment.getProdConceptID()).isEqualTo(9303);
		assertThat(shipment.getProdName()).isEqualTo("Home Delivery");
		assertThat(shipment.getServices()).containsExactly(337011, 337012);
	}

	@Test
	void deserialize_readsErrorMessages_whenNoForwardShipmentBooked() throws Exception
	{
		// nShift's OrderAdvice response when the FORWARD booking fails: no "Shipment", the reason under
		// "ErrorMessages" (a "ReturnShipment" may still be present and must be ignored gracefully). Mirrors the
		// DHL Freight "Internal Server Error: list index out of range" case — the error must be surfaced, not lost.
		final String json = "{ \"ReturnShipment\" : { \"ShpNo\" : \"FRT-1\" },"
				+ " \"ErrorMessages\" : [ \"Shipment submit failed. Internal Server Error: list index out of range\" ],"
				+ " \"CorrelationID\" : \"15cefb2a-04c7-4f16-9596-6fe6c30a7b9a\" }";

		final JsonOrderAdviceResponse response = objectMapper.readValue(json, JsonOrderAdviceResponse.class);

		assertThat(response.getShipment()).as("no forward Shipment when the booking failed").isNull();
		assertThat(response.getErrorMessages())
				.containsExactly("Shipment submit failed. Internal Server Error: list index out of range");
		assertThat(response.getCorrelationID()).isEqualTo("15cefb2a-04c7-4f16-9596-6fe6c30a7b9a");
		assertThat(response.failureReason()).isEqualTo("nShift errors: Shipment submit failed. Internal Server Error: list index out of range");
	}
}
