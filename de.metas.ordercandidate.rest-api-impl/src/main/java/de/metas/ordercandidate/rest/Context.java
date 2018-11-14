package de.metas.ordercandidate.rest;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import org.adempiere.service.OrgId;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder(toBuilder = true)
public class Context
{
	public static Context ofOrg(final OrgId orgId)
	{
		return builder().orgId(orgId).build();
	}

	@NonNull
	OrgId orgId;
	BPartnerId bpartnerId;
	BPartnerLocationId locationId;
	BPartnerContactId contactId;

	boolean bPartnerIsOrgBP;

	public Context with(final BPartnerId bpartnerId)
	{
		return toBuilder().bpartnerId(bpartnerId).build();
	}

	public Context with(final BPartnerLocationId locationId)
	{
		return toBuilder().locationId(locationId).build();
	}

	public Context with(final BPartnerContactId contactId)
	{
		return toBuilder().contactId(contactId).build();
	}
}
