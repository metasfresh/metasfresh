package de.metas.bpartner.service;

import static de.metas.util.Check.errorIf;
import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import org.adempiere.service.OrgId;
import org.compiere.util.Util;

import lombok.Builder;
import lombok.NonNull;
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
 * If there is at least one bPartner with the given {@code externalId} and either the given {@code orgId} or (depending on {@code includeAnyOrg}) {@code AD_Org_ID=0} (i.e. {@link OrgId#ANY}),
 * The return it. Prefer the one with the specific orgId over the one with orgId "ANY".
 * <p>
 * If there is at least one bPartner with the given {@code bpartnerValue} and either the given {@code orgId} or (depending on {@code includeAnyOrg}) {@code AD_Org_ID=0} (i.e. {@link OrgId#ANY}),
 * The return it. Prefer the one with the specific orgId over the one with orgId "ANY".
 * <p>
 * If there is at least one bPartner a bPartner-Location that has the given {@code locatorGln} and either the given {@code orgId} or (depending on {@code includeAnyOrg}) {@code AD_Org_ID=0} (i.e. {@link OrgId#ANY}),
 * The return it. Prefer the one with the specific orgId over the one with orgId "ANY".
 */
@Value
public class BPartnerQuery
{
	String bpartnerValue;
	String bpartnerName;
	String locatorGln;
	String externalId;
	OrgId orgId;

	boolean includeAnyOrg;
	boolean outOfTrx;
	boolean failIfNotExists;

	@Builder
	private BPartnerQuery(
			@Nullable final String externalId,
			@Nullable final String bpartnerValue,
			@Nullable final String bpartnerName,
			@Nullable final String locatorGln,
			@NonNull final OrgId orgId,
			@Nullable final Boolean includeAnyOrg,
			@Nullable final Boolean outOfTrx,
			@Nullable final Boolean failIfNotExists)
	{

		this.bpartnerValue = bpartnerValue;
		this.bpartnerName = bpartnerName;
		this.locatorGln = locatorGln;
		this.externalId = externalId;
		errorIf(isEmpty(bpartnerValue, true)
				&& isEmpty(bpartnerName, true)
				&& isEmpty(externalId, true)
				&& isEmpty(locatorGln, true),
				"At least one of the given bpartnerValue, bpartnerName, locatorGln or externalId needs to be non-empty");

		this.orgId = orgId;

		this.includeAnyOrg = Util.coalesce(includeAnyOrg, false);
		this.outOfTrx = Util.coalesce(outOfTrx, false);
		this.failIfNotExists = Util.coalesce(failIfNotExists, false);
	}
}
