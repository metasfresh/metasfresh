package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.eevolution.api.impl.ProductBOM;
import org.eevolution.api.impl.ProductBOMRequest;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IProductBOMDAO extends ISingletonService
{
	Optional<ProductBOM> retrieveValidProductBOM(@NonNull ProductBOMRequest productBOMRequest);

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

	boolean hasBOMs(@NonNull ProductBOMVersionsId bomVersionsId);

	Optional<I_PP_Product_BOM> getPreviousVersion(@NonNull I_PP_Product_BOM bomVersion, @Nullable Set<BOMType> bomTypes, @Nullable DocStatus docStatus);

	List<I_PP_Product_BOM> getSiblings(@NonNull I_PP_Product_BOM bomVersion);

	boolean isComponent(ProductId productId);

	Optional<I_PP_Product_BOM> getLatestBOMRecordByVersionAndType(@NonNull ProductBOMVersionsId bomVersionsId, @Nullable Set<BOMType> bomTypes);

	Optional<ProductBOMId> getLatestBOMIdByVersionAndType(@NonNull ProductBOMVersionsId bomVersionsId, @Nullable Set<BOMType> bomTypes);

	Optional<I_PP_Product_BOM> getByProductIdAndType(@NonNull ProductId productId, @NonNull Set<BOMType> bomTypes);

	default Optional<ProductBOMId> getIdByProductIdAndType(@NonNull final ProductId productId, @NonNull final Set<BOMType> bomTypes)
	{
		return getByProductIdAndType(productId, bomTypes)
				.map(I_PP_Product_BOM::getPP_Product_BOM_ID)
				.map(ProductBOMId::ofRepoId);
	}

	Optional<ProductBOMLineId> getBomLineByProductId(@NonNull ProductBOMId productBOMId, @NonNull ProductId productId);
}
