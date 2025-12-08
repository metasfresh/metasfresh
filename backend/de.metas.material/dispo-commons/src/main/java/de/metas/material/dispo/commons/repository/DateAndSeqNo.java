package de.metas.material.dispo.commons.repository;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.repository.atp.AddToResultGroupRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
public class DateAndSeqNo
{
	Instant date;

	/**
	 * Can be less or equal to zero which both means "not specified".
	 */
	int seqNo;

	/**
	 * Can be null if this instance is not used for a range start or end.
	 */
	public Operator operator;

	public static DateAndSeqNo atTimeNoSeqNo(@NonNull final Instant date)
	{
		return builder()
				.date(date)
				.build();
	}

	public static DateAndSeqNo ofCandidate(@NonNull final Candidate candidate)
	{
		return builder()
				.date(candidate.getDate())
				.seqNo(candidate.getSeqNo())
				.build();
	}

	public static DateAndSeqNo ofAddToResultGroupRequest(@NonNull final AddToResultGroupRequest addToResultGroupRequest)
	{
		return builder()
				.date(addToResultGroupRequest.getDate())
				.seqNo(addToResultGroupRequest.getSeqNo())
				.build();
	}

	public enum Operator
	{
		INCLUSIVE,

		EXCLUSIVE
	}

	@Builder(toBuilder = true)
	private DateAndSeqNo(
			@NonNull final Instant date,
			final int seqNo,
			@Nullable final Operator operator)
	{
		this.date = date;
		this.seqNo = seqNo;
		this.operator = operator;
	}

	/**
	 * @return {@code true} if this instances {@code date} is after the {@code other}'s {@code date}
	 * or if this instance's {@code seqNo} is greater than the {@code other}'s {@code seqNo}.
	 */
	public boolean isAfter(@NonNull final DateAndSeqNo other)
	{
		// note that we avoid using equals here, a timestamp and a date that are both "Date" might not be equal even if they have the same time.
		if (date.isAfter(other.getDate()))
		{
			return true;
		}
		if (date.isBefore(other.getDate()))
		{
			return false;
		}
		return seqNo > other.getSeqNo();
	}

	/**
	 * Analog to {@link #isAfter(DateAndSeqNo)}.
	 */
	public boolean isBefore(@NonNull final DateAndSeqNo other)
	{
		// note that we avoid using equals here, a timestamp and a date that are both "Date" might not be equal even if they have the same time.
		if (date.isBefore(other.getDate()))
		{
			return true;
		}
		if (date.isAfter(other.getDate()))
		{
			return false;
		}
		return seqNo < other.getSeqNo();
	}

	public DateAndSeqNo min(@Nullable final DateAndSeqNo other)
	{
		if (other == null)
		{
			return this;
		}
		else
		{
			return this.isBefore(other) ? this : other;
		}
	}

	public DateAndSeqNo max(@Nullable final DateAndSeqNo other)
	{
		if (other == null)
		{
			return this;
		}
		return this.isAfter(other) ? this : other;
	}

	public DateAndSeqNo withOperator(@Nullable final Operator operator)
	{
		return this
				.toBuilder()
				.operator(operator)
				.build();
	}

	public static boolean equals(@Nullable final DateAndSeqNo value1, @Nullable final DateAndSeqNo value2) {return Objects.equals(value1, value2);}
}
