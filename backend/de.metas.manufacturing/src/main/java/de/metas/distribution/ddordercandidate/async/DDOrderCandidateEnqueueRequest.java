package de.metas.distribution.ddordercandidate.async;

import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DDOrderCandidateEnqueueRequest
{
	@NonNull PInstanceId selectionId;

	public static DDOrderCandidateEnqueueRequest ofSelectionId(@NonNull final PInstanceId selectionId)
	{
		return builder().selectionId(selectionId).build();
	}
}
