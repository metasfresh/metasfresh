package de.metas.manufacturing.config;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MobileUIManufacturingUserProfile
{
	@NonNull UserId userId;
	boolean isScanResourceRequired;
}