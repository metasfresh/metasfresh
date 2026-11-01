package de.metas.shipper.gateway.nshift.client;

import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.util.StringUtils;
import de.metas.shipper.client.nshift.NShiftShipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentDispatchServiceTest
{
	@Mock
	private NShiftShipmentService shipmentService;

	private ShipmentDispatchService shipmentDispatchService;

	private static final JsonDeliveryResponse STUB_RESPONSE = JsonDeliveryResponse.builder()
			.requestId("test-request-id")
			.build();

	@BeforeEach
	void setUp()
	{
		shipmentDispatchService = new ShipmentDispatchService(shipmentService);
	}

	@Test
	void shipTypeS_delegates_to_createShipment()
	{
		final JsonDeliveryRequest request = buildRequest("S", false);
		when(shipmentService.createShipment(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryResponse result = shipmentDispatchService.createShipment(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(shipmentService).createShipment(request);
		verifyNoMoreInteractions(shipmentService);
	}

	@Test
	void shipTypeO_delegates_to_createShipmentViaOrderAdvice()
	{
		final JsonDeliveryRequest request = buildRequest("O", false);
		when(shipmentService.createShipmentViaOrderAdvice(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryResponse result = shipmentDispatchService.createShipment(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(shipmentService).createShipmentViaOrderAdvice(request);
		verifyNoMoreInteractions(shipmentService);
	}

	@Test
	void shipTypeAbsent_defaults_to_ORDER_and_delegates_to_createShipmentViaOrderAdvice()
	{
		final JsonDeliveryRequest request = buildRequest(null, false);
		when(shipmentService.createShipmentViaOrderAdvice(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryResponse result = shipmentDispatchService.createShipment(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(shipmentService).createShipmentViaOrderAdvice(request);
		verifyNoMoreInteractions(shipmentService);
	}

	@Test
	void shipTypeO_withIsManual_delegates_to_createShipment()
	{
		final JsonDeliveryRequest request = buildRequest("O", true);
		when(shipmentService.createShipment(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryResponse result = shipmentDispatchService.createShipment(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(shipmentService).createShipment(request);
		verifyNoMoreInteractions(shipmentService);
	}

	private static JsonDeliveryRequest buildRequest(@Nullable final String shipTypeCode, final boolean isManual)
	{
		final JsonShipperConfig.JsonShipperConfigBuilder configBuilder = JsonShipperConfig.builder()
				.url("https://nshift.example.com");
		if (shipTypeCode != null)
		{
			configBuilder.additionalProperty("ShipType", shipTypeCode);
			configBuilder.additionalProperty("IsManual", StringUtils.ofBoolean(isManual));
		}

		return JsonDeliveryRequest.builder()
				.pickupAddress(minimalAddress())
				.pickupDate("2025-01-01")
				.timeFrom("08:00")
				.timeTo("18:00")
				.deliveryAddress(minimalAddress())
				.shipperConfig(configBuilder.build())
				.build();
	}

	private static JsonAddress minimalAddress()
	{
		return JsonAddress.builder()
				.companyName1("Test Co")
				.country("DE")
				.zipCode("80331")
				.city("Munich")
				.build();
	}
}
