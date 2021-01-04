/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.procurement.webui.sync.exception;

import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import lombok.NonNull;

public class ReceiveSyncException extends RuntimeException
{
	public ReceiveSyncException(@NonNull final RequestToProcurementWeb requestToProcurementWeb, @NonNull final Exception cause)
	{
		super("Error receiving procurementEvent=" + requestToProcurementWeb, cause);
	}

	public ReceiveSyncException(@NonNull final RequestToProcurementWeb requestToProcurementWeb, @NonNull final String errorMessage)
	{
		super("Error receiving procurementEvent; errorMessage=" + errorMessage + "; procurementEvent=" + requestToProcurementWeb);
	}

	public ReceiveSyncException(@NonNull final String message, @NonNull final Exception cause)
	{
		super("Error receiving message=" + message, cause);
	}
}
