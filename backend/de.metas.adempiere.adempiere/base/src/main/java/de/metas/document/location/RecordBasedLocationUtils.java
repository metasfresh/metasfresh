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

package de.metas.document.location;

import de.metas.bpartner.BPartnerLocationId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;

@UtilityClass
public class RecordBasedLocationUtils
{
	public static <SELF extends RecordBasedLocationAdapter<SELF>> void updateCapturedLocationAndRenderedAddressIfNeeded(
			@NonNull RecordBasedLocationAdapter<SELF> locationAdapter,
			@NonNull final IDocumentLocationBL documentLocationBL)
	{
		final Object record = locationAdapter.getWrappedRecord();

		// do nothing if we are cloning the record
		if(InterfaceWrapperHelper.isCopying(record))
		{
			return;
		}

		final boolean isNewRecord = InterfaceWrapperHelper.isNew(record);
		final DocumentLocation currentLocation = locationAdapter.toPlainDocumentLocation(documentLocationBL).orElse(DocumentLocation.EMPTY);
		final DocumentLocation previousLocation = !isNewRecord
				? locationAdapter.toOldValues().toPlainDocumentLocation(documentLocationBL).orElse(DocumentLocation.EMPTY)
				: DocumentLocation.EMPTY;

		final boolean updateCapturedLocation = isNewRecord
				? currentLocation.getLocationId() == null
				: !BPartnerLocationId.equals(currentLocation.getBpartnerLocationId(), previousLocation.getBpartnerLocationId());

		final boolean updateRenderedAddress = isNewRecord
				|| updateCapturedLocation
				|| !currentLocation.equalsIgnoringRenderedAddress(previousLocation);

		if (updateCapturedLocation || updateRenderedAddress)
		{
			locationAdapter.toPlainDocumentLocation(documentLocationBL)
					.map(location -> updateCapturedLocation ? location.withLocationId(null) : location)
					.map(documentLocationBL::computeRenderedAddress)
					.ifPresent(locationAdapter::setRenderedAddressAndCapturedLocation);
		}
	}
}
