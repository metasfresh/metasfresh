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

/**
 * Listener interface for external system invocation errors.
 * Implementations can handle errors specific to their domain (e.g., EDI export status updates).
 * <p>
 * This listener is invoked when the external system REST API receives an error callback
 * (e.g., from Camel route processing failures).
 * <p>
 * Listeners should implement {@link #applies(ExternalSystemErrorContext)} to check if they should handle errors
 * for a given error context.
 */
public interface IExternalSystemInvocationErrorListener
{
	/**
	 * Checks if this listener applies to the given error context.
	 *
	 * @param errorContext the error context (EDI, ShopwareSync, etc.), never null (defaults to UNKNOWN)
	 * @return true if this listener should handle errors for the given context
	 */
	boolean applies(@NonNull ExternalSystemErrorContext errorContext);

	/**
	 * Called when external system invocation fails.
	 * <p>
	 * The listener queries its relevant tables (M_InOut, C_Invoice, etc.) by PInstance_ID
	 * to find which record(s) are affected by this error.
	 *
	 * @param pInstanceId the process instance ID of the external system invocation
	 * @param errorContext the error context (EDI, ShopwareSync, etc.), never null (defaults to UNKNOWN)
	 * @param errorMessage the aggregated error message from external system
	 */
	void onInvocationError(
			@NonNull PInstanceId pInstanceId,
			@NonNull ExternalSystemErrorContext errorContext,
			@NonNull String errorMessage);
}
