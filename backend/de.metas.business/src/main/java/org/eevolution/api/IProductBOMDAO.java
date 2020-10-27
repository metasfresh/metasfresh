package org.eevolution.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import com.google.common.collect.ImmutableList;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.product.ProductId;
import de.metas.util.ISingletonService;

public interface IProductBOMDAO extends ISingletonService
{
	I_PP_Product_BOM getById(ProductBOMId bomId);

	@Deprecated
	default I_PP_Product_BOM getById(final int productBomId)
	{
		return productBomId > 0 ? getById(ProductBOMId.ofRepoId(productBomId)) : null;
	}

	List<I_PP_Product_BOM> getByIds(Collection<ProductBOMId> bomIds);

	I_PP_Product_BOMLine getBOMLineById(int productBOMLineId);

	ImmutableList<I_PP_Product_BOMLine> retrieveLines(I_PP_Product_BOM productBOM);

	List<I_PP_Product_BOMLine> retrieveLinesByBOMIds(Collection<ProductBOMId> bomIds);

	int retrieveLastLineNo(int ppProductBOMId);

	Optional<I_PP_Product_BOM> getDefaultBOM(I_M_Product product);

	Optional<I_PP_Product_BOM> getDefaultBOMByProductId(ProductId productId);

	Optional<ProductBOMId> getDefaultBOMIdByProductId(ProductId productId);

	boolean hasBOMs(ProductId productId);

	IQuery<I_PP_Product_BOMLine> retrieveBOMLinesForProductQuery(Properties ctx, int productId, String trxName);

	List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(Collection<Integer> productIds);

	default List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(final Integer... productIds)
	{
		return retrieveBOMsContainingExactProducts(Arrays.asList(productIds));
	}

	void save(I_PP_Product_BOMLine bomLine);

	ProductBOMId createBOM(BOMCreateRequest request);

	ProductId getBOMProductId(ProductBOMId bomId);
}
