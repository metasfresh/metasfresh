package de.metas.distribution.ddordercandidate;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DDOrderCandidateProcessRequest
{
	/** User on whose behalf the processing shall take place */
	@NonNull UserId userId;
	@NonNull List<DDOrderCandidate> candidates;
}
