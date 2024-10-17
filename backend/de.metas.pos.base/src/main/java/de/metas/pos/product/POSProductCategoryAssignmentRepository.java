package de.metas.pos.product;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.pos.repository.model.I_C_POS_ProductCategory_Assignment;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
class POSProductCategoryAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ImmutableSet<POSProductCategoryId> getProductCategoryIdsByProductId(@NonNull final ProductId productId)
	{
		return getProductCategoryIdsByProductIds(ImmutableSet.of(productId)).get(productId);
	}

	public ImmutableSetMultimap<ProductId, POSProductCategoryId> getProductCategoryIdsByProductIds(@NonNull final Collection<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		return queryBL.createQueryBuilder(I_C_POS_ProductCategory_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_POS_ProductCategory_Assignment.COLUMNNAME_M_Product_ID, productIds)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> ProductId.ofRepoId(record.getM_Product_ID()),
						record -> POSProductCategoryId.ofRepoId(record.getC_POS_ProductCategory_ID())
				));
	}
}
