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
                .unitPrice(de.metas.common.delivery.v1.json.JsonMoney.builder().amount(new BigDecimal("1.23")).currencyCode("EUR").build())
                .totalValue(de.metas.common.delivery.v1.json.JsonMoney.builder().amount(new BigDecimal("2.46")).currencyCode("EUR").build())
                .productName("Prod")
                .productValue("P-1")
                .totalWeightInKg(new BigDecimal("0.5"))
                .shippedQuantity(JsonQuantity.builder().value(new BigDecimal("2")).uomCode("PCE").build())
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
}
