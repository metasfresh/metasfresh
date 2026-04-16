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
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.shipper.client.nshift.json.request.JsonShipAdvisorRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = { NShiftClientConfig.class, NShiftShipAdvisorService.class, NShiftRestClient.class })
@TestPropertySource(properties = {
		"logging.level.de.metas.shipper.client.nshift.NShiftShipAdvisorService=TRACE",
		"logging.level.de.metas.shipper.client.nshift.NShiftRestClient=TRACE"
})
@ExtendWith(SnapshotExtension.class)
public class NShiftShipAdvisorServiceTest
{
	private static final String ACTOR_ID = System.getProperty("nshift.test.actorId", "nShift portal actorId");
	private static final String USERNAME = System.getProperty("nshift.test.username", "nShift portal username");
	private static final String PASSWORD = System.getProperty("nshift.test.password", "nShift portal password");
	private static final String URL = System.getProperty("nshift.test.url", "https://demo.shipmentserver.com:8080");
	private static final String SHIP_RULE_SERVICE_LEVEL = System.getProperty("nshift.test.serviceLevel", "Test");

	private static final JsonDeliveryAdvisorRequest ADVISOR_REQUEST = JsonDeliveryAdvisorRequest.builder()
			.id("randomUUID")
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
			.pickupContact(JsonContact.builder()
					.name("Test Pickup Contact Name")
					.department("Test Department")
					.language("de")
					.phone("12345678")
					.emailAddress("noreply@metasfresh.com")
					.build())
			.pickupDate("2025-10-02")
			.pickupTimeFrom("08:00:00")
			.pickupTimeTo("17:00")
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
					.name("Test Delivery Contact Name")
					.department("Test Department")
					.language("de")
					.phone("12341234")
					.emailAddress("noreply@metasfresh.com")
					.build())
			.deliveryDate("2025-10-02")
			.deliveryNote("Delivery note")
			.customerReference("Customer reference")
			.incotermsValue("incoterms")
			.externalSystemValue("Other")
			.item(JsonDeliveryAdvisorRequestItem.builder()
					.numberOfItems(1)
					.grossWeightKg(BigDecimal.TEN)
					.packageDimensions(JsonPackageDimensions.builder()
							.lengthInCM(100)
							.widthInCM(20)
							.heightInCM(15)
							.build())
					.build())
			.shipperConfig(JsonShipperConfig.builder()
					.url(URL)
					.password(PASSWORD)
					.username(USERNAME)
					.additionalProperty(NShiftConstants.ACTOR_ID, ACTOR_ID)
					.additionalProperty(NShiftConstants.SERVICE_LEVEL, SHIP_RULE_SERVICE_LEVEL)
					.build())
			.build();

	@Autowired
	private NShiftShipAdvisorService nShiftShipAdvisorService;
	private Expect expect;

	@Test
	void build_request_test()
	{
		final JsonShipAdvisorRequest request = NShiftShipAdvisorService.buildRequest(ADVISOR_REQUEST);
		expect.serializer("orderedJson").toMatchSnapshot(request);
	}

	@Test
	@Disabled("This test is only for local testing of changes, we don't want to call an api on each build")
	void local_api_test()
	{
		final JsonDeliveryAdvisorResponse response = nShiftShipAdvisorService.advise(ADVISOR_REQUEST);
		assertNotNull(response);
		assertFalse(response.isError());
	}
}
