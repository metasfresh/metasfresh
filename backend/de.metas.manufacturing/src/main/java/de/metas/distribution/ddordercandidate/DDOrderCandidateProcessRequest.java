package de.metas.distribution.ddordercandidate;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DDOrderCandidateProcessRequest
{
	@NonNull List<DDOrderCandidate> candidates;
}
