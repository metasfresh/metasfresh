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

/**
 * JSON type for R_Request.ConfidentialType values.
 * <p>
 * Behavior:
 * - Deserialization: accepts a human-readable name as used in the generated constants of X_R_Request
 * (i.e., the part after the {@code CONFIDENTIALTYPE_} prefix: {@code PublicInformation}, {@code PartnerConfidential},
 * {@code Internal}, {@code PrivateInformation}); it also accepts the raw AD reference code values (A/C/I/P).
 * - Serialization: outputs the AD reference code value (A/C/I/P).
 */
@Getter
@RequiredArgsConstructor
public enum JsonConfidentialType
{
	// Keep codes in sync with org.compiere.model.X_R_Request#CONFIDENTIALTYPE_*
	PublicInformation("A"),
	PartnerConfidential("C"),
	Internal("I"),
	PrivateInformation("P");

	private final String code;

	/**
	 * Returns the AD reference code (A/C/I/P) when serializing to JSON.
	 */
	@JsonValue
	public String toJson()
	{
		return code;
	}

	/**
	 * Accepts either the human-readable constant name (e.g. "PublicInformation") or the code (e.g. "A").
	 */
	@JsonCreator
	public static JsonConfidentialType fromJson(final String value)
	{
		if (value == null)
		{
			return null;
		}

		// Try by code first
		for (final JsonConfidentialType t : values())
		{
			if (t.code.equalsIgnoreCase(value))
			{
				return t;
			}
		}

		// Then by enum name (human-readable constant without the generated prefix)
		for (final JsonConfidentialType t : values())
		{
			if (t.name().equalsIgnoreCase(value))
			{
				return t;
			}
		}

		throw new IllegalArgumentException("Unknown ConfidentialType: " + value);
	}
}
