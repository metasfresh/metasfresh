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

public interface IDocumentBillLocationAdapter extends IDocumentLocationAdapterTemplate
{
	int getBill_BPartner_ID();

	void setBill_BPartner_ID(int Bill_BPartner_ID);

	int getBill_Location_ID();

	void setBill_Location_ID(int Bill_Location_ID);

	int getBill_Location_Value_ID();

	void setBill_Location_Value_ID(int Bill_Location_Value_ID);

	int getBill_User_ID();

	void setBill_User_ID(int Bill_User_ID);

	String getBillToAddress();

	void setBillToAddress(String address);

	@Override
	default void setRenderedAddressAndCapturedLocation(@NonNull final RenderedAddressAndCapturedLocation from)
	{
		setBill_Location_Value_ID(LocationId.toRepoId(from.getCapturedLocationId()));
		setBillToAddress(from.getRenderedAddress());
	}

	default DocumentLocation toDocumentLocation()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(getBill_BPartner_ID());
		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, getBill_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, getBill_User_ID()))
				.locationId(LocationId.ofRepoIdOrNull(getBill_Location_Value_ID()))
				.bpartnerAddress(getBillToAddress())
				.build();
	}

	default void setFrom(@NonNull final DocumentLocation from)
	{
		setBill_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setBill_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setBill_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setBill_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		if (from.getBpartnerAddress()!=null)
		{
			setBillToAddress(from.getBpartnerAddress());
		}
	}

	default void setFrom(@NonNull final BPartnerInfo from)
	{
		setBill_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setBill_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setBill_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setBill_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
	}

	default BPartnerLocationAndCaptureId getBPartnerLocationAndCaptureId()
	{
		return getBPartnerLocationAndCaptureIdIfExists()
				.orElseThrow(() -> new AdempiereException("Failed extracting " + BPartnerLocationAndCaptureId.class.getSimpleName() + " from " + this));
	}

	default Optional<BPartnerLocationAndCaptureId> getBPartnerLocationAndCaptureIdIfExists()
	{
		return BPartnerLocationAndCaptureId.optionalOfRepoId(getBill_BPartner_ID(), getBill_Location_ID(), getBill_Location_Value_ID());
	}

	default BPartnerContactId getBPartnerContactId()
	{
		return BPartnerContactId.ofRepoIdOrNull(getBill_BPartner_ID(), getBill_User_ID());
	}
}
