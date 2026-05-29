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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Value
public class PayScheduleId implements RepoIdAware
{
	int repoId;

	private PayScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_PaySchedule_ID");
	}

	@JsonCreator
	public static PayScheduleId ofRepoId(final int payScheduleId)
	{
		return new PayScheduleId(payScheduleId);
	}

	@Nullable
	public static PayScheduleId ofRepoIdOrNull(@Nullable final Integer payScheduleId)
	{
		return payScheduleId != null && payScheduleId > 0 ? ofRepoId(payScheduleId) : null;
	}

	public static Optional<PayScheduleId> optionalOfRepoId(@Nullable final Integer payScheduleId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(payScheduleId));
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PayScheduleId payScheduleId)
	{
		return payScheduleId != null ? payScheduleId.getRepoId() : -1;
	}
}
