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
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.location.LocationId;
import lombok.NonNull;

import javax.annotation.Nullable;

public interface IDocumentHandOverLocationAdapter extends IDocumentLocationAdapterTemplate
{
	boolean isUseHandOver_Location();

	int getHandOver_Partner_ID();

	void setHandOver_Partner_ID(int HandOver_Partner_ID);

	int getHandOver_Location_ID();

	void setHandOver_Location_ID(int HandOver_Location_ID);

	int getHandOver_Location_Value_ID();

	void setHandOver_Location_Value_ID(int HandOver_Location_Value_ID);

	int getHandOver_User_ID();

	void setHandOver_User_ID(int HandOver_User_ID);

	String getHandOverAddress();

	void setHandOverAddress(String address);

	@Override
	default void setRenderedAddressAndCapturedLocation(@NonNull final RenderedAddressAndCapturedLocation from)
	{
		setHandOver_Location_Value_ID(LocationId.toRepoId(from.getCapturedLocationId()));
		setHandOverAddress(from.getRenderedAddress());
	}

	default void setFrom(@NonNull final BPartnerInfo from)
	{
		setHandOver_Partner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		setHandOver_Location_ID(BPartnerLocationId.toRepoId(from.getBpartnerLocationId()));
		setHandOver_Location_Value_ID(LocationId.toRepoId(from.getLocationId()));
		setHandOver_User_ID(BPartnerContactId.toRepoId(from.getContactId()));
		setHandOverAddress(null);
	}
}
