package de.metas.distribution.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MobileUIDistributionConfig
{
	boolean allowPickingAnyHU;
}
