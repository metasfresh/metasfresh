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

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.location.LocationId;
import lombok.NonNull;

import javax.annotation.Nullable;

public interface IDocumentHandOverLocationAdapter extends IDocumentLocationAdapterTemplate
{
	boolean isUseHandOver_Location();

	int getHandOver_Partner_ID();

	int getHandOver_Location_ID();

	void setHandOver_Location_ID(int HandOver_Location_ID);

	int getHandOver_Location_Value_ID();

	void setHandOver_Location_Value_ID(int HandOver_Location_Value_ID);

	int getHandOver_User_ID();

	String getHandOverAddress();

	void setHandOverAddress(String address);

	@Override
	default void setRenderedAddressAndCapturedLocation(@NonNull final RenderedAddressAndCapturedLocation from)
	{
		setHandOver_Location_Value_ID(LocationId.toRepoId(from.getCapturedLocationId()));
		setHandOverAddress(from.getRenderedAddress());
	}

	@Override
	default void setLocationAndResetRenderedAddress(@Nullable final BPartnerLocationAndCaptureId from)
	{
		setHandOver_Location_ID(from != null ? from.getBPartnerLocationRepoId() : -1);
		setHandOver_Location_Value_ID(from != null ? from.getLocationCaptureRepoId() : -1);
		setHandOverAddress(null);
	}
}
