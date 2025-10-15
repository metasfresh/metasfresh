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
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryRequestTest {

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

    private static JsonDeliveryOrderParcel aParcel() {
        return JsonDeliveryOrderParcel.builder()
                .id("1")
                .grossWeightKg(new BigDecimal("3.5"))
                .packageId("PKG-1")
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(10).widthInCM(20).heightInCM(30).build())
                .contents(java.util.Collections.emptyList())
                .build();
    }

    private static JsonShipperConfig aConfig() {
        return JsonShipperConfig.builder()
                .url("https://shipper.local")
                .build();
    }

    @Test
    void builder_sets_default_id() {
        final JsonDeliveryRequest req = JsonDeliveryRequest.builder()
                .deliveryOrderId(123)
                .pickupAddress(aPickupAddress())
                .pickupDate("2025-10-01")
                .deliveryAddress(aDeliveryAddress())
                .deliveryOrderParcel(aParcel())
                .shipperConfig(aConfig())
                .build();

        assertThat(req.getId()).isNotNull().isNotBlank();
    }

    @Test
    void json_roundtrip_preserves_values() throws Exception {
        final JsonDeliveryRequest req = JsonDeliveryRequest.builder()
                .id("REQ-1")
                .deliveryOrderId(456)
                .pickupAddress(aPickupAddress())
                .pickupDate("2025-10-01")
                .pickupNote("ready at 10:00")
                .deliveryAddress(aDeliveryAddress())
                .deliveryContact(JsonContact.builder().name("Jane").language("en").build())
                .deliveryDate("2025-10-02")
                .deliveryNote("leave at door")
                .customerReference("SO-123")
                .deliveryOrderParcel(aParcel())
                .shipperProduct(JsonShipperProduct.builder().code("EXPRESS").name("EXPRESS").build())
                .shipperEORI("DE123")
                .receiverEORI("DE999")
                .shipperConfig(JsonShipperConfig.builder().url("https://shipper.local").trackingUrlTemplate("http://trk/{awb}").build())
                .build();

        final String json = mapper().writeValueAsString(req);
        final JsonDeliveryRequest back = mapper().readValue(json, JsonDeliveryRequest.class);
        assertThat(back).isEqualTo(req);
    }
}
