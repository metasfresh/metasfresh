package de.metas.distribution.config;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class MobileUIDistributionConfig
{
	boolean allowPickingAnyHU;
	@NonNull CaptionFormat captionFormat;
}
