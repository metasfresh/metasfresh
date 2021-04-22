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

package de.metas.invoice.location;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapter;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.location.LocationId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;

public class InvoiceLocationsUpdater
{
	private final IDocumentLocationBL documentLocationBL;
	private final I_C_Invoice record;
	private final boolean isNewRecord;

	@Builder
	private InvoiceLocationsUpdater(
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final I_C_Invoice record)
	{
		this.documentLocationBL = documentLocationBL;
		this.record = record;
		this.isNewRecord = InterfaceWrapperHelper.isNew(record);
	}

	public void updateAllIfNeeded()
	{
		updateMainLocationIfNeeded();
	}

	private void updateMainLocationIfNeeded()
	{
		final boolean updateCapturedLocation = isNewRecord
				? LocationId.ofRepoIdOrNull(record.getC_BPartner_Location_Value_ID()) == null
				: InterfaceWrapperHelper.isValueChanged(record, I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID);

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| InterfaceWrapperHelper.isValueChanged(record,
														 I_C_Invoice.COLUMNNAME_C_BPartner_ID,
														 I_C_Invoice.COLUMNNAME_C_BPartner_Location_ID,
														 I_C_Invoice.COLUMNNAME_C_BPartner_Location_Value_ID,
														 I_C_Invoice.COLUMNNAME_AD_User_ID);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			final InvoiceDocumentLocationAdapter locationAdapter = InvoiceDocumentLocationAdapterFactory.locationAdapter(record);

			documentLocationBL.toPlainDocumentLocation(locationAdapter)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}
}
