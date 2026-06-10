/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.externalsystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Identifies the functional context of an external system invocation.
 * Both success and error listeners can filter invocations based on this context.
 */
public enum ExternalSystemInvocationContext
{
	/**
	 * Unknown or unspecified context.
	 * Used as default when no specific context is provided.
	 */
	UNKNOWN("Unknown"),

	/**
	 * EDI (Electronic Data Interchange) export context.
	 * Used for DESADV and INVOIC exports via external system.
	 */
	EDI("EDI"),

	/**
	 * Re-send context.
	 * Used when a record's non-Sent attempt is being re-triggered via the
	 * M_InOut_ReSend_ScriptedExportConversion AD process.
	 */
	RESEND("Resend");

	private final String code;

	ExternalSystemInvocationContext(@NonNull final String code)
	{
		this.code = code;
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}

	@JsonCreator
	public static ExternalSystemInvocationContext ofCodeOrUnknown(@Nullable final String code)
	{
		if (code == null)
		{
			return UNKNOWN;
		}

		return Arrays.stream(values())
				.filter(context -> context.code.equals(code))
				.findFirst()
				.orElse(UNKNOWN);
	}

	public boolean isUnknown()
	{
		return this == UNKNOWN;
	}

	public boolean isEDI()
	{
		return this == EDI;
	}

	public boolean isResend()
	{
		return this == RESEND;
	}
}
