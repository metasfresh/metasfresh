/*
 * #%L
 * de.metas.shipper.client.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.client.nshift;

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = { NShiftClientConfig.class, NShiftShipmentService.class, NShiftRestClient.class })
@TestPropertySource(properties = {
		"logging.level.de.metas.shipper.client.nshift.NShiftShipmentService=TRACE",
		"logging.level.de.metas.shipper.nshift.AbstractNShiftApiClient=TRACE"
})
@Disabled("This test is only for local testing of changes, we don't want to call an api on each build")
public class ShipmentServiceTest
{
	private static final String ACTOR_ID = "nShift portal actorId";
	private static final String USERNAME = "nShift portal username";
	private static final String PASSWORD = "nShift portal password";

	@Autowired
	NShiftShipmentService nShiftShipmentService;

	@Test
	void test()
	{
		final JsonDeliveryResponse response = nShiftShipmentService.createShipment(JsonDeliveryRequest.builder()
				.deliveryOrderId(1)
				.shipperProduct(JsonShipperProduct.builder().code("shipperProductCode").build())
				.pickupAddress(JsonAddress.builder()
						.bpartnerId(123)
						.companyName1("metas GmbH")
						.companyName2("")
						.companyDepartment("")
						.city("Bonn")
						.country("DE")
						.zipCode("53179")
						.street("Am Noßbacher Weg")
						.additionalAddressInfo("")
						.houseNo("2")
						.build())
				.pickupDate("2025-10-02")
				.pickupNote("Pickup note")
				.deliveryAddress(JsonAddress.builder()
						.bpartnerId(123)
						.companyName1("metas.ro SRL")
						.companyName2("")
						.companyDepartment("")
						.city("Timisoara")
						.country("RO")
						.zipCode("300078")
						.street("Alecsandri")
						.additionalAddressInfo("")
						.houseNo("3")
						.build())
				.deliveryContact(JsonContact.builder()
						.name("Test Contact Name")
						.language("de")
						.phone("12341234")
						.emailAddress("noreply@metasfresh.com")
						.build())
				.deliveryDate("2025-10-02")
				.deliveryNote("Delivery note")
				.customerReference("Customer reference")
				.deliveryOrderParcel(JsonDeliveryOrderParcel.builder()
						.id("1")
						.grossWeightKg(BigDecimal.TEN)
						.packageDimensions(JsonPackageDimensions.builder()
								.lengthInCM(100)
								.widthInCM(20)
								.heightInCM(15)
								.build())
						.packageId("1")
						.contents(ImmutableList.of(JsonDeliveryOrderLineContents.builder()
								.shipmentOrderItemId("1")
								.unitPrice(JsonMoney.builder()
										.amount(BigDecimal.TEN)
										.currencyCode("EUR")
										.build())
								.totalValue(JsonMoney.builder()
										.amount(BigDecimal.TEN)
										.currencyCode("EUR")
										.build())
								.productName("Test Product")
								.productValue("Test Product")
								.totalWeightInKg(BigDecimal.TEN)
								.shippedQuantity(JsonQuantity.builder()
										.value(BigDecimal.TEN)
										.uomCode("PCE")
										.build())
								.build()))
						.build())
				.deliveryOrderParcel(JsonDeliveryOrderParcel.builder()
						.id("2")
						.grossWeightKg(BigDecimal.TEN)
						.packageDimensions(JsonPackageDimensions.builder()
								.lengthInCM(100)
								.widthInCM(20)
								.heightInCM(15)
								.build())
						.packageId("2")
						.contents(ImmutableList.of(JsonDeliveryOrderLineContents.builder()
								.shipmentOrderItemId("2")
								.unitPrice(JsonMoney.builder()
										.amount(BigDecimal.TEN)
										.currencyCode("EUR")
										.build())
								.totalValue(JsonMoney.builder()
										.amount(BigDecimal.TEN)
										.currencyCode("EUR")
										.build())
								.productName("Test Product 2")
								.productValue("Test Product 2")
								.totalWeightInKg(BigDecimal.TEN)
								.shippedQuantity(JsonQuantity.builder()
										.value(BigDecimal.TEN)
										.uomCode("PCE")
										.build())
								.build()))
						.build())
				.shipperEORI("Shipper EORI")
				.receiverEORI("Receiver EORI")
				.shipperConfig(JsonShipperConfig.builder()
						.url("https://demo.shipmentserver.com:8080")
						.password(PASSWORD)
						.username(USERNAME)
						.additionalProperty(NShiftConstants.ACTOR_ID, ACTOR_ID)
						.build())
				.goodsType(JsonGoodsType.builder().id("5").name("Packet").build())
				.shipperProduct(JsonShipperProduct.builder().code("2757").build())
				.build());
		assertThat(response).isNotNull();
		assertFalse(response.isError());
	}
}
