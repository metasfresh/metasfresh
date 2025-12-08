package de.metas.distribution.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class MobileUIDistributionConfig
{
	boolean allowPickingAnyHU;
}
