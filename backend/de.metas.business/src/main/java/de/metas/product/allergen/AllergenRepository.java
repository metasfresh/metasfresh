package de.metas.product.allergen;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Allergen;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class AllergenRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, AllergenMap> cache = CCache.<Integer, AllergenMap>builder()
			.tableName(I_M_Allergen.Table_Name)
			.build();

	public List<Allergen> getByIds(@NonNull final Collection<AllergenId> ids)
	{
		return getAll().getByIds(ids);
	}

	private AllergenMap getAll()
	{
		return cache.getOrLoad(0, this::retrieveAll);
	}

	private AllergenMap retrieveAll()
	{
		final ImmutableList<Allergen> list = queryBL.createQueryBuilder(I_M_Allergen.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(AllergenRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new AllergenMap(list);
	}

	private static Allergen fromRecord(final I_M_Allergen record)
	{
		final IModelTranslationMap trl = InterfaceWrapperHelper.getModelTranslationMap(record);
		return Allergen.builder()
				.id(AllergenId.ofRepoId(record.getM_Allergen_ID()))
				.name(trl.getColumnTrl(I_M_Allergen.COLUMNNAME_Name, record.getName()))
				.color(StringUtils.trimBlankToNull(record.getColor()))
				.build();
	}
}
