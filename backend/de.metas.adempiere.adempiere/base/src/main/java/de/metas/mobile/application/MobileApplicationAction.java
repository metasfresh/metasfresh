package de.metas.mobile.application;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MobileApplicationAction
{
	@NonNull MobileApplicationActionId id;
	@NonNull String internalName;
}
