package org.eevolution.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
class ProductBOMCycleDetection
{
	private final IProductBL productBL;
	private final IProductBOMDAO productBOMDAO;

	private static final AdMessageKey ERR_PRODUCT_BOM_CYCLE = AdMessageKey.of("Product_BOM_Cycle_Error");

	private final HashMap<ProductBOMId, I_PP_Product_BOM> boms = new HashMap<>();
	private final HashMap<ProductId, List<I_PP_Product_BOMLine>> bomsLinesByComponentId = new HashMap<>();

	public void assertNoCycles(@NonNull final Collection<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return;
		}

		for (final ProductId productId : ImmutableSet.copyOf(productIds))
		{
			assertNoCycles(productId, ImmutableSet.of());
		}
	}

	private void assertNoCycles(@NonNull final ProductId productId, @NonNull final ImmutableSet<ProductId> trace)
	{
		final List<I_PP_Product_BOMLine> productBOMLines = getBOMLinesByComponentId(productId);
		for (final I_PP_Product_BOMLine productBOMLine : productBOMLines)
		{
			// Don't navigate the Co/ByProduct lines (gh480)
			if (isByOrCoProduct(productBOMLine))
			{
				continue;
			}

			assertNoCycles(productBOMLine, trace);
		}
	}

	private void assertNoCycles(final I_PP_Product_BOMLine bomLine, @NonNull final ImmutableSet<ProductId> trace)
	{
		final ProductBOMId bomId = ProductBOMId.ofRepoId(bomLine.getPP_Product_BOM_ID());
		final I_PP_Product_BOM bom = getBOM(bomId);
		if (!bom.isActive())
		{
			return;
		}

		// Check Child = Parent error
		final ProductId bomLineProductId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		final ProductId bomProductId = ProductId.ofRepoId(bom.getM_Product_ID());
		if (ProductId.equals(bomLineProductId, bomProductId))
		{
			throw newBOMCycleException(bomLineProductId);
		}

		// Check BOM Loop Error
		if (trace.contains(bomProductId))
		{
			throw newBOMCycleException(bomProductId);
		}

		final ImmutableSet<ProductId> newTrace = ImmutableSet.<ProductId>builder()
				.addAll(trace)
				.add(bomProductId)
				.build();

		assertNoCycles(bomProductId, newTrace);
	}

	private static boolean isByOrCoProduct(final I_PP_Product_BOMLine bomLine)
	{
		final BOMComponentType componentType = BOMComponentType.ofCode(Objects.requireNonNull(bomLine.getComponentType()));
		return componentType.isByOrCoProduct();
	}

	private I_PP_Product_BOM getBOM(final ProductBOMId bomId)
	{
		return boms.computeIfAbsent(bomId, productBOMDAO::getByIdInTrx);
	}

	private List<I_PP_Product_BOMLine> getBOMLinesByComponentId(final ProductId productId)
	{
		return bomsLinesByComponentId.computeIfAbsent(productId, productBOMDAO::retrieveBOMLinesByComponentIdInTrx);
	}

	private AdempiereException newBOMCycleException(final ProductId productId)
	{
		final I_M_Product product = productBL.getById(productId);
		return new AdempiereException(ERR_PRODUCT_BOM_CYCLE, product.getValue());
	}
}
