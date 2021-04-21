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
import de.metas.inout.location.adapter.DocumentDeliveryLocationAdapter;
import de.metas.inout.location.adapter.DocumentLocationAdapter;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;

public class InOutLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_M_InOut record;
	private final boolean isNewRecord;

	@Builder
	private InOutLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_M_InOut record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
		this.isNewRecord = InterfaceWrapperHelper.isNew(record);
	}

	public void updateAllIfNeeded()
	{
		updateMainLocationIfNeeded();
		updateDeliveryLocationIfNeeded();
	}

	private void updateMainLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getC_BPartner_Location_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_M_InOut.COLUMNNAME_C_BPartner_Location_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_M_InOut.COLUMNNAME_C_BPartner_ID,
														 I_M_InOut.COLUMNNAME_C_BPartner_Location_ID,
														 I_M_InOut.COLUMNNAME_C_BPartner_Location_Value_ID,
														 I_M_InOut.COLUMNNAME_AD_User_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final DocumentLocationAdapter locationAdapter = InOutDocumentLocationAdapterFactory.locationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}

	private void updateDeliveryLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getC_BPartner_Location_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_M_InOut.COLUMNNAME_DropShip_Location_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_M_InOut.COLUMNNAME_DropShip_BPartner_ID,
														 I_M_InOut.COLUMNNAME_DropShip_Location_ID,
														 I_M_InOut.COLUMNNAME_DropShip_Location_Value_ID,
														 I_M_InOut.COLUMNNAME_DropShip_User_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final DocumentDeliveryLocationAdapter locationAdapter = InOutDocumentLocationAdapterFactory.deliveryLocationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}
}
