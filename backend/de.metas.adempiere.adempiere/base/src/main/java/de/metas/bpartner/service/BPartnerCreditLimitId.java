/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner.service;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

@Value
@RepoIdAwares.SkipTest
public class BPartnerCreditLimitId implements RepoIdAware
{
	int repoId;
	@NonNull BPartnerId bpartnerId;

	public static BPartnerCreditLimitId ofRepoId(@NonNull final BPartnerId bpartnerId, final int bPartnerCreditLimitId)
	{
		return new BPartnerCreditLimitId(bpartnerId, bPartnerCreditLimitId);
	}

	public static BPartnerCreditLimitId ofRepoId(final int bpartnerId, final int bPartnerCreditLimitId)
	{
		return new BPartnerCreditLimitId(BPartnerId.ofRepoId(bpartnerId), bPartnerCreditLimitId);
	}

	@Nullable
	public static BPartnerCreditLimitId ofRepoIdOrNull(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bPartnerCreditLimitId)
	{
		return bpartnerId != null && bpartnerId > 0 && bPartnerCreditLimitId != null && bPartnerCreditLimitId > 0
				? ofRepoId(bpartnerId, bPartnerCreditLimitId)
				: null;
	}

	public static Optional<BPartnerCreditLimitId> optionalOfRepoId(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bPartnerCreditLimitId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(bpartnerId, bPartnerCreditLimitId));
	}

	@Nullable
	public static BPartnerCreditLimitId ofRepoIdOrNull(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final Integer bPartnerCreditLimitId)
	{
		return bpartnerId != null && bPartnerCreditLimitId != null && bPartnerCreditLimitId > 0 ? ofRepoId(bpartnerId, bPartnerCreditLimitId) : null;
	}

	@Jacksonized
	@Builder
	private BPartnerCreditLimitId(@NonNull final BPartnerId bpartnerId, final int bPartnerCreditLimitId)
	{
		this.bpartnerId = bpartnerId;
		this.repoId = Check.assumeGreaterThanZero(bPartnerCreditLimitId, "C_BPartner_CreditLimit_ID");
	}

	public static int toRepoId(@Nullable final BPartnerCreditLimitId bPartnerCreditLimitId)
	{
		return bPartnerCreditLimitId != null ? bPartnerCreditLimitId.getRepoId() : -1;
	}

	public static boolean equals(final @Nullable BPartnerCreditLimitId id1, final @Nullable BPartnerCreditLimitId id2)
	{
		return Objects.equals(id1, id2);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}

}
