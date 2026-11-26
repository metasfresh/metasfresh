/*
 * #%L
 * de-metas-common-delivery
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

package de.metas.common.delivery.v1.json.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryAdvisorRequestTest {

    private static ObjectMapper mapper() {
        final ObjectMapper m = new ObjectMapper();
        m.findAndRegisterModules();
        return m;
    }

    private static JsonAddress aPickupAddress() {
        return JsonAddress.builder()
                .companyName1("Warehouse A")
                .country("DE")
                .zipCode("10115")
                .city("Berlin")
                .build();
    }

    private static JsonAddress aDeliveryAddress() {
        return JsonAddress.builder()
                .companyName1("Customer B")
                .country("DE")
                .zipCode("20095")
                .city("Hamburg")
                .build();
    }

    private static JsonDeliveryAdvisorRequestItem anItem() {
        return JsonDeliveryAdvisorRequestItem.builder()
                .numberOfItems(2)
                .productName("P-1")
                .productValue("P-1")
                .build();
    }

    private static JsonShipperConfig aConfig() {
        return JsonShipperConfig.builder()
                .url("https://shipper.local")
                .build();
    }

    @Test
    void builder_sets_default_id() {
        final JsonDeliveryAdvisorRequest req = JsonDeliveryAdvisorRequest.builder()
                .pickupAddress(aPickupAddress())
                .pickupDate("2025-10-01")
                .pickupTimeFrom("09:00")
                .pickupTimeTo("17:00")
                .deliveryAddress(aDeliveryAddress())
                .grossWeightKg(new BigDecimal("7.5"))
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(40).widthInCM(20).heightInCM(15).build())
                .items(ImmutableList.of(anItem()))
                .shipperConfig(aConfig())
                .build();

        assertThat(req.getId()).isNotNull().isNotBlank();
    }

    @Test
    void json_roundtrip_preserves_values() throws Exception {
        final JsonDeliveryAdvisorRequest req = JsonDeliveryAdvisorRequest.builder()
                .id("ADV-1")
                .pickupAddress(aPickupAddress())
                .pickupContact(JsonContact.builder().name("John").language("en").build())
                .pickupDate("2025-10-01")
                .pickupTimeFrom("10:00")
                .pickupTimeTo("18:00")
                .pickupNote("ready at 10:00")
                .deliveryAddress(aDeliveryAddress())
                .deliveryContact(JsonContact.builder().name("Jane").language("en").build())
                .deliveryDate("2025-10-02")
                .deliveryNote("leave at door")
                .customerReference("SO-123")
                .grossWeightKg(new BigDecimal("7.5"))
                .topLevelType("LU")
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(40).widthInCM(20).heightInCM(15).build())
                .items(ImmutableList.of(anItem()))
                .shipperConfig(JsonShipperConfig.builder().url("https://shipper.local").trackingUrlTemplate("http://trk/{awb}").build())
                .build();

        final String json = mapper().writeValueAsString(req);
        final JsonDeliveryAdvisorRequest back = mapper().readValue(json, JsonDeliveryAdvisorRequest.class);
        assertThat(back).isEqualTo(req);
    }

    @Test
    void getValue_parcelLevel_topLevelType() {
        final JsonDeliveryAdvisorRequest withType = baseRequest()
                .topLevelType("LU")
                .build();
        assertThat(withType.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOP_LEVEL_TYPE)).isEqualTo("LU");

        final JsonDeliveryAdvisorRequest withoutType = baseRequest().build();
        assertThat(withoutType.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOP_LEVEL_TYPE)).isNull();
    }

    @Test
    void getValue_parcelLevel_weight() {
        final JsonDeliveryAdvisorRequest req = baseRequest().grossWeightKg(new BigDecimal("7.5")).build();
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG)).isEqualTo("7.5");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_G)).isEqualTo("7500");
    }

    @Test
    void getValue_parcelLevel_dimensions_present() {
        final JsonDeliveryAdvisorRequest req = baseRequest()
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(60).widthInCM(40).heightInCM(30).build())
                .build();
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM)).isEqualTo("60");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM)).isEqualTo("600");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM)).isEqualTo("40");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM)).isEqualTo("400");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM)).isEqualTo("30");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM)).isEqualTo("300");
    }

    @Test
    void getValue_parcelLevel_dimensions_null() {
        final JsonDeliveryAdvisorRequest req = baseRequest().build();
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM)).isNull();
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM)).isNull();
    }

    @Test
    void getValue_aggregatesAcrossItems() {
        final JsonDeliveryAdvisorRequestItem item1 = JsonDeliveryAdvisorRequestItem.builder()
                .numberOfItems(1)
                .productName("Prod A")
                .productValue("A-1")
                .totalValue(JsonMoney.builder().amount(new BigDecimal("10")).currencyCode("EUR").build())
                .shippedQuantity(JsonQuantity.builder().value(new BigDecimal("1")).uomCode("PCE").build())
                .build();
        final JsonDeliveryAdvisorRequestItem item2 = JsonDeliveryAdvisorRequestItem.builder()
                .numberOfItems(1)
                .productName("Prod B")
                .productValue("B-1")
                .totalValue(JsonMoney.builder().amount(new BigDecimal("15")).currencyCode("EUR").build())
                .shippedQuantity(JsonQuantity.builder().value(new BigDecimal("3")).uomCode("PCE").build())
                .build();

        final JsonDeliveryAdvisorRequest req = baseRequest()
                .items(ImmutableList.of(item1, item2))
                .build();

        // product name/value concatenated with comma
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME)).isEqualTo("Prod A,Prod B");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE)).isEqualTo("A-1,B-1");
        // total value summed
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE)).isEqualTo("25");
        // single common currency / uom
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE)).isEqualTo("EUR");
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE)).isEqualTo("PCE");
    }

    @Test
    void getValue_totalValue_sumsEqualAmounts_doesNotDeduplicate() {
        // Two items with the SAME total value must sum to 2x — not be collapsed to one (the ImmutableSet trap).
        final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
                .numberOfItems(1)
                .productName("Prod")
                .productValue("P-1")
                .totalValue(JsonMoney.builder().amount(new BigDecimal("10.00")).currencyCode("EUR").build())
                .build();

        final JsonDeliveryAdvisorRequest req = baseRequest()
                .items(ImmutableList.of(item, item))
                .build();

        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE)).isEqualTo("20.00");
    }

    @Test
    void getValue_totalValue_nullWhenNoItemCarriesValue() {
        final JsonDeliveryAdvisorRequest req = baseRequest().build(); // anItem() has no totalValue
        assertThat(req.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE)).isNull();
    }

    private static JsonDeliveryAdvisorRequest.JsonDeliveryAdvisorRequestBuilder baseRequest() {
        return JsonDeliveryAdvisorRequest.builder()
                .id("ADV-1")
                .pickupAddress(aPickupAddress())
                .pickupDate("2025-10-01")
                .pickupTimeFrom("09:00")
                .pickupTimeTo("17:00")
                .deliveryAddress(aDeliveryAddress())
                .grossWeightKg(BigDecimal.ONE)
                .items(ImmutableList.of(anItem()))
                .shipperConfig(aConfig());
    }
}
