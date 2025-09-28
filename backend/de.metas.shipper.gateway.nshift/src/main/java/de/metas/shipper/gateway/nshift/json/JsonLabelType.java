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
 * Defines the label formats supported by the nShift API.
 * All label data is Base64 encoded.
 * Unicode characters are only supported for PNG, PDF, and ZPLGK label types.
 */
@RequiredArgsConstructor
@Getter
public enum JsonLabelType {

    /**
     * No label will be returned, but the shipment is still created.
     */
    NONE("none"),

    /**
     * PNG format label. Supports Unicode.
     */
    PNG("PNG"),

    /**
     * PDF format label. Supports Unicode.
     */
    PDF("PDF"),

    /**
     * EPL format for printers like LP2844, ZM400, and GK420D.
     */
    EPL("EPL"),

    /**
     * ZPL format with a top margin of 30. Optimized for LP2844-Z printers.
     * May print too far down on other printers.
     */
    ZPL("ZPL"),

    /**
     * ZPL II format without a top margin. Recommended for printers like GK420D and ZM400. Supports Unicode.
     */
    ZPLGK("ZPLGK"),

    /**
     * Label format for Datamax printers.
     */
    DATAMAXLP2("DATAMAXLP2");

    @JsonValue
    private final String jsonValue;
}
