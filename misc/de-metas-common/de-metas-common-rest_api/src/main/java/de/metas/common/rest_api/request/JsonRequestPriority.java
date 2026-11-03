/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

/**
 * JSON type for R_Request.Priority values (AD_Reference _PriorityRule).
 *
 * Behavior:
 * - Deserialization: accepts a human-readable name as used in the generated constants of X_R_Request
 *   (i.e., the part after the {@code PRIORITY_} prefix: {@code Urgent}, {@code High}, {@code Medium}, {@code Low}, {@code Minor});
 *   it also accepts the raw AD reference code values ("1","3","5","7","9").
 * - Serialization: outputs the AD reference code value ("1","3","5","7","9").
 */
@Getter
@RequiredArgsConstructor
public enum JsonRequestPriority
{
    // Keep codes in sync with org.compiere.model.X_R_Request#PRIORITY_*
    Urgent("1"),
    High("3"),
    Medium("5"),
    Low("7"),
    Minor("9");

    private final String code;

    /**
     * Returns the AD reference code ("1","3","5","7","9") when serializing to JSON.
     */
    @JsonValue
    public String toJson()
    {
        return code;
    }

    /**
     * Accepts either the human-readable constant name (e.g. "High") or the code (e.g. "3").
     */
    @JsonCreator
	@Nullable
    public static JsonRequestPriority fromJson(final String value)
    {
        if (value == null)
        {
            return null;
        }

        // Try by code first
        for (final JsonRequestPriority t : values())
        {
            if (t.code.equalsIgnoreCase(value))
            {
                return t;
            }
        }

        // Then by enum name (human-readable constant without the generated prefix)
        for (final JsonRequestPriority t : values())
        {
            if (t.name().equalsIgnoreCase(value))
            {
                return t;
            }
        }

        throw new IllegalArgumentException("Unknown Request Priority: " + value);
    }
}
