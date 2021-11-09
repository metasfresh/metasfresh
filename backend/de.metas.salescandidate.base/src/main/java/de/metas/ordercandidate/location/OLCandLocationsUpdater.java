/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.location;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.ordercandidate.location.adapter.OLCandDocumentLocationAdapterFactory;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.Builder;
import lombok.NonNull;

public class OLCandLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_C_OLCand record;

	@Builder
	private OLCandLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_C_OLCand record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
	}

	public void updateAllIfNeeded()
	{
		OLCandDocumentLocationAdapterFactory.bpartnerLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		OLCandDocumentLocationAdapterFactory.bpartnerLocationOverrideAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		OLCandDocumentLocationAdapterFactory.billLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		OLCandDocumentLocationAdapterFactory.dropShipAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		OLCandDocumentLocationAdapterFactory.dropShipOverrideAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		OLCandDocumentLocationAdapterFactory.handOverLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		OLCandDocumentLocationAdapterFactory.handOverOverrideLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
	}
}
