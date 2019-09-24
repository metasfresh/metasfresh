package de.metas.bpartner;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.user.UserId;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class BPartnerContactId implements RepoIdAware
{
	@NonNull
	BPartnerId bpartnerId;
	@NonNull
	UserId userId;

	public static BPartnerContactId ofRepoId(@NonNull final BPartnerId bpartnerId, final int contactRepoId)
	{
		final UserId userId = toValidContactUserIdOrNull(contactRepoId);
		if (userId == null)
		{
			throw new AdempiereException("@Invalid@ @Contact_ID@");
		}

		return of(bpartnerId, userId);
	}

	public static BPartnerContactId of(@NonNull final BPartnerId bpartnerId, @NonNull final UserId userId)
	{
		return new BPartnerContactId(bpartnerId, userId);
	}

	public static BPartnerContactId ofRepoId(final int bpartnerRepoId, final int contactRepoId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRepoId);

		final UserId userId = toValidContactUserIdOrNull(contactRepoId);
		if (userId == null)
		{
			throw new AdempiereException("@Invalid@ @Contact_ID@");
		}

		return of(bpartnerId, userId);
	}

	public static BPartnerContactId ofRepoIdOrNull(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final Integer contactRepoId)
	{
		final UserId userId = toValidContactUserIdOrNull(contactRepoId);
		return userId != null ? of(bpartnerId, userId) : null;
	}

	public static BPartnerContactId ofRepoIdOrNull(
			@Nullable final Integer bpartnerRepoId,
			@Nullable final Integer contactRepoId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(bpartnerRepoId);
		if (bpartnerId == null)
		{
			return null;
		}

		final UserId userId = toValidContactUserIdOrNull(contactRepoId);
		if (userId == null)
		{
			return null;
		}

		return of(bpartnerId, userId);
	}

	private static UserId toValidContactUserIdOrNull(final Integer userRepoId)
	{
		final UserId userId = userRepoId != null ? UserId.ofRepoIdOrNull(userRepoId) : null;

		// NOTE: system user is not a valid BP contact
		return userId != null && userId.isRegularUser() ? userId : null;
	}

	private BPartnerContactId(@NonNull final BPartnerId bpartnerId, @NonNull final UserId userId)
	{
		this.bpartnerId = bpartnerId;
		this.userId = userId;
	}

	@Override
	public int getRepoId()
	{
		return userId.getRepoId();
	}

	public static int toRepoId(final BPartnerContactId id)
	{
		return id != null ? id.getRepoId() : -1;
	}
}
