package de.metas.product.allergen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product_Allergen;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Repository
public class ProductAllergensRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProductId, ProductAllergens> cache = CCache.<ProductId, ProductAllergens>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_M_Product_Allergen.Table_Name)
			.build();

	@NonNull
	public ProductAllergens getByProductId(@NonNull final ProductId productId)
	{
		return cache.getOrLoad(productId, this::retrieveByProductId);
	}

	public void save(@NonNull final SaveProductAllergenRequest request)
	{
		final ProductAllergens productAllergensToStore = request.getProductAllergens();

		final Map<AllergenId, I_M_Product_Allergen> existingRecordByAllergenId =
				getAllergenId2ProductAllergenRecord(productAllergensToStore.getProductId());

		//delete obsolete allergens
		existingRecordByAllergenId.keySet()
				.stream()
				.filter(allergenId -> !productAllergensToStore.hasAllergen(allergenId))
				.map(existingRecordByAllergenId::get)
				.forEach(InterfaceWrapperHelper::delete);

		//insert new allergens
		productAllergensToStore.getAllergenIds()
				.stream()
				.filter(allergenId -> !existingRecordByAllergenId.containsKey(allergenId))
				.forEach(allergenId -> {
					final I_M_Product_Allergen productAllergen = InterfaceWrapperHelper.newInstance(I_M_Product_Allergen.class);
					productAllergen.setM_Allergen_ID(allergenId.getRepoId());
					productAllergen.setM_Product_ID(productAllergensToStore.getProductId().getRepoId());
					productAllergen.setIsActive(true);
					productAllergen.setAD_Org_ID(request.getOrgId().getRepoId());

					InterfaceWrapperHelper.save(productAllergen);
				});
	}

	@NonNull
	private ProductAllergens retrieveByProductId(@NonNull final ProductId productId)
	{
		final ImmutableSet<AllergenId> allergenIds = streamForProductId(productId)
				.map(record -> AllergenId.ofRepoId(record.getM_Allergen_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return ProductAllergens.builder()
				.productId(productId)
				.allergenIds(allergenIds)
				.build();
	}

	@NonNull
	private Map<AllergenId, I_M_Product_Allergen> getAllergenId2ProductAllergenRecord(@NonNull final ProductId productId)
	{
		return streamForProductId(productId)
				.collect(ImmutableMap.toImmutableMap(record -> AllergenId.ofRepoId(record.getM_Allergen_ID()),
													 Function.identity()));
	}

	@NonNull
	private Stream<I_M_Product_Allergen> streamForProductId(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Product_Allergen.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Allergen.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream();
	}
}
