package de.metas.distribution.ddordercandidate;

import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DDOrderCandidateAlloc
{
	int id; // required for delete operations
	@NonNull DDOrderCandidateId ddOrderCandidateId;
	@NonNull DDOrderAndLineId ddOrderAndLineId;
	@NonNull Quantity qty;
}
