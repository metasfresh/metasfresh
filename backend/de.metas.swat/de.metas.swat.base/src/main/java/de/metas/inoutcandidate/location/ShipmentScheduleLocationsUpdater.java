/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.location;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.inoutcandidate.location.adapter.ShipmentScheduleDocumentLocationAdapterFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.Builder;
import lombok.NonNull;

public class ShipmentScheduleLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_M_ShipmentSchedule record;

	@Builder
	private ShipmentScheduleLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_M_ShipmentSchedule record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
	}

	public void updateAllIfNeeded()
	{
		ShipmentScheduleDocumentLocationAdapterFactory.mainLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		ShipmentScheduleDocumentLocationAdapterFactory.overrideLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
		ShipmentScheduleDocumentLocationAdapterFactory.billLocationAdapter(record).updateCapturedLocationAndRenderedAddressIfNeeded(documentLocationBL);
	}
}
