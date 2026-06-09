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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonMoneyTest {

    @Test
    void jsonCreator_and_jsonValue_roundtrip() {
        final JsonMoney money = JsonMoney.builder()
                .amount(new BigDecimal("12.34"))
                .currencyCode("EUR")
                .build();

        final String asString = money.toJson();
        assertThat(asString).isEqualTo("12.34 EUR");

        final JsonMoney parsed = JsonMoney.fromJson(asString);
        assertThat(parsed).isEqualTo(money);
    }
}
