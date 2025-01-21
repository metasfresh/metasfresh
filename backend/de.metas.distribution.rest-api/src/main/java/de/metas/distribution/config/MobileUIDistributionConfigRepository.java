package de.metas.distribution.config;

import org.springframework.stereotype.Repository;

@Repository
public class MobileUIDistributionConfigRepository
{
	private static final MobileUIDistributionConfig DEFAULT_CONFIG = MobileUIDistributionConfig.builder()
			.allowPickingAnyHU(true)
			.build();

	public MobileUIDistributionConfig getConfig()
	{
		return DEFAULT_CONFIG;
	}
}
