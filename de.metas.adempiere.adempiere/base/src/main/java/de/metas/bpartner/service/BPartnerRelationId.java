package de.metas.bpartner.service;

import java.util.Objects;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
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
public class BPartnerRelationId implements RepoIdAware
{
	int repoId;

	@NonNull
	BPartnerId bpartnerId;

	public static BPartnerRelationId ofRepoId(@NonNull final BPartnerId bpartnerId, final int bpartnerRelationId)
	{
		return new BPartnerRelationId(bpartnerId, bpartnerRelationId);
	}

	public static BPartnerRelationId ofRepoId(final int bpartnerId, final int bpartnerRelationId)
	{
		return new BPartnerRelationId(BPartnerId.ofRepoId(bpartnerId), bpartnerRelationId);
	}

	public static BPartnerRelationId ofRepoIdOrNull(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bpartnerRelationId)
	{
		return bpartnerId != null && bpartnerId > 0 && bpartnerRelationId != null && bpartnerRelationId > 0
				? ofRepoId(bpartnerId, bpartnerRelationId)
						: null;
	}

	public static BPartnerRelationId ofRepoIdOrNull(
			@Nullable final BPartnerId bpartnerId,
			final int bpartnerRelationId)
	{
		return bpartnerId != null && bpartnerRelationId > 0 ? ofRepoId(bpartnerId, bpartnerRelationId) : null;
	}

	private BPartnerRelationId(@NonNull final BPartnerId bpartnerId, final int bpartnerRelationId)
	{
		this.repoId = Check.assumeGreaterThanZero(bpartnerRelationId, "bpartnerRelationId");
		this.bpartnerId = bpartnerId;
	}

	public static int toRepoId(final BPartnerRelationId bpartnerRelationId)
	{
		return toRepoIdOr(bpartnerRelationId, -1);
	}

	public static int toRepoIdOr(final BPartnerRelationId bpartnerRelationId, final int defaultValue)
	{
		return bpartnerRelationId != null ? bpartnerRelationId.getRepoId() : defaultValue;
	}

	public static boolean equals(final BPartnerRelationId id1, final BPartnerRelationId id2)
	{
		return Objects.equals(id1, id2);
	}
}
