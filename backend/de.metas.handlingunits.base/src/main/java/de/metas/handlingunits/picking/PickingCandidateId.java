package de.metas.handlingunits.picking;

import java.util.Collection;
import java.util.Objects;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class PickingCandidateId implements RepoIdAware
{
	@JsonCreator
	public static PickingCandidateId ofRepoId(final int repoId)
	{
		return new PickingCandidateId(repoId);
	}

	@Nullable
	public static PickingCandidateId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static ImmutableSet<Integer> toIntSet(@NonNull final Collection<PickingCandidateId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ids.stream().map(PickingCandidateId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}

	int repoId;

	private PickingCandidateId(final int pickingCandidateRepoId)
	{
		repoId = Check.assumeGreaterThanZero(pickingCandidateRepoId, "pickingCandidateRepoId");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PickingCandidateId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(final PickingCandidateId o1, final PickingCandidateId o2)
	{
		return Objects.equals(o1, o2);
	}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_M_Picking_Candidate.Table_Name, getRepoId());
	}
}
