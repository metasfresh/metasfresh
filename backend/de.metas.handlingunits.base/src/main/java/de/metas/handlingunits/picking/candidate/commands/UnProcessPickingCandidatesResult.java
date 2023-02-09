package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class UnProcessPickingCandidatesResult
{
	@Singular
	@NonNull ImmutableList<PickingCandidate> pickingCandidates;

	public PickingCandidate getSinglePickingCandidate() {return CollectionUtils.singleElement(pickingCandidates);}
}
