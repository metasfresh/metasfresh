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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.location.LocationId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

public interface IDocumentShipFromLocationAdapter extends IDocumentLocationAdapterTemplate
{
	int getShipFrom_Partner_ID();

	void setShipFrom_Partner_ID(int shipFrom_Partner_ID);

	int getShipFrom_Location_ID();

	void setShipFrom_Location_ID(int shipFrom_Location_ID);

	int getShipFrom_Location_Value_ID();

	void setShipFrom_Location_Value_ID(int shipFrom_Location_Value_ID);

	int getShipFrom_User_ID();

	void setShipFrom_User_ID(int shipFrom_User_ID);

	String getShipFromAddress();

	void setShipFromAddress(String address);

	@Override
	default void setRenderedAddressAndCapturedLocation(@NonNull final RenderedAddressAndCapturedLocation from)
	{
		setShipFrom_Location_Value_ID(LocationId.toRepoId(from.getCapturedLocationId()));
		setShipFromAddress(from.getRenderedAddress());
	}

	default DocumentLocation toDocumentLocation()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(getShipFrom_Partner_ID());
		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, getShipFrom_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, getShipFrom_User_ID()))
				.locationId(LocationId.ofRepoIdOrNull(getShipFrom_Location_Value_ID()))
				.bpartnerAddress(getShipFromAddress())
				.build();
	}

	default void setFrom(@NonNull final DocumentLocation from)
	{
		setShipFrom_Partner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setShipFrom_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setShipFrom_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setShipFrom_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		if (from.getBpartnerAddress() != null)
		{
			setShipFromAddress(from.getBpartnerAddress());
		}
	}

	default void setFrom(@NonNull final BPartnerInfo from)
	{
		setShipFrom_Partner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setShipFrom_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setShipFrom_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setShipFrom_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
	}

	default BPartnerLocationAndCaptureId getBPartnerLocationAndCaptureId()
	{
		return getBPartnerLocationAndCaptureIdIfExists()
				.orElseThrow(() -> new AdempiereException("Failed extracting " + BPartnerLocationAndCaptureId.class.getSimpleName() + " from " + this));
	}

	default Optional<BPartnerLocationAndCaptureId> getBPartnerLocationAndCaptureIdIfExists()
	{
		return BPartnerLocationAndCaptureId.optionalOfRepoId(getShipFrom_Partner_ID(), getShipFrom_Location_ID(), getShipFrom_Location_Value_ID());
	}

	default BPartnerContactId getBPartnerContactId()
	{
		return BPartnerContactId.ofRepoIdOrNull(getShipFrom_Partner_ID(), getShipFrom_User_ID());
	}
}
