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
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryOrderParcelTest {

    private static ObjectMapper mapper() {
        final ObjectMapper m = new ObjectMapper();
        m.findAndRegisterModules();
        return m;
    }

    private static JsonDeliveryOrderLineContents aContent() {
        return JsonDeliveryOrderLineContents.builder()
                .shipmentOrderItemId("10")
                .unitPrice(JsonMoney.builder().amount(new BigDecimal("1.23")).currencyCode("EUR").build())
                .totalValue(JsonMoney.builder().amount(new BigDecimal("2.46")).currencyCode("EUR").build())
                .productName("Prod")
                .productValue("P-1")
				.customsTariff("CT-1")
                .totalWeightInKg(new BigDecimal("0.5"))
                .shippedQuantity(JsonQuantity.builder().value(new BigDecimal("2")).uomCode("PCE").build())
                .build();
    }

    private static JsonDeliveryOrderLineContents content(final String uom, final String totalWeightKg, final String qty) {
        return JsonDeliveryOrderLineContents.builder()
                .shipmentOrderItemId("x")
                .unitPrice(JsonMoney.builder().amount(BigDecimal.ONE).currencyCode("EUR").build())
                .totalValue(JsonMoney.builder().amount(BigDecimal.ONE).currencyCode("EUR").build())
                .productName("Prod")
                .productValue("P")
                .totalWeightInKg(new BigDecimal(totalWeightKg))
                .shippedQuantity(JsonQuantity.builder().value(new BigDecimal(qty)).uomCode(uom).build())
                .build();
    }

    private static JsonDeliveryOrderParcel parcelWith(final BigDecimal grossKg,
                                                      final int lengthCm, final int widthCm, final int heightCm,
                                                      final JsonDeliveryOrderLineContents... contents) {
        return JsonDeliveryOrderParcel.builder()
                .id("P1")
                .grossWeightKg(grossKg)
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(lengthCm).widthInCM(widthCm).heightInCM(heightCm).build())
                .packageId("PKG-1")
                .contents(ImmutableList.copyOf(contents))
                .build();
    }

    @Test
    void builder_and_json_roundtrip() throws Exception {
        final JsonDeliveryOrderParcel parcel = JsonDeliveryOrderParcel.builder()
                .id("P1")
                .content("Box")
                .grossWeightKg(new BigDecimal("1.23"))
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(10).widthInCM(20).heightInCM(30).build())
                .packageId("PKG-1")
                .awb("AWB-1")
                .trackingUrl("http://trk")
                .labelPdfBase64(new byte[]{1,2,3})
                .contents(Collections.singletonList(aContent()))
                .build();

        final String json = mapper().writeValueAsString(parcel);
        final JsonDeliveryOrderParcel back = mapper().readValue(json, JsonDeliveryOrderParcel.class);
        assertThat(back).isEqualTo(parcel);
    }

    @Test
    void getValue_grossWeight_kg_and_g() {
        final JsonDeliveryOrderParcel p = parcelWith(new BigDecimal("2.5"), 10, 20, 30, aContent());

        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG)).contains("2.5");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_G)).contains("2500");
    }

    @Test
    void getValue_packageDimensions_cm_and_mm() {
        final JsonDeliveryOrderParcel p = parcelWith(BigDecimal.ONE, 10, 20, 30, aContent());

        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM)).contains("10");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM)).contains("100");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM)).contains("20");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM)).contains("200");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM)).contains("30");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM)).contains("300");
    }

    @Test
    void getValue_uom_single_value_returns_value() {
        final JsonDeliveryOrderParcel p = parcelWith(BigDecimal.ONE, 1, 1, 1,
                content("PCE", "1", "1"),
                content("PCE", "2", "2"));

        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE)).contains("PCE");
    }

    @Test
    void getValue_uom_mixed_returns_empty() {
        final JsonDeliveryOrderParcel p = parcelWith(BigDecimal.ONE, 1, 1, 1,
                content("PCE", "1", "1"),
                content("KGM", "2", "2"));

        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE)).isEmpty();
    }

    @Test
    void getValue_unitWeight_single_value_kg_and_g() {
        final JsonDeliveryOrderParcel p = parcelWith(BigDecimal.ONE, 1, 1, 1,
                content("PCE", "1", "2"),   // unitWeight = 0.5 kg
                content("PCE", "2", "4"));  // unitWeight = 0.5 kg

        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_KG)).contains("0.5");
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G)).contains("500");
    }

    @Test
    void getValue_unitWeight_mixed_returns_empty() {
        final JsonDeliveryOrderParcel p = parcelWith(BigDecimal.ONE, 1, 1, 1,
                content("PCE", "1", "2"),   // 0.5 kg
                content("PCE", "1", "4"));  // 0.25 kg

        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_KG)).isEmpty();
        assertThat(p.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G)).isEmpty();
    }
}
