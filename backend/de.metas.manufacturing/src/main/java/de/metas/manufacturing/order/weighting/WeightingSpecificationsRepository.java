package de.metas.manufacturing.order.weighting;

import de.metas.cache.CCache;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.eevolution.model.I_PP_Weighting_Spec;
import org.springframework.stereotype.Repository;

@Repository
public class WeightingSpecificationsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, WeightingSpecifications> cache = CCache.<Integer, WeightingSpecifications>builder()
			.tableName(I_PP_Weighting_Spec.Table_Name)
			.build();

	public WeightingSpecifications getById(WeightingSpecificationsId id)
	{
		final WeightingSpecifications weightingSpecifications = getDefault();
		if (!WeightingSpecificationsId.equals(weightingSpecifications.getId(), id))
		{
			throw new AdempiereException("Invalid ID: " + id);
		}
		return weightingSpecifications;
	}

	public WeightingSpecificationsId getDefaultId()
	{
		return getDefault().getId();
	}

	public WeightingSpecifications getDefault()
	{
		return cache.getOrLoad(0, this::retrieveDefault);
	}

	private WeightingSpecifications retrieveDefault()
	{
		return queryBL
				.createQueryBuilder(I_PP_Weighting_Spec.class)
				.addEqualsFilter(I_PP_Weighting_Spec.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_PP_Weighting_Spec.class)
				.map(WeightingSpecificationsRepository::fromRecord)
				.orElseThrow(() -> new AdempiereException("No weighting specifications found"));
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
