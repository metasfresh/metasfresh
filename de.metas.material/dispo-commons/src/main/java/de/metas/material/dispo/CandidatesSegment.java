package de.metas.material.dispo;

import java.util.Date;
import java.util.Objects;

import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.material.dispo.Candidate.Type;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Identifies a set of candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
@Data
@Wither
public class CandidatesSegment
{
	public enum DateOperator
	{
		/**
		 * With this operator, the segment is supposed to match records with a date <b>before</b> the segment's {@link CandidatesSegment#getDate()}.
		 */
		until,

		/**
		 * With this operator, the segment is supposed to match records with a date <b>after</b> the segment's {@link CandidatesSegment#getDate()}.
		 */
		after,

		/**
		 * With this operator the segment matches records with a date <b>after</b> and also exactly <b>at</b> the segment's {@link CandidatesSegment#getDate()}.
		 */
		from
	}

	private final Type type;

	private final Integer productId;

	private final Integer warehouseId;

	@NonNull
	private final Date date;

	/**
	 * This mandatory property specifies how to interpret the date.
	 */
	@NonNull
	private final DateOperator dateOperator;

	/**
	 * If set, then this segment is about {@link Candidate}s that have a parent candidate which has the given product ID.
	 */
	private final Integer parentProductId;

	/**
	 * If set, then this segment is about {@link Candidate}s that have a parent candidate which has the given warehouse ID.
	 */
	private final Integer parentWarehouseId;

	private final TableRecordReference reference;

	/**
	 * This method ignores parent {@link #getParentProductId()}, {@link #getParentWarehouseId()},
	 * because we don't need it right now and it would mean that we had to fetch the given {@code candidate}'s parent from the repo.
	 *
	 * @param candidate
	 * @return
	 */
	public boolean matches(final Candidate candidate)
	{
		final boolean dateMatches;
		switch (dateOperator)
		{
			case after:
				dateMatches = candidate.getDate().getTime() > getDate().getTime();
				break;
			case from:
				dateMatches = candidate.getDate().getTime() >= getDate().getTime();
				break;
			case until:
				dateMatches = candidate.getDate().getTime() <= getDate().getTime();
				break;
			default:
				Check.errorIf(true, "Unexpected date operator={}; this={}", dateOperator, this);
				return false; // won't be reached
		}

		if (!dateMatches)
		{
			return false;
		}

		if (getProductId() != null && !Objects.equals(getProductId(), candidate.getProductId()))
		{
			return false;
		}

		if (getReference() != null && !Objects.equals(getReference(), candidate.getReference()))
		{
			return false;
		}

		if (getType() != null && !Objects.equals(getType(), candidate.getType()))
		{
			return false;
		}

		if (getWarehouseId() != null && !Objects.equals(getWarehouseId(), candidate.getWarehouseId()))
		{
			return false;
		}

		return true;
	}

}
