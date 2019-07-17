package de.metas.bpartner.service;

import static de.metas.util.Check.errorIf;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.util.rest.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Search by external ID, bpartner's Value, bpartner's Name or location's GLN in this order.
 * Prefer the one with the specific orgId over the one with orgId "ANY".
 */
@Value
public class BPartnerQuery
{
	BPartnerId bBartnerId;
	ExternalId externalId;
	String bpartnerValue;
	String bpartnerName;
	String locationGln;

	ImmutableSet<OrgId> onlyOrgIds;

	boolean outOfTrx;
	boolean failIfNotExists;

	@Builder
	private BPartnerQuery(
			@Nullable final BPartnerId bBartnerId,
			@Nullable final ExternalId externalId,
			@Nullable final String bpartnerValue,
			@Nullable final String bpartnerName,
			@Nullable final String locationGln,
			//
			@NonNull @Singular final ImmutableSet<OrgId> onlyOrgIds,
			//
			@Nullable final Boolean outOfTrx,
			@Nullable final Boolean failIfNotExists)
	{

		this.bBartnerId = bBartnerId;
		this.bpartnerValue = bpartnerValue;
		this.bpartnerName = bpartnerName;
		this.locationGln = locationGln;
		this.externalId = externalId;
		errorIf(bBartnerId == null
				&& isEmpty(bpartnerValue, true)
				&& isEmpty(bpartnerName, true)
				&& externalId == null
				&& isEmpty(locationGln, true),
				"At least one of the given bpartnerValue, bpartnerName, locationGln or externalId needs to be non-empty");

		this.onlyOrgIds = onlyOrgIds;

		this.outOfTrx = coalesce(outOfTrx, true);
		this.failIfNotExists = coalesce(failIfNotExists, false);
	}
}
