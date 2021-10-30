/*
 * #%L
 * de.metas.business
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

package de.metas.inout.location;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;

public class InOutLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_M_InOut record;

	@Builder
	private InOutLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_M_InOut record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
	}

	public void updateAllIfNeeded()
	{
		InOutDocumentLocationAdapterFactory.locationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		InOutDocumentLocationAdapterFactory.deliveryLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
	}
}
