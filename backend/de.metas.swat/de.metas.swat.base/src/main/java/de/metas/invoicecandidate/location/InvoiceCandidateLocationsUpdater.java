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

package de.metas.invoicecandidate.location;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.invoicecandidate.location.adapter.BillLocationAdapter;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.location.adapter.OverrideBillLocationAdapter;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

public class InvoiceCandidateLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_C_Invoice_Candidate record;
	private final boolean isNewRecord;

	@Builder
	private InvoiceCandidateLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_C_Invoice_Candidate record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
		this.isNewRecord = InterfaceWrapperHelper.isNew(record);
	}

	public void updateAllIfNeeded()
	{
		updateBillLocationIfNeeded();
		updateOverrideBillLocationIfNeeded();
	}

	private void updateBillLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getBill_Location_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Value_ID,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_User_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final BillLocationAdapter locationAdapter = InvoiceCandidateLocationAdapterFactory.billLocationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}

	private void updateOverrideBillLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getBill_Location_Override_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Override_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, // there is no Bill_BPartner_Override_ID
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Override_ID,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Override_Value_ID,
														 I_C_Invoice_Candidate.COLUMNNAME_Bill_User_ID_Override_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final OverrideBillLocationAdapter locationAdapter = InvoiceCandidateLocationAdapterFactory.billLocationOverrideAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}
}
