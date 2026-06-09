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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonQuantityTest {

    @Test
    void parse_and_toJson_roundtrip() {
        final JsonQuantity qty = JsonQuantity.builder().value(new java.math.BigDecimal("5")).uomCode("PCE").build();
        final String asString = qty.toJson();
        assertThat(asString).isEqualTo("5 PCE");

        final JsonQuantity parsed = JsonQuantity.parseString(asString);
        assertThat(parsed).isEqualTo(qty);
    }

    @Test
    void parse_invalid_throws() {
        assertThatThrownBy(() -> JsonQuantity.parseString("invalid"))
                .isInstanceOf(RuntimeException.class);
    }
}
