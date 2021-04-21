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
import de.metas.inoutcandidate.location.adapter.BillLocationAdapter;
import de.metas.inoutcandidate.location.adapter.MainLocationAdapter;
import de.metas.inoutcandidate.location.adapter.OverrideLocationAdapter;
import de.metas.inoutcandidate.location.adapter.ShipmentScheduleDocumentLocationAdapterFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

public class ShipmentScheduleLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_M_ShipmentSchedule record;
	private final boolean isNewRecord;

	@Builder
	private ShipmentScheduleLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_M_ShipmentSchedule record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
		this.isNewRecord = InterfaceWrapperHelper.isNew(record);
	}

	public void updateAllIfNeeded()
	{
		updateMainLocationIfNeeded();
		updateOverrideLocationIfNeeded();
		updateBillLocationIfNeeded();
	}

	private void updateMainLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getC_BPartner_Location_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_Value_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_AD_User_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final MainLocationAdapter locationAdapter = ShipmentScheduleDocumentLocationAdapterFactory.mainLocationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}

	private void updateOverrideLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getC_BP_Location_Override_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_Value_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_AD_User_Override_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final OverrideLocationAdapter locationAdapter = ShipmentScheduleDocumentLocationAdapterFactory.overrideLocationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}

	private void updateBillLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getBill_Location_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record,
														I_M_ShipmentSchedule.COLUMNNAME_Bill_BPartner_ID,
														I_M_ShipmentSchedule.COLUMNNAME_Bill_Location_ID,
														I_M_ShipmentSchedule.COLUMNNAME_Bill_User_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_M_ShipmentSchedule.COLUMNNAME_Bill_BPartner_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_Bill_Location_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_Value_ID,
														 I_M_ShipmentSchedule.COLUMNNAME_Bill_User_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final BillLocationAdapter locationAdapter = ShipmentScheduleDocumentLocationAdapterFactory.billLocationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}

}
