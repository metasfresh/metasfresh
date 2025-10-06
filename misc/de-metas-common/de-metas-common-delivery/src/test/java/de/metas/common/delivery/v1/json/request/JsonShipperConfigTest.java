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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JsonShipperConfigTest {

    private static ObjectMapper mapper() {
        final ObjectMapper m = new ObjectMapper();
        m.findAndRegisterModules();
        return m;
    }

    @Test
    void additionalProperties_and_json_roundtrip() throws Exception {
        final JsonShipperConfig cfg = JsonShipperConfig.builder()
                .url("https://api.example.com")
                .username("user")
                .password("secret")
                .clientId("client")
                .clientSecret("clientSecret")
                .trackingUrlTemplate("http://trk/{id}")
                .additionalProperty("k1", "v1")
                .additionalProperty("k2", "v2")
                .build();

        assertThat(cfg.getAdditionalProperties()).containsEntry("k1", "v1").containsEntry("k2", "v2");

        final String json = mapper().writeValueAsString(cfg);
        final JsonShipperConfig back = mapper().readValue(json, JsonShipperConfig.class);
        assertThat(back).isEqualTo(cfg);

        // also verify JSON contains the map entries
        final Map<String, Object> tree = mapper().readValue(json, new TypeReference<Map<String, Object>>(){});
        assertThat(tree).containsKeys("additionalProperties");
    }
}
