package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
public class ProcessPickingCandidatesRequest
{
	@NonNull ImmutableSet<PickingCandidateId> pickingCandidateIds;
	boolean alwaysPackEachCandidateInItsOwnHU;

	@Builder
	private ProcessPickingCandidatesRequest(
			@NonNull @Singular final ImmutableSet<PickingCandidateId> pickingCandidateIds,
			final boolean alwaysPackEachCandidateInItsOwnHU)
	{
		Check.assumeNotEmpty(pickingCandidateIds, "pickingCandidateIds is not empty");

		this.pickingCandidateIds = ImmutableSet.copyOf(pickingCandidateIds);
		this.alwaysPackEachCandidateInItsOwnHU = alwaysPackEachCandidateInItsOwnHU;
	}
}
