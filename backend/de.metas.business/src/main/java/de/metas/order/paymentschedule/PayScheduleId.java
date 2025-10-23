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

package de.metas.order.paymentschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Value
public class PayScheduleId implements RepoIdAware
{
	@JsonCreator
	public static PayScheduleId ofRepoId(final int repoId)
	{
		return new PayScheduleId(repoId);
	}

	@Nullable
	public static PayScheduleId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PayScheduleId(repoId) : null;
	}

	@NonNull
	public static Optional<PayScheduleId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	int repoId;

	private PayScheduleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_PaySchedule_ID");
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PayScheduleId payScheduleId)
	{
		return toRepoIdOr(payScheduleId, -1);
	}

	public static int toRepoIdOr(@Nullable final PayScheduleId payScheduleId, final int defaultValue)
	{
		return payScheduleId != null ? payScheduleId.getRepoId() : defaultValue;
	}

	public static boolean equals(@Nullable final PayScheduleId id1, @Nullable final PayScheduleId id2) {return Objects.equals(id1, id2);}
}