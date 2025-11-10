package de.metas.shipper.gateway.spi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperGatewayId;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import shadow.org.assertj.core.api.Assertions;

import java.io.IOException;

class OrderIdTest
{
	@Test
	public void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserialize(OrderId.of(ShipperGatewayId.ofString("testGateway"), "1234"));
		testSerializeDeserialize(OrderId.of(ShipperGatewayId.ofString("testGateway"), DeliveryOrderId.ofRepoId(1234)));
	}

	private void testSerializeDeserialize(@NonNull final Object obj) throws IOException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}

}