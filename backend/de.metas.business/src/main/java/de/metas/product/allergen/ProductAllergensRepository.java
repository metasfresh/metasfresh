package de.metas.product.allergen;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product_Allergen;
import org.compiere.model.I_M_Product_HazardSymbol;
import org.springframework.stereotype.Repository;

@Repository
public class ProductAllergensRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProductId, ProductAllergens> cache = CCache.<ProductId, ProductAllergens>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_M_Product_Allergen.Table_Name)
			.build();

	public ProductAllergens getByProductId(@NonNull ProductId productId)
	{
		return cache.getOrLoad(productId, this::retrieveByProductId);
	}

	private ProductAllergens retrieveByProductId(@NonNull ProductId productId)
	{
		final ImmutableSet<AllergenId> allergenIds = queryBL.createQueryBuilder(I_M_Product_Allergen.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_HazardSymbol.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(record -> AllergenId.ofRepoId(record.getM_Allergen_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return ProductAllergens.builder()
				.productId(productId)
				.allergenIds(allergenIds)
				.build();
	}

}
