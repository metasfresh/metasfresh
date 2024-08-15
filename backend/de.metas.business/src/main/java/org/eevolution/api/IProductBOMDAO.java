package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IProductBOMDAO extends ISingletonService
{
	Optional<I_PP_Product_BOM> getDefaultBOM(@NonNull ProductId productId, @NonNull BOMType bomType);

	I_PP_Product_BOM getById(ProductBOMId bomId);

	@Deprecated
	@Nullable
	default I_PP_Product_BOM getById(final int productBomId)
	{
		return productBomId > 0 ? getById(ProductBOMId.ofRepoId(productBomId)) : null;
	}

	List<I_PP_Product_BOM> getByIds(Collection<ProductBOMId> bomIds);

	I_PP_Product_BOMLine getBOMLineById(int productBOMLineId);

	ImmutableList<I_PP_Product_BOMLine> retrieveLines(I_PP_Product_BOM productBOM);

	List<I_PP_Product_BOMLine> retrieveLinesByBOMIds(Collection<ProductBOMId> bomIds);

	int retrieveLastLineNo(int ppProductBOMId);

	Optional<I_PP_Product_BOM> getDefaultBOMByProductId(ProductId productId);

	Optional<ProductBOMId> getDefaultBOMIdByProductId(ProductId productId);

	boolean hasBOMs(ProductId productId);

	List<I_PP_Product_BOMLine> retrieveBOMLinesByComponentIdInTrx(ProductId productId);

	List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(Collection<Integer> productIds);

	default List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(final Integer... productIds)
	{
		return retrieveBOMsContainingExactProducts(Arrays.asList(productIds));
	}

	void save(I_PP_Product_BOMLine bomLine);

	I_PP_Product_BOM createBOM(ProductBOMVersionsId versionsId, BOMCreateRequest request);

	ProductId getBOMProductId(ProductBOMId bomId);

	Optional<ProductBOMId> getLatestBOMByVersion(@NonNull ProductBOMVersionsId bomVersionsId);

	Optional<I_PP_Product_BOM> getLatestBOMRecordByVersionId(ProductBOMVersionsId bomVersionsId);

	Optional<I_PP_Product_BOM> getPreviousVersion(I_PP_Product_BOM bomVersion, DocStatus docStatus);

	boolean isComponent(ProductId productId);

	Optional<ProductBOMLineId> getBomLineByProductId(@NonNull ProductBOMId productBOMId, @NonNull ProductId productId);
}
