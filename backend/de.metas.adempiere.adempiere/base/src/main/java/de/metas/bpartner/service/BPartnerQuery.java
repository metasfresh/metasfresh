package de.metas.bpartner.service;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
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
 * Prefer the ones with a specific orgId > 0 over the ones with orgId "ANY".
 */
@Value
public class BPartnerQuery
{
	BPartnerId bPartnerId;
	ExternalId externalId;
	String bpartnerValue;
	String bpartnerName;
	ImmutableSet<GLN> glns;

	/**
	 * If there are multiple orgIds, they are {@code OR}ed.
	 * Note that this is is not required for security reasons.
	 */
	@Singular
	ImmutableSet<OrgId> onlyOrgIds;

	boolean failIfNotExists;

	@Builder(toBuilder = true)
	private BPartnerQuery(
			@Nullable final BPartnerId bPartnerId,
			@Nullable final ExternalId externalId,
			@Nullable final String bpartnerValue,
			@Nullable final String bpartnerName,
			@NonNull @Singular final Set<GLN> glns,
			//
			@NonNull @Singular final Set<OrgId> onlyOrgIds,
			//
			@Nullable final Boolean failIfNotExists)
	{

		this.bPartnerId = bPartnerId;
		this.bpartnerValue = bpartnerValue;
		this.bpartnerName = bpartnerName;
		this.glns = ImmutableSet.copyOf(glns);
		this.externalId = externalId;

		this.onlyOrgIds = ImmutableSet.copyOf(onlyOrgIds);

		this.failIfNotExists = coalesce(failIfNotExists, false);

		validate();
	}

	private void validate()
	{
		if (isEmpty())
		{
			throw new AdempiereException("At least one of the given bpartnerValue, bpartnerName, glns or externalId needs to be non-empty: " + this);
		}
	}

	public boolean isEmpty()
	{
		return bPartnerId == null
				&& Check.isEmpty(bpartnerValue, true)
				&& Check.isEmpty(bpartnerName, true)
				&& externalId == null
				&& Check.isEmpty(glns);
	}

	public BPartnerQuery withNoGLNs()
	{
		if (glns.isEmpty())
		{
			return this;
		}
		else
		{
			return toBuilder().clearGlns().build();
		}
	}
}
