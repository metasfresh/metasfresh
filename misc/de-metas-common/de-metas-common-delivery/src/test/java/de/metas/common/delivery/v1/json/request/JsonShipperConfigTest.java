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

import javax.annotation.Nullable;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JsonShipperConfigTest {

    private static JsonShipperConfig baseConfig(@Nullable final String existingServiceLevel)
    {
        final JsonShipperConfig.JsonShipperConfigBuilder b = JsonShipperConfig.builder()
                .url("https://api.nshift.example")
                .username("user")
                .password("pass")
                .clientId("cid")
                .clientSecret("csecret")
                .trackingUrlTemplate("https://track.example/{awb}")
                .additionalProperty("ActorId", "ACT123");
        if (existingServiceLevel != null)
        {
            b.additionalProperty("ServiceLevel", existingServiceLevel);
        }
        return b.build();
    }

    @Test
    void withAdditionalProperty_overridesExistingKey()
    {
        final JsonShipperConfig base = baseConfig("OLD_LEVEL");
        final JsonShipperConfig result = base.withAdditionalProperty("ServiceLevel", "NEW_LEVEL");

        assertThat(result.getAdditionalProperty("ServiceLevel")).isEqualTo("NEW_LEVEL");
        assertThat(result.getAdditionalProperty("ActorId")).isEqualTo("ACT123");
    }

    @Test
    void withAdditionalProperty_addsNewKey()
    {
        final JsonShipperConfig base = baseConfig(null);
        final JsonShipperConfig result = base.withAdditionalProperty("ServiceLevel", "EXPRESS");

        assertThat(result.getAdditionalProperty("ServiceLevel")).isEqualTo("EXPRESS");
        assertThat(result.getAdditionalProperty("ActorId")).isEqualTo("ACT123");
    }

    @Test
    void withAdditionalProperty_preservesAllBaseFields()
    {
        final JsonShipperConfig base = baseConfig(null);
        final JsonShipperConfig result = base.withAdditionalProperty("ServiceLevel", "STANDARD");

        assertThat(result.getUrl()).isEqualTo(base.getUrl());
        assertThat(result.getUsername()).isEqualTo(base.getUsername());
        assertThat(result.getPassword()).isEqualTo(base.getPassword());
        assertThat(result.getClientId()).isEqualTo(base.getClientId());
        assertThat(result.getClientSecret()).isEqualTo(base.getClientSecret());
        assertThat(result.getTrackingUrlTemplate()).isEqualTo(base.getTrackingUrlTemplate());
    }

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
