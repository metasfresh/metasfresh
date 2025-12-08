package de.metas.product.hazard_symbol;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product_HazardSymbol;
import org.springframework.stereotype.Repository;

@Repository
public class ProductHazardSymbolRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProductId, ProductHazardSymbols> cache = CCache.<ProductId, ProductHazardSymbols>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_M_Product_HazardSymbol.Table_Name)
			.build();

	public ProductHazardSymbols getByProductId(@NonNull ProductId productId)
	{
		return cache.getOrLoad(productId, this::retrieveByProductId);
	}

	private ProductHazardSymbols retrieveByProductId(@NonNull ProductId productId)
	{
		final ImmutableSet<HazardSymbolId> hazardSymbolIds = queryBL.createQueryBuilder(I_M_Product_HazardSymbol.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_HazardSymbol.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(record -> HazardSymbolId.ofRepoId(record.getM_HazardSymbol_ID()))
				.collect(ImmutableSet.toImmutableSet());

		return ProductHazardSymbols.builder()
				.productId(productId)
				.hazardSymbolIds(hazardSymbolIds)
				.build();
	}

}
