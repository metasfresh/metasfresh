package de.metas.pos.product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.image.AdImageId;
import de.metas.pos.repository.model.I_C_POS_ProductCategory;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
class POSProductCategoryRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, POSProductCategoriesMap> cache = CCache.<Integer, POSProductCategoriesMap>builder()
			.tableName(I_C_POS_ProductCategory.Table_Name)
			.build();

	public POSProductCategory getById(@NonNull final POSProductCategoryId id)
	{
		return getMap().getById(id);
	}

	public ImmutableSet<POSProductCategory> getActiveByIds(final Collection<POSProductCategoryId> ids)
	{
		return getMap().getActiveByIds(ids);
	}

	private POSProductCategoriesMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private POSProductCategoriesMap retrieveMap()
	{
		return queryBL
				.createQueryBuilder(I_C_POS_ProductCategory.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(POSProductCategoryRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(POSProductCategoriesMap::ofList))
				;
	}

	private static POSProductCategory fromRecord(final I_C_POS_ProductCategory record)
	{
		return POSProductCategory.builder()
				.id(POSProductCategoryId.ofRepoId(record.getC_POS_ProductCategory_ID()))
				.name(record.getName())
				.description(record.getDescription())
				.imageId(AdImageId.ofRepoIdOrNull(record.getAD_Image_ID()))
				.build();
	}

	//
	//
	//
	//
	//

	private static class POSProductCategoriesMap
	{
		public static final POSProductCategoriesMap EMPTY = new POSProductCategoriesMap(ImmutableList.of());

		private final ImmutableMap<POSProductCategoryId, POSProductCategory> byIds;

		private POSProductCategoriesMap(final List<POSProductCategory> list)
		{
			this.byIds = Maps.uniqueIndex(list, POSProductCategory::getId);
		}

		public static POSProductCategoriesMap ofList(final List<POSProductCategory> list)
		{
			return list.isEmpty() ? EMPTY : new POSProductCategoriesMap(list);
		}

		public POSProductCategory getById(@NonNull final POSProductCategoryId id)
		{
			final POSProductCategory category = byIds.get(id);
			if (category == null)
			{
				throw new AdempiereException("Category not found: " + id);
			}
			return category;
		}

		public ImmutableSet<POSProductCategory> getActiveByIds(final Collection<POSProductCategoryId> ids)
		{
			if (ids.isEmpty())
			{
				return ImmutableSet.of();
			}

			return ids.stream()
					.map(byIds::get)
					.filter(Objects::nonNull) // only active
					.collect(ImmutableSet.toImmutableSet());
		}
	}
}
