/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.location.adapter;

import java.util.Optional;

public interface DocumentLocationAdapterFactory
{
	Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(Object record);

	Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(Object record);

	Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(Object record);

	Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(Object record);

	default Optional<IDocumentShipFromLocationAdapter> getDocumentShipFromLocationAdapter(Object record)
	{
		return Optional.empty();
	}

	;
}
