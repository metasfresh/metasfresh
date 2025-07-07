package de.metas.manufacturing.order.weighting.spec;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.eevolution.model.I_PP_Weighting_Spec;
import org.springframework.stereotype.Repository;

@Repository
public class WeightingSpecificationsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, WeightingSpecificationsMap> cache = CCache.<Integer, WeightingSpecificationsMap>builder()
			.tableName(I_PP_Weighting_Spec.Table_Name)
			.build();

	public WeightingSpecifications getById(@NonNull final WeightingSpecificationsId id)
	{
		return getMap().getById(id);
	}

	public WeightingSpecificationsId getDefaultId()
	{
		return getMap().getDefaultId();
	}

	public WeightingSpecifications getDefault()
	{
		return getMap().getDefault();
	}

	public WeightingSpecificationsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private WeightingSpecificationsMap retrieveMap()
	{
		final ImmutableList<WeightingSpecifications> list = queryBL
				.createQueryBuilder(I_PP_Weighting_Spec.class)
				.addEqualsFilter(I_PP_Weighting_Spec.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(WeightingSpecificationsRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new WeightingSpecificationsMap(list);
	}

	public static WeightingSpecifications fromRecord(I_PP_Weighting_Spec record)
	{
		return WeightingSpecifications.builder()
				.id(WeightingSpecificationsId.ofRepoId(record.getPP_Weighting_Spec_ID()))
				.weightChecksRequired(record.getWeightChecksRequired())
				.tolerance(Percent.of(record.getTolerance_Perc()))
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.build();
	}
}
