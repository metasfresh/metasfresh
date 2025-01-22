package de.metas.distribution.config;

import de.metas.cache.CCache;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileUI_UserProfile_DD;
import org.springframework.stereotype.Repository;

@Repository
public class MobileUIDistributionConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MobileUIDistributionConfig> cache = CCache.<Integer, MobileUIDistributionConfig>builder()
			.tableName(I_MobileUI_UserProfile_DD.Table_Name)
			.build();

	private static final MobileUIDistributionConfig DEFAULT_CONFIG = MobileUIDistributionConfig.builder()
			.allowPickingAnyHU(false)
			.build();

	public MobileUIDistributionConfig getConfig()
	{
		return cache.getOrLoad(0, this::retrieveConfig);
	}

	private MobileUIDistributionConfig retrieveConfig()
	{
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_DD.class)
				.addOnlyActiveRecordsFilter()
				.firstOnlyOptional()
				.map(MobileUIDistributionConfigRepository::fromRecord)
				.orElse(DEFAULT_CONFIG);
	}

	private static MobileUIDistributionConfig fromRecord(final I_MobileUI_UserProfile_DD record)
	{
		return MobileUIDistributionConfig.builder()
				.allowPickingAnyHU(record.isAllowPickingAnyHU())
				.build();
	}
}
