/*
 * #%L
 * de.metas.shipper.gateway.nshift
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
package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Used in the References array in the Lines object.
 */
@RequiredArgsConstructor
@Getter
public enum JsonLineReferenceKind {

    ESRK_CONTENTS(23),
    ESRK_MARKING(24),
    ESRK_LINE_CARRIER_SPECIFIC_1(103),
    ESRK_LINE_CARRIER_SPECIFIC_2(104),
    ESRK_LINE_CARRIER_SPECIFIC_3(105),
    ESRK_LINE_CARRIER_SPECIFIC_4(106),
    ESRK_LINE_CARRIER_SPECIFIC_5(107),
    ESRK_CUSTOM_LINE_FIELD_1(129),
    ESRK_CUSTOM_LINE_FIELD_2(130),
    ESRK_CUSTOM_LINE_FIELD_3(131),
    ESRK_CUSTOM_LINE_FIELD_4(132),
    ESRK_CUSTOM_LINE_FIELD_5(133),
    ESRK_CUSTOM_LINE_FIELD_6(221),
    ESRK_CUSTOM_LINE_FIELD_7(222),
    ESRK_CUSTOM_LINE_FIELD_8(223),
    ESRK_CUSTOM_LINE_FIELD_9(224),
    ESRK_CUSTOM_LINE_FIELD_10(225),
    ESRK_CUSTOM_LINE_FIELD_11(226),
    ESRK_CUSTOM_LINE_FIELD_12(227),
    ESRK_CUSTOM_LINE_FIELD_13(228),
    ESRK_CUSTOM_LINE_FIELD_14(229),
    ESRK_CUSTOM_LINE_FIELD_15(230),
    ESRK_CUSTOM_LINE_FIELD_16(231),
    ESRK_CUSTOM_LINE_FIELD_17(232),
    ESRK_CUSTOM_LINE_FIELD_18(233),
    ESRK_CUSTOM_LINE_FIELD_19(234),
    ESRK_CUSTOM_LINE_FIELD_20(235);

    @JsonValue
    private final int jsonValue;
}