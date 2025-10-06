package de.metas.shipper.gateway.spi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryCode;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.mpackage.PackageId;
import lombok.NonNull;
import org.junit.jupiter.api.Test;
import shadow.org.assertj.core.api.Assertions;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

class DeliveryOrderTest
{
	@Test
	public void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserialize(
				DeliveryOrder.builder()
						.id(DeliveryOrderId.ofRepoId(123))
						//
						.deliveryAddress(address("delivery"))
						.deliveryContact(ContactPerson.builder()
								.name("testContact")
								.simplePhoneNumber("+49123456789")
								.emailAddress("test@test.com")
								.languageCode("de_DE")
								.build())
						.deliveryDate(DeliveryDate.builder()
								.date(LocalDate.parse("2025-04-05"))
								.timeFrom(LocalTime.of(12, 13))
								.timeTo(LocalTime.of(18, 19))
								.build())
						.deliveryNote("delivery note")
						//
						.pickupAddress(address("pickup"))
						.pickupDate(PickupDate.builder()
								.date(LocalDate.parse("2025-04-05"))
								.timeFrom(LocalTime.of(12, 13))
								.timeTo(LocalTime.of(18, 19))
								.build())
						// other
						.customerReference("customerReference")
						.shipperProduct(null)
						.deliveryOrderParcels(ImmutableList.of(
								DeliveryOrderParcel.builder()
										.content("content")
										.grossWeightKg(new BigDecimal("123.45"))
										.packageDimensions(PackageDimensions.builder()
												.lengthInCM(1)
												.heightInCM(2)
												.widthInCM(3)
												.build())
										.customDeliveryLineData(null)
										.packageId(PackageId.ofRepoId(9))
										.build()
						))
						.shipperId(ShipperId.ofRepoId(2))
						.shipperTransportationId(ShipperTransportationId.ofRepoId(3))
						.trackingNumber("trackingNumber")
						.trackingUrl("trackingUrl")
						.build()
		);
	}

	private static Address address(final String prefix)
	{
		return Address.builder()
				.bpartnerId(BPartnerId.ofRepoId(55))
				.companyName1(prefix + " - companyName1")
				.companyName2(prefix + " - companyName2")
				.street1(prefix + " - street1")
				.street2(prefix + " - street2")
				.houseNo(prefix + " - houseNo")
				.zipCode(prefix + "-123456")
				.city(prefix + " - city")
				.country(CountryCode.DE)
				.build();
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