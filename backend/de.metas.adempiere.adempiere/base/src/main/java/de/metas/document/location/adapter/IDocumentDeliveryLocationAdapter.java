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

import javax.annotation.Nullable;

public interface IDocumentDeliveryLocationAdapter extends IDocumentLocationAdapterTemplate
{
	int getDropShip_BPartner_ID();

	void setDropShip_BPartner_ID(int DropShip_BPartner_ID);

	int getDropShip_Location_ID();

	void setDropShip_Location_ID(int DropShip_Location_ID);

	int getDropShip_Location_Value_ID();

	void setDropShip_Location_Value_ID(int DropShip_Location_Value_ID);

	int getDropShip_User_ID();

	void setDropShip_User_ID(int DropShip_User_ID);

	int getM_Warehouse_ID();

	boolean isDropShip();

	String getDeliveryToAddress();

	void setDeliveryToAddress(String address);

	@Override
	default void setRenderedAddressAndCapturedLocation(@NonNull final RenderedAddressAndCapturedLocation from)
	{
		setDropShip_Location_Value_ID(LocationId.toRepoId(from.getCapturedLocationId()));
		setDeliveryToAddress(from.getRenderedAddress());
	}

	default DocumentLocation toDocumentLocation()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(getDropShip_BPartner_ID());
		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, getDropShip_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, getDropShip_User_ID()))
				.locationId(LocationId.ofRepoIdOrNull(getDropShip_Location_Value_ID()))
				.bpartnerAddress(getDeliveryToAddress())
				.build();
	}

	default void setFrom(@NonNull final DocumentLocation from)
	{
		setDropShip_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setDropShip_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setDropShip_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setDropShip_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		setDeliveryToAddress(from.getBpartnerAddress());
	}

	default void setFrom(@NonNull final BPartnerInfo from)
	{
		setDropShip_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setDropShip_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setDropShip_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setDropShip_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		setDeliveryToAddress(null);
	}
}
