package org.adempiere.service;

import org.adempiere.util.Check;

import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class OrgId
{
	int repoId;

	public static OrgId ofRepoId(final int repoId)
	{
		return new OrgId(repoId);
	}

	public static OrgId ofRepoIdOrNull(final int repoId)
	{
		return repoId >= 0 ? new OrgId(repoId) : null;
	}

	public static int toRepoId(final OrgId orgId)
	{
		return orgId != null ? orgId.getRepoId() : -1;
	}

	private OrgId(final int repoId)
	{
		// note that AD_Org_ID=0 means * (i.e. any org)
		this.repoId = Check.assumeGreaterOrEqualToZero(repoId, "repoId");
	}
}
