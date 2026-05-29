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

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.shipper.client.nshift.json.request.JsonShipmentRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = { NShiftClientConfig.class, NShiftShipmentService.class, NShiftRestClient.class })
@TestPropertySource(properties = {
		"logging.level.de.metas.shipper.client.nshift.NShiftShipmentService=TRACE",
		"logging.level.de.metas.shipper.client.nshift.NShiftRestClient=TRACE"
})
@ExtendWith(SnapshotExtension.class)
public class NShiftShipmentServiceTest
{
	private Expect expect;

	private static final String ACTOR_ID = System.getProperty("nshift.test.actorId", "123"); //nShift portal actorId
	private static final String USERNAME = System.getProperty("nshift.test.username", "nShift portal username");
	private static final String PASSWORD = System.getProperty("nshift.test.password", "nShift portal password");
	private static final String URL = System.getProperty("nshift.test.url", "https://demo.shipmentserver.com:8080");

	private static final JsonDeliveryRequest DELIVERY_REQUEST = JsonDeliveryRequest.builder()
			.deliveryOrderId(1)
			.shipperProduct(JsonShipperProduct.builder().code("10305").name("DHL Freight API - EuroConnect International").build())
			.service(JsonCarrierService.builder().id("972053").name("Ex Works").build())
			.goodsType(JsonGoodsType.builder().id("5").name("Packet").build())
			.pickupAddress(JsonAddress.builder()
					.bpartnerId(123)
					.companyName1("metas GmbH")
					.companyName2("senderCompanyName2")
					.companyDepartment("senderDepartmentName")
					.city("Bonn")
					.country("DE")
					.zipCode("53179")
					.street("Am Noßbacher Weg")
					.additionalAddressInfo("")
					.houseNo("2")
					.attention("z. Hd. Sender Test")
					.build())
			.pickupContact(JsonContact.builder()
					.name("Test Pickup Contact Name")
					.department("Test Contact Department")
					.language("de")
					.phone("12345678")
					.emailAddress("noreply@metasfresh.com")
					.build())
			.pickupDate("2025-10-02")
			.timeFrom("10:00:00")
			.timeTo("13:00:00")
			.pickupNote("Pickup note")
			.deliveryAddress(JsonAddress.builder()
					.bpartnerId(123)
					.companyName1("metas.ro SRL")
					.companyName2("receiverCompanyName2")
					.companyDepartment("receiverDepartmentName")
					.city("Timisoara")
					.country("RO")
					.zipCode("300078")
					.street("Alecsandri")
					.additionalAddressInfo("")
					.houseNo("3")
					.attention("z. Hd. Test")
					.build())
			.deliveryContact(JsonContact.builder()
					.name("Test Delivery Contact Name")
					.department("Test Contact Department")
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
					.grossWeightKg(BigDecimal.valueOf(20))
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
							.customsTariff("Test Customs Tariff 2")
							.totalWeightInKg(BigDecimal.TEN)
							.shippedQuantity(JsonQuantity.builder()
									.value(BigDecimal.TEN)
									.uomCode("PCE")
									.build())
							.build(),
							JsonDeliveryOrderLineContents.builder()
									.shipmentOrderItemId("3")
									.unitPrice(JsonMoney.builder()
											.amount(BigDecimal.TEN)
											.currencyCode("EUR")
											.build())
									.totalValue(JsonMoney.builder()
											.amount(BigDecimal.TEN)
											.currencyCode("EUR")
											.build())
									.productName("Test Product 3")
									.productValue("Test Product 3")
									.customsTariff("Test Customs Tariff 3")
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
					.url(URL)
					.password(PASSWORD)
					.username(USERNAME)
					.additionalProperty(NShiftConstants.ACTOR_ID, ACTOR_ID)
					.additionalProperty(NShiftConstants.IS_CREATE_DRAFT_SHIPMENT_ONLY, "N")
					.build())
			.mappingConfigs(NShiftTestMappingConfigs.SHARED)
			.build();

	@Autowired
	NShiftShipmentService nShiftShipmentService;

	@Test
	@Disabled("This test is only for local testing of changes, we don't want to call an api on each build")
	void local_api_test()
	{
		final JsonDeliveryResponse response = nShiftShipmentService.createShipment(DELIVERY_REQUEST);
		assertNotNull(response);
		assertNotNull(response.getItems().get(0).getTrackingUrl());
		assertFalse(response.isError());
	}

	@Test
	void build_request_test()
	{
		final JsonShipmentRequest request = NShiftShipmentService.buildShipmentRequest(DELIVERY_REQUEST);
		expect.serializer("orderedJson").toMatchSnapshot(request);
	}
}
