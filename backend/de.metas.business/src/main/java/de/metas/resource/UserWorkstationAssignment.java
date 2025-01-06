package de.metas.resource;

import de.metas.product.ResourceId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class UserWorkstationAssignment
{
	@NonNull UserId userId;
	@NonNull ResourceId workstationId;
	boolean active;
}
