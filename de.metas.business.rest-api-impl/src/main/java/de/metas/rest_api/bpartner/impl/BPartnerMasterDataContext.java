package de.metas.rest_api.bpartner.impl;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class BPartnerMasterDataContext
{
	public static BPartnerMasterDataContext ofOrg(@NonNull final OrgId orgId)
	{
		return builder().orgId(orgId).build();
	}

	@NonNull
	OrgId orgId;
	BPartnerId bpartnerId;
	BPartnerLocationId locationId;
	BPartnerContactId contactId;

	boolean bPartnerIsOrgBP;

	public BPartnerMasterDataContext setIfNotNull(@Nullable final BPartnerId bpartnerId)
	{
		if (bpartnerId == null)
		{
			return this;
		}
		return toBuilder().bpartnerId(bpartnerId).build();
	}

	public BPartnerMasterDataContext setIfNotNull(@Nullable final BPartnerLocationId locationId)
	{
		if (locationId == null)
		{
			return this;
		}
		return toBuilder().locationId(locationId).build();
	}

	public BPartnerMasterDataContext setIfNotNull(final BPartnerContactId contactId)
	{
		if (contactId == null)
		{
			return this;
		}
		return toBuilder().contactId(contactId).build();
	}
}
