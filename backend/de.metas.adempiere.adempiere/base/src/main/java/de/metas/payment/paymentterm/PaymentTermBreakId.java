package de.metas.payment.paymentterm;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

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
public class PaymentTermBreakId implements RepoIdAware
{
	int repoId;

	@NonNull
	PaymentTermId paymentTermId;

	public static PaymentTermBreakId ofRepoId(@NonNull final PaymentTermId paymentTermId, final int paymentTermBreakId)
	{
		return new PaymentTermBreakId(paymentTermId, paymentTermBreakId);
	}

	public static PaymentTermBreakId ofRepoId(final int paymentTermId, final int paymentTermBreakId)
	{
		return new PaymentTermBreakId(PaymentTermId.ofRepoId(paymentTermId), paymentTermBreakId);
	}

	@Nullable
	public static PaymentTermBreakId ofRepoIdOrNull(
			@Nullable final Integer paymentTermId,
			@Nullable final Integer paymentTermBreakId)
	{
		return paymentTermId != null && paymentTermId > 0 && paymentTermBreakId != null && paymentTermBreakId > 0
				? ofRepoId(paymentTermId, paymentTermBreakId)
				: null;
	}

	public static Optional<PaymentTermBreakId> optionalOfRepoId(
			@Nullable final Integer paymentTermId,
			@Nullable final Integer paymentTermBreakId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(paymentTermId, paymentTermBreakId));
	}

	@Nullable
	public static PaymentTermBreakId ofRepoIdOrNull(
			@Nullable final PaymentTermId paymentTermId,
			@Nullable final Integer paymentTermBreakId)
	{
		return paymentTermId != null && paymentTermBreakId != null && paymentTermBreakId > 0 ? ofRepoId(paymentTermId, paymentTermBreakId) : null;
	}

	@Jacksonized
	@Builder
	private PaymentTermBreakId(@NonNull final PaymentTermId paymentTermId, final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "paymentTermBreakId");
		this.paymentTermId = paymentTermId;
	}

	public static int toRepoId(@Nullable final PaymentTermBreakId paymentTermBreakId)
	{
		return paymentTermBreakId != null ? paymentTermBreakId.getRepoId() : -1;
	}

	@Nullable
	public static Integer toRepoIdOrNull(@Nullable final PaymentTermBreakId paymentTermBreakId)
	{
		return paymentTermBreakId != null ? paymentTermBreakId.getRepoId() : null;
	}

	public static boolean equals(final  @Nullable PaymentTermBreakId id1, final @Nullable PaymentTermBreakId id2)
	{
		return Objects.equals(id1, id2);
	}
}