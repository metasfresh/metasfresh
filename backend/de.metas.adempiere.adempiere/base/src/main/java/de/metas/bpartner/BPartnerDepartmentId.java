package de.metas.bpartner;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
public class BPartnerDepartmentId implements RepoIdAware
{
	int repoId;

	@NonNull
	BPartnerId bpartnerId;

	public static BPartnerDepartmentId ofRepoId(@NonNull final BPartnerId bpartnerId, final int bpartnerDepartmentId)
	{
		return new BPartnerDepartmentId(bpartnerId, bpartnerDepartmentId);
	}

	public static BPartnerDepartmentId ofRepoId(final int bpartnerId, final int bpartnerDepartmentId)
	{
		return new BPartnerDepartmentId(BPartnerId.ofRepoId(bpartnerId), bpartnerDepartmentId);
	}

	@Nullable
	public static BPartnerDepartmentId ofRepoIdOrNull(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final Integer bpartnerDepartmentId)
	{
		return bpartnerDepartmentId != null && bpartnerDepartmentId > 0
				? ofRepoId(bpartnerId, bpartnerDepartmentId)
				: null;
	}

	private BPartnerDepartmentId(@NonNull final BPartnerId bpartnerId, final int bpartnerDepartmentId)
	{
		this.repoId = Check.assumeGreaterThanZero(bpartnerDepartmentId, "bpartnerDepartmentId");
		this.bpartnerId = bpartnerId;
	}

	public static int toRepoId(@Nullable final BPartnerDepartmentId bpartnerDepartmentId)
	{
		return bpartnerDepartmentId != null ? bpartnerDepartmentId.getRepoId() : -1;
	}

	@Nullable
	public static Integer toRepoIdOrNull(@Nullable final BPartnerDepartmentId bPartnerDepartmentId)
	{
		return bPartnerDepartmentId != null ? bPartnerDepartmentId.getRepoId() : null;
	}
}
