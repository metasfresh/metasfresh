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

public interface IDocumentLocationAdapter extends IDocumentLocationAdapterTemplate
{
	int getC_BPartner_ID();

	void setC_BPartner_ID(int C_BPartner_ID);

	int getC_BPartner_Location_ID();

	void setC_BPartner_Location_ID(int C_BPartner_Location_ID);

	int getC_BPartner_Location_Value_ID();

	void setC_BPartner_Location_Value_ID(int C_BPartner_Location_Value_ID);

	int getAD_User_ID();

	void setAD_User_ID(int AD_User_ID);

	String getBPartnerAddress();

	void setBPartnerAddress(String address);

	default BPartnerLocationAndCaptureId getBPartnerLocationAndCaptureId()
	{
		return getBPartnerLocationAndCaptureIdIfExists()
				.orElseThrow(() -> new AdempiereException("Failed extracting " + BPartnerLocationAndCaptureId.class.getSimpleName() + " from " + this));
	}

	default Optional<BPartnerLocationAndCaptureId> getBPartnerLocationAndCaptureIdIfExists()
	{
		return BPartnerLocationAndCaptureId.optionalOfRepoId(getC_BPartner_ID(), getC_BPartner_Location_ID(), getC_BPartner_Location_Value_ID());
	}

	default Optional<BPartnerContactId> getBPartnerContactId()
	{
		return Optional.ofNullable(BPartnerContactId.ofRepoIdOrNull(getC_BPartner_ID(), getAD_User_ID()));
	}

	@Override
	default void setRenderedAddressAndCapturedLocation(@NonNull final RenderedAddressAndCapturedLocation from)
	{
		setC_BPartner_Location_Value_ID(LocationId.toRepoId(from.getCapturedLocationId()));
		setBPartnerAddress(from.getRenderedAddress());
	}

	default DocumentLocation toDocumentLocation()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(getC_BPartner_ID());
		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, getC_BPartner_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, getAD_User_ID()))
				.locationId(LocationId.ofRepoIdOrNull(getC_BPartner_Location_Value_ID()))
				.bpartnerAddress(getBPartnerAddress())
				.build();
	}


	default void setFrom(@NonNull final DocumentLocation from)
	{
		setC_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setC_BPartner_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setAD_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		setBPartnerAddress(from.getBpartnerAddress());
	}

	default void setFrom(@NonNull final BPartnerInfo from)
	{
		setC_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setC_BPartner_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setAD_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		setBPartnerAddress(null);
	}
}
