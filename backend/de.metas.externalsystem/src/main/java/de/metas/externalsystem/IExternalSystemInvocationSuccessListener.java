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

import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

/**
 * Listener interface for external system invocation successes.
 * Implementations can handle success callbacks specific to their domain (e.g., EDI export status updates).
 * <p>
 * This listener is invoked when the external system REST API receives a success callback
 * (e.g., from Camel route processing completions).
 * <p>
 * Listeners should implement {@link #applies(ExternalSystemInvocationContext)} to check if they should handle
 * successes for a given context.
 */
public interface IExternalSystemInvocationSuccessListener
{
	/**
	 * Checks if this listener applies to the given invocation context.
	 *
	 * @param context the invocation context (EDI, Resend, etc.), never null (defaults to UNKNOWN)
	 * @return true if this listener should handle successes for the given context
	 */
	boolean applies(@NonNull ExternalSystemInvocationContext context);

	/**
	 * Called when external system invocation succeeds.
	 * <p>
	 * The listener queries its relevant tables (M_InOut, C_Invoice, etc.) by PInstance_ID
	 * to find which record(s) are affected by this success.
	 *
	 * @param pInstanceId    the process instance ID of the external system invocation
	 * @param context        the invocation context (EDI, Resend, etc.), never null (defaults to UNKNOWN)
	 * @param httpStatus     the HTTP response status returned by the external system
	 */
	void onInvocationSuccess(
			@NonNull PInstanceId pInstanceId,
			@NonNull ExternalSystemInvocationContext context,
			@NonNull HttpStatus httpStatus);
}
