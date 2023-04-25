package de.metas.product.hazard_symbol;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.image.AdImageId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_HazardSymbol;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class HazardSymbolRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HazardSymbolMap> cache = CCache.<Integer, HazardSymbolMap>builder()
			.tableName(I_M_HazardSymbol.Table_Name)
			.build();

	public List<HazardSymbol> getByIds(@NonNull final Collection<HazardSymbolId> ids)
	{
		return getAll().getByIds(ids);
	}

	private HazardSymbolMap getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private HazardSymbolMap retrieveAll()
	{
		final ImmutableList<HazardSymbol> list = queryBL.createQueryBuilder(I_M_HazardSymbol.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(HazardSymbolRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new HazardSymbolMap(list);
	}

	private static HazardSymbol fromRecord(final I_M_HazardSymbol record)
	{
		final IModelTranslationMap trl = InterfaceWrapperHelper.getModelTranslationMap(record);
		return HazardSymbol.builder()
				.id(HazardSymbolId.ofRepoId(record.getM_HazardSymbol_ID()))
				.value(record.getValue())
				.name(trl.getColumnTrl(I_M_HazardSymbol.COLUMNNAME_Name, record.getName()))
				.imageId(AdImageId.ofRepoIdOrNull(record.getAD_Image_ID()))
				.build();
	}
}
