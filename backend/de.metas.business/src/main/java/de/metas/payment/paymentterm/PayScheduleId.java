/*
 * #%L
 * de.metas.business
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

package de.metas.payment.paymentterm;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Value
public class PayScheduleId implements RepoIdAware
{
	@NonNull
	PaymentTermId paymentTermId;
	int repoId;

	public static PayScheduleId ofRepoId(@NonNull final PaymentTermId paymentTermId, final int payScheduleId)
	{
		return new PayScheduleId(paymentTermId, payScheduleId);
	}

	public static PayScheduleId ofRepoId(final int paymentTermId, final int payScheduleId)
	{
		return new PayScheduleId(PaymentTermId.ofRepoId(paymentTermId), payScheduleId);
	}

	@Nullable
	public static PayScheduleId ofRepoIdOrNull(
			@Nullable final Integer paymentTermId,
			@Nullable final Integer payScheduleId)
	{
		return paymentTermId != null && paymentTermId > 0 && payScheduleId != null && payScheduleId > 0
				? ofRepoId(paymentTermId, payScheduleId)
				: null;
	}

	public static Optional<PayScheduleId> optionalOfRepoId(
			@Nullable final Integer paymentTermId,
			@Nullable final Integer payScheduleId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(paymentTermId, payScheduleId));
	}

	@Nullable
	public static PayScheduleId ofRepoIdOrNull(
			@Nullable final PaymentTermId paymentTermId,
			@Nullable final Integer payScheduleId)
	{
		return paymentTermId != null && payScheduleId != null && payScheduleId > 0 ? ofRepoId(paymentTermId, payScheduleId) : null;
	}

	@Jacksonized
	@Builder
	private PayScheduleId(@NonNull final PaymentTermId paymentTermId, final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_PaySchedule_ID");
		this.paymentTermId = paymentTermId;
	}

	public static int toRepoId(@Nullable final PayScheduleId payScheduleId)
	{
		return payScheduleId != null ? payScheduleId.getRepoId() : -1;
	}

	@Nullable
	public static Integer toRepoIdOrNull(@Nullable final PayScheduleId payScheduleId)
	{
		return payScheduleId != null ? payScheduleId.getRepoId() : null;
	}
}
