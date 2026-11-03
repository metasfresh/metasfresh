package de.metas.shipper.gateway.nshift.client;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.shipper.client.nshift.NShiftOrderAdvisorService;
import de.metas.shipper.client.nshift.NShiftShipAdvisorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipAdvisorServiceTest
{
	@Mock
	private NShiftShipAdvisorService shipAdvisorService;

	@Mock
	private NShiftOrderAdvisorService orderAdvisorService;

	private ShipAdvisorService shipAdvisorServiceUnderTest;

	private static final JsonDeliveryAdvisorResponse STUB_RESPONSE = JsonDeliveryAdvisorResponse.builder()
			.requestId("test-advise-id")
			.build();

	@BeforeEach
	void setUp()
	{
		shipAdvisorServiceUnderTest = new ShipAdvisorService(shipAdvisorService, orderAdvisorService);
	}

	@Test
	void adviseTypeS_delegates_to_shipAdvisorService()
	{
		final JsonDeliveryAdvisorRequest request = buildRequest("S");
		when(shipAdvisorService.advise(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryAdvisorResponse result = shipAdvisorServiceUnderTest.advise(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(shipAdvisorService).advise(request);
		verifyNoMoreInteractions(shipAdvisorService, orderAdvisorService);
	}

	@Test
	void adviseTypeO_delegates_to_orderAdvisorService()
	{
		final JsonDeliveryAdvisorRequest request = buildRequest("O");
		when(orderAdvisorService.advise(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryAdvisorResponse result = shipAdvisorServiceUnderTest.advise(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(orderAdvisorService).advise(request);
		verifyNoMoreInteractions(shipAdvisorService, orderAdvisorService);
	}

	@Test
	void adviseTypeAbsent_defaults_to_ORDER_and_delegates_to_orderAdvisorService()
	{
		final JsonDeliveryAdvisorRequest request = buildRequest(null);
		when(orderAdvisorService.advise(request)).thenReturn(STUB_RESPONSE);

		final JsonDeliveryAdvisorResponse result = shipAdvisorServiceUnderTest.advise(request);

		assertThat(result).isSameAs(STUB_RESPONSE);
		verify(orderAdvisorService).advise(request);
		verifyNoMoreInteractions(shipAdvisorService, orderAdvisorService);
	}

	private static JsonDeliveryAdvisorRequest buildRequest(final String adviseTypeCode)
	{
		final JsonShipperConfig.JsonShipperConfigBuilder configBuilder = JsonShipperConfig.builder()
				.url("https://nshift.example.com");
		if (adviseTypeCode != null)
		{
			configBuilder.additionalProperty("AdviseType", adviseTypeCode);
		}

		return JsonDeliveryAdvisorRequest.builder()
				.pickupAddress(minimalAddress())
				.pickupDate("2025-01-01")
				.pickupTimeFrom("08:00")
				.deliveryAddress(minimalAddress())
				.grossWeightKg(BigDecimal.ONE)
				.items(ImmutableList.of(JsonDeliveryAdvisorRequestItem.builder()
						.numberOfItems(1)
						.productName("Test Product")
						.productValue("TEST")
						.build()))
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
