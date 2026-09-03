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

package de.metas.common.delivery.v1.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonAddressContactTest {

    private static ObjectMapper mapper() {
        final ObjectMapper m = new ObjectMapper();
        m.findAndRegisterModules();
        return m;
        
    }

    @Test
    void address_json_roundtrip() throws Exception {
        final JsonAddress address = JsonAddress.builder()
                .companyName1("Acme GmbH")
                .companyName2("Dept A")
                .companyDepartment("Sales")
                .street("Main")
                .houseNo("1a")
                .additionalAddressInfo("c/o John")
                .country("DE")
                .zipCode("10115")
                .city("Berlin")
                .bpartnerId(123)
                .build();

        final String json = mapper().writeValueAsString(address);
        final JsonAddress back = mapper().readValue(json, JsonAddress.class);
        assertThat(back).isEqualTo(address);
    }

    @Test
    void contact_json_roundtrip() throws Exception {
        final JsonContact contact = JsonContact.builder()
                .name("John Doe")
                .phone("+49 111 222")
                .emailAddress("john@example.com")
                .language("en")
                .build();

        final String json = mapper().writeValueAsString(contact);
        final JsonContact back = mapper().readValue(json, JsonContact.class);
        assertThat(back).isEqualTo(contact);
    }
}
