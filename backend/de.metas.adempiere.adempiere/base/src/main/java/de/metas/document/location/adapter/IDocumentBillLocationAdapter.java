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

import de.metas.location.LocationId;

import javax.annotation.Nullable;

public interface IDocumentBillLocationAdapter extends IDocumentLocationAdapterTemplate
{
	int getBill_BPartner_ID();

	int getBill_Location_ID();

	int getBill_Location_Value_ID();

	void setBill_Location_Value_ID(int Bill_Location_Value_ID);

	int getBill_User_ID();

	String getBillToAddress();

	void setBillToAddress(String address);

	@Override
	default void setLocationAndAddress(@Nullable final LocationId locationId, @Nullable final String address)
	{
		setBill_Location_Value_ID(LocationId.toRepoId(locationId));
		setBillToAddress(address);
	}
}
