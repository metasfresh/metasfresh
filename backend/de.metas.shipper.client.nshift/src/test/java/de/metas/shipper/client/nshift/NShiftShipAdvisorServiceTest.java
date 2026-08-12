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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.shipper.client.nshift.json.JsonDetail;
import de.metas.shipper.client.nshift.json.JsonDetailGroup;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonReference;
import de.metas.shipper.client.nshift.json.request.JsonShipAdvisorRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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
					.attention("Attention Sender Test")
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
					.attention("Attention Test")
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
			.preAdviceRequired("Y")
			.grossWeightKg(BigDecimal.TEN)
			.packageDimensions(JsonPackageDimensions.builder()
					.lengthInCM(100)
					.widthInCM(20)
					.heightInCM(15)
					.build())
			.items(ImmutableList.of(JsonDeliveryAdvisorRequestItem.builder()
					.numberOfItems(1)
					.productName("Test Product")
					.productValue("Test Product Value")
					.build()))
			.shipperConfig(JsonShipperConfig.builder()
					.url(URL)
					.password(PASSWORD)
					.username(USERNAME)
					.additionalProperty(NShiftConstants.ACTOR_ID, ACTOR_ID)
					.additionalProperty(NShiftConstants.SERVICE_LEVEL, SHIP_RULE_SERVICE_LEVEL)
					.build())
			.mappingConfigs(NShiftTestMappingConfigs.SHARED_TEST)
			.build();

	@Autowired
	@NonNull
	private NShiftShipAdvisorService nShiftShipAdvisorService;

	@Autowired
	@Qualifier(NShiftClientConfig.NSHIFT_OBJECT_MAPPER)
	private ObjectMapper nShiftObjectMapper;

	private static final Pattern EMPTY_JSON_ARRAY = Pattern.compile("\\[\\s*\\]");

	@SuppressWarnings("unused") // injected by SnapshotExtension via reflection
	private Expect expect;

	// Two products on one parcel: parcel-level weight/dims describe the whole HU; each product's per-unit values
	// are resolved on the (single) line via the line references / detail-group fallback chain.
	private static final JsonDeliveryAdvisorRequest ADVISOR_REQUEST_TWO_ITEMS = ADVISOR_REQUEST.toBuilder()
			.grossWeightKg(new BigDecimal("12"))
			.packageDimensions(JsonPackageDimensions.builder()
					.lengthInCM(80)
					.widthInCM(40)
					.heightInCM(30)
					.build())
			.items(ImmutableList.of(
					JsonDeliveryAdvisorRequestItem.builder()
							.numberOfItems(1)
							.productName("Product A")
							.productValue("ValueA")
							.totalValue(JsonMoney.builder().amount(new BigDecimal("10")).currencyCode("EUR").build())
							.unitPrice(JsonMoney.builder().amount(new BigDecimal("10")).currencyCode("EUR").build())
							.shippedQuantity(JsonQuantity.builder().value(new BigDecimal("1")).uomCode("PCE").build())
							.totalWeightInKg(new BigDecimal("5"))
							.customsTariff("11111111")
							.countryOfOrigin("DE")
							.build(),
					JsonDeliveryAdvisorRequestItem.builder()
							.numberOfItems(1)
							.productName("Product B")
							.productValue("ValueB")
							.totalValue(JsonMoney.builder().amount(new BigDecimal("15")).currencyCode("EUR").build())
							.unitPrice(JsonMoney.builder().amount(new BigDecimal("15")).currencyCode("EUR").build())
							.shippedQuantity(JsonQuantity.builder().value(new BigDecimal("1")).uomCode("PCE").build())
							.totalWeightInKg(new BigDecimal("7"))
							.customsTariff("22222222")
							.countryOfOrigin("DE")
							.build()))
			.build();

	@Test
	void serializedRequestHasNoEmptyLists() throws Exception
	{
		final JsonShipAdvisorRequest request = NShiftShipAdvisorService.buildRequest(ADVISOR_REQUEST);
		final String json = nShiftObjectMapper.writeValueAsString(request);
		assertNoEmptyJsonArrays(json);
	}

	private static void assertNoEmptyJsonArrays(final String json)
	{
		assertFalse(EMPTY_JSON_ARRAY.matcher(json).find(),
				"nShift request must not serialize empty lists (nShift fails with 'list index out of range'):\n" + json);
	}

	@Test
	void build_request_test()
	{
		final JsonShipAdvisorRequest request = NShiftShipAdvisorService.buildRequest(ADVISOR_REQUEST);
		expect.serializer("orderedJson").toMatchSnapshot(request);
	}

	@Test
	void build_request_two_items_parcel_and_per_item()
	{
		final JsonShipAdvisorRequest request = NShiftShipAdvisorService.buildRequest(ADVISOR_REQUEST_TWO_ITEMS);

		assertThat(request.getData().getLines())
				.as("advise emits exactly one line for the parcel")
				.hasSize(1);
		final JsonLine line = request.getData().getLines().get(0);

		// PARCEL level: weight kg→g and dims cm→mm come from the parcel-level fields (whole HU).
		assertThat(line.getLineWeight()).as("parcel gross weight 12kg → 12000g").isEqualTo(12000);
		assertThat(line.getLength()).as("parcel length 80cm → 800mm").isEqualTo(800);
		assertThat(line.getWidth()).as("parcel width 40cm → 400mm").isEqualTo(400);
		assertThat(line.getHeight()).as("parcel height 30cm → 300mm").isEqualTo(300);

		// PER-ITEM level: both products' values are resolved on the line references.
		// SHARED_TEST maps line references: ProductName→kind 23, ProductValue→kind 132, TotalValue→kind 130.
		final Map<Integer, String> refsByKind = line.getReferences().stream()
				.collect(Collectors.toMap(JsonReference::getKind, JsonReference::getValue));

		// kind 23 = product name (LINE_REFERENCE_KIND_CONTENTS) — both products aggregated (comma-joined by getValue)
		assertThat(refsByKind.get(23)).as("both product names present").isEqualTo("Product A,Product B");
		// kind 132 = product value (CUSTOM_FIELD_4) — both products aggregated
		assertThat(refsByKind.get(132)).as("both product values present").isEqualTo("ValueA,ValueB");
		// kind 130 = total value (CUSTOM_FIELD_2) — per-item totals summed (10 + 15 = 25)
		assertThat(refsByKind.get(130)).as("total value summed across items").isEqualTo("25");
		// kind 134 = gross weight kg (CUSTOM_FIELD_6) — PARCEL-level, resolved ONCE (not duplicated per item)
		assertThat(refsByKind.get(134)).as("parcel gross weight resolved once").isEqualTo("12");
	}

	@Test
	void build_request_emits_detail_groups_like_ship_path()
	{
		final JsonShipAdvisorRequest request = NShiftShipAdvisorService.buildRequest(ADVISOR_REQUEST_TWO_ITEMS);

		final List<JsonDetailGroup> detailGroups = request.getData().getDetailGroups();
		assertThat(detailGroups)
				.as("advise must carry detail groups (preview of what ship sends to nShift)")
				.isNotEmpty();

		// LINE_DETAIL_GROUP: group "1" (customs article) — single advise parcel ⇒ lineNo = 1.
		final JsonDetailGroup lineGroup = detailGroups.stream()
				.filter(g -> NShiftTestMappingConfigs.DETAIL_GROUP_KEY_CUSTOMS_ARTICLE.equals(g.getGroupID()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("expected a LINE_DETAIL_GROUP with groupID="
						+ NShiftTestMappingConfigs.DETAIL_GROUP_KEY_CUSTOMS_ARTICLE));

		assertThat(lineGroup.getRows())
				.as("one detail row per product item")
				.hasSize(2);

		// Each row: lineNo = 1 (single parcel), the eDekGoodsLineNo special detail (kindId 193 = lineNo),
		// and the resolved per-product values (description of goods = product name).
		final Map<String, Map<Integer, String>> rowsByProductName = lineGroup.getRows().stream()
				.collect(Collectors.toMap(
						row -> row.getDetails().stream()
								.filter(d -> d.getKindId() == Integer.parseInt(NShiftTestMappingConfigs.DETAIL_KIND_DESCRIPTION_OF_GOODS))
								.map(JsonDetail::getValue)
								.findFirst().orElse(null),
						row -> {
							assertThat(row.getLineNo()).as("single advise parcel ⇒ lineNo 1").isEqualTo(1);
							return row.getDetails().stream()
									.collect(Collectors.toMap(
											JsonDetail::getKindId,
											JsonDetail::getValue,
											(a, b) -> a));
						}));

		assertThat(rowsByProductName.keySet())
				.as("both products resolved as LINE detail rows")
				.containsExactlyInAnyOrder("Product A", "Product B");

		final int kindUnitValue = Integer.parseInt(NShiftTestMappingConfigs.DETAIL_KIND_UNIT_VALUE);
		final int kindQuantity = Integer.parseInt(NShiftTestMappingConfigs.DETAIL_KIND_QUANTITY);
		final int kindLineNo = 193;
		// Product A
		assertThat(rowsByProductName.get("Product A").get(kindUnitValue)).as("Product A unit price").isEqualTo("10");
		assertThat(rowsByProductName.get("Product A").get(kindQuantity)).as("Product A shipped qty").isEqualTo("1");
		assertThat(rowsByProductName.get("Product A").get(kindLineNo)).as("Product A eDekGoodsLineNo").isEqualTo("1");
		// Product B
		assertThat(rowsByProductName.get("Product B").get(kindUnitValue)).as("Product B unit price").isEqualTo("15");
		assertThat(rowsByProductName.get("Product B").get(kindQuantity)).as("Product B shipped qty").isEqualTo("1");

		// Shipment-level DETAIL_GROUP: group "2" (customs info). SHARED_TEST maps SenderCountryCode→ShipperEORI
		// for receiver-country RO, so a shipment-level group is present (single row, no lineNo).
		final JsonDetailGroup shipmentGroup = detailGroups.stream()
				.filter(g -> NShiftTestMappingConfigs.DETAIL_GROUP_KEY_CUSTOMS_INFO.equals(g.getGroupID()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("expected a shipment-level DETAIL_GROUP with groupID="
						+ NShiftTestMappingConfigs.DETAIL_GROUP_KEY_CUSTOMS_INFO));
		assertThat(shipmentGroup.getRows()).as("shipment-level group has one row").hasSize(1);
		assertThat(shipmentGroup.getRows().get(0).getLineNo()).as("shipment-level row carries no lineNo").isNull();
	}

	@Test
	@Disabled("This test is only for local testing of changes, we don't want to call an api on each build")
	void local_api_test()
	{
		final JsonDeliveryAdvisorResponse response = nShiftShipAdvisorService.advise(
				ADVISOR_REQUEST.toBuilder().mappingConfigs(NShiftTestMappingConfigs.SHARED_DB).build());
		assertNotNull(response);
		assertFalse(response.isError());
	}
}
