package de.metas.cost.classification;

import de.metas.cache.CCache;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_CostClassification;
import org.springframework.stereotype.Repository;

@Repository
public class CostClassificationRepository
{
	private final CCache<Integer, CostClassificationMap> cache = CCache.<Integer, CostClassificationMap>builder()
			.tableName(I_C_CostClassification.Table_Name)
			.build();

	public CostClassification getById(final CostClassificationId id)
	{
		return getMap().getById(id);
	}

	private CostClassificationMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private CostClassificationMap retrieveMap()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_CostClassification.class)
				.addOnlyActiveRecordsFilter()
				.stream()
				.map(CostClassificationRepository::fromRecord)
				.collect(CostClassificationMap.collect());
	}

	private static CostClassification fromRecord(final I_C_CostClassification record)
	{
		// TODO
	}

}
