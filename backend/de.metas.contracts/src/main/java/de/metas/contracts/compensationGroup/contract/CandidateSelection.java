package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupCompensationBase;
import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/** An order's candidate lines and what is needed from them: their ids, the bases they match, and their split by position. */
@Value
class CandidateSelection
{
	static final CandidateSelection NONE = new CandidateSelection(ImmutableList.of());

	@NonNull ImmutableList<CandidateLine> lines;

	boolean isEmpty()
	{
		return lines.isEmpty();
	}

	ImmutableSet<OrderLineId> getLineIds()
	{
		return lines.stream()
				.map(CandidateLine::getLineId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return the schema bases that at least one of the lines falls into */
	ImmutableSet<GroupCompensationBase> getMatchedBases()
	{
		return lines.stream()
				.flatMap(candidateLine -> candidateLine.getMatchedBases().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return one selection per position (the lines without any position form one, too), in the order of the positions' first line */
	ImmutableList<PositionSelection> splitByPosition()
	{
		return lines.stream()
				.map(CandidateLine::getPositionId)
				.distinct()
				.map(this::selectPosition)
				.collect(ImmutableList.toImmutableList());
	}

	private PositionSelection selectPosition(@Nullable final ProjectId positionId)
	{
		final ImmutableList<CandidateLine> positionLines = lines.stream()
				.filter(candidateLine -> ProjectId.equals(candidateLine.getPositionId(), positionId))
				.collect(ImmutableList.toImmutableList());
		return new PositionSelection(positionId, new CandidateSelection(positionLines));
	}

	/** The candidate lines of one position; {@code positionId} is null for the lines without any position. */
	@Value
	static class PositionSelection
	{
		@Nullable ProjectId positionId;
		@NonNull CandidateSelection candidates;
	}
}
