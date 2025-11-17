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
                .grossWeightKg(new BigDecimal("7.5"))
                .packageDimensions(JsonPackageDimensions.builder().lengthInCM(40).widthInCM(20).heightInCM(15).build())
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
                .item(anItem())
                .shipperConfig(aConfig())
                .build();

        assertThat(req.getId()).isNotNull().isNotBlank();
    }

    @Test
    void json_roundtrip_preserves_values() throws Exception {
        final JsonDeliveryAdvisorRequest req = JsonDeliveryAdvisorRequest.builder()
                .id("ADV-1")
                .pickupAddress(aPickupAddress())
                .pickupDate("2025-10-01")
                .pickupTimeFrom("10:00")
                .pickupTimeTo("18:00")
                .pickupNote("ready at 10:00")
                .deliveryAddress(aDeliveryAddress())
                .deliveryContact(JsonContact.builder().name("Jane").language("en").build())
                .deliveryDate("2025-10-02")
                .deliveryNote("leave at door")
                .customerReference("SO-123")
                .item(anItem())
                .shipperConfig(JsonShipperConfig.builder().url("https://shipper.local").trackingUrlTemplate("http://trk/{awb}").build())
                .build();

        final String json = mapper().writeValueAsString(req);
        final JsonDeliveryAdvisorRequest back = mapper().readValue(json, JsonDeliveryAdvisorRequest.class);
        assertThat(back).isEqualTo(req);
    }
}
