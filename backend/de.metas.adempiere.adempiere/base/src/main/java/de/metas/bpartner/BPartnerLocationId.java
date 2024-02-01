package de.metas.bpartner;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

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
public class BPartnerLocationId implements RepoIdAware
{
	int repoId;

	@NonNull
	BPartnerId bpartnerId;

	public static BPartnerLocationId ofRepoId(@NonNull final BPartnerId bpartnerId, final int bpartnerLocationId)
	{
		return new BPartnerLocationId(bpartnerId, bpartnerLocationId);
	}

	public static BPartnerLocationId ofRepoId(final int bpartnerId, final int bpartnerLocationId)
	{
		return new BPartnerLocationId(BPartnerId.ofRepoId(bpartnerId), bpartnerLocationId);
	}

	@Nullable
	public static BPartnerLocationId ofRepoIdOrNull(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bpartnerLocationId)
	{
		return bpartnerId != null && bpartnerId > 0 && bpartnerLocationId != null && bpartnerLocationId > 0
				? ofRepoId(bpartnerId, bpartnerLocationId)
				: null;
	}

	public static Optional<BPartnerLocationId> optionalOfRepoId(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bpartnerLocationId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(bpartnerId, bpartnerLocationId));
	}


	@Nullable
	public static BPartnerLocationId ofRepoIdOrNull(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final Integer bpartnerLocationId)
	{
		return bpartnerId != null && bpartnerLocationId != null && bpartnerLocationId > 0 ? ofRepoId(bpartnerId, bpartnerLocationId) : null;
	}

	@Jacksonized
	@Builder
	private BPartnerLocationId(@NonNull final BPartnerId bpartnerId, final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "bpartnerLocationId");
		this.bpartnerId = bpartnerId;
	}

	public static int toRepoId(@Nullable final BPartnerLocationId bpLocationId)
	{
		return bpLocationId != null ? bpLocationId.getRepoId() : -1;
	}

	@Nullable
	public static Integer toRepoIdOrNull(@Nullable final BPartnerLocationId bpLocationId)
	{
		return bpLocationId != null ? bpLocationId.getRepoId() : null;
	}

	public static boolean equals(final BPartnerLocationId id1, final BPartnerLocationId id2)
	{
		return Objects.equals(id1, id2);
	}
}
